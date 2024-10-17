package develop.grassserver.member.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CheckAuthCodeRequest(

        @Schema(description = "멤버 이메일", example = "example@pusan.ac.kr")
        @NotBlank(message = "이메일은 필수 항목입니다.")
        String email,

        @Schema(description = "인증코드", example = "123456")
        @NotBlank(message = "인증코드는 필수 항목입니다.")
        String code
) {
}
