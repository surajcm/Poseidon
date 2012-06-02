package com.poseidon.Transaction.domain;

import java.util.Date;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 3:56:06 PM
 */
public class TransactionVO {
    private Long id;
    private String TagNo;
    private Date DateReported;
    private Long CustomerId;
    private String CustomerName;
    private String ProductCategory;
    private Long MakeId;
    private Long ModelId;
    private String SerialNo;
    private String Accessories;
    private String ComplaintReported;
    private String ComplaintDiagonsed;
    private String EnggRemark;
    private String RepairAction;
    private String Status;
    private Date createdOn;
    private Date modifiedOn;
    private String createdBy;
    private String ModifiedBy;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagNo() {
        return TagNo;
    }

    public void setTagNo(String tagNo) {
        TagNo = tagNo;
    }

    public Date getDateReported() {
        return DateReported;
    }

    public void setDateReported(Date dateReported) {
        DateReported = dateReported;
    }

    public Long getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(Long customerId) {
        CustomerId = customerId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getProductCategory() {
        return ProductCategory;
    }

    public void setProductCategory(String productCategory) {
        ProductCategory = productCategory;
    }

    public Long getMakeId() {
        return MakeId;
    }

    public void setMakeId(Long makeId) {
        MakeId = makeId;
    }

    public Long getModelId() {
        return ModelId;
    }

    public void setModelId(Long modelId) {
        ModelId = modelId;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public String getAccessories() {
        return Accessories;
    }

    public void setAccessories(String accessories) {
        Accessories = accessories;
    }

    public String getComplaintReported() {
        return ComplaintReported;
    }

    public void setComplaintReported(String complaintReported) {
        ComplaintReported = complaintReported;
    }

    public String getComplaintDiagonsed() {
        return ComplaintDiagonsed;
    }

    public void setComplaintDiagonsed(String complaintDiagonsed) {
        ComplaintDiagonsed = complaintDiagonsed;
    }

    public String getEnggRemark() {
        return EnggRemark;
    }

    public void setEnggRemark(String enggRemark) {
        EnggRemark = enggRemark;
    }

    public String getRepairAction() {
        return RepairAction;
    }

    public void setRepairAction(String repairAction) {
        RepairAction = repairAction;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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
        return ModifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        ModifiedBy = modifiedBy;
    }
}
