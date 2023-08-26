package com.sm.demo.springbatch.config;

import com.sm.demo.springbatch.repository.entity.UserEntity;
import com.sm.demo.springbatch.tasks.UserItemProcessor;
import com.sm.demo.springbatch.tasks.UserItemReader;
import com.sm.demo.springbatch.tasks.UserItemWriter;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import java.util.List;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class UserBatchConfig {

    private final UserItemReader userItemReader;
    private final UserItemProcessor userItemProcessor;
    private final UserItemWriter userItemWriter;

    @Bean
    public Job runJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("user-job", jobRepository)
                .flow(step1(jobRepository, transactionManager))
                .end()
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("user-step", jobRepository).
                <List<UserEntity>, Map<Integer, String>>chunk(10, transactionManager)
                .reader(userItemReader)
                .processor(userItemProcessor)
                .writer(userItemWriter)
                .build();
    }
}
