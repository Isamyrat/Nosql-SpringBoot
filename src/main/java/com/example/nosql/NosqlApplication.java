package com.example.nosql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication()
@EnableWebMvc
public class NosqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(NosqlApplication.class, args);
    }

}
