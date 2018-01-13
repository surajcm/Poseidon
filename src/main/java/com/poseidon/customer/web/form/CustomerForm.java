package com.poseidon.customer.web.form;

import com.poseidon.customer.domain.CustomerVO;

import java.util.List;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 10:48:04 PM
 */
public class CustomerForm {
    private Long id;
    private List<CustomerVO> customerVOs;
    private CustomerVO currentCustomerVO;
    private CustomerVO searchCustomerVO;
    private String loggedInRole;
    private String loggedInUser;
    private String statusMessage;
    private String statusMessageType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatusMessageType() {
        return statusMessageType;
    }

    public void setStatusMessageType(String statusMessageType) {
        this.statusMessageType = statusMessageType;
    }

    @Override
    public String toString() {
        return "CustomerForm{" +
                "id=" + id +
                ", customerVOs=" + customerVOs +
                ", currentCustomerVO=" + currentCustomerVO +
                ", searchCustomerVO=" + searchCustomerVO +
                ", loggedInRole='" + loggedInRole + '\'' +
                ", loggedInUser='" + loggedInUser + '\'' +
                '}';
    }
}
