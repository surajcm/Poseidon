package com.poseidon.customer.dao;

import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    List<CustomerVO> listAllCustomerDetails();

    CustomerVO saveCustomer(CustomerVO currentCustomerVo);

    Optional<CustomerVO> getCustomerFromId(Long id);

    void deleteCustomerFromId(Long id) throws CustomerException;

    void updateCustomer(CustomerVO currentCustomerVo) throws CustomerException;

    List<CustomerVO> searchCustomer(CustomerVO searchCustomerVo)throws CustomerException;
}
