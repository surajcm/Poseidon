package com.poseidon.transaction.service;

import com.poseidon.transaction.dao.TransactionDAO;
import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.domain.TransactionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * user: Suraj.
 * Date: Jun 2, 2012
 * Time: 3:45:31 PM
 */
@Service
@SuppressWarnings("unused")
public class TransactionService {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionService.class);
    private static final String EXCEPTION_TYPE_IN_SERVICE_IMPL = "Exception type in service impl {}";

    private final TransactionDAO transactionDAO;

    public TransactionService(final TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    /**
     * list all transactions.
     *
     * @return list of transaction vo
     */
    public List<TransactionVO> listAllTransactions() {
        return transactionDAO.listAllTransactions();
    }

    /**
     * list today's transactions.
     *
     * @return list of transactions
     */
    public List<TransactionVO> listTodaysTransactions() {
        return transactionDAO.listTodaysTransactions();
    }

    /**
     * save the current transaction.
     *
     * @param currentTransaction transaction instance
     * @return tag number of the transaction
     */
    public String saveTransaction(final TransactionVO currentTransaction) {
        return transactionDAO.saveTransaction(currentTransaction);
    }

    /**
     * search transaction based on given conditions.
     *
     * @param searchTransaction transaction instance
     * @return list of transactions
     */
    public List<TransactionVO> searchTransactions(final TransactionVO searchTransaction) {
        return transactionDAO.searchTransactions(searchTransaction);
    }

    /**
     * fetch transaction from id.
     *
     * @param id of the transaction to be fetched
     * @return transaction instance
     */
    public TransactionVO fetchTransactionFromId(final Long id) {
        return transactionDAO.fetchTransactionFromId(id);
    }

    /**
     * fetch a transaction from tag number.
     *
     * @param tagNo tag number of the transaction
     * @return reporting transaction vo
     */
    public TransactionReportVO fetchTransactionFromTag(final String tagNo) {
        return transactionDAO.fetchTransactionFromTag(tagNo);
    }

    /**
     * update transaction.
     *
     * @param currentTransaction transaction instance
     */
    public void updateTransaction(final TransactionVO currentTransaction) {
        transactionDAO.updateTransaction(currentTransaction);
    }

    /**
     * delete a transaction.
     *
     * @param id of the transaction to be deleted
     */
    public void deleteTransaction(final Long id) {
        transactionDAO.deleteTransaction(id);
    }

    /**
     * update the status of a transaction.
     *
     * @param id     id of transaction
     * @param status new status to be updated
     */
    public void updateTransactionStatus(final Long id, final String status) {
        transactionDAO.updateTransactionStatus(id, status);
    }

    public List<String> allTagNumbers() {
        return transactionDAO.allTagNumbers();
    }
}
