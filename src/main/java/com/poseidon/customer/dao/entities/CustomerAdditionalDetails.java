package com.poseidon.customer.dao.entities;

import com.poseidon.init.entity.CommonEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "customer_additional_details")
@SuppressWarnings("PMD.DataClass")
public class CustomerAdditionalDetails extends CommonEntity {

    @Column(name = "customerId")
    private Long customerId;

    @Column(name = "contactPerson")
    private String contactPerson;

    @Column(name = "contactPhone")
    private String contactPhone;

    @Column(name = "note")
    private String note;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final Long customerId) {
        this.customerId = customerId;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(final String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(final String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

}
