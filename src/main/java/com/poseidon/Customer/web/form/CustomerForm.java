package com.poseidon.Customer.web.form;

import com.poseidon.Customer.domain.CustomerVO;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:48:04 PM
 */
public class CustomerForm {
    private List<CustomerVO> customerVOs;
    private CustomerVO currentCustomerVO;
    private CustomerVO searchCustomerVO;
    private String loggedInRole;
    private String loggedInUser;

    public List<CustomerVO> getCustomerVOs() {
        return customerVOs;
    }

    public void setCustomerVOs(List<CustomerVO> customerVOs) {
        this.customerVOs = customerVOs;
    }

    public CustomerVO getCurrentCustomerVO() {
        return currentCustomerVO;
    }

    public void setCurrentCustomerVO(CustomerVO currentCustomerVO) {
        this.currentCustomerVO = currentCustomerVO;
    }

    public CustomerVO getSearchCustomerVO() {
        return searchCustomerVO;
    }

    public void setSearchCustomerVO(CustomerVO searchCustomerVO) {
        this.searchCustomerVO = searchCustomerVO;
    }

    public String getLoggedInRole() {
        return loggedInRole;
    }

    public void setLoggedInRole(String loggedInRole) {
        this.loggedInRole = loggedInRole;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
