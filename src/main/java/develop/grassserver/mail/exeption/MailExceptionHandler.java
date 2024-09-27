package develop.grassserver.mail.exeption;

import develop.grassserver.utils.ApiUtils;
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
