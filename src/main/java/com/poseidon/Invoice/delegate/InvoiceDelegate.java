package com.poseidon.Invoice.delegate;

import com.poseidon.Invoice.service.InvoiceService;

/**
 * User: Suraj
 * Date: 7/26/12
 * Time: 10:32 PM
 */
public class InvoiceDelegate {
    private InvoiceService invoiceService;

    public InvoiceService getInvoiceService() {
        return invoiceService;
    }

    public void setInvoiceService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }
}
