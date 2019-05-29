package com.poseidon.transaction.web.controller;

import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;
import com.poseidon.customer.service.CustomerService;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.make.service.MakeService;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.exception.TransactionException;
import com.poseidon.transaction.service.TransactionService;
import com.poseidon.transaction.web.form.TransactionForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 3:33:20 PM
 */
@Controller
//@RequestMapping("/txs")
@SuppressWarnings("unused")
public class TransactionController {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionController.class);
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final String DATA_FROM_DATABASE = " An error occurred while fetching data from database. !! ";
    private static final String UNKNOWN_ERROR_HAS_BEEN_OCCURRED = " An Unknown Error has been occurred !!";
    private static final String EXCEPTION_IN_CONTROLLER = " Exception type in controller {}";
    private static final String TRANSACTION_VO = " transaction vo is {}";
    private static final String MAKE_VO = "make vo is {}";
    private static final String TRANSACTION_LIST = "txs/TransactionList";
    private static final String TRANSACTION_FORM = "transactionForm";

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MakeService makeService;

    @Autowired
    private CustomerService customerService;

    /**
     * List all transactions
     *
     * @param transactionForm TransactionForm
     * @return view
     */
    @PostMapping(value = "/txs/List.htm")
    public ModelAndView list(TransactionForm transactionForm) {
        LOG.info(" Inside List method of TransactionController ");
        LOG.info(" form details are {}", transactionForm);
        List<TransactionVO> transactionVOs = null;
        try {
            transactionVOs = transactionService.listAllTransactions();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (transactionVOs != null) {
            transactionVOs.stream().map(transactionVO -> " transaction vo is " + transactionVO).forEach(LOG::info);
            transactionForm.setTransactionsList(transactionVOs);
        }
        //get all the make list for displaying in search
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = makeService.fetchMakes();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        if (makeVOs != null) {
            makeVOs.stream().map(makeVO -> "make vo is" + makeVO).forEach(LOG::info);
            transactionForm.setMakeVOs(makeVOs);
        }
        transactionForm.setSearchTransaction(new TransactionVO());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setStatusList(populateStatus());
        return new ModelAndView(TRANSACTION_LIST, TRANSACTION_FORM, transactionForm);
    }

    private List<String> populateStatus() {
        List<String> statusList = new ArrayList<>();
        statusList.add("NEW");
        statusList.add("ACCEPTED");
        statusList.add("VERIFIED");
        statusList.add("CLOSED");
        statusList.add("REJECTED");
        statusList.add("INVOICED");
        return statusList;
    }

    /**
     * add a new transaction
     *
     * @param transactionForm TransactionForm
     * @return view
     */
    @PostMapping(value = "/txs/AddTxn.htm")
    public ModelAndView addTxn(TransactionForm transactionForm) {
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
            makeVOs.stream().map(makeVO -> "make vo is" + makeVO).forEach(LOG::info);
            transactionForm.setMakeVOs(makeVOs);
            if (!makeVOs.isEmpty()) {
                List<MakeAndModelVO> makeAndModelVOs;
                LOG.info("The selected make id is {}", makeVOs.get(0).getId());
                makeAndModelVOs = makeService.getAllModelsFromMakeId(makeVOs.get(0).getId());
                if (makeAndModelVOs != null) {
                    transactionForm.setMakeAndModelVOs(makeAndModelVOs);
                    makeAndModelVOs.stream().map(makeAndModelVO -> "makeAndModel vo is" + makeAndModelVO)
                            .forEach(LOG::info);
                }
            }
        }
        transactionForm.setCurrentTransaction(new TransactionVO());
        transactionForm.setCustomerVO(new CustomerVO());
        return new ModelAndView("txs/TxnAdd", TRANSACTION_FORM, transactionForm);
    }

    /**
     * save the current transaction
     *
     * @param transactionForm TransactionForm
     * @return view
     */
    @PostMapping(value = "/txs/SaveTxn.htm")
    public ModelAndView saveTxn(TransactionForm transactionForm) {
        LOG.info(" Inside SaveTxn method of TransactionController ");
        LOG.info(" form details are {} ", transactionForm);
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
                    LOG.info("the customer id from db is  {}", customerId);

                } catch (CustomerException e) {
                    LOG.error(e.getLocalizedMessage());
                }
            }
            String tagNo = transactionService.saveTransaction(transactionVO);
            transactionForm.setStatusMessage("Successfully added the transaction, Tag Number is " + tagNo);
            transactionForm.setStatusMessageType(SUCCESS);
        } catch (TransactionException e) {
            transactionForm.setStatusMessage("Unable to create a new transaction due to a Data base error");
            transactionForm.setStatusMessageType(ERROR);
            LOG.error(e.getLocalizedMessage());
            LOG.error(EXCEPTION_IN_CONTROLLER, e.exceptionType);
            if (e.getExceptionType().equalsIgnoreCase(TransactionException.DATABASE_ERROR)) {
                LOG.info(DATA_FROM_DATABASE);
            } else {
                LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
            }

        } catch (Exception e) {
            transactionForm.setStatusMessage("Unable to create the new transaction");
            transactionForm.setStatusMessageType(ERROR);
            LOG.error(e.getLocalizedMessage());
            LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
        }
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setCurrentTransaction(new TransactionVO());
        return list(transactionForm);
    }

    /**
     * update model drop down via ajax
     *
     * @param httpServletRequest  req
     * @param httpServletResponse res
     */
    @RequestMapping(value = "/txs/UpdateModelAjax.htm", method = {RequestMethod.GET, RequestMethod.POST})
    public void updateModelAjax(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse) {
        String responseString = "";

        String selectMakeId = httpServletRequest.getParameter("selectMakeId");
        //get all the models for this make id
        LOG.info(" At UpdateModelAjax, selectMakeId is : {}", selectMakeId);
        List<MakeAndModelVO> makeAndModelVOs;
        try {
            makeAndModelVOs = makeService.getAllModelsFromMakeId(Long.valueOf(selectMakeId));
            if (makeAndModelVOs != null && !makeAndModelVOs.isEmpty()) {
                responseString = makeAndModelVOs.stream()
                        .map(makeAndModelVO -> "#start#" + "#id#" + makeAndModelVO.getModelId()
                                + "#id#" + "#modelName#" + makeAndModelVO.getModelName()
                                + "#modelName#" + "#start#").collect(Collectors.joining());
            }
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());

        }
        // get a id-name combination, which is splittable by js
        httpServletResponse.setContentType("text/plain");
        PrintWriter out;
        try {
            out = httpServletResponse.getWriter();
            out.print(responseString);
            out.flush();
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
        }
    }

    /**
     * search transactions
     *
     * @param transactionForm TransactionForm
     * @return view
     */
    @PostMapping(value = "/txs/SearchTxn.htm")
    public ModelAndView searchTxn(TransactionForm transactionForm) {
        LOG.info(" Inside SearchTxn method of TransactionController ");
        LOG.info(" form details are {}", transactionForm);
        LOG.info(" form search details are {}", transactionForm.getSearchTransaction());
        List<TransactionVO> transactionVOs = null;
        try {
            transactionVOs = transactionService.searchTransactions(transactionForm.getSearchTransaction());
            transactionForm.setStatusMessage("Found " + transactionVOs.size() + " Transactions ");
            transactionForm.setStatusMessageType("info");
        } catch (TransactionException e) {
            transactionForm.setStatusMessage("Unable to search due to a data base error");
            transactionForm.setStatusMessageType(ERROR);
            LOG.error(e.getLocalizedMessage());
            LOG.error(EXCEPTION_IN_CONTROLLER, e.exceptionType);
            if (e.getExceptionType().equalsIgnoreCase(TransactionException.DATABASE_ERROR)) {
                LOG.info(DATA_FROM_DATABASE);
            } else {
                LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
            }

        } catch (Exception e1) {
            transactionForm.setStatusMessage("Unable to search ");
            transactionForm.setStatusMessageType(ERROR);
            LOG.error(e1.getLocalizedMessage());
            LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);

        }
        if (transactionVOs != null) {
            transactionVOs.forEach(transactionVO -> LOG.debug(TRANSACTION_VO, transactionVO));
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
            makeVOs.forEach(makeVO -> LOG.debug(MAKE_VO, makeVO));
            transactionForm.setMakeVOs(makeVOs);
        }
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setStatusList(populateStatus());
        return new ModelAndView(TRANSACTION_LIST, TRANSACTION_FORM, transactionForm);
    }

    /**
     * edit a transaction
     *
     * @param transactionForm TransactionForm
     * @return view
     */
    @PostMapping(value = "/txs/EditTxn.htm")
    public ModelAndView editTxn(TransactionForm transactionForm) {
        LOG.info(" EditTxn method of TransactionController ");
        LOG.info(" transactionForm is {}", transactionForm);
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
                    makeVOs.forEach(makeVO -> LOG.info(MAKE_VO, makeVO));
                    transactionForm.setMakeVOs(makeVOs);
                }
                List<MakeAndModelVO> makeAndModelVOs;
                makeAndModelVOs = makeService.getAllModelsFromMakeId(transactionVO.getMakeId());
                if (makeAndModelVOs != null) {
                    transactionForm.setMakeAndModelVOs(makeAndModelVOs);
                    makeAndModelVOs.forEach(makeAndModelVO -> LOG.info("makeAndModel vo is {}", makeAndModelVO));
                }
            }
        } catch (TransactionException e) {
            LOG.error(e.getLocalizedMessage());
            LOG.error(EXCEPTION_IN_CONTROLLER, e.exceptionType);
            if (e.getExceptionType().equalsIgnoreCase(TransactionException.DATABASE_ERROR)) {
                LOG.info(DATA_FROM_DATABASE);
            } else {
                LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
            }
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
            LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
        }
        if (transactionVO != null) {
            LOG.info("transactionVO {}", transactionVO);
        }
        transactionForm.setCurrentTransaction(transactionVO);
        transactionForm.setCustomerVO(Objects.requireNonNullElseGet(customerVO, CustomerVO::new));
        transactionForm.setStatusList(populateStatus());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        return new ModelAndView("txs/TxnEdit", TRANSACTION_FORM, transactionForm);
    }

    /**
     * update transaction
     *
     * @param transactionForm TransactionForm
     * @return view
     */
    @PostMapping(value = "/txs/updateTxn.htm")
    public ModelAndView updateTxn(TransactionForm transactionForm) {
        LOG.info(" updateTxn method of TransactionController ");
        LOG.info("TransactionForm values are {}", transactionForm);
        transactionForm.getCurrentTransaction().setModifiedBy(transactionForm.getLoggedInUser());
        transactionForm.getCurrentTransaction().setModifiedOn(new Date());
        LOG.info("TransactionForm, current transactions are values are {}", transactionForm.getCurrentTransaction());
        try {
            transactionService.updateTransaction(transactionForm.getCurrentTransaction());
            transactionForm.setStatusMessage("Successfully updated the transaction");
            transactionForm.setStatusMessageType(SUCCESS);
        } catch (Exception e) {
            transactionForm.setStatusMessage("Unable to update the selected transaction");
            transactionForm.setStatusMessageType(ERROR);
            LOG.error(e.getLocalizedMessage());
            LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
        }
        List<TransactionVO> transactionVOs = null;
        try {
            transactionVOs = transactionService.listTodaysTransactions();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (transactionVOs != null) {
            transactionVOs.forEach(transactionVO -> LOG.info(TRANSACTION_VO, transactionVO));
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
            makeVOs.forEach(makeVO -> LOG.info(MAKE_VO, makeVO));
            transactionForm.setMakeVOs(makeVOs);
        }
        transactionForm.setSearchTransaction(new TransactionVO());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setStatusList(populateStatus());
        return new ModelAndView(TRANSACTION_LIST, TRANSACTION_FORM, transactionForm);
    }

    /**
     * delete transaction
     *
     * @param transactionForm TransactionForm
     * @return view
     */
    @PostMapping(value = "/txs/DeleteTxn.htm")
    public ModelAndView deleteTxn(TransactionForm transactionForm) {
        LOG.info(" DeleteTxn method of TransactionController ");
        LOG.info("TransactionForm values are {}", transactionForm);
        try {
            transactionService.deleteTransaction(transactionForm.getId());
            transactionForm.setStatusMessage("Successfully deleted the transaction");
            transactionForm.setStatusMessageType(SUCCESS);
        } catch (TransactionException e) {
            transactionForm.setStatusMessage("Unable to delete the selected transaction due to a Data base error");
            transactionForm.setStatusMessageType(ERROR);
            LOG.error(e.getLocalizedMessage());
            LOG.error(EXCEPTION_IN_CONTROLLER, e.exceptionType);
            if (e.getExceptionType().equalsIgnoreCase(TransactionException.DATABASE_ERROR)) {
                LOG.info(DATA_FROM_DATABASE);
            } else {
                LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
            }
        } catch (Exception e) {
            transactionForm.setStatusMessage("Unable to delete the selected transaction");
            transactionForm.setStatusMessageType(ERROR);
            LOG.error(e.getLocalizedMessage());
            LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
        }
        List<TransactionVO> transactionVOs = null;
        try {
            transactionVOs = transactionService.listTodaysTransactions();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (transactionVOs != null) {
            transactionVOs.forEach(transactionVO -> LOG.info(TRANSACTION_VO, transactionVO));
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
            makeVOs.forEach(makeVO -> LOG.info(MAKE_VO, makeVO));
            transactionForm.setMakeVOs(makeVOs);
        }
        transactionForm.setSearchTransaction(new TransactionVO());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setStatusList(populateStatus());
        return new ModelAndView(TRANSACTION_LIST, TRANSACTION_FORM, transactionForm);
    }


}
