package com.sm.demo.springbatch.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Slf4j
@AllArgsConstructor
public class JobScheduler {
    private final JobLauncher jobLauncher;
    private final Job job;

    @Scheduled(fixedRate = 10000, initialDelay = 10000)//execute job on 10 seconds interval
    public void scheduleUserJob() throws Exception {
        log.info("User Batch job starting");
        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDateTime("startAt", LocalDateTime.now()).toJobParameters();
        jobLauncher.run(job, jobParameters);
    }
}
