package com.poseidon.invoice.domain;

import java.util.Date;
import java.util.StringJoiner;

/**
 * user: Suraj
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
    private Double rate;
    private Double amount;
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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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

    @Override
    public String toString() {
        return new StringJoiner(", ", InvoiceVO.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("tagNo='" + tagNo + "'")
                .add("customerId=" + customerId)
                .add("customerName='" + customerName + "'")
                .add("description='" + description + "'")
                .add("serialNo='" + serialNo + "'")
                .add("quantity=" + quantity)
                .add("rate=" + rate)
                .add("amount=" + amount)
                .add("startsWith=" + startsWith)
                .add("includes=" + includes)
                .add("greater=" + greater)
                .add("lesser=" + lesser)
                .add("createdDate=" + createdDate)
                .add("modifiedDate=" + modifiedDate)
                .add("createdBy='" + createdBy + "'")
                .add("modifiedBy='" + modifiedBy + "'")
                .toString();
    }
}
