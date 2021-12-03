package com.poseidon.customer.web.controller;

import com.poseidon.customer.CustomerConfigurations;
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
@WebMvcTest(CustomerController.class)
@ContextConfiguration(classes = {CustomerConfigurations.class})
class CustomerControllerTest {
    private MockMvc mvc;
    @Autowired
    private CustomerController customerController;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void list() throws Exception {
        mvc.perform(post("/customer/List.htm")).andExpect(status().isOk());
    }

    @Test
    void deleteCustomer() throws Exception {
        mvc.perform(post("/customer/deleteCust.htm")).andExpect(status().isOk());
    }

    @Test
    void saveCustomer() throws Exception {
        mvc.perform(post("/customer/saveCustomer.htm")).andExpect(status().isOk());
    }

    @Test
    void updateCustomer() throws Exception {
        mvc.perform(post("/customer/updateCustomer.htm")).andExpect(status().isOk());
    }

    @Test
    void searchCustomer() throws Exception {
        mvc.perform(post("/customer/searchCustomer.htm")).andExpect(status().isOk());
    }
}