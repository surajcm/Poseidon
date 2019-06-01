package com.poseidon.customer.service.impl;

import com.poseidon.customer.dao.impl.CustomerDAOImpl;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.service.CustomerServiceConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.reflect.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Suraj.
 * on 11/25/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CustomerServiceConfiguration.class})
public class CustomerServiceImplTest {
    private CustomerServiceImpl customerService;

    @Autowired
    private CustomerDAOImpl customerDAO;

    /**
     * initial set up.
     */
    @Before
    public void setup() {
        customerService = new CustomerServiceImpl();
        Whitebox.setInternalState(customerService, "customerDAO", customerDAO);
    }

    @Test
    public void verifyListAllCustomerDetails() {

        List<CustomerVO> customerVOs = customerService.listAllCustomerDetails();
        assertEquals(0, customerVOs.size());
    }

}