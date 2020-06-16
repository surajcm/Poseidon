package com.poseidon.transaction.dao.impl;

import com.poseidon.customer.dao.entities.Customer;
import com.poseidon.customer.dao.entities.CustomerAdditionalDetails;
import com.poseidon.customer.dao.impl.CustomerAdditionalDetailsRepository;
import com.poseidon.customer.dao.impl.CustomerRepository;
import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.dao.entities.Model;
import com.poseidon.make.dao.impl.MakeRepository;
import com.poseidon.make.dao.impl.ModelRepository;
import com.poseidon.transaction.dao.entities.Transaction;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.exception.TransactionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TransactionDAOImplTest {
    private final TransactionDAOImpl transactionDAO = new TransactionDAOImpl();
    private final TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
    private final CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
    private final MakeRepository makeRepository = Mockito.mock(MakeRepository.class);
    private final ModelRepository modelRepository = Mockito.mock(ModelRepository.class);
    private final CustomerAdditionalDetailsRepository customerAdditionalDetailsRepository =
            Mockito.mock(CustomerAdditionalDetailsRepository.class);

    @BeforeEach
    public void setup() {
        Whitebox.setInternalState(transactionDAO, "transactionRepository", transactionRepository);
        Whitebox.setInternalState(transactionDAO, "customerRepository", customerRepository);
        Whitebox.setInternalState(transactionDAO, "makeRepository", makeRepository);
        Whitebox.setInternalState(transactionDAO, "modelRepository", modelRepository);
        Whitebox.setInternalState(transactionDAO, "customerAdditionalDetailsRepository",
                customerAdditionalDetailsRepository);
    }

    @Test
    public void listTodaysTransactionsSuccess() throws TransactionException {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(mockCustomer()));
        when(makeRepository.getOne(anyLong())).thenReturn(mockMake());
        when(modelRepository.getOne(anyLong())).thenReturn(mockModel());
        when(transactionRepository.todaysTransaction()).thenReturn(mockListOfTransactions());
        Assertions.assertNotNull(transactionDAO.listTodaysTransactions());
    }

    @Test
    public void listTodaysTransactionsFailure() {
        when(transactionRepository.todaysTransaction()).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(TransactionException.class, transactionDAO::listTodaysTransactions);
    }

    @Test
    public void saveTransactionSuccess() {
        when(transactionRepository.save(any())).thenReturn(mockTransaction());
        Assertions.assertAll(() -> transactionDAO.saveTransaction(new TransactionVO()));
    }

    @Test
    public void saveTransactionFailure() {
        when(transactionRepository.save(any())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(TransactionException.class, () -> transactionDAO.saveTransaction(new TransactionVO()));
    }

    @Test
    public void fetchTransactionFromIdSuccess() throws TransactionException {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(mockCustomer()));
        when(makeRepository.getOne(anyLong())).thenReturn(mockMake());
        when(modelRepository.getOne(anyLong())).thenReturn(mockModel());
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(mockTransaction()));
        Assertions.assertNotNull(transactionDAO.fetchTransactionFromId(1234L));
    }

    @Test
    public void fetchTransactionFromIdEmpty() throws TransactionException {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertNull(transactionDAO.fetchTransactionFromId(1234L));
    }

    @Test
    public void fetchTransactionFromIdFailure() {
        when(transactionRepository.findById(anyLong())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(TransactionException.class, () -> transactionDAO.fetchTransactionFromId(1234L));
    }

    @Test
    public void updateTransactionSuccess() {
        when(transactionRepository.findById(null)).thenReturn(Optional.of(mockTransaction()));
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(mockCustomer()));
        when(makeRepository.getOne(anyLong())).thenReturn(mockMake());
        when(modelRepository.getOne(anyLong())).thenReturn(mockModel());
        when(transactionRepository.save(any())).thenReturn(mockTransaction());
        Assertions.assertAll(() -> transactionDAO.updateTransaction(new TransactionVO()));
    }

    @Test
    public void updateTransactionEmpty() {
        when(transactionRepository.findById(null)).thenReturn(Optional.empty());
        Assertions.assertAll(() -> transactionDAO.updateTransaction(new TransactionVO()));
    }

    @Test
    public void updateTransactionFailure() {
        when(transactionRepository.findById(null)).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(TransactionException.class,
                () -> transactionDAO.updateTransaction(new TransactionVO()));
    }

    @Test
    public void deleteTransactionSuccess() {
        Assertions.assertAll(() -> transactionDAO.deleteTransaction(1234L));
    }

    @Test
    public void deleteTransactionFailure() {
        doThrow(new CannotAcquireLockException("DB error")).when(transactionRepository).deleteById(anyLong());
        Assertions.assertThrows(TransactionException.class, () -> transactionDAO.deleteTransaction(1234L));
    }

    @Test
    public void fetchTransactionFromTagSuccess() throws TransactionException {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(mockCustomer()));
        when(customerAdditionalDetailsRepository.findByCustomerId(anyLong()))
                .thenReturn(Optional.of(mockCustomerAdditionalDetails()));
        when(makeRepository.getOne(anyLong())).thenReturn(mockMake());
        when(modelRepository.getOne(anyLong())).thenReturn(mockModel());
        when(transactionRepository.findBytagno(anyString())).thenReturn(mockTransaction());
        Assertions.assertNotNull(transactionDAO.fetchTransactionFromTag("ABC"));
    }

    @Test
    public void fetchTransactionFromTagFailure() {
        when(transactionRepository.findBytagno(anyString())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(TransactionException.class, () -> transactionDAO.fetchTransactionFromTag("ABC"));
    }

    @Test
    public void updateTransactionStatusSuccess() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(mockTransaction()));
        Assertions.assertAll(() -> transactionDAO.updateTransactionStatus(1234L, "SUCCESS"));
    }

    @Test
    public void updateTransactionStatusEmpty() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertAll(() -> transactionDAO.updateTransactionStatus(1234L, "SUCCESS"));
    }

    @Test
    public void updateTransactionStatusFailure() {
        when(transactionRepository.findById(anyLong())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(TransactionException.class,
                () -> transactionDAO.updateTransactionStatus(1234L, "SUCCESS"));
    }

    @Test
    public void listAllTransactionsSuccess() throws TransactionException {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(mockCustomer()));
        when(makeRepository.getOne(anyLong())).thenReturn(mockMake());
        when(modelRepository.getOne(anyLong())).thenReturn(mockModel());
        when(transactionRepository.findAll()).thenReturn(mockListOfTransactions());
        Assertions.assertNotNull(transactionDAO.listAllTransactions());
    }

    @Test
    public void listAllTransactionsFailure() {
        when(transactionRepository.findAll()).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(TransactionException.class, transactionDAO::listAllTransactions);
    }

    private List<Transaction> mockListOfTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(mockTransaction());
        return transactions;
    }

    private Transaction mockTransaction() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1234L);
        transaction.setTagno("ABC");
        transaction.setDateReported(OffsetDateTime.now(ZoneId.systemDefault()));
        transaction.setProductCategory("ABC");
        transaction.setCustomerId(1234L);
        transaction.setMakeId(1234L);
        transaction.setModelId(1234L);
        transaction.setSerialNumber("ABC");
        transaction.setStatus("PENDING");
        transaction.setAccessories("ABC");
        addAdditionalRemarks(transaction);
        return transaction;
    }

    private void addAdditionalRemarks(final Transaction transaction) {
        transaction.setComplaintReported("ABC");
        transaction.setComplaintDiagnosed("ABC");
        transaction.setEngineerRemarks("ABC");
        transaction.setRepairAction("ABC");
        transaction.setNote("ABC");
    }

    private Customer mockCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(1234L);
        customer.setName("ABC");
        return customer;
    }

    private CustomerAdditionalDetails mockCustomerAdditionalDetails() {
        CustomerAdditionalDetails additionalDetails = new CustomerAdditionalDetails();
        additionalDetails.setNote("ABC");
        additionalDetails.setContactPerson1("ABC");
        additionalDetails.setContactPhone1("12321323");
        additionalDetails.setContactPerson2("ABC");
        additionalDetails.setContactPhone2("12321323");
        return additionalDetails;
    }

    private Make mockMake() {
        Make make = new Make();
        make.setMakeId(1234L);
        make.setMakeName("ABC");
        return make;
    }

    private Model mockModel() {
        Model model = new Model();
        model.setModelId(1234L);
        model.setModelName("ABC");
        return model;
    }
}