package com.poseidon.invoice.service.impl;

import com.poseidon.invoice.dao.InvoiceDAO;
import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.invoice.exception.InvoiceException;
import com.poseidon.invoice.service.InvoiceService;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.exception.TransactionException;
import com.poseidon.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private static final Logger LOG = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    @Autowired
    private InvoiceDAO invoiceDAO;

    @Autowired
    private TransactionService transactionService;

    /**
     * add invoice.
     *
     * @param currentInvoiceVO currentInvoiceVO
     * @throws InvoiceException on error
     */
    @Override
    public void addInvoice(final InvoiceVO currentInvoiceVO) throws InvoiceException {
        try {
            var transactionReportVO = transactionService
                    .fetchTransactionFromTag(currentInvoiceVO.getTagNo());
            currentInvoiceVO.setCustomerId(transactionReportVO.getCustomerId());
            currentInvoiceVO.setCustomerName(transactionReportVO.getCustomerName());
            currentInvoiceVO.setSerialNo(transactionReportVO.getSerialNo());
            invoiceDAO.addInvoice(currentInvoiceVO);
        } catch (TransactionException | InvoiceException ex) {
            LOG.error("Error occurred ", ex);
            throw new InvoiceException(ex);
        }
    }

    /**
     * fetch Invoice For List Of Transactions.
     *
     * @return list of invoice vo
     * @throws InvoiceException on error
     */
    @Override
    public List<InvoiceVO> fetchInvoiceForListOfTransactions() throws InvoiceException {
        List<InvoiceVO> invoiceVOs = null;
        var transactionVOs = getTransactionVOS();
        if (transactionVOs != null) {
            var tagNumbers = fetchTagNoFromListOfTransactionVOs(transactionVOs);
            try {
                invoiceVOs = invoiceDAO.fetchInvoiceForListOfTransactions(tagNumbers);
            } catch (InvoiceException ex) {
                LOG.error(ex.getLocalizedMessage(), ex);
                throw new InvoiceException(ex);
            }
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
     * @throws InvoiceException on error
     */
    @Override
    public InvoiceVO fetchInvoiceVOFromId(final Long id) throws InvoiceException {
        InvoiceVO invoiceVO;
        try {
            invoiceVO = invoiceDAO.fetchInvoiceVOFromId(id);
        } catch (InvoiceException ex) {
            LOG.error(ex.getLocalizedMessage(), ex);
            throw new InvoiceException(ex);
        }
        return invoiceVO;
    }

    /**
     * fetch InvoiceVO From Id.
     *
     * @param tagNo tagNo
     * @return InvoiceVO
     * @throws InvoiceException on error
     */
    @Override
    public InvoiceVO fetchInvoiceVOFromTagNo(final String tagNo) throws InvoiceException {
        InvoiceVO invoiceVO;
        try {
            invoiceVO = invoiceDAO.fetchInvoiceVOFromTagNo(tagNo);
        } catch (InvoiceException ex) {
            LOG.error(ex.getLocalizedMessage(), ex);
            throw new InvoiceException(ex);
        }
        return invoiceVO;
    }

    /**
     * delete Invoice.
     *
     * @param id id
     * @throws InvoiceException on error
     */
    @Override
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
    @Override
    public void updateInvoice(final InvoiceVO currentInvoiceVO) throws InvoiceException {
        try {
            var transactionReportVo = transactionService
                    .fetchTransactionFromTag(currentInvoiceVO.getTagNo());
            currentInvoiceVO.setCustomerId(transactionReportVo.getCustomerId());
            currentInvoiceVO.setCustomerName(transactionReportVo.getCustomerName());
            currentInvoiceVO.setSerialNo(transactionReportVo.getSerialNo());
            invoiceDAO.updateInvoice(currentInvoiceVO);
        } catch (TransactionException | InvoiceException ex) {
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
    @Override
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
