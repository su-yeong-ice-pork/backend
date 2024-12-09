package develop.grassserver.common.scheduler;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RandomStudyMatchingScheduler {
    
    private final JobLauncher jobLauncher;
    private final Job randomStudyMatchingJob;

    @Scheduled(cron = "0 30 5 * * ?")
    public void runJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("runDate", new Date())
                    .toJobParameters();

            jobLauncher.run(randomStudyMatchingJob, jobParameters);
        } catch (Exception e) {
            log.error("랜덤 스터디 매칭 스케줄러 에러 발생 " + e.getMessage());
        }
    }

//    로컬에서 바로 테스트 해보고 싶을 때 실행
//    @PostConstruct
//    public void runOnStartup() {
//        runJob();
//    }
}
