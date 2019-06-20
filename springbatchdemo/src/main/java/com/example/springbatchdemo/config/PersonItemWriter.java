package com.example.springbatchdemo.config;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@StepScope
public class PersonItemWriter implements ItemWriter<Person> {

    private final PersonService personService;

    public PersonItemWriter(PersonService personService) {
        this.personService=personService;
    }

    @Override
    public void write(List<? extends Person> list) {
        for (Person person: list){
            personService.persistServiceAndDoOtherThings(person);
        }
    }


}
