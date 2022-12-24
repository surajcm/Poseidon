package com.poseidon.company.service;


import com.poseidon.company.dao.CompanyDAO;
import com.poseidon.company.dao.entities.CompanyTerms;
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
        var companyTerms = companyDAO.listCompanyTerms(companyCode);
        return companyTerms.map(this::convertToCompanyTermsVO);
    }

    /**
     * update company details.
     *
     * @param companyTermsVO companyTermsVO
     * @return company terms vo
     */
    public Optional<CompanyTermsVO> updateCompanyDetails(final CompanyTermsVO companyTermsVO) {
        var companyTerms = getCompanyTerms(companyTermsVO, new CompanyTerms());
        return companyDAO.updateCompanyDetails(companyTerms).map(this::convertToCompanyTermsVO);
    }

    public boolean isValidCompanyCode(final String companyCode) {
        return companyDAO.isValidCompanyCode(companyCode);
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

}
