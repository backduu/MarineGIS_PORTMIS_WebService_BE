package com.bluespace.marine_mis_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class MarineMisServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarineMisServiceApplication.class, args);
    }

}
