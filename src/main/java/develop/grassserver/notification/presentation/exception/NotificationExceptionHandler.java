package develop.grassserver.notification.presentation.exception;

import develop.grassserver.common.utils.ApiUtils.ApiResult;
import develop.grassserver.notification.application.exception.ExceedSendEmojiCountException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotificationExceptionHandler {

    @ExceptionHandler(ExceedSendEmojiCountException.class)
    public ResponseEntity<ApiResult<?>> exceedSendEmojiCountException(ExceedSendEmojiCountException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}