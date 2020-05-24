package com.poseidon.reports;

import com.poseidon.make.service.MakeService;
import com.poseidon.reports.service.ReportsService;
import com.poseidon.reports.web.controller.ReportsController;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class ReportsConfigurations {
    @Bean
    public ReportsController reportsController() {
        return new ReportsController();
    }

    @Bean
    public ReportsService reportsService() {
        return Mockito.mock(ReportsService.class);
    }

    @Bean
    public MakeService makeService() {
        return Mockito.mock(MakeService.class);
    }
}
