package develop.grassserver.utils.jwt;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

public final class JwtUtil {
    public static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(System.getenv("JWT_SECRET").getBytes());
    public static final String ISSUER = "GrassServer";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final int TOKEN_BEGIN_INDEX = 7;
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60 * 24 * 30L; // 30일
    public static final long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 60;

    private JwtUtil() {
    }
}
