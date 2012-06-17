package com.poseidon.Transaction.web.form;

import com.poseidon.Make.domain.MakeVO;
import com.poseidon.Transaction.domain.TransactionVO;
import com.poseidon.Make.domain.MakeAndModelVO;
import com.poseidon.Customer.domain.CustomerVO;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 3:53:07 PM
 */
public class TransactionForm {
    private TransactionVO currentTransaction;
    private TransactionVO searchTransaction;
    private List<TransactionVO> transactionsList;
    private String loggedInUser;
    private String loggedInRole;
    private List<MakeVO> makeVOs;
    private List<MakeAndModelVO> makeAndModelVOs;
    private List<String> statusList;
    private CustomerVO customerVO;
    private String statusMessage;
    private String statusMessageType;
    private Long Id;

    public TransactionVO getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(TransactionVO currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public TransactionVO getSearchTransaction() {
        return searchTransaction;
    }

    public void setSearchTransaction(TransactionVO searchTransaction) {
        this.searchTransaction = searchTransaction;
    }

    public List<TransactionVO> getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(List<TransactionVO> transactionsList) {
        this.transactionsList = transactionsList;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getLoggedInRole() {
        return loggedInRole;
    }

    public void setLoggedInRole(String loggedInRole) {
        this.loggedInRole = loggedInRole;
    }

    public List<MakeVO> getMakeVOs() {
        return makeVOs;
    }

    public void setMakeVOs(List<MakeVO> makeVOs) {
        this.makeVOs = makeVOs;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    public CustomerVO getCustomerVO() {
        return customerVO;
    }

    public void setCustomerVO(CustomerVO customerVO) {
        this.customerVO = customerVO;
    }

    public List<MakeAndModelVO> getMakeAndModelVOs() {
        return makeAndModelVOs;
    }

    public void setMakeAndModelVOs(List<MakeAndModelVO> makeAndModelVOs) {
        this.makeAndModelVOs = makeAndModelVOs;
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

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    @Override
    public String toString() {
        return "TransactionForm{" +
                "currentTransaction=" + currentTransaction +
                ", searchTransaction=" + searchTransaction +
                ", transactionsList=" + transactionsList +
                ", loggedInUser='" + loggedInUser + '\'' +
                ", loggedInRole='" + loggedInRole + '\'' +
                ", makeVOs=" + makeVOs +
                ", statusList=" + statusList +
                ", customerVO=" + customerVO +
                '}';
    }
}
