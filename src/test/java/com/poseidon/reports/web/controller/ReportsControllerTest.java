package com.poseidon.reports.web.controller;

import com.poseidon.reports.ReportsConfigurations;
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
@WebMvcTest(ReportsController.class)
@ContextConfiguration(classes = {ReportsConfigurations.class})
public class ReportsControllerTest {
    private MockMvc mvc;
    @Autowired
    private ReportsController reportsController;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(reportsController).build();
    }

    @Test
    public void list() throws Exception {
        mvc.perform(post("/reports/List.htm")).andExpect(status().isOk());
    }

    @Test
    public void getMakeDetailsReport() throws Exception {
        mvc.perform(post("/reports/getMakeDetailsReport.htm")).andExpect(status().isOk());
    }

    @Test
    public void getCallReport() throws Exception {
        mvc.perform(post("/reports/getCallReport.htm")).andExpect(status().isOk());
    }

    @Test
    public void getTransactionsListReport() throws Exception {
        mvc.perform(post("/reports/getTransactionsListReport.htm")).andExpect(status().isOk());
    }

    @Test
    public void getModelListReport() throws Exception {
        mvc.perform(post("/reports/getModelListReport.htm")).andExpect(status().isOk());
    }

    @Test
    public void getErrorReport() throws Exception {
        mvc.perform(post("/reports/getErrorReport.htm")).andExpect(status().isOk());
    }

    @Test
    public void getInvoiceReport() throws Exception {
        mvc.perform(post("/reports/getInvoiceReport.htm")).andExpect(status().isOk());
    }

}