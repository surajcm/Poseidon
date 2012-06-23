package com.poseidon.Transaction.web.controller;

import com.poseidon.Customer.web.controller.CustomerController;
import com.poseidon.Customer.web.form.CustomerForm;
import com.poseidon.Make.domain.MakeVO;
import com.poseidon.Make.exception.MakeException;
import com.poseidon.Transaction.exception.TransactionException;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.poseidon.Transaction.delegate.TransactionDelegate;
import com.poseidon.Transaction.web.form.TransactionForm;
import com.poseidon.Transaction.domain.TransactionVO;
import com.poseidon.Make.delegate.MakeDelegate;
import com.poseidon.Make.domain.MakeAndModelVO;
import com.poseidon.Customer.domain.CustomerVO;
import com.poseidon.Customer.delegate.CustomerDelegate;
import com.poseidon.Customer.exception.CustomerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

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

    private CustomerDelegate customerDelegate;
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

    public CustomerDelegate getCustomerDelegate() {
        return customerDelegate;
    }

    public void setCustomerDelegate(CustomerDelegate customerDelegate) {
        this.customerDelegate = customerDelegate;
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
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = getMakeDelegate().fetchMakes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (makeVOs != null) {
            for (MakeVO makeVO : makeVOs) {
                log.info("make vo is" + makeVO);
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
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = getMakeDelegate().fetchMakes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (makeVOs != null) {
            for (MakeVO makeVO : makeVOs) {
                log.info("make vo is" + makeVO);
            }
            transactionForm.setMakeVOs(makeVOs);
            if (makeVOs.size() > 0) {
                List<MakeAndModelVO> makeAndModelVOs = null;
                try {
                    log.info("The selected make id is " + makeVOs.get(0).getId());
                    makeAndModelVOs = getMakeDelegate().getAllModelsFromMakeId(makeVOs.get(0).getId());
                    if (makeAndModelVOs != null) {
                        transactionForm.setMakeAndModelVOs(makeAndModelVOs);
                        for (MakeAndModelVO makeAndModelVO : makeAndModelVOs) {
                            log.info("makeAndModel vo is" + makeAndModelVO);
                        }
                    }
                } catch (MakeException e) {
                    e.printStackTrace();
                }
            }
        }
        transactionForm.setCurrentTransaction(new TransactionVO());
        transactionForm.setCustomerVO(new CustomerVO());
        return new ModelAndView("txs/TxnAdd", "transactionForm", transactionForm);
    }

    public ModelAndView SaveTxn(HttpServletRequest request,
                                HttpServletResponse response, TransactionForm transactionForm) {
        log.info(" Inside SaveTxn method of TransactionController ");
        log.info(" form details are " + transactionForm);
        TransactionVO transactionVO = transactionForm.getCurrentTransaction();
        transactionVO.setDateReported(new Date());
        transactionVO.setCreatedOn(new Date());
        transactionVO.setModifiedOn(new Date());
        transactionVO.setCreatedBy(transactionForm.getLoggedInUser());
        transactionVO.setModifiedBy(transactionForm.getLoggedInUser());
        transactionVO.setStatus("NEW");
        try {
            if (transactionVO.getCustomerId() == null) {
                try {
                    getCustomerDelegate().saveCustomer(transactionForm.getCustomerVO());
                    //get the customer Id
                    transactionForm.getCustomerVO().setStartsWith(Boolean.FALSE);
                    transactionForm.getCustomerVO().setIncludes(Boolean.FALSE);
                    List<CustomerVO> customerVOs = getCustomerDelegate().searchCustomer(transactionForm.getCustomerVO());
                    if (customerVOs != null && customerVOs.size() > 0) {
                        transactionForm.getCustomerVO().setCustomerId(customerVOs.get(0).getCustomerId());
                        transactionVO.setCustomerId(customerVOs.get(0).getCustomerId());
                        log.info("the customer id from db is " + customerVOs.get(0).getCustomerId());
                    }
                } catch (CustomerException e) {
                    e.printStackTrace();
                }
            }
            getTransactionDelegate().saveTransaction(transactionVO);
            transactionForm.setStatusMessage("Successfully added the Transaction");
            transactionForm.setStatusMessageType("success");
        } catch (TransactionException e) {
            transactionForm.setStatusMessage("Unable to create a new transaction due to a Data base error");
            transactionForm.setStatusMessageType("error");
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(TransactionException.DATABASE_ERROR)) {
                log.info(" An error occurred while fetching data from database. !! ");
            } else {
                log.info(" An Unknown Error has been occurred !!");
            }

        }catch (Exception e){
            transactionForm.setStatusMessage("Unable to create the new Transaction");
            transactionForm.setStatusMessageType("error");
            e.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");
        }
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setCurrentTransaction(new TransactionVO());
        return List(request, response, transactionForm);
    }

    public void UpdateModelAjax(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse) {
        StringBuffer responseString = new StringBuffer();

        String selectMakeId = httpServletRequest.getParameter("selectMakeId");
        //get all the models for this make id
        List<MakeAndModelVO> makeAndModelVOs = null;
        try {
            makeAndModelVOs = getMakeDelegate().getAllModelsFromMakeId(Long.valueOf(selectMakeId));
            if (makeAndModelVOs != null && makeAndModelVOs.size() > 0) {
                for (MakeAndModelVO makeAndModelVO : makeAndModelVOs) {
                    responseString.append("#start#");
                    responseString.append("#id#").append(makeAndModelVO.getModelId()).append("#id#");
                    responseString.append("#modelName#").append(makeAndModelVO.getModelName()).append("#modelName#");
                    responseString.append("#start#");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        // get a id-name combination, which is splittable by js
        httpServletResponse.setContentType("text/plain");
        PrintWriter out = null;
        try {
            out = httpServletResponse.getWriter();
            out.print(responseString.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return abc;
    }

    public ModelAndView SearchTxn(HttpServletRequest request,
                                  HttpServletResponse response, TransactionForm transactionForm) {
        log.info(" Inside SearchTxn method of TransactionController ");
        log.info(" form details are " + transactionForm);
        log.info(" form search details are " + transactionForm.getSearchTransaction());
        List<TransactionVO> transactionVOs = null;
        try {
            transactionVOs = getTransactionDelegate().searchTransactions(transactionForm.getSearchTransaction());
            transactionForm.setStatusMessage("Found " + transactionVOs.size() + " Transactions ");
            transactionForm.setStatusMessageType("info");
        } catch (TransactionException e) {
            transactionForm.setStatusMessage("Unable to search due to a data base error");
            transactionForm.setStatusMessageType("error");
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(TransactionException.DATABASE_ERROR)) {
                log.info(" An error occurred while fetching data from database. !! ");
            } else {
                log.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            transactionForm.setStatusMessage("Unable to search ");
            transactionForm.setStatusMessageType("error");
            e1.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");

        }
        if (transactionVOs != null) {
            for (TransactionVO transactionVO : transactionVOs) {
                log.info(" transaction vo is " + transactionVO);
            }
            transactionForm.setTransactionsList(transactionVOs);
        }
        //get all the make list for displaying in search
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = getMakeDelegate().fetchMakes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (makeVOs != null) {
            for (MakeVO makeVO : makeVOs) {
                log.info("make vo is" + makeVO);
            }
            transactionForm.setMakeVOs(makeVOs);
        }
        //transactionForm.setSearchTransaction(new TransactionVO());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setStatusList(populateStatus());
        return new ModelAndView("txs/TransactionList", "transactionForm", transactionForm);
    }

    public ModelAndView EditTxn(HttpServletRequest request,
                                HttpServletResponse response, TransactionForm transactionForm) {
        log.info(" EditTxn method of TransactionController ");

        log.info(" transactionForm is " + transactionForm.toString());
        TransactionVO transactionVO = null;
        CustomerVO customerVO = null;
        try {
            transactionVO = getTransactionDelegate().fetchTransactionFromId(transactionForm.getId());
            if(transactionVO != null && transactionVO.getCustomerId() != null && transactionVO.getCustomerId() > 0 ){
                customerVO = getCustomerDelegate().getCustomerFromId(transactionVO.getCustomerId());
            }
            if(transactionVO != null && transactionVO.getMakeId() != null && transactionVO.getMakeId() > 0){
                List<MakeVO> makeVOs = null;
                try {
                    makeVOs = getMakeDelegate().fetchMakes();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (makeVOs != null) {
                    for (MakeVO makeVO : makeVOs) {
                        log.info("make vo is" + makeVO);
                    }
                    transactionForm.setMakeVOs(makeVOs);
                }
                List<MakeAndModelVO> makeAndModelVOs = null;
                makeAndModelVOs = getMakeDelegate().getAllModelsFromMakeId(transactionVO.getMakeId());
                if (makeAndModelVOs != null) {
                    transactionForm.setMakeAndModelVOs(makeAndModelVOs);
                    for (MakeAndModelVO makeAndModelVO : makeAndModelVOs) {
                        log.info("makeAndModel vo is" + makeAndModelVO);
                    }
                }
            }
        } catch (TransactionException e) {
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(TransactionException.DATABASE_ERROR)) {
                log.info(" An error occurred while fetching data from database. !! ");
            } else {
                log.info(" An Unknown Error has been occurred !!");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");
        }
        if(transactionVO != null ){
            log.info("transactionVO "+transactionVO);
        }
        transactionForm.setCurrentTransaction(transactionVO);
        if(customerVO != null){
            transactionForm.setCustomerVO(customerVO);
        }else {
            transactionForm.setCustomerVO(new CustomerVO());
        }
        transactionForm.setStatusList(populateStatus());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        return new ModelAndView("txs/TxnEdit", "transactionForm", transactionForm);
    }

    public ModelAndView updateTxn(HttpServletRequest request,
                                  HttpServletResponse response, TransactionForm transactionForm) {
        log.info(" updateTxn method of TransactionController ");
        log.info("TransactionForm values are "+transactionForm);
        transactionForm.getCurrentTransaction().setModifiedBy(transactionForm.getLoggedInUser());
        transactionForm.getCurrentTransaction().setModifiedOn(new Date());
        log.info("TransactionForm, current transactions are values are "+transactionForm.getCurrentTransaction());
        try {
            getTransactionDelegate().updateTransaction(transactionForm.getCurrentTransaction());
            transactionForm.setStatusMessage("Successfully updated the Transaction");
            transactionForm.setStatusMessageType("success");
        }catch (TransactionException e){
            transactionForm.setStatusMessage("Unable to update the selected transaction due to a Data base error");
            transactionForm.setStatusMessageType("error");
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(TransactionException.DATABASE_ERROR)) {
                log.info(" An error occurred while fetching data from database. !! ");
            } else {
                log.info(" An Unknown Error has been occurred !!");
            }
        }catch (Exception e){
            transactionForm.setStatusMessage("Unable to update the selected Transaction");
            transactionForm.setStatusMessageType("error");
            e.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");
        }
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
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = getMakeDelegate().fetchMakes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (makeVOs != null) {
            for (MakeVO makeVO : makeVOs) {
                log.info("make vo is" + makeVO);
            }
            transactionForm.setMakeVOs(makeVOs);
        }
        transactionForm.setSearchTransaction(new TransactionVO());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setStatusList(populateStatus());
        return new ModelAndView("txs/TransactionList", "transactionForm", transactionForm);
    }

    public ModelAndView DeleteTxn(HttpServletRequest request,
                                  HttpServletResponse response, TransactionForm transactionForm) {
        log.info(" DeleteTxn method of TransactionController ");
        log.info("TransactionForm values are "+transactionForm);
        try {
            getTransactionDelegate().deleteTransaction(transactionForm.getId());
            transactionForm.setStatusMessage("Successfully deleted the Transaction");
            transactionForm.setStatusMessageType("success");
        }catch (TransactionException e){
            transactionForm.setStatusMessage("Unable to delete the selected transaction due to a Data base error");
            transactionForm.setStatusMessageType("error");
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(TransactionException.DATABASE_ERROR)) {
                log.info(" An error occurred while fetching data from database. !! ");
            } else {
                log.info(" An Unknown Error has been occurred !!");
            }
        }catch (Exception e){
            transactionForm.setStatusMessage("Unable to delete the selected Transaction");
            transactionForm.setStatusMessageType("error");
            e.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");
        }
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
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = getMakeDelegate().fetchMakes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (makeVOs != null) {
            for (MakeVO makeVO : makeVOs) {
                log.info("make vo is" + makeVO);
            }
            transactionForm.setMakeVOs(makeVOs);
        }
        transactionForm.setSearchTransaction(new TransactionVO());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setStatusList(populateStatus());
        return new ModelAndView("txs/TransactionList", "transactionForm", transactionForm);
    }

}
