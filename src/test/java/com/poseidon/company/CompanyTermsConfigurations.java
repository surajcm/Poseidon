package com.poseidon.company;

import com.poseidon.company.service.CompanyService;
import com.poseidon.company.web.controller.CompanyController;
import com.poseidon.user.service.UserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class CompanyTermsConfigurations {
    @Bean
    public CompanyController companyTermsController() {
        return new CompanyController(
                Mockito.mock(CompanyService.class),
                Mockito.mock(UserService.class));
    }

    @Bean
    public CompanyService companyTermsService() {
        return Mockito.mock(CompanyService.class);
    }

    @Bean
    public UserService userService() {
        return Mockito.mock(UserService.class);
    }
}
