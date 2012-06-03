package com.poseidon.Customer.dao.impl;

import com.poseidon.Customer.dao.CustomerDAO;
import com.poseidon.Customer.domain.CustomerVO;
import com.poseidon.Customer.exception.CustomerException;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:45:56 PM
 */
public class CustomerDAOImpl extends JdbcDaoSupport implements CustomerDAO {
    public List<CustomerVO> listAllCustomerDetails() throws CustomerException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
