package develop.grassserver.member.auth.exception;

import develop.grassserver.utils.ApiUtils.ApiResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(IncorrectAuthCodeException.class)
    public ResponseEntity<ApiResult<?>> incorrectAuthCodeException(IncorrectAuthCodeException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(ExpirationAuthCodeException.class)
    public ResponseEntity<ApiResult<?>> expirationAuthCodeException(ExpirationAuthCodeException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
