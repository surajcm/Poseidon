package com.poseidon.transaction.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Entity
//todo : add schema
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long transactionId;

    @Column(name = "tagno")
    private String tagno;

    @Column(name = "datereported")
    private OffsetDateTime dateReported;

    @Column(name = "customerid")
    private Integer customerId;

    @Column(name = "productcategory")
    private String productCategory;

    @Column(name = "makeid")
    private Integer makeId;

    @Column(name = "modelid")
    private Integer modelId;

    @Column(name = "serialno")
    private String serialno;

    @Column(name = "accessories")
    private String accessories;

    @Column(name = "complaintReported")
    private String complaintReported;

    @Column(name = "complaintDiagnosed")
    private String complaintDiagnosed;

    @Column(name = "engineerRemarks")
    private String engineerRemarks;

    @Column(name = "repairAction")
    private String repairAction;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private String status;

    @Column(name = "createdOn")
    private OffsetDateTime createdOn;

    @Column(name = "modifiedOn")
    private OffsetDateTime modifiedOn;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "modifiedBy")
    private String modifiedBy;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTagno() {
        return tagno;
    }

    public void setTagno(String tagno) {
        this.tagno = tagno;
    }

    public OffsetDateTime getDateReported() {
        return dateReported;
    }

    public void setDateReported(OffsetDateTime dateReported) {
        this.dateReported = dateReported;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Integer getMakeId() {
        return makeId;
    }

    public void setMakeId(Integer makeId) {
        this.makeId = makeId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
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

    public String getComplaintDiagnosed() {
        return complaintDiagnosed;
    }

    public void setComplaintDiagnosed(String complaintDiagnosed) {
        this.complaintDiagnosed = complaintDiagnosed;
    }

    public String getEngineerRemarks() {
        return engineerRemarks;
    }

    public void setEngineerRemarks(String engineerRemarks) {
        this.engineerRemarks = engineerRemarks;
    }

    public String getRepairAction() {
        return repairAction;
    }

    public void setRepairAction(String repairAction) {
        this.repairAction = repairAction;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @PrePersist
    @PreUpdate
    public void initializeDate() {
        if (this.transactionId == null) {
            createdOn = OffsetDateTime.now();
        }
        modifiedOn = OffsetDateTime.now();
    }
}
