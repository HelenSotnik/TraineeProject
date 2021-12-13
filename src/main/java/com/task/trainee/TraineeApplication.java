package com.task.trainee;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TraineeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(TraineeApplication.class, args);
    }
}
