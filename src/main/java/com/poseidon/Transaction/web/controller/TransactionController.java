package com.poseidon.Transaction.web.controller;

import com.poseidon.Customer.domain.CustomerVO;
import com.poseidon.Customer.exception.CustomerException;
import com.poseidon.Customer.service.CustomerService;
import com.poseidon.Make.domain.MakeAndModelVO;
import com.poseidon.Make.domain.MakeVO;
import com.poseidon.Make.service.MakeService;
import com.poseidon.Transaction.domain.TransactionVO;
import com.poseidon.Transaction.exception.TransactionException;
import com.poseidon.Transaction.service.TransactionService;
import com.poseidon.Transaction.web.form.TransactionForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 3:33:20 PM
 */
@Controller
public class TransactionController {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionController.class);

    private TransactionService transactionService;

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    private MakeService makeService;

    public void setMakeService(MakeService makeService) {
        this.makeService = makeService;
    }
    private CustomerService customerService;


    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "/txs/List.htm", method = RequestMethod.POST)
    public ModelAndView List(TransactionForm transactionForm) {
        LOG.info(" Inside List method of TransactionController ");
        LOG.info(" form details are" + transactionForm);
        //TransactionForm transactionForm = new TransactionForm();
        List<TransactionVO> transactionVOs = null;
        try {
            transactionVOs = transactionService.listTodaysTransactions();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (transactionVOs != null) {
            for (TransactionVO transactionVO : transactionVOs) {
                LOG.info(" transaction vo is " + transactionVO);
            }
            transactionForm.setTransactionsList(transactionVOs);
        }
        //get all the make list for displaying in search
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = makeService.fetchMakes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (makeVOs != null) {
            for (MakeVO makeVO : makeVOs) {
                LOG.info("make vo is" + makeVO);
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
        statusList.add("INVOICED");
        return statusList;
    }

    @RequestMapping(value = "/txs/AddTxn.htm", method = RequestMethod.POST)
    public ModelAndView AddTxn(TransactionForm transactionForm) {
        LOG.info(" Inside AddTxn method of TransactionController ");
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        //get all the make list for displaying in search
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = makeService.fetchMakes();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (makeVOs != null) {
            for (MakeVO makeVO : makeVOs) {
                LOG.info("make vo is" + makeVO);
            }
            transactionForm.setMakeVOs(makeVOs);
            if (makeVOs.size() > 0) {
                List<MakeAndModelVO> makeAndModelVOs;
                    LOG.info("The selected make id is " + makeVOs.get(0).getId());
                    makeAndModelVOs = makeService.getAllModelsFromMakeId(makeVOs.get(0).getId());
                    if (makeAndModelVOs != null) {
                        transactionForm.setMakeAndModelVOs(makeAndModelVOs);
                        for (MakeAndModelVO makeAndModelVO : makeAndModelVOs) {
                            LOG.info("makeAndModel vo is" + makeAndModelVO);
                        }
                    }

            }
        }
        transactionForm.setCurrentTransaction(new TransactionVO());
        transactionForm.setCustomerVO(new CustomerVO());
        return new ModelAndView("txs/TxnAdd", "transactionForm", transactionForm);
    }

    @RequestMapping(value = "/txs/SaveTxn.htm", method = RequestMethod.POST)
    public ModelAndView SaveTxn(TransactionForm transactionForm) {
        LOG.info(" Inside SaveTxn method of TransactionController ");
        LOG.info(" form details are " + transactionForm);
        TransactionVO transactionVO = transactionForm.getCurrentTransaction();
        transactionVO.setCreatedOn(new Date());
        transactionVO.setModifiedOn(new Date());
        transactionVO.setCreatedBy(transactionForm.getLoggedInUser());
        transactionVO.setModifiedBy(transactionForm.getLoggedInUser());
        transactionVO.setStatus("NEW");
        if (transactionForm.getCustomerVO() != null
                && transactionForm.getCustomerVO().getCustomerId() != null
                && transactionForm.getCustomerVO().getCustomerId() > 0) {
            transactionVO.setCustomerId(transactionForm.getCustomerVO().getCustomerId());
        }
        try {
            if (transactionVO.getCustomerId() == null) {
                try {
                    transactionForm.getCustomerVO().setCreatedOn(new Date());
                    transactionForm.getCustomerVO().setModifiedOn(new Date());
                    transactionForm.getCustomerVO().setCreatedBy(transactionForm.getLoggedInUser());
                    transactionForm.getCustomerVO().setModifiedBy(transactionForm.getLoggedInUser());
                    long customerId = customerService.saveCustomer(transactionForm.getCustomerVO());
                    transactionForm.getCustomerVO().setCustomerId(customerId);
                    transactionVO.setCustomerId(customerId);
                    LOG.info("the customer id from db is " + customerId);

                } catch (CustomerException e) {
                    LOG.error(e.getLocalizedMessage());
                }
            }
            String tagNo = transactionService.saveTransaction(transactionVO);
            transactionForm.setStatusMessage("Successfully added the Transaction, Tag Number is "+tagNo);
            transactionForm.setStatusMessageType("success");
        } catch (TransactionException e) {
            transactionForm.setStatusMessage("Unable to create a new transaction due to a Data base error");
            transactionForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
            LOG.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(TransactionException.DATABASE_ERROR)) {
                LOG.info(" An error occurred while fetching data from database. !! ");
            } else {
                LOG.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e) {
            transactionForm.setStatusMessage("Unable to create the new Transaction");
            transactionForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
            LOG.info(" An Unknown Error has been occurred !!");
        }
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setCurrentTransaction(new TransactionVO());
        return List(transactionForm);
    }

    @RequestMapping(value = "/txs/UpdateModelAjax.htm", method = { RequestMethod.GET, RequestMethod.POST })
    public void UpdateModelAjax(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse) {
        StringBuilder responseString = new StringBuilder();

        String selectMakeId = httpServletRequest.getParameter("selectMakeId");
        //get all the models for this make id
        LOG.info(" At UpdateModelAjax, selectMakeId is :"+ selectMakeId);
        List<MakeAndModelVO> makeAndModelVOs;
        try {
            makeAndModelVOs = makeService.getAllModelsFromMakeId(Long.valueOf(selectMakeId));
            if (makeAndModelVOs != null && makeAndModelVOs.size() > 0) {
                for (MakeAndModelVO makeAndModelVO : makeAndModelVOs) {
                    responseString.append("#start#");
                    responseString.append("#id#").append(makeAndModelVO.getModelId()).append("#id#");
                    responseString.append("#modelName#").append(makeAndModelVO.getModelName()).append("#modelName#");
                    responseString.append("#start#");
                }
            }
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());

        }
        // get a id-name combination, which is splittable by js
        httpServletResponse.setContentType("text/plain");
        PrintWriter out;
        try {
            out = httpServletResponse.getWriter();
            out.print(responseString.toString());
            out.flush();
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
        }
        //return abc;
    }

    @RequestMapping(value = "/txs/SearchTxn.htm", method = RequestMethod.POST)
    public ModelAndView SearchTxn(TransactionForm transactionForm) {
        LOG.info(" Inside SearchTxn method of TransactionController ");
        LOG.info(" form details are " + transactionForm);
        LOG.info(" form search details are " + transactionForm.getSearchTransaction());
        List<TransactionVO> transactionVOs = null;
        try {
            transactionVOs = transactionService.searchTransactions(transactionForm.getSearchTransaction());
            transactionForm.setStatusMessage("Found " + transactionVOs.size() + " Transactions ");
            transactionForm.setStatusMessageType("info");
        } catch (TransactionException e) {
            transactionForm.setStatusMessage("Unable to search due to a data base error");
            transactionForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
            LOG.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(TransactionException.DATABASE_ERROR)) {
                LOG.info(" An error occurred while fetching data from database. !! ");
            } else {
                LOG.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            transactionForm.setStatusMessage("Unable to search ");
            transactionForm.setStatusMessageType("error");
            LOG.error(e1.getLocalizedMessage());
            LOG.info(" An Unknown Error has been occurred !!");

        }
        if (transactionVOs != null) {
            for (TransactionVO transactionVO : transactionVOs) {
                LOG.debug(" transaction vo is " + transactionVO);
            }
            transactionForm.setTransactionsList(transactionVOs);
        }
        //get all the make list for displaying in search
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = makeService.fetchMakes();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (makeVOs != null) {
            for (MakeVO makeVO : makeVOs) {
                LOG.debug("make vo is" + makeVO);
            }
            transactionForm.setMakeVOs(makeVOs);
        }
        //transactionForm.setSearchTransaction(new TransactionVO());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setStatusList(populateStatus());
        return new ModelAndView("txs/TransactionList", "transactionForm", transactionForm);
    }

    @RequestMapping(value = "/txs/EditTxn.htm", method = RequestMethod.POST)
    public ModelAndView EditTxn(TransactionForm transactionForm) {
        LOG.info(" EditTxn method of TransactionController ");

        LOG.info(" transactionForm is " + transactionForm.toString());
        TransactionVO transactionVO = null;
        CustomerVO customerVO = null;
        try {
            transactionVO = transactionService.fetchTransactionFromId(transactionForm.getId());
            if (transactionVO != null && transactionVO.getCustomerId() != null && transactionVO.getCustomerId() > 0) {
                customerVO = customerService.getCustomerFromId(transactionVO.getCustomerId());
            }
            if (transactionVO != null && transactionVO.getMakeId() != null && transactionVO.getMakeId() > 0) {
                List<MakeVO> makeVOs = null;
                try {
                    makeVOs = makeService.fetchMakes();
                } catch (Exception e) {
                    LOG.error(e.getLocalizedMessage());
                }
                if (makeVOs != null) {
                    for (MakeVO makeVO : makeVOs) {
                        LOG.info("make vo is" + makeVO);
                    }
                    transactionForm.setMakeVOs(makeVOs);
                }
                List<MakeAndModelVO> makeAndModelVOs;
                makeAndModelVOs = makeService.getAllModelsFromMakeId(transactionVO.getMakeId());
                if (makeAndModelVOs != null) {
                    transactionForm.setMakeAndModelVOs(makeAndModelVOs);
                    for (MakeAndModelVO makeAndModelVO : makeAndModelVOs) {
                        LOG.info("makeAndModel vo is" + makeAndModelVO);
                    }
                }
            }
        } catch (TransactionException e) {
            LOG.error(e.getLocalizedMessage());
            LOG.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(TransactionException.DATABASE_ERROR)) {
                LOG.info(" An error occurred while fetching data from database. !! ");
            } else {
                LOG.info(" An Unknown Error has been occurred !!");
            }
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
            LOG.info(" An Unknown Error has been occurred !!");
        }
        if (transactionVO != null) {
            LOG.info("transactionVO " + transactionVO);
        }
        transactionForm.setCurrentTransaction(transactionVO);
        if (customerVO != null) {
            transactionForm.setCustomerVO(customerVO);
        } else {
            transactionForm.setCustomerVO(new CustomerVO());
        }
        transactionForm.setStatusList(populateStatus());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        return new ModelAndView("txs/TxnEdit", "transactionForm", transactionForm);
    }

    @RequestMapping(value = "/txs/updateTxn.htm", method = RequestMethod.POST)
    public ModelAndView updateTxn(TransactionForm transactionForm) {
        LOG.info(" updateTxn method of TransactionController ");
        LOG.info("TransactionForm values are " + transactionForm);
        transactionForm.getCurrentTransaction().setModifiedBy(transactionForm.getLoggedInUser());
        transactionForm.getCurrentTransaction().setModifiedOn(new Date());
        LOG.info("TransactionForm, current transactions are values are " + transactionForm.getCurrentTransaction());
        try {
            transactionService.updateTransaction(transactionForm.getCurrentTransaction());
            transactionForm.setStatusMessage("Successfully updated the Transaction");
            transactionForm.setStatusMessageType("success");
        } catch (TransactionException e) {
            transactionForm.setStatusMessage("Unable to update the selected transaction due to a Data base error");
            transactionForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
            LOG.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(TransactionException.DATABASE_ERROR)) {
                LOG.info(" An error occurred while fetching data from database. !! ");
            } else {
                LOG.info(" An Unknown Error has been occurred !!");
            }
        } catch (Exception e) {
            transactionForm.setStatusMessage("Unable to update the selected Transaction");
            transactionForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
            LOG.info(" An Unknown Error has been occurred !!");
        }
        List<TransactionVO> transactionVOs = null;
        try {
            transactionVOs = transactionService.listTodaysTransactions();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (transactionVOs != null) {
            for (TransactionVO transactionVO : transactionVOs) {
                LOG.info(" transaction vo is " + transactionVO);
            }
            transactionForm.setTransactionsList(transactionVOs);
        }
        //get all the make list for displaying in search
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = makeService.fetchMakes();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (makeVOs != null) {
            for (MakeVO makeVO : makeVOs) {
                LOG.info("make vo is" + makeVO);
            }
            transactionForm.setMakeVOs(makeVOs);
        }
        transactionForm.setSearchTransaction(new TransactionVO());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setStatusList(populateStatus());
        return new ModelAndView("txs/TransactionList", "transactionForm", transactionForm);
    }

    @RequestMapping(value = "/txs/DeleteTxn.htm", method = RequestMethod.POST)
    public ModelAndView DeleteTxn(TransactionForm transactionForm) {
        LOG.info(" DeleteTxn method of TransactionController ");
        LOG.info("TransactionForm values are " + transactionForm);
        try {
            transactionService.deleteTransaction(transactionForm.getId());
            transactionForm.setStatusMessage("Successfully deleted the Transaction");
            transactionForm.setStatusMessageType("success");
        } catch (TransactionException e) {
            transactionForm.setStatusMessage("Unable to delete the selected transaction due to a Data base error");
            transactionForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
            LOG.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(TransactionException.DATABASE_ERROR)) {
                LOG.info(" An error occurred while fetching data from database. !! ");
            } else {
                LOG.info(" An Unknown Error has been occurred !!");
            }
        } catch (Exception e) {
            transactionForm.setStatusMessage("Unable to delete the selected Transaction");
            transactionForm.setStatusMessageType("error");
            LOG.error(e.getLocalizedMessage());
            LOG.info(" An Unknown Error has been occurred !!");
        }
        List<TransactionVO> transactionVOs = null;
        try {
            transactionVOs = transactionService.listTodaysTransactions();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (transactionVOs != null) {
            for (TransactionVO transactionVO : transactionVOs) {
                LOG.info(" transaction vo is " + transactionVO);
            }
            transactionForm.setTransactionsList(transactionVOs);
        }
        //get all the make list for displaying in search
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = makeService.fetchMakes();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (makeVOs != null) {
            for (MakeVO makeVO : makeVOs) {
                LOG.info("make vo is" + makeVO);
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
