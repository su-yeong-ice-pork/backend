package develop.grassserver.common.scheduler;

import develop.grassserver.common.annotation.Scheduler;
import develop.grassserver.common.annotation.SchedulerName;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Scheduler
@RequiredArgsConstructor
public class RandomStudyMatchingScheduler {

    private final JobLauncher jobLauncher;
    private final Job randomStudyMatchingJob;

    @Scheduled(cron = "0 30 5 * * ?")
    @SchedulerName("랜덤 스터디 매칭 스케줄러")
    public void runJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("runDate", new Date())
                .toJobParameters();

        try {
            jobLauncher.run(randomStudyMatchingJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException |
                 JobRestartException |
                 JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            throw new RuntimeException("Job 실행 중 오류 발생", e);
        }
    }

}
