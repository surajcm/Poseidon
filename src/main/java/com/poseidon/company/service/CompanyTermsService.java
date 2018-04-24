package com.poseidon.company.service;

import com.poseidon.company.domain.CompanyTermsVO;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 10:43:26 PM
 */
public interface CompanyTermsService {
    CompanyTermsVO listCompanyTerms();

    CompanyTermsVO updateCompanyDetails(CompanyTermsVO companyTermsVO);

}
