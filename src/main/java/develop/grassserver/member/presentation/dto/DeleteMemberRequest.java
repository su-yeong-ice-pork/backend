package develop.grassserver.member.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record DeleteMemberRequest(

        @Schema(description = "멤버 비밀번호", example = "비밀번호")
        @NotBlank(message = "비밀번호는 필수 항목입니다.")
        String password
) {
}
