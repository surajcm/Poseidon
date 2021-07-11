package com.poseidon.transaction.service.impl;

import com.poseidon.transaction.dao.TransactionDAO;
import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.exception.TransactionException;
import com.poseidon.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * user: Suraj.
 * Date: Jun 2, 2012
 * Time: 3:45:31 PM
 */
@Service
@SuppressWarnings("unused")
public class TransactionServiceImpl implements TransactionService {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private static final String EXCEPTION_TYPE_IN_SERVICE_IMPL = "Exception type in service impl {}";

    @Autowired
    private TransactionDAO transactionDAO;

    /**
     * list today's transactions.
     *
     * @return list of transactions
     * @throws TransactionException on error
     */
    @Override
    public List<TransactionVO> listTodaysTransactions() throws TransactionException {
        List<TransactionVO> transactionVOs;
        try {
            transactionVOs = transactionDAO.listTodaysTransactions();
        } catch (TransactionException ex) {
            LOG.error(EXCEPTION_TYPE_IN_SERVICE_IMPL, ex.getMessage());
            throw new TransactionException(ex.getMessage());
        }
        return transactionVOs;
    }

    /**
     * save the current transaction.
     *
     * @param currentTransaction transaction instance
     * @return tag number of the transaction
     * @throws TransactionException on error
     */
    @Override
    public String saveTransaction(final TransactionVO currentTransaction) throws TransactionException {
        String tagNo;
        try {
            tagNo = transactionDAO.saveTransaction(currentTransaction);
        } catch (TransactionException ex) {
            LOG.error(EXCEPTION_TYPE_IN_SERVICE_IMPL, ex.getExceptionType());
            throw new TransactionException(ex.getExceptionType());
        }
        return tagNo;
    }

    /**
     * search transaction based on given conditions.
     *
     * @param searchTransaction transaction instance
     * @return list of transactions
     * @throws TransactionException on error
     */
    @Override
    public List<TransactionVO> searchTransactions(final TransactionVO searchTransaction)
            throws TransactionException {
        List<TransactionVO> transactionVOs;
        try {
            transactionVOs = transactionDAO.searchTransactions(searchTransaction);
        } catch (TransactionException ex) {
            LOG.error(EXCEPTION_TYPE_IN_SERVICE_IMPL, ex.getExceptionType());
            throw new TransactionException(ex.getExceptionType());
        }
        return transactionVOs;
    }

    /**
     * fetch transaction from id.
     *
     * @param id of the transaction to be fetched
     * @return transaction instance
     * @throws TransactionException on error
     */
    @Override
    public TransactionVO fetchTransactionFromId(final Long id) throws TransactionException {
        TransactionVO transactionVO;
        try {
            transactionVO = transactionDAO.fetchTransactionFromId(id);
        } catch (TransactionException ex) {
            LOG.error(EXCEPTION_TYPE_IN_SERVICE_IMPL, ex.getExceptionType());
            throw new TransactionException(ex.getExceptionType());
        }
        return transactionVO;
    }

    /**
     * fetch a transaction from tag number.
     *
     * @param tagNo tag number of the transaction
     * @return reporting transaction vo
     * @throws TransactionException on error
     */
    @Override
    public TransactionReportVO fetchTransactionFromTag(final String tagNo) throws TransactionException {
        TransactionReportVO transactionVO;
        try {
            transactionVO = transactionDAO.fetchTransactionFromTag(tagNo);
        } catch (TransactionException ex) {
            LOG.error(EXCEPTION_TYPE_IN_SERVICE_IMPL, ex.getExceptionType());
            throw new TransactionException(ex.getExceptionType());
        }
        return transactionVO;
    }

    /**
     * update transaction.
     *
     * @param currentTransaction transaction instance
     */
    @Override
    public void updateTransaction(final TransactionVO currentTransaction) {
        try {
            transactionDAO.updateTransaction(currentTransaction);
        } catch (TransactionException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    }

    /**
     * delete a transaction.
     *
     * @param id of the transaction to be deleted
     * @throws TransactionException on error
     */
    @Override
    public void deleteTransaction(final Long id) throws TransactionException {
        try {
            transactionDAO.deleteTransaction(id);
        } catch (TransactionException ex) {
            LOG.error(EXCEPTION_TYPE_IN_SERVICE_IMPL, ex.getExceptionType());
            throw new TransactionException(ex.getExceptionType());
        }
    }

    /**
     * update the status of a transaction.
     *
     * @param id     id of transaction
     * @param status new status to be updated
     * @throws TransactionException on error
     */
    @Override
    public void updateTransactionStatus(final Long id, final String status) throws TransactionException {
        try {
            transactionDAO.updateTransactionStatus(id, status);
        } catch (TransactionException ex) {
            LOG.error(EXCEPTION_TYPE_IN_SERVICE_IMPL, ex.getExceptionType());
            throw new TransactionException(ex.getExceptionType());
        }
    }

    /**
     * list all transactions.
     *
     * @return list of transaction vo
     * @throws TransactionException on error
     */
    @Override
    public List<TransactionVO> listAllTransactions() throws TransactionException {
        List<TransactionVO> transactionVOs;
        try {
            transactionVOs = transactionDAO.listAllTransactions();
        } catch (TransactionException ex) {
            LOG.error(EXCEPTION_TYPE_IN_SERVICE_IMPL, ex.getExceptionType());
            throw new TransactionException(ex.getExceptionType());
        }
        return transactionVOs;
    }
}
