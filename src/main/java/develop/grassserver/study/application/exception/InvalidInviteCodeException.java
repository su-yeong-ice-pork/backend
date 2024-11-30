package develop.grassserver.study.application.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class InvalidInviteCodeException extends RuntimeException {

    private static final String MESSAGE = "해당 인증 코드에 대한 스터디가 존재하지 않습니다. 인증코드를 다시 입력해주세요.";

    public InvalidInviteCodeException() {
        super(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
