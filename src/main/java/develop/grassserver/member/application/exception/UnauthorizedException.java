package develop.grassserver.member.application.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RuntimeException {

    private static final String MESSAGE = "인증되지 않은 멤버입니다.";

    public UnauthorizedException() {
        super(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.UNAUTHORIZED, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}
