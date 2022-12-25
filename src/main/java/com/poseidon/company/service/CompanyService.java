package com.poseidon.company.service;


import com.poseidon.company.dao.CompanyDAO;
import com.poseidon.company.dao.entities.CompanyTerms;
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
    public Optional<CompanyTerms> listCompanyTerms(final String companyCode) {
        return companyDAO.listCompanyTerms(companyCode);
    }

    /**
     * update company details.
     *
     * @param companyTerms companyTerms
     * @return company terms vo
     */
    public Optional<CompanyTerms> updateCompanyDetails(final CompanyTerms companyTerms) {
        return companyDAO.updateCompanyDetails(companyTerms);
    }

    public boolean isValidCompanyCode(final String companyCode) {
        return companyDAO.isValidCompanyCode(companyCode);
    }


}
