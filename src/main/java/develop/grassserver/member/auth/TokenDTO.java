package develop.grassserver.member.auth;

import lombok.Builder;

@Builder
public record TokenDTO(
        String refreshToken,
        String accessToken,
        String email
) {
}
