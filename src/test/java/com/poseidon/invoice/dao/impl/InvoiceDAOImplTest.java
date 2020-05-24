package com.poseidon.invoice.dao.impl;

import com.poseidon.invoice.dao.entities.Invoice;
import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.invoice.exception.InvoiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class InvoiceDAOImplTest {
    private final InvoiceDAOImpl invoiceDAO = new InvoiceDAOImpl();
    private final InvoiceRepository invoiceRepository = Mockito.mock(InvoiceRepository.class);

    @BeforeEach
    public void setup() {
        Whitebox.setInternalState(invoiceDAO, "invoiceRepository", invoiceRepository);
    }

    @Test
    public void addInvoiceSuccess() {
        when(invoiceRepository.save(any())).thenReturn(mockInvoice());
        Assertions.assertAll(() -> invoiceDAO.addInvoice(new InvoiceVO()));
    }

    @Test
    public void addInvoiceOnFailure() {
        when(invoiceRepository.save(any())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(InvoiceException.class, () -> invoiceDAO.addInvoice(new InvoiceVO()));
    }

    @Test
    public void fetchInvoiceForListOfTransactionsSuccess() throws InvoiceException {
        when(invoiceRepository.fetchTodaysInvoices(any())).thenReturn(mockListOfInvoices());
        List<String> tagNumbers = List.of("ABC", "CDE");
        Assertions.assertNotNull(invoiceDAO.fetchInvoiceForListOfTransactions(tagNumbers));
    }

    @Test
    public void fetchInvoiceForListOfTransactionsFailure() {
        when(invoiceRepository.fetchTodaysInvoices(any())).thenThrow(new CannotAcquireLockException("DB error"));
        List<String> tagNumbers = List.of("ABC", "CDE");
        Assertions.assertThrows(InvoiceException.class,
                () -> invoiceDAO.fetchInvoiceForListOfTransactions(tagNumbers));
    }

    @Test
    public void fetchInvoiceVOFromIdSuccess() throws InvoiceException {
        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.of(mockInvoice()));
        Assertions.assertNotNull(invoiceDAO.fetchInvoiceVOFromId(1234L));
    }

    @Test
    public void fetchInvoiceVOFromIdFailure() {
        when(invoiceRepository.findById(anyLong())).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(InvoiceException.class, () -> invoiceDAO.fetchInvoiceVOFromId(1234L));
    }

    @Test
    public void deleteInvoiceSuccess() {
        Assertions.assertAll(() -> invoiceDAO.deleteInvoice(1234L));
    }

    @Test
    public void deleteInvoiceFailure() {
        doThrow(new CannotAcquireLockException("DB error")).when(invoiceRepository).deleteById(anyLong());
        Assertions.assertThrows(InvoiceException.class, () -> invoiceDAO.deleteInvoice(1234L));
    }

    @Test
    public void updateInvoiceSuccess() {
        when(invoiceRepository.findById(null)).thenReturn(Optional.of(mockInvoice()));
        Assertions.assertAll(() -> invoiceDAO.updateInvoice(new InvoiceVO()));
    }

    @Test
    public void updateInvoiceEmpty() {
        when(invoiceRepository.findById(null)).thenReturn(Optional.empty());
        Assertions.assertAll(() -> invoiceDAO.updateInvoice(new InvoiceVO()));
    }

    @Test
    public void updateInvoiceFailure() {
        when(invoiceRepository.findById(null)).thenThrow(new CannotAcquireLockException("DB error"));
        Assertions.assertThrows(InvoiceException.class, () -> invoiceDAO.updateInvoice(new InvoiceVO()));
    }

    private Invoice mockInvoice() {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1234L);
        return invoice;
    }

    private List<Invoice> mockListOfInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(mockInvoice());
        return invoices;
    }

}