package com.poseidon.company.service.impl;

import com.poseidon.company.dao.CompanyTermsDAO;
import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.service.CompanyTermsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyTermsServiceImpl implements CompanyTermsService {

    private final CompanyTermsDAO companyTermsDAO;

    public CompanyTermsServiceImpl(final CompanyTermsDAO companyTermsDAO) {
        this.companyTermsDAO = companyTermsDAO;
    }

    /**
     * list company terms.
     *
     * @return CompanyTermsVO
     */
    @Override
    public Optional<CompanyTermsVO> listCompanyTerms() {
        return companyTermsDAO.listCompanyTerms();
    }

    /**
     * update company details.
     *
     * @param companyTermsVO companyTermsVO
     * @return company terms vo
     */
    @Override
    public Optional<CompanyTermsVO> updateCompanyDetails(final CompanyTermsVO companyTermsVO) {
        return companyTermsDAO.updateCompanyDetails(companyTermsVO);
    }

}
