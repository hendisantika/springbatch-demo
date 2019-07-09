package com.hendisantika.springbatchdemo.config;

import com.hendisantika.springbatchdemo.model.Vehicle;
import com.hendisantika.springbatchdemo.steps.listener.JobCompletionNotificationListener1;
import com.hendisantika.springbatchdemo.steps.listener.JobCompletionNotificationListener2;
import com.hendisantika.springbatchdemo.steps.processor.VehicleProcessor;
import com.hendisantika.springbatchdemo.steps.processor.WordProcessor;
import com.hendisantika.springbatchdemo.steps.reader.WordReader;
import com.hendisantika.springbatchdemo.steps.writer.VehicleWriter;
import com.hendisantika.springbatchdemo.steps.writer.WordWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by IntelliJ IDEA.
 * Project : springbatch-demo
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-07-10
 * Time: 05:38
 */
@Configuration
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private VehicleProcessor vechileProcessor;

    @Autowired
    private VehicleWriter vechileWriter;

    @Autowired
    private WordReader wordReader;

    @Autowired
    private WordProcessor wordProcessor;

    @Autowired
    private WordWriter wordWriter;


    @Bean(name = "job1")
    public Job job1(JobCompletionNotificationListener1 listner) {
        return jobs.get("job1")
                .incrementer(new RunIdIncrementer())
                .listener(listner)
                .flow(step1()) //execute a step or sequence of steps
                .next(step2())
                .end()
                .build();
    }

    @Bean(name = "job2")
    public Job job2(JobCompletionNotificationListener2 listner) {
        return jobs.get("job2")
                .incrementer(new RunIdIncrementer())
                .listener(listner)
                .start(step2())   //for only one step
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Vehicle, Vehicle>chunk(10)
                .reader(reader())
                .processor(vechileProcessor)
                .writer(vechileWriter)
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .<String, String>chunk(3)
                .reader(wordReader)
                .processor(wordProcessor)
                .writer(wordWriter)
                .build();
    }


    @Bean
    public FlatFileItemReader<Vehicle> reader() {
        return new FlatFileItemReaderBuilder<Vehicle>().name("vechileItemReader").resource(new ClassPathResource("vechiles.csv"))
                .delimited().names(new String[]{"type", "model", "built"})
                .linesToSkip(1)  //skipping row one from csv file
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Vehicle>() {

                    {
                        setTargetType(Vehicle.class);
                    }
                }).build();
    }

}