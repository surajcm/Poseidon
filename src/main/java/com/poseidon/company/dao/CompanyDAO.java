package com.poseidon.company.dao;

import com.poseidon.company.dao.entities.CompanyTerms;
import com.poseidon.company.dao.repo.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.rainerhahnekamp.sneakythrow.Sneaky.sneak;

@Service
@SuppressWarnings("unused")
public class CompanyDAO {
    private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

    private final CompanyRepository companyRepository;

    public CompanyDAO(final CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * list company terms.

     * @return CompanyTermsVO
     */
    public Optional<CompanyTerms> listCompanyTerms(final String companyCode) {
        return companyRepository.findByCode(companyCode);
    }

    /**
     * update company details.
     *
     * @param companyTerms companyTerms
     * @return CompanyTermsVO
     */
    public Optional<CompanyTerms> updateCompanyDetails(final CompanyTerms companyTerms) {
        var optionalCompanyTerms =
                sneak(companyRepository::findFirstByOrderByIdAsc);
        return optionalCompanyTerms
                .map(c -> updateCompany(c, companyTerms))
                .map(companyRepository::save);
    }

    public boolean isValidCompanyCode(final String companyCode) {
        var companyTerms = companyRepository.findByCode(companyCode);
        return companyTerms.isPresent();
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
}
