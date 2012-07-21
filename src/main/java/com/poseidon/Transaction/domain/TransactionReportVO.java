package com.poseidon.Transaction.domain;

import java.util.Date;

/**
 * User: Suraj
 * Date: 6/30/12
 * Time: 11:23 AM
 */

public class TransactionReportVO {
    private Long id;
    private String tagNo;
    private Date dateReported;
    private Long customerId;
    private String customerName;
    private String productCategory;
    private Long makeId;
    private String makeName;
    private Long modelId;
    private String modelName;
    private String serialNo;
    private String accessories;
    private String complaintReported;
    private String complaintDiagonsed;
    private String enggRemark;
    private String repairAction;
    private String status;
    private Date createdOn;
    private Date modifiedOn;
    private String createdBy;
    private String modifiedBy;
    private Boolean startswith;
    private Boolean includes;
    private String notes;
    private String startDate;
    private String endDate;
    private String address1;
    private String address2;
    private String phone;
    private String mobile;
    private String email;
    private String contactPerson1;
    private String contactPh1;
    private String contactPerson2;
    private String contactPh2;
    private String companyTerms;

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

    public Date getDateReported() {
        return dateReported;
    }

    public void setDateReported(Date dateReported) {
        this.dateReported = dateReported;
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

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Long getMakeId() {
        return makeId;
    }

    public void setMakeId(Long makeId) {
        this.makeId = makeId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getAccessories() {
        return accessories;
    }

    public void setAccessories(String accessories) {
        this.accessories = accessories;
    }

    public String getComplaintReported() {
        return complaintReported;
    }

    public void setComplaintReported(String complaintReported) {
        this.complaintReported = complaintReported;
    }

    public String getComplaintDiagonsed() {
        return complaintDiagonsed;
    }

    public void setComplaintDiagonsed(String complaintDiagonsed) {
        this.complaintDiagonsed = complaintDiagonsed;
    }

    public String getEnggRemark() {
        return enggRemark;
    }

    public void setEnggRemark(String enggRemark) {
        this.enggRemark = enggRemark;
    }

    public String getRepairAction() {
        return repairAction;
    }

    public void setRepairAction(String repairAction) {
        this.repairAction = repairAction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
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

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Boolean getStartswith() {
        return startswith;
    }

    public void setStartswith(Boolean startswith) {
        this.startswith = startswith;
    }

    public Boolean getIncludes() {
        return includes;
    }

    public void setIncludes(Boolean includes) {
        this.includes = includes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getContactPh1() {
        return contactPh1;
    }

    public void setContactPh1(String contactPh1) {
        this.contactPh1 = contactPh1;
    }

    public String getContactPerson2() {
        return contactPerson2;
    }

    public void setContactPerson2(String contactPerson2) {
        this.contactPerson2 = contactPerson2;
    }

    public String getContactPh2() {
        return contactPh2;
    }

    public void setContactPh2(String contactPh2) {
        this.contactPh2 = contactPh2;
    }

    public String getCompanyTerms() {
        return companyTerms;
    }

    public void setCompanyTerms(String companyTerms) {
        this.companyTerms = companyTerms;
    }

    @Override
    public String toString() {
        return "TransactionReportVO{" +
                "id=" + id +
                ", tagNo='" + tagNo + '\'' +
                ", dateReported=" + dateReported +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", makeId=" + makeId +
                ", makeName='" + makeName + '\'' +
                ", modelId=" + modelId +
                ", modelName='" + modelName + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", accessories='" + accessories + '\'' +
                ", complaintReported='" + complaintReported + '\'' +
                ", complaintDiagonsed='" + complaintDiagonsed + '\'' +
                ", enggRemark='" + enggRemark + '\'' +
                ", repairAction='" + repairAction + '\'' +
                ", status='" + status + '\'' +
                ", createdOn=" + createdOn +
                ", modifiedOn=" + modifiedOn +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", startswith=" + startswith +
                ", includes=" + includes +
                ", notes='" + notes + '\'' +
                '}';
    }
}

