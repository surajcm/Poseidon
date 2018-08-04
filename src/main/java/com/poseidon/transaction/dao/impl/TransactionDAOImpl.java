package com.poseidon.transaction.dao.impl;

import com.poseidon.transaction.dao.TransactionDAO;
import com.poseidon.transaction.dao.entities.Transaction;
import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.exception.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 3:46:15 PM
 */
@Repository
public class TransactionDAOImpl implements TransactionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionRepository transactionRepository;

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

    private List<TransactionVO> getTodaysTransactions() throws DataAccessException {

        return (List<TransactionVO>) jdbcTemplate.query(GET_TODAYS_TRANSACTIONS_SQL, new TransactionListRowMapper());
    }

    public String saveTransaction(TransactionVO currentTransaction) throws TransactionException {
        String tagNo;
        try {
            Transaction txn = getTransaction(currentTransaction);
            Transaction newTxn = transactionRepository.save(txn);
            tagNo = "WON2N" + newTxn.getTransactionId();
            newTxn.setTagno(tagNo);
            transactionRepository.save(newTxn);
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
        return tagNo;
    }

    private Transaction getTransaction(TransactionVO currentTransaction) {
        Transaction txn = new Transaction();
        txn.setDateReported(OffsetDateTime.parse(currentTransaction.getDateReported()));
        txn.setProductCategory(currentTransaction.getProductCategory());
        txn.setCustomerId(currentTransaction.getCustomerId());
        txn.setMakeId(currentTransaction.getMakeId());
        txn.setModelId(currentTransaction.getModelId());
        txn.setSerialNumber(currentTransaction.getSerialNo());
        txn.setAccessories(currentTransaction.getAccessories());
        txn.setComplaintReported(currentTransaction.getComplaintReported());
        txn.setComplaintDiagnosed(currentTransaction.getComplaintDiagonsed());
        txn.setEngineerRemarks(currentTransaction.getEnggRemark());
        txn.setRepairAction(currentTransaction.getRepairAction());
        txn.setNote(currentTransaction.getNotes());
        txn.setStatus(currentTransaction.getStatus());
        txn.setCreatedBy(currentTransaction.getCreatedBy());
        txn.setModifiedBy(currentTransaction.getModifiedBy());
        return txn;
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
        TransactionVO transactionVO = null;
        try {
            Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
            if(optionalTransaction.isPresent()) {
                Transaction txn = optionalTransaction.get();
                transactionVO = convertToVO(txn);
            }
        } catch (DataAccessException e) {
            LOG.error(e.getLocalizedMessage());
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
        return transactionVO;
    }

    private TransactionVO convertToVO(Transaction txn) {
        TransactionVO transactionVO = new TransactionVO();
        transactionVO.setId(txn.getTransactionId());
        transactionVO.setTagNo(txn.getTagno());
        transactionVO.setDateReported(txn.getDateReported().toString());
        transactionVO.setProductCategory(txn.getProductCategory());
        transactionVO.setCustomerId(txn.getCustomerId());
        transactionVO.setMakeId(txn.getMakeId());
        transactionVO.setModelId(txn.getModelId());
        transactionVO.setSerialNo(txn.getSerialNumber());
        transactionVO.setStatus(txn.getStatus());
        transactionVO.setAccessories(txn.getAccessories());
        transactionVO.setComplaintReported(txn.getComplaintReported());
        transactionVO.setComplaintDiagonsed(txn.getComplaintDiagnosed());
        transactionVO.setEndDate(txn.getEngineerRemarks());
        transactionVO.setRepairAction(txn.getRepairAction());
        transactionVO.setNotes(txn.getNote());
        return transactionVO;
    }

    public void updateTransaction(TransactionVO currentTransaction) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(currentTransaction.getId());
        if(optionalTransaction.isPresent()) {
            Transaction txn = convertToTXN(optionalTransaction.get(), currentTransaction);
            transactionRepository.save(txn);
        }
    }

    private Transaction convertToTXN(Transaction txn, TransactionVO currentTransaction) {
        txn.setDateReported(OffsetDateTime.parse(currentTransaction.getDateReported()));
        txn.setProductCategory(currentTransaction.getProductCategory());
        txn.setCustomerId(currentTransaction.getCustomerId());
        txn.setMakeId(currentTransaction.getMakeId());
        txn.setModelId(currentTransaction.getModelId());
        txn.setSerialNumber(currentTransaction.getSerialNo());
        txn.setAccessories(currentTransaction.getAccessories());
        txn.setComplaintReported(currentTransaction.getComplaintReported());
        txn.setComplaintDiagnosed(currentTransaction.getComplaintDiagonsed());
        txn.setEngineerRemarks(currentTransaction.getEnggRemark());
        txn.setRepairAction(currentTransaction.getRepairAction());
        txn.setNote(currentTransaction.getNotes());
        txn.setStatus(currentTransaction.getStatus());
        txn.setModifiedBy(currentTransaction.getModifiedBy());
        return txn;
    }

    public void deleteTransaction(Long id) throws TransactionException {
        try {
            transactionRepository.deleteById(id);
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
            Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
            if (optionalTransaction.isPresent()) {
                Transaction txn = optionalTransaction.get();
                txn.setStatus(status);
                transactionRepository.save(txn);
            }
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

    private List<TransactionVO> getAllTransactions() throws DataAccessException {
        return (List<TransactionVO>) jdbcTemplate.query(GET_ALL_TRANSACTIONS_SQL, new TransactionListRowMapper());
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
