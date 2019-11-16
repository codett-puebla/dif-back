package com.devnous.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
public class PlatformApplication extends SpringBootServletInitializer {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Mexico_City"));   // It will set America/Mexico_City timezone
        System.out.println("Application Spring ERP running at : " + new Date());   // It will print UTC timezone
    }

    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
    }
}

