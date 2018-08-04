package com.poseidon.transaction.dao;

import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.exception.TransactionException;

import java.util.List;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 3:45:52 PM
 */
public interface TransactionDAO {
    List<TransactionVO> listTodaysTransactions() throws TransactionException;

    String saveTransaction(TransactionVO currentTransaction) throws TransactionException;

    List<TransactionVO> searchTransactions(TransactionVO searchTransaction) throws TransactionException;

    TransactionVO fetchTransactionFromId(Long id) throws TransactionException;

    void updateTransaction(TransactionVO currentTransaction);

    void deleteTransaction(Long id)throws TransactionException;

    TransactionReportVO fetchTransactionFromTag(String tagNo) throws TransactionException;

    void updateTransactionStatus(Long id, String status)throws TransactionException;

    List<TransactionVO> listAllTransactions() throws TransactionException;
}
