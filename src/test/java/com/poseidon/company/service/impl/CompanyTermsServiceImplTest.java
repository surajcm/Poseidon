package com.poseidon.company.service.impl;

import com.poseidon.company.dao.CompanyTermsDAO;
import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.exception.CompanyTermsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CompanyTermsServiceImplTest {
    private final CompanyTermsServiceImpl companyTermsService = new CompanyTermsServiceImpl();
    private final CompanyTermsDAO companyTermsDAO = Mockito.mock(CompanyTermsDAO.class);

    @BeforeEach
    public void setup() {
        Whitebox.setInternalState(companyTermsService, "companyTermsDAO", companyTermsDAO);
    }

    @Test
    void listCompanyTermsSuccess() throws CompanyTermsException {
        when(companyTermsDAO.listCompanyTerms()).thenReturn(mockCompanyTermsVO());
        var companyTermsVO = companyTermsService.listCompanyTerms();
        Assertions.assertEquals("ABC", companyTermsVO.getCompanyName());
    }

    @Test
    void listCompanyTermsFailure() throws CompanyTermsException {
        when(companyTermsDAO.listCompanyTerms())
                .thenThrow(new CompanyTermsException(CompanyTermsException.DATABASE_ERROR));
        Assertions.assertNull(companyTermsService.listCompanyTerms());
    }

    @Test
    void updateCompanyDetailsSuccess() throws CompanyTermsException {
        when(companyTermsDAO.updateCompanyDetails(any())).thenReturn(mockCompanyTermsVO());
        var companyTermsVO = companyTermsService.updateCompanyDetails(new CompanyTermsVO());
        Assertions.assertEquals("ABC", companyTermsVO.getCompanyName());
    }

    @Test
    void updateCompanyDetailsFailure() throws CompanyTermsException {
        when(companyTermsDAO.updateCompanyDetails(any()))
                .thenThrow(new CompanyTermsException(CompanyTermsException.DATABASE_ERROR));
        Assertions.assertNull(companyTermsService.updateCompanyDetails(new CompanyTermsVO()));
    }

    private CompanyTermsVO mockCompanyTermsVO() {
        var companyTermsVO = new CompanyTermsVO();
        companyTermsVO.setCompanyName("ABC");
        return companyTermsVO;
    }
}