package com.karthick.springboot.samples.springboot2.unittesting.userservice;

import com.karthick.springboot.samples.springboot2.unittesting.nameservice.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    private NameService nameService;

    public UserService() {
        System.out.println("UserService.UserService  =====" + this.toString());
    }

    public String getUserName(String id){
        return nameService.getName(id);
    }

}
