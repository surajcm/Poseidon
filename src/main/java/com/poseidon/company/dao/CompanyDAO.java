package com.poseidon.company.dao;

import com.poseidon.company.dao.entities.CompanyTerms;
import com.poseidon.company.dao.repo.CompanyRepository;
import com.poseidon.company.domain.CompanyTermsVO;
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
    public Optional<CompanyTermsVO> listCompanyTerms(final String companyCode) {
        var companyTerms = companyRepository.findByCompanyCode(companyCode);
        return companyTerms.map(this::convertToCompanyTermsVO);
    }

    /**
     * update company details.
     *
     * @param companyTermsVO companyTermsVO
     * @return CompanyTermsVO
     */
    public Optional<CompanyTermsVO> updateCompanyDetails(final CompanyTermsVO companyTermsVO) {
        var optionalCompanyTerms =
                sneak(companyRepository::findFirstByOrderByIdAsc);
        return optionalCompanyTerms
                .map(terms -> getCompanyTerms(companyTermsVO, terms))
                .map(companyRepository::save)
                .map(this::convertToCompanyTermsVO);
    }

    private CompanyTermsVO convertToCompanyTermsVO(final CompanyTerms companyTerms) {
        var companyTermsVO = new CompanyTermsVO();
        companyTermsVO.setCompanyName(companyTerms.getCompanyName());
        companyTermsVO.setCompanyAddress(companyTerms.getCompanyAddress());
        companyTermsVO.setCompanyPhoneNumber(companyTerms.getCompanyPhone());
        companyTermsVO.setCompanyEmail(companyTerms.getCompanyEmail());
        companyTermsVO.setCompanyWebsite(companyTerms.getCompanyWebsite());
        companyTermsVO.setCompanyCode(companyTerms.getCompanyCode());
        companyTermsVO.setCompanyVatTin(companyTerms.getVatTin());
        companyTermsVO.setCompanyCstTin(companyTerms.getCstTin());
        companyTermsVO.setTermsAndConditions(companyTerms.getTerms());
        return companyTermsVO;
    }

    private CompanyTerms getCompanyTerms(final CompanyTermsVO companyTermsVO,
                                         final CompanyTerms companyTerms) {
        companyTerms.setCompanyName(companyTermsVO.getCompanyName());
        companyTerms.setTerms(companyTermsVO.getTermsAndConditions());
        companyTerms.setCompanyAddress(companyTermsVO.getCompanyAddress());
        companyTerms.setCompanyPhone(companyTermsVO.getCompanyPhoneNumber());
        companyTerms.setCompanyEmail(companyTermsVO.getCompanyEmail());
        companyTerms.setCompanyWebsite(companyTermsVO.getCompanyWebsite());
        companyTerms.setCompanyCode(companyTermsVO.getCompanyCode());
        companyTerms.setVatTin(companyTermsVO.getCompanyVatTin());
        companyTerms.setCstTin(companyTermsVO.getCompanyCstTin());
        companyTerms.setModifiedBy(companyTermsVO.getModifiedBy());
        return companyTerms;
    }

    public boolean isValidCompanyCode(final String companyCode) {
        var companyTerms = companyRepository.findByCompanyCode(companyCode);
        return companyTerms.isPresent();
    }
}
