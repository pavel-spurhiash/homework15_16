package com.gmail.pashasimonpure.app.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.gmail.pashasimonpure.app.controller.runner",
        "com.gmail.pashasimonpure.app.service.impl",
        "com.gmail.pashasimonpure.app.repository.impl"
})
public class SpringBootModuleApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringBootModuleApplication.class, args);

    }

}


