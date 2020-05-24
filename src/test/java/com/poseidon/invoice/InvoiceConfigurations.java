package com.poseidon.invoice;

import com.poseidon.invoice.service.InvoiceService;
import com.poseidon.invoice.web.controller.InvoiceController;
import com.poseidon.make.service.MakeService;
import com.poseidon.transaction.service.TransactionService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class InvoiceConfigurations {
    @Bean
    public InvoiceController invoiceController() {
        return new InvoiceController();
    }

    @Bean
    public InvoiceService invoiceService() {
        return Mockito.mock(InvoiceService.class);
    }

    @Bean
    public TransactionService transactionService() {
        return Mockito.mock(TransactionService.class);
    }

    @Bean
    public MakeService makeService() {
        return Mockito.mock(MakeService.class);
    }
}
