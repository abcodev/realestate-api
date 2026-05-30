package realestate.server.application.common.exception;

public class CustomAuthException extends AppException {
    
    public CustomAuthException(AuthExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
