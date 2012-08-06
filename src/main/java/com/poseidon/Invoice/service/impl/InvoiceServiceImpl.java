package com.poseidon.Invoice.service.impl;

import com.poseidon.Invoice.dao.InvoiceDAO;
import com.poseidon.Invoice.domain.InvoiceVO;
import com.poseidon.Invoice.exception.InvoiceException;
import com.poseidon.Invoice.service.InvoiceService;

import java.util.List;

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

    public void addInvoice(InvoiceVO currentInvoiceVO) {
        try{
            getInvoiceDAO().addInvoice(currentInvoiceVO);
        }catch (InvoiceException e){
            e.printStackTrace();
        }
    }

    public List<InvoiceVO> fetchInvoiceForListOfTransactions(List<String> tagNumbers) {
        List<InvoiceVO> invoiceVOs = null;
        try{
            invoiceVOs = getInvoiceDAO().fetchInvoiceForListOfTransactions(tagNumbers);
        }catch (InvoiceException e){
            e.printStackTrace();
        }
        return invoiceVOs;
    }
}
