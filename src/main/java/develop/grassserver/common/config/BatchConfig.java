package develop.grassserver.common.config;

import develop.grassserver.randomStudy.domain.entity.RandomStudyApplication;
import develop.grassserver.randomStudy.infrastructure.repository.RandomStudyMemberRepository;
import develop.grassserver.randomStudy.infrastructure.repository.RandomStudyRepository;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final RandomStudyRepository randomStudyRepository;
    private final RandomStudyMemberRepository randomStudyMemberRepository;

    @Bean
    public Job randomStudyMatchingJob(Step matchingStep) {
        return new JobBuilder("randomStudyMatchingJob", jobRepository)
                .start(matchingStep)
                .build();
    }

    @Bean
    public Step matchingStep() {
        return new StepBuilder("matchingStep", jobRepository)
                .<RandomStudyApplication, RandomStudyApplication>chunk(100, transactionManager)
                .reader(applicationItemReader())
                .processor(applicationItemProcessor())
                .writer(applicationItemWriter())
                .build();

    }

    @Bean
    @JobScope
    public JpaPagingItemReader<RandomStudyApplication> applicationItemReader() {
        return new JpaPagingItemReaderBuilder<RandomStudyApplication>()
                .name("applicationItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString(
                        "SELECT rsa FROM RandomStudyApplication rsa WHERE rsa.attendanceDate = :today ORDER BY rsa.attendanceTime")
                .parameterValues(Map.of("today", LocalDate.now()))
                .pageSize(100)
                .build();
    }

    @Bean
    public ItemProcessor<RandomStudyApplication, RandomStudyApplication> applicationItemProcessor() {
        return item -> item;
    }

    @Bean
    public ItemWriter<RandomStudyApplication> applicationItemWriter() {
        return new RandomStudyApplicationItemWriter(randomStudyRepository, randomStudyMemberRepository);
    }
}
