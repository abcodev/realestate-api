package realtyos.server.application.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 애플리케이션 전역에서 사용하는 에러 코드 정의.
 * <p>
 * 각 에러 코드는 HTTP 상태 코드와 사용자에게 보여줄 메시지를 포함합니다.
 * 도메인별로 에러 코드를 그룹핑하여 관리합니다.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // ===== 공통 에러 (C) =====
    /** 잘못된 요청 파라미터 */
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력값입니다."),
    /** 지원하지 않는 HTTP 메서드 */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", "지원하지 않는 HTTP 메서드입니다."),
    /** 서버 내부 오류 */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C003", "서버 내부 오류가 발생했습니다."),
    /** 요청한 리소스를 찾을 수 없음 */
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "C004", "요청한 리소스를 찾을 수 없습니다."),
    /** 접근 권한 없음 */
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "C005", "접근 권한이 없습니다."),
    /** 요청 처리 시간 초과 */
    REQUEST_TIMEOUT(HttpStatus.SERVICE_UNAVAILABLE, "C006", "요청 처리 시간이 초과되었습니다."),
    /** 외부 API 호출 시간 초과 */
    EXTERNAL_API_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "C007", "외부 서비스 응답 시간이 초과되었습니다."),

    // ===== 날씨 관련 에러 (W) =====
    WEATHER_NOT_FOUND(HttpStatus.NOT_FOUND, "W001", "날씨 정보를 찾을 수 없습니다."),
    WEATHER_API_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "W002", "날씨 외부 API 호출에 실패했습니다."),

    // ===== 스포츠 관련 에러 (S) =====
    SPORTS_NOT_FOUND(HttpStatus.NOT_FOUND, "S001", "스포츠 정보를 찾을 수 없습니다."),
    SPORTS_API_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "S002", "스포츠 외부 API 호출에 실패했습니다."),

    // ===== 금융 관련 에러 (F) =====
    FINANCE_NOT_FOUND(HttpStatus.NOT_FOUND, "F001", "금융 정보를 찾을 수 없습니다."),
    FINANCE_API_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "F002", "금융 외부 API 호출에 실패했습니다."),

    // ===== 인증 관련 에러 (A) =====
    AUTH_INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "A001", "유효하지 않은 리프레시 토큰입니다."),
    AUTH_REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A002", "리프레시 토큰이 만료되었습니다."),

    // ===== 결제 관련 에러 (P) =====
    PAYMENT_VERIFICATION_FAILED(HttpStatus.BAD_REQUEST, "P001", "결제 영수증 검증에 실패했습니다."),
    PAYMENT_ALREADY_PROCESSED(HttpStatus.CONFLICT, "P002", "이미 처리된 결제입니다."),
    PAYMENT_PLATFORM_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "P003", "지원하지 않는 결제 플랫폼입니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "P004", "등록되지 않은 상품입니다."),
    SUBSCRIPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "P005", "구독 정보를 찾을 수 없습니다."),
    WEBHOOK_VERIFICATION_FAILED(HttpStatus.BAD_REQUEST, "P006", "Webhook 검증에 실패했습니다."),
    PAYMENT_RESTORE_NO_HISTORY(HttpStatus.NOT_FOUND, "P007", "구매내역이 없습니다.");

    private final HttpStatus httpStatus;  // HTTP 상태 코드
    private final String code;            // 내부 에러 코드
    private final String message;         // 사용자 노출 메시지
}
