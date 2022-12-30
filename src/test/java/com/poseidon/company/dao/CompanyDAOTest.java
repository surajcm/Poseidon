package com.poseidon.company.dao;

import com.poseidon.company.dao.entities.CompanyTerms;
import com.poseidon.company.dao.repo.CompanyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CompanyDAOTest {
    private static final String COMPANY_CODE = "ABC";
    private final CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
    private final CompanyDAO companyDAO = new CompanyDAO(companyRepository);

    @Test
    void listCompanyTermsSuccess() {
        when(companyRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.of(mockCompanyTerms()));
        Assertions.assertNotNull(companyDAO.listCompanyTerms("QC01"));
    }

    @Test
    void listCompanyTermsEmpty() {
        when(companyRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.empty());
        Assertions.assertTrue(companyDAO.listCompanyTerms("QC01").isEmpty());
    }

    @Test
    void updateCompanyDetailsSuccessWithNull() {
        when(companyRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.empty());
        Assertions.assertTrue(companyDAO.updateCompanyDetails(new CompanyTerms()).isEmpty());
    }

    @Test
    void updateCompanyDetailsSuccessWithNotNull() {
        when(companyRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.of(mockCompanyTerms()));
        when(companyRepository.save(any())).thenReturn(mockCompanyTerms());
        Assertions.assertNotNull(companyDAO.updateCompanyDetails(new CompanyTerms()));
    }

    @Test
    void isValidCompanyCode() {
        when(companyRepository.findByCode(anyString())).thenReturn(Optional.of(mockCompanyTerms()));
        Assertions.assertTrue(companyDAO.isValidCompanyCode(COMPANY_CODE));
    }

    private CompanyTerms mockCompanyTerms() {
        var companyTerms = new CompanyTerms();
        companyTerms.setName(COMPANY_CODE);
        companyTerms.setTerms(COMPANY_CODE);
        companyTerms.setAddress(COMPANY_CODE);
        companyTerms.setPhone(COMPANY_CODE);
        companyTerms.setEmail(COMPANY_CODE);
        companyTerms.setWebsite(COMPANY_CODE);
        companyTerms.setVatTin(COMPANY_CODE);
        companyTerms.setCstTin(COMPANY_CODE);
        companyTerms.setModifiedBy(COMPANY_CODE);
        return companyTerms;
    }

}