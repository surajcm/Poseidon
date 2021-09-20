package com.poseidon.customer;

import com.poseidon.customer.service.CustomerService;
import com.poseidon.customer.web.controller.CustomerController;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class CustomerConfigurations {
    @Bean
    public CustomerController customerController() {
        return new CustomerController(Mockito.mock(CustomerService.class));
    }

    @Bean
    public CustomerService customerService() {
        return Mockito.mock(CustomerService.class);
    }
}
