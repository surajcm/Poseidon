package com.poseidon.invoice.dao;

import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.invoice.exception.InvoiceException;

import java.util.List;

/**
 * user: Suraj
 * Date: 7/26/12
 * Time: 10:35 PM
 */
public interface InvoiceDAO {
    void addInvoice(InvoiceVO currentInvoiceVO) throws InvoiceException;

    List<InvoiceVO> fetchInvoiceForListOfTransactions(List<String> tagNumbers) throws InvoiceException;

    InvoiceVO fetchInvoiceVOFromId(Long id) throws InvoiceException ;

    void deleteInvoice(Long id) throws InvoiceException;

    void updateInvoice(InvoiceVO currentInvoiceVO) throws InvoiceException;

    List<InvoiceVO> findInvoices(InvoiceVO searchInvoiceVO) throws InvoiceException;
}
