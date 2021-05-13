package com.poseidon.customer.web.controller;

import com.poseidon.customer.domain.CustomerAdditionalDetailsVO;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;
import com.poseidon.customer.service.CustomerService;
import com.poseidon.customer.web.form.CustomerForm;
import com.poseidon.transaction.web.form.TransactionForm;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;


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

    @Autowired
    private CustomerService customerService;

    /**
     * list the customers.
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping("/customer/List.htm")
    public ModelAndView list(final CustomerForm customerForm) {
        LOG.info("Inside list method of CustomerController ");
        LOG.info("Form details are {}", customerForm);
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = customerService.listAllCustomerDetails();
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        if (customerVOs != null) {
            customerVOs.forEach(customerVO -> LOG.info("customerVO is {}", customerVO));
            customerForm.setCustomerVOs(customerVOs);
        }
        customerForm.setSearchCustomerVO(new CustomerVO());
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        return new ModelAndView("customer/CustomerList", CUSTOMER_FORM, customerForm);
    }

    /**
     * add a customer.
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping("/customer/addCustomer.htm")
    public ModelAndView addCustomer(final CustomerForm customerForm) {
        LOG.info("AddCustomer method of CustomerController {}", customerForm);
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
        LOG.info("EditCustomer method of CustomerController ");
        LOG.info("customerForm is {}", customerForm);
        LOG.info("customerForm is {}", customerForm.getCurrentCustomerVO());
        var customerVO = getCustomerVOFromId(customerForm.getId());
        if (customerVO == null) {
            LOG.error(" No details found for current makeVO !!");
        } else {
            LOG.info(" customerVO details are {}", customerVO);
        }
        customerForm.setCurrentCustomerVO(customerVO);
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        return new ModelAndView("customer/EditCustomer", CUSTOMER_FORM, customerForm);
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
        LOG.info("TransactionForm values are {}", transactionForm);
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
        LOG.info(CUSTOMER_FORM_IS, customerForm);
        try {
            customerService.deleteCustomerFromId(customerForm.getId());
            customerForm.setStatusMessage("Deleted the customer details successfully");
            customerForm.setStatusMessageType(SUCCESS);
        } catch (CustomerException ex) {
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
        LOG.info(" saveCustomer method of CustomerController ");
        LOG.info(CUSTOMER_FORM_IS, customerForm);
        try {
            CustomerVO customerVO = customerForm.getCurrentCustomerVO();
            customerVO.setCreatedBy(customerForm.getLoggedInUser());
            customerVO.setModifiedBy(customerForm.getLoggedInUser());
            //populate customer additional details
            //todo: this need to be corrected at ui level later
            customerVO.setCustomerAdditionalDetailsVO(populateAdditionalDetails(customerVO));
            customerService.saveCustomer(customerVO);
            customerForm.setStatusMessage("Added the new customer details successfully");
            customerForm.setStatusMessageType(SUCCESS);
        } catch (CustomerException ex) {
            customerForm.setStatusMessage("Unable to add the new customer details due to a Data base error");
            customerForm.setStatusMessageType(ERROR);
            LOG.error(ex.getLocalizedMessage());
            LOG.error(EXCEPTION_TYPE_IN_CONTROLLER, ex.getExceptionType());
            if (ex.getExceptionType().equalsIgnoreCase(CustomerException.DATABASE_ERROR)) {
                LOG.info(DB_ERROR_OCCURRED);
            } else {
                LOG.info(UNKNOWN_ERROR);
            }
        } catch (Exception e1) {
            customerForm.setStatusMessage("Unable to add the new customer details");
            customerForm.setStatusMessageType(ERROR);
            LOG.error(e1.getLocalizedMessage());
            LOG.info(UNKNOWN_ERROR);
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
        LOG.info(" updateCustomer method of CustomerController ");
        LOG.info(CUSTOMER_FORM_IS, customerForm);
        try {
            var customerVO = customerForm.getCurrentCustomerVO();
            customerVO.setModifiedOn(OffsetDateTime.now(ZoneId.systemDefault()));
            customerVO.setModifiedBy(customerForm.getLoggedInUser());
            customerService.updateCustomer(customerVO);
            customerForm.setStatusMessage("Updated the selected customer details successfully");
            customerForm.setStatusMessageType(SUCCESS);
        } catch (CustomerException ex) {
            customerForm.setStatusMessage("Unable to update the selected customer details due to a Data base error");
            customerForm.setStatusMessageType(ERROR);
            LOG.error(ex.getLocalizedMessage());
            LOG.error(EXCEPTION_TYPE_IN_CONTROLLER, ex.getExceptionType());
            if (ex.getExceptionType().equalsIgnoreCase(CustomerException.DATABASE_ERROR)) {
                LOG.info(DB_ERROR_OCCURRED);
            } else {
                LOG.info(UNKNOWN_ERROR);
            }
        } catch (Exception e1) {
            customerForm.setStatusMessage("Unable to update the selected customer details");
            customerForm.setStatusMessageType(ERROR);
            LOG.error(e1.getLocalizedMessage());
            LOG.info(UNKNOWN_ERROR);
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
        LOG.info(" searchCustomer method of CustomerController ");
        LOG.info(CUSTOMER_FORM_IS, customerForm);
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = customerService.searchCustomer(customerForm.getSearchCustomerVO());
            customerForm.setStatusMessage("Found " + customerVOs.size() + " customer details");
            customerForm.setStatusMessageType("info");
        } catch (CustomerException ex) {
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
        return convertToJson(customerVO);
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

    private CustomerVO getCustomerVOFromId(final Long id) {
        CustomerVO customerVO = null;
        try {
            customerVO = customerService.getCustomerFromId(id);
        } catch (CustomerException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return customerVO;
    }

}
