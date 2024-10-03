package develop.grassserver.member.profile.exeption;

import develop.grassserver.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class ImageUploadFailedException extends RuntimeException {

    private static final String MESSAGE = "이미지 업로드에 실패하였습니다.";

    public ImageUploadFailedException() {
        super(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
