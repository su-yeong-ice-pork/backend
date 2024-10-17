package develop.grassserver.member.application.exception;

import develop.grassserver.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class DuplicateMemberException extends RuntimeException {

    private static final String MESSAGE = "멤버가 중복됩니다.";

    public DuplicateMemberException() {
        super(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.CONFLICT, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.CONFLICT;
    }
}
