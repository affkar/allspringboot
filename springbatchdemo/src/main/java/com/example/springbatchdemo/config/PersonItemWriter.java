package com.example.springbatchdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@StepScope
public class PersonItemWriter implements ItemWriter<Person> {

    private static final Logger LOG = LoggerFactory.getLogger( PersonItemWriter.class );
    private final PersonService personService;

    public PersonItemWriter(PersonService personService) {
        this.personService=personService;
    }

    @Override
    public void write(List<? extends Person> list) {
        for (Person person: list){
            try {
                personService.persistServiceAndDoOtherThings(person);
            }catch(Exception e){
                LOG.error("Error Processing [" + person+"]", e);
            }
        }
    }


}
