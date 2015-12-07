package com.poseidon.Customer.dao;

import com.poseidon.Customer.domain.CustomerVO;
import com.poseidon.Customer.exception.CustomerException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smuraleedharan
 * on 12/7/15.
 */
public class MockCustomerDAOImpl implements CustomerDAO {
    @Override
    public List<CustomerVO> listAllCustomerDetails() throws CustomerException {
        List<CustomerVO> customerVOs = new ArrayList<CustomerVO>();
        CustomerVO customerVO = new CustomerVO();
        customerVO.setCustomerName("Tester");
        return customerVOs;
    }

    @Override
    public long saveCustomer(CustomerVO currentCustomerVO) throws CustomerException {
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
    public void updateCustomer(CustomerVO currentCustomerVO) throws CustomerException {

    }

    @Override
    public List<CustomerVO> searchCustomer(CustomerVO searchCustomerVO) throws CustomerException {
        return null;
    }
}
