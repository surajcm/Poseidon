package com.poseidon.CompanyTerms.domain;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:42:40 PM
 */
public class CompanyTermsVO {
    private Long termsId;
    private String termsAndConditions;
    private String companyDetails;

    public Long getTermsId() {
        return termsId;
    }

    public void setTermsId(Long termsId) {
        this.termsId = termsId;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public String getCompanyDetails() {
        return companyDetails;
    }

    public void setCompanyDetails(String companyDetails) {
        this.companyDetails = companyDetails;
    }

    @Override
    public String toString() {
        return "CompanyTermsVO{" +
                "termsId=" + termsId +
                ", termsAndConditions='" + termsAndConditions + '\'' +
                ", companyDetails='" + companyDetails + '\'' +
                '}';
    }
}
