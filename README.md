# SpringBatch

Architecture
![overallarchitecture](https://user-images.githubusercontent.com/14364853/44007041-559865f4-9e5c-11e8-8d6b-5895a3b5b7e0.png)

Spring Batch is an open source framework for batch processing. It is a lightweight, comprehensive solution designed to enable the development of robust batch applications,which are often found in modern enterprise systems
   --wiki
Technically batch reads the data from the source, processed the read data according to the business requirements and finally writes the processed data to the respective destination.

UseCase :: Claim process in the insurance domain.

<h4> System Requirement</h4>
1.Spring Boot
2.Java 8
3. Any preferred Id

<h4>ResourceUrl<h4>
<pre>
http://localhost:8080/h2 >> console of h2 database.
GET http://localhost:8080/launchJob1 >> To launch Job1.
GET http://localhost:8080/launchJob2 >> To launch Job2.
Delete GET http://localhost:8080 >> To delete all the inserted record.
</pre>

<h4>About Project<h4>
<h6>pom.xml</h6>
   
 ![pom](https://user-images.githubusercontent.com/14364853/44007085-48487dca-9e5d-11e8-912b-e529b828982d.png)

<h6>Structure</h6>

 ![projectstructure](https://user-images.githubusercontent.com/14364853/44007123-e1265706-9e5d-11e8-9c77-1736c0f08b6c.png)

<h6>Description</6>
This is the example of the batch application which runs at 8080 . I have configured H2 database and JPA for persistence. The purpose of the application is to read csv file, process it and write to the H2 database via spring batch. In order to stop the default behaviour of the spring batch in the properties file I have added <b>spring.batch.job.enabled=false</b> which means I will launch the batch job manually via REST . 

Enabling Batch processing using @EnableBatchProcessing

    @EnableBatchProcessing
    @SpringBootApplication
    public class BatchSpringRunner {

    public static void main(String[] args) {
        SpringApplication.run(BatchSpringRunner.class, args);
    }
}

            
We need to create BatchConfig.java which is in our source code.  Here Jobs are created by JobBuilderFactory and Steps are created from StepBuilderFactory . Every Step has  three parts ItemReader ,ItemProcessor and ItemWriter as well as we can added listener as I have added in my case. In my case I have two jobs with bean qualifier name job1 and job2 .If we have multiple steps we have to use flow() and if only one step we can use start() as well.

Lets take Job1 for explanation

    ...

    @Autowired
    private JobBuilderFactory jobs;
    
    @Bean(name ="job1")
    public Job job1(JobCompletionNotificationListener1 listner){
        return jobs.get("job1")
                .incrementer(new RunIdIncrementer())
                .listener(listner)
                .flow(step1()) //execute a step or sequence of steps
                .next(step2())
                .end()
                .build();
    }
    ...  
  
In job1 we are passing JobCompletionNotificationListener1 which extends JobExecutionListenerSupport . The method afterJob gets triggered after the completion of step. <b>Note: Step has three parts Read,Process and Write</b>.
Incrementer is a id that we assign for every run and we are using the default in our case. job1 has multiple steps step1 and step2.

step1

    ...
    
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    private VechileProcessor vechileProcessor;
    
    @Autowired
    private VechileWriter vechileWriter;
    
    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1")
                .<Vechile,Vechile>chunk(10)
                .reader(reader())
                .processor(vechileProcessor)
                .writer(vechileWriter)
                .build();
    }
    ...

In step we have batch chunk of size 10 and input/output of type vechile. We have a reader() method whose sole purpose is to read the vechile.csv file and convert to the vechile entity.

<h6>CSV</h6>

 ![input-csv](https://user-images.githubusercontent.com/14364853/44007127-0137403c-9e5e-11e8-9fe7-b192e419416e.png)

    @Bean
    public FlatFileItemReader<Vechile> reader() {
        return new     FlatFileItemReaderBuilder<Vechile>().name("vechileItemReader").resource(new ClassPathResource("vechiles.csv"))
                .delimited().names(new String[] {"type","model","built" })
                .linesToSkip(1)  //skipping row one from csv file
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Vechile>(){
                    
                    {
                        setTargetType(Vechile.class);
                    }
                }).build();
    }
    
 We are using the FlatFileItemReader which reads the csv from the ClassPathResource. After reading the csv , item is converted to targetType.i.e Vechile.java
 
 vechileProcessor is the intermediate operation in which read data is transformed according to business requirements. In our case 
 
    @Component
    public class VechileProcessor implements ItemProcessor<Vechile, Vechile> {
    
    private  static long id =0;
    
    @Override
    public Vechile process(Vechile vechile) throws Exception {
        
        if(Integer.parseInt(vechile.getBuilt())>1998){
            final String model = firstIndexCapital(vechile.getModel()).toString();
            vechile = new Vechile(++id,vechile.getType(),model,vechile.getBuilt());
        }
        
        return vechile;
    }
    
    public StringBuilder firstIndexCapital(String word){
        StringBuilder sb = new StringBuilder();
        sb.append(word.charAt(0)+"".toUpperCase());
        sb.append(word.subSequence(1, word.length()));
        return sb;
    }
    
we are filtering out the vechile whose built is greater than 1998 and we change the First index to model to uppercase as well as added the id because if we see in the entity our id is primarykey and in our csv we don't have ids. 

After the completion of the processor we have vechileProcessor.

vechileProcessor

    @Component
     public class VechileWriter implements ItemWriter<Vechile>{
    
    @Autowired
    private VechileRepository vechileRepository;

    @Override
    public void write(List<? extends Vechile> vechiles) throws Exception {
        this.vechileRepository.saveAll(vechiles);
        
    }
    
Here we have injected vechileRepository and called the saveAll() to persist the list of vechiles. After vechileWriter done, JobCompletionNotificationListner1 is triggered.After the completion of step1, step2 get triggered and follow the same steps accordingly.


<h4>Launching Job</h4>

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
    private VehileService vechileService;
    

    @GetMapping("/launchJob1")
    public String kickOffJob() {

        try {
            
            JobParameters jobParameters = new JobParametersBuilder().addLong("time",System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(job1,jobParameters);
        
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return "Done";

    }
    ....
    
<h6>Before Job Launch</h6>

 ![h2-before-launching-job](https://user-images.githubusercontent.com/14364853/44007132-372f4978-9e5e-11e8-8043-768739bd43c7.png)
    
Here we have autowired JobLauncher. Whenever http://localhost:8080/launchJob1 is hit job1 get triggered. run(..) Start a job execution for the given Job and JobParameters.

<h6>After Job Launch</h6>

 ![h2-after-launching-job](https://user-images.githubusercontent.com/14364853/44007145-7639c3be-9e5e-11e8-9b67-c18f3047aa39.png)


<h4>References</h4>
Various Internet sources
    






