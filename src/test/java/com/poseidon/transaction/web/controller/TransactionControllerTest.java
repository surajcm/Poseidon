package com.poseidon.transaction.web.controller;

import com.poseidon.transaction.TransactionConfigurations;
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
@WebMvcTest(TransactionController.class)
@ContextConfiguration(classes = {TransactionConfigurations.class})
class TransactionControllerTest {
    private MockMvc mvc;
    @Autowired
    private TransactionController transactionController;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void listAll() throws Exception {
        mvc.perform(post("/txs/listTransactions")).andExpect(status().isOk());
    }

    @Test
    void addTxn() throws Exception {
        mvc.perform(post("/txs/addTxn")).andExpect(status().isOk());
    }

    @Test
    void saveTxn() throws Exception {
        mvc.perform(post("/txs/saveTxn")).andExpect(status().isOk());
    }

    @Test
    void searchTxn() throws Exception {
        mvc.perform(post("/txs/searchTxn")).andExpect(status().isOk());
    }

    @Test
    void editTxn() throws Exception {
        mvc.perform(post("/txs/editTxn")).andExpect(status().isOk());
    }

    @Test
    void updateTxn() throws Exception {
        mvc.perform(post("/txs/updateTxn")).andExpect(status().isOk());
    }

    @Test
    void deleteTxn() throws Exception {
        mvc.perform(post("/txs/deleteTxn")).andExpect(status().isOk());
    }
}