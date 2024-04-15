package com.poseidon.make;

import com.poseidon.make.service.MakeService;
import com.poseidon.make.web.controller.MakeController;
import com.poseidon.model.service.ModelService;
import com.poseidon.model.web.ModelController;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration
public class MakeConfigurations {
    private final MakeService makeService = Mockito.mock(MakeService.class);

    @Bean
    public MakeController makeController() {
        return new MakeController(makeService);
    }

    @Bean
    public ModelController modelController() {
        return new ModelController(Mockito.mock(MakeService.class), Mockito.mock(ModelService.class));
    }

    @Bean
    public MakeService makeService() {
        return makeService;
    }

    @Bean
    public ModelService modelService() {
        return Mockito.mock(ModelService.class);
    }
}
