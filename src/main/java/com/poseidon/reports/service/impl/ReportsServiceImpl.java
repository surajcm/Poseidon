package com.poseidon.reports.service.impl;

import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.service.CompanyTermsService;
import com.poseidon.invoice.domain.InvoiceReportVO;
import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.invoice.exception.InvoiceException;
import com.poseidon.invoice.service.InvoiceService;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.service.MakeService;
import com.poseidon.reports.dao.ReportsDAO;
import com.poseidon.reports.domain.ReportsVO;
import com.poseidon.reports.exception.ReportsException;
import com.poseidon.reports.service.ReportsService;
import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.exception.TransactionException;
import com.poseidon.transaction.service.TransactionService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * user: Suraj.
 * Date: Jun 3, 2012
 * Time: 10:40:26 AM
 */
@Service
public class ReportsServiceImpl implements ReportsService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportsServiceImpl.class);
    private final ReportsDAO reportsDAO;
    private final MakeService makeService;
    private final TransactionService transactionService;
    private final CompanyTermsService companyTermsService;
    private final InvoiceService invoiceService;

    public ReportsServiceImpl(final ReportsDAO reportsDAO,
                              final MakeService makeService,
                              final TransactionService transactionService,
                              final CompanyTermsService companyTermsService,
                              final InvoiceService invoiceService) {
        this.reportsDAO = reportsDAO;
        this.makeService = makeService;
        this.transactionService = transactionService;
        this.companyTermsService = companyTermsService;
        this.invoiceService = invoiceService;
    }

    /**
     * daily report.
     *
     * @return list of reports
     */
    @Override
    public List<ReportsVO> generateDailyReport() {
        List<ReportsVO> reportsVOs = null;
        try {
            reportsVOs = reportsDAO.generateDailyReport();
        } catch (ReportsException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return reportsVOs;
    }

    /**
     * make details chart.
     *
     * @param jasperReport  jasperReport
     * @param currentReport currentReport
     * @return JasperPrint
     */
    @Override
    public JasperPrint getMakeDetailsChart(final JasperReport jasperReport, final ReportsVO currentReport) {
        currentReport.setMakeVOList(makeService.fetchMakes());
        var jasperPrint = new JasperPrint();
        try {
            jasperPrint = reportsDAO.getMakeDetailsChart(jasperReport, currentReport);
        } catch (JRException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return jasperPrint;
    }

    /**
     * call report.
     *
     * @param jasperReport  jasperReport
     * @param currentReport currentReport
     * @return JasperPrint
     */
    @Override
    public JasperPrint getCallReport(final JasperReport jasperReport, final ReportsVO currentReport) {
        var transactionVO = getTransactionReportVO(currentReport.getTagNo());
        currentReport.setTransactionReportVO(transactionVO);
        var jasperPrint = new JasperPrint();
        CompanyTermsVO companyTerms = companyTerms();
        try {
            jasperPrint = reportsDAO.getCallReport(jasperReport, currentReport, companyTerms);
        } catch (JRException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return jasperPrint;
    }

    private CompanyTermsVO companyTerms() {
        var companyTermsVO = companyTermsService.listCompanyTerms();
        CompanyTermsVO companyTerms = null;
        if (companyTermsVO.isPresent()) {
            companyTerms = companyTermsVO.get();
        }
        return companyTerms;
    }

    private TransactionReportVO getTransactionReportVO(final String tagNo) {
        TransactionReportVO transactionVO = null;
        try {
            transactionVO = transactionService.fetchTransactionFromTag(tagNo);
        } catch (TransactionException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return transactionVO;
    }

    /**
     * transaction list report.
     *
     * @param jasperReport      jasperReport
     * @param currentReport     currentReport
     * @param searchTransaction searchTransaction
     * @return JasperPrint
     */
    @Override
    public JasperPrint getTransactionsListReport(final JasperReport jasperReport,
                                                 final ReportsVO currentReport,
                                                 final TransactionVO searchTransaction) {
        currentReport.setTransactionsList(getTransactionVOS(searchTransaction));
        var jasperPrint = new JasperPrint();
        try {
            jasperPrint = reportsDAO.getTransactionsListReport(jasperReport, currentReport);
        } catch (JRException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return jasperPrint;
    }

    private List<TransactionVO> getTransactionVOS(final TransactionVO searchTransaction) {
        List<TransactionVO> transactions = null;
        try {
            transactions = transactionService.searchTransactions(searchTransaction);
        } catch (TransactionException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return transactions;
    }

    /**
     * model list report.
     *
     * @param jasperReport         jasperReport
     * @param currentReport        currentReport
     * @param searchMakeAndModelVO searchMakeAndModelVO
     * @return JasperPrint
     */
    @Override
    public JasperPrint getModelListReport(final JasperReport jasperReport,
                                          final ReportsVO currentReport,
                                          final MakeAndModelVO searchMakeAndModelVO) {
        currentReport.setMakeAndModelVOs(makeService.searchMakeVOs(searchMakeAndModelVO));
        var jasperPrint = new JasperPrint();
        try {
            jasperPrint = reportsDAO.getModelListReport(jasperReport, currentReport);
        } catch (JRException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return jasperPrint;
    }

    /**
     * error report.
     *
     * @param jasperReport  jasperReport
     * @param currentReport currentReport
     * @return JasperPrint
     */
    @Override
    public JasperPrint getErrorReport(final JasperReport jasperReport, final ReportsVO currentReport) {
        var jasperPrint = new JasperPrint();
        try {
            jasperPrint = reportsDAO.getErrorReport(jasperReport, currentReport);
        } catch (JRException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return jasperPrint;
    }

    /**
     * invoice report.
     *
     * @param jasperReport  jasperReport
     * @param currentReport currentReport
     * @return JasperPrint
     */
    @Override
    public JasperPrint getInvoiceReport(final JasperReport jasperReport, final ReportsVO currentReport) {
        var transaction = getTransactionReportVO(currentReport.getTagNo());
        var invoiceVO = getInvoiceVOFromTag(transaction);
        if (invoiceVO != null) {
            currentReport.setInvoiceReportVO(getInvoiceReportVO(transaction, invoiceVO));
        }
        var jasperPrint = new JasperPrint();
        try {
            jasperPrint = reportsDAO.getInvoiceReport(jasperReport, currentReport);
        } catch (JRException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return jasperPrint;
    }

    private InvoiceVO getInvoiceVOFromTag(final TransactionReportVO transaction) {
        InvoiceVO invoiceVO = null;
        if (transaction != null) {
            invoiceVO = getInvoiceVOs(transaction.getTagNo());
        }
        return invoiceVO;
    }

    private InvoiceReportVO getInvoiceReportVO(final TransactionReportVO transactionVO, final InvoiceVO invoiceVO) {
        var invoiceReportVO = new InvoiceReportVO();
        updateInvoiceInfo(invoiceReportVO, invoiceVO);
        updateTransactionInfo(invoiceReportVO, transactionVO);
        updateCompanyInfo(invoiceReportVO);
        return invoiceReportVO;
    }

    private void updateInvoiceInfo(final InvoiceReportVO invoiceReportVO, final InvoiceVO invoiceVO) {
        invoiceReportVO.setInvoiceId(invoiceVO.getId());
        invoiceReportVO.setDescription(invoiceVO.getDescription());
        invoiceReportVO.setSerialNo(invoiceVO.getSerialNo());
        invoiceReportVO.setQuantity(invoiceVO.getQuantity());
        invoiceReportVO.setRate(invoiceVO.getRate());
        invoiceReportVO.setAmount(invoiceVO.getAmount());
        invoiceReportVO.setTotalAmount(invoiceVO.getAmount());
    }

    private void updateTransactionInfo(final InvoiceReportVO invoiceReportVO, final TransactionReportVO transactionVO) {
        invoiceReportVO.setTagNo(transactionVO.getTagNo());
        invoiceReportVO.setCustomerId(transactionVO.getCustomerId());
        invoiceReportVO.setCustomerName(transactionVO.getCustomerName());
        invoiceReportVO.setCustomerAddress(transactionVO.getAddress());
    }

    private void updateCompanyInfo(final InvoiceReportVO invoiceReportVO) {
        var companyTermsVO = companyTermsService.listCompanyTerms();
        if (companyTermsVO.isPresent()) {
            invoiceReportVO.setCompanyName(companyTermsVO.get().getCompanyName());
            invoiceReportVO.setCompanyAddress(companyTermsVO.get().getCompanyAddress());
            invoiceReportVO.setCompanyPhoneNumber(companyTermsVO.get().getCompanyPhoneNumber());
            invoiceReportVO.setCompanyWebsite(companyTermsVO.get().getCompanyWebsite());
            invoiceReportVO.setCompanyEmail(companyTermsVO.get().getCompanyEmail());
            invoiceReportVO.setCompanyTerms(companyTermsVO.get().getCompanyTerms());
            invoiceReportVO.setCompanyVatTin(companyTermsVO.get().getCompanyVatTin());
            invoiceReportVO.setCompanyCstTin(companyTermsVO.get().getCompanyCstTin());
        }
    }

    private InvoiceVO getInvoiceVOs(final String tagNo) {
        InvoiceVO firstInvoice = null;
        try {
            firstInvoice = invoiceService.fetchInvoiceVOFromTagNo(tagNo);
        } catch (InvoiceException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return firstInvoice;
    }
}
