package com.poseidon.company.web.controller;

import com.poseidon.company.CompanyTermsConfigurations;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CompanyTermsController.class)
@ContextConfiguration(classes = {CompanyTermsConfigurations.class})
public class CompanyTermsControllerTest {
    private MockMvc mvc;
    @Autowired
    private CompanyTermsController companyTermsController;
    @Autowired
    private CompanyTermsService companyTermsService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(companyTermsController).build();
    }

    @Test
    public void list() throws Exception {
        mvc.perform(post("/company/List.htm")).andExpect(status().isOk());
    }

    @Test
    public void updateCompanyDetails() throws Exception {
        mvc.perform(post("/company/updateCompanyDetails.htm")).andExpect(status().isOk());
    }
}