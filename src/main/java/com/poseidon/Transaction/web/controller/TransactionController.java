package com.poseidon.Transaction.web.controller;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.poseidon.Transaction.delegate.TransactionDelegate;
import com.poseidon.Transaction.web.form.TransactionForm;
import com.poseidon.Transaction.domain.TransactionVO;
import com.poseidon.Make.delegate.MakeDelegate;
import com.poseidon.Make.domain.MakeVO;

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

    private MakeDelegate makeDelegate;
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

    public MakeDelegate getMakeDelegate() {
        return makeDelegate;
    }

    public void setMakeDelegate(MakeDelegate makeDelegate) {
        this.makeDelegate = makeDelegate;
    }

    public ModelAndView List(HttpServletRequest request,
                             HttpServletResponse response, TransactionForm transactionForm) {
        log.info(" Inside List method of TransactionController ");
        log.info(" form details are" + transactionForm);
        //TransactionForm transactionForm = new TransactionForm();
        List<TransactionVO> transactionVOs = null;
        try {
            transactionVOs = getTransactionDelegate().listTodaysTransactions();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (transactionVOs != null) {
            for (TransactionVO transactionVO : transactionVOs) {
                log.info(" transaction vo is " + transactionVO);
            }
            transactionForm.setTransactionsList(transactionVOs);
        }
        //get all the make list for displaying in search
        List<MakeVO> makeVOs =  null;
        try {
            makeVOs = getMakeDelegate().listAllMakes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(makeVOs != null){
            for(MakeVO makeVO:makeVOs){
                log.info("make vo is"+makeVO);
            }
            transactionForm.setMakeVOs(makeVOs);
        }
        transactionForm.setSearchTransaction(new TransactionVO());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setStatusList(populateStatus());
        return new ModelAndView("txs/TransactionList", "transactionForm", transactionForm);
    }

    private List<String> populateStatus() {
        List<String> statusList = new ArrayList<String>();
        statusList.add("--Select--");
        statusList.add("NEW");
        statusList.add("ACCEPTED");
        statusList.add("VERIFIED");
        statusList.add("CLOSED");
        statusList.add("REJECTED");
        return statusList;
    }

    public ModelAndView AddTxn(HttpServletRequest request,
                               HttpServletResponse response, TransactionForm transactionForm) {
        log.info(" Inside AddTxn method of TransactionController ");
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        //get all the make list for displaying in search
        List<MakeVO> makeVOs =  null;
        try {
            makeVOs = getMakeDelegate().listAllMakes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(makeVOs != null){
            for(MakeVO makeVO:makeVOs){
                log.info("make vo is"+makeVO);
            }
            transactionForm.setMakeVOs(makeVOs);
        }
        transactionForm.setCurrentTransaction(new TransactionVO());
        return new ModelAndView("txs/TxnAdd", "transactionForm", transactionForm);
    }

    public ModelAndView SaveTxn(HttpServletRequest request,
                               HttpServletResponse response, TransactionForm transactionForm) {
        log.info(" Inside SaveTxn method of TransactionController ");
        log.info(" form details are "+transactionForm);
        try{
            getTransactionDelegate().saveTransaction(transactionForm.getCurrentTransaction());    
        }catch(Exception e){
            e.printStackTrace();

        }
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setCurrentTransaction(new TransactionVO());
        return List(request,response,transactionForm);
    }
}
