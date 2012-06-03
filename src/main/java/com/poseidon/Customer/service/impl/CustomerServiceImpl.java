package com.poseidon.Customer.service.impl;

import com.poseidon.Customer.service.CustomerService;
import com.poseidon.Customer.domain.CustomerVO;
import com.poseidon.Customer.exception.CustomerException;
import com.poseidon.Customer.dao.CustomerDAO;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:47:17 PM
 */
public class CustomerServiceImpl implements CustomerService {
    private CustomerDAO customerDAO;
    private final Log log = LogFactory.getLog(CustomerServiceImpl.class);

    public CustomerDAO getCustomerDAO() {
        return customerDAO;
    }

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<CustomerVO> listAllCustomerDetails() throws CustomerException {
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = getCustomerDAO().listAllCustomerDetails();
        }catch (CustomerException e){
            e.printStackTrace();
        }
        return customerVOs;
    }
}
