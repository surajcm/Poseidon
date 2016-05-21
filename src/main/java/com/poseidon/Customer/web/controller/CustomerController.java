package com.poseidon.Customer.web.controller;

import com.poseidon.Transaction.web.form.TransactionForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.poseidon.Customer.delegate.CustomerDelegate;
import com.poseidon.Customer.web.form.CustomerForm;
import com.poseidon.Customer.domain.CustomerVO;
import com.poseidon.Customer.exception.CustomerException;

import java.util.Date;
import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:47:39 PM
 */
@Controller
public class CustomerController {
    /**
     * CustomerDelegate instance
     */
    private CustomerDelegate customerDelegate;

    /**
     * logger for user controller
     */
    private final Log log = LogFactory.getLog(CustomerController.class);

    public CustomerDelegate getCustomerDelegate() {
        return customerDelegate;
    }

    public void setCustomerDelegate(CustomerDelegate customerDelegate) {
        this.customerDelegate = customerDelegate;
    }

    @RequestMapping(value = "/customer/List.htm", method = RequestMethod.POST)
    public ModelAndView List(CustomerForm customerForm) {
        log.info(" Inside List method of CustomerController ");
        log.info(" form details are " + customerForm);

        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = getCustomerDelegate().listAllCustomerDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (customerVOs != null) {
            for (CustomerVO customerVO : customerVOs) {
                log.info(" customerVO is " + customerVO);
            }
            customerForm.setCustomerVOs(customerVOs);
        }
        customerForm.setSearchCustomerVO(new CustomerVO());
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        return new ModelAndView("customer/CustomerList", "customerForm", customerForm);
    }

    @RequestMapping(value = "/customer/addCust.htm", method = RequestMethod.POST)
    public ModelAndView addCust(CustomerForm customerForm) {
        log.info(" addCust method of CustomerController "+customerForm);
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        customerForm.setCurrentCustomerVO(new CustomerVO());
        return new ModelAndView("customer/AddCustomer", "customerForm", customerForm);
    }

    @RequestMapping(value = "/customer/editCust.htm", method = RequestMethod.POST)
    public ModelAndView editCust(CustomerForm customerForm) {
        log.info(" editCust method of CustomerController ");
        log.info(" customerForm is " + customerForm.toString());
        log.info(" customerForm is " + customerForm.getCurrentCustomerVO());
        CustomerVO customerVO = null;
        try {
            customerVO = getCustomerDelegate().getCustomerFromId(customerForm.getId());
        } catch (CustomerException e) {
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(CustomerException.DATABASE_ERROR)) {
                log.info(" An error occurred while fetching data from database. !! ");
            } else {
                log.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            e1.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");
        }

        if (customerVO == null) {
            log.error(" No details found for current makeVO !!");
        } else {
            log.info(" customerVO details are " + customerVO);
        }

        customerForm.setCurrentCustomerVO(customerVO);
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        return new ModelAndView("customer/EditCustomer", "customerForm", customerForm);
    }

    @RequestMapping(value = "/customer/deleteCust.htm", method = RequestMethod.POST)
    public ModelAndView deleteCust(CustomerForm customerForm) {
        log.info(" deleteCust method of CustomerController ");
        log.info(" CustomerForm is " + customerForm);
        try {
            getCustomerDelegate().deleteCustomerFromId(customerForm.getId());
            customerForm.setStatusMessage("Deleted the Customer details successfully");
            customerForm.setStatusMessageType("success");
        } catch (CustomerException e) {
            customerForm.setStatusMessage("Unable to delete the selected Customer details due to a Data base error");
            customerForm.setStatusMessageType("error");
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(CustomerException.DATABASE_ERROR)) {
                log.info(" An error occurred while fetching data from database. !! ");
            } else {
                log.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            customerForm.setStatusMessage("Unable to delete the selected Customer details");
            customerForm.setStatusMessageType("error");
            e1.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");
        }
        return List(customerForm);
    }

    @RequestMapping(value = "/customer/saveCustomer.htm", method = RequestMethod.POST)
    public ModelAndView saveCustomer(CustomerForm customerForm) {
        log.info(" saveCustomer method of CustomerController ");
        log.info(" CustomerForm is " + customerForm);
        try {
            CustomerVO customerVO = customerForm.getCurrentCustomerVO();
            customerVO.setCreatedOn(new Date());
            customerVO.setModifiedOn(new Date());
            customerVO.setCreatedBy(customerForm.getLoggedInUser());
            customerVO.setModifiedBy(customerForm.getLoggedInUser());
            getCustomerDelegate().saveCustomer(customerVO);
            customerForm.setStatusMessage("Added the new Customer details successfully");
            customerForm.setStatusMessageType("success");
        } catch (CustomerException e) {
            customerForm.setStatusMessage("Unable to add the new Customer details due to a Data base error");
            customerForm.setStatusMessageType("error");
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(CustomerException.DATABASE_ERROR)) {
                log.info(" An error occurred while fetching data from database. !! ");
            } else {
                log.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            customerForm.setStatusMessage("Unable to add the new Customer details");
            customerForm.setStatusMessageType("error");
            e1.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");
        }
        return List(customerForm);
    }

    @RequestMapping(value = "/customer/updateCustomer.htm", method = RequestMethod.POST)
    public ModelAndView updateCustomer(CustomerForm customerForm) {
        log.info(" updateCustomer method of CustomerController ");
        log.info(" CustomerForm is " + customerForm);
        try {
            CustomerVO customerVO = customerForm.getCurrentCustomerVO();
            customerVO.setModifiedOn(new Date());
            customerVO.setModifiedBy(customerForm.getLoggedInUser());
            getCustomerDelegate().updateCustomer(customerVO);
            customerForm.setStatusMessage("Updated the selected Customer details successfully");
            customerForm.setStatusMessageType("success");
        } catch (CustomerException e) {
            customerForm.setStatusMessage("Unable to update the selected Customer details due to a Data base error");
            customerForm.setStatusMessageType("error");
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(CustomerException.DATABASE_ERROR)) {
                log.info(" An error occurred while fetching data from database. !! ");
            } else {
                log.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            customerForm.setStatusMessage("Unable to update the selected Customer details");
            customerForm.setStatusMessageType("error");
            e1.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");
        }
        return List(customerForm);
    }

    @RequestMapping(value = "/customer/searchCustomer.htm", method = RequestMethod.POST)
    public ModelAndView searchCustomer(CustomerForm customerForm) {
        log.info(" searchCustomer method of CustomerController ");
        log.info(" CustomerForm is " + customerForm);
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = getCustomerDelegate().searchCustomer(customerForm.getSearchCustomerVO());
            customerForm.setStatusMessage("Found " + customerVOs.size() + " Customer details");
            customerForm.setStatusMessageType("info");
        } catch (CustomerException e) {
            customerForm.setStatusMessage("Unable to fetch Customer details due to a Data base error");
            customerForm.setStatusMessageType("error");
            e.printStackTrace();
            log.error(" Exception type in controller " + e.ExceptionType);
            if (e.getExceptionType().equalsIgnoreCase(CustomerException.DATABASE_ERROR)) {
                log.info(" An error occurred while fetching data from database. !! ");
            } else {
                log.info(" An Unknown Error has been occurred !!");
            }

        } catch (Exception e1) {
            customerForm.setStatusMessage("Unable fetch Customer details");
            customerForm.setStatusMessageType("error");
            e1.printStackTrace();
            log.info(" An Unknown Error has been occurred !!");
        }

        if (customerVOs != null) {
            for (CustomerVO customerVO : customerVOs) {
                log.info(" customerVO is " + customerVO);
            }
            customerForm.setCustomerVOs(customerVOs);
        }
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        return new ModelAndView("customer/CustomerList", "customerForm", customerForm);
    }

    @RequestMapping(value = "/customer/editCustomer.htm", method = RequestMethod.POST)
    public ModelAndView editCustomer(TransactionForm transactionForm) {
        log.info(" editCustomer method of TransactionController ");
        log.info("TransactionForm values are " + transactionForm);
        CustomerForm customerForm = new CustomerForm();
        if (transactionForm.getCustomerVO() != null && transactionForm.getCustomerVO().getCustomerId() > 0) {
            customerForm.setId(transactionForm.getCustomerVO().getCustomerId());
            return editCust(customerForm);
        }else{
            return new ModelAndView("ErrorPage","userForm",customerForm);
        }

    }

}
