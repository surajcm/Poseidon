package com.poseidon.company.service;

import com.poseidon.company.dao.CompanyDAO;
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
class CompanyServiceTest {
    private final CompanyDAO companyDAO = Mockito.mock(CompanyDAO.class);
    private final CompanyService companyService = new CompanyService(companyDAO);

    @Test
    void listCompanyTermsSuccess() {
        when(companyDAO.listCompanyTerms("QC01")).thenReturn(mockCompanyTermsVO());
        var companyTermsVO = companyService.listCompanyTerms("QC01");
        Assertions.assertEquals("ABC", companyTermsVO.get().getCompanyName());
    }

    @Test
    void updateCompanyDetailsSuccess() {
        when(companyDAO.updateCompanyDetails(any())).thenReturn(mockCompanyTermsVO());
        var companyTermsVO = companyService.updateCompanyDetails(new CompanyTermsVO());
        Assertions.assertEquals("ABC", companyTermsVO.get().getCompanyName());
    }

    private Optional<CompanyTermsVO> mockCompanyTermsVO() {
        var companyTermsVO = new CompanyTermsVO();
        companyTermsVO.setCompanyName("ABC");
        return Optional.of(companyTermsVO);
    }
}