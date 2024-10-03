package develop.grassserver.grass.exception;

import develop.grassserver.member.exception.DuplicateMemberException;
import develop.grassserver.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GrassExceptionHandler {

    @ExceptionHandler(MissingAttendanceException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> missingAttendanceException(DuplicateMemberException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
