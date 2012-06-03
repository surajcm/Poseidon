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

    public List<CompanyTermsVO> listCompanyTerms() {
        List<CompanyTermsVO> companyTermsVOs = null;
        try {
            companyTermsVOs = getCompanyTermsDAO().listCompanyTerms();
        } catch (CompanyTermsException e) {
            e.printStackTrace();
        }
        return companyTermsVOs;
    }
}
