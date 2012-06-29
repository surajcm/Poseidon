package com.poseidon.Transaction.dao.impl;

import com.poseidon.Transaction.dao.TransactionDAO;
import com.poseidon.Transaction.domain.TransactionVO;
import com.poseidon.Transaction.exception.TransactionException;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            " mdl.ModelName, t.SerialNo, t.Status " +
            " FROM transaction t inner join customer c on t.CustomerId=C.Id " +
            " inner join make mk on mk.Id=t.MakeId " +
            " inner join model mdl on mdl.Id=t.ModelId " +
            " WHERE CAST(t.DateReported AS DATE) = current_date();";
    private static final String INSERT_NEW_TRANSACTION_SQL = " insert into transaction (TagNo, DateReported, " +
            " CustomerId, ProductCategory, MakeId, ModelId, SerialNo, Accessories, ComplaintReported, " +
            " ComplaintDiagonsed, EnggRemark, RepairAction, Note, Status, createdOn, modifiedOn, " +
            " createdBy, ModifiedBy) values( ? , ? ,? , ? , ?, ?, ?, ?, ? ," +
            " ? , ? , ? , ? , ? , ?, ?, ? , ? )";
    private static final String GET_SINGLE_TRANSACTION_SQL = "SELECT t.Id, t.TagNo, t.DateReported, t.CustomerId, " +
            " t.ProductCategory, t.MakeId, " +
            " t.ModelId, t.SerialNo, t.Status, t.Accessories, t.ComplaintReported, " +
            " t.ComplaintDiagonsed ,t.EnggRemark, t.RepairAction, t.Note "+
            " FROM transaction t inner join customer c on t.CustomerId=C.Id " +
            " inner join make mk on mk.Id=t.MakeId " +
            " inner join model mdl on mdl.Id=t.ModelId " +
            " WHERE t.Id = ? ;";
    private static final String UPDATE_TRANSACTION_SQL = " update transaction set TagNo = ?, ProductCategory = ?," +
            " MakeId = ?, ModelId = ? , SerialNo = ? , Status = ? , Accessories = ?, ComplaintReported = ? , " +
            " ComplaintDiagonsed = ?, EnggRemark = ?, RepairAction = ?, Note = ?, modifiedOn = ? , ModifiedBy =  ? " +
            " where Id = ? ";
    private static final String DELETE_TRANSACTION_BY_ID = " delete from transaction where id = ?";

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
        try {
            saveTxn(currentTransaction);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
    }

    private void saveTxn(TransactionVO currentTransaction) {
        Object[] parameters =
                new Object[]{currentTransaction.getTagNo(),
                        currentTransaction.getDateReported(),
                        currentTransaction.getCustomerId(),
                        currentTransaction.getProductCategory(),
                        currentTransaction.getMakeId(),
                        currentTransaction.getModelId(),
                        currentTransaction.getSerialNo(),
                        currentTransaction.getAccessories(),
                        currentTransaction.getComplaintReported(),
                        currentTransaction.getComplaintDiagonsed(),
                        currentTransaction.getEnggRemark(),
                        currentTransaction.getRepairAction(),
                        currentTransaction.getNotes(),
                        currentTransaction.getStatus(),
                        currentTransaction.getCreatedOn(),
                        currentTransaction.getModifiedOn(),
                        currentTransaction.getCreatedBy(),
                        currentTransaction.getModifiedBy()};
        getJdbcTemplate().update(INSERT_NEW_TRANSACTION_SQL, parameters);
    }

    public List<TransactionVO> searchTransactions(TransactionVO searchTransaction) throws TransactionException {
        List<TransactionVO> transactionVOList;
        try {
            transactionVOList = searchTxs(searchTransaction);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
        return transactionVOList;
    }

    public TransactionVO fetchTransactionFromId(Long id) throws TransactionException {
        TransactionVO transactionVO;
        try {
            transactionVO = fetchTxn(id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
        return transactionVO;
    }

    public void updateTransaction(TransactionVO currentTransaction) throws TransactionException {
        Object[] parameters = new Object[]{
                currentTransaction.getTagNo(),
                currentTransaction.getProductCategory(),
                currentTransaction.getMakeId(),
                currentTransaction.getModelId(),
                currentTransaction.getSerialNo(),
                currentTransaction.getStatus(),
                currentTransaction.getAccessories(),
                currentTransaction.getComplaintReported(),
                currentTransaction.getComplaintDiagonsed(),
                currentTransaction.getEnggRemark(),
                currentTransaction.getRepairAction(),
                currentTransaction.getNotes(),
                currentTransaction.getModifiedOn(),
                currentTransaction.getModifiedBy(),
                currentTransaction.getId()};

        try {
            getJdbcTemplate().update(UPDATE_TRANSACTION_SQL, parameters);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
    }

    public void deleteTransaction(Long id) throws TransactionException {
        try {
            Object[] parameters = new Object[]{id};
            getJdbcTemplate().update(DELETE_TRANSACTION_BY_ID, parameters);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
    }

    private TransactionVO fetchTxn(Long id) {
        return (TransactionVO) getJdbcTemplate().queryForObject(GET_SINGLE_TRANSACTION_SQL, new Object[]{id}, new TransactionFullRowMapper());
    }

    private List<TransactionVO> searchTxs(TransactionVO searchTransaction) {
        StringBuffer SEARCH_TRANSACTION_QUERY = new StringBuffer();
        SEARCH_TRANSACTION_QUERY.append("SELECT Tr.id,")
                .append(" Tr.TagNo, ")
                .append(" Cust.Name ,")
                .append(" Tr.DateReported ,")
                .append(" Mk.MakeName ,")
                .append(" Mdl.ModelName ,")
                .append(" Tr.SerialNo,")
                .append(" Tr.Status ")
                .append(" FROM transaction Tr inner join make Mk on Tr.MakeId=Mk.Id ")
                .append(" inner join model Mdl on Tr.ModelId=Mdl.Id and Mdl.MakeId=Mk.Id ")
                .append(" inner join customer Cust on Cust.Id=Tr.CustomerId ");
        Boolean isWhereAdded = Boolean.FALSE;

        if(searchTransaction.getTagNo() != null && searchTransaction.getTagNo().trim().length() > 0){
            SEARCH_TRANSACTION_QUERY.append(" where ");
            isWhereAdded = Boolean.TRUE;
            if(searchTransaction.getIncludes()){
                SEARCH_TRANSACTION_QUERY.append(" Tr.TagNo like '%").append(searchTransaction.getTagNo()).append("%'");
            }else if (searchTransaction.getStartswith()){
                SEARCH_TRANSACTION_QUERY.append(" Tr.TagNo like '").append(searchTransaction.getTagNo()).append("%'");
            }else {
                SEARCH_TRANSACTION_QUERY.append(" Tr.TagNo like '").append(searchTransaction.getTagNo()).append("'");
            }
        }

        if (searchTransaction.getCustomerName() != null && searchTransaction.getCustomerName().trim().length() > 0 ) {
            if (!isWhereAdded) {
                SEARCH_TRANSACTION_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                SEARCH_TRANSACTION_QUERY.append(" and ");
            }
            if(searchTransaction.getIncludes()){
                SEARCH_TRANSACTION_QUERY.append(" Cust.Name like '%").append(searchTransaction.getCustomerName()).append("%'");
            }else if (searchTransaction.getStartswith()){
                SEARCH_TRANSACTION_QUERY.append(" Cust.Name like '").append(searchTransaction.getCustomerName()).append("%'");
            }else {
                SEARCH_TRANSACTION_QUERY.append(" Cust.Name like '").append(searchTransaction.getCustomerName()).append("'");
            }
        }

        // need to implement  and Tr.DateReported like '%%'
        if (searchTransaction.getSerialNo() != null && searchTransaction.getSerialNo().trim().length() > 0 ) {
            if (!isWhereAdded) {
                SEARCH_TRANSACTION_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                SEARCH_TRANSACTION_QUERY.append(" and ");
            }
            if(searchTransaction.getIncludes()){
                SEARCH_TRANSACTION_QUERY.append(" Tr.SerialNo like '%").append(searchTransaction.getSerialNo()).append("%'");
            }else if (searchTransaction.getStartswith()){
                SEARCH_TRANSACTION_QUERY.append(" Tr.SerialNo like '").append(searchTransaction.getSerialNo()).append("%'");
            }else {
                SEARCH_TRANSACTION_QUERY.append(" Tr.SerialNo like '").append(searchTransaction.getSerialNo()).append("'");
            }
        }

        if(searchTransaction.getMakeId() != null && searchTransaction.getMakeId() > 0 ){
            if (!isWhereAdded) {
                SEARCH_TRANSACTION_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                SEARCH_TRANSACTION_QUERY.append(" and ");
            }
            SEARCH_TRANSACTION_QUERY.append(" Tr.MakeId = ").append(searchTransaction.getMakeId());
        }

        if(searchTransaction.getModelId() != null && searchTransaction.getModelId() > 0 ){
            if (!isWhereAdded) {
                SEARCH_TRANSACTION_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                SEARCH_TRANSACTION_QUERY.append(" and ");
            }
            SEARCH_TRANSACTION_QUERY.append(" Tr.ModelId = ").append(searchTransaction.getModelId());
        }

        if(searchTransaction.getStatus() != null && searchTransaction.getStatus().trim().length() > 0 ){
            if (!isWhereAdded) {
                SEARCH_TRANSACTION_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                SEARCH_TRANSACTION_QUERY.append(" and ");
            }
            SEARCH_TRANSACTION_QUERY.append(" Tr.Status like '").append(searchTransaction.getStatus()).append("'");
        }
        if(searchTransaction.getStartDate() != null
                && searchTransaction.getStartDate().trim().length() > 0
                && searchTransaction.getEndDate() != null
                && searchTransaction.getEndDate().trim().length() > 0){
            if (!isWhereAdded) {
                SEARCH_TRANSACTION_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                SEARCH_TRANSACTION_QUERY.append(" and ");
            }

            SEARCH_TRANSACTION_QUERY.append(" Tr.DateReported between '").append(getMySQLFriendlyDate(searchTransaction.getStartDate()))
                    .append("' and '").append(getMySQLFriendlyDate(searchTransaction.getEndDate())).append("'");
        }
        log.info("query created is "+ SEARCH_TRANSACTION_QUERY.toString());
        return (List<TransactionVO>) getJdbcTemplate().query(SEARCH_TRANSACTION_QUERY.toString(), new TransactionListRowMapper());
    }

    private String getMySQLFriendlyDate(String startDate) {
        Date startReportDate = new Date(startDate);
        Format formatter = new SimpleDateFormat("yyyy-mm-dd");
        return formatter.format(startReportDate);
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
            txs.setDateReported(resultSet.getDate("DateReported"));
            txs.setCustomerName(resultSet.getString("Name"));
            txs.setMakeName(resultSet.getString("MakeName"));
            txs.setModelName(resultSet.getString("ModelName"));
            txs.setSerialNo(resultSet.getString("SerialNo"));
            txs.setStatus(resultSet.getString("Status"));
            return txs;
        }

    }

    /**
     * Row mapper as inner class
     */
    private class TransactionFullRowMapper implements RowMapper {

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
            txs.setDateReported(resultSet.getDate("DateReported"));
            txs.setCustomerId(resultSet.getLong("CustomerId"));
            txs.setProductCategory(resultSet.getString("ProductCategory"));
            txs.setMakeId(resultSet.getLong("MakeId"));
            txs.setModelId(resultSet.getLong("ModelId"));
            txs.setSerialNo(resultSet.getString("SerialNo"));
            txs.setStatus(resultSet.getString("Status"));
            txs.setAccessories(resultSet.getString("Accessories"));
            txs.setComplaintReported(resultSet.getString("ComplaintReported"));
            txs.setComplaintDiagonsed(resultSet.getString("ComplaintDiagonsed"));
            txs.setEnggRemark(resultSet.getString("EnggRemark"));
            txs.setRepairAction(resultSet.getString("RepairAction"));
            txs.setNotes(resultSet.getString("Note"));
            return txs;
        }

    }
}
