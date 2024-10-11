package com.example.batch.scheduling;

import java.util.TimeZone;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importUserJob;

    @Scheduled(cron = "0 20 18 * * *", zone = "Asia/Kolkata") 
    public void runBatchJob() {
        try {
        	//System.out.println("Default Time Zone: " + TimeZone.getDefault().getID());
        	JobParameters jobParams = new JobParametersBuilder().addLong("Start-AT", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(importUserJob, jobParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
