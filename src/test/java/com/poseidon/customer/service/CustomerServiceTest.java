package com.poseidon.customer.service;

import com.poseidon.customer.dao.CustomerDAO;
import com.poseidon.customer.dao.entities.Customer;
import com.poseidon.customer.domain.CustomerAdditionalDetailsVO;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by Suraj.
 * on 11/25/15.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CustomerServiceConfiguration.class})
class CustomerServiceTest {
    private CustomerService customerService;

    @Autowired
    private CustomerDAO customerDAO;

    @BeforeEach
    public void setup() {
        customerService = new CustomerService(customerDAO);
    }

    @Test
    void verifyListAllCustomerDetailsSuccess() {
        var customerVOs = customerService.listAllCustomerDetails();
        Assertions.assertEquals(0, customerVOs.size());
    }

    @Test
    void saveCustomerSuccess() {
        when(customerDAO.saveCustomer(any(), any())).thenReturn(mockCustomer());
        var response = customerService.saveCustomer(mockCustomerVO(),
                mockCustomer());
        Assertions.assertEquals(1L, response.getId());
    }

    private Customer mockCustomer() {
        var customer = new Customer();
        customer.setId(1L);
        customer.setName("Moon Knight");
        customer.setAddress("Egypt");
        customer.setPhone("000000");
        customer.setMobile("000000");
        customer.setEmail("email@email.com");
        customer.setCreatedBy("admin");
        customer.setCreatedOn(LocalDateTime.now(ZoneId.systemDefault()));
        customer.setModifiedBy("admin");
        customer.setModifiedOn(LocalDateTime.now(ZoneId.systemDefault()));
        return customer;
    }

    private CustomerVO mockCustomerVO() {
        var customerVO = new CustomerVO();
        customerVO.setCustomerId(1L);
        customerVO.setCustomerName("Moon Knight");
        customerVO.setAddress("Egypt");
        customerVO.setPhoneNo("000000");
        customerVO.setMobile("000000");
        customerVO.setEmail("email@email.com");
        customerVO.setContactPerson("No one");
        customerVO.setContactMobile("000000");
        customerVO.setNotes("Notes");
        customerVO.setCustomerAdditionalDetailsVO(mockAdditionalDetailsVO());
        customerVO.setCreatedBy("admin");
        customerVO.setCreatedOn(OffsetDateTime.now(ZoneId.systemDefault()));
        customerVO.setModifiedBy("admin");
        customerVO.setModifiedOn(OffsetDateTime.now(ZoneId.systemDefault()));
        return customerVO;
    }

    private CustomerAdditionalDetailsVO mockAdditionalDetailsVO() {
        var additionalDetails = new CustomerAdditionalDetailsVO();
        additionalDetails.setCustomerId(1L);
        additionalDetails.setContactPerson("No One");
        additionalDetails.setContactMobile("000000");
        additionalDetails.setNotes("Notes");
        additionalDetails.setCreatedBy("admin");
        additionalDetails.setCreatedOn(OffsetDateTime.now(ZoneId.systemDefault()));
        additionalDetails.setModifiedBy("admin");
        additionalDetails.setModifiedOn(OffsetDateTime.now(ZoneId.systemDefault()));
        return additionalDetails;
    }

    @Test
    void getCustomerFromIdSuccess() {
        when(customerDAO.getCustomerFromId(anyLong())).thenReturn(Optional.of(new CustomerVO()));
        Assertions.assertNotNull(customerService.getCustomerFromId(1234L));
    }

    @Test
    void deleteCustomerFromIdSuccess() {
        Assertions.assertAll(() -> customerService.deleteCustomerFromId(1234L));
    }

    @Test
    void updateCustomerSuccess() {
        Assertions.assertAll(() -> customerService.updateCustomer(Mockito.mock(CustomerVO.class)));
    }

    @Test
    void updateCustomerFailure() throws CustomerException {
        doThrow(new CustomerException(CustomerException.DATABASE_ERROR))
                .when(customerDAO).updateCustomer(any());
        Assertions.assertAll(() -> customerService.updateCustomer(Mockito.mock(CustomerVO.class)));
    }

    @Test
    void searchCustomerSuccess() {
        when(customerDAO.searchCustomer(Mockito.mock(CustomerVO.class))).thenReturn(new ArrayList<>());
        Assertions.assertNotNull(customerService.searchCustomer(Mockito.mock(CustomerVO.class)));
    }
}