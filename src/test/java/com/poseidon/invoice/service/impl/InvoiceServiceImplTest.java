package com.poseidon.invoice.service.impl;

import com.poseidon.invoice.dao.InvoiceDAO;
import com.poseidon.transaction.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class InvoiceServiceImplTest {
    private final InvoiceServiceImpl invoiceService = new InvoiceServiceImpl();
    private InvoiceDAO invoiceDAO = Mockito.mock(InvoiceDAO.class);
    private TransactionService transactionService= Mockito.mock(TransactionService.class);

    @BeforeEach
    public void setup() {
        Whitebox.setInternalState(invoiceService, "invoiceDAO", invoiceDAO);
        Whitebox.setInternalState(invoiceService, "transactionService", transactionService);
    }

}