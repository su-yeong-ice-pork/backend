package develop.grassserver.friend.application.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class NotExistFriendRelationException extends RuntimeException {

    private static final String MESSAGE = "친구로 등록되지 않은 멤버입니다.";

    public NotExistFriendRelationException() {
        super(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
