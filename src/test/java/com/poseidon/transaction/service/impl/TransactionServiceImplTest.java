package com.poseidon.transaction.service.impl;

import com.poseidon.transaction.dao.TransactionDAO;
import com.poseidon.transaction.exception.TransactionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TransactionServiceImplTest {
    private final TransactionServiceImpl transactionService = new TransactionServiceImpl();
    private final TransactionDAO transactionDAO = Mockito.mock(TransactionDAO.class);

    @BeforeEach
    public void setup() {
        Whitebox.setInternalState(transactionService, "transactionDAO", transactionDAO);
    }

    @Test
    public void listTodayTransactionsSuccess() throws TransactionException {
        when(transactionDAO.listTodaysTransactions()).thenReturn(new ArrayList<>());
        Assertions.assertNotNull(transactionService.listTodaysTransactions());
    }

    @Test
    public void listTodayTransactionsFailure() throws TransactionException {
        when(transactionDAO.listTodaysTransactions())
                .thenThrow(new TransactionException(TransactionException.DATABASE_ERROR));
        Assertions.assertThrows(TransactionException.class, transactionService::listTodaysTransactions);
    }
}