package com.poseidon.Transaction.service;

import com.poseidon.Transaction.domain.TransactionVO;
import com.poseidon.Transaction.exception.TransactionException;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 3:45:07 PM
 */
public interface TransactionService {
    public List<TransactionVO> listTodaysTransactions() throws TransactionException;
}
