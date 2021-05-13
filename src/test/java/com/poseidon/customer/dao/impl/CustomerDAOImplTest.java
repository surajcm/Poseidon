package com.poseidon.customer.dao.impl;

import com.poseidon.customer.dao.entities.Customer;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CustomerDAOImplTest {
    private final CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    private final CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
    private final CustomerAdditionalDetailsRepository customerAdditionalDetailsRepository =
            Mockito.mock(CustomerAdditionalDetailsRepository.class);

    @BeforeEach
    public void setup() {
        Whitebox.setInternalState(customerDAO, "customerRepository", customerRepository);
        Whitebox.setInternalState(customerDAO,
                "customerAdditionalDetailsRepository", customerAdditionalDetailsRepository);
    }

    @Test
    void listAllCustomerDetailsSuccess() throws CustomerException {
        when(customerRepository.findAll()).thenReturn(mockCustomers());
        Assertions.assertNotNull(customerDAO.listAllCustomerDetails());
    }

    @Test
    void listAllCustomerDetailsFailure() {
        when(customerRepository.findAll()).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(CustomerException.class, customerDAO::listAllCustomerDetails);
    }

    @Test
    void saveCustomerSuccess() throws CustomerException {
        when(customerRepository.save(any())).thenReturn(mockCustomer());
        Long id = customerDAO.saveCustomer(mockCustomerVO());
        Assertions.assertEquals(1234L, id);
    }

    @Test
    void saveCustomerFailure() {
        when(customerRepository.save(any())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(CustomerException.class, () -> customerDAO.saveCustomer(mockCustomerVO()));
    }

    @Test
    void getCustomerFromIdSuccess() throws CustomerException {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(mockCustomer()));
        Assertions.assertNotNull(customerDAO.getCustomerFromId(1234L));
    }

    @Test
    void getCustomerFromIdEmptySuccess() throws CustomerException {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertNull(customerDAO.getCustomerFromId(1234L));
    }

    @Test
    void getCustomerFromIdFailure() {
        when(customerRepository.findById(anyLong())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(CustomerException.class, () -> customerDAO.getCustomerFromId(1234L));
    }

    @Test
    void deleteCustomerFromIdSuccess() {
        Assertions.assertAll(() -> customerDAO.deleteCustomerFromId(1234L));
    }

    @Test
    void deleteCustomerFromIdFailure() {
        doThrow(new CannotAcquireLockException("DB error")).when(customerRepository).deleteById(anyLong());
        Assertions.assertThrows(CustomerException.class, () -> customerDAO.deleteCustomerFromId(1234L));
    }

    @Test
    void updateCustomerSuccess() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(mockCustomer()));
        Assertions.assertAll(() -> customerDAO.updateCustomer(mockCustomerVO()));
    }

    @Test
    void updateCustomerEmptySuccess() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertAll(() -> customerDAO.updateCustomer(mockCustomerVO()));
    }

    @Test
    void updateCustomerFailure() {
        when(customerRepository.findById(anyLong())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(CustomerException.class, () -> customerDAO.updateCustomer(mockCustomerVO()));
    }

    @Test
    void searchCustomerSuccess() throws CustomerException {
        when(customerRepository.findAll(any())).thenReturn(mockCustomers());
        var mockCustomerVO = mockCustomerVO();
        Assertions.assertNotNull(customerDAO.searchCustomer(mockCustomerVO));
        mockCustomerVO.setIncludes(Boolean.TRUE);
        Assertions.assertNotNull(customerDAO.searchCustomer(mockCustomerVO));
        mockCustomerVO.setIncludes(Boolean.FALSE);
        mockCustomerVO.setStartsWith(Boolean.TRUE);
        Assertions.assertNotNull(customerDAO.searchCustomer(mockCustomerVO));
    }

    @Test
    void searchCustomerFailure() {
        when(customerRepository.findAll(any())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(CustomerException.class, () -> customerDAO.searchCustomer(mockCustomerVO()));
    }

    private CustomerVO mockCustomerVO() {
        var customerVO = new CustomerVO();
        customerVO.setCustomerId(1234L);
        customerVO.setCustomerName("ABC");
        customerVO.setNotes("ABC");
        customerVO.setAddress("ABC");
        customerVOContacts(customerVO);
        customerVO.setNotes("ABC");
        customerVO.setCreatedBy("ABC");
        customerVO.setModifiedBy("ABC");
        return customerVO;
    }

    private void customerVOContacts(final CustomerVO customerVO) {
        customerVO.setPhoneNo("ABC");
        customerVO.setMobile("ABC");
        customerVO.setEmail("ABC");
        customerVO.setContactPerson("ABC");
        customerVO.setContactMobile("ABC");
    }

    private List<Customer> mockCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(mockCustomer());
        return customers;
    }

    private Customer mockCustomer() {
        var customer = new Customer();
        customer.setCustomerId(1234L);
        customer.setName("ABC");
        customer.setAddress("ABC");
        setCustomerContacts(customer);
        customer.setCreatedBy("ABC");
        customer.setModifiedBy("ABC");
        return customer;
    }

    private void setCustomerContacts(final Customer customer) {
        customer.setPhone("ABC");
        customer.setMobile("ABC");
        customer.setEmail("ABC");
    }
}