package com.poseidon.company.web.form;

import com.poseidon.company.domain.CompanyTermsVO;

import java.util.StringJoiner;

public class CompanyTermsForm {
    private Long id;
    private String companyName;
    private String companyAddress;
    private String companyPhoneNumber;
    private String companyWebsite;
    private String companyEmail;
    private String companyTerms;
    private CompanyTermsVO currentCompanyTermsVO;
    private String loggedInRole;
    private String loggedInUser;

    public CompanyTermsVO getCurrentCompanyTermsVO() {
        return currentCompanyTermsVO;
    }

    public void setCurrentCompanyTermsVO(final CompanyTermsVO currentCompanyTermsVO) {
        this.currentCompanyTermsVO = currentCompanyTermsVO;
    }

    public String getLoggedInRole() {
        return loggedInRole;
    }

    public void setLoggedInRole(final String loggedInRole) {
        this.loggedInRole = loggedInRole;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(final String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

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

    public String getCompanyTerms() {
        return companyTerms;
    }

    public void setCompanyTerms(final String companyTerms) {
        this.companyTerms = companyTerms;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(final String companyEmail) {
        this.companyEmail = companyEmail;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CompanyTermsForm.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("companyName='" + companyName + "'")
                .add("companyAddress='" + companyAddress + "'")
                .add("companyPhoneNumber='" + companyPhoneNumber + "'")
                .add("companyWebsite='" + companyWebsite + "'")
                .add("companyEmail='" + companyEmail + "'")
                .add("companyTerms='" + companyTerms + "'")
                .add("currentCompanyTermsVO=" + currentCompanyTermsVO)
                .add("loggedInRole='" + loggedInRole + "'")
                .add("loggedInUser='" + loggedInUser + "'")
                .toString();
    }
}
