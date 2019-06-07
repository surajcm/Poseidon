package com.poseidon.customer.domain;

import java.time.OffsetDateTime;
import java.util.StringJoiner;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 10:46:23 PM
 */
public class CustomerVO {
    private Long customerId;
    private String customerName;
    private String address1;
    private String address2;
    private String phoneNo;
    private String mobile;
    private String email;
    private String contactPerson1;
    private String contactPerson2;
    private String contactMobile1;
    private String contactMobile2;
    private String notes;
    private Boolean startsWith;
    private Boolean includes;
    private OffsetDateTime createdOn;
    private OffsetDateTime modifiedOn;
    private String createdBy;
    private String modifiedBy;

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

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPerson1() {
        return contactPerson1;
    }

    public void setContactPerson1(String contactPerson1) {
        this.contactPerson1 = contactPerson1;
    }

    public String getContactPerson2() {
        return contactPerson2;
    }

    public void setContactPerson2(String contactPerson2) {
        this.contactPerson2 = contactPerson2;
    }

    public String getContactMobile1() {
        return contactMobile1;
    }

    public void setContactMobile1(String contactMobile1) {
        this.contactMobile1 = contactMobile1;
    }

    public String getContactMobile2() {
        return contactMobile2;
    }

    public void setContactMobile2(String contactMobile2) {
        this.contactMobile2 = contactMobile2;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public OffsetDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(OffsetDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public OffsetDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(OffsetDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
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
        return new StringJoiner(", ", CustomerVO.class.getSimpleName() + "[", "]")
                .add("customerId=" + customerId)
                .add("customerName='" + customerName + "'")
                .add("address1='" + address1 + "'")
                .add("address2='" + address2 + "'")
                .add("phoneNo='" + phoneNo + "'")
                .add("mobile='" + mobile + "'")
                .add("email='" + email + "'")
                .add("contactPerson1='" + contactPerson1 + "'")
                .add("contactPerson2='" + contactPerson2 + "'")
                .add("contactMobile1='" + contactMobile1 + "'")
                .add("contactMobile2='" + contactMobile2 + "'")
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
