package com.poseidon.customer.service.impl;

import com.poseidon.customer.dao.CustomerDAO;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;
import com.poseidon.customer.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 10:47:17 PM
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerDAO customerDAO;

    public List<CustomerVO> listAllCustomerDetails() {
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = customerDAO.listAllCustomerDetails();
        }catch (CustomerException e){
            LOG.error(e.getMessage());
        }
        return customerVOs;
    }

    public long saveCustomer(CustomerVO currentCustomerVO) {
        long customerId = 0L;
        try {
            customerId = customerDAO.saveCustomer(currentCustomerVO);
        }catch (CustomerException e){
            LOG.error(e.getMessage());
        }
        return customerId;
    }

    public CustomerVO getCustomerFromId(Long id) {
        CustomerVO customerVO= null;
        try {
            customerVO = customerDAO.getCustomerFromId(id);
        }catch (CustomerException e){
            LOG.error(e.getMessage());
        }
        return customerVO;
    }

    public void deleteCustomerFromId(Long id) {
        try {
            customerDAO.deleteCustomerFromId(id);
        }catch (CustomerException e){
            LOG.error(e.getMessage());
        }
    }

    public void updateCustomer(CustomerVO currentCustomerVO) {
        try {
            customerDAO.updateCustomer(currentCustomerVO);
        }catch (CustomerException e){
            LOG.error(e.getMessage());
        }
    }

    public List<CustomerVO> searchCustomer(CustomerVO searchCustomerVO) {
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = customerDAO.searchCustomer(searchCustomerVO);
        }catch (CustomerException e){
            LOG.error(e.getMessage());
        }
        return customerVOs;
    }
}
