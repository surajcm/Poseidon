package com.poseidon.invoice.dao;

import com.poseidon.invoice.dao.entities.Invoice;
import com.poseidon.invoice.dao.impl.InvoiceRepository;
import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.invoice.exception.InvoiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class InvoiceDAOTest {
    private final InvoiceRepository invoiceRepository = Mockito.mock(InvoiceRepository.class);
    private final InvoiceDAO invoiceDAO = new InvoiceDAO(invoiceRepository);

    @Test
    void addInvoiceSuccess() {
        when(invoiceRepository.save(any())).thenReturn(mockInvoice());
        Assertions.assertAll(() -> invoiceDAO.addInvoice(new InvoiceVO()));
    }

    @Test
    void addInvoiceOnFailure() {
        when(invoiceRepository.save(any())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(InvoiceException.class, () -> invoiceDAO.addInvoice(new InvoiceVO()));
    }

    @Test
    void fetchInvoiceForListOfTransactionsSuccess() throws InvoiceException {
        when(invoiceRepository.fetchTodaysInvoices(any())).thenReturn(mockListOfInvoices());
        var tagNumbers = List.of("ABC", "CDE");
        Assertions.assertNotNull(invoiceDAO.fetchInvoiceForListOfTransactions(tagNumbers));
    }

    @Test
    void fetchInvoiceForListOfTransactionsFailure() {
        when(invoiceRepository.fetchTodaysInvoices(any())).thenThrow(new CannotAcquireLockException("DB error"));
        var tagNumbers = List.of("ABC", "CDE");
        Assertions.assertThrows(InvoiceException.class,
                () -> invoiceDAO.fetchInvoiceForListOfTransactions(tagNumbers));
    }

    @Test
    void fetchInvoiceVOFromIdSuccess() throws InvoiceException {
        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.of(mockInvoice()));
        Assertions.assertNotNull(invoiceDAO.fetchInvoiceVOFromId(1234L));
    }

    @Test
    void fetchInvoiceVOFromIdFailure() {
        when(invoiceRepository.findById(anyLong())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(InvoiceException.class, () -> invoiceDAO.fetchInvoiceVOFromId(1234L));
    }

    @Test
    void deleteInvoiceSuccess() {
        Assertions.assertAll(() -> invoiceDAO.deleteInvoice(1234L));
    }

    @Test
    void deleteInvoiceFailure() {
        doThrow(new CannotAcquireLockException("DB error")).when(invoiceRepository).deleteById(anyLong());
        Assertions.assertThrows(InvoiceException.class, () -> invoiceDAO.deleteInvoice(1234L));
    }

    @Test
    void updateInvoiceSuccess() {
        when(invoiceRepository.findById(null)).thenReturn(Optional.of(mockInvoice()));
        Assertions.assertAll(() -> invoiceDAO.updateInvoice(new InvoiceVO()));
    }

    @Test
    void updateInvoiceEmpty() {
        when(invoiceRepository.findById(null)).thenReturn(Optional.empty());
        Assertions.assertAll(() -> invoiceDAO.updateInvoice(new InvoiceVO()));
    }

    @Test
    void updateInvoiceFailure() {
        when(invoiceRepository.findById(null)).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(InvoiceException.class, () -> invoiceDAO.updateInvoice(new InvoiceVO()));
    }

    private Invoice mockInvoice() {
        var invoice = new Invoice();
        invoice.setInvoiceId(1234L);
        return invoice;
    }

    private List<Invoice> mockListOfInvoices() {
        return List.of(mockInvoice());
    }

}