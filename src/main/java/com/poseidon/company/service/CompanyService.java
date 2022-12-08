package com.poseidon.company.service;


import com.poseidon.company.dao.CompanyDAO;
import com.poseidon.company.domain.CompanyTermsVO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyDAO companyDAO;

    public CompanyService(final CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    /**
     * list company terms.
     *
     * @return CompanyTermsVO
     */
    public Optional<CompanyTermsVO> listCompanyTerms(final String companyCode) {
        return companyDAO.listCompanyTerms(companyCode);
    }

    /**
     * update company details.
     *
     * @param companyTermsVO companyTermsVO
     * @return company terms vo
     */
    public Optional<CompanyTermsVO> updateCompanyDetails(final CompanyTermsVO companyTermsVO) {
        return companyDAO.updateCompanyDetails(companyTermsVO);
    }

    public Boolean isValidCompanyCode(final String companyCode) {
        return companyDAO.isValidCompanyCode(companyCode);
    }

}
