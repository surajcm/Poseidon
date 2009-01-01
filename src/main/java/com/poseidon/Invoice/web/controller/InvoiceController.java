package com.poseidon.Invoice.web.controller;

import com.poseidon.Invoice.delegate.InvoiceDelegate;
import com.poseidon.Invoice.web.form.InvoiceForm;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Suraj
 * Date: 7/26/12
 * Time: 9:56 PM
 */
public class InvoiceController extends MultiActionController {
    private InvoiceDelegate invoiceDelegate;

    public InvoiceDelegate getInvoiceDelegate() {
        return invoiceDelegate;
    }

    public void setInvoiceDelegate(InvoiceDelegate invoiceDelegate) {
        this.invoiceDelegate = invoiceDelegate;
    }

    public ModelAndView ListInvoice(HttpServletRequest request,
                                    HttpServletResponse response){
        InvoiceForm invoiceForm = new InvoiceForm();
        return new ModelAndView("invoice/ListInvoice", "invoiceForm", invoiceForm);
    }
}
