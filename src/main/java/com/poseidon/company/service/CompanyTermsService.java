package com.poseidon.company.service;


import com.poseidon.company.dao.CompanyTermsDAO;
import com.poseidon.company.domain.CompanyTermsVO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyTermsService {

    private final CompanyTermsDAO companyTermsDAO;

    public CompanyTermsService(final CompanyTermsDAO companyTermsDAO) {
        this.companyTermsDAO = companyTermsDAO;
    }

    /**
     * list company terms.
     *
     * @return CompanyTermsVO
     */
    public Optional<CompanyTermsVO> listCompanyTerms(final String companyCode) {
        return companyTermsDAO.listCompanyTerms(companyCode);
    }

    /**
     * update company details.
     *
     * @param companyTermsVO companyTermsVO
     * @return company terms vo
     */
    public Optional<CompanyTermsVO> updateCompanyDetails(final CompanyTermsVO companyTermsVO) {
        return companyTermsDAO.updateCompanyDetails(companyTermsVO);
    }

}
