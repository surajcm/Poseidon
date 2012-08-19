package com.poseidon.Transaction.dao;

import com.poseidon.Transaction.domain.TransactionReportVO;
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

    public String saveTransaction(TransactionVO currentTransaction) throws TransactionException;

    public List<TransactionVO> searchTransactions(TransactionVO searchTransaction) throws TransactionException;

    public TransactionVO fetchTransactionFromId(Long id) throws TransactionException;

    public void updateTransaction(TransactionVO currentTransaction) throws TransactionException;

    public void deleteTransaction(Long id)throws TransactionException;

    public TransactionReportVO fetchTransactionFromTag(String tagNo) throws TransactionException;

    public void updateTransactionStatus(Long id, String status)throws TransactionException;
}
