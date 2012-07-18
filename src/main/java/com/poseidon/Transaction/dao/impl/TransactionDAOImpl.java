package com.poseidon.Transaction.dao.impl;

import com.poseidon.Transaction.dao.TransactionDAO;
import com.poseidon.Transaction.domain.TransactionReportVO;
import com.poseidon.Transaction.domain.TransactionVO;
import com.poseidon.Transaction.exception.TransactionException;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.RowMapper;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 3:46:15 PM
 */
public class TransactionDAOImpl  extends JdbcDaoSupport implements TransactionDAO {
    private SimpleJdbcInsert insertTransaction;
    //logger
    private final Log log = LogFactory.getLog(TransactionDAOImpl.class);
    private final String GET_TODAYS_TRANSACTIONS_SQL ="SELECT t.Id, t.TagNo,C.Name, t.DateReported, mk.MakeName, " +
            " mdl.ModelName, t.SerialNo, t.Status " +
            " FROM transaction t inner join customer c on t.CustomerId=C.Id " +
            " inner join make mk on mk.Id=t.MakeId " +
            " inner join model mdl on mdl.Id=t.ModelId " +
            " WHERE CAST(t.DateReported AS DATE) = current_date() order by t.modifiedOn;";

    private static final String GET_SINGLE_TRANSACTION_SQL = "SELECT t.Id, t.TagNo, t.DateReported, t.CustomerId, " +
            " t.ProductCategory, t.MakeId, " +
            " t.ModelId, t.SerialNo, t.Status, t.Accessories, t.ComplaintReported, " +
            " t.ComplaintDiagonsed ,t.EnggRemark, t.RepairAction, t.Note "+
            " FROM transaction t inner join customer c on t.CustomerId=C.Id " +
            " inner join make mk on mk.Id=t.MakeId " +
            " inner join model mdl on mdl.Id=t.ModelId " +
            " WHERE t.Id = ? ;";
    private static final String UPDATE_TRANSACTION_SQL = " update transaction set ProductCategory = ?," +
            " MakeId = ?, ModelId = ? , SerialNo = ? , Status = ? , Accessories = ?, ComplaintReported = ? , " +
            " ComplaintDiagonsed = ?, EnggRemark = ?, RepairAction = ?, Note = ?, modifiedOn = ? , ModifiedBy =  ? " +
            " where Id = ? ";
    private static final String DELETE_TRANSACTION_BY_ID = " delete from transaction where id = ?";

    private static final String GET_SINGLE_TRANSACTION_FROM_TAG_SQL = "SELECT t.id, t.TagNo, t.DateReported, " +
            " c.Name, c.Address1, c.address2, c.Phone, c.Mobile, c.email, c.ContactPerson1, c.ContactPh1, c.ContactPerson2, " +
            " c.ContactPh2, t.ProductCategory, mk.MakeName, mdl.ModelName, t.SerialNo, t.Accessories, t.ComplaintReported, " +
            " t.ComplaintDiagonsed, t.EnggRemark, t.RepairAction, t.Note, t.Status FROM transaction t inner join customer c " +
            " on t.CustomerId=c.Id inner join make mk on t.MakeId=mk.Id inner join model mdl " +
            " on t.ModelId=mdl.Id and mdl.makeId=mk.Id where t.TagNo = ?";

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
        } catch (ParseException e) {
            e.printStackTrace();
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
    }

    private void saveTxn(TransactionVO currentTransaction) throws ParseException {
        insertTransaction = new SimpleJdbcInsert(getDataSource()).withTableName("transaction").usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("DateReported", getMySQLSaveDate(currentTransaction.getDateReported()))
                .addValue("CustomerId", currentTransaction.getCustomerId())
                .addValue("ProductCategory", currentTransaction.getProductCategory())
                .addValue("MakeId", currentTransaction.getMakeId())
                .addValue("ModelId", currentTransaction.getModelId())
                .addValue("SerialNo", currentTransaction.getSerialNo())
                .addValue("Accessories", currentTransaction.getAccessories())
                .addValue("ComplaintReported", currentTransaction.getComplaintReported())
                .addValue("ComplaintDiagonsed", currentTransaction.getComplaintDiagonsed())
                .addValue("EnggRemark", currentTransaction.getEnggRemark())
                .addValue("RepairAction", currentTransaction.getRepairAction())
                .addValue("Note", currentTransaction.getNotes())
                .addValue("Status", currentTransaction.getStatus())
                .addValue("createdOn", currentTransaction.getCreatedOn())
                .addValue("modifiedOn", currentTransaction.getModifiedOn())
                .addValue("createdBy", currentTransaction.getCreatedBy())
                .addValue("ModifiedBy", currentTransaction.getModifiedBy());
        Number newId = insertTransaction.executeAndReturnKey(parameters);
        log.info(" the queryForInt resulted in  " + newId.longValue());
        currentTransaction.setId(newId.longValue());
        // update the tag No
        String tagNo = "WON2N"+newId.longValue();
        String query = "update transaction set TagNo = '"+tagNo+"' where id ="+newId.longValue();
        getJdbcTemplate().update(query);
    }

    public List<TransactionVO> searchTransactions(TransactionVO searchTransaction) throws TransactionException {
        List<TransactionVO> transactionVOList;
        try {
            transactionVOList = searchTxs(searchTransaction);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        } catch (ParseException e) {
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

    public TransactionReportVO fetchTransactionFromTag(String tagNo) throws TransactionException {
        TransactionReportVO transactionVO;
        try {
            transactionVO = fetchTxnFromTag(tagNo);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
        return transactionVO;
    }

    private TransactionVO fetchTxn(Long id) {
        return (TransactionVO) getJdbcTemplate().queryForObject(GET_SINGLE_TRANSACTION_SQL, new Object[]{id}, new TransactionFullRowMapper());
    }

    private TransactionReportVO fetchTxnFromTag(String tag) {
        return (TransactionReportVO) getJdbcTemplate().queryForObject(GET_SINGLE_TRANSACTION_FROM_TAG_SQL, new Object[]{tag}, new TransactionReportRowMapper());
    }

    private List<TransactionVO> searchTxs(TransactionVO searchTransaction) throws ParseException {
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

    private String getMySQLFriendlyDate(String startDate) throws ParseException {
        Date startReportDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(startDate);
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(startReportDate);
    }
    private Date getMySQLSaveDate(String dateVal) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(dateVal);
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
            txs.setDateReported(resultSet.getDate("DateReported").toString());
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
            txs.setDateReported(resultSet.getDate("DateReported").toString());
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

    /**
     * Row mapper as inner class
     */
    private class TransactionReportRowMapper implements RowMapper {

        /**
         * method to map the result to vo
         *
         * @param resultSet resultSet instance
         * @param i         i instance
         * @return UserVO as Object
         * @throws java.sql.SQLException on error
         */
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            TransactionReportVO txs = new TransactionReportVO();
            txs.setId(resultSet.getLong("Id"));
            txs.setTagNo(resultSet.getString("TagNo"));
            txs.setDateReported(resultSet.getDate("DateReported"));
            txs.setCustomerName(resultSet.getString("Name"));
            txs.setAddress1(resultSet.getString("Address1"));
            txs.setAddress2(resultSet.getString("address2"));
            txs.setPhone(resultSet.getString("Phone"));
            txs.setMobile(resultSet.getString("Mobile"));
            txs.setEmail(resultSet.getString("email"));
            txs.setContactPerson1(resultSet.getString("ContactPerson1"));
            txs.setContactPh1(resultSet.getString("ContactPh1"));
            txs.setContactPerson2(resultSet.getString("ContactPerson2"));
            txs.setContactPh2(resultSet.getString("ContactPh2"));
            txs.setProductCategory(resultSet.getString("ProductCategory"));
            txs.setMakeName(resultSet.getString("MakeName"));
            txs.setModelName(resultSet.getString("ModelName"));
            txs.setSerialNo(resultSet.getString("SerialNo"));
            txs.setAccessories(resultSet.getString("Accessories"));
            txs.setComplaintReported(resultSet.getString("ComplaintReported"));
            txs.setComplaintDiagonsed(resultSet.getString("ComplaintDiagonsed"));
            txs.setEnggRemark(resultSet.getString("EnggRemark"));
            txs.setRepairAction(resultSet.getString("RepairAction"));
            txs.setNotes(resultSet.getString("Note"));
            txs.setStatus(resultSet.getString("Status"));
            return txs;
        }

    }
}
