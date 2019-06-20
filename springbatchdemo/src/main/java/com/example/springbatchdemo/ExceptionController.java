package com.example.springbatchdemo;

import com.example.springbatchdemo.config.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {

    @Autowired
    PersonService personService;

    @PostMapping(path="/toggleException")
    public void toggleException(){
        personService.toggleThrowException();
    }

}
