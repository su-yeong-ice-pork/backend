package develop.grassserver.member.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class ReauthenticationRequiredException extends RuntimeException {
    private final String message = "토큰이 만료되었습니다. 재로그인을 해주세요.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.UNAUTHORIZED, message);
    }

    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}


