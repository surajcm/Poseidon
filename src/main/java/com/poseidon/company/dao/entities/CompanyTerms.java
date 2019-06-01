package com.poseidon.company.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Entity
//todo : add schema
@Table(name = "companyterms")
public class CompanyTerms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long companyId;

    @Column(name = "terms")
    private String terms;

    @Column(name = "companyAddress")
    private String companyAddress;

    @Column(name = "companyName")
    private String companyName;

    @Column(name = "companyPhone")
    private String companyPhone;

    @Column(name = "companyEmail")
    private String companyEmail;

    @Column(name = "companyWebsite")
    private String companyWebsite;

    @Column(name = "vatTin")
    private String vatTin;

    @Column(name = "cstTin")
    private String cstTin;

    @Column(name = "createdOn")
    private OffsetDateTime createdOn;

    @Column(name = "modifiedOn")
    private OffsetDateTime modifiedOn;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "modifiedBy")
    private String modifiedBy;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getVatTin() {
        return vatTin;
    }

    public void setVatTin(String vatTin) {
        this.vatTin = vatTin;
    }

    public String getCstTin() {
        return cstTin;
    }

    public void setCstTin(String cstTin) {
        this.cstTin = cstTin;
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

    /**
     * initialize / update date fields
     */
    @PrePersist
    @PreUpdate
    public void initializeDate() {
        if (this.getCompanyId() == null) {
            createdOn = OffsetDateTime.now(ZoneId.systemDefault());
        }
        modifiedOn = OffsetDateTime.now(ZoneId.systemDefault());
    }
}
