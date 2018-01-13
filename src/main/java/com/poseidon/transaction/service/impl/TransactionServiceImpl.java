package com.poseidon.transaction.service.impl;

import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.service.TransactionService;
import com.poseidon.transaction.dao.TransactionDAO;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.exception.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 3:45:31 PM
 */
public class TransactionServiceImpl implements TransactionService {

    /**
     * user service instance
     */
    private TransactionDAO transactionDAO;

    private final Logger LOG = LoggerFactory.getLogger(TransactionServiceImpl.class);

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
            LOG.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
        }
        return transactionVOs;
    }

    public String saveTransaction(TransactionVO currentTransaction) throws TransactionException {
        String tagNo = null;
        try {
            tagNo = getTransactionDAO().saveTransaction(currentTransaction);
        } catch (TransactionException t) {
            LOG.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
        }
        return tagNo;
    }

    public List<TransactionVO> searchTransactions(TransactionVO searchTransaction) throws TransactionException {
        List<TransactionVO> transactionVOs = null;
        try {
            transactionVOs = getTransactionDAO().searchTransactions(searchTransaction);
        } catch (TransactionException t) {
            LOG.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
        }
        return transactionVOs;
    }

    public TransactionVO fetchTransactionFromId(Long id) throws TransactionException {
        TransactionVO transactionVO= null;
        try {
            transactionVO = getTransactionDAO().fetchTransactionFromId(id);
        } catch (TransactionException t) {
            LOG.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
        }
        return transactionVO;
    }

    public TransactionReportVO fetchTransactionFromTag(String tagNo) throws TransactionException {
        TransactionReportVO transactionVO= null;
        try {
            transactionVO = getTransactionDAO().fetchTransactionFromTag(tagNo);
        } catch (TransactionException t) {
            LOG.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
        }
        return transactionVO;
    }

    public void updateTransaction(TransactionVO currentTransaction) throws TransactionException {
        try {
            getTransactionDAO().updateTransaction(currentTransaction);
        } catch (TransactionException t) {
            LOG.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
        }
    }

    public void deleteTransaction(Long id) throws TransactionException {
        try {
            getTransactionDAO().deleteTransaction(id);
        } catch (TransactionException t) {
            LOG.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
        }
    }

    @Override
    public void updateTransactionStatus(Long id, String status) throws TransactionException {
        try {
            getTransactionDAO().updateTransactionStatus(id,status);
        } catch (TransactionException t) {
            LOG.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
        }
    }
}
