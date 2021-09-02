package com.poseidon.customer.dao;

import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by smuraleedharan
 * on 12/7/15.
 */
public class MockCustomerDaoImpl implements CustomerDAO {
    @Override
    public List<CustomerVO> listAllCustomerDetails() {
        List<CustomerVO> customerVOs = new ArrayList<>();
        var customerVo = new CustomerVO();
        customerVo.setCustomerName("Tester");
        return customerVOs;
    }

    @Override
    public CustomerVO saveCustomer(final CustomerVO currentCustomerVo) {
        return new CustomerVO();
    }

    @Override
    public Optional<CustomerVO> getCustomerFromId(final Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteCustomerFromId(final Long id) throws CustomerException {

    }

    @Override
    public void updateCustomer(final CustomerVO currentCustomerVo) throws CustomerException {

    }

    @Override
    public List<CustomerVO> searchCustomer(final CustomerVO searchCustomerVo) throws CustomerException {
        return null;
    }
}
