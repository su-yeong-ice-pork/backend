package develop.grassserver.common.scheduler;

import develop.grassserver.common.annotation.Scheduler;
import develop.grassserver.grass.application.service.GrassScoreAggregateService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@Scheduler
@RequiredArgsConstructor
public class GrassScoreRankingScheduler {

    private final GrassScoreAggregateService grassScoreAggregateService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void calculateRanking() {
        grassScoreAggregateService.calculateGrassScoreRanking();
    }
}
