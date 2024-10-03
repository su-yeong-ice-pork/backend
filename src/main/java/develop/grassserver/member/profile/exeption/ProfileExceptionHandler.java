package develop.grassserver.member.profile.exeption;

import develop.grassserver.utils.ApiUtils.ApiResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProfileExceptionHandler {

    @ExceptionHandler(ImageUploadFailedException.class)
    public ResponseEntity<ApiResult<?>> imageUploadFailedException(ImageUploadFailedException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
