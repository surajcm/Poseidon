package com.poseidon.customer.service.impl;

import com.poseidon.customer.dao.impl.CustomerDAOImpl;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;
import com.poseidon.customer.service.CustomerServiceConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

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
class CustomerServiceImplTest {
    private CustomerServiceImpl customerService;

    @Autowired
    private CustomerDAOImpl customerDAO;

    /**
     * initial set up.
     */
    @BeforeEach
    public void setup() {
        customerService = new CustomerServiceImpl();
        Whitebox.setInternalState(customerService, "customerDAO", customerDAO);
    }

    @Test
    void verifyListAllCustomerDetailsSuccess() {
        List<CustomerVO> customerVOs = customerService.listAllCustomerDetails();
        Assertions.assertEquals(0, customerVOs.size());
    }

    @Test
    void verifyListAllCustomerDetailsFailure() throws CustomerException {
        when(customerDAO.listAllCustomerDetails()).thenThrow(new CustomerException(CustomerException.DATABASE_ERROR));
        Assertions.assertNull(customerService.listAllCustomerDetails());
    }

    @Test
    void saveCustomerSuccess() {
        Assertions.assertEquals(0, customerService.saveCustomer(Mockito.mock(CustomerVO.class)));
    }

    @Test
    void saveCustomerFailure() throws CustomerException {
        when(customerDAO.saveCustomer(any())).thenThrow(new CustomerException(CustomerException.DATABASE_ERROR));
        Assertions.assertEquals(0, customerService.saveCustomer(Mockito.mock(CustomerVO.class)));
    }

    @Test
    void getCustomerFromIdSuccess() throws CustomerException {
        when(customerDAO.getCustomerFromId(anyLong())).thenReturn(new CustomerVO());
        Assertions.assertNotNull(customerService.getCustomerFromId(1234L));
    }

    @Test
    void getCustomerFromIdFailure() throws CustomerException {
        when(customerDAO.getCustomerFromId(anyLong()))
                .thenThrow(new CustomerException(CustomerException.DATABASE_ERROR));
        Assertions.assertNull(customerService.getCustomerFromId(1234L));
    }

    @Test
    void deleteCustomerFromIdSuccess() {
        Assertions.assertAll(() -> customerService.deleteCustomerFromId(1234L));
    }

    @Test
    void deleteCustomerFromIdFailure() throws CustomerException {
        doThrow(new CustomerException(CustomerException.DATABASE_ERROR))
                .when(customerDAO).deleteCustomerFromId(anyLong());
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
    void searchCustomerSuccess() throws CustomerException {
        when(customerDAO.searchCustomer(Mockito.mock(CustomerVO.class))).thenReturn(new ArrayList<>());
        Assertions.assertNotNull(customerService.searchCustomer(Mockito.mock(CustomerVO.class)));
    }

    @Test
    void searchCustomerFailure() throws CustomerException {
        doThrow(new CustomerException(CustomerException.DATABASE_ERROR))
                .when(customerDAO).searchCustomer(any());
        Assertions.assertNull(customerService.searchCustomer(Mockito.mock(CustomerVO.class)));
    }

}