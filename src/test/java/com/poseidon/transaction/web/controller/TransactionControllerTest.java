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
        mvc.perform(post("/txs/List.htm")).andExpect(status().isOk());
    }

    @Test
    void addTxn() throws Exception {
        mvc.perform(post("/txs/AddTxn.htm")).andExpect(status().isOk());
    }

    @Test
    void saveTxn() throws Exception {
        mvc.perform(post("/txs/SaveTxn.htm")).andExpect(status().isOk());
    }

    @Test
    void searchTxn() throws Exception {
        mvc.perform(post("/txs/SearchTxn.htm")).andExpect(status().isOk());
    }

    @Test
    void editTxn() throws Exception {
        mvc.perform(post("/txs/EditTxn.htm")).andExpect(status().isOk());
    }

    @Test
    void updateTxn() throws Exception {
        mvc.perform(post("/txs/updateTxn.htm")).andExpect(status().isOk());
    }

    @Test
    void deleteTxn() throws Exception {
        mvc.perform(post("/txs/DeleteTxn.htm")).andExpect(status().isOk());
    }
}