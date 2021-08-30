package com.poseidon.company.web.controller;

import com.poseidon.company.CompanyTermsConfigurations;
import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.service.CompanyTermsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CompanyController.class)
@ContextConfiguration(classes = {CompanyTermsConfigurations.class})
class CompanyControllerTest {
    private MockMvc mvc;
    @Autowired
    private CompanyController companyController;
    @Autowired
    private CompanyTermsService companyTermsService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(companyController).build();
    }

    @Test
    void listNormal() throws Exception {
        mvc.perform(post("/company/Company.htm")).andExpect(status().isOk());
        when(companyTermsService.listCompanyTerms()).thenThrow(new RuntimeException());
        mvc.perform(post("/company/Company.htm")).andExpect(status().isOk());
    }

    @Test
    void list() throws Exception {
        when(companyTermsService.listCompanyTerms()).thenReturn(Optional.of(new CompanyTermsVO()));
        mvc.perform(post("/company/Company.htm")).andExpect(status().isOk());
    }

    @Test
    void updateCompanyDetailsSuccess() throws Exception {
        mvc.perform(post("/company/updateCompanyDetails.htm")).andExpect(status().isOk());
        when(companyTermsService.updateCompanyDetails(any())).thenThrow(new RuntimeException());
        mvc.perform(post("/company/updateCompanyDetails.htm")).andExpect(status().isOk());
    }

    @Test
    void updateCompanyDetails() throws Exception {
        when(companyTermsService.updateCompanyDetails(any())).thenReturn(Optional.of(new CompanyTermsVO()));
        mvc.perform(post("/company/updateCompanyDetails.htm")).andExpect(status().isOk());
    }
}