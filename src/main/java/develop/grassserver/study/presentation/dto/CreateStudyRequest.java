package develop.grassserver.study.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateStudyRequest(
        @Schema(description = "스터디 이름", example = "우주 최강 토스 스터디")
        @NotBlank(message = "스터디 이름은 필수 항목입니다.")
        String name,
        String goalMessage,
        long goalTime
) {
}
