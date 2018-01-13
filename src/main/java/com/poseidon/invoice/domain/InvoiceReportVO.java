package com.poseidon.invoice.domain;

/**
 * user: Suraj
 * Date: 8/15/12
 * Time: 7:18 PM
 */
public class InvoiceReportVO {
    private Long invoiceId;
    private String tagNo;
    private Long customerId;
    private String customerName;
    private String customerAddress1;
    private String customerAddress2;
    private String description;
    private String serialNo;
    private int quantity;
    private double rate;
    private double amount;
    private double totalAmount;
    private String companyName;
    private String companyAddress;
    private String companyPhoneNumber;
    private String companyWebsite;
    private String companyEmail;
    private String companyTerms;
    private String companyVATTIN;
    private String companyCSTTIN;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
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

    public String getCustomerAddress1() {
        return customerAddress1;
    }

    public void setCustomerAddress1(String customerAddress1) {
        this.customerAddress1 = customerAddress1;
    }

    public String getCustomerAddress2() {
        return customerAddress2;
    }

    public void setCustomerAddress2(String customerAddress2) {
        this.customerAddress2 = customerAddress2;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public void setCompanyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyTerms() {
        return companyTerms;
    }

    public void setCompanyTerms(String companyTerms) {
        this.companyTerms = companyTerms;
    }

    public String getCompanyVATTIN() {
        return companyVATTIN;
    }

    public void setCompanyVATTIN(String companyVATTIN) {
        this.companyVATTIN = companyVATTIN;
    }

    public String getCompanyCSTTIN() {
        return companyCSTTIN;
    }

    public void setCompanyCSTTIN(String companyCSTTIN) {
        this.companyCSTTIN = companyCSTTIN;
    }
}
