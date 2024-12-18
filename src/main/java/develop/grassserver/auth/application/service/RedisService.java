package develop.grassserver.auth.application.service;

import develop.grassserver.auth.application.exception.ExpirationAuthCodeException;
import develop.grassserver.auth.application.exception.IncorrectAuthCodeException;
import develop.grassserver.auth.application.valid.AuthValidator;
import develop.grassserver.common.utils.jwt.JwtUtil;
import develop.grassserver.member.presentation.dto.CheckAuthCodeRequest;
import develop.grassserver.study.application.dto.StudyRankingDTO;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
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

    public void saveIndividualGrassScoreRanking(List<Long> grassScoreAggregateIds) {
        redisTemplate.delete(INDIVIDUAL_GRASS_SCORE_RANKING_KEY);
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        for (Long id : grassScoreAggregateIds) {
            listOperations.rightPush(INDIVIDUAL_GRASS_SCORE_RANKING_KEY, String.valueOf(id));
        }
    }

    public List<Long> getIndividualGrassScoreRanking() {
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        List<Object> readRankingList = listOperations.range(INDIVIDUAL_GRASS_SCORE_RANKING_KEY, 0, -1);
        if (Objects.isNull(readRankingList) || readRankingList.isEmpty()) {
            return List.of();
        }
        return readRankingList.stream()
                .mapToLong(ranking -> Long.parseLong((String) ranking))
                .boxed()
                .toList();
    }

    public void saveStudyRanking(List<StudyRankingDTO> studyRankings) {
        for (int i = 1; i <= studyRankings.size(); i++) {
            String key = STUDY_RANKING_KEY + i;
            redisTemplate.delete(key);

            ListOperations<String, Object> listOperations = redisTemplate.opsForList();
            StudyRankingDTO dto = studyRankings.get(i - 1);
            listOperations.rightPush(key, dto.studyName());
            listOperations.rightPush(key, dto.memberCount());
            listOperations.rightPush(key, dto.totalStudyTime());
        }
    }

    public List<StudyRankingDTO> getStudyRanking() {
        return redisTemplate.keys(STUDY_RANKING_KEY + "*").stream()
                .sorted()
                .map(key -> {
                    List<Object> values = redisTemplate.opsForList().range(key, 0, -1);

                    String studyName = (String) values.get(0);
                    int memberCount = (Integer) values.get(1);
                    Long totalStudyTime = (Long) values.get(2);

                    return new StudyRankingDTO(studyName, memberCount, totalStudyTime);
                })
                .collect(Collectors.toList());
    }
}
