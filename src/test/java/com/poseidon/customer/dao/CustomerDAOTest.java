package com.poseidon.customer.dao;

import com.poseidon.customer.dao.entities.Customer;
import com.poseidon.customer.dao.entities.CustomerAdditionalDetails;
import com.poseidon.customer.dao.repo.CustomerAdditionalDetailsRepository;
import com.poseidon.customer.dao.repo.CustomerRepository;
import com.poseidon.customer.dao.spec.CustomerSpecification;
import com.poseidon.customer.domain.CustomerVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CustomerDAOTest {
    private final CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
    private final CustomerAdditionalDetailsRepository customerAdditionalDetailsRepository =
            Mockito.mock(CustomerAdditionalDetailsRepository.class);
    private final CustomerDAO customerDAO = new CustomerDAO(
            customerRepository, customerAdditionalDetailsRepository);

    @Test
    void listAllCustomerDetailsSuccess() {
        when(customerRepository.findAll()).thenReturn(mockCustomers());
        Assertions.assertNotNull(customerDAO.listAllCustomerDetails());
    }

    @Test
    void saveCustomerSuccess() {
        when(customerRepository.save(any())).thenReturn(mockCustomer());
        CustomerVO customer = customerDAO.saveCustomer(mockCustomerVO());
        Assertions.assertEquals(1234L, customer.getCustomerId());
    }

    @Test
    void getCustomerFromIdSuccessWithAdditionalDetails() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(mockCustomer()));
        when(customerAdditionalDetailsRepository.findByCustomerId(anyLong())).thenReturn(additionalDetails());
        Assertions.assertNotNull(customerDAO.getCustomerFromId(1234L));
    }

    @Test
    void getCustomerFromIdSuccess() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(mockCustomer()));
        Assertions.assertNotNull(customerDAO.getCustomerFromId(1234L));
    }

    @Test
    void getCustomerFromIdEmptySuccess() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertTrue(customerDAO.getCustomerFromId(1234L).isEmpty());
    }

    @Test
    void deleteCustomerFromIdSuccess() {
        Assertions.assertAll(() -> customerDAO.deleteCustomerFromId(1234L));
    }

    @Test
    void deleteCustomerFromIdSuccessWithDetails() {
        when(customerAdditionalDetailsRepository.findByCustomerId(anyLong())).thenReturn(additionalDetails());
        Assertions.assertAll(() -> customerDAO.deleteCustomerFromId(1234L));
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
    void searchCustomerSuccess() {
        when(customerRepository.findAll(any(CustomerSpecification.class))).thenReturn(mockCustomers());
        var mockCustomerVO = mockCustomerVO();
        Assertions.assertNotNull(customerDAO.searchCustomer(mockCustomerVO));
        mockCustomerVO.setIncludes(Boolean.TRUE);
        Assertions.assertNotNull(customerDAO.searchCustomer(mockCustomerVO));
        mockCustomerVO.setIncludes(Boolean.FALSE);
        mockCustomerVO.setStartsWith(Boolean.TRUE);
        Assertions.assertNotNull(customerDAO.searchCustomer(mockCustomerVO));
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
        customer.setId(1234L);
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

    private Optional<CustomerAdditionalDetails> additionalDetails() {
        var additionalDetails = new CustomerAdditionalDetails();
        additionalDetails.setId(1234L);
        additionalDetails.setCustomerId(1234L);
        additionalDetails.setContactPerson("HULK");
        additionalDetails.setContactPerson("90909090");
        additionalDetails.setNote("Notes");
        return Optional.of(additionalDetails);
    }
}