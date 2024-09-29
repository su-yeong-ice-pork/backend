package develop.grassserver.member.auth;

import develop.grassserver.member.auth.exception.ExpirationAuthCodeException;
import develop.grassserver.member.auth.exception.IncorrectAuthCodeException;
import develop.grassserver.member.check.dto.CheckAuthCodeRequest;
import develop.grassserver.utils.jwt.JwtUtil;
import java.time.Duration;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    public static final String REFRESH_TOKEN_PREFIX = "refresh-token";
    private static final long AUTH_CODE_EXPIRATION_TIME = 60 * 5L;
    private final RedisTemplate<String, Object> redisTemplate;

    public void saveAuthCode(String email, String code) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Duration duration = Duration.ofSeconds(AUTH_CODE_EXPIRATION_TIME);
        valueOperations.set(email, code, duration);
    }

    public void checkAuthCode(CheckAuthCodeRequest request) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String email = request.email();
        String code = (String) valueOperations.get(email);
        if (Objects.isNull(code)) {
            throw new ExpirationAuthCodeException("인증코드가 만료되었습니다. 인증코드를 재발급해주세요.");
        }
        if (!AuthValidator.isCorrectAuthCode(code, request)) {
            throw new IncorrectAuthCodeException("인증코드가 일치하지 않습니다.");
        }
        deleteAuthCode(email);
    }

    private void deleteAuthCode(String email) {
        redisTemplate.delete(email);
    }

    public void saveRefreshToken(String email, String refreshToken) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Duration duration = Duration.ofSeconds(JwtUtil.REFRESH_TOKEN_EXPIRATION_TIME);
        if (!Objects.isNull(getRefreshToken(email))) {
            deleteRefreshToken(email);
        }
        valueOperations.set(getRefreshCodeKey(email), refreshToken, duration);
    }

    public String getRefreshToken(String email) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return (String) valueOperations.get(getRefreshCodeKey(email));
    }

    public void deleteRefreshToken(String email) {
        redisTemplate.delete(getRefreshCodeKey(email));
    }

    private String getRefreshCodeKey(String email) {
        return REFRESH_TOKEN_PREFIX + email;
    }
}
