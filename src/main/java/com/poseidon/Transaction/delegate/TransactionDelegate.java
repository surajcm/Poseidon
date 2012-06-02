package com.poseidon.Transaction.delegate;

import com.poseidon.Transaction.service.TransactionService;
import com.poseidon.Transaction.domain.TransactionVO;
import com.poseidon.Transaction.exception.TransactionException;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 3:34:37 PM
 */
public class TransactionDelegate {
    /**
     * user service instance
     */
    private TransactionService transactionService;

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public List<TransactionVO> listTodaysTransactions() throws TransactionException {
        return getTransactionService().listTodaysTransactions();
    }
}
