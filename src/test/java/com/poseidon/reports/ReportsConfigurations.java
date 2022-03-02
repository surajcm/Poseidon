package com.poseidon.reports;

import com.poseidon.make.service.MakeService;
import com.poseidon.reports.service.ReportsService;
import com.poseidon.reports.util.ReportsUtil;
import com.poseidon.reports.web.controller.ReportsController;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class ReportsConfigurations {
    @Bean
    public ReportsController reportsController() {
        return new ReportsController(Mockito.mock(ReportsService.class),
                Mockito.mock(MakeService.class),
                Mockito.mock(ReportsUtil.class));
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
