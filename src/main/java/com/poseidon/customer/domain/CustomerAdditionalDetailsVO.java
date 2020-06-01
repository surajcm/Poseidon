package com.poseidon.customer.domain;

import java.time.OffsetDateTime;

public class CustomerAdditionalDetailsVO {
    private Long customerId;
    private String contactPerson1;
    private String contactPerson2;
    private String contactMobile1;
    private String contactMobile2;
    private String notes;
    private OffsetDateTime createdOn;
    private OffsetDateTime modifiedOn;
    private String createdBy;
    private String modifiedBy;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final Long customerId) {
        this.customerId = customerId;
    }

    public String getContactPerson1() {
        return contactPerson1;
    }

    public void setContactPerson1(final String contactPerson1) {
        this.contactPerson1 = contactPerson1;
    }

    public String getContactPerson2() {
        return contactPerson2;
    }

    public void setContactPerson2(final String contactPerson2) {
        this.contactPerson2 = contactPerson2;
    }

    public String getContactMobile1() {
        return contactMobile1;
    }

    public void setContactMobile1(final String contactMobile1) {
        this.contactMobile1 = contactMobile1;
    }

    public String getContactMobile2() {
        return contactMobile2;
    }

    public void setContactMobile2(final String contactMobile2) {
        this.contactMobile2 = contactMobile2;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

    public OffsetDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(final OffsetDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public OffsetDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(final OffsetDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
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
}
