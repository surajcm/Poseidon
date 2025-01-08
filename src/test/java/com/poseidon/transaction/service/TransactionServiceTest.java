package com.poseidon.transaction.service;

import com.poseidon.transaction.dao.TransactionDAO;
import com.poseidon.transaction.domain.TransactionVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class TransactionServiceTest {
    private final TransactionDAO transactionDAO = Mockito.mock(TransactionDAO.class);
    private final TransactionService transactionService = new TransactionService(transactionDAO);

    @Test
    void listTodayTransactionsSuccess() {
        when(transactionDAO.listTodaysTransactions()).thenReturn(new ArrayList<>());
        Assertions.assertNotNull(transactionService.listTodaysTransactions(),
                "Today's transactions should not be null");
    }

    @Test
    void saveTransactionSuccess() {
        Assertions.assertNull(transactionService.saveTransaction(Mockito.mock(TransactionVO.class)),
                "Transaction saved should not be null");
    }

    @Test
    void searchTransactionsSuccess() {
        when(transactionDAO.searchTransactions(any())).thenReturn(new ArrayList<>());
        Assertions.assertNotNull(transactionService.searchTransactions(Mockito.mock(TransactionVO.class)),
                "Search should not be null");
    }

    @Test
    void fetchTransactionFromIdSuccess() {
        Assertions.assertNull(transactionService.fetchTransactionFromId(1234L),
                "Transaction should not be null");
    }

    @Test
    void fetchTransactionFromTagSuccess() {
        Assertions.assertNull(transactionService.fetchTransactionFromTag("tag"),
                "Transaction with tag tag should not be null");
    }

    @Test
    void updateTransactionSuccess() {
        Assertions.assertAll(() -> transactionService.updateTransaction(Mockito.mock(TransactionVO.class)));
    }

    @Test
    void deleteTransactionSuccess() {
        Assertions.assertAll(() -> transactionService.deleteTransaction(anyLong()));
    }

    @Test
    void updateTransactionStatusSuccess() {
        Assertions.assertAll(() -> transactionService.updateTransactionStatus(anyLong(), anyString()));
    }

    @Test
    void listAllTransactionsSuccess() {
        when(transactionDAO.listAllTransactions()).thenReturn(new ArrayList<>());
        Assertions.assertNotNull(transactionService.listAllTransactions(),
                "All transactions should not be null");
    }

    @Test
    void allTagNumbers() {
        when(transactionService.allTagNumbers()).thenReturn(List.of("ABC", "DEF"));
        Assertions.assertTrue(transactionService.allTagNumbers().contains("ABC"), "ABC should be in the list");
    }

}