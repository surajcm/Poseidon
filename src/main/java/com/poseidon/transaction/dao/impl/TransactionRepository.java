package com.poseidon.transaction.dao.impl;

import com.poseidon.transaction.dao.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository  extends JpaRepository<Transaction, Long> {
    Transaction findBytagno(String tagNo);

    @Query(value = "SELECT distinct txn FROM Transaction txn WHERE txn.dateReported = CURRENT_DATE")
    List<Transaction> todaysTransaction();
}
