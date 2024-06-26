package com.poseidon.reports.web.controller;

import com.poseidon.make.service.MakeService;
import com.poseidon.reports.ReportsConfigurations;
import com.poseidon.reports.service.ReportsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
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
        mvc.perform(post("/reports/list")).andExpect(status().isOk());
        when(reportsService.generateDailyReport()).thenThrow(new RuntimeException());
        when(makeService.fetchAllMakes()).thenThrow(new RuntimeException());
        mvc.perform(post("/reports/list")).andExpect(status().isOk());
    }

    @Test
    void makeDetailsReport() throws Exception {
        mvc.perform(post("/reports/makeDetailsReport")).andExpect(status().isOk());
    }

    @Test
    void callReport() throws Exception {
        mvc.perform(post("/reports/callReport")).andExpect(status().isOk());
    }

    @Test
    void transactionsListReport() throws Exception {
        mvc.perform(post("/reports/transactionsListReport")).andExpect(status().isOk());
    }

    @Test
    void modelListReport() throws Exception {
        mvc.perform(post("/reports/modelListReport")).andExpect(status().isOk());
    }

    @Test
    void errorReport() throws Exception {
        mvc.perform(post("/reports/errorReport")).andExpect(status().isOk());
    }

    @Test
    @Disabled
    @DisplayName("Should generate invoice report")
    //@WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    //@WithUserDetails("manager@manager.com")
    void invoiceReport() throws Exception {
        mvc.perform(post("/reports/invoiceReport"))
                .andExpect(status().isOk());
    }
}