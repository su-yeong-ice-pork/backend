package develop.grassserver.member.auth.exception;

import develop.grassserver.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class ExpirationAuthCodeException extends RuntimeException {

    public ExpirationAuthCodeException(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.NOT_FOUND, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }
}
