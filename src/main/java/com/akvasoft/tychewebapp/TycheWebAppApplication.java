package com.akvasoft.tychewebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class TycheWebAppApplication extends SpringBootServletInitializer {

    @PostConstruct
    public void init(){
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("PST"));
    }

    public static void main(String[] args) {

        SpringApplication.run(TycheWebAppApplication.class, args);
    }

}

