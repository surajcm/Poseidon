package com.poseidon.invoice.service.impl;

import com.poseidon.invoice.dao.InvoiceDAO;
import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.invoice.exception.InvoiceException;
import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.exception.TransactionException;
import com.poseidon.transaction.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class InvoiceServiceImplTest {
    private final InvoiceServiceImpl invoiceService = new InvoiceServiceImpl();
    private final InvoiceDAO invoiceDAO = Mockito.mock(InvoiceDAO.class);
    private final TransactionService transactionService = Mockito.mock(TransactionService.class);

    @BeforeEach
    public void setup() {
        Whitebox.setInternalState(invoiceService, "invoiceDAO", invoiceDAO);
        Whitebox.setInternalState(invoiceService, "transactionService", transactionService);
    }

    @Test
    void addInvoiceSuccess() throws TransactionException {
        when(transactionService.fetchTransactionFromTag(anyString()))
                .thenReturn(Mockito.mock(TransactionReportVO.class));
        InvoiceVO invoiceVO = new InvoiceVO();
        invoiceVO.setTagNo("1234");
        Assertions.assertAll(() -> invoiceService.addInvoice(invoiceVO));
    }

    @Test
    void addInvoiceFailure() throws TransactionException {
        when(transactionService.fetchTransactionFromTag(anyString()))
                .thenThrow(new TransactionException(TransactionException.DATABASE_ERROR));
        InvoiceVO invoiceVO = new InvoiceVO();
        invoiceVO.setTagNo("1234");
        Assertions.assertThrows(InvoiceException.class, () -> invoiceService.addInvoice(invoiceVO));
    }

    @Test
    void fetchInvoiceForListOfTransactionsSuccess() throws TransactionException, InvoiceException {
        when(transactionService.listTodaysTransactions()).thenReturn(mockListOfTransactionVOs());
        when(invoiceDAO.fetchInvoiceForListOfTransactions(ArgumentMatchers.<List<String>>any()))
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
    void fetchInvoiceForListOfTransactionsOnInvoiceFailure() throws TransactionException, InvoiceException {
        when(transactionService.listTodaysTransactions()).thenReturn(mockListOfTransactionVOs());
        when(invoiceDAO.fetchInvoiceForListOfTransactions(ArgumentMatchers.<List<String>>any()))
                .thenThrow(new InvoiceException(InvoiceException.DATABASE_ERROR));
        Assertions.assertThrows(InvoiceException.class, invoiceService::fetchInvoiceForListOfTransactions);
    }

    @Test
    void fetchInvoiceVOFromIdSuccess() throws InvoiceException {
        when(invoiceDAO.fetchInvoiceVOFromId(anyLong())).thenReturn(new InvoiceVO());
        Assertions.assertNotNull(invoiceService.fetchInvoiceVOFromId(1234L));
    }

    @Test
    void fetchInvoiceVOFromIdFailure() throws InvoiceException {
        when(invoiceDAO.fetchInvoiceVOFromId(anyLong()))
                .thenThrow(new InvoiceException(InvoiceException.DATABASE_ERROR));
        Assertions.assertThrows(InvoiceException.class, () -> invoiceService.fetchInvoiceVOFromId(1234L));
    }

    @Test
    void fetchInvoiceVOFromTagNoSuccess() throws InvoiceException {
        when(invoiceDAO.fetchInvoiceVOFromTagNo(anyString())).thenReturn(new InvoiceVO());
        Assertions.assertNotNull(invoiceService.fetchInvoiceVOFromTagNo("ABC"));
    }

    @Test
    void fetchInvoiceVOFromTagNoFailure() throws InvoiceException {
        when(invoiceDAO.fetchInvoiceVOFromTagNo(anyString()))
                .thenThrow(new InvoiceException(InvoiceException.DATABASE_ERROR));
        Assertions.assertThrows(InvoiceException.class, () -> invoiceService.fetchInvoiceVOFromTagNo("ABC"));
    }

    @Test
    void deleteInvoiceSuccess() {
        Assertions.assertAll(() -> invoiceService.deleteInvoice(1234L));
    }

    @Test
    void deleteInvoiceFailure() throws InvoiceException {
        doThrow(new InvoiceException(InvoiceException.DATABASE_ERROR)).when(invoiceDAO).deleteInvoice(anyLong());
        Assertions.assertThrows(InvoiceException.class, () -> invoiceService.deleteInvoice(1234L));
    }

    @Test
    void updateInvoiceSuccess() throws TransactionException {
        when(transactionService.fetchTransactionFromTag(anyString()))
                .thenReturn(Mockito.mock(TransactionReportVO.class));
        InvoiceVO invoiceVO = new InvoiceVO();
        invoiceVO.setTagNo("1234");
        Assertions.assertAll(() -> invoiceService.updateInvoice(invoiceVO));
    }

    @Test
    void updateInvoiceFailure() throws TransactionException {
        when(transactionService.fetchTransactionFromTag(anyString()))
                .thenThrow(new TransactionException(TransactionException.DATABASE_ERROR));
        InvoiceVO invoiceVO = new InvoiceVO();
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
        when(invoiceDAO.findInvoices(any())).thenThrow(new InvoiceException(InvoiceException.DATABASE_ERROR));
        Assertions.assertThrows(InvoiceException.class,
                () -> invoiceService.findInvoices(Mockito.mock(InvoiceVO.class)));
    }

    private List<TransactionVO> mockListOfTransactionVOs() {
        TransactionVO transactionVO1 = new TransactionVO();
        transactionVO1.setTagNo("1234");
        TransactionVO transactionVO2 = new TransactionVO();
        transactionVO2.setTagNo("5678");
        List<TransactionVO> transactionVOS = new ArrayList<>();
        transactionVOS.add(transactionVO1);
        transactionVOS.add(transactionVO2);
        return transactionVOS;
    }

}