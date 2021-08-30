package com.poseidon.company.dao;

import com.poseidon.company.domain.CompanyTermsVO;

import java.util.Optional;

public interface CompanyTermsDAO {

    Optional<CompanyTermsVO> listCompanyTerms();

    Optional<CompanyTermsVO> updateCompanyDetails(CompanyTermsVO companyTermsVO);
}
