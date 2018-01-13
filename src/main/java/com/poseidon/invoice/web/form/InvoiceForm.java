package com.poseidon.invoice.web.form;

import com.poseidon.invoice.domain.InvoiceVO;

import java.util.List;

/**
 * user: Suraj
 * Date: 7/26/12
 * Time: 10:39 PM
 */
public class InvoiceForm {
    private Long id;
    private String loggedInUser;
    private String loggedInRole;
    private String statusMessage;
    private String statusMessageType;
    private String tagNo;
    private Long customerId;
    private String customerName;
    private String description;
    private String serialNo;
    private int quantity;
    private double rate;
    private double amount;
    private double totalAmount;
    private InvoiceVO searchInvoiceVO;
    private InvoiceVO currentInvoiceVO;
    private List<InvoiceVO> invoiceVOs;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public InvoiceVO getSearchInvoiceVO() {
        return searchInvoiceVO;
    }

    public void setSearchInvoiceVO(InvoiceVO searchInvoiceVO) {
        this.searchInvoiceVO = searchInvoiceVO;
    }

    public List<InvoiceVO> getInvoiceVOs() {
        return invoiceVOs;
    }

    public void setInvoiceVOs(List<InvoiceVO> invoiceVOs) {
        this.invoiceVOs = invoiceVOs;
    }

    public InvoiceVO getCurrentInvoiceVO() {
        return currentInvoiceVO;
    }

    public void setCurrentInvoiceVO(InvoiceVO currentInvoiceVO) {
        this.currentInvoiceVO = currentInvoiceVO;
    }

    @Override
    public String toString() {
        return "InvoiceForm{" +
                "id=" + id +
                ", loggedInUser='" + loggedInUser + '\'' +
                ", loggedInRole='" + loggedInRole + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                ", statusMessageType='" + statusMessageType + '\'' +
                ", tagNo='" + tagNo + '\'' +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", description='" + description + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", quantity=" + quantity +
                ", rate=" + rate +
                ", amount=" + amount +
                ", totalAmount=" + totalAmount +
                ", searchInvoiceVO=" + searchInvoiceVO +
                ", currentInvoiceVO=" + currentInvoiceVO +
                ", invoiceVOs=" + invoiceVOs +
                '}';
    }
}
