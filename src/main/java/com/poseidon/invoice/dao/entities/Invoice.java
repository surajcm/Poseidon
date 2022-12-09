package com.poseidon.invoice.dao.entities;

import com.poseidon.init.entity.CommonEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "invoice")
public class Invoice extends CommonEntity {

    @Column(name = "transactionId")
    private Long transactionId;

    @Column(name = "description")
    private String description;

    @Column(name = "serialNumber")
    private String serialNumber;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "rate")
    private Long rate;

    @Column(name = "customerId")
    private Long customerId;

    @Column(name = "customername")
    private String customerName;

    @Column(name = "tagNumber")
    private String tagNumber;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(final Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(final String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(final Long amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(final Long rate) {
        this.rate = rate;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(final String serialNumber) {
        this.serialNumber = serialNumber;
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

}
