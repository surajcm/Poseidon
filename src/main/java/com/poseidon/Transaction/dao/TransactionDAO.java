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
    List<TransactionVO> listTodaysTransactions() throws TransactionException;

    String saveTransaction(TransactionVO currentTransaction) throws TransactionException;

    List<TransactionVO> searchTransactions(TransactionVO searchTransaction) throws TransactionException;

    TransactionVO fetchTransactionFromId(Long id) throws TransactionException;

    void updateTransaction(TransactionVO currentTransaction) throws TransactionException;

    void deleteTransaction(Long id)throws TransactionException;

    TransactionReportVO fetchTransactionFromTag(String tagNo) throws TransactionException;

    void updateTransactionStatus(Long id, String status)throws TransactionException;
}
