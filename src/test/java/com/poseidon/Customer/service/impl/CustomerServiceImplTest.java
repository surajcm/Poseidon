package com.poseidon.Customer.service.impl;

import com.poseidon.Customer.dao.MockCustomerDAOImpl;
import com.poseidon.Customer.domain.CustomerVO;
import com.poseidon.Customer.exception.CustomerException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Suraj
 *
 * on 11/25/15.
 */
public class CustomerServiceImplTest {
    private CustomerServiceImpl customerService;
    private MockCustomerDAOImpl mockCustomerDAO;

    @Before
    public void setup(){
        mockCustomerDAO = new MockCustomerDAOImpl();
        customerService = new CustomerServiceImpl();
        customerService.setCustomerDAO(mockCustomerDAO);
    }

    @Test
    public void verifyListAllCustomerDetails() {
        try {
            List<CustomerVO> customerVOs = customerService.listAllCustomerDetails();
            assertEquals("Verifying customer name",customerVOs.get(0).getCustomerName(),"Tester");
        } catch (CustomerException e) {
            fail(e.getMessage());
        }
    }

}