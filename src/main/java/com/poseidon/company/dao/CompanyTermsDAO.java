package com.poseidon.company.dao;

import com.poseidon.company.dao.entities.CompanyTerms;
import com.poseidon.company.dao.repo.CompanyTermsRepository;
import com.poseidon.company.domain.CompanyTermsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.rainerhahnekamp.sneakythrow.Sneaky.sneak;

@Service
@SuppressWarnings("unused")
public class CompanyTermsDAO {
    private static final Logger logger = LoggerFactory.getLogger(CompanyTermsDAO.class);

    private final CompanyTermsRepository companyTermsRepository;

    public CompanyTermsDAO(final CompanyTermsRepository companyTermsRepository) {
        this.companyTermsRepository = companyTermsRepository;
    }

    /**
     * list company terms.

     * @return CompanyTermsVO
     */
    public Optional<CompanyTermsVO> listCompanyTerms() {
        var companyTerms = sneak(companyTermsRepository::findFirstByOrderByIdAsc);
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
                sneak(companyTermsRepository::findFirstByOrderByIdAsc);
        return optionalCompanyTerms
                .map(terms -> getCompanyTerms(companyTermsVO, terms))
                .map(companyTermsRepository::save)
                .map(this::convertToCompanyTermsVO);
    }

    private CompanyTermsVO convertToCompanyTermsVO(final CompanyTerms companyTerms) {
        var companyTermsVO = new CompanyTermsVO();
        companyTermsVO.setCompanyName(companyTerms.getCompanyName());
        companyTermsVO.setCompanyAddress(companyTerms.getCompanyAddress());
        companyTermsVO.setCompanyPhoneNumber(companyTerms.getCompanyPhone());
        companyTermsVO.setCompanyEmail(companyTerms.getCompanyEmail());
        companyTermsVO.setCompanyWebsite(companyTerms.getCompanyWebsite());
        companyTermsVO.setCompanyVatTin(companyTerms.getVatTin());
        companyTermsVO.setCompanyCstTin(companyTerms.getCstTin());
        companyTermsVO.setCompanyTerms(companyTerms.getTerms());
        return companyTermsVO;
    }

    private CompanyTerms getCompanyTerms(final CompanyTermsVO companyTermsVO,
                                         final CompanyTerms companyTerms) {
        companyTerms.setCompanyName(companyTermsVO.getCompanyName());
        companyTerms.setTerms(companyTermsVO.getCompanyTerms());
        companyTerms.setCompanyAddress(companyTermsVO.getCompanyAddress());
        companyTerms.setCompanyPhone(companyTermsVO.getCompanyPhoneNumber());
        companyTerms.setCompanyEmail(companyTermsVO.getCompanyEmail());
        companyTerms.setCompanyWebsite(companyTermsVO.getCompanyWebsite());
        companyTerms.setVatTin(companyTermsVO.getCompanyVatTin());
        companyTerms.setCstTin(companyTermsVO.getCompanyCstTin());
        companyTerms.setModifiedBy(companyTermsVO.getModifiedBy());
        return companyTerms;
    }
}
