package develop.grassserver.member.application.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class MemberNameFormatException extends RuntimeException {

    private static final String MESSAGE = "멤버 이름 형식 오류입니다.";

    public MemberNameFormatException() {
        super(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
