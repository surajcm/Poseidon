package com.poseidon.customer.service;

import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;

import java.util.List;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 10:46:52 PM
 */
public interface CustomerService {
    List<CustomerVO> listAllCustomerDetails() throws CustomerException;

    long saveCustomer(CustomerVO currentCustomerVO) throws CustomerException;

    CustomerVO getCustomerFromId(Long id) throws CustomerException;

    void deleteCustomerFromId(Long id)throws CustomerException;

    void updateCustomer(CustomerVO currentCustomerVO)throws CustomerException;

    List<CustomerVO> searchCustomer(CustomerVO searchCustomerVO)throws CustomerException;
}
