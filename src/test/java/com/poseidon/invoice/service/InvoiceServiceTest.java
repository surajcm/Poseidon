package com.poseidon.invoice.service;

import com.poseidon.invoice.dao.InvoiceDAO;
import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class InvoiceServiceTest {
    private final InvoiceDAO invoiceDAO = Mockito.mock(InvoiceDAO.class);
    private final TransactionService transactionService = Mockito.mock(TransactionService.class);
    private final InvoiceService invoiceService = new InvoiceService(invoiceDAO, transactionService);

    @Test
    void addInvoiceSuccess() {
        when(transactionService.fetchTransactionFromTag(anyString()))
                .thenReturn(Mockito.mock(TransactionReportVO.class));
        var invoiceVO = new InvoiceVO();
        invoiceVO.setTagNo("1234");
        Assertions.assertAll(() -> invoiceService.addInvoice(invoiceVO));
    }

    @Test
    void fetchInvoiceForListOfTransactionsSuccess() {
        when(transactionService.listTodaysTransactions()).thenReturn(mockListOfTransactionVOs());
        when(invoiceDAO.fetchInvoiceForListOfTransactions(any()))
                .thenReturn(new ArrayList<>());
        Assertions.assertNotNull(invoiceService.fetchInvoiceForListOfTransactions());
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
    void updateInvoiceSuccess() {
        when(transactionService.fetchTransactionFromTag(anyString()))
                .thenReturn(Mockito.mock(TransactionReportVO.class));
        var invoiceVO = new InvoiceVO();
        invoiceVO.setTagNo("1234");
        Assertions.assertAll(() -> invoiceService.updateInvoice(invoiceVO));
    }

    @Test
    void findInvoicesSuccess() {
        when(invoiceDAO.findInvoices(any())).thenReturn(new ArrayList<>());
        Assertions.assertNotNull(invoiceService.findInvoices(Mockito.mock(InvoiceVO.class)));
    }

    @Test
    void allTagNumbers() {
        when(transactionService.allTagNumbers()).thenReturn(List.of("ABC", "DEF"));
        Assertions.assertTrue(invoiceService.allTagNumbers().contains("ABC"));
    }

    private List<TransactionVO> mockListOfTransactionVOs() {
        var transactionVO1 = new TransactionVO();
        transactionVO1.setTagNo("1234");
        TransactionVO transactionVO2 = new TransactionVO();
        transactionVO2.setTagNo("5678");
        return List.of(transactionVO1, transactionVO2);
    }

}