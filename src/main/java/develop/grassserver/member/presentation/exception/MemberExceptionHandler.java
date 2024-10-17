package develop.grassserver.member.presentation.exception;

import develop.grassserver.auth.application.exception.ReauthenticationRequiredException;
import develop.grassserver.auth.application.exception.UnauthorizedException;
import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.member.application.exception.DuplicateMemberException;
import develop.grassserver.member.application.exception.InvalidPasswordException;
import develop.grassserver.member.application.exception.MemberEmailFormatException;
import develop.grassserver.member.application.exception.MemberNameFormatException;
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
