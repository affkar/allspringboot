package com.example.springbatchdemo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.interceptor.TransactionAttribute;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, Step myStep){
        return jobBuilderFactory.get("ETLLoad").incrementer(new RunIdIncrementer()).start(myStep).build();
    }


    @Bean
    public Step myStep(StepBuilderFactory stepBuilderFactory, ItemReader<? extends Person> reader, ItemProcessor<? super Person, ? extends Person> processor, ItemWriter<? super Person> writer) {
        return stepBuilderFactory.get("ETLLoadStep").<Person,Person>chunk(1).reader(reader)
                .faultTolerant()
                .skip(RuntimeException.class).skipLimit(3)
//                .noRetry(Exception.class)
//                .noRollback(Exception.class)
//                .transactionAttribute(transAttrib)
                .processorNonTransactional()
                .processor(processor)
                .writer(writer).build();
    }

    @Bean
    public ItemWriter<? super Person> writer(EntityManagerFactory entityManagerFactory) {
        /*JpaItemWriter<Person> userJpaItemWriter = new JpaItemWriter<>();
        userJpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return userJpaItemWriter;*/
        return new PersonItemWriter();
    }


    @Bean
    public ItemProcessor<? super Person,? extends Person> processor(final PersonService personService) {
        return person -> {
            try {
                personService.persistServiceAndDoOtherThings(person);
            }catch(RuntimeException e){
                System.out.println("got back an exception "+e.getMessage() );
                e.printStackTrace();
            }
          return person;
        };
    }

    @Bean
    public FlatFileItemReader<Person> reader(@Value("${input}") Resource resource) {
        FlatFileItemReader<Person> u = new FlatFileItemReader<>();
        u.setResource(resource);
        u.setStrict(false);
        u.setLinesToSkip(1);

        DefaultLineMapper<Person> lineMapper=new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer= new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setNames(new String[]{"id","name","dept","salary"});

        lineMapper.setLineTokenizer(delimitedLineTokenizer);

        BeanWrapperFieldSetMapper<Person> beanWrapperFieldSetMapper=new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(Person.class);

        lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        u.setLineMapper(lineMapper);
        return u;
    }
}
