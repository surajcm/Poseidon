package com.poseidon.customer.service.impl;

import com.poseidon.customer.service.CustomerService;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;
import com.poseidon.customer.dao.CustomerDAO;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 10:47:17 PM
 */
public class CustomerServiceImpl implements CustomerService {
    private CustomerDAO customerDAO;
    private final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);

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
            LOG.error(e.getMessage());
        }
        return customerVOs;
    }

    public long saveCustomer(CustomerVO currentCustomerVO) throws CustomerException {
        long customerId = 0L;
        try {
            customerId = getCustomerDAO().saveCustomer(currentCustomerVO);
        }catch (CustomerException e){
            LOG.error(e.getMessage());
        }
        return customerId;
    }

    public CustomerVO getCustomerFromId(Long id) throws CustomerException {
        CustomerVO customerVO= null;
        try {
            customerVO = getCustomerDAO().getCustomerFromId(id);
        }catch (CustomerException e){
            LOG.error(e.getMessage());
        }
        return customerVO;
    }

    public void deleteCustomerFromId(Long id) throws CustomerException {
        try {
            getCustomerDAO().deleteCustomerFromId(id);
        }catch (CustomerException e){
            LOG.error(e.getMessage());
        }
    }

    public void updateCustomer(CustomerVO currentCustomerVO) throws CustomerException {
        try {
            getCustomerDAO().updateCustomer(currentCustomerVO);
        }catch (CustomerException e){
            LOG.error(e.getMessage());
        }
    }

    public List<CustomerVO> searchCustomer(CustomerVO searchCustomerVO) throws CustomerException {
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = getCustomerDAO().searchCustomer(searchCustomerVO);
        }catch (CustomerException e){
            LOG.error(e.getMessage());
        }
        return customerVOs;
    }
}
