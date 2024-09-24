package develop.grassserver.member.auth;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private static final long AUTH_CODE_EXPIRATION_TIME = 60 * 5L;

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveAuthCode(String email, String code) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Duration duration = Duration.ofSeconds(AUTH_CODE_EXPIRATION_TIME);
        valueOperations.set(email, code, duration);
    }
}
