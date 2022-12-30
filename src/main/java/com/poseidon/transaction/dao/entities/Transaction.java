package com.poseidon.transaction.dao.entities;

import com.poseidon.init.entity.CommonEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "transaction")
@SuppressWarnings("PMD.DataClass")
public class Transaction extends CommonEntity {

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

}
