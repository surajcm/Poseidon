package com.poseidon.company.dao.impl;

import com.poseidon.company.dao.CompanyTermsDAO;
import com.poseidon.company.dao.entities.CompanyTerms;
import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.exception.CompanyTermsException;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 10:00:05 PM
 */
@Repository
@SuppressWarnings("unused")
public class CompanyTermsDAOImpl implements CompanyTermsDAO {

    private static final Logger logger = LoggerFactory.getLogger(CompanyTermsDAOImpl.class);

    @Autowired
    private CompanyTermsRepository companyTermsRepository;

    /**
     * list company terms
     *
     * @return CompanyTermsVO
     * @throws CompanyTermsException on error
     */
    public CompanyTermsVO listCompanyTerms() throws CompanyTermsException {
        CompanyTermsVO companyTermsVO = null;
        try {
            List<CompanyTerms> companyTermsList = companyTermsRepository.findAll();
            if (CollectionUtils.isNotEmpty(companyTermsList)) {
                CompanyTerms companyTerms = companyTermsList.get(0);
                companyTermsVO = convertToCompanyTermsVO(companyTerms);
            }
        } catch (DataAccessException e) {
            throw new CompanyTermsException(CompanyTermsException.DATABASE_ERROR);
        }
        return companyTermsVO;
    }

    private CompanyTermsVO convertToCompanyTermsVO(CompanyTerms companyTerms) {
        CompanyTermsVO companyTermsVO = new CompanyTermsVO();
        companyTermsVO.setCompanyTerms(companyTerms.getTerms());
        companyTermsVO.setCompanyAddress(companyTerms.getCompanyAddress());
        companyTermsVO.setCompanyName(companyTerms.getCompanyAddress());
        companyTermsVO.setCompanyPhoneNumber(companyTerms.getCompanyPhone());
        companyTermsVO.setCompanyEmail(companyTerms.getCompanyEmail());
        companyTermsVO.setCompanyWebsite(companyTerms.getCompanyWebsite());
        companyTermsVO.setCompanyVATTIN(companyTerms.getVatTin());
        companyTermsVO.setCompanyCSTTIN(companyTerms.getCstTin());
        return companyTermsVO;
    }

    /**
     * update company details
     *
     * @param companyTermsVO companyTermsVO
     * @return CompanyTermsVO
     * @throws CompanyTermsException on error
     */
    public CompanyTermsVO updateCompanyDetails(CompanyTermsVO companyTermsVO) throws CompanyTermsException {
        CompanyTermsVO termsVO = null;
        try {
            Optional<CompanyTerms> optionalCompanyTerms = companyTermsRepository.findById(1L);
            if (optionalCompanyTerms.isPresent()) {
                CompanyTerms companyTerms = optionalCompanyTerms.get();
                companyTerms.setCompanyName(companyTermsVO.getCompanyName());
                companyTerms.setTerms(companyTermsVO.getCompanyTerms());
                companyTerms.setCompanyAddress(companyTermsVO.getCompanyAddress());
                companyTerms.setCompanyPhone(companyTermsVO.getCompanyPhoneNumber());
                companyTerms.setCompanyEmail(companyTermsVO.getCompanyEmail());
                companyTerms.setCompanyWebsite(companyTermsVO.getCompanyWebsite());
                companyTerms.setVatTin(companyTermsVO.getCompanyVATTIN());
                companyTerms.setCstTin(companyTermsVO.getCompanyCSTTIN());
                companyTerms.setModifiedBy(companyTermsVO.getModifiedBy());
                CompanyTerms updatedCompanyTerms = companyTermsRepository.save(companyTerms);
                termsVO = convertToCompanyTermsVO(updatedCompanyTerms);
            }
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw new CompanyTermsException(CompanyTermsException.DATABASE_ERROR);
        }
        return termsVO;
    }
}
