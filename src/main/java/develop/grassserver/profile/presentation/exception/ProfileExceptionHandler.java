package develop.grassserver.profile.presentation.exception;

import develop.grassserver.common.utils.ApiUtils.ApiResult;
import develop.grassserver.profile.application.exception.ImageUploadFailedException;
import develop.grassserver.profile.application.exception.NotEnoughFreezeCountException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProfileExceptionHandler {

    @ExceptionHandler(ImageUploadFailedException.class)
    public ResponseEntity<ApiResult<?>> imageUploadFailedException(ImageUploadFailedException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(NotEnoughFreezeCountException.class)
    public ResponseEntity<ApiResult<?>> notEnoughFreezeCountException(ImageUploadFailedException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
