package develop.grassserver.common.config;

import develop.grassserver.randomStudy.infrastructure.repository.RandomStudyMemberRepository;
import develop.grassserver.randomStudy.infrastructure.repository.RandomStudyRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final RandomStudyRepository randomStudyRepository;
    private final RandomStudyMemberRepository randomStudyMemberRepository;

    @Bean
    public Job randomStudyMatchingJob(JobRepository jobRepository, Step matchingStep) {
        return new JobBuilder("randomStudyMatchingJob", jobRepository)
                .start(matchingStep)
                .build();
    }
}
