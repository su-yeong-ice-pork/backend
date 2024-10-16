package develop.grassserver.member.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends RuntimeException {
    private final String message = "로그인이 실패하였습니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.UNAUTHORIZED, message);
    }

    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}


