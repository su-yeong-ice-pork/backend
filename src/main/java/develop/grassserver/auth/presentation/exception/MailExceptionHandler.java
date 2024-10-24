package develop.grassserver.auth.presentation.exception;

import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.auth.application.exception.MailSendException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MailExceptionHandler {

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> mailSendException(MailSendException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
