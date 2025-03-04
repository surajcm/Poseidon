package com.poseidon.invoice.service;

import com.poseidon.invoice.dao.InvoiceDAO;
import com.poseidon.invoice.dao.entities.Invoice;
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
     * fetch Invoice For List Of Transactions.
     *
     * @return list of invoice vo
     */
    public List<InvoiceVO> fetchInvoiceForListOfTransactions() {
        List<InvoiceVO> invoiceVOs = null;
        var transactionVOs = transactionService.listAllTransactions();
        if (transactionVOs != null) {
            var tagNumbers = fetchTagNoFromListOfTransactionVOs(transactionVOs);
            log.info("tag numbers are : {}", tagNumbers);
            var invoices = invoiceDAO.fetchInvoiceForListOfTransactions(tagNumbers);
            invoiceVOs = invoices.stream().map(this::getInvoiceVoFromInvoice).toList();
            log.info("invoice vos count : {}", invoiceVOs.size());
        }
        return invoiceVOs;
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
        var invoice = convertInvoiceVOToInvoice(currentInvoiceVO);
        invoiceDAO.addInvoice(invoice);
        return transactionReportVO.getId();
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
        var optionalInvoice =  invoiceDAO.fetchInvoiceVOFromId(id);
        return optionalInvoice.map(this::getInvoiceVoFromInvoice);
    }

    /**
     * fetch InvoiceVO From Id.
     *
     * @param tagNo tagNo
     * @return InvoiceVO
     */
    public Optional<InvoiceVO> fetchInvoiceVOFromTagNo(final String tagNo) {
        var optionalInvoice =  invoiceDAO.fetchInvoiceVOFromTagNo(tagNo);
        return optionalInvoice.map(this::getInvoiceVoFromInvoice);
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
        currentInvoiceVO.setTransactionId(transactionReportVo.getId());
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

    private Invoice convertInvoiceVOToInvoice(final InvoiceVO currentInvoiceVO) {
        var invoice = getInvoice(currentInvoiceVO, new Invoice());
        invoice.setCreatedBy(currentInvoiceVO.getCreatedBy());
        return invoice;
    }

    private Invoice getInvoice(final InvoiceVO currentInvoiceVO, final Invoice invoice) {
        invoice.setTagNumber(currentInvoiceVO.getTagNo());
        invoice.setDescription(currentInvoiceVO.getDescription());
        invoice.setSerialNumber(currentInvoiceVO.getSerialNo());
        if (currentInvoiceVO.getAmount() != null) {
            invoice.setAmount(currentInvoiceVO.getAmount().longValue());
        }
        invoice.setQuantity(currentInvoiceVO.getQuantity());
        if (currentInvoiceVO.getRate() != null) {
            invoice.setRate(currentInvoiceVO.getRate().longValue());
        }
        invoice.setCustomerName(currentInvoiceVO.getCustomerName());
        invoice.setCustomerId(currentInvoiceVO.getCustomerId());
        invoice.setTransactionId(currentInvoiceVO.getTransactionId());
        invoice.setModifiedBy(currentInvoiceVO.getModifiedBy());
        return invoice;
    }

    private InvoiceVO getInvoiceVoFromInvoice(final Invoice invoice) {
        var invoiceVO = new InvoiceVO();
        invoiceVO.setId(invoice.getId());
        invoiceVO.setTransactionId(invoice.getTransactionId());
        invoiceVO.setTagNo(invoice.getTagNumber());
        invoiceVO.setCustomerId(invoice.getCustomerId());
        invoiceVO.setCustomerName(invoice.getCustomerName());
        invoiceVO.setDescription(invoice.getDescription());
        invoiceVO.setSerialNo(invoice.getSerialNumber());
        if (invoice.getQuantity() != null) {
            invoiceVO.setQuantity(invoice.getQuantity());
        }
        if (invoice.getRate() != null) {
            invoiceVO.setRate(Double.valueOf(invoice.getRate()));
        }
        if (invoice.getAmount() != null) {
            invoiceVO.setAmount(Double.valueOf(invoice.getAmount()));
        }
        return invoiceVO;
    }
}
