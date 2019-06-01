package com.poseidon.company.service.impl;

import com.poseidon.company.service.CompanyTermsService;
import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.dao.CompanyTermsDAO;
import com.poseidon.company.exception.CompanyTermsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 10:43:48 PM
 */
@Service
public class CompanyTermsServiceImpl implements CompanyTermsService {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyTermsServiceImpl.class);

    @Autowired
    private CompanyTermsDAO companyTermsDAO;

    /**
     * list company terms
     *
     * @return CompanyTermsVO
     */
    @Override
    public CompanyTermsVO listCompanyTerms() {
        CompanyTermsVO companyTermsVO = null;
        try {
            companyTermsVO = companyTermsDAO.listCompanyTerms();
        } catch (CompanyTermsException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return companyTermsVO;
    }

    /**
     * update company details
     *
     * @param companyTermsVO companyTermsVO
     * @return company terms vo
     */
    @Override
    public CompanyTermsVO updateCompanyDetails(CompanyTermsVO companyTermsVO) {
        CompanyTermsVO termsVO = null;
        try {
            termsVO = companyTermsDAO.updateCompanyDetails(companyTermsVO);
        } catch (CompanyTermsException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return termsVO;
    }

}
