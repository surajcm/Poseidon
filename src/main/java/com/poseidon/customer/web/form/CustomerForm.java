package com.poseidon.customer.web.form;

import com.poseidon.customer.domain.CustomerVO;

import java.util.List;
import java.util.StringJoiner;

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

    public void setId(final Long id) {
        this.id = id;
    }

    public List<CustomerVO> getCustomerVOs() {
        return customerVOs;
    }

    public void setCustomerVOs(final List<CustomerVO> customerVOs) {
        this.customerVOs = customerVOs;
    }

    public CustomerVO getCurrentCustomerVO() {
        return currentCustomerVO;
    }

    public void setCurrentCustomerVO(final CustomerVO currentCustomerVO) {
        this.currentCustomerVO = currentCustomerVO;
    }

    public CustomerVO getSearchCustomerVO() {
        return searchCustomerVO;
    }

    public void setSearchCustomerVO(final CustomerVO searchCustomerVO) {
        this.searchCustomerVO = searchCustomerVO;
    }

    public String getLoggedInRole() {
        return loggedInRole;
    }

    public void setLoggedInRole(final String loggedInRole) {
        this.loggedInRole = loggedInRole;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(final String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(final String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatusMessageType() {
        return statusMessageType;
    }

    public void setStatusMessageType(final String statusMessageType) {
        this.statusMessageType = statusMessageType;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CustomerForm.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("customerVOs=" + customerVOs)
                .add("currentCustomerVO=" + currentCustomerVO)
                .add("searchCustomerVO=" + searchCustomerVO)
                .add("loggedInRole='" + loggedInRole + "'")
                .add("loggedInUser='" + loggedInUser + "'")
                .add("statusMessage='" + statusMessage + "'")
                .add("statusMessageType='" + statusMessageType + "'")
                .toString();
    }
}
