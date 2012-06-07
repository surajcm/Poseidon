package com.poseidon.CompanyTerms.service.impl;

import com.poseidon.CompanyTerms.service.CompanyTermsService;
import com.poseidon.CompanyTerms.domain.CompanyTermsVO;
import com.poseidon.CompanyTerms.dao.CompanyTermsDAO;
import com.poseidon.CompanyTerms.exception.CompanyTermsException;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:43:48 PM
 */
public class CompanyTermsServiceImpl implements CompanyTermsService {
    private CompanyTermsDAO companyTermsDAO;
    private final Log log = LogFactory.getLog(CompanyTermsServiceImpl.class);

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
            e.printStackTrace();
        }
        return companyTermsVO;
    }

    public CompanyTermsVO fetchCompany() {
        CompanyTermsVO companyTermsVO= null;
        try {
            companyTermsVO = getCompanyTermsDAO().fetchCompany();
        } catch (CompanyTermsException e) {
            e.printStackTrace();
        }
        return companyTermsVO;
    }

    public CompanyTermsVO fetchTerms() {
        CompanyTermsVO companyTermsVO= null;
        try {
            companyTermsVO = getCompanyTermsDAO().fetchTerms();
        } catch (CompanyTermsException e) {
            e.printStackTrace();
        }
        return companyTermsVO;
    }
}
