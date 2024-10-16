package develop.grassserver.auth.presentation.exception;

import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.common.utils.ApiUtils.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResult<?>> usernameNotFoundException(UsernameNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiUtils.error(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

}
