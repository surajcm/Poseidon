package com.poseidon.Invoice.service;

import com.poseidon.Invoice.domain.InvoiceVO;

import java.util.List;

/**
 * User: Suraj
 * Date: 7/26/12
 * Time: 10:38 PM
 */
public interface InvoiceService {
    public void addInvoice(InvoiceVO currentInvoiceVO);

    public List<InvoiceVO> fetchInvoiceForListOfTransactions(List<String> tagNumbers);
}
