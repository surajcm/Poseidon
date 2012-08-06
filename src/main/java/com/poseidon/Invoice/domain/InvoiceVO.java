package com.poseidon.Invoice.domain;

import java.util.Date;

/**
 * User: Suraj
 * Date: 7/26/12
 * Time: 10:40 PM
 */
public class InvoiceVO {
    private Long id;
    private String tagNo;
    private Long customerId;
    private String customerName;
    private String description;
    private String serialNo;
    private int quantity;
    private double rate;
    private double amount;
    private Boolean startsWith;
    private Boolean includes;
    private Boolean greater;
    private Boolean lesser;
    private Date createdDate;
    private Date modifiedDate;
    private String createdBy;
    private String modifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(Boolean startsWith) {
        this.startsWith = startsWith;
    }

    public Boolean getIncludes() {
        return includes;
    }

    public void setIncludes(Boolean includes) {
        this.includes = includes;
    }

    public Boolean getGreater() {
        return greater;
    }

    public void setGreater(Boolean greater) {
        this.greater = greater;
    }

    public Boolean getLesser() {
        return lesser;
    }

    public void setLesser(Boolean lesser) {
        this.lesser = lesser;
    }

    @Override
    public String toString() {
        return "InvoiceVO{" +
                "id=" + id +
                ", tagNo='" + tagNo + '\'' +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", description='" + description + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", quantity=" + quantity +
                ", rate=" + rate +
                ", amount=" + amount +
                ", startsWith=" + startsWith +
                ", includes=" + includes +
                ", greater=" + greater +
                ", lesser=" + lesser +
                '}';
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
