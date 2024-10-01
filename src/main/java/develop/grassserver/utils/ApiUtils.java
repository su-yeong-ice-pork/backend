package develop.grassserver.utils;

import org.springframework.http.HttpStatus;

public final class ApiUtils {

    private ApiUtils() {
    }

    public static <T> ApiResult<T> success(T response) {
        return new ApiResult<>(true, response, null);
    }

    public static ApiResult<String> success() {
        return new ApiResult<>(true, "요청이 성공적으로 처리되었습니다.", null);
    }

    public static <T> ApiResult<T> error(HttpStatus status, String message) {
        return new ApiResult<>(false, null, new ApiError(status.value(), message));
    }

    public record ApiResult<T>(boolean success, T response, ApiError error) {
    }

    public record ApiError(int status, String message) {
    }
}
