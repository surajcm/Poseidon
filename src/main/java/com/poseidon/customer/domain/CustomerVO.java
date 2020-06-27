package com.poseidon.customer.domain;

import java.time.OffsetDateTime;
import java.util.StringJoiner;

public class CustomerVO {
    private Long customerId;
    private String customerName;
    private String address;
    private String phoneNo;
    private String mobile;
    private String email;
    private String contactPerson;
    private String contactMobile;
    private String notes;
    private CustomerAdditionalDetailsVO customerAdditionalDetailsVO;
    private Boolean startsWith;
    private Boolean includes;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(final String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(final String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(final String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public CustomerAdditionalDetailsVO getCustomerAdditionalDetailsVO() {
        return customerAdditionalDetailsVO;
    }

    public void setCustomerAdditionalDetailsVO(final CustomerAdditionalDetailsVO customerAdditionalDetailsVO) {
        this.customerAdditionalDetailsVO = customerAdditionalDetailsVO;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(final String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(final String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
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

    @Override
    public String toString() {
        return new StringJoiner(", ", CustomerVO.class.getSimpleName() + "[", "]")
                .add("customerId=" + customerId)
                .add("customerName='" + customerName + "'")
                .add("address='" + address + "'")
                .add("phoneNo='" + phoneNo + "'")
                .add("mobile='" + mobile + "'")
                .add("email='" + email + "'")
                .add("contactPerson='" + contactPerson + "'")
                .add("contactMobile='" + contactMobile + "'")
                .add("notes='" + notes + "'")
                .add("startsWith=" + startsWith)
                .add("includes=" + includes)
                .add("createdOn=" + createdOn)
                .add("modifiedOn=" + modifiedOn)
                .add("createdBy='" + createdBy + "'")
                .add("modifiedBy='" + modifiedBy + "'")
                .toString();
    }
}
