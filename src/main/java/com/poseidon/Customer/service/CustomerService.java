package com.poseidon.Customer.service;

import com.poseidon.Customer.domain.CustomerVO;
import com.poseidon.Customer.exception.CustomerException;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:46:52 PM
 */
public interface CustomerService {
    public List<CustomerVO> listAllCustomerDetails() throws CustomerException;

    public void saveCustomer(CustomerVO currentCustomerVO) throws CustomerException;

    public CustomerVO getCustomerFromId(Long id) throws CustomerException;

    public void deleteCustomerFromId(Long id)throws CustomerException;

    public void updateCustomer(CustomerVO currentCustomerVO)throws CustomerException;

    public List<CustomerVO> searchCustomer(CustomerVO searchCustomerVO)throws CustomerException;
}
