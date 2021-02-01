package com.example.jsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
@EnableCaching
public class JspApplication {

    public static void main(String[] args) {
        SpringApplication.run(JspApplication.class, args);
    }

}
