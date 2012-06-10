package com.poseidon.Transaction.domain;

import java.util.Date;
import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 3:56:06 PM
 */
public class TransactionVO {
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

    @Override
    public String toString() {
        return "TransactionVO{" +
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
