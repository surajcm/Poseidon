package com.poseidon.invoice.dao.impl;

import com.poseidon.invoice.dao.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository  extends JpaRepository<Invoice, Integer> {
}
