package com.poseidon.customer.service.impl;

import com.poseidon.customer.dao.MockCustomerDAOImpl;
import com.poseidon.customer.domain.CustomerVO;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
        //customerService.setCustomerDAO(mockCustomerDAO);
    }

    @Test
    public void verifyListAllCustomerDetails() {
        //try {
            List<CustomerVO> customerVOs = customerService.listAllCustomerDetails();
            //assertEquals("Verifying customer name",customerVOs.get(0).getCustomerName(),"Tester");
        //} catch (CustomerException e) {
            //fail(e.getMessage());
        //}
    }

}