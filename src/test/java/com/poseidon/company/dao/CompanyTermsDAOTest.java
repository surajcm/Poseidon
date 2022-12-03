package com.poseidon.company.dao;

import com.poseidon.company.dao.entities.CompanyTerms;
import com.poseidon.company.dao.repo.CompanyTermsRepository;
import com.poseidon.company.domain.CompanyTermsVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CompanyTermsDAOTest {
    private final CompanyTermsRepository companyTermsRepository = Mockito.mock(CompanyTermsRepository.class);
    private final CompanyTermsDAO companyTermsDAO = new CompanyTermsDAO(companyTermsRepository);

    @Test
    void listCompanyTermsSuccess() {
        when(companyTermsRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.of(mockCompanyTerms()));
        Assertions.assertNotNull(companyTermsDAO.listCompanyTerms("QC01"));
    }

    @Test
    void listCompanyTermsEmpty() {
        when(companyTermsRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.empty());
        Assertions.assertTrue(companyTermsDAO.listCompanyTerms("QC01").isEmpty());
    }

    @Test
    void updateCompanyDetailsSuccessWithNull() {
        when(companyTermsRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.empty());
        Assertions.assertTrue(companyTermsDAO.updateCompanyDetails(new CompanyTermsVO()).isEmpty());
    }

    @Test
    void updateCompanyDetailsSuccessWithNotNull() {
        when(companyTermsRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.of(mockCompanyTerms()));
        when(companyTermsRepository.save(any())).thenReturn(mockCompanyTerms());
        Assertions.assertNotNull(companyTermsDAO.updateCompanyDetails(new CompanyTermsVO()));
    }

    private CompanyTerms mockCompanyTerms() {
        var companyTerms = new CompanyTerms();
        companyTerms.setCompanyName("ABC");
        companyTerms.setTerms("ABC");
        companyTerms.setCompanyAddress("ABC");
        companyTerms.setCompanyPhone("ABC");
        companyTerms.setCompanyEmail("ABC");
        companyTerms.setCompanyWebsite("ABC");
        companyTerms.setVatTin("ABC");
        companyTerms.setCstTin("ABC");
        companyTerms.setModifiedBy("ABC");
        return companyTerms;
    }

}