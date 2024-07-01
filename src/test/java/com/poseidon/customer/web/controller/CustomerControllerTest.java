package com.poseidon.customer.web.controller;

import com.poseidon.customer.CustomerConfigurations;
import com.poseidon.customer.dao.entities.Customer;
import com.poseidon.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
@ContextConfiguration(classes = {CustomerConfigurations.class})
class CustomerControllerTest {
    private MockMvc mvc;
    @Autowired
    private CustomerController customerController;
    @Autowired
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void list() throws Exception {
        when(customerService.listAll(anyInt())).thenReturn(mockCustomers());
        mvc.perform(post("/customer/List")).andExpect(status().isOk());
    }

    @Test
    void deleteCustomer() throws Exception {
        when(customerService.listAll(anyInt())).thenReturn(mockCustomers());
        mvc.perform(post("/customer/deleteCustomer")).andExpect(status().isOk());
    }

    @Test
    void searchCustomer() throws Exception {
        mvc.perform(post("/customer/searchCustomer")).andExpect(status().isOk());
    }

    private Page<Customer> mockCustomers() {
        return new PageImpl<>(List.of(new Customer(), new Customer()));
    }
}