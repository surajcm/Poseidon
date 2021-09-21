package com.poseidon.transaction.dao;

import com.poseidon.customer.dao.entities.Customer;
import com.poseidon.customer.dao.repo.CustomerRepository;
import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.dao.entities.Model;
import com.poseidon.make.dao.repo.MakeRepository;
import com.poseidon.make.dao.repo.ModelRepository;
import com.poseidon.transaction.dao.entities.Transaction;
import com.poseidon.transaction.dao.repo.TransactionRepository;
import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.domain.TransactionVO;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.rainerhahnekamp.sneakythrow.Sneaky.sneak;
import static com.rainerhahnekamp.sneakythrow.Sneaky.sneaked;

/**
 * user: Suraj.
 * Date: Jun 2, 2012
 * Time: 3:46:15 PM
 */
@SuppressWarnings("unused")
@Repository
public class TransactionDAO {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionDAO.class);
    public static final String AND = " and ";
    public static final String COMPANY_KEY = "WON2N";
    private static final String TAG_NO = "tagno";
    private static final String MM_DD_YYYY = "MM/dd/yyyy";
    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final MakeRepository makeRepository;
    private final ModelRepository modelRepository;
    @PersistenceContext
    private EntityManager em;

    public TransactionDAO(final TransactionRepository transactionRepository,
                          final CustomerRepository customerRepository,
                          final MakeRepository makeRepository,
                          final ModelRepository modelRepository) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
        this.makeRepository = makeRepository;
        this.modelRepository = modelRepository;
    }

    /**
     * list all transactions.
     *
     * @return list of transactions
     */
    public List<TransactionVO> listAllTransactions() {
        var transactions = sneak(transactionRepository::findAll);
        return transactions.stream().map(this::convertToVO).toList();
    }

    /**
     * list today's transactions.
     */
    public List<TransactionVO> listTodaysTransactions() {
        var transactions = sneak(transactionRepository::todaysTransaction);
        return transactions.stream().map(this::convertToVO).toList();
    }

    /**
     * fetch a transaction by its id.
     *
     * @param id id of the transaction
     * @return transaction
     */
    public TransactionVO fetchTransactionFromId(final Long id) {
        TransactionVO transactionVO = null;
        var optionalTransaction = sneak(() -> transactionRepository.findById(id));
        if (optionalTransaction.isPresent()) {
            transactionVO = convertToVO(optionalTransaction.get());
        }
        return transactionVO;
    }

    /**
     * fetch the transaction from its tag.
     *
     * @param tagNo tag
     * @return transaction for reporting
     */
    public TransactionReportVO fetchTransactionFromTag(final String tagNo) {
        var transaction = sneak(() -> transactionRepository.findBytagno(tagNo));
        return convertToTransactionReportVO(transaction);
    }

    /**
     * save transaction.
     *
     * @param currentTransaction transaction
     * @return tag number
     */
    public String saveTransaction(final TransactionVO currentTransaction) {
        var txn = getTransaction(currentTransaction);
        var newTxn = sneak(() -> transactionRepository.save(txn));
        var tagNo = COMPANY_KEY + newTxn.getId();
        newTxn.setTagno(tagNo);
        sneak(() -> transactionRepository.save(newTxn));
        return tagNo;
    }

    /**
     * update transaction.
     *
     * @param currentTransaction transaction
     */
    public void updateTransaction(final TransactionVO currentTransaction) {
        var optionalTransaction = sneak(() -> transactionRepository.findById(currentTransaction.getId()));
        if (optionalTransaction.isPresent()) {
            var txn = convertToTXN(optionalTransaction.get(), currentTransaction);
            transactionRepository.save(txn);
        }
    }

    /**
     * delete a transaction based on id.
     *
     * @param id id of transaction
     */
    public void deleteTransaction(final Long id) {
        sneaked(transactionRepository::deleteById);
    }

    /**
     * update transaction status.
     *
     * @param id     id of the transaction
     * @param status status
     */
    public void updateTransactionStatus(final Long id, final String status) {
        var optionalTransaction = sneak(() -> transactionRepository.findById(id));
        if (optionalTransaction.isPresent()) {
            var txn = optionalTransaction.get();
            txn.setStatus(status);
            sneaked(() -> transactionRepository.save(txn));
        }
    }

    /**
     * search transactions.
     *
     * @param searchTransaction transaction
     * @return list of matching transactions
     */
    public List<TransactionVO> searchTransactions(final TransactionVO searchTransaction) {
        return sneak(() -> searchWithSpecTxs(searchTransaction));
    }

    private List<TransactionVO> searchWithSpecTxs(final TransactionVO searchTransaction) {
        var builder = em.unwrap(Session.class).getCriteriaBuilder();
        var criteria = builder.createQuery(Transaction.class);
        var txnRoot = criteria.from(Transaction.class);
        criteria.select(txnRoot);
        var includes = searchTransaction.getIncludes();
        var startsWith = searchTransaction.getStartswith();
        if (StringUtils.hasText(searchTransaction.getTagNo())) {
            var tag = pattern(includes, startsWith, searchTransaction.getTagNo());
            criteria.where(builder.like(txnRoot.get(TAG_NO), tag));
        }
        if (StringUtils.hasText(searchTransaction.getCustomerName())) {
            //todo: use search, so that starts with and includes can happen
            var customers = customerRepository
                    .findByName(searchTransaction.getCustomerName());
            var customerIds = customers.stream().map(Customer::getId)
                    .collect(Collectors.toUnmodifiableSet());
            var customerExpression = txnRoot.get("customerId");
            var customerPredicate = customerExpression.in(customerIds);
            criteria.where(customerPredicate);
        }
        if (StringUtils.hasText(searchTransaction.getStartDate()) ||
                StringUtils.hasText(searchTransaction.getEndDate())) {
            var startDate = fetchStartDate(searchTransaction.getStartDate());
            var endDate = fetchEndDate(searchTransaction.getEndDate());
            criteria.where(builder.between(txnRoot.get("dateReported"), startDate, endDate));
        }
        if (StringUtils.hasText(searchTransaction.getSerialNo())) {
            var serial = pattern(includes, startsWith, searchTransaction.getSerialNo());
            criteria.where(builder.like(txnRoot.get("serialNumber"), serial));
        }
        if (searchTransaction.getMakeId() != null) {
            criteria.where(builder.equal(txnRoot.get("makeId"), searchTransaction.getMakeId()));
        }
        if (searchTransaction.getModelId() != null) {
            criteria.where(builder.equal(txnRoot.get("modelId"), searchTransaction.getModelId()));
        }
        if (StringUtils.hasText(searchTransaction.getStatus())) {
            criteria.where(builder.like(txnRoot.get("status"), searchTransaction.getStatus()));
        }
        List<Transaction> resultList = em.unwrap(Session.class).createQuery(criteria).getResultList();
        return resultList.stream().map(this::convertToVO).toList();
    }

    private LocalDateTime fetchStartDate(final String startDate) {
        LocalDateTime dateTime;
        if (StringUtils.hasText(startDate)) {
            var formatter = DateTimeFormatter.ofPattern(MM_DD_YYYY);
            dateTime = LocalDate.parse(startDate, formatter).atStartOfDay();
        } else {
            dateTime = LocalDateTime.now(ZoneId.systemDefault()).minusYears(10L);
        }
        return dateTime;
    }

    private LocalDateTime fetchEndDate(final String endDate) {
        LocalDateTime dateTime;
        if (StringUtils.hasText(endDate)) {
            var formatter = DateTimeFormatter.ofPattern(MM_DD_YYYY);
            dateTime = LocalDate.parse(endDate, formatter).atStartOfDay();
        } else {
            dateTime = LocalDateTime.now(ZoneId.systemDefault());
        }
        return dateTime;
    }

    private String formatLocalDateTimeToString(final LocalDateTime start) {
        var formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return start.format(formatter);
    }

    private String pattern(final boolean includes, final boolean startsWith, final String element) {
        String patternString;
        if (Boolean.TRUE.equals(includes)) {
            patternString = "%" + element + "%";
        } else if (Boolean.TRUE.equals(startsWith)) {
            patternString = element + "%";
        } else {
            patternString = element;
        }
        return patternString;
    }

    private TransactionVO convertToVO(final Transaction txn) {
        final Optional<Customer> customer = customerRepository.findById(txn.getCustomerId());
        final Make make = makeRepository.getById(txn.getMakeId());
        final Model model = modelRepository.getById(txn.getModelId());
        var transactionVO = new TransactionVO();
        transactionVO.setId(txn.getId());
        transactionVO.setTagNo(txn.getTagno());
        transactionVO.setDateReported(txn.getDateReported().toString());
        transactionVO.setProductCategory(txn.getProductCategory());
        transactionVO.setCustomerId(txn.getCustomerId());
        customer.ifPresent(value -> transactionVO.setCustomerName(value.getName()));
        transactionVO.setMakeId(txn.getMakeId());
        transactionVO.setMakeName(make.getMakeName());
        transactionVO.setModelId(txn.getModelId());
        transactionVO.setModelName(model.getModelName());
        transactionVO.setSerialNo(txn.getSerialNumber());
        transactionVO.setStatus(txn.getStatus());
        transactionVO.setAccessories(txn.getAccessories());
        transactionVO.setComplaintReported(txn.getComplaintReported());
        transactionVO.setComplaintDiagnosed(txn.getComplaintDiagnosed());
        transactionVO.setEnggRemark(txn.getEngineerRemarks());
        transactionVO.setRepairAction(txn.getRepairAction());
        transactionVO.setNotes(txn.getNote());
        return transactionVO;
    }

    private Transaction convertToTXN(final Transaction txn, final TransactionVO currentTransaction) {
        txn.setDateReported(parseDate(currentTransaction.getDateReported()));
        setTxn(currentTransaction, txn);
        txn.setModifiedBy(currentTransaction.getModifiedBy());
        return txn;
    }

    private LocalDateTime parseDate(final String dateReported) {
        var parsedTime = LocalDateTime.now(ZoneId.systemDefault());
        try {
            parsedTime = LocalDateTime.parse(dateReported);
        } catch (Exception ex) {
            LOG.info(ex.getMessage());
        }
        return parsedTime;
    }

    private LocalDateTime getParsedDate(final String dateReported) {
        var reported = LocalDateTime.now(ZoneId.systemDefault());
        try {
            reported = LocalDate.parse(dateReported, DateTimeFormatter.ofPattern(MM_DD_YYYY))
                    .atStartOfDay();
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
        return reported;
    }

    private TransactionReportVO convertToTransactionReportVO(final Transaction transaction) {
        var txs = new TransactionReportVO();
        txs.setId(transaction.getId());
        txs.setTagNo(transaction.getTagno());
        txs.setDateReported(transaction.getDateReported());
        txs.setCustomerId(transaction.getCustomerId());
        var customerOpt = sneak(() -> customerRepository.findById(transaction.getCustomerId()));
        if (customerOpt.isPresent()) {
            var customer = customerOpt.get();
            txs.setCustomerName(customer.getName());
            txs.setAddress(customer.getAddress());
            txs.setPhone(customer.getPhone());
            txs.setMobile(customer.getMobile());
            txs.setEmail(customer.getEmail());
        }
        txs.setProductCategory(transaction.getProductCategory());
        var make = sneak(() -> makeRepository.getById(transaction.getMakeId()));
        txs.setMakeName(make.getMakeName());
        var model = sneak(() -> modelRepository.getById(transaction.getModelId()));
        txs.setModelName(model.getModelName());
        txs.setSerialNo(transaction.getSerialNumber());
        txs.setAccessories(transaction.getAccessories());
        txs.setComplaintReported(transaction.getComplaintReported());
        txs.setComplaintDiagnosed(transaction.getComplaintDiagnosed());
        txs.setEnggRemark(transaction.getEngineerRemarks());
        txs.setRepairAction(transaction.getRepairAction());
        txs.setNotes(transaction.getNote());
        txs.setStatus(transaction.getStatus());
        return txs;
    }

    private Transaction getTransaction(final TransactionVO currentTransaction) {
        var txn = new Transaction();
        txn.setDateReported(getParsedDate(currentTransaction.getDateReported()));
        setTxn(currentTransaction, txn);
        txn.setCreatedBy(currentTransaction.getCreatedBy());
        txn.setModifiedBy(currentTransaction.getModifiedBy());
        return txn;
    }

    private void setTxn(final TransactionVO currentTransaction, final Transaction txn) {
        txn.setProductCategory(currentTransaction.getProductCategory());
        txn.setCustomerId(currentTransaction.getCustomerId());
        txn.setMakeId(currentTransaction.getMakeId());
        txn.setModelId(currentTransaction.getModelId());
        txn.setSerialNumber(currentTransaction.getSerialNo());
        txn.setAccessories(currentTransaction.getAccessories());
        txn.setComplaintReported(currentTransaction.getComplaintReported());
        txn.setComplaintDiagnosed(currentTransaction.getComplaintDiagnosed());
        txn.setEngineerRemarks(currentTransaction.getEnggRemark());
        txn.setRepairAction(currentTransaction.getRepairAction());
        txn.setNote(currentTransaction.getNotes());
        txn.setStatus(currentTransaction.getStatus());
    }
}
