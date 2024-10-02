package develop.grassserver.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(title = "비밀번호 변경 요청 DTO")
public record ChangePasswordRequest(
        @Schema(description = "멤버 이름", example = "김김진진우우")
        @NotBlank(message = "이름은 필수 항목입니다.")
        String name,

        @Schema(description = "멤버 이메일", example = "example@pusan.ac.kr")
        @NotBlank(message = "이메일은 필수 항목입니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        String email,

        @Schema(description = "새 비밀번호", example = "김김진진우우")
        @NotBlank(message = "비밀번호는 필수 항목입니다.")
        String password
) {
}
