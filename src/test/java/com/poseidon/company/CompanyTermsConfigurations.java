package com.poseidon.company;

import com.poseidon.company.service.CompanyTermsService;
import com.poseidon.company.web.controller.CompanyTermsController;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class CompanyTermsConfigurations {
    @Bean
    public CompanyTermsController companyTermsController() {
        return new CompanyTermsController();
    }

    @Bean
    public CompanyTermsService companyTermsService() {
        return Mockito.mock(CompanyTermsService.class);
    }
}
