package com.poseidon.invoice.service.impl;

import com.poseidon.invoice.dao.InvoiceDAO;
import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.invoice.exception.InvoiceException;
import com.poseidon.invoice.service.InvoiceService;
import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.exception.TransactionException;
import com.poseidon.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * user: Suraj
 * Date: 7/26/12
 * Time: 10:38 PM
 */
public class InvoiceServiceImpl implements InvoiceService {
    private static final Logger LOG = LoggerFactory.getLogger(InvoiceServiceImpl.class);
    private InvoiceDAO invoiceDAO;
    private InvoiceService invoiceService;
    private TransactionService transactionService;

    public void setInvoiceService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void setInvoiceDAO(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    public void addInvoice(InvoiceVO currentInvoiceVO) throws InvoiceException {
        TransactionReportVO transactionReportVO = null;
        try {
            transactionReportVO = transactionService.fetchTransactionFromTag(currentInvoiceVO.getTagNo());
        } catch (TransactionException e) {
            e.printStackTrace();
        }
        currentInvoiceVO.setCustomerId(transactionReportVO.getCustomerId());
        currentInvoiceVO.setCustomerName(transactionReportVO.getCustomerName());
        currentInvoiceVO.setSerialNo(transactionReportVO.getSerialNo());
        try {
            invoiceDAO.addInvoice(currentInvoiceVO);
        } catch (InvoiceException e) {
            LOG.error(e.getLocalizedMessage());
            throw new InvoiceException(e.getMessage());
        }
    }

    public List<InvoiceVO> fetchInvoiceForListOfTransactions() throws InvoiceException {
        List<TransactionVO> transactionVOs = null;
        try {
            transactionVOs = transactionService.listTodaysTransactions();
        } catch (TransactionException e) {
            e.printStackTrace();
        }
        List<String> tagNumbers = fetchTagNoFromListOfTransactionVOs(transactionVOs);
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = invoiceDAO.fetchInvoiceForListOfTransactions(tagNumbers);
        } catch (InvoiceException e) {
            LOG.error(e.getLocalizedMessage());
            throw new InvoiceException(e.getMessage());
        }
        return invoiceVOs;
    }

    private List<String> fetchTagNoFromListOfTransactionVOs(List<TransactionVO> transactionVOs) {
        List<String> tagNumbers = new ArrayList<>();
        for(TransactionVO transactionVO:transactionVOs){
            tagNumbers.add(transactionVO.getTagNo());
        }
        return tagNumbers;
    }


    public InvoiceVO fetchInvoiceVOFromId(Long id) throws InvoiceException {
        InvoiceVO invoiceVO;
        try {
            invoiceVO = invoiceDAO.fetchInvoiceVOFromId(id);
        } catch (InvoiceException e) {
            LOG.error(e.getLocalizedMessage());
            throw new InvoiceException(e.getMessage());
        }
        return invoiceVO;
    }

    public void deleteInvoice(Long id) throws InvoiceException {
        try {
            invoiceDAO.deleteInvoice(id);
        } catch (InvoiceException e) {
            LOG.error(e.getLocalizedMessage());
            throw new InvoiceException(e.getMessage());
        }
    }

    public void updateInvoice(InvoiceVO currentInvoiceVO) throws InvoiceException {
        TransactionReportVO transactionReportVO = null;
        try {
            transactionReportVO = transactionService.fetchTransactionFromTag(currentInvoiceVO.getTagNo());
        } catch (TransactionException e) {
            e.printStackTrace();
        }
        currentInvoiceVO.setCustomerId(transactionReportVO.getCustomerId());
        currentInvoiceVO.setCustomerName(transactionReportVO.getCustomerName());
        currentInvoiceVO.setSerialNo(transactionReportVO.getSerialNo());
        try {
            invoiceDAO.updateInvoice(currentInvoiceVO);
        } catch (InvoiceException e) {
            e.printStackTrace();
            throw new InvoiceException(e.getMessage());
        }
    }

    public List<InvoiceVO> findInvoices(InvoiceVO searchInvoiceVO) throws InvoiceException {
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = invoiceDAO.findInvoices(searchInvoiceVO);
        } catch (InvoiceException e) {
            LOG.error(e.getLocalizedMessage());
            throw new InvoiceException(e.getMessage());
        }
        return invoiceVOs;
    }
}
