package develop.grassserver.auth.application.service;


import static develop.grassserver.common.utils.jwt.JwtUtil.ACCESS_TOKEN_EXPIRATION_TIME;
import static develop.grassserver.common.utils.jwt.JwtUtil.ISSUER;
import static develop.grassserver.common.utils.jwt.JwtUtil.REFRESH_TOKEN_EXPIRATION_TIME;
import static develop.grassserver.common.utils.jwt.JwtUtil.TOKEN_BEGIN_INDEX;
import static develop.grassserver.common.utils.jwt.JwtUtil.TOKEN_PREFIX;

import develop.grassserver.auth.presentation.dto.TokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private final RedisService redisService;

    private String createToken(String email, Long expirationTime) {
        return TOKEN_PREFIX + Jwts.builder()
                .subject(email)
                .issuer(ISSUER)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                .signWith(getHashKey())
                .compact();
    }

    private SecretKey getHashKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public TokenDTO createAllToken(String email) {
        return TokenDTO.builder()
                .email(email)
                .refreshToken(createToken(email, REFRESH_TOKEN_EXPIRATION_TIME))
                .accessToken(createToken(email, ACCESS_TOKEN_EXPIRATION_TIME))
                .build();
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getHashKey())
                .build()
                .parseSignedClaims(removeBearerPrefix(token))
                .getPayload();
        return claims.getSubject();
    }

    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken;
        }
        return null;
    }

    private String removeBearerPrefix(String token) {
        if (!token.startsWith(TOKEN_PREFIX)) {
            throw new SecurityException();
        }
        return token.substring(TOKEN_BEGIN_INDEX);
    }

    public void saveRefreshToken(String code, String refreshToken) {
        redisService.saveRefreshToken(code, refreshToken);
    }

    public TokenDTO renewTokens(String refreshToken) {
        String email = getEmailFromToken(refreshToken);
        return createAllToken(email);
    }

    public void deleteRefreshToken(String code) {
        redisService.deleteRefreshToken(code);
    }
}
