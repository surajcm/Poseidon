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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        txn.setDateReported(LocalDate.parse(currentTransaction.getDateReported(),
                DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                .atTime(OffsetTime.MIN));
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
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
            throw new TransactionException(TransactionException.DATABASE_ERROR);
        }
        return transactionVOList;
    }

    public TransactionVO fetchTransactionFromId(Long id) {
        TransactionVO transactionVO = null;
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            Transaction txn = optionalTransaction.get();
            transactionVO = convertToVO(txn);
        }
        return transactionVO;
    }

    private TransactionVO convertToVO(Transaction txn) {
        Customer customer = customerRepository.getOne(txn.getCustomerId());
        Make make = makeRepository.getOne(txn.getMakeId());
        Model model = modelRepository.getOne(txn.getModelId());
        TransactionVO transactionVO = new TransactionVO();
        transactionVO.setId(txn.getTransactionId());
        transactionVO.setTagNo(txn.getTagno());
        transactionVO.setDateReported(txn.getDateReported().toString());
        transactionVO.setProductCategory(txn.getProductCategory());
        transactionVO.setCustomerId(txn.getCustomerId());
        transactionVO.setCustomerName(customer.getName());
        transactionVO.setMakeId(txn.getMakeId());
        transactionVO.setMakeName(make.getMakeName());
        transactionVO.setModelId(txn.getModelId());
        transactionVO.setModelName(model.getModelName());
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
        if (optionalTransaction.isPresent()) {
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

    private List<TransactionVO> searchTxs(TransactionVO searchTransaction) {
        StringBuilder hqlQuery = new StringBuilder("SELECT tr FROM Transaction tr ")
                .append("inner join Make mk on tr.makeId=mk.id ")
                .append("inner join Model mdl on tr.modelId=mdl.id ")
                .append("inner join Customer cust on cust.id=tr.customerId ");
        boolean hasTagNo = searchTransaction.getTagNo() != null && searchTransaction.getTagNo().trim().length() > 0;
        boolean hasCustomerName = searchTransaction.getCustomerName() != null
                && searchTransaction.getCustomerName().trim().length() > 0;
        boolean hasSerialNo = searchTransaction.getSerialNo() != null
                && searchTransaction.getSerialNo().trim().length() > 0;
        boolean hasMake = searchTransaction.getMakeId() != null && searchTransaction.getMakeId() > 0;
        boolean hasModel = searchTransaction.getModelId() != null && searchTransaction.getModelId() > 0;
        boolean hasStatus = searchTransaction.getStatus() != null && searchTransaction.getStatus().trim().length() > 0;
        boolean hasStartDateAndEndDate = searchTransaction.getStartDate() != null
                && searchTransaction.getStartDate().trim().length() > 0
                && searchTransaction.getEndDate() != null
                && searchTransaction.getEndDate().trim().length() > 0;

        if (hasTagNo || hasCustomerName || hasSerialNo || hasMake || hasModel || hasStatus || hasStartDateAndEndDate) {
            hqlQuery.append(" where");
        }
        List<TransactionVO> hTransactionVoList = new ArrayList<>();
        try {
            boolean first = false;
            String tag = "";
            if (hasTagNo) {
                first = true;
                hqlQuery.append(" tr.tagno like :tag ");
                if (searchTransaction.getIncludes() != null && searchTransaction.getIncludes()) {
                    tag = "%" + searchTransaction.getTagNo() + "%";
                } else if (searchTransaction.getStartswith() != null && searchTransaction.getStartswith()) {
                    tag = searchTransaction.getTagNo() + "%";
                } else {
                    tag = searchTransaction.getTagNo();
                }
            }
            String customer = "";
            if (hasCustomerName) {
                if (first) {
                    hqlQuery.append(" and ");
                }
                hqlQuery.append(" cust.name like :customer ");
                if (searchTransaction.getIncludes() != null && searchTransaction.getIncludes()) {
                    customer = "%" + searchTransaction.getCustomerName() + "%";
                } else if (searchTransaction.getStartswith() != null && searchTransaction.getStartswith()) {
                    customer = searchTransaction.getCustomerName() + "%";
                } else {
                    customer = searchTransaction.getCustomerName();
                }
            }
            String serial = "";
            if (hasSerialNo) {
                if (first) {
                    hqlQuery.append(" and ");
                }
                hqlQuery.append(" tr.serialno like :serial ");
                if (searchTransaction.getIncludes() != null && searchTransaction.getIncludes()) {
                    serial = "%" + searchTransaction.getSerialNo() + "%";
                } else if (searchTransaction.getStartswith() != null && searchTransaction.getStartswith()) {
                    serial = searchTransaction.getSerialNo() + "%";
                } else {
                    serial = searchTransaction.getSerialNo();
                }
            }
            Long make = 0L;
            if (hasMake) {
                if (first) {
                    hqlQuery.append(" and ");
                }
                hqlQuery.append(" mk.id like :make ");
                make = searchTransaction.getMakeId();
            }
            Long model = 0L;
            if (hasModel) {
                if (first) {
                    hqlQuery.append(" and ");
                }
                hqlQuery.append(" mdl.id like :model ");
                model = searchTransaction.getModelId();
            }
            String status = "";
            if (hasStatus) {
                if (first) {
                    hqlQuery.append(" and ");
                }
                hqlQuery.append(" tr.status like :status ");
                if (searchTransaction.getIncludes() != null && searchTransaction.getIncludes()) {
                    status = "%" + searchTransaction.getStatus() + "%";
                } else if (searchTransaction.getStartswith() != null && searchTransaction.getStartswith()) {
                    status = searchTransaction.getStatus() + "%";
                } else {
                    status = searchTransaction.getStatus();
                }
            }
            OffsetDateTime start = OffsetDateTime.now();
            OffsetDateTime end = OffsetDateTime.now();
            if (hasStartDateAndEndDate) {
                if (first) {
                    hqlQuery.append(" and ");
                }
                start = getParsedDate(searchTransaction.getStartDate());
                end = getParsedDate(searchTransaction.getEndDate());
                hqlQuery.append(" tr.dateReported between :start and :end ");
            }
            Query query1 = em.createQuery(hqlQuery.toString());
            if (hasTagNo) {
                query1.setParameter("tag", tag);
            }
            if (hasCustomerName) {
                query1.setParameter("customer", customer);
            }
            if (hasSerialNo) {
                query1.setParameter("serial", serial);
            }
            if (hasMake) {
                query1.setParameter("make", make);
            }
            if (hasModel) {
                query1.setParameter("model", model);
            }
            if (hasStatus) {
                query1.setParameter("status", status);
            }
            if (hasStartDateAndEndDate) {
                query1.setParameter("start", start);
                query1.setParameter("end", end);
            }

            List<Transaction> transactions = query1.getResultList();
            hTransactionVoList = transactions.stream().map(this::convertToVO).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hTransactionVoList;
    }

    private OffsetDateTime getParsedDate(String dateField) {
        return LocalDate.parse(dateField,
                DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                .atTime(OffsetTime.MIN);
    }
}
