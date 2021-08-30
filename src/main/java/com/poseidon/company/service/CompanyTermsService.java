package com.poseidon.company.service;

import com.poseidon.company.domain.CompanyTermsVO;

import java.util.Optional;

public interface CompanyTermsService {
    Optional<CompanyTermsVO> listCompanyTerms();

    Optional<CompanyTermsVO> updateCompanyDetails(CompanyTermsVO companyTermsVO);
}
