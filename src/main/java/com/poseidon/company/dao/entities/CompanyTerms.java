package com.poseidon.company.dao.entities;

import com.poseidon.init.entity.CommonEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "companyterms")
public class CompanyTerms extends CommonEntity {

    @Column(name = "name")
    private String companyName;

    @Column(name = "code")
    private String companyCode;

    @Column(name = "address")
    private String companyAddress;

    @Column(name = "phone")
    private String companyPhone;

    @Column(name = "email")
    private String companyEmail;

    @Column(name = "website")
    private String companyWebsite;

    @Column(name = "terms")
    private String terms;

    @Column(name = "vatTin")
    private String vatTin;

    @Column(name = "cstTin")
    private String cstTin;

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

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(final String companyCode) {
        this.companyCode = companyCode;
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
    
}
