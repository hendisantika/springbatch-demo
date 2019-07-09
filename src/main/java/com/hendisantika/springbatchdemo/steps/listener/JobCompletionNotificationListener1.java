package com.hendisantika.springbatchdemo.steps.listener;

import com.hendisantika.springbatchdemo.model.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * Project : springbatch-demo
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-07-10
 * Time: 05:36
 */
@Component
public class JobCompletionNotificationListener1 extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener1.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {

            log.info("!!! Job Done");
            final String queryStr = "Select * from Vechile";
            jdbcTemplate.query(queryStr,
                    (rs, row) -> new Vehicle(
                            Long.parseLong(rs.getString(1)),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4))
            ).forEach(person -> log.info("Found <" + person + "> in the database."));
        }
        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.error("Job failed");
        }
    }


}