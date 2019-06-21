package com.example.springbatchdemo.config;

import com.example.springbatchdemo.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Component
public class PersonService {

    private static final Logger LOG = LoggerFactory.getLogger( PersonService.class );
    
    @Autowired
    private PersonRepository personRepository;
    private boolean throwException=true;

    public void toggleThrowException(){
        throwException=!throwException;
        if(throwException){
            LOG.warn("Exceptions will be thrown");
        }else{
            LOG.warn("Exception will not be thrown");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persistServiceAndDoOtherThings(Person person) {
        Map<Integer, String> deptIdToDeptName=new HashMap<>();
        deptIdToDeptName.put(1, "Finance");
        deptIdToDeptName.put(2, "Error");
        deptIdToDeptName.put(3, "HR");
        person.setDept(deptIdToDeptName.get(person.getId()));
        LOG.info("persistServiceAndDoOtherThings was called for {} {} {} ", person.getId(), person.getName(), person.getDept());
        personRepository.save(person);
        if(throwException && person.getDept()!=null && person.getDept().equalsIgnoreCase("Error")){
            throw new RuntimeException("throwing Exception for the th item.");
        }
    }
}
