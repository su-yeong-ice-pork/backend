package develop.grassserver.auth.presentation.dto;

import lombok.Builder;

@Builder
public record TokenResponse(
        String refreshToken,
        String accessToken,
        String email
) {
}
