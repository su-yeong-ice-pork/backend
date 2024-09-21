package develop.grassserver.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public final class ApiUtils {

    private ApiUtils() {
    }

    public static <T> ApiResult<T> success(T response) {
        return new ApiResult<>(true, response, null);
    }

    public static <T> ApiResult<T> error(HttpStatus status, String message) {
        return new ApiResult<>(false, null, new ApiError(status.value(), message));
    }

    @Getter
    public record ApiResult<T>(boolean success, T response, ApiError error) {
    }

    @Getter
    public record ApiError(int status, String message) {
    }
}
