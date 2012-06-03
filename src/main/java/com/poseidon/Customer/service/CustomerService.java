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
}
