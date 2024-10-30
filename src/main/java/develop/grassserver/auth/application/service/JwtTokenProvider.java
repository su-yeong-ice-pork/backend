package develop.grassserver.auth.application.service;

import static develop.grassserver.common.utils.jwt.JwtUtil.ACCESS_TOKEN_EXPIRATION_TIME;
import static develop.grassserver.common.utils.jwt.JwtUtil.ISSUER;
import static develop.grassserver.common.utils.jwt.JwtUtil.REFRESH_TOKEN_EXPIRATION_TIME;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final int TOKEN_BEGIN_INDEX = 7;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public String createAccessToken(String email) {
        return createToken(email, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String createRefreshToken(String email) {
        return createToken(email, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    private String createToken(String email, Long expirationTime) {
        return TOKEN_PREFIX + Jwts.builder()
                .subject(email)
                .issuer(ISSUER)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                .signWith(getHashKey())
                .compact();
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getHashKey())
                .build()
                .parseSignedClaims(removeBearerPrefix(token))
                .getPayload();
        return claims.getSubject();
    }

    private SecretKey getHashKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private String removeBearerPrefix(String token) {
        if (!token.startsWith(TOKEN_PREFIX)) {
            throw new SecurityException();
        }
        return token.substring(TOKEN_BEGIN_INDEX);
    }
}
