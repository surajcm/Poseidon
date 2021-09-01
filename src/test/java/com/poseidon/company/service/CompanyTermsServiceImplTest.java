package com.poseidon.company.service;

import com.poseidon.company.dao.CompanyTermsDAO;
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
class CompanyTermsServiceImplTest {
    private final CompanyTermsDAO companyTermsDAO = Mockito.mock(CompanyTermsDAO.class);
    private final CompanyTermsService companyTermsService = new CompanyTermsService(companyTermsDAO);

    @Test
    void listCompanyTermsSuccess() {
        when(companyTermsDAO.listCompanyTerms()).thenReturn(mockCompanyTermsVO());
        var companyTermsVO = companyTermsService.listCompanyTerms();
        Assertions.assertEquals("ABC", companyTermsVO.get().getCompanyName());
    }

    @Test
    void updateCompanyDetailsSuccess() {
        when(companyTermsDAO.updateCompanyDetails(any())).thenReturn(mockCompanyTermsVO());
        var companyTermsVO = companyTermsService.updateCompanyDetails(new CompanyTermsVO());
        Assertions.assertEquals("ABC", companyTermsVO.get().getCompanyName());
    }

    private Optional<CompanyTermsVO> mockCompanyTermsVO() {
        var companyTermsVO = new CompanyTermsVO();
        companyTermsVO.setCompanyName("ABC");
        return Optional.of(companyTermsVO);
    }
}