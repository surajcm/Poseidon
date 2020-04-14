package com.poseidon.company.dao;

import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.exception.CompanyTermsException;

public interface CompanyTermsDAO {

    CompanyTermsVO listCompanyTerms() throws CompanyTermsException;

    CompanyTermsVO updateCompanyDetails(CompanyTermsVO companyTermsVO)throws CompanyTermsException;
}
