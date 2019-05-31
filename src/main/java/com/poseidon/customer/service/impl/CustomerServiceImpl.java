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

    /**
     * list all customer details
     *
     * @return list of customer vo
     */
    public List<CustomerVO> listAllCustomerDetails() {
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = customerDAO.listAllCustomerDetails();
        } catch (CustomerException e) {
            LOG.error(e.getMessage());
        }
        return customerVOs;
    }

    /**
     * save a customer
     *
     * @param currentCustomerVO currentCustomerVO
     * @return view
     */
    public long saveCustomer(CustomerVO currentCustomerVO) {
        long customerId = 0L;
        try {
            customerId = customerDAO.saveCustomer(currentCustomerVO);
        } catch (CustomerException e) {
            LOG.error(e.getMessage());
        }
        return customerId;
    }

    /**
     * get customer from id
     *
     * @param id of customer
     * @return customer vo
     */
    public CustomerVO getCustomerFromId(Long id) {
        CustomerVO customerVO = null;
        try {
            customerVO = customerDAO.getCustomerFromId(id);
        } catch (CustomerException e) {
            LOG.error(e.getMessage());
        }
        return customerVO;
    }

    /**
     * delete customer with id
     *
     * @param id of customer to be deleted
     */
    public void deleteCustomerFromId(Long id) {
        try {
            customerDAO.deleteCustomerFromId(id);
        } catch (CustomerException e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * update customer
     *
     * @param currentCustomerVO currentCustomerVO
     */
    public void updateCustomer(CustomerVO currentCustomerVO) {
        try {
            customerDAO.updateCustomer(currentCustomerVO);
        } catch (CustomerException e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * search customer
     *
     * @param searchCustomerVO searchCustomerVO
     * @return list of customer vo
     */
    public List<CustomerVO> searchCustomer(CustomerVO searchCustomerVO) {
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = customerDAO.searchCustomer(searchCustomerVO);
        } catch (CustomerException e) {
            LOG.error(e.getMessage());
        }
        return customerVOs;
    }
}
