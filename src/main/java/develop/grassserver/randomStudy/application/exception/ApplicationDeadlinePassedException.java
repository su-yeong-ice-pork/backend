package develop.grassserver.randomStudy.application.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class ApplicationDeadlinePassedException extends RuntimeException {

    private static final String MESSAGE = "신청 마감 시간이 지났습니다. 신청은 매일 새벽 5시까지 가능합니다.";

    public ApplicationDeadlinePassedException() {
        super(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
