package develop.grassserver.study.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EnterStudyRequest(
        @Schema(description = "스터디 초대코드", example = "GSDS34")
        @NotBlank(message = "초대코드는 필수 항목입니다.")
        @Size(min = 6, max = 6, message = "초대코드는 정확히 6자리여야 합니다.")
        String inviteCode) {
}
