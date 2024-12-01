package develop.grassserver.friend.presentation.dto;

import jakarta.validation.constraints.Min;

public record SendCheerUpEmojiRequest(

        @Min(value = 0, message = "잘못된 이모지 번호입니다.")
        int emojiNumber
) {
}
