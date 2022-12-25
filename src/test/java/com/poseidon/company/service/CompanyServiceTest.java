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
    private final CompanyDAO companyDAO = Mockito.mock(CompanyDAO.class);
    private final CompanyService companyService = new CompanyService(companyDAO);

    @Test
    void listCompanyTermsSuccess() {
        when(companyDAO.listCompanyTerms("QC01")).thenReturn(mockCompanyTerms());
        var companyTermsVO = companyService.listCompanyTerms("QC01");
        Assertions.assertTrue(companyTermsVO.isPresent());
        Assertions.assertEquals("ABC", companyTermsVO.get().getName());
    }

    @Test
    void updateCompanyDetailsSuccess() {
        when(companyDAO.updateCompanyDetails(any())).thenReturn(mockCompanyTerms());
        var companyTermsVO = companyService.updateCompanyDetails(new CompanyTerms());
        Assertions.assertTrue(companyTermsVO.isPresent());
        Assertions.assertEquals("ABC", companyTermsVO.get().getName());
    }

    @Test
    void isValidCompanyCode() {
        when(companyDAO.isValidCompanyCode(anyString())).thenReturn(true);
        Assertions.assertTrue(companyService.isValidCompanyCode("ABC"));
    }

    private Optional<CompanyTerms> mockCompanyTerms() {
        var companyTerms = new CompanyTerms();
        companyTerms.setName("ABC");
        return Optional.of(companyTerms);
    }
}