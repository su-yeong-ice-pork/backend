package develop.grassserver.notification.application.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class ExceedSendMessageCountException extends RuntimeException {

    private static final String MESSAGE = "응원 메세지는 하루에 2번만 보낼 수 있습니다.";

    public ExceedSendMessageCountException() {
        super(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
