package com.poseidon.init;

import com.poseidon.init.error.PoseidonErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class ErrorConfigurations {
    @Bean
    public PoseidonErrorController poseidonErrorController() {
        return new PoseidonErrorController();
    }
}
