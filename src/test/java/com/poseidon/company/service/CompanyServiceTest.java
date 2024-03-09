package com.poseidon.company.service;

import com.poseidon.company.dao.entities.CompanyTerms;
import com.poseidon.company.dao.repo.CompanyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CompanyServiceTest {
    private static final String COMPANY_CODE = "ABC";
    private final CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
    private final CompanyService companyService = new CompanyService(companyRepository);


    @Test
    void listCompanyTermsSuccess() {
        when(companyRepository.findByCode("QC01")).thenReturn(mockCompanyTerms());
        var companyTerms = companyService.listCompanyTerms("QC01");
        Assertions.assertTrue(companyTerms.isPresent());
        Assertions.assertEquals(COMPANY_CODE, companyTerms.get().getName());
    }

    @Test
    void updateCompanyDetailsSuccess() {
        when(companyRepository.findById(any())).thenReturn(mockCompanyTerms());
        when(companyRepository.save(any())).thenReturn(mockCompany());
        var companyTerms = companyService.updateCompanyDetails(new CompanyTerms());
        Assertions.assertTrue(companyTerms.isPresent());
        Assertions.assertEquals(COMPANY_CODE, companyTerms.get().getName());
    }

    @Test
    void isValidCompanyCode() {
        when(companyRepository.findByCode(anyString())).thenReturn(Optional.of(new CompanyTerms()));
        Assertions.assertTrue(companyService.isValidCompanyCode(COMPANY_CODE));
    }

    private Optional<CompanyTerms> mockCompanyTerms() {
        return Optional.of(mockCompany());
    }

    private CompanyTerms mockCompany() {
        var companyTerms = new CompanyTerms();
        companyTerms.setName(COMPANY_CODE);
        return companyTerms;
    }
}