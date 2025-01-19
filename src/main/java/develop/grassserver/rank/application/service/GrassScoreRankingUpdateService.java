package develop.grassserver.rank.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import develop.grassserver.auth.application.service.RedisService;
import develop.grassserver.grass.infrastructure.repositiory.GrassScoreAggregateQueryRepository;
import develop.grassserver.rank.presentation.dto.IndividualRankingResponse;
import io.lettuce.core.RedisCommandTimeoutException;
import io.lettuce.core.RedisConnectionException;
import io.lettuce.core.RedisException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.QueryTimeoutException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GrassScoreRankingUpdateService {

    private final RedisService redisService;

    private final GrassScoreAggregateQueryRepository grassScoreAggregateQueryRepository;

    @Retryable(
            retryFor = {
                    PersistenceException.class,
                    SQLException.class,
                    SQLTimeoutException.class,
                    QueryTimeoutException.class,
                    JDBCConnectionException.class
            },
            maxAttempts = 5,
            backoff = @Backoff(delay = 5000)
    )
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateGrassAggregateScore() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        grassScoreAggregateQueryRepository.update(yesterday);
    }

    @Retryable(
            retryFor = {
                    RedisConnectionFailureException.class,
                    RedisConnectionException.class,
                    RedisCommandTimeoutException.class,
                    RedisException.class,
                    SocketTimeoutException.class,
                    ConnectException.class
            },
            maxAttempts = 5,
            backoff = @Backoff(delay = 5000)
    )
    public void saveIndividualGrassScoreRanking(IndividualRankingResponse response) {
        try {
            redisService.saveIndividualGrassScoreRanking(response);
        } catch (JsonProcessingException e) {
            log.error("랭킹 JSON 저장 중 오류");
            throw new RedisException("랭킹 JSON 저장 중 오류");
        }
    }
}
