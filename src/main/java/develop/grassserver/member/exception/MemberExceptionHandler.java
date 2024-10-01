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

    @ExceptionHandler(MemberNameFormatException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> memberNameFormatException(MemberNameFormatException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(MemberEmailFormatException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> memberEmailFormatException(MemberEmailFormatException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> handleForbiddenException(UnauthorizedException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> invalidPasswordException(InvalidPasswordException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(ReauthenticationRequiredException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> reauthenticationRequiredException(
            ReauthenticationRequiredException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

}
