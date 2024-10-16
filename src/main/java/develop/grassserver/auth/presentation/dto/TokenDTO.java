package develop.grassserver.auth.presentation.dto;

import lombok.Builder;

@Builder
public record TokenDTO(
        String refreshToken,
        String accessToken,
        String email
) {
}
