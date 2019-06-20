package com.example.springbatchdemo.repository;

import com.example.springbatchdemo.config.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}
