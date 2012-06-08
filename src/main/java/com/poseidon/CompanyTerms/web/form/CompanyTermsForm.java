package com.poseidon.CompanyTerms.web.form;

import com.poseidon.CompanyTerms.domain.CompanyTermsVO;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:45:13 PM
 */
public class CompanyTermsForm {
    private CompanyTermsVO currentCompanyTermsVO;
    private CompanyTermsVO searchCompanyTermsVO;
    private String loggedInRole;
    private String loggedInUser;

    public CompanyTermsVO getCurrentCompanyTermsVO() {
        return currentCompanyTermsVO;
    }

    public void setCurrentCompanyTermsVO(CompanyTermsVO currentCompanyTermsVO) {
        this.currentCompanyTermsVO = currentCompanyTermsVO;
    }

    public CompanyTermsVO getSearchCompanyTermsVO() {
        return searchCompanyTermsVO;
    }

    public void setSearchCompanyTermsVO(CompanyTermsVO searchCompanyTermsVO) {
        this.searchCompanyTermsVO = searchCompanyTermsVO;
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

    @Override
    public String toString() {
        return "CompanyTermsForm{" +
                "currentCompanyTermsVO=" + currentCompanyTermsVO +
                ", searchCompanyTermsVO=" + searchCompanyTermsVO +
                ", loggedInRole='" + loggedInRole + '\'' +
                ", loggedInUser='" + loggedInUser + '\'' +
                '}';
    }
}
