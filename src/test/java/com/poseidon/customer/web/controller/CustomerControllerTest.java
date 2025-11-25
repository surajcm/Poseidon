package com.poseidon.customer.web.controller;

import com.poseidon.customer.CustomerConfigurations;
import com.poseidon.customer.dao.entities.Customer;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @Test
    void saveCustomer2() throws Exception {
        Customer savedCustomer = new Customer();
        savedCustomer.setName("Test Customer");

        when(customerService.saveCustomer(any(CustomerVO.class), any(Customer.class)))
                .thenReturn(savedCustomer);

        mvc.perform(post("/customer/saveCustomer2")
                        .param("modalCustomerName", "Test Customer")
                        .param("modalAddress", "Test Address")
                        .param("modalPhone", "1234567890")
                        .param("modalMobile", "9876543210")
                        .param("modalEmail", "test@test.com")
                        .param("modalContact", "Contact Person")
                        .param("modalContactMobile", "1231231234")
                        .param("modalNotes", "Test Notes"))
                .andExpect(status().isOk());
    }

    @Test
    void updateCustomer() throws Exception {
        CustomerVO existingCustomer = new CustomerVO();
        existingCustomer.setCustomerId(1L);
        existingCustomer.setCustomerName("Old Name");

        when(customerService.getCustomerFromId(1L)).thenReturn(Optional.of(existingCustomer));
        when(customerService.listAllCustomerDetails()).thenReturn(List.of(new Customer()));

        mvc.perform(put("/customer/updateCustomer")
                        .param("id", "1")
                        .param("modalCustomerName", "Updated Customer")
                        .param("modalAddress", "Updated Address")
                        .param("modalPhone", "1234567890")
                        .param("modalMobile", "9876543210")
                        .param("modalEmail", "updated@test.com")
                        .param("modalContact", "Updated Contact")
                        .param("modalContactMobile", "1231231234")
                        .param("modalNotes", "Updated Notes"))
                .andExpect(status().isOk());
    }

}
