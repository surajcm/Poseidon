package com.poseidon.customer.service;

import com.poseidon.customer.dao.CustomerDAO;
import com.poseidon.customer.dao.entities.Customer;
import com.poseidon.customer.dao.entities.CustomerAdditionalDetails;
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
    public List<Customer> listAllCustomerDetails() {
        return customerDAO.listAllCustomerDetails();
    }

    /**
     * save a customer.
     *
     * @param currentCustomerVO currentCustomerVO
     */
    public Customer saveCustomer(final CustomerVO currentCustomerVO,
                                   final Customer customer) {
        var newAdditionalDetails = createAdditionalDetails(currentCustomerVO);
        return customerDAO.saveCustomer(customer, newAdditionalDetails);
    }

    private CustomerAdditionalDetails createAdditionalDetails(final CustomerVO currentCustomerVO) {
        var additionalDetails = new CustomerAdditionalDetails();
        additionalDetails.setContactPerson(currentCustomerVO.getContactPerson());
        additionalDetails.setContactPhone(currentCustomerVO.getContactMobile());
        additionalDetails.setNote(currentCustomerVO.getNotes());
        additionalDetails.setCreatedBy(currentCustomerVO.getCreatedBy());
        additionalDetails.setModifiedBy(currentCustomerVO.getModifiedBy());
        return additionalDetails;
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
