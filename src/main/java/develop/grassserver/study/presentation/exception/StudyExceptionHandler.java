package develop.grassserver.study.presentation.exception;

import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.study.application.exception.InvalidInviteCodeException;
import develop.grassserver.study.application.exception.LeaderCannotLeaveException;
import develop.grassserver.study.application.exception.NotAStudyMemberException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StudyExceptionHandler {

    @ExceptionHandler(InvalidInviteCodeException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> invalidInviteCodeException(InvalidInviteCodeException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(LeaderCannotLeaveException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> leaderCannotLeaveException(LeaderCannotLeaveException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(NotAStudyMemberException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> notAStudyMemberException(NotAStudyMemberException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
