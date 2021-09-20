package com.poseidon.customer.service;

import com.poseidon.customer.dao.CustomerDAO;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerDAO customerDAO;

    public CustomerService(final CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    /**
     * list all customer details.
     *
     * @return list of customer vo
     */
    public List<CustomerVO> listAllCustomerDetails() {
        return customerDAO.listAllCustomerDetails();
    }

    /**
     * save a customer.
     *
     * @param currentCustomerVO currentCustomerVO
     */
    public CustomerVO saveCustomer(final CustomerVO currentCustomerVO) {
        return customerDAO.saveCustomer(currentCustomerVO);
    }

    /**
     * get customer from id.
     *
     * @param id of customer
     * @return customer vo
     */
    public Optional<CustomerVO> getCustomerFromId(final Long id) {
        return customerDAO.getCustomerFromId(id);
    }

    /**
     * delete a customer with id.
     *
     * @param id of customer to be deleted
     */
    public void deleteCustomerFromId(final Long id) {
        customerDAO.deleteCustomerFromId(id);
    }

    /**
     * update customer.
     *
     * @param currentCustomerVO currentCustomerVO
     */
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
    public List<CustomerVO> searchCustomer(final CustomerVO searchCustomerVO) {
        return customerDAO.searchCustomer(searchCustomerVO);
    }
}
