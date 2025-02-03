package develop.grassserver.grass.application.service;

import develop.grassserver.auth.application.service.RedisService;
import develop.grassserver.common.BaseEntity;
import develop.grassserver.grass.domain.entity.GrassScoreAggregate;
import develop.grassserver.grass.infrastructure.repositiory.GrassScoreAggregateQueryRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GrassScoreAggregateService {

    private final RedisService redisService;
    private final GrassScoreAggregateQueryRepository aggregateQueryRepository;

    @Transactional
    public void calculateGrassScoreRanking() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        aggregateQueryRepository.update(yesterday);

        List<GrassScoreAggregate> grassScoreAggregates = aggregateQueryRepository.getTopMembers();

        redisService.saveIndividualGrassScoreRanking(getTopGrassScoreAggregates(grassScoreAggregates));
    }

    private List<Long> getTopGrassScoreAggregates(List<GrassScoreAggregate> grassScoreAggregates) {
        return grassScoreAggregates.stream()
                .mapToLong(BaseEntity::getId)
                .boxed()
                .toList();
    }
}
