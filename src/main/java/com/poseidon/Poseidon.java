package com.poseidon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@SuppressWarnings({"PMD.UseUtilityClass"})
public class Poseidon {
    public static void main(final String[] args) {
        SpringApplication.run(Poseidon.class, args);
    }
}