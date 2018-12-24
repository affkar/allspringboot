package com.example.springbatchdemo.config;

import com.example.springbatchdemo.model.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing

public class SpringBatchConfig {

    @Bean
    private Job job(JobBuilderFactory jobBuilderFactory){
        return jobBuilderFactory.get("ETLLoad").incrementer(new RunIdIncrementer()).start(step());
    }


    @Bean
    public Step step(StepBuilderFactory stepBuilderFactory, ItemWriter<? super User> writer) {
        return stepBuilderFactory.get("ETLLoadStep").<User,User>chunk(100).reader(reader())
                .processor(processor())
                .writer(writer).build();
    }

    @Bean
    public ItemWriter<? super User> writer(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<User> userJpaItemWriter = new JpaItemWriter<>();
        userJpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return userJpaItemWriter;
    }


    @Bean
    public ItemProcessor<? super User,? extends User> processor() {
        Map<String, String> deptIdToDeptName=new HashMap<>();
        deptIdToDeptName.put("001","Finance");
        deptIdToDeptName.put("002","HR");
        deptIdToDeptName.put("003","Accounts");

        return user -> {
          user.setDept(deptIdToDeptName.get(user.getDept()));
          return user;
        };

    }

    @Bean
    public FlatFileItemReader<User> reader(@Value("${input}") Resource resource) {
        FlatFileItemReader<User> u = new FlatFileItemReader<>();
        u.setResource(resource);
        u.setStrict(false);
        u.setLinesToSkip(1);

        DefaultLineMapper<User> lineMapper=new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer= new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setNames(new String[]{"id","name","dept","salary"});

        lineMapper.setLineTokenizer(delimitedLineTokenizer);

        BeanWrapperFieldSetMapper<User> beanWrapperFieldSetMapper=new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(User.class);

        lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        u.setLineMapper(lineMapper);
        return u;
    }
}
