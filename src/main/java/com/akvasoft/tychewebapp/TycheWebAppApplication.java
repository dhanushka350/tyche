package com.akvasoft.tychewebapp;

import com.akvasoft.tychewebapp.service.MainService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.IOException;

@SpringBootApplication
public class TycheWebAppApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(TycheWebAppApplication.class, args);
    }

}

