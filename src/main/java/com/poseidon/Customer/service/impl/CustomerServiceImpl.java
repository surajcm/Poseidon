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

    public long saveCustomer(CustomerVO currentCustomerVO) throws CustomerException {
        long customerId = 0L;
        try {
            customerId = getCustomerDAO().saveCustomer(currentCustomerVO);
        }catch (CustomerException e){
            e.printStackTrace();
        }
        return customerId;
    }

    public CustomerVO getCustomerFromId(Long id) throws CustomerException {
        CustomerVO customerVO= null;
        try {
            customerVO = getCustomerDAO().getCustomerFromId(id);
        }catch (CustomerException e){
            e.printStackTrace();
        }
        return customerVO;
    }

    public void deleteCustomerFromId(Long id) throws CustomerException {
        try {
            getCustomerDAO().deleteCustomerFromId(id);
        }catch (CustomerException e){
            e.printStackTrace();
        }
    }

    public void updateCustomer(CustomerVO currentCustomerVO) throws CustomerException {
        try {
            getCustomerDAO().updateCustomer(currentCustomerVO);
        }catch (CustomerException e){
            e.printStackTrace();
        }
    }

    public List<CustomerVO> searchCustomer(CustomerVO searchCustomerVO) throws CustomerException {
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = getCustomerDAO().searchCustomer(searchCustomerVO);
        }catch (CustomerException e){
            e.printStackTrace();
        }
        return customerVOs;
    }
}
