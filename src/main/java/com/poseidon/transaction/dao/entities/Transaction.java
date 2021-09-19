package com.poseidon.transaction.dao.entities;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
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
    private LocalDateTime dateReported;

    @Column(name = "customerId")
    private Long customerId;

    @Column(name = "productcategory")
    private String productCategory;

    @Column(name = "makeId")
    private Long makeId;

    @Column(name = "modelId")
    private Long modelId;

    @Column(name = "serialNumber")
    private String serialNumber;

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

    @CreatedDate
    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    @LastModifiedDate
    @Column(name = "modifiedOn")
    private LocalDateTime modifiedOn;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "modifiedBy")
    private String modifiedBy;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(final Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTagno() {
        return tagno;
    }

    public void setTagno(final String tagno) {
        this.tagno = tagno;
    }

    public LocalDateTime getDateReported() {
        return dateReported;
    }

    public void setDateReported(final LocalDateTime dateReported) {
        this.dateReported = dateReported;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final Long customerId) {
        this.customerId = customerId;
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(final String serialNumber) {
        this.serialNumber = serialNumber;
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

    public String getEngineerRemarks() {
        return engineerRemarks;
    }

    public void setEngineerRemarks(final String engineerRemarks) {
        this.engineerRemarks = engineerRemarks;
    }

    public String getRepairAction() {
        return repairAction;
    }

    public void setRepairAction(final String repairAction) {
        this.repairAction = repairAction;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(final LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(final LocalDateTime modifiedOn) {
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
