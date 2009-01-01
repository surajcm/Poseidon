package com.poseidon.Invoice.service.impl;

import com.poseidon.Invoice.dao.InvoiceDAO;
import com.poseidon.Invoice.service.InvoiceService;

/**
 * User: Suraj
 * Date: 7/26/12
 * Time: 10:38 PM
 */
public class InvoiceServiceImpl implements InvoiceService {
    private InvoiceDAO invoiceDAO;

    public InvoiceDAO getInvoiceDAO() {
        return invoiceDAO;
    }

    public void setInvoiceDAO(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }
}
