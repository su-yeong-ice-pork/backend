package develop.grassserver.study.application.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class LeaderCannotLeaveException extends RuntimeException {

    private static final String MESSAGE = "방장은 스터디를 나갈 수 없습니다.";

    public LeaderCannotLeaveException() {
        super(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
