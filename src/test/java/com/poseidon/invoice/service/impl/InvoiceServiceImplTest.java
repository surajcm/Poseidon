package com.poseidon.invoice.service.impl;

import com.poseidon.invoice.dao.InvoiceDAO;
import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.invoice.exception.InvoiceException;
import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.exception.TransactionException;
import com.poseidon.transaction.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class InvoiceServiceImplTest {
    private final InvoiceDAO invoiceDAO = Mockito.mock(InvoiceDAO.class);
    private final TransactionService transactionService = Mockito.mock(TransactionService.class);
    private final InvoiceService invoiceService = new InvoiceService(invoiceDAO, transactionService);

    @Test
    void addInvoiceSuccess() throws TransactionException {
        when(transactionService.fetchTransactionFromTag(anyString()))
                .thenReturn(Mockito.mock(TransactionReportVO.class));
        var invoiceVO = new InvoiceVO();
        invoiceVO.setTagNo("1234");
        Assertions.assertAll(() -> invoiceService.addInvoice(invoiceVO));
    }

    @Test
    void addInvoiceFailure() throws TransactionException {
        when(transactionService.fetchTransactionFromTag(anyString()))
                .thenThrow(new TransactionException(TransactionException.DATABASE_ERROR));
        var invoiceVO = new InvoiceVO();
        invoiceVO.setTagNo("1234");
        Assertions.assertThrows(InvoiceException.class, () -> invoiceService.addInvoice(invoiceVO));
    }

    @Test
    void fetchInvoiceForListOfTransactionsSuccess() throws TransactionException, InvoiceException {
        when(transactionService.listTodaysTransactions()).thenReturn(mockListOfTransactionVOs());
        when(invoiceDAO.fetchInvoiceForListOfTransactions(ArgumentMatchers.any()))
                .thenReturn(new ArrayList<>());
        Assertions.assertNotNull(invoiceService.fetchInvoiceForListOfTransactions());
    }

    @Test
    void fetchInvoiceForListOfTransactionsOnTransactionFailure() throws TransactionException, InvoiceException {
        when(transactionService.listTodaysTransactions())
                .thenThrow(new TransactionException(TransactionException.DATABASE_ERROR));
        Assertions.assertNull(invoiceService.fetchInvoiceForListOfTransactions());
    }

    @Test
    void fetchInvoiceVOFromIdSuccess() {
        when(invoiceDAO.fetchInvoiceVOFromId(anyLong())).thenReturn(Optional.empty());
        Assertions.assertNotNull(invoiceService.fetchInvoiceVOFromId(1234L));
    }

    @Test
    void fetchInvoiceVOFromTagNoSuccess() {
        when(invoiceDAO.fetchInvoiceVOFromTagNo(anyString())).thenReturn(Optional.empty());
        Assertions.assertNotNull(invoiceService.fetchInvoiceVOFromTagNo("ABC"));
    }

    @Test
    void deleteInvoiceSuccess() {
        Assertions.assertAll(() -> invoiceService.deleteInvoice(1234L));
    }

    @Test
    void deleteInvoiceFailure() throws InvoiceException {
        doThrow(new InvoiceException(new RuntimeException())).when(invoiceDAO).deleteInvoice(anyLong());
        Assertions.assertThrows(InvoiceException.class, () -> invoiceService.deleteInvoice(1234L));
    }

    @Test
    void updateInvoiceSuccess() throws TransactionException {
        when(transactionService.fetchTransactionFromTag(anyString()))
                .thenReturn(Mockito.mock(TransactionReportVO.class));
        var invoiceVO = new InvoiceVO();
        invoiceVO.setTagNo("1234");
        Assertions.assertAll(() -> invoiceService.updateInvoice(invoiceVO));
    }

    @Test
    void updateInvoiceFailure() throws TransactionException {
        when(transactionService.fetchTransactionFromTag(anyString()))
                .thenThrow(new TransactionException(TransactionException.DATABASE_ERROR));
        var invoiceVO = new InvoiceVO();
        invoiceVO.setTagNo("1234");
        Assertions.assertThrows(InvoiceException.class, () -> invoiceService.updateInvoice(invoiceVO));
    }

    @Test
    void findInvoicesSuccess() throws InvoiceException {
        when(invoiceDAO.findInvoices(any())).thenReturn(new ArrayList<>());
        Assertions.assertNotNull(invoiceService.findInvoices(Mockito.mock(InvoiceVO.class)));
    }

    @Test
    void findInvoicesFailure() throws InvoiceException {
        when(invoiceDAO.findInvoices(any())).thenThrow(new InvoiceException(new RuntimeException()));
        Assertions.assertThrows(InvoiceException.class,
                () -> invoiceService.findInvoices(Mockito.mock(InvoiceVO.class)));
    }

    private List<TransactionVO> mockListOfTransactionVOs() {
        var transactionVO1 = new TransactionVO();
        transactionVO1.setTagNo("1234");
        TransactionVO transactionVO2 = new TransactionVO();
        transactionVO2.setTagNo("5678");
        return List.of(transactionVO1, transactionVO2);
    }

}