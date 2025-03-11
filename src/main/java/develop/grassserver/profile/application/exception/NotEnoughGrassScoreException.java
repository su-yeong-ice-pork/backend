package develop.grassserver.profile.application.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class NotEnoughGrassScoreException extends RuntimeException {

    private static final String MESSAGE = "출석 점수가 부족합니다.";

    public NotEnoughGrassScoreException() {
        super(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
