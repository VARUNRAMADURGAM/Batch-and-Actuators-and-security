package com.example.batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class BatchController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importUserJob;

    @GetMapping("/run")
    public ResponseEntity<String> runBatchJob() {
        try {
        	JobParameters jobParams = new JobParametersBuilder().addLong("Start-AT", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(importUserJob, jobParams);
            return ResponseEntity.ok("Batch job has been triggered");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Batch job failed to start");
        }
    }
}