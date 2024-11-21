package develop.grassserver.study.application.service.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class NotAStudyMemberException extends RuntimeException {

    private static final String MESSAGE = "해당 고정 스터디에 소속되어 있지 않습니다.";

    public NotAStudyMemberException() {
        super(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.FORBIDDEN, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.FORBIDDEN;
    }
}
