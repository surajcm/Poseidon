package com.poseidon.company.dao.impl;

import com.poseidon.company.dao.entities.CompanyTerms;
import com.poseidon.company.exception.CompanyTermsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

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

    private List<CompanyTerms> mockCompanyTermsList() {
        CompanyTerms companyTerms = new CompanyTerms();
        List<CompanyTerms> companyTermsList = new ArrayList<>();
        companyTermsList.add(companyTerms);
        return companyTermsList;
    }

}