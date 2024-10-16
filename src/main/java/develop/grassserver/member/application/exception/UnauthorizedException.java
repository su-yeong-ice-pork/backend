package develop.grassserver.member.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.UNAUTHORIZED, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}
