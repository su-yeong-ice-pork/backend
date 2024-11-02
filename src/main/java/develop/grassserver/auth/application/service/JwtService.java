package develop.grassserver.auth.application.service;


import static develop.grassserver.common.utils.jwt.JwtUtil.TOKEN_PREFIX;

import develop.grassserver.auth.application.dto.TokenDTO;
import develop.grassserver.auth.application.exception.ReauthenticationRequiredException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final RedisService redisService;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenDTO createAllToken(String email) {
        TokenDTO token = TokenDTO.builder()
                .refreshToken(jwtTokenProvider.createRefreshToken(email))
                .accessToken(jwtTokenProvider.createAccessToken(email))
                .build();
        // redisService.saveRefreshToken(email, token.refreshToken());
        return token;
    }

    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken;
        }
        return null;
    }

    public TokenDTO renewTokens(String refreshToken) {
        String email = jwtTokenProvider.getEmailFromToken(refreshToken);
        if (!isValidRefreshToken(email, refreshToken)) {
            throw new ReauthenticationRequiredException();
        }
        return createAllToken(email);
    }

    private Boolean isValidRefreshToken(String email, String refreshToken) {
        String token = redisService.getRefreshToken(email);
        if (!StringUtils.hasText(token)) {
            return false;
        }
        return token.equals(refreshToken);
    }

    public void deleteRefreshToken(String email) {
        redisService.deleteRefreshToken(email);
    }

}
