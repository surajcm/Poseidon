package com.poseidon.company.dao;

import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.exception.CompanyTermsException;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 9:59:42 PM
 */
public interface CompanyTermsDAO {

    CompanyTermsVO listCompanyTerms() throws CompanyTermsException;

    void updateCompanyDetails(CompanyTermsVO companyTermsVO)throws CompanyTermsException;
}
