package develop.grassserver.friend.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record SendCheerUpMessageRequest(

        @NotBlank(message = "응원 메시지는 필수 항목입니다.")
        String message
) {
}
