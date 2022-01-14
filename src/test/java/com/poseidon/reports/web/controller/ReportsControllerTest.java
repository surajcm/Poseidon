package com.poseidon.reports.web.controller;

import com.poseidon.make.service.MakeService;
import com.poseidon.reports.ReportsConfigurations;
import com.poseidon.reports.service.ReportsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReportsController.class)
@ContextConfiguration(classes = {ReportsConfigurations.class})
class ReportsControllerTest {
    private MockMvc mvc;
    @Autowired
    private ReportsController reportsController;

    @Autowired
    private ReportsService reportsService;

    @Autowired
    private MakeService makeService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(reportsController).build();
    }

    @Test
    void list() throws Exception {
        mvc.perform(post("/reports/List")).andExpect(status().isOk());
        when(reportsService.generateDailyReport()).thenThrow(new RuntimeException());
        when(makeService.fetchMakes()).thenThrow(new RuntimeException());
        mvc.perform(post("/reports/List")).andExpect(status().isOk());
    }

    @Test
    void getMakeDetailsReport() throws Exception {
        mvc.perform(post("/reports/getMakeDetailsReport.htm")).andExpect(status().isOk());
    }

    @Test
    void getCallReport() throws Exception {
        mvc.perform(post("/reports/getCallReport.htm")).andExpect(status().isOk());
    }

    @Test
    void getTransactionsListReport() throws Exception {
        mvc.perform(post("/reports/getTransactionsListReport.htm")).andExpect(status().isOk());
    }

    @Test
    void getModelListReport() throws Exception {
        mvc.perform(post("/reports/getModelListReport.htm")).andExpect(status().isOk());
    }

    @Test
    void getErrorReport() throws Exception {
        mvc.perform(post("/reports/getErrorReport.htm")).andExpect(status().isOk());
    }

    @Test
    void getInvoiceReport() throws Exception {
        mvc.perform(post("/reports/getInvoiceReport.htm")).andExpect(status().isOk());
    }
}