package com.poseidon.Transaction.service.impl;

import com.poseidon.Transaction.domain.TransactionReportVO;
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

    public String saveTransaction(TransactionVO currentTransaction) throws TransactionException {
        String tagNo = null;
        try {
            tagNo = getTransactionDAO().saveTransaction(currentTransaction);
        } catch (TransactionException t) {
            log.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return tagNo;
    }

    public List<TransactionVO> searchTransactions(TransactionVO searchTransaction) throws TransactionException {
        List<TransactionVO> transactionVOs = null;
        try {
            transactionVOs = getTransactionDAO().searchTransactions(searchTransaction);
        } catch (TransactionException t) {
            log.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return transactionVOs;
    }

    public TransactionVO fetchTransactionFromId(Long id) throws TransactionException {
        TransactionVO transactionVO= null;
        try {
            transactionVO = getTransactionDAO().fetchTransactionFromId(id);
        } catch (TransactionException t) {
            log.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return transactionVO;
    }

    public TransactionReportVO fetchTransactionFromTag(String tagNo) throws TransactionException {
        TransactionReportVO transactionVO= null;
        try {
            transactionVO = getTransactionDAO().fetchTransactionFromTag(tagNo);
        } catch (TransactionException t) {
            log.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return transactionVO;
    }

    public void updateTransaction(TransactionVO currentTransaction) throws TransactionException {
        try {
            getTransactionDAO().updateTransaction(currentTransaction);
        } catch (TransactionException t) {
            log.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void deleteTransaction(Long id) throws TransactionException {
        try {
            getTransactionDAO().deleteTransaction(id);
        } catch (TransactionException t) {
            log.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void updateTransactionStatus(Long id, String status) throws TransactionException {
        try {
            getTransactionDAO().updateTransactionStatus(id,status);
        } catch (TransactionException t) {
            log.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
