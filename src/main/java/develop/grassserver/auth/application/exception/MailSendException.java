package develop.grassserver.auth.application.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class MailSendException extends RuntimeException {

    public MailSendException(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
