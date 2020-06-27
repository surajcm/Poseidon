package com.poseidon.invoice.dao.impl;

import com.poseidon.invoice.dao.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository  extends JpaRepository<Invoice, Long> {
    @Query("SELECT distinct inv FROM Invoice inv WHERE inv.tagNumber IN (:tags) ")
    List<Invoice> fetchTodaysInvoices(@Param("tags") List<String> tagNumbers);

    Optional<Invoice> findByTagNumber(String tagNo);
}
