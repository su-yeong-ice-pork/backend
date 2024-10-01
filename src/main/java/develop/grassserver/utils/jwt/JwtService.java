package develop.grassserver.utils.jwt;


import static develop.grassserver.utils.jwt.JwtUtil.ACCESS_TOKEN_EXPIRATION_TIME;
import static develop.grassserver.utils.jwt.JwtUtil.ISSUER;
import static develop.grassserver.utils.jwt.JwtUtil.REFRESH_TOKEN_EXPIRATION_TIME;
import static develop.grassserver.utils.jwt.JwtUtil.SECRET_KEY;
import static develop.grassserver.utils.jwt.JwtUtil.TOKEN_BEGIN_INDEX;
import static develop.grassserver.utils.jwt.JwtUtil.TOKEN_PREFIX;

import develop.grassserver.member.auth.RedisService;
import develop.grassserver.member.auth.TokenDTO;
import develop.grassserver.member.exception.ReauthenticationRequiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final RedisService redisService;

    private String createToken(String email, Long expirationTime) {
        return Jwts.builder()
                .subject(email)
                .issuer(ISSUER)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                .signWith(SECRET_KEY)
                .compact();
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
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(removeBearerPrefix(token))
                .getPayload();
        return claims.getSubject();
    }

    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_BEGIN_INDEX);
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

    public TokenDTO renewTokens(String code) {
        String savedRefreshToken = redisService.getRefreshToken(code);
        if (savedRefreshToken == null) {
            throw new ReauthenticationRequiredException();
        }
        String email = getEmailFromToken(savedRefreshToken);
        return createAllToken(email);
    }
}
