package com.poseidon.CompanyTerms.service.impl;

import com.poseidon.CompanyTerms.service.CompanyTermsService;
import com.poseidon.CompanyTerms.domain.CompanyTermsVO;
import com.poseidon.CompanyTerms.dao.CompanyTermsDAO;
import com.poseidon.CompanyTerms.exception.CompanyTermsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:43:48 PM
 */
public class CompanyTermsServiceImpl implements CompanyTermsService {
    private CompanyTermsDAO companyTermsDAO;
    private final Logger LOG = LoggerFactory.getLogger(CompanyTermsServiceImpl.class);

    public CompanyTermsDAO getCompanyTermsDAO() {
        return companyTermsDAO;
    }

    public void setCompanyTermsDAO(CompanyTermsDAO companyTermsDAO) {
        this.companyTermsDAO = companyTermsDAO;
    }

    public CompanyTermsVO listCompanyTerms() {
        CompanyTermsVO companyTermsVO= null;
        try {
            companyTermsVO = getCompanyTermsDAO().listCompanyTerms();
        } catch (CompanyTermsException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return companyTermsVO;
    }

    public void updateCompanyDetails(CompanyTermsVO companyTermsVO) {
        try {
            getCompanyTermsDAO().updateCompanyDetails(companyTermsVO);
        } catch (CompanyTermsException e) {
            LOG.error(e.getLocalizedMessage());
        }
    }

}
