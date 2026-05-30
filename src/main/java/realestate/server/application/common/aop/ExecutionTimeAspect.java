package realestate.server.application.common.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * API 호출 정보를 로깅하는 AOP Aspect.
 * <p>
 * 모든 {@code @RestController} 메서드 또는 {@link LogExecutionTime} 어노테이션이
 * 붙은 메서드에 대해 다음 정보를 자동으로 로깅합니다:
 * <ul>
 *   <li>호출 시간 (time)</li>
 *   <li>실행 소요 시간 (duration, ms)</li>
 *   <li>클라이언트 IP 주소 (clientIp)</li>
 *   <li>기기/브라우저 정보 (userAgent)</li>
 * </ul>
 */
@Aspect      // AOP Aspect로 등록
@Component   // Spring Bean으로 등록
@Slf4j       // Lombok 로거 자동 생성
public class ExecutionTimeAspect {

    // 로그에 출력할 날짜/시간 포맷
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * API 호출 정보를 측정하고 로깅하는 Around Advice.
     * <p>
     * 적용 대상:
     * 1. {@link LogExecutionTime} 어노테이션이 붙은 메서드
     * 2. {@code @RestController} 클래스 내의 모든 메서드
     *
     * @param joinPoint AOP가 가로챈 메서드 실행 지점
     * @return 원래 메서드의 반환값
     * @throws Throwable 원래 메서드에서 발생하는 예외를 그대로 전파
     */
    @Around("@annotation(LogExecutionTime) || within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1. API 호출 시간 기록
        String callTime = LocalDateTime.now().format(FORMATTER);
        String clientIp = "unknown";
        String userAgent = "unknown";

        // 2. HTTP 요청 정보에서 클라이언트 IP와 User-Agent 추출
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            clientIp = extractClientIp(request);   // 클라이언트 IP 추출
            userAgent = request.getHeader("User-Agent") != null
                    ? request.getHeader("User-Agent")
                    : "unknown";
        }

        // 3. StopWatch로 메서드 실행 시간 측정
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object proceed = joinPoint.proceed(); // 실제 메서드 실행

        stopWatch.stop();

        // 4. 로그 출력 (호출 시간, 메서드명, 소요 시간, IP, User-Agent)
        log.info("[API Call] time={} | method={} | duration={}ms | clientIp={} | userAgent={}",
                callTime,
                joinPoint.getSignature().toShortString(),
                stopWatch.getTotalTimeMillis(),
                clientIp,
                userAgent
        );

        return proceed;
    }

    /**
     * 클라이언트의 실제 IP 주소를 추출합니다.
     * <p>
     * 프록시나 로드밸런서를 거치는 경우, 원본 클라이언트 IP가
     * X-Forwarded-For 등의 헤더에 담겨 전달되므로 이를 우선 확인합니다.
     *
     * @param request HTTP 요청 객체
     * @return 추출된 클라이언트 IP 주소
     */
    private String extractClientIp(HttpServletRequest request) {
        // 프록시/로드밸런서 환경에서 실제 클라이언트 IP를 담는 헤더 목록
        String[] headerNames = {
                "X-Forwarded-For",        // 가장 일반적인 프록시 헤더
                "Proxy-Client-IP",        // Apache 프록시
                "WL-Proxy-Client-IP",     // WebLogic 프록시
                "HTTP_CLIENT_IP",         // 일부 프록시 서버
                "HTTP_X_FORWARDED_FOR"    // 일부 프록시 서버
        };

        // 각 헤더를 순회하며 유효한 IP가 있으면 반환
        for (String header : headerNames) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                // X-Forwarded-For는 "client, proxy1, proxy2" 형식이므로 첫 번째 값 사용
                return ip.split(",")[0].trim();
            }
        }

        // 프록시 헤더가 없으면 직접 연결된 IP 반환
        return request.getRemoteAddr();
    }
}
