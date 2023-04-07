package com.example.star_wars_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StarWarsProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(StarWarsProjectApplication.class, args);
    }
}