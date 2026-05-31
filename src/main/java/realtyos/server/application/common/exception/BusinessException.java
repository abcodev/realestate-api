package realtyos.server.application.common.exception;

import lombok.Getter;

/**
 * 비즈니스 로직에서 발생하는 커스텀 예외.
 * <p>
 * 서비스 레이어에서 비즈니스 규칙 위반 시 이 예외를 던지면,
 * {@link GlobelExceptionHandler}에서 자동으로 적절한 에러 응답을 생성합니다.
 *
 * <p>사용 예시:
 * <pre>{@code
 * throw new BusinessException(ErrorCode.WEATHER_NOT_FOUND);
 * }</pre>
 */
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /**
     * 커스텀 메시지를 포함하는 비즈니스 예외 생성.
     *
     * @param errorCode 에러 코드
     * @param message   기본 메시지 대신 사용할 커스텀 메시지
     */
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
