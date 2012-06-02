package com.poseidon.Transaction.web.controller;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.poseidon.Transaction.delegate.TransactionDelegate;
import com.poseidon.Transaction.web.form.TransactionForm;
import com.poseidon.Transaction.domain.TransactionVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;

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
                             HttpServletResponse response,TransactionForm transactionForm) {
        log.info(" Inside List method of TransactionController ");
        log.info(" form details are"+ transactionForm);
        //TransactionForm transactionForm = new TransactionForm();
        List<TransactionVO> transactionVOs = null;
        try {
            transactionVOs = getTransactionDelegate().listTodaysTransactions();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(TransactionVO transactionVO:transactionVOs){
            log.info(" transaction vo is "+ transactionVOs);    
        }
        transactionForm.setTransactionsList(transactionVOs);
        transactionForm.setSearchTransaction(populateSearchVO());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        return new ModelAndView("txs/TransactionList", "transactionForm", transactionForm);
    }

    private TransactionVO populateSearchVO(){
      TransactionVO vo = new TransactionVO();
      List<String> statusList = new ArrayList<String>();
      statusList.add("--Select--");
      statusList.add("NEW");
      statusList.add("ACCEPTED");
      statusList.add("VERIFIED");
      statusList.add("CLOSED");
      statusList.add("REJECTEd");
      vo.setStatusList(statusList);
      return vo;
    }
}
