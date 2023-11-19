package com.example.demospringbatchexportfile.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor()); // Optional: Set a TaskExecutor
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    @Bean
    public JobRepository jobRepository(PlatformTransactionManager transactionManager) throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setIsolationLevelForCreate("ISOLATION_REPEATABLE_READ");
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public ItemReader<HouseHold> itemReader() {
        JdbcCursorItemReader<HouseHold> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT * FROM house_holds");
        reader.setRowMapper(new BeanPropertyRowMapper<>(HouseHold.class));
        return reader;
    }

    @Bean
    public ItemWriter<HouseHold> itemWriter() {
        FlatFileItemWriter<HouseHold> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("output.csv"));
        writer.setLineAggregator(new DelimitedLineAggregator<HouseHold>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<HouseHold>() {{
                setNames(new String[]{"id", "username", "email", "addressDetail", "ward",
                        "district", "province", "quantity", "amount", "productName"});
            }});
        }});
        return writer;
    }

    @Bean
    public Step exportStep(ItemReader<HouseHold> itemReader, ItemWriter<HouseHold> itemWriter) {
        return stepBuilderFactory.get("exportStep")
                .<HouseHold, HouseHold>chunk(100)
                .reader(itemReader)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public Job exportJob(JobBuilderFactory jobBuilderFactory, Step exportStep) {
        return jobBuilderFactory.get("exportJob")
                .start(exportStep)
                .build();
    }
}
