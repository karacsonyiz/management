package com.example.jsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JspApplication {

    public static void main(String[] args) {
        SpringApplication.run(JspApplication.class, args);
    }

}
