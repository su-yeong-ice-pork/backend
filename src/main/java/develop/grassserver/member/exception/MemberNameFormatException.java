package develop.grassserver.member.exception;

import develop.grassserver.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class MemberNameFormatException extends RuntimeException {

    public MemberNameFormatException(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
