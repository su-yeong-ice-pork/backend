package develop.grassserver.member.exception;

import develop.grassserver.utils.ApiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> duplicateMemberException(DuplicateMemberException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(MemberNameFormatException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> memberNameFormatException(MemberNameFormatException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiUtils.ApiResult<Void>> handleForbiddenException(ForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiUtils.error(HttpStatus.FORBIDDEN, ex.getMessage()));
    }
}
