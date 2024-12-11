package develop.grassserver.common.scheduler;

import develop.grassserver.common.annotation.Scheduler;
import develop.grassserver.grass.application.service.GrassRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@Scheduler
@RequiredArgsConstructor
public class GrassScoreRankingScheduler {

    private final GrassRankingService grassRankingService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void calculateRanking() {

    }
}
