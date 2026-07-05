package com.poseidon.transaction;

import com.poseidon.customer.service.CustomerService;
import com.poseidon.make.service.MakeService;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.service.TransactionService;
import com.poseidon.transaction.web.controller.TransactionController;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ContextConfiguration
public class TransactionConfigurations {
    @Bean
    public TransactionController transactionController() {
        var transactionService = transactionService();
        var makeService = makeService();
        return new TransactionController(transactionService, makeService, customerService());
    }

    @Bean
    public TransactionService transactionService() {
        var mock = Mockito.mock(TransactionService.class);
        Page<TransactionVO> emptyPage = new PageImpl<>(Collections.emptyList());
        when(mock.listAll(anyInt())).thenReturn(emptyPage);
        when(mock.listAllTransactions()).thenReturn(new ArrayList<>());
        return mock;
    }

    @Bean
    public MakeService makeService() {
        var mock = Mockito.mock(MakeService.class);
        when(mock.fetchAllMakes()).thenReturn(new ArrayList<>());
        return mock;
    }

    @Bean
    public CustomerService customerService() {
        return Mockito.mock(CustomerService.class);
    }
}
