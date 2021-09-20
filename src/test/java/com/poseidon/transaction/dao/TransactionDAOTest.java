package com.poseidon.transaction.dao;

import com.poseidon.customer.dao.entities.Customer;
import com.poseidon.customer.dao.repo.CustomerRepository;
import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.dao.entities.Model;
import com.poseidon.make.dao.repo.MakeRepository;
import com.poseidon.make.dao.repo.ModelRepository;
import com.poseidon.transaction.dao.entities.Transaction;
import com.poseidon.transaction.dao.repo.TransactionRepository;
import com.poseidon.transaction.domain.TransactionVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TransactionDAOTest {
    private final TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
    private final CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
    private final MakeRepository makeRepository = Mockito.mock(MakeRepository.class);
    private final ModelRepository modelRepository = Mockito.mock(ModelRepository.class);
    private final TransactionDAO transactionDAO = new TransactionDAO(
            transactionRepository,
            customerRepository,
            makeRepository,
            modelRepository);

    @Test
    void listTodaysTransactionsSuccess() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(mockCustomer()));
        when(makeRepository.getById(anyLong())).thenReturn(mockMake());
        when(modelRepository.getById(anyLong())).thenReturn(mockModel());
        when(transactionRepository.todaysTransaction()).thenReturn(mockListOfTransactions());
        Assertions.assertNotNull(transactionDAO.listTodaysTransactions());
    }

    @Test
    void saveTransactionSuccess() {
        when(transactionRepository.save(any())).thenReturn(mockTransaction());
        Assertions.assertAll(() -> transactionDAO.saveTransaction(new TransactionVO()));
    }

    @Test
    void fetchTransactionFromIdSuccess() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(mockCustomer()));
        when(makeRepository.getById(anyLong())).thenReturn(mockMake());
        when(modelRepository.getById(anyLong())).thenReturn(mockModel());
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(mockTransaction()));
        Assertions.assertNotNull(transactionDAO.fetchTransactionFromId(1234L));
    }

    @Test
    void fetchTransactionFromIdEmpty() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertNull(transactionDAO.fetchTransactionFromId(1234L));
    }

    @Test
    void updateTransactionSuccess() {
        when(transactionRepository.findById(null)).thenReturn(Optional.of(mockTransaction()));
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(mockCustomer()));
        when(makeRepository.getById(anyLong())).thenReturn(mockMake());
        when(modelRepository.getById(anyLong())).thenReturn(mockModel());
        when(transactionRepository.save(any())).thenReturn(mockTransaction());
        Assertions.assertAll(() -> transactionDAO.updateTransaction(new TransactionVO()));
    }

    @Test
    void updateTransactionEmpty() {
        when(transactionRepository.findById(null)).thenReturn(Optional.empty());
        Assertions.assertAll(() -> transactionDAO.updateTransaction(new TransactionVO()));
    }

    @Test
    void deleteTransactionSuccess() {
        Assertions.assertAll(() -> transactionDAO.deleteTransaction(1234L));
    }

    @Test
    void fetchTransactionFromTagSuccess() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(mockCustomer()));
        when(makeRepository.getById(anyLong())).thenReturn(mockMake());
        when(modelRepository.getById(anyLong())).thenReturn(mockModel());
        when(transactionRepository.findBytagno(anyString())).thenReturn(mockTransaction());
        Assertions.assertNotNull(transactionDAO.fetchTransactionFromTag("ABC"));
    }

    @Test
    void updateTransactionStatusSuccess() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(mockTransaction()));
        Assertions.assertAll(() -> transactionDAO.updateTransactionStatus(1234L, "SUCCESS"));
    }

    @Test
    void updateTransactionStatusEmpty() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertAll(() -> transactionDAO.updateTransactionStatus(1234L, "SUCCESS"));
    }

    @Test
    void listAllTransactionsSuccess() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(mockCustomer()));
        when(makeRepository.getById(anyLong())).thenReturn(mockMake());
        when(modelRepository.getById(anyLong())).thenReturn(mockModel());
        when(transactionRepository.findAll()).thenReturn(mockListOfTransactions());
        Assertions.assertNotNull(transactionDAO.listAllTransactions());
    }

    private List<Transaction> mockListOfTransactions() {
        return List.of(mockTransaction());
    }

    private Transaction mockTransaction() {
        var transaction = new Transaction();
        transaction.setTransactionId(1234L);
        transaction.setTagno("ABC");
        transaction.setDateReported(LocalDateTime.now(ZoneId.systemDefault()));
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
        var customer = new Customer();
        customer.setId(1234L);
        customer.setName("ABC");
        return customer;
    }

    private Make mockMake() {
        var make = new Make();
        make.setMakeId(1234L);
        make.setMakeName("ABC");
        return make;
    }

    private Model mockModel() {
        var model = new Model();
        model.setModelId(1234L);
        model.setModelName("ABC");
        return model;
    }
}