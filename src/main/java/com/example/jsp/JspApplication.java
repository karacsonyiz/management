package com.example.jsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
//TODO : refactor ManageController,GeneratedEntity rewrite simple to Entity,Make constants class,Spring security rewrite,
//TODO : delete unused classes,make Toast,better Session handling,refactor managecontroller,rewrite classnames
public class JspApplication {

    public static void main(String[] args) {
        SpringApplication.run(JspApplication.class, args);
    }

}
