package com.poseidon.invoice.service;

import com.poseidon.invoice.dao.InvoiceDAO;
import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    private static final Logger log = LoggerFactory.getLogger(InvoiceService.class);

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
     */
    public Long addInvoice(final InvoiceVO currentInvoiceVO) {
        var transactionReportVO = transactionService
                .fetchTransactionFromTag(currentInvoiceVO.getTagNo());
        currentInvoiceVO.setTransactionId(transactionReportVO.getId());
        currentInvoiceVO.setCustomerId(transactionReportVO.getCustomerId());
        currentInvoiceVO.setCustomerName(transactionReportVO.getCustomerName());
        currentInvoiceVO.setSerialNo(transactionReportVO.getSerialNo());
        invoiceDAO.addInvoice(currentInvoiceVO);
        return transactionReportVO.getId();
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
            log.info("tag numbers are : {}", tagNumbers);
            invoiceVOs = invoiceDAO.fetchInvoiceForListOfTransactions(tagNumbers);
            log.info("invoice vos count : {}", invoiceVOs.size());
        }
        return invoiceVOs;
    }

    private List<TransactionVO> getTransactionVOS() {
        return transactionService.listAllTransactions();
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
     */
    public void deleteInvoice(final Long id) {
        invoiceDAO.deleteInvoice(id);
    }

    /**
     * update Invoice.
     *
     * @param currentInvoiceVO currentInvoiceVO
     */
    public void updateInvoice(final InvoiceVO currentInvoiceVO) {
        var transactionReportVo = transactionService
                .fetchTransactionFromTag(currentInvoiceVO.getTagNo());
        currentInvoiceVO.setCustomerId(transactionReportVo.getCustomerId());
        currentInvoiceVO.setCustomerName(transactionReportVo.getCustomerName());
        currentInvoiceVO.setSerialNo(transactionReportVo.getSerialNo());
        invoiceDAO.updateInvoice(currentInvoiceVO);
    }

    /**
     * findInvoices.
     *
     * @param searchInvoiceVo InvoiceVO
     * @return List of InvoiceVO
     */
    public List<InvoiceVO> findInvoices(final InvoiceVO searchInvoiceVo) {
        return invoiceDAO.findInvoices(searchInvoiceVo);
    }


    public List<String> allTagNumbers() {
        return transactionService.allTagNumbers();
    }
}
