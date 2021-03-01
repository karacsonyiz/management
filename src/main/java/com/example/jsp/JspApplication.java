package com.example.jsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
//TODO : refactor ManageController,GeneratedEntity rewrite simple to Entity,Make constants class,Spring security rewrite,
//TODO : delete unused classes,make Toast,better Session handling,refactor managecontroller,rewrite classnames
//TODO : spring session,transaction-propagation,optimistic lock,criteriabuilder,lazy load,pagination,auditing from abstract class,
//TODO : langugage,theme switching,mobile view,carousel,jsp form-formvalidation-rejectvalue,add login password
public class JspApplication {

    public static void main(String[] args) {
        SpringApplication.run(JspApplication.class, args);
    }

}
