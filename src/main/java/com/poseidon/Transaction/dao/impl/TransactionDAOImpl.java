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
    private final String GET_TODAYS_TRANSACTIONS_SQL ="SELECT t.Id, t.TagNo,C.Name, t.DateReported, mk.MakeName, " +
            "mdl.ModelName, t.SerialNo, t.Status " +
            "FROM transaction t inner join customer c on t.CustomerId=C.Id " +
            "inner join make mk on mk.Id=t.MakeId " +
            "inner join model mdl on mdl.Id=t.ModelId " +
            "WHERE t.DateReported=CURDATE();";
    
    public List<TransactionVO> listTodaysTransactions() throws TransactionException {
        List<TransactionVO> transactionVOList;
        try {
            transactionVOList = getTodaysTransactions();
        } catch (DataAccessException e) {
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
        return transactionVOList;
    }

    public void saveTransaction(TransactionVO currentTransaction) throws TransactionException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<TransactionVO> getTodaysTransactions() throws DataAccessException {
        return (List<TransactionVO>) getJdbcTemplate().query(GET_TODAYS_TRANSACTIONS_SQL, new TransactionListRowMapper());
    }

    /**
     * Row mapper as inner class
     */
    private class TransactionListRowMapper implements RowMapper {

        /**
         * method to map the result to vo
         *
         * @param resultSet resultSet instance
         * @param i         i instance
         * @return UserVO as Object
         * @throws java.sql.SQLException on error
         */
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            TransactionVO txs = new TransactionVO();
            txs.setId(resultSet.getLong("Id"));
            txs.setTagNo(resultSet.getString("TagNo"));
            txs.setCustomerName(resultSet.getString("Name"));
            txs.setDateReported(resultSet.getDate("DateReported"));
            txs.setMakeName(resultSet.getString("MakeName"));
            txs.setModelName(resultSet.getString("ModelName"));
            txs.setSerialNo(resultSet.getString("SerialNo"));
            txs.setStatus(resultSet.getString("Status"));
            /*txs.setCreatedBy(resultSet.getString("createdBy"));
            txs.setCreatedOn(resultSet.getDate("createdOn"));
            txs.setModifiedBy(resultSet.getString("modifiedBy"));
            txs.setModifiedOn(resultSet.getDate("modifiedOn"));*/

            return txs;
        }

    }
}
