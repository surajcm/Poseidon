package com.poseidon.Customer.delegate;

import com.poseidon.Customer.domain.CustomerVO;
import com.poseidon.Customer.service.CustomerService;
import com.poseidon.Customer.exception.CustomerException;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:46:10 PM
 */
public class CustomerDelegate {
    private CustomerService customerService;

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<CustomerVO> listAllCustomerDetails() throws CustomerException {
        return getCustomerService().listAllCustomerDetails();
    }

    public void saveCustomer(CustomerVO currentCustomerVO) throws CustomerException {
        getCustomerService().saveCustomer(currentCustomerVO);
    }

    public CustomerVO getCustomerFromId(Long id) throws CustomerException {
        return getCustomerService().getCustomerFromId(id);
    }

    public void deleteCustomerFromId(Long id)  throws CustomerException {
        getCustomerService().deleteCustomerFromId(id);
    }

    public void updateCustomer(CustomerVO currentCustomerVO) throws CustomerException {
        getCustomerService().updateCustomer(currentCustomerVO);
    }
}
