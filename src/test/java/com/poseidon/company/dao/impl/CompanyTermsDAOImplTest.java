package com.poseidon.company.dao.impl;

import com.poseidon.company.dao.entities.CompanyTerms;
import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.exception.CompanyTermsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CompanyTermsDAOImplTest {
    private final CompanyTermsDAOImpl companyTermsDAO = new CompanyTermsDAOImpl();
    private final CompanyTermsRepository companyTermsRepository = Mockito.mock(CompanyTermsRepository.class);

    @BeforeEach
    public void setup() {
        Whitebox.setInternalState(companyTermsDAO, "companyTermsRepository", companyTermsRepository);
    }

    @Test
    public void listCompanyTermsSuccess() throws CompanyTermsException {
        when(companyTermsRepository.findAll()).thenReturn(mockCompanyTermsList());
        Assertions.assertNotNull(companyTermsDAO.listCompanyTerms());
    }

    @Test
    public void listCompanyTermsFailure() throws CompanyTermsException {
        when(companyTermsRepository.findAll()).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(CompanyTermsException.class, companyTermsDAO::listCompanyTerms);
    }

    @Test
    public void updateCompanyDetailsSuccessWithNull() throws CompanyTermsException {
        when(companyTermsRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertNull(companyTermsDAO.updateCompanyDetails(new CompanyTermsVO()));
    }

    @Test
    public void updateCompanyDetailsSuccessWithNotNull() throws CompanyTermsException {
        when(companyTermsRepository.findById(anyLong())).thenReturn(Optional.of(mockCompanyTerms()));
        when(companyTermsRepository.save(any())).thenReturn(mockCompanyTerms());
        Assertions.assertNotNull(companyTermsDAO.updateCompanyDetails(new CompanyTermsVO()));
    }

    @Test
    public void updateCompanyDetailsFailure() throws CompanyTermsException {
        when(companyTermsRepository.findById(anyLong())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(CompanyTermsException.class,
                () -> companyTermsDAO.updateCompanyDetails(new CompanyTermsVO()));
    }

    private List<CompanyTerms> mockCompanyTermsList() {
        CompanyTerms companyTerms = new CompanyTerms();
        List<CompanyTerms> companyTermsList = new ArrayList<>();
        companyTermsList.add(companyTerms);
        return companyTermsList;
    }

    private CompanyTerms mockCompanyTerms() {
        CompanyTerms companyTerms = new CompanyTerms();
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