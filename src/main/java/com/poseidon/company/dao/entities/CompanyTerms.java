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
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(final String companyAddress) {
        this.address = companyAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(final String companyName) {
        this.name = companyName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String companyCode) {
        this.code = companyCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String companyPhone) {
        this.phone = companyPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String companyEmail) {
        this.email = companyEmail;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(final String companyWebsite) {
        this.website = companyWebsite;
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
