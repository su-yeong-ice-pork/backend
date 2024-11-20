package develop.grassserver.auth.application.service;

import develop.grassserver.auth.application.exception.ExpirationAuthCodeException;
import develop.grassserver.auth.application.exception.IncorrectAuthCodeException;
import develop.grassserver.auth.application.valid.AuthValidator;
import develop.grassserver.common.utils.jwt.JwtUtil;
import develop.grassserver.member.presentation.dto.CheckAuthCodeRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class RedisService {

    private static final String REFRESH_TOKEN_PREFIX = "refresh-token";
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
        if (!StringUtils.hasText(code)) {
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

    public void saveRefreshToken(String code, String refreshToken) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Duration duration = Duration.ofSeconds(JwtUtil.REFRESH_TOKEN_EXPIRATION_TIME);
        valueOperations.set(getRefreshCodeKey(code), refreshToken, duration);
    }

    public String getRefreshToken(String code) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return (String) valueOperations.get(getRefreshCodeKey(code));
    }

    public void deleteRefreshToken(String code) {
        redisTemplate.delete(getRefreshCodeKey(code));
    }

    private String getRefreshCodeKey(String code) {
        return REFRESH_TOKEN_PREFIX + code;
    }

    public void saveStudyStatus(Long memberId) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("studying-" + memberId, LocalDateTime.now().toString());
    }
}
