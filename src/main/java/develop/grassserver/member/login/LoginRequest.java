package develop.grassserver.member.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "이메일은 필수 항목입니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수 항목입니다.")
        String password,

        boolean autoLogin,

        @NotBlank(message = "기기 식별자는 필수 항목입니다.")
        String code
) {
}
