package develop.grassserver.grass.exception;

import develop.grassserver.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class AlreadyCheckedInException extends RuntimeException {
    private final String message = "오늘의 잔디가 이미 존재합니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, message);
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
