package com.hendisantika.springbatchdemo.controller;

import com.hendisantika.springbatchdemo.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : springbatch-demo
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-07-10
 * Time: 05:40
 */
@RestController
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Qualifier("job1")
    @Autowired
    private Job job1;

    @Qualifier("job2")
    @Autowired
    private Job job2;

    @Autowired
    private VehicleService vechileService;

    @GetMapping("/launchJob1")
    public String kickOffJob() {

        try {

            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(job1, jobParameters);

        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return "Done";

    }

    @GetMapping("/launchJob2")
    public String kickOffJob2() {

        try {

            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(job2, jobParameters);


        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return "Done";

    }

}
