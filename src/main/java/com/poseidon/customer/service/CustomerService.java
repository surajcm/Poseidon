package com.poseidon.customer.service;

import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerVO> listAllCustomerDetails() throws CustomerException;

    CustomerVO saveCustomer(CustomerVO currentCustomerVO) throws CustomerException;

    Optional<CustomerVO> getCustomerFromId(Long id);

    void deleteCustomerFromId(Long id)throws CustomerException;

    void updateCustomer(CustomerVO currentCustomerVO)throws CustomerException;

    List<CustomerVO> searchCustomer(CustomerVO searchCustomerVO)throws CustomerException;
}
