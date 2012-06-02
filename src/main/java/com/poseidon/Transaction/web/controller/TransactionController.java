package com.poseidon.Transaction.web.controller;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.poseidon.Transaction.delegate.TransactionDelegate;
import com.poseidon.Transaction.web.form.TransactionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 3:33:20 PM
 */
public class TransactionController extends MultiActionController {

    /**
     * user Delegate instance
     */
    private TransactionDelegate transactionDelegate;

    /**
     * logger for user controller
     */
    private final Log log = LogFactory.getLog(TransactionController.class);

    public TransactionDelegate getTransactionDelegate() {
        return transactionDelegate;
    }

    public void setTransactionDelegate(TransactionDelegate transactionDelegate) {
        this.transactionDelegate = transactionDelegate;
    }

    public ModelAndView List(HttpServletRequest request,
                             HttpServletResponse response) {
        log.info(" Inside List method of TransactionController ");
        TransactionForm transactionForm = new TransactionForm();
        try {
            transactionForm.setTransactionsList(getTransactionDelegate().listTodaysTransactions());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("txs/TransactionList", "transactionForm", transactionForm);
    }
}
