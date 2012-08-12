package com.poseidon.CompanyTerms.web.form;

import com.poseidon.CompanyTerms.domain.CompanyTermsVO;

import java.security.PrivateKey;
import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:45:13 PM
 */
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

    public void setCurrentCompanyTermsVO(CompanyTermsVO currentCompanyTermsVO) {
        this.currentCompanyTermsVO = currentCompanyTermsVO;
    }

    public String getLoggedInRole() {
        return loggedInRole;
    }

    public void setLoggedInRole(String loggedInRole) {
        this.loggedInRole = loggedInRole;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public void setCompanyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getCompanyTerms() {
        return companyTerms;
    }

    public void setCompanyTerms(String companyTerms) {
        this.companyTerms = companyTerms;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    @Override
    public String toString() {
        return "CompanyTermsForm{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", companyPhoneNumber='" + companyPhoneNumber + '\'' +
                ", companyWebsite='" + companyWebsite + '\'' +
                ", companyEmail='" + companyEmail + '\'' +
                ", companyTerms='" + companyTerms + '\'' +
                ", currentCompanyTermsVO=" + currentCompanyTermsVO +
                ", loggedInRole='" + loggedInRole + '\'' +
                ", loggedInUser='" + loggedInUser + '\'' +
                '}';
    }
}
