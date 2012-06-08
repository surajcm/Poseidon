package com.poseidon.CompanyTerms.service;

import com.poseidon.CompanyTerms.domain.CompanyTermsVO;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:43:26 PM
 */
public interface CompanyTermsService {
    public CompanyTermsVO listCompanyTerms();

    public CompanyTermsVO fetchCompany();

    public CompanyTermsVO fetchTerms();

    public void updateTerms(String termsAndConditions);

    public void updateCompany(String companyDetails);
}
