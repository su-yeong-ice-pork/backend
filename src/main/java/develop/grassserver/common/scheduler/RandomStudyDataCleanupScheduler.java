package develop.grassserver.common.scheduler;

import develop.grassserver.common.annotation.Scheduler;
import develop.grassserver.common.annotation.SchedulerName;
import develop.grassserver.randomStudy.application.service.RandomStudyDataCleanupService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Scheduler
@RequiredArgsConstructor
public class RandomStudyDataCleanupScheduler {

    private final RandomStudyDataCleanupService randomStudyDataCleanupService;

    @Scheduled(cron = "0 0 5 * * ?")
    @SchedulerName("랜덤 스터디 삭제 스케줄러")
    public void cleanUpOldData() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        int deletedApplications = randomStudyDataCleanupService.deleteOldRandomStudyApplications(yesterday);
        int deletedStudies = randomStudyDataCleanupService.deleteOldRandomStudies();
        int deletedMembers = randomStudyDataCleanupService.deleteOldRandomStudyMembers();

        log.info("전일 데이터 soft-delete 완료 - Applications: {}, Studies: {}, Members: {}",
                deletedApplications, deletedStudies, deletedMembers);
    }
}
