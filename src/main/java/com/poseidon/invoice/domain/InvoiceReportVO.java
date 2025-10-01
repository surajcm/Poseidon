package com.poseidon.invoice.domain;

import java.util.StringJoiner;

public class InvoiceReportVO {
    private Long invoiceId;
    private String tagNo;
    private Long customerId;
    private String customerName;
    private String customerAddress;
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
    private String termsAndConditions;
    private String companyVatTin;
    private String companyCstTin;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(final Long invoiceId) {
        this.invoiceId = invoiceId;
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

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(final String customerAddress) {
        this.customerAddress = customerAddress;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(final String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public void setCompanyPhoneNumber(final String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(final String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(final String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(final String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public String getCompanyVatTin() {
        return companyVatTin;
    }

    public void setCompanyVatTin(final String companyVatTin) {
        this.companyVatTin = companyVatTin;
    }

    public String getCompanyCstTin() {
        return companyCstTin;
    }

    public void setCompanyCstTin(final String companyCstTin) {
        this.companyCstTin = companyCstTin;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", InvoiceReportVO.class.getSimpleName() + "[", "]")
                .add("invoiceId=" + invoiceId).add("tagNo='" + tagNo + "'")
                .add("customerId=" + customerId).add("customerName='" + customerName + "'")
                .add("customerAddress='" + customerAddress + "'").add("description='" + description + "'")
                .add("serialNo='" + serialNo + "'").add("quantity=" + quantity)
                .add("rate=" + rate).add("amount=" + amount).add("totalAmount=" + totalAmount)
                .add("companyName='" + companyName + "'").add("companyAddress='" + companyAddress + "'")
                .add("companyPhoneNumber='" + companyPhoneNumber + "'").add("companyWebsite='" + companyWebsite + "'")
                .add("companyEmail='" + companyEmail + "'").add("termsAndConditions='" + termsAndConditions + "'")
                .add("companyVatTin='" + companyVatTin + "'").add("companyCstTin='" + companyCstTin + "'")
                .toString();
    }
}
