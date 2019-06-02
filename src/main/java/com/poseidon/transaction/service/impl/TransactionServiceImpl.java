package com.poseidon.transaction.service.impl;

import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.service.TransactionService;
import com.poseidon.transaction.dao.TransactionDAO;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.exception.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 3:45:31 PM
 */
@Service
@SuppressWarnings("unused")
public class TransactionServiceImpl implements TransactionService {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionDAO transactionDAO;

    /**
     * list today's transactions
     *
     * @return list of transactions
     * @throws TransactionException on error
     */
    @Override
    public List<TransactionVO> listTodaysTransactions() throws TransactionException {
        List<TransactionVO> transactionVOs = null;
        try {
            transactionVOs = transactionDAO.listTodaysTransactions();
        } catch (TransactionException t) {
            LOG.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
        }
        return transactionVOs;
    }

    /**
     * save the current transaction
     *
     * @param currentTransaction transaction instance
     * @return tag number of the transaction
     * @throws TransactionException on error
     */
    @Override
    public String saveTransaction(TransactionVO currentTransaction) throws TransactionException {
        String tagNo = null;
        try {
            tagNo = transactionDAO.saveTransaction(currentTransaction);
        } catch (TransactionException t) {
            LOG.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
        }
        return tagNo;
    }

    /**
     * search transaction based on given conditions
     *
     * @param searchTransaction transaction instance
     * @return list of transactions
     * @throws TransactionException on error
     */
    @Override
    public List<TransactionVO> searchTransactions(TransactionVO searchTransaction) throws TransactionException {
        List<TransactionVO> transactionVOs = null;
        try {
            transactionVOs = transactionDAO.searchTransactions(searchTransaction);
        } catch (TransactionException t) {
            LOG.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
        }
        return transactionVOs;
    }

    /**
     * fetch transaction from id
     *
     * @param id of the transaction to be fetched
     * @return transaction instance
     * @throws TransactionException on error
     */
    @Override
    public TransactionVO fetchTransactionFromId(Long id) throws TransactionException {
        TransactionVO transactionVO = null;
        try {
            transactionVO = transactionDAO.fetchTransactionFromId(id);
        } catch (TransactionException t) {
            LOG.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
        }
        return transactionVO;
    }

    /**
     * fetch a transaction from tag number
     *
     * @param tagNo tag number of the transaction
     * @return reporting transaction vo
     * @throws TransactionException on error
     */
    @Override
    public TransactionReportVO fetchTransactionFromTag(String tagNo) throws TransactionException {
        TransactionReportVO transactionVO = null;
        try {
            transactionVO = transactionDAO.fetchTransactionFromTag(tagNo);
        } catch (TransactionException t) {
            LOG.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
        }
        return transactionVO;
    }

    /**
     * update transaction
     *
     * @param currentTransaction transaction instance
     */
    @Override
    public void updateTransaction(TransactionVO currentTransaction) {
        try {
            transactionDAO.updateTransaction(currentTransaction);
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
        }
    }

    /**
     * delete a transaction
     *
     * @param id of the transaction to be deleted
     * @throws TransactionException on error
     */
    @Override
    public void deleteTransaction(Long id) throws TransactionException {
        try {
            transactionDAO.deleteTransaction(id);
        } catch (TransactionException t) {
            LOG.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
        }
    }

    /**
     * update the status of a transaction
     *
     * @param id     id of transaction
     * @param status new status to be updated
     * @throws TransactionException on error
     */
    @Override
    public void updateTransactionStatus(Long id, String status) throws TransactionException {
        try {
            transactionDAO.updateTransactionStatus(id, status);
        } catch (TransactionException t) {
            LOG.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
        }
    }

    /**
     * list all transactions
     *
     * @return list of transaction vo
     * @throws TransactionException on error
     */
    @Override
    public List<TransactionVO> listAllTransactions() throws TransactionException {
        List<TransactionVO> transactionVOs = null;
        try {
            transactionVOs = transactionDAO.listAllTransactions();
        } catch (TransactionException t) {
            LOG.error(" Exception type in service impl " + t.getExceptionType());
            throw new TransactionException(t.getExceptionType());
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
        }
        return transactionVOs;
    }
}
