package com.poseidon.company.dao.entities;

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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(final String terms) {
        this.terms = terms;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(final String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(final String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(final String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(final String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getVatTin() {
        return vatTin;
    }

    public void setVatTin(final String vatTin) {
        this.vatTin = vatTin;
    }

    public String getCstTin() {
        return cstTin;
    }

    public void setCstTin(final String cstTin) {
        this.cstTin = cstTin;
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
