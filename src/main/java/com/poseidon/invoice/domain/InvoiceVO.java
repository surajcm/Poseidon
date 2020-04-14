package com.poseidon.invoice.domain;

import java.time.OffsetDateTime;
import java.util.StringJoiner;

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
    private OffsetDateTime createdDate;
    private OffsetDateTime modifiedDate;
    private String createdBy;
    private String modifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    public Double getRate() {
        return rate;
    }

    public void setRate(final Double rate) {
        this.rate = rate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    public Boolean getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(final Boolean startsWith) {
        this.startsWith = startsWith;
    }

    public Boolean getIncludes() {
        return includes;
    }

    public void setIncludes(final Boolean includes) {
        this.includes = includes;
    }

    public Boolean getGreater() {
        return greater;
    }

    public void setGreater(final Boolean greater) {
        this.greater = greater;
    }

    public Boolean getLesser() {
        return lesser;
    }

    public void setLesser(final Boolean lesser) {
        this.lesser = lesser;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public OffsetDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(final OffsetDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(final String modifiedBy) {
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
