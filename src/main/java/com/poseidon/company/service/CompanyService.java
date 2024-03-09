package com.poseidon.company.service;


import com.poseidon.company.dao.entities.CompanyTerms;
import com.poseidon.company.dao.repo.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(final CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * list company terms.
     *
     * @return CompanyTermsVO
     */
    public Optional<CompanyTerms> listCompanyTerms(final String companyCode) {
        return companyRepository.findByCode(companyCode);
    }

    /**
     * update company details.
     *
     * @param companyTerms companyTerms
     * @return company terms vo
     */
    public Optional<CompanyTerms> updateCompanyDetails(final CompanyTerms companyTerms) {
        var optionalCompanyTerms =
                companyRepository.findById(companyTerms.getId());
        return optionalCompanyTerms
                .map(c -> updateCompany(c, companyTerms))
                .map(companyRepository::save);
    }

    public boolean isValidCompanyCode(final String companyCode) {
        return companyRepository.findByCode(companyCode).isPresent();
    }

    private CompanyTerms updateCompany(final CompanyTerms fromDB,
                                       final CompanyTerms companyTerms) {
        fromDB.setName(companyTerms.getName());
        fromDB.setTerms(companyTerms.getTerms());
        fromDB.setAddress(companyTerms.getAddress());
        fromDB.setPhone(companyTerms.getPhone());
        fromDB.setEmail(companyTerms.getEmail());
        fromDB.setWebsite(companyTerms.getWebsite());
        fromDB.setCode(companyTerms.getCode());
        fromDB.setVatTin(companyTerms.getVatTin());
        fromDB.setCstTin(companyTerms.getCstTin());
        fromDB.setModifiedBy(companyTerms.getModifiedBy());
        return fromDB;
    }


    public List<CompanyTerms> listCompanies() {
        return companyRepository.findAll();
    }

    public void deleteCompany(final Long id) {
        companyRepository.deleteById(id);
    }

    public CompanyTerms getCompanyTerms(final Long id) {
        return companyRepository.getReferenceById(id);
    }
}
