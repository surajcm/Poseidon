package com.poseidon.customer.service.impl;

import com.poseidon.customer.dao.impl.CustomerDAOImpl;
import com.poseidon.customer.domain.CustomerVO;
import com.poseidon.customer.service.CustomerServiceConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.powermock.reflect.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * Created by Suraj.
 * on 11/25/15.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CustomerServiceConfiguration.class})
public class CustomerServiceImplTest {
    private CustomerServiceImpl customerService;

    @Autowired
    private CustomerDAOImpl customerDAO;

    /**
     * initial set up.
     */
    @BeforeEach
    public void setup() {
        customerService = new CustomerServiceImpl();
        Whitebox.setInternalState(customerService, "customerDAO", customerDAO);
    }

    @Test
    public void verifyListAllCustomerDetails() {
        List<CustomerVO> customerVOs = customerService.listAllCustomerDetails();
        Assertions.assertEquals(0, customerVOs.size());
    }

}