package develop.grassserver.utils;

import io.swagger.v3.oas.annotations.media.Schema;
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

    public record ApiResult<T>(
            @Schema(description = "성공 여부", example = "true")
            boolean success,

            @Schema(description = "응답 내용", example = "null")
            T response,

            @Schema(description = "에러 여부", example = "{ status: 에러코드, message: 에러메시지 }")
            ApiError error
    ) {
    }

    public record ApiError(
            @Schema(description = "에러 코드", example = "400")
            int status,

            @Schema(description = "에러 메시지", example = "다시 입력하셈")
            String message
    ) {
    }
}
