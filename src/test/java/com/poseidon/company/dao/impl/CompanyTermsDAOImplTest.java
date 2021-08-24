package com.poseidon.company.dao.impl;

import com.poseidon.company.dao.entities.CompanyTerms;
import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.exception.CompanyTermsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CompanyTermsDAOImplTest {
    private final CompanyTermsRepository companyTermsRepository = Mockito.mock(CompanyTermsRepository.class);
    private final CompanyTermsDAOImpl companyTermsDAO = new CompanyTermsDAOImpl(companyTermsRepository);

    @Test
    void listCompanyTermsSuccess() throws CompanyTermsException {
        when(companyTermsRepository.findFirstByOrderByCompanyIdAsc()).thenReturn(Optional.of(mockCompanyTerms()));
        Assertions.assertNotNull(companyTermsDAO.listCompanyTerms());
    }

    @Test
    void listCompanyTermsEmpty() throws CompanyTermsException {
        when(companyTermsRepository.findFirstByOrderByCompanyIdAsc()).thenReturn(Optional.empty());
        Assertions.assertNull(companyTermsDAO.listCompanyTerms());
    }

    @Test
    void listCompanyTermsFailure() {
        when(companyTermsRepository.findFirstByOrderByCompanyIdAsc())
                .thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(CompanyTermsException.class, companyTermsDAO::listCompanyTerms);
    }

    @Test
    void updateCompanyDetailsSuccessWithNull() throws CompanyTermsException {
        when(companyTermsRepository.findFirstByOrderByCompanyIdAsc()).thenReturn(Optional.empty());
        Assertions.assertNull(companyTermsDAO.updateCompanyDetails(new CompanyTermsVO()));
    }

    @Test
    void updateCompanyDetailsSuccessWithNotNull() throws CompanyTermsException {
        when(companyTermsRepository.findFirstByOrderByCompanyIdAsc()).thenReturn(Optional.of(mockCompanyTerms()));
        when(companyTermsRepository.save(any())).thenReturn(mockCompanyTerms());
        Assertions.assertNotNull(companyTermsDAO.updateCompanyDetails(new CompanyTermsVO()));
    }

    @Test
    void updateCompanyDetailsFailure() {
        when(companyTermsRepository.findFirstByOrderByCompanyIdAsc())
                .thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(CompanyTermsException.class,
                () -> companyTermsDAO.updateCompanyDetails(new CompanyTermsVO()));
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