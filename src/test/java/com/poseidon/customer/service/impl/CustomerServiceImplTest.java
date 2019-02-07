package com.poseidon.customer.service.impl;

import com.poseidon.customer.dao.MockCustomerDaoImpl;
import com.poseidon.customer.domain.CustomerVO;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Suraj.
 * on 11/25/15.
 */
public class CustomerServiceImplTest {
    private CustomerServiceImpl customerService;
    private MockCustomerDaoImpl mockCustomerDao;

    /**
     * initial set up.
     */
    @Before
    public void setup() {
        mockCustomerDao = new MockCustomerDaoImpl();
        customerService = new CustomerServiceImpl();
        //customerService.setCustomerDAO(mockCustomerDao);
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