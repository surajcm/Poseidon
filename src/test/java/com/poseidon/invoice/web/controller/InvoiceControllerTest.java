package com.poseidon.invoice.web.controller;

import com.poseidon.invoice.InvoiceConfigurations;
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
@WebMvcTest(InvoiceController.class)
@ContextConfiguration(classes = {InvoiceConfigurations.class})
class InvoiceControllerTest {
    private MockMvc mvc;
    @Autowired
    private InvoiceController invoiceController;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(invoiceController).build();
    }

    @Test
    void listInvoice() throws Exception {
        mvc.perform(post("/invoice/ListInvoice.htm")).andExpect(status().isOk());
    }

    @Test
    void saveInvoice() throws Exception {
        mvc.perform(post("/invoice/saveInvoice.htm")).andExpect(status().isOk());
    }

    @Test
    void editInvoice() throws Exception {
        mvc.perform(post("/invoice/EditInvoice.htm")).andExpect(status().isOk());
    }

    @Test
    void deleteInvoice() throws Exception {
        mvc.perform(post("/invoice/DeleteInvoice.htm")).andExpect(status().isOk());
    }

    @Test
    void searchInvoice() throws Exception {
        mvc.perform(post("/invoice/SearchInvoice.htm")).andExpect(status().isOk());
    }

    @Test
    void updateInvoice() throws Exception {
        mvc.perform(post("/invoice/updateInvoice.htm")).andExpect(status().isOk());
    }

    @Test
    void invoiceTxn() throws Exception {
        mvc.perform(post("/invoice/InvoiceTxn.htm")).andExpect(status().isOk());
    }
}