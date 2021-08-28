package com.poseidon.transaction;

import com.poseidon.customer.service.CustomerService;
import com.poseidon.make.service.MakeService;
import com.poseidon.transaction.service.TransactionService;
import com.poseidon.transaction.web.controller.TransactionController;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class TransactionConfigurations {
    @Bean
    public TransactionController transactionController() {
        return new TransactionController(
                Mockito.mock(TransactionService.class),
                Mockito.mock(MakeService.class),
                Mockito.mock(CustomerService.class));
    }

    @Bean
    public TransactionService transactionService() {
        return Mockito.mock(TransactionService.class);
    }

    @Bean
    public MakeService makeService() {
        return Mockito.mock(MakeService.class);
    }

    @Bean
    public CustomerService customerService() {
        return Mockito.mock(CustomerService.class);
    }
}
