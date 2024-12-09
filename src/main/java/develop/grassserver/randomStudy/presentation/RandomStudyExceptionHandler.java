package develop.grassserver.randomStudy.presentation;

import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.randomStudy.application.exception.ApplicationDeadlinePassedException;
import develop.grassserver.randomStudy.application.exception.DuplicateApplicationException;
import develop.grassserver.randomStudy.application.exception.NotARandomStudyMemberException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RandomStudyExceptionHandler {

    @ExceptionHandler(ApplicationDeadlinePassedException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> applicationDeadlinePassedException(
            ApplicationDeadlinePassedException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(DuplicateApplicationException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> duplicateApplicationException(
            DuplicateApplicationException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(NotARandomStudyMemberException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> notARandomStudyMemberException(
            NotARandomStudyMemberException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
