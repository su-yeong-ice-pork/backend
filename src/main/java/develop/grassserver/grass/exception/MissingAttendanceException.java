package develop.grassserver.grass.exception;

import develop.grassserver.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class MissingAttendanceException extends RuntimeException {
    private final String message = "출석 기록이 없습니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, message);
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
