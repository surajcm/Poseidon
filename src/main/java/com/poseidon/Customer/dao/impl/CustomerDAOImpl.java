package com.poseidon.Customer.dao.impl;

import com.poseidon.Customer.dao.CustomerDAO;
import com.poseidon.Customer.domain.CustomerVO;
import com.poseidon.Customer.exception.CustomerException;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
    private SimpleJdbcInsert insertCustomer;
    //logger
    private final Log log = LogFactory.getLog(CustomerDAOImpl.class);
    private final String GET_CUSTOMERS_SQL = " SELECT Id, Name, ifnull(Address1,'') as Address1,ifnull(address2,'') as Address2," +
            " ifnull(Phone,'') as Phone,ifnull(Mobile,'') as Mobile, " +
            " ifnull(email,'') as email, ifnull(ContactPerson1,'') as ContactPerson1," +
            " ifnull(ContactPh1,'') as ContactPh1, ifnull(ContactPerson2,'') as ContactPerson2, " +
            " ifnull(ContactPh2,'') as ContactPh2, ifnull(Note,'') as Note FROM customer order by modifiedOn;";

    private final String GET_SINGLE_CUSTOMER_SQL = " select * from customer where id = ? ";
    private final String DELETE_CUSTOMER_BY_ID_SQL = " delete from customer where id = ? ";
    private final String UPDATE_CUSTOMER_SQL = " update customer set Name = ? , Address1 = ? , address2 = ? , Phone = ?," +
            " Mobile = ? , email = ?, ContactPerson1 = ?, ContactPh1 = ?, ContactPerson2 = ?, ContactPh2 = ?," +
            " Note = ?, modifiedOn = ?, modifiedBy = ?  where id = ? ";

    public List<CustomerVO> listAllCustomerDetails() throws CustomerException {
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = fetchAllCustomers();
        } catch (DataAccessException e) {
            throw new CustomerException(CustomerException.DATABASE_ERROR);
        }
        return customerVOs;
    }

    public long saveCustomer(CustomerVO currentCustomerVO) throws CustomerException {

        try {
            return saveCustomerDetails(currentCustomerVO);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new CustomerException(CustomerException.DATABASE_ERROR);
        }
    }

    public CustomerVO getCustomerFromId(Long id) throws CustomerException {
        CustomerVO customerVO;
        try {
            customerVO = fetchCustomerFromId(id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new CustomerException(CustomerException.DATABASE_ERROR);
        }
        return customerVO;
    }

    public void deleteCustomerFromId(Long id) throws CustomerException {
        try {
            Object[] parameters = new Object[]{id};
            getJdbcTemplate().update(DELETE_CUSTOMER_BY_ID_SQL, parameters);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new CustomerException(CustomerException.DATABASE_ERROR);
        }
    }

    public void updateCustomer(CustomerVO currentCustomerVO) throws CustomerException {
        Object[] parameters = new Object[]{currentCustomerVO.getCustomerName(),
                currentCustomerVO.getAddress1(),
                currentCustomerVO.getAddress2(),
                currentCustomerVO.getPhoneNo(),
                currentCustomerVO.getMobile(),
                currentCustomerVO.getEmail(),
                currentCustomerVO.getContactPerson1(),
                currentCustomerVO.getContactMobile1(),
                currentCustomerVO.getContactPerson2(),
                currentCustomerVO.getContactMobile2(),
                currentCustomerVO.getNotes(),
                currentCustomerVO.getCustomerId(),
                currentCustomerVO.getModifiedOn(),
                currentCustomerVO.getModifiedBy()};

        try {
            getJdbcTemplate().update(UPDATE_CUSTOMER_SQL, parameters);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new CustomerException(CustomerException.DATABASE_ERROR);
        }
    }

    public List<CustomerVO> searchCustomer(CustomerVO searchCustomerVO) throws CustomerException {
        List<CustomerVO> customerVOs = null;
        try {
            customerVOs = SearchCustomer(searchCustomerVO);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new CustomerException(CustomerException.DATABASE_ERROR);
        }
        return customerVOs;
    }

    private CustomerVO fetchCustomerFromId(Long id) {
        return (CustomerVO) getJdbcTemplate().queryForObject(GET_SINGLE_CUSTOMER_SQL, new Object[]{id}, new CustomerListRowMapper());
    }

    private long saveCustomerDetails(CustomerVO currentCustomerVO) {
        insertCustomer = new SimpleJdbcInsert(getDataSource()).withTableName("customer").usingGeneratedKeyColumns("id");
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("Name", currentCustomerVO.getCustomerName())
                .addValue("Address1", currentCustomerVO.getAddress1())
                .addValue("address2", currentCustomerVO.getAddress2())
                .addValue("Phone", currentCustomerVO.getPhoneNo())
                .addValue("Mobile", currentCustomerVO.getMobile())
                .addValue("email", currentCustomerVO.getEmail())
                .addValue("ContactPerson1", currentCustomerVO.getContactPerson1())
                .addValue("ContactPh1", currentCustomerVO.getContactMobile1())
                .addValue("ContactPerson2", currentCustomerVO.getContactPerson2())
                .addValue("ContactPh2", currentCustomerVO.getContactMobile2())
                .addValue("Note", currentCustomerVO.getNotes())
                .addValue("createdOn", currentCustomerVO.getCreatedOn())
                .addValue("modifiedOn", currentCustomerVO.getModifiedOn())
                .addValue("createdBy", currentCustomerVO.getCreatedBy())
                .addValue("modifiedBy", currentCustomerVO.getModifiedBy());

        Number newId = insertCustomer.executeAndReturnKey(parameters);
        log.info(" the query resulted in  " + newId.longValue());
        currentCustomerVO.setCustomerId(newId.longValue());
        return newId.longValue();
    }

    private List<CustomerVO> fetchAllCustomers() {
        return (List<CustomerVO>) getJdbcTemplate().query(GET_CUSTOMERS_SQL, new CustomerListRowMapper());
    }

    private List<CustomerVO> SearchCustomer(CustomerVO searchVO) {
        StringBuffer SEARCH_QUERY = new StringBuffer();
        SEARCH_QUERY.append(" SELECT Id, Name,Address1,address2,Phone,Mobile,email,ContactPerson1,")
                .append(" ContactPh1,ContactPerson2,ContactPh2,Note FROM customer ");
        Boolean isWhereAdded = Boolean.FALSE;
        if (searchVO.getCustomerId() != null && searchVO.getCustomerId() > 0) {
            SEARCH_QUERY.append(" where ");
            isWhereAdded = Boolean.TRUE;
            SEARCH_QUERY.append(" Id = ").append(searchVO.getCustomerId());
        }
        if (searchVO.getCustomerName() != null && searchVO.getCustomerName().trim().length() > 0) {
            if (!isWhereAdded) {
                SEARCH_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                SEARCH_QUERY.append(" and ");
            }
            if (searchVO.getIncludes()) {
                SEARCH_QUERY.append(" Name like '%").append(searchVO.getCustomerName()).append("%'");
            } else if (searchVO.getStartsWith()) {
                SEARCH_QUERY.append(" Name like '").append(searchVO.getCustomerName()).append("%'");
            } else {
                SEARCH_QUERY.append(" Name like '").append(searchVO.getCustomerName()).append("'");
            }
        }
        if (searchVO.getMobile() != null && searchVO.getMobile().trim().length() > 0) {
            if (!isWhereAdded) {
                SEARCH_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                SEARCH_QUERY.append(" and ");
            }
            if (searchVO.getIncludes()) {
                SEARCH_QUERY.append(" Mobile like '%").append(searchVO.getMobile()).append("%'");
            } else if (searchVO.getStartsWith()) {
                SEARCH_QUERY.append(" Mobile like '").append(searchVO.getMobile()).append("%'");
            } else {
                SEARCH_QUERY.append(" Mobile like '").append(searchVO.getMobile()).append("'");
            }
        }
        log.info("Search query is " + SEARCH_QUERY.toString());
        return (List<CustomerVO>) getJdbcTemplate().query(SEARCH_QUERY.toString(), new CustomerListRowMapper());
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
