package com.poseidon.transaction.dao.impl;

import com.poseidon.transaction.dao.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository  extends JpaRepository<Transaction, Long> {
}
