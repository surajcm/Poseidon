package com.poseidon.invoice.service;

import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.invoice.exception.InvoiceException;

import java.util.List;

public interface InvoiceService {
    void addInvoice(InvoiceVO currentInvoiceVO) throws InvoiceException;

    List<InvoiceVO> fetchInvoiceForListOfTransactions() throws InvoiceException;

    InvoiceVO fetchInvoiceVOFromId(Long id) throws InvoiceException;

    void deleteInvoice(Long id) throws InvoiceException;

    void updateInvoice(InvoiceVO currentInvoiceVO) throws InvoiceException;

    List<InvoiceVO> findInvoices(InvoiceVO searchInvoiceVO) throws InvoiceException;

}
