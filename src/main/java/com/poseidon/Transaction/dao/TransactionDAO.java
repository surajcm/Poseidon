package com.poseidon.Transaction.dao;

import com.poseidon.Transaction.domain.TransactionVO;
import com.poseidon.Transaction.exception.TransactionException;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 3:45:52 PM
 */
public interface TransactionDAO {
    public List<TransactionVO> listTodaysTransactions() throws TransactionException;

    public void saveTransaction(TransactionVO currentTransaction) throws TransactionException;

    public List<TransactionVO> searchTransactions(TransactionVO searchTransaction) throws TransactionException;

    public TransactionVO fetchTransactionFromId(Long id) throws TransactionException;
}
