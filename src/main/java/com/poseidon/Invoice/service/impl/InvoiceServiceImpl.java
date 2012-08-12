package com.poseidon.Invoice.service.impl;

import com.poseidon.Invoice.dao.InvoiceDAO;
import com.poseidon.Invoice.domain.InvoiceVO;
import com.poseidon.Invoice.exception.InvoiceException;
import com.poseidon.Invoice.service.InvoiceService;
import com.poseidon.Transaction.domain.TransactionVO;

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

    public void addInvoice(InvoiceVO currentInvoiceVO) throws InvoiceException {
        try{
            getInvoiceDAO().addInvoice(currentInvoiceVO);
        }catch (InvoiceException e){
            e.printStackTrace();
            throw new InvoiceException(e.getMessage());
        }
    }

    public List<InvoiceVO> fetchInvoiceForListOfTransactions(List<String> tagNumbers) throws InvoiceException {
        List<InvoiceVO> invoiceVOs = null;
        try{
            invoiceVOs = getInvoiceDAO().fetchInvoiceForListOfTransactions(tagNumbers);
        }catch (InvoiceException e){
            e.printStackTrace();
            throw new InvoiceException(e.getMessage());
        }
        return invoiceVOs;
    }

    public InvoiceVO fetchInvoiceVOFromId(Long id) throws InvoiceException {
        InvoiceVO invoiceVO = null;
        try{
            invoiceVO = getInvoiceDAO().fetchInvoiceVOFromId(id);
        }catch (InvoiceException e){
            e.printStackTrace();
            throw new InvoiceException(e.getMessage());
        }
        return invoiceVO;
    }

    public void deleteInvoice(Long id) throws InvoiceException {
        try{
            getInvoiceDAO().deleteInvoice(id);
        }catch (InvoiceException e){
            e.printStackTrace();
            throw new InvoiceException(e.getMessage());
        }
    }

    public void updateInvoice(InvoiceVO currentInvoiceVO) throws InvoiceException {
        try{
            getInvoiceDAO().updateInvoice(currentInvoiceVO);
        }catch (InvoiceException e){
            e.printStackTrace();
            throw new InvoiceException(e.getMessage());
        }
    }

    public List<InvoiceVO> findInvoices(InvoiceVO searchInvoiceVO) throws InvoiceException {
        List<InvoiceVO> invoiceVOs = null;
        try{
            invoiceVOs = getInvoiceDAO().findInvoices(searchInvoiceVO);
        }catch (InvoiceException e){
            e.printStackTrace();
            throw new InvoiceException(e.getMessage());
        }
        return invoiceVOs;
    }
}
