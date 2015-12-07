package com.poseidon.Invoice.dao;

import com.poseidon.Invoice.domain.InvoiceVO;
import com.poseidon.Invoice.exception.InvoiceException;
import com.poseidon.Transaction.domain.TransactionVO;

import java.util.List;

/**
 * User: Suraj
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
