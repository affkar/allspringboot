package com.example.springbatchdemo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

@RestController
public class BatchJobController {

    @Autowired
    private  JobLauncher jobLauncher;

    @Autowired
    private  JobRepository jobRepository;

    @Autowired
    private  Job job;

    @PostMapping(path="/startBatch")
    public void startBatch()  {
        HashMap<String, JobParameter> parameters = new HashMap<>();
        parameters.put("datetime", new JobParameter(new Date()));
        try {
            jobLauncher.run(job, new JobParameters(parameters));
        } catch (JobExecutionAlreadyRunningException |JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }
    }

}
