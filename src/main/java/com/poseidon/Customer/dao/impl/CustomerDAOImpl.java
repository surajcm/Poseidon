package com.poseidon.Customer.dao.impl;

import com.poseidon.Customer.dao.CustomerDAO;
import com.poseidon.Customer.domain.CustomerVO;
import com.poseidon.Customer.exception.CustomerException;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.DataAccessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 10:45:56 PM
 */
public class CustomerDAOImpl extends JdbcDaoSupport implements CustomerDAO {
//logger
private final Log log = LogFactory.getLog(CustomerDAOImpl.class);
private final String GET_CUSTOMERS_SQL = " SELECT Id, Name, ifnull(Address1,'') as Address1,ifnull(address2,'') as Address2," +
        " ifnull(Phone,'') as Phone,ifnull(Mobile,'') as Mobile, " +
        " ifnull(email,'') as email, ifnull(ContactPerson1,'') as ContactPerson1," +
        " ifnull(ContactPh1,'') as ContactPh1, ifnull(ContactPerson2,'') as ContactPerson2, " +
        " ifnull(ContactPh2,'') as ContactPh2, ifnull(Note,'') as Note FROM customer ;";

    public List<CustomerVO> listAllCustomerDetails() throws CustomerException {
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = fetchAllCustomers();
        } catch (DataAccessException e) {
            throw new CustomerException(CustomerException.DATABASE_ERROR);
        }
        return customerVOs;
    }

    private List<CustomerVO> fetchAllCustomers() {
        return (List<CustomerVO>) getJdbcTemplate().query(GET_CUSTOMERS_SQL, new CustomerListRowMapper());
    }

    private class CustomerListRowMapper implements RowMapper {
        /**
         * method to map the result to vo
         *
         * @param resultSet resultSet instance
         * @param i         i instance
         * @return UserVO as Object
         * @throws java.sql.SQLException on error
         */
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            CustomerVO customerVO = new CustomerVO();
            customerVO.setCustomerId(resultSet.getLong("Id"));
            customerVO.setCustomerName(resultSet.getString("Name"));
            customerVO.setAddress1(resultSet.getString("Address1"));
            customerVO.setAddress2(resultSet.getString("Address2"));
            customerVO.setPhoneNo(resultSet.getString("Phone"));
            customerVO.setMobile(resultSet.getString("Mobile"));
            customerVO.setEmail(resultSet.getString("email"));
            customerVO.setContactPerson1(resultSet.getString("ContactPerson1"));
            customerVO.setContactMobile1(resultSet.getString("ContactPh1"));
            customerVO.setContactPerson2(resultSet.getString("ContactPerson2"));
            customerVO.setContactMobile2(resultSet.getString("ContactPh2"));
            customerVO.setNotes(resultSet.getString("Note"));
            return customerVO;
        }
    }
}
