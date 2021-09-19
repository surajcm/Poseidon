package com.poseidon.transaction.domain;

import java.time.OffsetDateTime;
import java.util.StringJoiner;

/**
 * user: Suraj.
 * Date: Jun 2, 2012
 * Time: 3:56:06 PM
 */
public class TransactionVO {
    private Long id;
    private String tagNo;
    private String dateReported;
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
    private String complaintDiagnosed;
    private String enggRemark;
    private String repairAction;
    private String status;
    private OffsetDateTime createdOn;
    private OffsetDateTime modifiedOn;
    private String createdBy;
    private String modifiedBy;
    private Boolean startswith;
    private Boolean includes;
    private String notes;
    private String startDate;
    private String endDate;

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

    public String getDateReported() {
        return dateReported;
    }

    public void setDateReported(final String dateReported) {
        this.dateReported = dateReported;
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

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(final String productCategory) {
        this.productCategory = productCategory;
    }

    public Long getMakeId() {
        return makeId;
    }

    public void setMakeId(final Long makeId) {
        this.makeId = makeId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(final Long modelId) {
        this.modelId = modelId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(final String serialNo) {
        this.serialNo = serialNo;
    }

    public String getAccessories() {
        return accessories;
    }

    public void setAccessories(final String accessories) {
        this.accessories = accessories;
    }

    public String getComplaintReported() {
        return complaintReported;
    }

    public void setComplaintReported(final String complaintReported) {
        this.complaintReported = complaintReported;
    }

    public String getComplaintDiagnosed() {
        return complaintDiagnosed;
    }

    public void setComplaintDiagnosed(final String complaintDiagnosed) {
        this.complaintDiagnosed = complaintDiagnosed;
    }

    public String getEnggRemark() {
        return enggRemark;
    }

    public void setEnggRemark(final String enggRemark) {
        this.enggRemark = enggRemark;
    }

    public String getRepairAction() {
        return repairAction;
    }

    public void setRepairAction(final String repairAction) {
        this.repairAction = repairAction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
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

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(final String makeName) {
        this.makeName = makeName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(final String modelName) {
        this.modelName = modelName;
    }

    public Boolean getStartswith() {
        return startswith;
    }

    public void setStartswith(final Boolean startswith) {
        this.startswith = startswith;
    }

    public Boolean getIncludes() {
        return includes;
    }

    public void setIncludes(final Boolean includes) {
        this.includes = includes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(final String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TransactionVO.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("tagNo='" + tagNo + "'")
                .add("dateReported='" + dateReported + "'")
                .add("customerId=" + customerId)
                .add("customerName='" + customerName + "'")
                .add("productCategory='" + productCategory + "'")
                .add("makeId=" + makeId)
                .add("makeName='" + makeName + "'")
                .add("modelId=" + modelId)
                .add("modelName='" + modelName + "'")
                .add("serialNo='" + serialNo + "'")
                .add("accessories='" + accessories + "'")
                .add("complaintReported='" + complaintReported + "'")
                .add("complaintDiagonsed='" + complaintDiagnosed + "'")
                .add("enggRemark='" + enggRemark + "'")
                .add("repairAction='" + repairAction + "'")
                .add("status='" + status + "'")
                .add("createdOn=" + createdOn)
                .add("modifiedOn=" + modifiedOn)
                .add("createdBy='" + createdBy + "'")
                .add("modifiedBy='" + modifiedBy + "'")
                .add("startswith=" + startswith)
                .add("includes=" + includes)
                .add("notes='" + notes + "'")
                .add("startDate='" + startDate + "'")
                .add("endDate='" + endDate + "'")
                .toString();
    }
}
