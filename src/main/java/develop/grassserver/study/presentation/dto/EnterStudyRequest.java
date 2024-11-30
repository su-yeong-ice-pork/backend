package develop.grassserver.study.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record EnterStudyRequest(
        @Schema(description = "스터디 초대코드", example = "GSDS234")
        @NotBlank(message = "초대코드는 필수 항목입니다.")
        String inviteCode) {
}
