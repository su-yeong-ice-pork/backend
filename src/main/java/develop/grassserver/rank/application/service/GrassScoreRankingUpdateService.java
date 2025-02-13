package develop.grassserver.rank.application.service;

import develop.grassserver.auth.application.service.RedisService;
import develop.grassserver.common.annotation.RDBRetryable;
import develop.grassserver.common.annotation.RedisRetryable;
import develop.grassserver.grass.infrastructure.repositiory.GrassScoreAggregateQueryRepository;
import develop.grassserver.rank.presentation.dto.IndividualRankingResponse;
import develop.grassserver.rank.presentation.dto.MajorRankingResponse;
import develop.grassserver.rank.presentation.dto.StudyRankingResponse;
import io.lettuce.core.RedisException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.function.ThrowingConsumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class GrassScoreRankingUpdateService {

    private final RedisService redisService;

    private final GrassScoreAggregateQueryRepository grassScoreAggregateQueryRepository;

    @RDBRetryable
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateGrassAggregateScore() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        grassScoreAggregateQueryRepository.update(yesterday);
    }

    @RedisRetryable
    public void saveIndividualGrassScoreRanking(IndividualRankingResponse response) {
        saveRanking(
                response,
                redisService::saveIndividualGrassScoreRanking,
                "랭킹 JSON 저장 중 오류"
        );
    }

    @RedisRetryable
    public void saveStudyRanking(StudyRankingResponse response) {
        saveRanking(
                response,
                redisService::saveStudyRanking,
                "스터디 랭킹 JSON 저장 중 오류"
        );
    }

    @RedisRetryable
    public void saveMajorRanking(MajorRankingResponse response) {
        saveRanking(
                response,
                redisService::saveMajorRanking,
                "학과 랭킹 JSON 저장 중 오류"
        );
    }

    private <T> void saveRanking(
            T response,
            ThrowingConsumer<T> saveFunction,
            String errorMessage
    ) {
        try {
            saveFunction.accept(response);
        } catch (Exception e) {
            log.error(errorMessage, e);
            throw new RedisException(errorMessage);
        }
    }
}
