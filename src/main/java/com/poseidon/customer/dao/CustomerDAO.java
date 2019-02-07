package com.poseidon.customer.dao;

import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;

import java.util.List;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 10:45:35 PM
 */
public interface CustomerDAO {
    List<CustomerVO> listAllCustomerDetails() throws CustomerException;

    long saveCustomer(CustomerVO currentCustomerVo) throws CustomerException;

    CustomerVO getCustomerFromId(Long id) throws CustomerException;

    void deleteCustomerFromId(Long id) throws CustomerException;

    void updateCustomer(CustomerVO currentCustomerVo) throws CustomerException;

    List<CustomerVO> searchCustomer(CustomerVO searchCustomerVo)throws CustomerException;
}
