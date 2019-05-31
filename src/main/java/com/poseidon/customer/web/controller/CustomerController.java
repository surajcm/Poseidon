package com.poseidon.customer.web.controller;

import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;
import com.poseidon.customer.service.CustomerService;
import com.poseidon.customer.web.form.CustomerForm;
import com.poseidon.transaction.web.form.TransactionForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 10:47:39 PM
 */
@Controller
//@RequestMapping("/customer")
@SuppressWarnings("unused")
public class CustomerController {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);
    private static final String CUSTOMER_FORM = "customerForm";
    private static final String AN_ERROR_OCCURRED_WHILE_FETCHING_DATA_FROM_DATABASE =
            " An error occurred while fetching data from database. !! ";
    private static final String UNKNOWN_ERROR_HAS_BEEN_OCCURRED = " An Unknown Error has been occurred !!";
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final String EXCEPTION_TYPE_IN_CONTROLLER = " Exception type in controller {}";
    private static final String CUSTOMER_FORM_IS = " CustomerForm is {}";

    @Autowired
    private CustomerService customerService;

    /**
     * list the customers
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping(value = "/customer/List.htm")
    public ModelAndView list(CustomerForm customerForm) {
        LOG.info(" Inside list method of CustomerController ");
        LOG.info(" form details are {}", customerForm);
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = customerService.listAllCustomerDetails();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }
        if (customerVOs != null) {
            customerVOs.forEach(customerVO -> LOG.info(" customerVO is {}", customerVO));
            customerForm.setCustomerVOs(customerVOs);
        }
        customerForm.setSearchCustomerVO(new CustomerVO());
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        return new ModelAndView("customer/CustomerList", CUSTOMER_FORM, customerForm);
    }

    /**
     * add a customer
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping(value = "/customer/addCust.htm")
    public ModelAndView addCustomer(CustomerForm customerForm) {
        LOG.info(" addCustomer method of CustomerController {}", customerForm);
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        customerForm.setCurrentCustomerVO(new CustomerVO());
        return new ModelAndView("customer/AddCustomer", CUSTOMER_FORM, customerForm);
    }

    /**
     * edit a customer
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping(value = "/customer/editCust.htm")
    public ModelAndView editCustomer(CustomerForm customerForm) {
        LOG.info(" editCustomer method of CustomerController ");
        LOG.info(" customerForm is {}", customerForm);
        LOG.info(" customerForm is {}", customerForm.getCurrentCustomerVO());
        CustomerVO customerVO = null;
        try {
            customerVO = customerService.getCustomerFromId(customerForm.getId());
        } catch (CustomerException e) {
            LOG.error(e.getLocalizedMessage());
            LOG.error(EXCEPTION_TYPE_IN_CONTROLLER, e.getExceptionType());
            if (e.getExceptionType().equalsIgnoreCase(CustomerException.DATABASE_ERROR)) {
                LOG.info(AN_ERROR_OCCURRED_WHILE_FETCHING_DATA_FROM_DATABASE);
            } else {
                LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
            }
        } catch (Exception e1) {
            LOG.error(e1.getLocalizedMessage());
            LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
        }
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
     * edit a customer
     *
     * @param transactionForm transactionForm
     * @return view
     */
    @PostMapping(value = "/customer/editCustomer.htm")
    public ModelAndView editCustomer(TransactionForm transactionForm) {
        LOG.info(" editCustomer method of TransactionController ");
        LOG.info("TransactionForm values are {}", transactionForm);
        CustomerForm customerForm = new CustomerForm();
        if (transactionForm.getCustomerVO() != null && transactionForm.getCustomerVO().getCustomerId() > 0) {
            customerForm.setId(transactionForm.getCustomerVO().getCustomerId());
            return editCustomer(customerForm);
        } else {
            return new ModelAndView("ErrorPage", "userForm", customerForm);
        }
    }

    /**
     * delete a customer
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping(value = "/customer/deleteCust.htm")
    public ModelAndView deleteCustomer(CustomerForm customerForm) {
        LOG.info(" deleteCustomer method of CustomerController ");
        LOG.info(CUSTOMER_FORM_IS, customerForm);
        try {
            customerService.deleteCustomerFromId(customerForm.getId());
            customerForm.setStatusMessage("Deleted the customer details successfully");
            customerForm.setStatusMessageType(SUCCESS);
        } catch (CustomerException e) {
            customerForm.setStatusMessage("Unable to delete the selected customer details due to a Data base error");
            customerForm.setStatusMessageType(ERROR);
            LOG.error(e.getLocalizedMessage());
            LOG.error(EXCEPTION_TYPE_IN_CONTROLLER, e.getExceptionType());
            if (e.getExceptionType().equalsIgnoreCase(CustomerException.DATABASE_ERROR)) {
                LOG.info(AN_ERROR_OCCURRED_WHILE_FETCHING_DATA_FROM_DATABASE);
            } else {
                LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
            }
        } catch (Exception e1) {
            customerForm.setStatusMessage("Unable to delete the selected customer details");
            customerForm.setStatusMessageType(ERROR);
            LOG.error(e1.getLocalizedMessage());
            LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
        }
        return list(customerForm);
    }

    /**
     * save the customer
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping(value = "/customer/saveCustomer.htm")
    public ModelAndView saveCustomer(CustomerForm customerForm) {
        LOG.info(" saveCustomer method of CustomerController ");
        LOG.info(CUSTOMER_FORM_IS, customerForm);
        try {
            CustomerVO customerVO = customerForm.getCurrentCustomerVO();
            customerVO.setCreatedOn(new Date());
            customerVO.setModifiedOn(new Date());
            customerVO.setCreatedBy(customerForm.getLoggedInUser());
            customerVO.setModifiedBy(customerForm.getLoggedInUser());
            customerService.saveCustomer(customerVO);
            customerForm.setStatusMessage("Added the new customer details successfully");
            customerForm.setStatusMessageType(SUCCESS);
        } catch (CustomerException e) {
            customerForm.setStatusMessage("Unable to add the new customer details due to a Data base error");
            customerForm.setStatusMessageType(ERROR);
            LOG.error(e.getLocalizedMessage());
            LOG.error(EXCEPTION_TYPE_IN_CONTROLLER, e.getExceptionType());
            if (e.getExceptionType().equalsIgnoreCase(CustomerException.DATABASE_ERROR)) {
                LOG.info(AN_ERROR_OCCURRED_WHILE_FETCHING_DATA_FROM_DATABASE);
            } else {
                LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
            }
        } catch (Exception e1) {
            customerForm.setStatusMessage("Unable to add the new customer details");
            customerForm.setStatusMessageType(ERROR);
            LOG.error(e1.getLocalizedMessage());
            LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
        }
        return list(customerForm);
    }

    /**
     * update a customer
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping(value = "/customer/updateCustomer.htm")
    public ModelAndView updateCustomer(CustomerForm customerForm) {
        LOG.info(" updateCustomer method of CustomerController ");
        LOG.info(CUSTOMER_FORM_IS, customerForm);
        try {
            CustomerVO customerVO = customerForm.getCurrentCustomerVO();
            customerVO.setModifiedOn(new Date());
            customerVO.setModifiedBy(customerForm.getLoggedInUser());
            customerService.updateCustomer(customerVO);
            customerForm.setStatusMessage("Updated the selected customer details successfully");
            customerForm.setStatusMessageType(SUCCESS);
        } catch (CustomerException e) {
            customerForm.setStatusMessage("Unable to update the selected customer details due to a Data base error");
            customerForm.setStatusMessageType(ERROR);
            LOG.error(e.getLocalizedMessage());
            LOG.error(EXCEPTION_TYPE_IN_CONTROLLER, e.getExceptionType());
            if (e.getExceptionType().equalsIgnoreCase(CustomerException.DATABASE_ERROR)) {
                LOG.info(AN_ERROR_OCCURRED_WHILE_FETCHING_DATA_FROM_DATABASE);
            } else {
                LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
            }
        } catch (Exception e1) {
            customerForm.setStatusMessage("Unable to update the selected customer details");
            customerForm.setStatusMessageType(ERROR);
            LOG.error(e1.getLocalizedMessage());
            LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
        }
        return list(customerForm);
    }

    /**
     * search customer
     *
     * @param customerForm customerForm
     * @return view
     */
    @PostMapping(value = "/customer/searchCustomer.htm")
    public ModelAndView searchCustomer(CustomerForm customerForm) {
        LOG.info(" searchCustomer method of CustomerController ");
        LOG.info(CUSTOMER_FORM_IS, customerForm);
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = customerService.searchCustomer(customerForm.getSearchCustomerVO());
            customerForm.setStatusMessage("Found " + customerVOs.size() + " customer details");
            customerForm.setStatusMessageType("info");
        } catch (CustomerException e) {
            customerForm.setStatusMessage("Unable to fetch customer details due to a Data base error");
            customerForm.setStatusMessageType(ERROR);
            LOG.error(e.getLocalizedMessage());
            LOG.error(EXCEPTION_TYPE_IN_CONTROLLER, e.getExceptionType());
            if (e.getExceptionType().equalsIgnoreCase(CustomerException.DATABASE_ERROR)) {
                LOG.info(AN_ERROR_OCCURRED_WHILE_FETCHING_DATA_FROM_DATABASE);
            } else {
                LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
            }
        } catch (Exception e1) {
            customerForm.setStatusMessage("Unable fetch customer details");
            customerForm.setStatusMessageType(ERROR);
            LOG.error(e1.getLocalizedMessage());
            LOG.info(UNKNOWN_ERROR_HAS_BEEN_OCCURRED);
        }
        if (customerVOs != null) {
            customerVOs.forEach(customerVO -> LOG.info(" customerVO is {}", customerVO));
            customerForm.setCustomerVOs(customerVOs);
        }
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        return new ModelAndView("customer/CustomerList", CUSTOMER_FORM, customerForm);
    }

}
