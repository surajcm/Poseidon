package com.poseidon.make;

import com.poseidon.make.service.MakeService;
import com.poseidon.make.web.controller.MakeController;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration
public class MakeConfigurations {
    @Bean
    public MakeController makeController() {
        return new MakeController(Mockito.mock(MakeService.class));
    }

    @Bean
    public MakeService makeService() {
        return Mockito.mock(MakeService.class);
    }
}
