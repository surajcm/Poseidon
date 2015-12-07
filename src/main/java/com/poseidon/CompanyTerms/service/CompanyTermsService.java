package com.poseidon.CompanyTerms.service;

import com.poseidon.CompanyTerms.domain.CompanyTermsVO;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:43:26 PM
 */
public interface CompanyTermsService {
    CompanyTermsVO listCompanyTerms();

    void updateCompanyDetails(CompanyTermsVO companyTermsVO);

}
