package com.poseidon.invoice.service.impl;

import com.poseidon.invoice.dao.InvoiceDAO;
import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.exception.TransactionException;
import com.poseidon.transaction.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class InvoiceServiceImplTest {
    private final InvoiceServiceImpl invoiceService = new InvoiceServiceImpl();
    private InvoiceDAO invoiceDAO = Mockito.mock(InvoiceDAO.class);
    private TransactionService transactionService = Mockito.mock(TransactionService.class);

    @BeforeEach
    public void setup() {
        Whitebox.setInternalState(invoiceService, "invoiceDAO", invoiceDAO);
        Whitebox.setInternalState(invoiceService, "transactionService", transactionService);
    }

    @Test
    public void addInvoiceSuccess() throws TransactionException {
        when(transactionService.fetchTransactionFromTag(anyString()))
                .thenReturn(Mockito.mock(TransactionReportVO.class));
        InvoiceVO invoiceVO = new InvoiceVO();
        invoiceVO.setTagNo("1234");
        Assertions.assertAll(() -> invoiceService.addInvoice(invoiceVO));
    }

}