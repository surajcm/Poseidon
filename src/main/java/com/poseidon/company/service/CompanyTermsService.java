package com.poseidon.company.service;

import com.poseidon.company.domain.CompanyTermsVO;

public interface CompanyTermsService {
    CompanyTermsVO listCompanyTerms();

    CompanyTermsVO updateCompanyDetails(CompanyTermsVO companyTermsVO);
}
