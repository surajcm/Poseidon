package com.poseidon.transaction.dao.impl;

import com.poseidon.transaction.dao.TransactionDAO;
import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.exception.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 3:46:15 PM
 */
@Repository
public class TransactionDAOImpl implements TransactionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private SimpleJdbcInsert insertTransaction;
    private static final Logger LOG = LoggerFactory.getLogger(TransactionDAOImpl.class);

    private final String GET_TODAYS_TRANSACTIONS_SQL = "SELECT t.Id, t.tagNo,c.name, t.dateReported, mk.makeName, " +
            " mdl.modelName, t.serialNo, t.status " +
            " FROM transaction t inner join customer c on t.customerId=c.Id " +
            " inner join make mk on mk.id=t.makeId " +
            " inner join model mdl on mdl.id=t.modelId " +
            " WHERE CAST(t.dateReported AS DATE) = current_date() order by t.modifiedOn;";

    private final String GET_ALL_TRANSACTIONS_SQL = "SELECT t.Id, t.tagNo,c.name, t.dateReported, mk.makeName, " +
            " mdl.modelName, t.serialNo, t.status " +
            " FROM transaction t inner join customer c on t.customerId=c.Id " +
            " inner join make mk on mk.id=t.makeId " +
            " inner join model mdl on mdl.id=t.modelId " +
            " order by t.modifiedOn;";

    private static final String GET_SINGLE_TRANSACTION_SQL = "SELECT t.Id, t.tagNo, t.dateReported, t.customerId, " +
            " t.productCategory, t.makeId, " +
            " t.modelId, t.serialNo, t.status, t.accessories, t.complaintReported, " +
            " t.complaintDiagnosed ,t.engineerRemarks, t.repairAction, t.note " +
            " FROM transaction t inner join customer c on t.customerId=c.Id " +
            " inner join make mk on mk.id=t.makeId " +
            " inner join model mdl on mdl.id=t.modelId " +
            " WHERE t.id = ? ;";
    private static final String UPDATE_TRANSACTION_SQL = " update transaction set dateReported = ? , productCategory = ?," +
            " makeId = ?, modelId = ? , serialNo = ? , status = ? , accessories = ?, complaintReported = ? , " +
            " complaintDiagnosed = ?, engineerRemarks = ?, repairAction = ?, note = ?, modifiedOn = ? , modifiedBy =  ? " +
            " where id = ? ";

    private static final String UPDATE_TRANSACTION_STATUS_SQL = " update transaction set status = ? " +
            " where id = ? ";
    private static final String DELETE_TRANSACTION_BY_ID = " delete from transaction where id = ?";

    private static final String GET_SINGLE_TRANSACTION_FROM_TAG_SQL = "SELECT t.id, t.tagNo, t.dateReported, " +
            " t.customerId, c.name, c.address1, c.address2, c.phone, c.mobile, c.email, c.contactPerson1, c.contactPhone1, c.contactPerson2, " +
            " c.contactPhone2, t.productCategory, mk.makeName, mdl.modelName, t.serialNo, t.accessories, t.complaintReported, " +
            " t.complaintDiagnosed, t.engineerRemarks, t.repairAction, t.note, t.status FROM transaction t inner join customer c " +
            " on t.customerId=c.id inner join make mk on t.makeId=mk.id inner join model mdl " +
            " on t.modelId=mdl.id and mdl.makeId=mk.id where t.tagNo = ?";

    public List<TransactionVO> listTodaysTransactions() throws TransactionException {
        List<TransactionVO> transactionVOList;
        try {
            transactionVOList = getTodaysTransactions();
        } catch (DataAccessException e) {
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
        return transactionVOList;
    }

    public String saveTransaction(TransactionVO currentTransaction) throws TransactionException {
        String tagNo;
        try {
            tagNo = saveTxn(currentTransaction);
        } catch (DataAccessException | ParseException e) {
            LOG.error(e.getLocalizedMessage());
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
        return tagNo;
    }

    private String saveTxn(TransactionVO currentTransaction) throws ParseException {
        insertTransaction = new SimpleJdbcInsert(jdbcTemplate).withTableName("transaction").usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("dateReported", getMySQLSaveDate(currentTransaction.getDateReported()))
                .addValue("customerId", currentTransaction.getCustomerId())
                .addValue("productCategory", currentTransaction.getProductCategory())
                .addValue("makeId", currentTransaction.getMakeId())
                .addValue("modelId", currentTransaction.getModelId())
                .addValue("serialNo", currentTransaction.getSerialNo())
                .addValue("accessories", currentTransaction.getAccessories())
                .addValue("complaintReported", currentTransaction.getComplaintReported())
                .addValue("complaintDiagnosed", currentTransaction.getComplaintDiagonsed())
                .addValue("engineerRemarks", currentTransaction.getEnggRemark())
                .addValue("repairAction", currentTransaction.getRepairAction())
                .addValue("note", currentTransaction.getNotes())
                .addValue("status", currentTransaction.getStatus())
                .addValue("createdOn", currentTransaction.getCreatedOn())
                .addValue("modifiedOn", currentTransaction.getModifiedOn())
                .addValue("createdBy", currentTransaction.getCreatedBy())
                .addValue("modifiedBy", currentTransaction.getModifiedBy());
        Number newId = insertTransaction.executeAndReturnKey(parameters);
        LOG.info(" the queryForInt resulted in  " + newId.longValue());
        currentTransaction.setId(newId.longValue());
        // update the tag No
        String tagNo = "WON2N" + newId.longValue();
        String query = "update transaction set TagNo = '" + tagNo + "' where id =" + newId.longValue();
        jdbcTemplate.update(query);
        return tagNo;
    }

    public List<TransactionVO> searchTransactions(TransactionVO searchTransaction) throws TransactionException {
        List<TransactionVO> transactionVOList;
        try {
            transactionVOList = searchTxs(searchTransaction);
        } catch (DataAccessException | ParseException e) {
            LOG.error(e.getLocalizedMessage());
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
        return transactionVOList;
    }

    public TransactionVO fetchTransactionFromId(Long id) throws TransactionException {
        TransactionVO transactionVO;
        try {
            transactionVO = fetchTxn(id);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
        return transactionVO;
    }

    public void updateTransaction(TransactionVO currentTransaction) throws TransactionException {
        try {
            Object[] parameters = new Object[]{
                    getMySQLSaveDate(currentTransaction.getDateReported()),
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


            jdbcTemplate.update(UPDATE_TRANSACTION_SQL, parameters);
        } catch (DataAccessException | ParseException e) {
            LOG.error(e.getLocalizedMessage());
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
    }

    public void deleteTransaction(Long id) throws TransactionException {
        try {
            Object[] parameters = new Object[]{id};
            jdbcTemplate.update(DELETE_TRANSACTION_BY_ID, parameters);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
    }

    public TransactionReportVO fetchTransactionFromTag(String tagNo) throws TransactionException {
        TransactionReportVO transactionVO;
        try {
            transactionVO = fetchTxnFromTag(tagNo);
        } catch (Exception e){
            LOG.error(e.getLocalizedMessage());
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
        return transactionVO;
    }

    @Override
    public void updateTransactionStatus(Long id, String status) throws TransactionException {
        try {
            Object[] parameters = new Object[]{status,id};


            jdbcTemplate.update(UPDATE_TRANSACTION_STATUS_SQL, parameters);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
    }

    @Override
    public List<TransactionVO> listAllTransactions() throws TransactionException {
        List<TransactionVO> transactionVOList;
        try {
            transactionVOList = getAllTransactions();
        } catch (DataAccessException e) {
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
        return transactionVOList;
    }

    private TransactionVO fetchTxn(Long id) {
        return (TransactionVO) jdbcTemplate.queryForObject(GET_SINGLE_TRANSACTION_SQL, new Object[]{id}, new TransactionFullRowMapper());
    }

    private TransactionReportVO fetchTxnFromTag(String tag) {
        return (TransactionReportVO) jdbcTemplate.queryForObject(GET_SINGLE_TRANSACTION_FROM_TAG_SQL, new Object[]{tag}, new TransactionReportRowMapper());
    }

    private List<TransactionVO> searchTxs(TransactionVO searchTransaction) throws ParseException {
        StringBuffer SEARCH_TRANSACTION_QUERY = new StringBuffer();
        SEARCH_TRANSACTION_QUERY.append("SELECT Tr.id,")
                .append(" Tr.tagNo, ")
                .append(" Cust.name ,")
                .append(" Tr.dateReported ,")
                .append(" Mk.makeName ,")
                .append(" Mdl.modelName ,")
                .append(" Tr.serialNo,")
                .append(" Tr.status ")
                .append(" FROM transaction Tr inner join make Mk on Tr.makeId=Mk.id ")
                .append(" inner join model Mdl on Tr.modelId=Mdl.id and Mdl.makeId=Mk.id ")
                .append(" inner join customer Cust on Cust.id=Tr.customerId ");
        Boolean isWhereAdded = Boolean.FALSE;

        if (searchTransaction.getTagNo() != null && searchTransaction.getTagNo().trim().length() > 0) {
            SEARCH_TRANSACTION_QUERY.append(" where ");
            isWhereAdded = Boolean.TRUE;
            if (searchTransaction.getIncludes()) {
                SEARCH_TRANSACTION_QUERY.append(" Tr.tagNo like '%").append(searchTransaction.getTagNo()).append("%'");
            } else if (searchTransaction.getStartswith()) {
                SEARCH_TRANSACTION_QUERY.append(" Tr.tagNo like '").append(searchTransaction.getTagNo()).append("%'");
            } else {
                SEARCH_TRANSACTION_QUERY.append(" Tr.tagNo like '").append(searchTransaction.getTagNo()).append("'");
            }
        }

        if (searchTransaction.getCustomerName() != null && searchTransaction.getCustomerName().trim().length() > 0) {
            if (!isWhereAdded) {
                SEARCH_TRANSACTION_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                SEARCH_TRANSACTION_QUERY.append(" and ");
            }
            if (searchTransaction.getIncludes()) {
                SEARCH_TRANSACTION_QUERY.append(" Cust.name like '%").append(searchTransaction.getCustomerName()).append("%'");
            } else if (searchTransaction.getStartswith()) {
                SEARCH_TRANSACTION_QUERY.append(" Cust.name like '").append(searchTransaction.getCustomerName()).append("%'");
            } else {
                SEARCH_TRANSACTION_QUERY.append(" Cust.name like '").append(searchTransaction.getCustomerName()).append("'");
            }
        }

        // need to implement  and Tr.DateReported like '%%'
        if (searchTransaction.getSerialNo() != null && searchTransaction.getSerialNo().trim().length() > 0) {
            if (!isWhereAdded) {
                SEARCH_TRANSACTION_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                SEARCH_TRANSACTION_QUERY.append(" and ");
            }
            if (searchTransaction.getIncludes()) {
                SEARCH_TRANSACTION_QUERY.append(" Tr.serialNo like '%").append(searchTransaction.getSerialNo()).append("%'");
            } else if (searchTransaction.getStartswith()) {
                SEARCH_TRANSACTION_QUERY.append(" Tr.serialNo like '").append(searchTransaction.getSerialNo()).append("%'");
            } else {
                SEARCH_TRANSACTION_QUERY.append(" Tr.serialNo like '").append(searchTransaction.getSerialNo()).append("'");
            }
        }

        if (searchTransaction.getMakeId() != null && searchTransaction.getMakeId() > 0) {
            if (!isWhereAdded) {
                SEARCH_TRANSACTION_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                SEARCH_TRANSACTION_QUERY.append(" and ");
            }
            SEARCH_TRANSACTION_QUERY.append(" Tr.makeId = ").append(searchTransaction.getMakeId());
        }

        if (searchTransaction.getModelId() != null && searchTransaction.getModelId() > 0) {
            if (!isWhereAdded) {
                SEARCH_TRANSACTION_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                SEARCH_TRANSACTION_QUERY.append(" and ");
            }
            SEARCH_TRANSACTION_QUERY.append(" Tr.modelId = ").append(searchTransaction.getModelId());
        }

        if (searchTransaction.getStatus() != null && searchTransaction.getStatus().trim().length() > 0) {
            if (!isWhereAdded) {
                SEARCH_TRANSACTION_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                SEARCH_TRANSACTION_QUERY.append(" and ");
            }
            SEARCH_TRANSACTION_QUERY.append(" Tr.status like '").append(searchTransaction.getStatus()).append("'");
        }
        if (searchTransaction.getStartDate() != null
                && searchTransaction.getStartDate().trim().length() > 0
                && searchTransaction.getEndDate() != null
                && searchTransaction.getEndDate().trim().length() > 0) {
            if (!isWhereAdded) {
                SEARCH_TRANSACTION_QUERY.append(" where ");
                isWhereAdded = Boolean.TRUE;
            } else {
                SEARCH_TRANSACTION_QUERY.append(" and ");
            }

            SEARCH_TRANSACTION_QUERY.append(" Tr.dateReported between '").append(getMySQLFriendlyDate(searchTransaction.getStartDate()))
                    .append("' and '").append(getMySQLFriendlyDate(searchTransaction.getEndDate())).append("'");
        }
        LOG.info("query created is " + SEARCH_TRANSACTION_QUERY.toString());
        return (List<TransactionVO>) jdbcTemplate.query(SEARCH_TRANSACTION_QUERY.toString(), new TransactionListRowMapper());
    }

    private String getMySQLFriendlyDate(String startDate) throws ParseException {
        Date startReportDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(startDate);
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(startReportDate);
    }

    private Date getMySQLSaveDate(String dateVal) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(dateVal);
    }

    private List<TransactionVO> getTodaysTransactions() throws DataAccessException {
        return (List<TransactionVO>) jdbcTemplate.query(GET_TODAYS_TRANSACTIONS_SQL, new TransactionListRowMapper());
    }

    private List<TransactionVO> getAllTransactions() throws DataAccessException {
        return (List<TransactionVO>) jdbcTemplate.query(GET_ALL_TRANSACTIONS_SQL, new TransactionListRowMapper());
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
            txs.setId(resultSet.getLong("id"));
            txs.setTagNo(resultSet.getString("tagNo"));
            txs.setDateReported(resultSet.getDate("dateReported").toString());
            txs.setCustomerName(resultSet.getString("name"));
            txs.setMakeName(resultSet.getString("makeName"));
            txs.setModelName(resultSet.getString("modelName"));
            txs.setSerialNo(resultSet.getString("serialNo"));
            txs.setStatus(resultSet.getString("status"));
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
            txs.setId(resultSet.getLong("id"));
            txs.setTagNo(resultSet.getString("tagNo"));
            txs.setDateReported(resultSet.getDate("dateReported").toString());
            txs.setCustomerId(resultSet.getLong("customerId"));
            txs.setProductCategory(resultSet.getString("productCategory"));
            txs.setMakeId(resultSet.getLong("makeId"));
            txs.setModelId(resultSet.getLong("modelId"));
            txs.setSerialNo(resultSet.getString("serialNo"));
            txs.setStatus(resultSet.getString("status"));
            txs.setAccessories(resultSet.getString("accessories"));
            txs.setComplaintReported(resultSet.getString("complaintReported"));
            txs.setComplaintDiagonsed(resultSet.getString("complaintDiagnosed"));
            txs.setEnggRemark(resultSet.getString("engineerRemarks"));
            txs.setRepairAction(resultSet.getString("repairAction"));
            txs.setNotes(resultSet.getString("note"));
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
            txs.setId(resultSet.getLong("id"));
            txs.setTagNo(resultSet.getString("tagNo"));
            txs.setDateReported(resultSet.getDate("dateReported"));
            txs.setCustomerName(resultSet.getString("name"));
            txs.setCustomerId(resultSet.getLong("customerId"));
            txs.setAddress1(resultSet.getString("address1"));
            txs.setAddress2(resultSet.getString("address2"));
            txs.setPhone(resultSet.getString("phone"));
            txs.setMobile(resultSet.getString("mobile"));
            txs.setEmail(resultSet.getString("email"));
            txs.setContactPerson1(resultSet.getString("contactPerson1"));
            txs.setContactPh1(resultSet.getString("contactPhone1"));
            txs.setContactPerson2(resultSet.getString("contactPerson2"));
            txs.setContactPh2(resultSet.getString("contactPhone2"));
            txs.setProductCategory(resultSet.getString("productCategory"));
            txs.setMakeName(resultSet.getString("makeName"));
            txs.setModelName(resultSet.getString("modelName"));
            txs.setSerialNo(resultSet.getString("serialNo"));
            txs.setAccessories(resultSet.getString("accessories"));
            txs.setComplaintReported(resultSet.getString("complaintReported"));
            txs.setComplaintDiagonsed(resultSet.getString("complaintDiagnosed"));
            txs.setEnggRemark(resultSet.getString("engineerRemarks"));
            txs.setRepairAction(resultSet.getString("repairAction"));
            txs.setNotes(resultSet.getString("note"));
            txs.setStatus(resultSet.getString("status"));
            return txs;
        }

    }
}
