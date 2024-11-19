package develop.grassserver.friend.presentation.dto;

import jakarta.validation.constraints.Min;

public record RequestFriendRequest(

        @Min(value = 1, message = "잘못된 멤버 ID 입니다,")
        Long memberId
) {
}
