package com.poseidon.invoice.web.controller;

import com.poseidon.invoice.InvoiceConfigurations;
import com.poseidon.invoice.exception.InvoiceException;
import com.poseidon.invoice.service.InvoiceService;
import com.poseidon.transaction.exception.TransactionException;
import com.poseidon.transaction.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InvoiceController.class)
@ContextConfiguration(classes = {InvoiceConfigurations.class})
public class InvoiceControllerTest {
    private MockMvc mvc;
    @Autowired
    private InvoiceController invoiceController;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private TransactionService transactionService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(invoiceController).build();
    }

    @Test
    public void listInvoice() throws Exception {
        mvc.perform(post("/invoice/ListInvoice.htm")).andExpect(status().isOk());
    }

    @Test
    public void listInvoiceError() throws Exception {
        when(invoiceService.fetchInvoiceForListOfTransactions())
                .thenThrow(new InvoiceException(InvoiceException.DATABASE_ERROR));
        mvc.perform(post("/invoice/ListInvoice.htm")).andExpect(status().isOk());
    }

    @Test
    public void addInvoice() throws Exception {
        mvc.perform(post("/invoice/addInvoice.htm")).andExpect(status().isOk());
    }

    @Test
    public void saveInvoice() throws Exception {
        mvc.perform(post("/invoice/saveInvoice.htm")).andExpect(status().isOk());
    }

    @Test
    public void editInvoice() throws Exception {
        mvc.perform(post("/invoice/EditInvoice.htm")).andExpect(status().isOk());
    }

    @Test
    public void editInvoiceFailure() throws Exception {
        when(invoiceService.fetchInvoiceVOFromId(null))
                .thenThrow(new InvoiceException(InvoiceException.DATABASE_ERROR));
        mvc.perform(post("/invoice/EditInvoice.htm")).andExpect(status().isOk());
    }

    @Test
    public void deleteInvoice() throws Exception {
        mvc.perform(post("/invoice/DeleteInvoice.htm")).andExpect(status().isOk());
    }

    @Test
    public void searchInvoice() throws Exception {
        mvc.perform(post("/invoice/SearchInvoice.htm")).andExpect(status().isOk());
    }

    @Test
    public void searchInvoiceFailure() throws Exception {
        when(invoiceService.findInvoices(null))
                .thenThrow(new InvoiceException(InvoiceException.DATABASE_ERROR));
        mvc.perform(post("/invoice/SearchInvoice.htm")).andExpect(status().isOk());
    }

    @Test
    public void updateInvoice() throws Exception {
        mvc.perform(post("/invoice/updateInvoice.htm")).andExpect(status().isOk());
    }

    @Test
    public void updateInvoiceFailure() throws Exception {
        doThrow(new InvoiceException(InvoiceException.DATABASE_ERROR))
                .when(invoiceService).updateInvoice(null);
        mvc.perform(post("/invoice/updateInvoice.htm")).andExpect(status().isOk());
    }

    @Test
    public void invoiceTxn() throws Exception {
        mvc.perform(post("/invoice/InvoiceTxn.htm")).andExpect(status().isOk());
    }

    @Test
    public void invoiceTxnError() throws Exception {
        when(transactionService.fetchTransactionFromId(null))
                .thenThrow(new TransactionException(TransactionException.DATABASE_ERROR));
        mvc.perform(post("/invoice/InvoiceTxn.htm")).andExpect(status().isOk());
    }
}