package com.poseidon.Customer.web.controller;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.poseidon.Customer.delegate.CustomerDelegate;
import com.poseidon.Customer.web.form.CustomerForm;
import com.poseidon.Customer.domain.CustomerVO;

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
                                 HttpServletResponse response, CustomerForm customerForm){
        log.info(" listMake addCust method of MakeController ");
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        customerForm.setCurrentCustomerVO(new CustomerVO());
        return new ModelAndView("customer/AddCustomer", "customerForm", customerForm);
    }

    public ModelAndView editCust(HttpServletRequest request,
                                 HttpServletResponse response, CustomerForm customerForm){
        log.info(" listMake editCust method of MakeController ");
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        customerForm.setCurrentCustomerVO(new CustomerVO());
        return new ModelAndView("customer/EditCustomer", "customerForm", customerForm);
    }

    public ModelAndView deleteCust(HttpServletRequest request,
                                 HttpServletResponse response, CustomerForm customerForm){
        log.info(" listMake deleteCust method of MakeController ");
        customerForm.setLoggedInUser(customerForm.getLoggedInUser());
        customerForm.setLoggedInRole(customerForm.getLoggedInRole());
        customerForm.setCurrentCustomerVO(new CustomerVO());
        return new ModelAndView("customer/DeleteCustomer", "customerForm", customerForm);
    }
}
