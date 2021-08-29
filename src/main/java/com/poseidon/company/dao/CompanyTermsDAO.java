package com.poseidon.company.dao;

import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.exception.CompanyTermsException;

import java.util.Optional;

public interface CompanyTermsDAO {

    Optional<CompanyTermsVO> listCompanyTerms() throws CompanyTermsException;

    Optional<CompanyTermsVO> updateCompanyDetails(CompanyTermsVO companyTermsVO)throws CompanyTermsException;
}
