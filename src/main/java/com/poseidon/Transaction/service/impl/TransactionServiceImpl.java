package com.poseidon.Transaction.service.impl;

import com.poseidon.Transaction.service.TransactionService;
import com.poseidon.Transaction.dao.TransactionDAO;
import com.poseidon.Transaction.domain.TransactionVO;
import com.poseidon.Transaction.exception.TransactionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 3:45:31 PM
 */
public class TransactionServiceImpl implements TransactionService {

    /**
     * user service instance
     */
    private TransactionDAO transactionDAO;

    private final Log log = LogFactory.getLog(TransactionServiceImpl.class);

    public TransactionDAO getTransactionDAO() {
        return transactionDAO;
    }

    public void setTransactionDAO(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    public List<TransactionVO> listTodaysTransactions() throws TransactionException {
        List<TransactionVO> transactionVOs = null;
        try {
            transactionVOs = getTransactionDAO().listTodaysTransactions();
        } catch (TransactionException t) {
            log.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return transactionVOs;
    }

    public void saveTransaction(TransactionVO currentTransaction) throws TransactionException {
        try {
            getTransactionDAO().saveTransaction(currentTransaction);
        } catch (TransactionException t) {
            log.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
