package develop.grassserver.friend.application.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class ExistFriendRelationException extends RuntimeException {

    private static final String MESSAGE = "이미 등록된 친구입니다.";

    public ExistFriendRelationException() {
        super(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.CONFLICT, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.CONFLICT;
    }
}
