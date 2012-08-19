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

    public String saveTransaction(TransactionVO currentTransaction) throws TransactionException {
        return getTransactionService().saveTransaction(currentTransaction);
    }


    public List<TransactionVO> searchTransactions(TransactionVO searchTransaction) throws  TransactionException{
        return getTransactionService().searchTransactions(searchTransaction);
    }

    public TransactionVO fetchTransactionFromId(Long id) throws TransactionException{
        return getTransactionService().fetchTransactionFromId(id);
    }

    public void updateTransaction(TransactionVO currentTransaction)throws TransactionException{
        getTransactionService().updateTransaction(currentTransaction);
    }

    public void deleteTransaction(Long id) throws TransactionException{
        getTransactionService().deleteTransaction(id);
    }

    public void updateTransactionStatus(Long id, String status)throws TransactionException{
        getTransactionService().updateTransactionStatus(id,status);
    }
}
