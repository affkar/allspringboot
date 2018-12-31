package com.karthick.springboot.samples.springboot2.unittesting.userservice;


import com.karthick.springboot.samples.springboot2.unittesting.Application;
import com.karthick.springboot.samples.springboot2.unittesting.nameservice.NameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ComponentScan("")
public class UserServiceTest {

    @MockBean
    private NameService nameService;

    @Autowired
    private UserService userService;

    @Test
    public void test1(){
        when(nameService.getName(anyString())).thenReturn("Karthick");
        System.out.println("userName = " + userService.getUserName("2"));
    }

}
