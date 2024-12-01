package develop.grassserver.randomStudy.application.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class DuplicateApplicationException extends RuntimeException {

    private static final String MESSAGE = "이미 해당 날짜에 랜덤 스터디를 신청하셨습니다. 랜덤 스터디는 하루에 하나만 가능합니다.";

    public DuplicateApplicationException() {
        super(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
