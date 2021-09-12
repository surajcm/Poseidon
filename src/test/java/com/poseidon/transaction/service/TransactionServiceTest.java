package com.poseidon.transaction.service;

import com.poseidon.transaction.dao.TransactionDAO;
import com.poseidon.transaction.domain.TransactionVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TransactionServiceTest {
    private final TransactionDAO transactionDAO = Mockito.mock(TransactionDAO.class);
    private final TransactionService transactionService = new TransactionService(transactionDAO);

    @Test
    void listTodayTransactionsSuccess() {
        when(transactionDAO.listTodaysTransactions()).thenReturn(new ArrayList<>());
        Assertions.assertNotNull(transactionService.listTodaysTransactions());
    }

    @Test
    void saveTransactionSuccess() {
        Assertions.assertNull(transactionService.saveTransaction(Mockito.mock(TransactionVO.class)));
    }

    @Test
    void searchTransactionsSuccess() {
        when(transactionDAO.searchTransactions(any())).thenReturn(new ArrayList<>());
        Assertions.assertNotNull(transactionService.searchTransactions(Mockito.mock(TransactionVO.class)));
    }

    @Test
    void fetchTransactionFromIdSuccess() {
        Assertions.assertNull(transactionService.fetchTransactionFromId(1234L));
    }

    @Test
    void fetchTransactionFromTagSuccess() {
        Assertions.assertNull(transactionService.fetchTransactionFromTag("tag"));
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
        Assertions.assertNotNull(transactionService.listAllTransactions());
    }
}