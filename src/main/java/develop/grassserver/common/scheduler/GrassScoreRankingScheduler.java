package develop.grassserver.common.scheduler;

import develop.grassserver.common.annotation.Scheduler;
import develop.grassserver.common.annotation.SchedulerName;
import develop.grassserver.grass.application.service.GrassScoreAggregateService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@Scheduler
@RequiredArgsConstructor
public class GrassScoreRankingScheduler {

    private final GrassScoreAggregateService grassScoreAggregateService;

    @Scheduled(cron = "0 6 16 * * ?")
    @SchedulerName("랭킹 계산 스케줄러")
    public void calculateRanking() {
        grassScoreAggregateService.calculateGrassScoreRanking();
        throw new RuntimeException("테스트 오류");
    }
}
