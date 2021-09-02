package com.poseidon.customer.service.impl;

import com.poseidon.customer.dao.CustomerDAO;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;
import com.poseidon.customer.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return customerDAO.listAllCustomerDetails();
    }

    /**
     * save a customer.
     *
     * @param currentCustomerVO currentCustomerVO
     */
    @Override
    public CustomerVO saveCustomer(final CustomerVO currentCustomerVO) {
        return customerDAO.saveCustomer(currentCustomerVO);
    }

    /**
     * get customer from id.
     *
     * @param id of customer
     * @return customer vo
     */
    @Override
    public Optional<CustomerVO> getCustomerFromId(final Long id) {
        return customerDAO.getCustomerFromId(id);
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
