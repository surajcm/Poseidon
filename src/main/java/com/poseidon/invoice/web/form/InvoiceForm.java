package com.poseidon.invoice.web.form;

import com.poseidon.invoice.domain.InvoiceVO;

import java.util.List;

/**
 * user: Suraj.
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
    private InvoiceVO searchInvoiceVo;
    private InvoiceVO currentInvoiceVo;
    private List<InvoiceVO> invoiceVos;

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(final String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getLoggedInRole() {
        return loggedInRole;
    }

    public void setLoggedInRole(final String loggedInRole) {
        this.loggedInRole = loggedInRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(final String tagNo) {
        this.tagNo = tagNo;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(final String customerName) {
        this.customerName = customerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(final String serialNo) {
        this.serialNo = serialNo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(final double rate) {
        this.rate = rate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(final double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public InvoiceVO getSearchInvoiceVo() {
        return searchInvoiceVo;
    }

    public void setSearchInvoiceVo(final InvoiceVO searchInvoiceVo) {
        this.searchInvoiceVo = searchInvoiceVo;
    }

    public List<InvoiceVO> getInvoiceVos() {
        return invoiceVos;
    }

    public void setInvoiceVos(final List<InvoiceVO> invoiceVos) {
        this.invoiceVos = invoiceVos;
    }

    public InvoiceVO getCurrentInvoiceVo() {
        return currentInvoiceVo;
    }

    public void setCurrentInvoiceVo(final InvoiceVO currentInvoiceVo) {
        this.currentInvoiceVo = currentInvoiceVo;
    }

    @Override
    public String toString() {
        return "InvoiceForm{"
                + "id="
                + id
                + ", loggedInUser='"
                + loggedInUser
                + '\''
                + ", loggedInRole='"
                + loggedInRole
                + '\''
                + ", statusMessage='"
                + statusMessage
                + '\''
                + ", statusMessageType='"
                + statusMessageType
                + '\''
                + ", tagNo='"
                + tagNo
                + '\''
                + ", customerId="
                + customerId
                + ", customerName='"
                + customerName
                + '\''
                + ", description='"
                + description
                + '\''
                + ", serialNo='"
                + serialNo
                + '\''
                + ", quantity="
                + quantity
                + ", rate="
                + rate
                + ", amount="
                + amount
                + ", totalAmount="
                + totalAmount
                + ", searchInvoiceVo="
                + searchInvoiceVo
                + ", currentInvoiceVo="
                + currentInvoiceVo
                + ", invoiceVos="
                + invoiceVos
                + '}';
    }
}
