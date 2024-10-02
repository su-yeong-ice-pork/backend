package develop.grassserver.member.login;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(title = "로그인 요청 DTO")
public record LoginRequest(
        @Schema(description = "사용자 이메일", example = "example@pusan.ac.kr")
        @NotBlank(message = "이메일은 필수 항목입니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        String email,

        @Schema(description = "사용자 비밀번호", example = "김김진진우우")
        @NotBlank(message = "비밀번호는 필수 항목입니다.")
        String password,

        boolean autoLogin,

        @NotBlank(message = "기기 식별자는 필수 항목입니다.")
        String code
) {
}
