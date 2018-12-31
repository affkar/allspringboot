package com.karthick.springboot.samples.springboot2.unittesting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.out.println("Application.main");
        SpringApplication.run(Application.class, args);
    }

}
