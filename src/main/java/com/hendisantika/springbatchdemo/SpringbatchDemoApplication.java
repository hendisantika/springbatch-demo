package com.hendisantika.springbatchdemo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringbatchDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbatchDemoApplication.class, args);
    }

}
