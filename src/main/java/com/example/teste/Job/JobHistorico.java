package com.example.teste.Job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JobHistorico {
public static void main(String[] args) {
        SpringApplication.run(JobHistorico.class, args);
    }
}
