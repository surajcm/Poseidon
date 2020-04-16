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
public class CompanyTermsServiceImplTest {
    private final CompanyTermsServiceImpl companyTermsService = new CompanyTermsServiceImpl();
    private final CompanyTermsDAO companyTermsDAO = Mockito.mock(CompanyTermsDAO.class);

    @BeforeEach
    public void setup() {
        Whitebox.setInternalState(companyTermsService, "companyTermsDAO", companyTermsDAO);
    }

    @Test
    public void listCompanyTermsSuccess() throws CompanyTermsException {
        when(companyTermsDAO.listCompanyTerms()).thenReturn(mockCompanyTermsVO());
        CompanyTermsVO companyTermsVO = companyTermsService.listCompanyTerms();
        Assertions.assertEquals("ABC", companyTermsVO.getCompanyName());
    }

    @Test
    public void listCompanyTermsFailure() throws CompanyTermsException {
        when(companyTermsDAO.listCompanyTerms())
                .thenThrow(new CompanyTermsException(CompanyTermsException.DATABASE_ERROR));
        Assertions.assertNull(companyTermsService.listCompanyTerms());
    }

    @Test
    public void updateCompanyDetailsSuccess() throws CompanyTermsException {
        when(companyTermsDAO.updateCompanyDetails(any())).thenReturn(mockCompanyTermsVO());
        CompanyTermsVO companyTermsVO = companyTermsService.updateCompanyDetails(new CompanyTermsVO());
        Assertions.assertEquals("ABC", companyTermsVO.getCompanyName());
    }

    @Test
    public void updateCompanyDetailsFailure() throws CompanyTermsException {
        when(companyTermsDAO.updateCompanyDetails(any()))
                .thenThrow(new CompanyTermsException(CompanyTermsException.DATABASE_ERROR));
        Assertions.assertNull(companyTermsService.updateCompanyDetails(new CompanyTermsVO()));
    }

    private CompanyTermsVO mockCompanyTermsVO() {
        CompanyTermsVO companyTermsVO = new CompanyTermsVO();
        companyTermsVO.setCompanyName("ABC");
        return companyTermsVO;
    }
}