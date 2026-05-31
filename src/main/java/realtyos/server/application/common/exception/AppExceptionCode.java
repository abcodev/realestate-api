package realtyos.server.application.common.exception;

import org.springframework.http.HttpStatus;

public interface AppExceptionCode {
    HttpStatus getStatus();

    String getCode();

    String getMessage();
}
