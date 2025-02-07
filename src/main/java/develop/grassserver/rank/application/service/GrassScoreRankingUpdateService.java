package develop.grassserver.rank.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import develop.grassserver.auth.application.service.RedisService;
import develop.grassserver.common.annotation.RedisRetryable;
import develop.grassserver.grass.infrastructure.repositiory.GrassScoreAggregateQueryRepository;
import develop.grassserver.rank.presentation.dto.IndividualRankingResponse;
import develop.grassserver.rank.presentation.dto.StudyRankingResponse;
import io.lettuce.core.RedisException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GrassScoreRankingUpdateService {

    private final RedisService redisService;

    private final GrassScoreAggregateQueryRepository grassScoreAggregateQueryRepository;

    @RedisRetryable
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateGrassAggregateScore() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        grassScoreAggregateQueryRepository.update(yesterday);
    }

    @RedisRetryable
    public void saveIndividualGrassScoreRanking(IndividualRankingResponse response) {
        try {
            redisService.saveIndividualGrassScoreRanking(response);
        } catch (JsonProcessingException e) {
            log.error("랭킹 JSON 저장 중 오류");
            throw new RedisException("랭킹 JSON 저장 중 오류");
        }
    }

    @RedisRetryable
    public void saveStudyRanking(StudyRankingResponse response) {
        try {
            redisService.saveStudyRanking(response);
        } catch (JsonProcessingException e) {
            log.error("스터디 랭킹 JSON 저장 중 오류");
            throw new RedisException("스터디 랭킹 JSON 저장 중 오류");
        }
    }
}
