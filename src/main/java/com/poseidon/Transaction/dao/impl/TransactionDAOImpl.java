package com.poseidon.Transaction.dao.impl;

import com.poseidon.Transaction.dao.TransactionDAO;
import com.poseidon.Transaction.domain.TransactionVO;
import com.poseidon.Transaction.exception.TransactionException;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.RowMapper;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 3:46:15 PM
 */
public class TransactionDAOImpl  extends JdbcDaoSupport implements TransactionDAO {
   //logger
    private final Log log = LogFactory.getLog(TransactionDAOImpl.class);

    public List<TransactionVO> listTodaysTransactions() throws TransactionException {
        List<TransactionVO> transactionVOList;
        try {
            transactionVOList = getTodaysTransactions();
        } catch (DataAccessException e) {
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
        return transactionVOList;
    }

    public List<TransactionVO> getTodaysTransactions() throws DataAccessException {
        return getJdbcTemplate().query(GET_ALL_USERS_SQL, new UserRowMapper());
    }

    /**
     * Row mapper as inner class
     */
    private class UserRowMapper implements RowMapper {

        /**
         * method to map the result to vo
         *
         * @param resultSet resultSet instance
         * @param i         i instance
         * @return UserVO as Object
         * @throws java.sql.SQLException on error
         */
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            TransactionVO user = new TransactionVO();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("Name"));
            user.setLoginId(resultSet.getString("LogId"));
            user.setPassword(resultSet.getString("Pass"));
            user.setRole(resultSet.getString("Role"));
            user.setCreatedBy(resultSet.getString("createdBy"));
            user.setCreatedDate(resultSet.getDate("createdOn"));
            user.setLastModifiedBy(resultSet.getString("modifiedBy"));
            user.setModifiedDate(resultSet.getDate("modifiedOn"));

            return user;
        }

    }
}
