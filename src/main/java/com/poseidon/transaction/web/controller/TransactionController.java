package com.poseidon.transaction.web.controller;

import com.poseidon.customer.dao.entities.Customer;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.service.CustomerService;
import com.poseidon.init.util.CommonUtils;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.make.service.MakeService;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.service.TransactionService;
import com.poseidon.transaction.web.form.TransactionForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.poseidon.transaction.web.controller.TransactionStatus.populateStatus;

/**
 * user: Suraj.
 * Date: Jun 2, 2012
 * Time: 3:33:20 PM
 */
@Controller
@SuppressWarnings("unused")
public class TransactionController {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionController.class);
    private static final String SUCCESS = "success";
    private static final String DANGER = "danger";
    private static final String DATA_FROM_DATABASE = " An error occurred while fetching data from database. !! ";
    private static final String UNKNOWN_ERROR = " An Unknown Error has been occurred !!";
    private static final String EXCEPTION_IN_CONTROLLER = " Exception type in controller {}";
    private static final String TRANSACTION_VO = " transaction vo is {}";
    private static final String MAKE_VO = "make vo is {}";
    private static final String TRANSACTION_LIST = "txs/TransactionList";
    private static final String TRANSACTION_FORM = "transactionForm";

    private final TransactionService transactionService;

    private final MakeService makeService;

    private final CustomerService customerService;

    public TransactionController(final TransactionService transactionService,
                                 final MakeService makeService,
                                 final CustomerService customerService) {
        this.transactionService = transactionService;
        this.makeService = makeService;
        this.customerService = customerService;
    }

    /**
     * List all transactions.
     *
     * @param transactionForm TransactionForm
     * @return view
     */
    @PostMapping("/txs/listTransactions")
    public String listPage(final TransactionForm transactionForm, final Model model) {
        var transactionVOs = transactionService.listAllTransactions();
        if (transactionVOs != null) {
            transactionVOs.stream().map(transactionVO -> " transaction vo is " + transactionVO).forEach(LOG::info);
            transactionForm.setTransactionsList(transactionVOs);
        }
        //get all the make list for displaying in search
        transactionForm.setMakeVOs(getMakeVOS());
        transactionForm.setSearchTransaction(new TransactionVO());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setStatusList(populateStatus());
        model.addAttribute(TRANSACTION_FORM, transactionForm);
        return TRANSACTION_LIST;
    }

    private List<MakeVO> getMakeVOS() {
        return makeService.fetchMakes();
    }


    /**
     * add a new transaction.
     *
     * @param transactionForm TransactionForm
     * @return view
     */
    @PostMapping("/txs/addTxn")
    public String addTxn(final TransactionForm transactionForm, final Model model) {
        LOG.info("AddTxn method of TransactionController ");
        //get all the make list for displaying in search
        var makeVOs = getMakeVOS();
        List<MakeAndModelVO> makeAndModelVOs = null;
        if (makeVOs != null && !makeVOs.isEmpty()) {
            transactionForm.setMakeVOs(makeVOs);
            LOG.info("The selected make id is {}", makeVOs.get(0).getId());
            makeAndModelVOs = makeService.getAllModelsFromMakeId(makeVOs.get(0).getId());
        }
        if (makeAndModelVOs != null) {
            transactionForm.setMakeAndModelVOs(makeAndModelVOs);
            makeAndModelVOs.stream().map(makeAndModelVO -> "makeAndModel vo is" + makeAndModelVO)
                    .forEach(LOG::info);
        }
        transactionForm.setCurrentTransaction(new TransactionVO());
        transactionForm.setCustomerVO(new CustomerVO());
        model.addAttribute(TRANSACTION_FORM, transactionForm);
        return "txs/TxnAdd";
    }

    /**
     * save the current transaction.
     *
     * @param transactionForm TransactionForm
     * @return view
     */
    @PostMapping("/txs/saveTxn")
    public String saveTxn(final TransactionForm transactionForm, final Model model) {
        LOG.info("SaveTxn method of TransactionController ");
        var sanitizedForm = CommonUtils.sanitizedString(transactionForm.toString());
        LOG.info("form details are {} ", sanitizedForm);
        var transactionVO = transactionForm.getCurrentTransaction();
        if (transactionForm.getCurrentTransaction() != null) {
            transactionVO.setCreatedOn(OffsetDateTime.now(ZoneId.systemDefault()));
            transactionVO.setModifiedOn(OffsetDateTime.now(ZoneId.systemDefault()));
            transactionVO.setCreatedBy(transactionForm.getLoggedInUser());
            transactionVO.setModifiedBy(transactionForm.getLoggedInUser());
            transactionVO.setStatus(TransactionStatus.NEW.name());
        }
        if (hasValidCustomerId(transactionForm)) {
            transactionVO.setCustomerId(transactionForm.getCustomerVO().getCustomerId());
        }
        if (transactionVO != null && transactionVO.getCustomerId() == null) {
            transactionForm.getCustomerVO().setCreatedOn(OffsetDateTime.now(ZoneId.systemDefault()));
            transactionForm.getCustomerVO().setModifiedOn(OffsetDateTime.now(ZoneId.systemDefault()));
            transactionForm.getCustomerVO().setCreatedBy(transactionForm.getLoggedInUser());
            transactionForm.getCustomerVO().setModifiedBy(transactionForm.getLoggedInUser());
            var customerInput = convertToSingleCustomer(transactionForm.getCustomerVO());
            var customer = customerService.saveCustomer(transactionForm.getCustomerVO(), customerInput);
            var customerId = customer.getCustomerId();
            transactionForm.getCustomerVO().setCustomerId(customerId);
            transactionVO.setCustomerId(customerId);
            LOG.info("the customer id from db is  {}", customerId);
        }
        String tagNo = transactionService.saveTransaction(transactionVO);
        transactionForm.setStatusMessage("Successfully added the transaction, Tag Number is " + tagNo);
        transactionForm.setStatusMessageType(SUCCESS);
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setCurrentTransaction(new TransactionVO());
        model.addAttribute(TRANSACTION_FORM, transactionForm);
        return listPage(transactionForm, model);
    }

    private Customer convertToSingleCustomer(final CustomerVO currentCustomerVO) {
        var customer = new Customer();
        customer.setName(currentCustomerVO.getCustomerName());
        customer.setAddress(currentCustomerVO.getAddress());
        customer.setPhone(currentCustomerVO.getPhoneNo());
        customer.setMobile(currentCustomerVO.getMobile());
        customer.setEmail(currentCustomerVO.getEmail());
        customer.setCreatedBy(currentCustomerVO.getCreatedBy());
        customer.setModifiedBy(currentCustomerVO.getModifiedBy());
        return customer;
    }

    private boolean hasValidCustomerId(final TransactionForm transactionForm) {
        return transactionForm.getCustomerVO() != null
                && transactionForm.getCustomerVO().getCustomerId() != null
                && transactionForm.getCustomerVO().getCustomerId() > 0;
    }

    /**
     * search transactions.
     *
     * @param transactionForm TransactionForm
     * @return view
     */
    @PostMapping("/txs/searchTxn")
    public String searchTxn(final TransactionForm transactionForm, final Model model) {
        LOG.info("SearchTxn method of TransactionController ");
        var sanitizedForm = CommonUtils.sanitizedString(transactionForm.toString());
        LOG.info("form details are {}", sanitizedForm);
        if (transactionForm.getSearchTransaction() != null) {
            var sanitizedSearch = CommonUtils.sanitizedString(
                    transactionForm.getSearchTransaction().toString());
            LOG.info("form search details are {}", sanitizedSearch);
        }
        var transactionVOs = transactionService.searchTransactions(
                transactionForm.getSearchTransaction());
        transactionForm.setStatusMessage("Found " + transactionVOs.size() + " Transactions ");
        transactionForm.setStatusMessageType("info");
        if (!transactionVOs.isEmpty()) {
            transactionVOs.forEach(transactionVO -> LOG.debug(TRANSACTION_VO, transactionVO));
            transactionForm.setTransactionsList(transactionVOs);
        } else {
            transactionForm.setStatusMessage("Unable to find transaction");
            transactionForm.setStatusMessageType(DANGER);
        }
        transactionForm.setMakeVOs(getMakeVOS());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setStatusList(populateStatus());
        model.addAttribute(TRANSACTION_FORM, transactionForm);
        return TRANSACTION_LIST;
    }

    /**
     * edit a transaction.
     *
     * @param transactionForm TransactionForm
     * @return view
     */
    @PostMapping("/txs/editTxn")
    public String editTxn(final TransactionForm transactionForm, final Model model) {
        LOG.info("EditTxn method of TransactionController ");
        var sanitizedForm = CommonUtils.sanitizedString(transactionForm.toString());
        LOG.info("transactionForm is {}", sanitizedForm);
        var transactionVO = transactionService.fetchTransactionFromId(transactionForm.getId());
        if (transactionVO != null) {
            LOG.info("transactionVO {}", transactionVO);
        }
        if (transactionVO != null && transactionVO.getMakeId() != null && transactionVO.getMakeId() > 0) {
            transactionForm.setMakeVOs(getMakeVOS());
            var makeAndModelVOs = makeService.getAllModelsFromMakeId(transactionVO.getMakeId());
            if (makeAndModelVOs != null) {
                transactionForm.setMakeAndModelVOs(makeAndModelVOs);
                makeAndModelVOs.forEach(makeAndModelVO -> LOG.info("makeAndModel vo is {}", makeAndModelVO));
            }
        }
        transactionForm.setCurrentTransaction(transactionVO);
        var customerVO = getCustomerVOFromTransaction(transactionVO);
        customerVO.ifPresent(vo -> transactionForm.setCustomerVO(Objects.requireNonNullElseGet(vo, CustomerVO::new)));
        transactionForm.setStatusList(populateStatus());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        model.addAttribute(TRANSACTION_FORM, transactionForm);
        return "txs/TxnEdit";
    }

    private Optional<CustomerVO> getCustomerVOFromTransaction(final TransactionVO transactionVO) {
        Optional<CustomerVO> customerVO = Optional.empty();
        if (transactionVO != null && transactionVO.getCustomerId() != null && transactionVO.getCustomerId() > 0) {
            customerVO = customerService.getCustomerFromId(transactionVO.getCustomerId());
        }
        return customerVO;
    }

    /**
     * update transaction.
     *
     * @param transactionForm TransactionForm
     * @return view
     */
    @PostMapping("/txs/updateTxn")
    public String updateTxn(final TransactionForm transactionForm, final Model model) {
        LOG.info("UpdateTxn method of TransactionController ");
        var sanitizedForm = CommonUtils.sanitizedString(transactionForm.toString());
        LOG.info("TransactionForm values are {}", sanitizedForm);
        if (transactionForm.getCurrentTransaction() != null) {
            transactionForm.getCurrentTransaction().setModifiedBy(transactionForm.getLoggedInUser());
            transactionForm.getCurrentTransaction().setModifiedOn(OffsetDateTime.now(ZoneId.systemDefault()));
            var sanitizedCurrent = CommonUtils.sanitizedString(
                    transactionForm.getCurrentTransaction().toString());
            LOG.info("TransactionForm, current transactions are values are {}", sanitizedCurrent);
        }
        try {
            transactionForm.getCurrentTransaction().setCustomerId(transactionForm.getCustomerVO().getCustomerId());
            transactionService.updateTransaction(transactionForm.getCurrentTransaction());
            transactionForm.setStatusMessage("Successfully updated the transaction");
            transactionForm.setStatusMessageType(SUCCESS);
        } catch (Exception ex) {
            transactionForm.setStatusMessage("Unable to update the selected transaction");
            transactionForm.setStatusMessageType(DANGER);
            LOG.error(ex.getLocalizedMessage());
            LOG.info(UNKNOWN_ERROR);
        }
        transactionForm.setTransactionsList(transactionService.listAllTransactions());
        transactionForm.setMakeVOs(getMakeVOS());
        transactionForm.setSearchTransaction(new TransactionVO());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setStatusList(populateStatus());
        model.addAttribute(TRANSACTION_FORM, transactionForm);
        return TRANSACTION_LIST;
    }


    /**
     * delete transaction.
     *
     * @param transactionForm TransactionForm
     * @return view
     */
    @PostMapping("/txs/deleteTxn")
    public String deleteTxn(final TransactionForm transactionForm, final Model model) {
        LOG.info("DeleteTxn method of TransactionController ");
        var sanitizedForm = CommonUtils.sanitizedString(transactionForm.toString());
        LOG.info("TransactionForm values are {}", sanitizedForm);
        transactionService.deleteTransaction(transactionForm.getId());
        transactionForm.setStatusMessage("Successfully deleted the transaction");
        transactionForm.setStatusMessageType(SUCCESS);
        var transactionVOs = transactionService.listAllTransactions();
        if (!transactionVOs.isEmpty()) {
            transactionVOs.forEach(transactionVO -> LOG.info(TRANSACTION_VO, transactionVO));
            transactionForm.setTransactionsList(transactionVOs);
        }
        transactionForm.setMakeVOs(getMakeVOS());
        transactionForm.setSearchTransaction(new TransactionVO());
        transactionForm.setLoggedInRole(transactionForm.getLoggedInRole());
        transactionForm.setLoggedInUser(transactionForm.getLoggedInUser());
        transactionForm.setStatusList(populateStatus());
        model.addAttribute(TRANSACTION_FORM, transactionForm);
        return TRANSACTION_LIST;
    }

    /**
     * update model drop down via ajax.
     *
     * @param selectMakeId selectMakeId
     */
    @PostMapping("/txs/updateModel")
    public @ResponseBody
    List<MakeAndModelVO> updateModel(@ModelAttribute("selectMakeId") final String selectMakeId) {
        var sanitizedMakeId = CommonUtils.sanitizedString(selectMakeId);
        LOG.info("At UpdateModel, selectMakeId is : {}", sanitizedMakeId);
        return makeService.getAllModelsFromMakeId(Long.valueOf(selectMakeId));
    }

    private Map<String, String> getMakeModelMap(final MakeAndModelVO makeAndModelVO) {
        Map<String, String> mmMap = new HashMap<>();
        mmMap.put("id", String.valueOf(makeAndModelVO.getModelId()));
        mmMap.put("modelName", makeAndModelVO.getModelName());
        return mmMap;
    }
}
