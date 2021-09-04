package com.poseidon.customer.web.controller;

import com.poseidon.customer.domain.CustomerAdditionalDetailsVO;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.service.impl.CustomerService;
import com.poseidon.customer.web.form.CustomerForm;
import com.poseidon.transaction.web.form.TransactionForm;
import com.poseidon.util.CommonUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;


@Controller
//@RequestMapping("/customer")
@SuppressWarnings("unused")
public class CustomerController {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);
    private static final String CUSTOMER_FORM = "customerForm";
    private static final String DB_ERROR_OCCURRED =
            " An error occurred while fetching data from database. !! ";
    private static final String UNKNOWN_ERROR = " An Unknown Error has been occurred !!";
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final String EXCEPTION_TYPE_IN_CONTROLLER = " Exception type in controller {}";
    private static final String CUSTOMER_FORM_IS = " CustomerForm is {}";

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * list the customers.
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping("/customer/List.htm")
    public ModelAndView list(final CustomerForm customerForm) {
        logIncoming(customerForm);
        List<CustomerVO> customerVOs = customerService.listAllCustomerDetails();
        if (!customerVOs.isEmpty()) {
            customerVOs.forEach(customerVO -> LOG.info("customerVO is {}", customerVO));
            customerForm.setCustomerVOs(customerVOs);
        }
        customerForm.setSearchCustomerVO(new CustomerVO());
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        return new ModelAndView("customer/CustomerList", CUSTOMER_FORM, customerForm);
    }

    private void logIncoming(final CustomerForm customerForm) {
        LOG.info("Inside list method of CustomerController ");
        var sanitizedForm = CommonUtils.sanitizedString(customerForm.toString());
        LOG.info("Form details are {}", sanitizedForm);
    }

    /**
     * add a customer.
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping("/customer/addCustomer.htm")
    public ModelAndView addCustomer(final CustomerForm customerForm) {
        var sanitizedForm = CommonUtils.sanitizedString(customerForm.toString());
        LOG.info("AddCustomer method of CustomerController {}", sanitizedForm);
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        customerForm.setCurrentCustomerVO(new CustomerVO());
        return new ModelAndView("customer/AddCustomer", CUSTOMER_FORM, customerForm);
    }

    /**
     * edit a customer.
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping("/customer/editCust.htm")
    public ModelAndView editCustomer(final CustomerForm customerForm) {
        logIncomingEdit(customerForm);
        var customerVO = getCustomerVOFromId(customerForm.getId());
        if (customerVO.isPresent()) {
            LOG.info(" customerVO details are {}", customerVO.get());
            customerForm.setCurrentCustomerVO(customerVO.get());
        } else {
            LOG.error(" No details found for current makeVO !!");
        }
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        return new ModelAndView("customer/EditCustomer", CUSTOMER_FORM, customerForm);
    }

    private void logIncomingEdit(final CustomerForm customerForm) {
        var sanitizedForm = CommonUtils.sanitizedString(customerForm.toString());
        LOG.info("EditCustomer method of CustomerController ");
        LOG.info("customerForm is {}", sanitizedForm);
        if (customerForm.getCurrentCustomerVO() != null) {
            var sanitizedCustomerVO = CommonUtils.sanitizedString(
                    customerForm.getCurrentCustomerVO().toString());
            LOG.info("customerVO is {}", sanitizedCustomerVO);
        }
    }

    /**
     * edit a customer.
     *
     * @param transactionForm transactionForm
     * @return view
     */
    @PostMapping("/customer/editCustomer.htm")
    public ModelAndView editCustomerOnTransaction(final TransactionForm transactionForm) {
        LOG.info("EditCustomer method of TransactionController ");
        var sanitizedForm = CommonUtils.sanitizedString(transactionForm.toString());
        LOG.info("TransactionForm values are {}", sanitizedForm);
        var customerForm = new CustomerForm();
        if (transactionForm.getCustomerVO() != null && transactionForm.getCustomerVO().getCustomerId() > 0) {
            customerForm.setId(transactionForm.getCustomerVO().getCustomerId());
            return editCustomer(customerForm);
        } else {
            return new ModelAndView("ErrorPage", "userForm", customerForm);
        }
    }

    /**
     * delete a customer.
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping("/customer/deleteCust.htm")
    public ModelAndView deleteCustomer(final CustomerForm customerForm) {
        LOG.info("DeleteCustomer method of CustomerController ");
        var sanitizedForm = CommonUtils.sanitizedString(customerForm.toString());
        LOG.info(CUSTOMER_FORM_IS, sanitizedForm);
        try {
            customerService.deleteCustomerFromId(customerForm.getId());
            customerForm.setStatusMessage("Deleted the customer details successfully");
            customerForm.setStatusMessageType(SUCCESS);
        } catch (Exception ex) {
            customerForm.setStatusMessage("Unable to delete the selected customer details due to a Data base error");
            customerForm.setStatusMessageType(ERROR);
            LOG.error(ex.getLocalizedMessage());
        }
        return list(customerForm);
    }

    /**
     * save the customer.
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping("/customer/saveCustomer.htm")
    public ModelAndView saveCustomer(final CustomerForm customerForm) {
        LOG.info("SaveCustomer method of CustomerController ");
        var sanitizedForm = CommonUtils.sanitizedString(customerForm.toString());
        LOG.info(CUSTOMER_FORM_IS, sanitizedForm);
        try {
            var customerVO = customerForm.getCurrentCustomerVO();
            customerVO.setCreatedBy(customerForm.getLoggedInUser());
            customerVO.setModifiedBy(customerForm.getLoggedInUser());
            //populate customer additional details
            //todo: this need to be corrected at ui level later
            customerVO.setCustomerAdditionalDetailsVO(populateAdditionalDetails(customerVO));
            customerService.saveCustomer(customerVO);
            customerForm.setStatusMessage("Added the new customer details successfully");
            customerForm.setStatusMessageType(SUCCESS);
        } catch (Exception ex) {
            customerForm.setStatusMessage("Unable to add the new customer details due to a Data base error");
            customerForm.setStatusMessageType(ERROR);
            LOG.error(ex.getLocalizedMessage());
        }
        return list(customerForm);
    }

    private CustomerAdditionalDetailsVO populateAdditionalDetails(final CustomerVO customerVO) {
        var customerAdditionalDetailsVO = new CustomerAdditionalDetailsVO();
        customerAdditionalDetailsVO.setContactPerson(customerVO.getContactPerson());
        customerAdditionalDetailsVO.setContactMobile(customerVO.getContactMobile());
        customerAdditionalDetailsVO.setNotes(customerVO.getNotes());
        customerAdditionalDetailsVO.setCreatedBy(customerVO.getCreatedBy());
        customerAdditionalDetailsVO.setModifiedBy(customerVO.getCreatedBy());
        return customerAdditionalDetailsVO;
    }

    /**
     * update a customer.
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping("/customer/updateCustomer.htm")
    public ModelAndView updateCustomer(final CustomerForm customerForm) {
        LOG.info("UpdateCustomer method of CustomerController ");
        var sanitizedForm = CommonUtils.sanitizedString(customerForm.toString());
        LOG.info(CUSTOMER_FORM_IS, sanitizedForm);
        try {
            var customerVO = customerForm.getCurrentCustomerVO();
            customerVO.setModifiedOn(OffsetDateTime.now(ZoneId.systemDefault()));
            customerVO.setModifiedBy(customerForm.getLoggedInUser());
            customerService.updateCustomer(customerVO);
            customerForm.setStatusMessage("Updated the selected customer details successfully");
            customerForm.setStatusMessageType(SUCCESS);
        } catch (Exception ex) {
            customerForm.setStatusMessage("Unable to update the selected customer details due to a Data base error");
            customerForm.setStatusMessageType(ERROR);
            LOG.error(ex.getLocalizedMessage());
        }
        return list(customerForm);
    }

    /**
     * search customer.
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping("/customer/searchCustomer.htm")
    public ModelAndView searchCustomer(final CustomerForm customerForm) {
        LOG.info("SearchCustomer method of CustomerController ");
        var sanitizedForm = CommonUtils.sanitizedString(customerForm.toString());
        LOG.info(CUSTOMER_FORM_IS, sanitizedForm);
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = customerService.searchCustomer(customerForm.getSearchCustomerVO());
            customerForm.setStatusMessage("Found " + customerVOs.size() + " customer details");
            customerForm.setStatusMessageType("info");
        } catch (Exception ex) {
            customerForm.setStatusMessage("Unable to fetch customer details due to a Data base error");
            customerForm.setStatusMessageType(ERROR);
            LOG.error(ex.getLocalizedMessage());
        }
        if (customerVOs != null) {
            customerVOs.forEach(customerVO -> LOG.info(" customerVO is {}", customerVO));
            customerForm.setCustomerVOs(customerVOs);
        }
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        return new ModelAndView("customer/CustomerList", CUSTOMER_FORM, customerForm);
    }

    @PostMapping("/customer/viewCustomer.htm")
    public @ResponseBody
    String viewCustomer(@ModelAttribute("customerId") final String customerId) {
        var id = Long.parseLong(customerId);
        var customerVO = getCustomerVOFromId(id);
        return convertToJson(customerVO.get());
    }

    private String convertToJson(final CustomerVO customerVO) {
        String response;
        var mapper = new ObjectMapper();
        try {
            response = mapper.writeValueAsString(customerVO);
        } catch (IOException ex) {
            response = ERROR;
            LOG.error(ex.getMessage());
        }
        LOG.info(response);
        return response;
    }

    private Optional<CustomerVO> getCustomerVOFromId(final Long id) {
        return customerService.getCustomerFromId(id);
    }

}
