package develop.grassserver.member.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(title = "멤버 인증 요청 DTO")
public record MemberAuthRequest(
        @Schema(description = "멤버 인증 이름", example = "김김진진우우")
        @NotBlank(message = "이름은 필수 항목입니다.")
        String name,

        @Schema(description = "멤버 인증 이메일", example = "example@pusan.ac.kr")
        @NotBlank(message = "이메일은 필수 항목입니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        String email
) {
}
