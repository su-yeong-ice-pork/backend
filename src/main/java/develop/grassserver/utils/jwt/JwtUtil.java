package develop.grassserver.utils.jwt;

import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;

public final class JwtUtil {
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final long expirationSeconds = 3600; // 1시간
    public static final String ISSUER = "GrassServer";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final int TOKEN_BEGIN_INDEX = 7;
    public static final Long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60 * 24 * 30L; // 30일

    private JwtUtil() {
    }
}
