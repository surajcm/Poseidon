package com.poseidon.invoice.service.impl;

import com.poseidon.invoice.dao.InvoiceDAO;
import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.invoice.exception.InvoiceException;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.exception.TransactionException;
import com.poseidon.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    private static final Logger LOG = LoggerFactory.getLogger(InvoiceService.class);

    private final InvoiceDAO invoiceDAO;

    private final TransactionService transactionService;

    public InvoiceService(final InvoiceDAO invoiceDAO, final TransactionService transactionService) {
        this.invoiceDAO = invoiceDAO;
        this.transactionService = transactionService;
    }

    /**
     * add invoice.
     *
     * @param currentInvoiceVO currentInvoiceVO
     * @throws InvoiceException on error
     */
    public void addInvoice(final InvoiceVO currentInvoiceVO) throws InvoiceException {
        try {
            var transactionReportVO = transactionService
                    .fetchTransactionFromTag(currentInvoiceVO.getTagNo());
            currentInvoiceVO.setCustomerId(transactionReportVO.getCustomerId());
            currentInvoiceVO.setCustomerName(transactionReportVO.getCustomerName());
            currentInvoiceVO.setSerialNo(transactionReportVO.getSerialNo());
            invoiceDAO.addInvoice(currentInvoiceVO);
        } catch (TransactionException ex) {
            LOG.error("Error occurred ", ex);
            throw new InvoiceException(ex);
        }
    }

    /**
     * fetch Invoice For List Of Transactions.
     *
     * @return list of invoice vo
     */
    public List<InvoiceVO> fetchInvoiceForListOfTransactions() {
        List<InvoiceVO> invoiceVOs = null;
        var transactionVOs = getTransactionVOS();
        if (transactionVOs != null) {
            var tagNumbers = fetchTagNoFromListOfTransactionVOs(transactionVOs);
            invoiceVOs = invoiceDAO.fetchInvoiceForListOfTransactions(tagNumbers);
        }
        return invoiceVOs;
    }

    private List<TransactionVO> getTransactionVOS() {
        List<TransactionVO> transactionVOs = null;
        try {
            transactionVOs = transactionService.listTodaysTransactions();
        } catch (TransactionException ex) {
            LOG.error(ex.getLocalizedMessage(), ex);
        }
        return transactionVOs;
    }

    private List<String> fetchTagNoFromListOfTransactionVOs(final List<TransactionVO> transactionVOs) {
        return transactionVOs.stream().map(TransactionVO::getTagNo).toList();
    }


    /**
     * fetch InvoiceVO From Id.
     *
     * @param id id
     * @return InvoiceVO
     */
    public Optional<InvoiceVO> fetchInvoiceVOFromId(final Long id) {
        return invoiceDAO.fetchInvoiceVOFromId(id);
    }

    /**
     * fetch InvoiceVO From Id.
     *
     * @param tagNo tagNo
     * @return InvoiceVO
     */
    public Optional<InvoiceVO> fetchInvoiceVOFromTagNo(final String tagNo) {
        return invoiceDAO.fetchInvoiceVOFromTagNo(tagNo);
    }

    /**
     * delete Invoice.
     *
     * @param id id
     * @throws InvoiceException on error
     */
    public void deleteInvoice(final Long id) throws InvoiceException {
        try {
            invoiceDAO.deleteInvoice(id);
        } catch (InvoiceException ex) {
            LOG.error(ex.getLocalizedMessage(), ex);
            throw new InvoiceException(ex);
        }
    }

    /**
     * update Invoice.
     *
     * @param currentInvoiceVO currentInvoiceVO
     * @throws InvoiceException on error
     */
    public void updateInvoice(final InvoiceVO currentInvoiceVO) throws InvoiceException {
        try {
            var transactionReportVo = transactionService
                    .fetchTransactionFromTag(currentInvoiceVO.getTagNo());
            currentInvoiceVO.setCustomerId(transactionReportVo.getCustomerId());
            currentInvoiceVO.setCustomerName(transactionReportVo.getCustomerName());
            currentInvoiceVO.setSerialNo(transactionReportVo.getSerialNo());
            invoiceDAO.updateInvoice(currentInvoiceVO);
        } catch (TransactionException ex) {
            LOG.error(ex.getLocalizedMessage(), ex);
            throw new InvoiceException(ex);
        }
    }

    /**
     * findInvoices.
     *
     * @param searchInvoiceVo InvoiceVO
     * @return List of InvoiceVO
     * @throws InvoiceException on error
     */
    public List<InvoiceVO> findInvoices(final InvoiceVO searchInvoiceVo) throws InvoiceException {
        List<InvoiceVO> invoiceVOs;
        try {
            invoiceVOs = invoiceDAO.findInvoices(searchInvoiceVo);
        } catch (InvoiceException ex) {
            LOG.error(ex.getLocalizedMessage(), ex);
            throw new InvoiceException(ex);
        }
        return invoiceVOs;
    }
}
