package com.poseidon.customer.service.impl;

import com.poseidon.customer.dao.CustomerDAO;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;
import com.poseidon.customer.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerDAO customerDAO;

    public CustomerServiceImpl(final CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    /**
     * list all customer details.
     *
     * @return list of customer vo
     */
    @Override
    public List<CustomerVO> listAllCustomerDetails() {
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = customerDAO.listAllCustomerDetails();
        } catch (CustomerException ex) {
            LOG.error(ex.getMessage());
        }
        return customerVOs;
    }

    /**
     * save a customer.
     *
     * @param currentCustomerVO currentCustomerVO
     * @return view
     */
    @Override
    public long saveCustomer(final CustomerVO currentCustomerVO) {
        var customerId = 0L;
        try {
            customerId = customerDAO.saveCustomer(currentCustomerVO);
        } catch (CustomerException ex) {
            LOG.error(ex.getMessage());
        }
        return customerId;
    }

    /**
     * get customer from id.
     *
     * @param id of customer
     * @return customer vo
     */
    @Override
    public CustomerVO getCustomerFromId(final Long id) {
        CustomerVO customerVO = null;
        try {
            customerVO = customerDAO.getCustomerFromId(id);
        } catch (CustomerException ex) {
            LOG.error(ex.getMessage());
        }
        return customerVO;
    }

    /**
     * delete a customer with id.
     *
     * @param id of customer to be deleted
     */
    @Override
    public void deleteCustomerFromId(final Long id) {
        try {
            customerDAO.deleteCustomerFromId(id);
        } catch (CustomerException ex) {
            LOG.error(ex.getMessage());
        }
    }

    /**
     * update customer.
     *
     * @param currentCustomerVO currentCustomerVO
     */
    @Override
    public void updateCustomer(final CustomerVO currentCustomerVO) {
        try {
            customerDAO.updateCustomer(currentCustomerVO);
        } catch (CustomerException ex) {
            LOG.error(ex.getMessage());
        }
    }

    /**
     * search customer.
     *
     * @param searchCustomerVO searchCustomerVO
     * @return list of customer vo
     */
    @Override
    public List<CustomerVO> searchCustomer(final CustomerVO searchCustomerVO) {
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = customerDAO.searchCustomer(searchCustomerVO);
        } catch (CustomerException ex) {
            LOG.error(ex.getMessage());
        }
        return customerVOs;
    }
}
