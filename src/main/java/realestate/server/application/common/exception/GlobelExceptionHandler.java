package realestate.server.application.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestControllerAdvice
public class GlobelExceptionHandler {

    /**
     * ResponseStatusException 처리 (401, 404 등 Spring 기본 예외)
     * catch-all Exception 핸들러보다 먼저 매칭되어 원래 상태코드를 보존합니다.
     */
    @ExceptionHandler(ResponseStatusException.class)
    protected ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException e) {
        log.warn("ResponseStatusException: status={}, reason={}", e.getStatusCode(), e.getReason());
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .code(String.valueOf(e.getStatusCode().value()))
                .message(e.getReason())
                .build();
        return ResponseEntity
                .status(e.getStatusCode())
                .body(response);
    }

    /**
     * BusinessException 처리 (기존 비즈니스 로직 예외)
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.warn("BusinessException: {}", e.getMessage());
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.of(errorCode));
    }

    /**
     * AppException 처리 (앱 예외 코드 기반)
     */
    @ExceptionHandler(AppException.class)
    protected ResponseEntity<ErrorResponse> handleAppException(AppException e) {
        log.warn("AppException: code={}, message={}", e.getExceptionCode().getCode(), e.getMessage());
        AppExceptionCode code = e.getExceptionCode();
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .code(code.getCode())
                .message(code.getMessage())
                .build();
        return ResponseEntity
                .status(code.getStatus())
                .body(response);
    }

    /**
     * 소켓 타임아웃 (외부 API 호출 타임아웃)
     */
    @ExceptionHandler(SocketTimeoutException.class)
    protected ResponseEntity<ErrorResponse> handleSocketTimeoutException(SocketTimeoutException e, WebRequest request) {
        log.error("External API timeout: uri={}, message={}", request.getDescription(false), e.getMessage());
        return ResponseEntity
                .status(ErrorCode.EXTERNAL_API_TIMEOUT.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.EXTERNAL_API_TIMEOUT, e.getMessage()));
    }

    /**
     * 일반 타임아웃 (요청 처리 시간 초과)
     */
    @ExceptionHandler(TimeoutException.class)
    protected ResponseEntity<ErrorResponse> handleTimeoutException(TimeoutException e, WebRequest request) {
        log.error("Request timeout: uri={}, message={}", request.getDescription(false), e.getMessage());
        return ResponseEntity
                .status(ErrorCode.REQUEST_TIMEOUT.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.REQUEST_TIMEOUT, e.getMessage()));
    }

    /**
     * 기타 예상하지 못한 예외
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e, WebRequest request) {
        log.error("Unhandled exception: uri={}, type={}, message={}",
                request.getDescription(false), e.getClass().getSimpleName(), e.getMessage(), e);
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
}
