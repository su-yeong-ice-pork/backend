package develop.grassserver.grass.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "공부시간 저장 요청 DTO")
public record StudyTimeRequest(
        @Schema(description = "오늘 공부시간", example = "08:32:11")
        @NotBlank(message = "공부시간은 필수 항목입니다.")
        String todayStudyTime
) {
}
