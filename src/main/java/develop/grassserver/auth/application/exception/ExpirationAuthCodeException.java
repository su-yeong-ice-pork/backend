package develop.grassserver.auth.application.exception;

import develop.grassserver.common.utils.ApiUtils;
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
