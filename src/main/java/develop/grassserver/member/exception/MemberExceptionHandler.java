package develop.grassserver.member.exception;

import develop.grassserver.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> duplicateMemberException(DuplicateMemberException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
