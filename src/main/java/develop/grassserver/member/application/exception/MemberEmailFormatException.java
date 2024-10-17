package develop.grassserver.member.application.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class MemberEmailFormatException extends RuntimeException {

    private static final String MESSAGE = "멤버 이메일 형식 오류입니다.";

    public MemberEmailFormatException() {
        super(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
