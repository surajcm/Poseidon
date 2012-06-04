package com.poseidon.CompanyTerms.delegate;

import com.poseidon.CompanyTerms.domain.CompanyTermsVO;
import com.poseidon.CompanyTerms.service.CompanyTermsService;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:42:08 PM
 */
public class CompanyTermsDelegate {
    private CompanyTermsService companyTermsService;

    public CompanyTermsService getCompanyTermsService() {
        return companyTermsService;
    }

    public void setCompanyTermsService(CompanyTermsService companyTermsService) {
        this.companyTermsService = companyTermsService;
    }

    public CompanyTermsVO listCompanyTerms() {
        return getCompanyTermsService().listCompanyTerms();
    }
}
