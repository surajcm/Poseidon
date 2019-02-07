package com.poseidon.customer.dao;

import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.exception.CustomerException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smuraleedharan
 * on 12/7/15.
 */
public class MockCustomerDaoImpl implements CustomerDAO {
    @Override
    public List<CustomerVO> listAllCustomerDetails() throws CustomerException {
        List<CustomerVO> customerVOs = new ArrayList<>();
        CustomerVO customerVo = new CustomerVO();
        customerVo.setCustomerName("Tester");
        return customerVOs;
    }

    @Override
    public long saveCustomer(CustomerVO currentCustomerVo) throws CustomerException {
        return 0;
    }

    @Override
    public CustomerVO getCustomerFromId(Long id) throws CustomerException {
        return null;
    }

    @Override
    public void deleteCustomerFromId(Long id) throws CustomerException {

    }

    @Override
    public void updateCustomer(CustomerVO currentCustomerVo) throws CustomerException {

    }

    @Override
    public List<CustomerVO> searchCustomer(CustomerVO searchCustomerVo) throws CustomerException {
        return null;
    }
}
