package com.poseidon.CompanyTerms.dao;

import com.poseidon.CompanyTerms.domain.CompanyTermsVO;
import com.poseidon.CompanyTerms.exception.CompanyTermsException;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 9:59:42 PM
 */
public interface CompanyTermsDAO {

    CompanyTermsVO listCompanyTerms() throws CompanyTermsException;

    void updateCompanyDetails(CompanyTermsVO companyTermsVO)throws CompanyTermsException;
}
