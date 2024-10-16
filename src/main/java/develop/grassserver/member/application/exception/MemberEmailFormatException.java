package develop.grassserver.member.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class MemberEmailFormatException extends RuntimeException {

    public MemberEmailFormatException(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
