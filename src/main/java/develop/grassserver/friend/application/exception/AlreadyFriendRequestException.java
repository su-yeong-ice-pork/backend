package develop.grassserver.friend.application.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class AlreadyFriendRequestException extends RuntimeException {

    private static final String MESSAGE = "이미 친구 요청을 보낸 상태입니다.";

    public AlreadyFriendRequestException() {
        super(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.CONFLICT, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.CONFLICT;
    }
}
