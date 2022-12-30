package com.poseidon.company.service;

import com.poseidon.company.dao.CompanyDAO;
import com.poseidon.company.dao.entities.CompanyTerms;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CompanyServiceTest {
    private static final String COMPANY_CODE = "ABC";
    private final CompanyDAO companyDAO = Mockito.mock(CompanyDAO.class);
    private final CompanyService companyService = new CompanyService(companyDAO);

    @Test
    void listCompanyTermsSuccess() {
        when(companyDAO.listCompanyTerms("QC01")).thenReturn(mockCompanyTerms());
        var companyTermsVO = companyService.listCompanyTerms("QC01");
        Assertions.assertTrue(companyTermsVO.isPresent());
        Assertions.assertEquals(COMPANY_CODE, companyTermsVO.get().getName());
    }

    @Test
    void updateCompanyDetailsSuccess() {
        when(companyDAO.updateCompanyDetails(any())).thenReturn(mockCompanyTerms());
        var companyTermsVO = companyService.updateCompanyDetails(new CompanyTerms());
        Assertions.assertTrue(companyTermsVO.isPresent());
        Assertions.assertEquals(COMPANY_CODE, companyTermsVO.get().getName());
    }

    @Test
    void isValidCompanyCode() {
        when(companyDAO.isValidCompanyCode(anyString())).thenReturn(true);
        Assertions.assertTrue(companyService.isValidCompanyCode(COMPANY_CODE));
    }

    private Optional<CompanyTerms> mockCompanyTerms() {
        var companyTerms = new CompanyTerms();
        companyTerms.setName(COMPANY_CODE);
        return Optional.of(companyTerms);
    }
}