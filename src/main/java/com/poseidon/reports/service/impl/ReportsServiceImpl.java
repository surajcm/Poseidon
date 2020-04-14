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
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ReportsDAO reportsDAO;
    @Autowired
    private MakeService makeService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CompanyTermsService companyTermsService;
    @Autowired
    private InvoiceService invoiceService;

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
        JasperPrint jasperPrint = new JasperPrint();
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
    public JasperPrint getCallReport(final JasperReport jasperReport,
                                     final ReportsVO currentReport) {
        CompanyTermsVO companyTermsVO = companyTermsService.listCompanyTerms();
        TransactionReportVO transactionVO = null;
        try {
            transactionVO = transactionService.fetchTransactionFromTag(currentReport.getTagNo());
        } catch (TransactionException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        currentReport.setTransactionReportVO(transactionVO);
        JasperPrint jasperPrint = new JasperPrint();
        try {
            jasperPrint = reportsDAO.getCallReport(jasperReport,
                    currentReport,
                    companyTermsVO);
        } catch (JRException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return jasperPrint;
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
        try {
            currentReport.setTransactionsList(transactionService.searchTransactions(searchTransaction));
        } catch (TransactionException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        JasperPrint jasperPrint = new JasperPrint();
        try {
            jasperPrint = reportsDAO.getTransactionsListReport(jasperReport, currentReport);
        } catch (JRException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return jasperPrint;
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
        JasperPrint jasperPrint = new JasperPrint();
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
        JasperPrint jasperPrint = new JasperPrint();
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
        try {
            InvoiceReportVO invoiceReportVO = new InvoiceReportVO();
            CompanyTermsVO companyTermsVO = companyTermsService.listCompanyTerms();
            TransactionReportVO transactionVO = transactionService.fetchTransactionFromTag(currentReport.getTagNo());
            if (transactionVO != null) {
                InvoiceVO searchInvoiceVO = new InvoiceVO();
                searchInvoiceVO.setTagNo(transactionVO.getTagNo());
                searchInvoiceVO.setStartsWith(Boolean.FALSE);
                searchInvoiceVO.setIncludes(Boolean.FALSE);
                List<InvoiceVO> invoiceVOs = invoiceService.findInvoices(searchInvoiceVO);
                if (invoiceVOs != null && !invoiceVOs.isEmpty()) {
                    InvoiceVO invoiceVO = invoiceVOs.get(0);
                    invoiceReportVO.setInvoiceId(invoiceVO.getId());
                    invoiceReportVO.setTagNo(transactionVO.getTagNo());
                    invoiceReportVO.setCustomerId(transactionVO.getCustomerId());
                    invoiceReportVO.setCustomerName(transactionVO.getCustomerName());
                    invoiceReportVO.setCustomerAddress1(transactionVO.getAddress1());
                    invoiceReportVO.setCustomerAddress2(transactionVO.getAddress2());
                    invoiceReportVO.setDescription(invoiceVO.getDescription());
                    invoiceReportVO.setSerialNo(invoiceVO.getSerialNo());
                    invoiceReportVO.setQuantity(invoiceVO.getQuantity());
                    invoiceReportVO.setRate(invoiceVO.getRate());
                    invoiceReportVO.setAmount(invoiceVO.getAmount());
                    invoiceReportVO.setTotalAmount(invoiceVO.getAmount());
                }
            }
            if (companyTermsVO != null) {
                invoiceReportVO.setCompanyName(companyTermsVO.getCompanyName());
                invoiceReportVO.setCompanyAddress(companyTermsVO.getCompanyAddress());
                invoiceReportVO.setCompanyPhoneNumber(companyTermsVO.getCompanyPhoneNumber());
                invoiceReportVO.setCompanyWebsite(companyTermsVO.getCompanyWebsite());
                invoiceReportVO.setCompanyEmail(companyTermsVO.getCompanyEmail());
                invoiceReportVO.setCompanyTerms(companyTermsVO.getCompanyTerms());
                invoiceReportVO.setCompanyVatTin(companyTermsVO.getCompanyVatTin());
                invoiceReportVO.setCompanyCstTin(companyTermsVO.getCompanyCstTin());
            }
            currentReport.setInvoiceReportVO(invoiceReportVO);
        } catch (TransactionException | InvoiceException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        JasperPrint jasperPrint = new JasperPrint();
        try {
            jasperPrint = reportsDAO.getInvoiceReport(jasperReport, currentReport);
        } catch (JRException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return jasperPrint;
    }
}
