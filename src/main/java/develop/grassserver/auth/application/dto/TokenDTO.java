package develop.grassserver.auth.application.dto;

import lombok.Builder;

@Builder
public record TokenDTO(
        String refreshToken,
        String accessToken
) {
}
