package com.poseidon.company;

import com.poseidon.company.service.CompanyTermsService;
import com.poseidon.company.web.controller.CompanyController;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class CompanyTermsConfigurations {
    @Bean
    public CompanyController companyTermsController() {
        return new CompanyController();
    }

    @Bean
    public CompanyTermsService companyTermsService() {
        return Mockito.mock(CompanyTermsService.class);
    }
}
