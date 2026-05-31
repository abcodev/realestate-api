package realtyos.server.application.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 메서드 실행 시간을 로깅하기 위한 커스텀 어노테이션.
 * <p>
 * 이 어노테이션이 붙은 메서드는 {@link ExecutionTimeAspect}에 의해
 * 실행 시간, 호출 시간, 클라이언트 IP, User-Agent 정보가 자동으로 로깅됩니다.
 * <p>
 * RestController 메서드에는 자동 적용되므로,
 * 그 외 Service/Repository 등에서 개별적으로 측정하고 싶을 때 사용합니다.
 */
@Target(ElementType.METHOD)       // 메서드 레벨에서만 사용 가능
@Retention(RetentionPolicy.RUNTIME) // 런타임까지 어노테이션 정보 유지
public @interface LogExecutionTime {
}
