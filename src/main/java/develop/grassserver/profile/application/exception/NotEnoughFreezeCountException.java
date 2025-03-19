package develop.grassserver.profile.application.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class NotEnoughFreezeCountException extends RuntimeException {

    private static final String MESSAGE = "프리즈 개수가 부족합니다.";

    public NotEnoughFreezeCountException() {
        super(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
