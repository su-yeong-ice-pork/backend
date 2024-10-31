package develop.grassserver.profile.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UpdateProfileMessageRequest(
        @Schema(description = "변경 메시지", example = "변경할 메시지")
        @NotBlank(message = "메시지는 필수 항목입니다.")
        String message
) {
}
