package com.poseidon.CompanyTerms.dao;

import com.poseidon.CompanyTerms.domain.CompanyTermsVO;
import com.poseidon.CompanyTerms.exception.CompanyTermsException;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 9:59:42 PM
 */
public interface CompanyTermsDAO {
    public CompanyTermsVO listCompanyTerms() throws CompanyTermsException;

    public CompanyTermsVO fetchCompany() throws CompanyTermsException;

    public CompanyTermsVO fetchTerms( )throws CompanyTermsException;
}
