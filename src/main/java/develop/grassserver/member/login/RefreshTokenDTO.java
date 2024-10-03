package develop.grassserver.member.login;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record RefreshTokenDTO(
        @Schema(description = "리프레시 토큰")
        @NotBlank(message = "리프레시 토큰은 필수 항목입니다.")
        String refreshToken
) {
}
