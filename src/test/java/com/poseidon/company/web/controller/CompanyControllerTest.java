package com.poseidon.company.web.controller;

import com.poseidon.company.CompanyTermsConfigurations;
import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.service.CompanyTermsService;
import com.poseidon.user.domain.UserVO;
import com.poseidon.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import static org.mockito.ArgumentMatchers.anyString;
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
    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(companyController).build();
    }

    @Test
    @Disabled
    void listNormal() throws Exception {
        when(userService.findUserFromName(anyString())).thenReturn(mockUser());
        mvc.perform(post("/company/company")).andExpect(status().isOk());
        when(companyTermsService.listCompanyTerms(anyString())).thenThrow(new RuntimeException());
        mvc.perform(post("/company/company")).andExpect(status().isOk());
    }

    private UserVO mockUser() {
        var user = new UserVO();
        user.setId(1234L);
        user.setName("ABC");
        user.setEmail("ABC");
        user.setPassword("PASS");
        user.setRole("ADMIN");
        user.setCompanyCode("QC01");
        return user;
    }

    @Test
    @Disabled
    void list() throws Exception {
        when(companyTermsService.listCompanyTerms(anyString())).thenReturn(Optional.of(new CompanyTermsVO()));
        mvc.perform(post("/company/company")).andExpect(status().isOk());
    }

    @Test
    @Disabled
    void updateCompanyDetailsSuccess() throws Exception {
        mvc.perform(post("/company/updateCompanyDetails")).andExpect(status().isOk());
        when(companyTermsService.updateCompanyDetails(any())).thenThrow(new RuntimeException());
        mvc.perform(post("/company/updateCompanyDetails")).andExpect(status().isOk());
    }

    @Test
    void updateCompanyDetails() throws Exception {
        when(companyTermsService.updateCompanyDetails(any())).thenReturn(Optional.of(new CompanyTermsVO()));
        mvc.perform(post("/company/updateCompanyDetails")).andExpect(status().isOk());
    }
}