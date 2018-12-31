package com.karthick.springboot.samples.springboot2.unittesting.nameservice;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Primary
public class NameService {

    public NameService() {
        System.out.println("NameService.NameService =====" + this.toString());
    }

    public String getName(String id) {
        return "A dummy username";
    }

}
