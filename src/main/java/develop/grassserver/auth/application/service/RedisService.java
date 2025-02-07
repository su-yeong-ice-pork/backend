package develop.grassserver.auth.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import develop.grassserver.auth.application.exception.ExpirationAuthCodeException;
import develop.grassserver.auth.application.exception.IncorrectAuthCodeException;
import develop.grassserver.auth.application.valid.AuthValidator;
import develop.grassserver.common.utils.jwt.JwtUtil;
import develop.grassserver.member.presentation.dto.CheckAuthCodeRequest;
import develop.grassserver.rank.presentation.dto.IndividualRankingResponse;
import develop.grassserver.rank.presentation.dto.StudyRankingResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
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
    private static final String STUDY_STATUS_KEY_PREFIX = "studying-";
    private static final String INDIVIDUAL_GRASS_SCORE_RANKING_KEY = "grass_score_ranking";
    private static final String STUDY_RANKING_KEY = "study_ranking";
    private static final long RANKING_EXPIRATION_TIME = 24 * 60 * 60L;

    private final ObjectMapper objectMapper;
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
        valueOperations.set(STUDY_STATUS_KEY_PREFIX + memberId, LocalDateTime.now().toString());
    }

    public Map<Long, Boolean> getOthersStudyStatus(List<Long> friendIds) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return friendIds.stream()
                .collect(
                        Collectors.toMap(
                                friendId -> friendId,
                                friendId -> valueOperations.get(STUDY_STATUS_KEY_PREFIX + friendId) != null
                        )
                );
    }

    public void deleteMemberStudyStatus(Long memberId) {
        redisTemplate.delete(STUDY_STATUS_KEY_PREFIX + memberId);
    }

    public void saveIndividualGrassScoreRanking(IndividualRankingResponse response) throws JsonProcessingException {
        saveAsJson(response, INDIVIDUAL_GRASS_SCORE_RANKING_KEY);
    }

    public IndividualRankingResponse getIndividualGrassScoreRanking() throws JsonProcessingException {
        return getAsObject(INDIVIDUAL_GRASS_SCORE_RANKING_KEY, IndividualRankingResponse.class);
    }

    public void saveStudyRanking(StudyRankingResponse response) throws JsonProcessingException {
        saveAsJson(response, STUDY_RANKING_KEY);
    }

    private void saveAsJson(Object object, String key) throws JsonProcessingException {
        String rankingJSON = objectMapper.writeValueAsString(object);
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, rankingJSON, RANKING_EXPIRATION_TIME);
    }

    public StudyRankingResponse getStudyRanking() throws JsonProcessingException {
        return getAsObject(STUDY_RANKING_KEY, StudyRankingResponse.class);
    }

    private <T> T getAsObject(String key, Class<T> responseType) throws JsonProcessingException {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String rankingJSON = ((String) Objects.requireNonNull(valueOperations.get(key)))
                .replaceAll("\\p{Cntrl}", "");
        return objectMapper.readValue(rankingJSON, responseType);
    }
}
