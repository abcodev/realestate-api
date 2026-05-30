package realestate.server.application.common.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 에러 발생 시 클라이언트에 반환하는 통일된 에러 응답 형식.
 * <p>
 * 응답 예시:
 * <pre>{@code
 * {
 *   "timestamp": "2026-02-09T16:05:00",
 *   "code": "W001",
 *   "message": "날씨 정보를 찾을 수 없습니다.",
 *   "detail": "id=123에 해당하는 날씨 정보가 없습니다."
 * }
 * }</pre>
 */
@Getter
@Builder
public class ErrorResponse {

    /** 에러 발생 시각 */
    private final LocalDateTime timestamp;

    /** 내부 에러 코드 (예: W001, C003) */
    private final String code;

    /** 사용자 노출 메시지 */
    private final String message;

    /** 상세 에러 정보 (디버깅용, 선택적) */
    private final String detail;

    /**
     * ErrorCode로부터 ErrorResponse를 생성합니다.
     *
     * @param errorCode 에러 코드
     * @return ErrorResponse 인스턴스
     */
    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

    /**
     * ErrorCode + 상세 메시지로 ErrorResponse를 생성합니다.
     *
     * @param errorCode 에러 코드
     * @param detail    상세 에러 정보
     * @return ErrorResponse 인스턴스
     */
    public static ErrorResponse of(ErrorCode errorCode, String detail) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .detail(detail)
                .build();
    }
}
