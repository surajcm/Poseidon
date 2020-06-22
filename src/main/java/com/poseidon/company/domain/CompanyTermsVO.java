package com.poseidon.company.domain;

import java.time.OffsetDateTime;

public class CompanyTermsVO {
    private Long id;
    private String companyName;
    private String companyAddress;
    private String companyPhoneNumber;
    private String companyWebsite;
    private String companyEmail;
    private String companyTerms;
    private String termsAndConditions;
    private String companyDetails;
    private String companyVatTin;
    private String companyCstTin;
    private OffsetDateTime createdDate;
    private OffsetDateTime modifiedDate;
    private String createdBy;
    private String modifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(final String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public void setCompanyPhoneNumber(final String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(final String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(final String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyTerms() {
        return companyTerms;
    }

    public void setCompanyTerms(final String companyTerms) {
        this.companyTerms = companyTerms;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(final String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public String getCompanyDetails() {
        return companyDetails;
    }

    public void setCompanyDetails(final String companyDetails) {
        this.companyDetails = companyDetails;
    }

    public String getCompanyVatTin() {
        return companyVatTin;
    }

    public void setCompanyVatTin(final String companyVatTin) {
        this.companyVatTin = companyVatTin;
    }

    public String getCompanyCstTin() {
        return companyCstTin;
    }

    public void setCompanyCstTin(final String companyCstTin) {
        this.companyCstTin = companyCstTin;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public OffsetDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(final OffsetDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
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

    @Override
    public String toString() {
        return "CompanyTermsVO{" +
                "companyName='" + companyName + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", companyPhoneNumber='" + companyPhoneNumber + '\'' +
                ", companyWebsite='" + companyWebsite + '\'' +
                ", companyEmail='" + companyEmail + '\'' +
                ", companyTerms='" + companyTerms + '\'' +
                ", termsAndConditions='" + termsAndConditions + '\'' +
                ", companyDetails='" + companyDetails + '\'' +
                ", companyVatTin='" + companyVatTin + '\'' +
                ", companyCstTin='" + companyCstTin + '\'' +
                '}';
    }
}
