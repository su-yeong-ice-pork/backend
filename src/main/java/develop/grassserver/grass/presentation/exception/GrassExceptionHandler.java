package develop.grassserver.grass.presentation.exception;

import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.grass.application.exception.AlreadyCheckedInException;
import develop.grassserver.grass.application.exception.MissingAttendanceException;
import java.time.format.DateTimeParseException;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GrassExceptionHandler {

    @ExceptionHandler(MissingAttendanceException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> missingAttendanceException(MissingAttendanceException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(AlreadyCheckedInException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> alreadyCheckedInException(AlreadyCheckedInException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler({DateTimeParseException.class, UnsupportedTemporalTypeException.class})
    public ResponseEntity<ApiUtils.ApiResult<?>> handleDateTimeParseException(Exception ex) {
        return new ResponseEntity<>(
                ApiUtils.error(HttpStatus.BAD_REQUEST, "잘못된 시간 형식입니다."),
                HttpStatus.BAD_REQUEST
        );
    }
}
