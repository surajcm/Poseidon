package com.poseidon.invoice.dao;

import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.invoice.exception.InvoiceException;

import java.util.List;

public interface InvoiceDAO {
    void addInvoice(InvoiceVO currentInvoiceVO) throws InvoiceException;

    List<InvoiceVO> fetchInvoiceForListOfTransactions(List<String> tagNumbers) throws InvoiceException;

    InvoiceVO fetchInvoiceVOFromId(Long id) throws InvoiceException;

    InvoiceVO fetchInvoiceVOFromTagNo(final String tagNo) throws InvoiceException;

    void deleteInvoice(Long id) throws InvoiceException;

    void updateInvoice(InvoiceVO currentInvoiceVO) throws InvoiceException;

    List<InvoiceVO> findInvoices(InvoiceVO searchInvoiceVO) throws InvoiceException;
}
