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
    private final ModelService modelService = Mockito.mock(ModelService.class);

    @Bean
    public MakeController makeController() {
        return new MakeController(makeService);
    }

    @Bean
    public ModelController modelController() {
        return new ModelController(makeService, modelService);
    }

    @Bean
    public MakeService makeService() {
        return makeService;
    }

    @Bean
    public ModelService modelService() {
        return modelService;
    }
}
