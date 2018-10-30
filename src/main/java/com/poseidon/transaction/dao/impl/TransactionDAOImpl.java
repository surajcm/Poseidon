package com.poseidon.transaction.dao.impl;

import com.poseidon.customer.dao.entities.Customer;
import com.poseidon.customer.dao.impl.CustomerRepository;
import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.dao.entities.Model;
import com.poseidon.make.dao.impl.MakeRepository;
import com.poseidon.make.dao.impl.ModelRepository;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import java.util.stream.Collectors;

/**
 * user: Suraj
 * Date: Jun 2, 2012
 * Time: 3:46:15 PM
 */
@SuppressWarnings("unused")
@Repository
public class TransactionDAOImpl implements TransactionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MakeRepository makeRepository;

    @Autowired
    private ModelRepository modelRepository;

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOG = LoggerFactory.getLogger(TransactionDAOImpl.class);

    public List<TransactionVO> listTodaysTransactions() {
        List<Transaction> transactions = transactionRepository.todaysTransaction();
        return transactions.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    public String saveTransaction(TransactionVO currentTransaction) {
        Transaction txn = getTransaction(currentTransaction);
        Transaction newTxn = transactionRepository.save(txn);
        String tagNo = "WON2N" + newTxn.getTransactionId();
        newTxn.setTagno(tagNo);
        transactionRepository.save(newTxn);
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

    public TransactionVO fetchTransactionFromId(Long id) {
        TransactionVO transactionVO = null;
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if(optionalTransaction.isPresent()) {
            Transaction txn = optionalTransaction.get();
            transactionVO = convertToVO(txn);
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

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public TransactionReportVO fetchTransactionFromTag(String tagNo) {
        Transaction transaction = transactionRepository.findBytagno(tagNo);
        return convertToTransactionReportVO(transaction);
    }

    @Override
    public void updateTransactionStatus(Long id, String status) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            Transaction txn = optionalTransaction.get();
            txn.setStatus(status);
            transactionRepository.save(txn);
        }
    }

    @Override
    public List<TransactionVO> listAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    private TransactionReportVO convertToTransactionReportVO(Transaction transaction) {
        Customer customer = customerRepository.getOne(transaction.getCustomerId());
        Make make = makeRepository.getOne(transaction.getMakeId());
        Model model = modelRepository.getOne(transaction.getModelId());
        TransactionReportVO txs = new TransactionReportVO();
        txs.setId(transaction.getTransactionId());
        txs.setTagNo(transaction.getTagno());
        txs.setDateReported(new Date(transaction.getDateReported().toInstant().toEpochMilli()));
        txs.setCustomerName(customer.getName());
        txs.setCustomerId(transaction.getCustomerId());
        txs.setAddress1(customer.getAddress1());
        txs.setAddress2(customer.getAddress2());
        txs.setPhone(customer.getPhone());
        txs.setMobile(customer.getMobile());
        txs.setEmail(customer.getEmail());
        txs.setContactPerson1(customer.getContactPerson1());
        txs.setContactPh1(customer.getContactPhone1());
        txs.setContactPerson2(customer.getContactPerson2());
        txs.setContactPh2(customer.getContactPhone2());
        txs.setProductCategory(transaction.getProductCategory());
        txs.setMakeName(make.getMakeName());
        txs.setModelName(model.getModelName());
        txs.setSerialNo(transaction.getSerialNumber());
        txs.setAccessories(transaction.getAccessories());
        txs.setComplaintReported(transaction.getComplaintReported());
        txs.setComplaintDiagonsed(transaction.getComplaintDiagnosed());
        txs.setEnggRemark(transaction.getEngineerRemarks());
        txs.setRepairAction(transaction.getRepairAction());
        txs.setNotes(transaction.getNote());
        txs.setStatus(transaction.getStatus());
        return txs;
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
        List<TransactionVO> query = jdbcTemplate.query(SEARCH_TRANSACTION_QUERY.toString(), new TransactionListRowMapper());

        /*CriteriaBuilder builder = em.unwrap(Session.class).getCriteriaBuilder();
        CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
        Root<Transaction> transactionRoot = criteria.from(Transaction.class);
        criteria.select(transactionRoot);
        //todo : complete this
        if (searchTransaction.getIncludes()) {
            if(!StringUtils.isEmpty(searchTransaction.getTagNo())) {
                criteria.where(builder.like(transactionRoot.get("tagno"), "%"+searchTransaction.getTagNo()+"%"));
            }
        } else if (searchTransaction.getStartswith()) {
            SEARCH_TRANSACTION_QUERY.append(" Tr.tagNo like '").append(searchTransaction.getTagNo()).append("%'");
        } else {
            SEARCH_TRANSACTION_QUERY.append(" Tr.tagNo like '").append(searchTransaction.getTagNo()).append("'");
        }*/

        return query;
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
}
