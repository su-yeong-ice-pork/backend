package develop.grassserver.member.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class DuplicateMemberException extends RuntimeException {

    public DuplicateMemberException(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.CONFLICT, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.CONFLICT;
    }
}
