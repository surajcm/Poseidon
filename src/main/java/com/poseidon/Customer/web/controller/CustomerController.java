package com.poseidon.Customer.web.controller;

import com.poseidon.Transaction.web.form.TransactionForm;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.poseidon.Customer.delegate.CustomerDelegate;
import com.poseidon.Customer.web.form.CustomerForm;
import com.poseidon.Customer.domain.CustomerVO;
import com.poseidon.Customer.exception.CustomerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:47:39 PM
 */
public class CustomerController extends MultiActionController {
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

    public ModelAndView List(HttpServletRequest request,
                             HttpServletResponse response, CustomerForm customerForm) {
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

    public ModelAndView addCust(HttpServletRequest request,
                                HttpServletResponse response, CustomerForm customerForm) {
        log.info(" addCust method of CustomerController ");
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        customerForm.setCurrentCustomerVO(new CustomerVO());
        return new ModelAndView("customer/AddCustomer", "customerForm", customerForm);
    }

    public ModelAndView editCust(HttpServletRequest request,
                                 HttpServletResponse response, CustomerForm customerForm) {
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

    public ModelAndView deleteCust(HttpServletRequest request,
                                   HttpServletResponse response, CustomerForm customerForm) {
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
        return List(request, response, customerForm);
    }

    public ModelAndView saveCustomer(HttpServletRequest request,
                                     HttpServletResponse response, CustomerForm customerForm) {
        log.info(" saveCustomer method of CustomerController ");
        log.info(" CustomerForm is " + customerForm);
        try {
            getCustomerDelegate().saveCustomer(customerForm.getCurrentCustomerVO());
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
        return List(request, response, customerForm);
    }

    public ModelAndView updateCustomer(HttpServletRequest request,
                                       HttpServletResponse response, CustomerForm customerForm) {
        log.info(" updateCustomer method of CustomerController ");
        log.info(" CustomerForm is " + customerForm);
        try {
            getCustomerDelegate().updateCustomer(customerForm.getCurrentCustomerVO());
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
        return List(request, response, customerForm);
    }

    public ModelAndView searchCustomer(HttpServletRequest request,
                                       HttpServletResponse response, CustomerForm customerForm) {
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

    public ModelAndView editCustomer(HttpServletRequest request,
                                     HttpServletResponse response, TransactionForm transactionForm) {
        log.info(" editCustomer method of TransactionController ");
        log.info("TransactionForm values are " + transactionForm);
        CustomerForm customerForm = new CustomerForm();
        if (transactionForm.getCustomerVO() != null && transactionForm.getCustomerVO().getCustomerId() > 0) {
            customerForm.setId(transactionForm.getCustomerVO().getCustomerId());
            return editCust(request, response, customerForm);
        }else{
            return new ModelAndView("ErrorPage","userForm",customerForm);
        }

    }

}
