package develop.grassserver.grass.application.service;

import develop.grassserver.grass.domain.entity.GrassScoreAggregate;
import develop.grassserver.grass.infrastructure.repositiory.GrassScoreAggregateQueryRepository;
import develop.grassserver.rank.application.service.GrassScoreRankingUpdateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GrassScoreAggregateService {

    private final GrassScoreRankingUpdateService grassScoreRankingUpdateService;

    private final GrassScoreAggregateQueryRepository aggregateQueryRepository;

    public void calculateGrassScoreRanking() {
        List<GrassScoreAggregate> grassScoreAggregates = aggregateQueryRepository.getTopMembers();

        try {
            grassScoreRankingUpdateService.updateGrassAggregateScore();
        } catch (Exception exception) {
            log.error("출석 점수 UPDATE 실패 = {}", exception.getMessage());
        }

        try {
            grassScoreRankingUpdateService.saveIndividualGrassScoreRanking(grassScoreAggregates);
            log.info("랭킹 저장 성공");
        } catch (Exception exception) {
            log.error("랭킹 저장 실패 = {}", exception.getMessage());
        }
    }
}
