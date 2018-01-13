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

import java.util.List;

/**
 * user: Suraj
 * Date: Jun 3, 2012
 * Time: 10:40:26 AM
 */
public class ReportsServiceImpl implements ReportsService{
    private static final Logger LOG = LoggerFactory.getLogger(ReportsServiceImpl.class);
    private ReportsDAO reportsDAO;
    private MakeService makeService;
    private TransactionService transactionService;
    private CompanyTermsService companyTermsService;
    private InvoiceService invoiceService;


    public void setReportsDAO(ReportsDAO reportsDAO) {
        this.reportsDAO = reportsDAO;
    }

    public void setMakeService(MakeService makeService) {
        this.makeService = makeService;
    }
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    public void setCompanyTermsService(CompanyTermsService companyTermsService) {
        this.companyTermsService = companyTermsService;
    }


    public void setInvoiceService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public List<ReportsVO> generateDailyReport() {
        List<ReportsVO> reportsVOs = null;
        try {
            reportsVOs = reportsDAO.generateDailyReport();
        }catch (ReportsException e){
            LOG.error(e.getLocalizedMessage());
        }
        return reportsVOs;
    }

    public JasperPrint getMakeDetailsChart(JasperReport jasperReport, ReportsVO currentReport) {
        currentReport.setMakeVOList(makeService.fetchMakes());
        JasperPrint jasperPrint = new JasperPrint();
        try {
            jasperPrint = reportsDAO.getMakeDetailsChart(jasperReport, currentReport);
        } catch (JRException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return jasperPrint;
    }

    public JasperPrint getCallReport(JasperReport jasperReport,
                                     ReportsVO currentReport) {
        CompanyTermsVO companyTermsVO = companyTermsService.listCompanyTerms();
        TransactionReportVO transactionVO = null;
        try {
            transactionVO = transactionService.fetchTransactionFromTag(currentReport.getTagNo());
        } catch (TransactionException e) {
            e.printStackTrace();
        }
        currentReport.setTransactionReportVO(transactionVO);
        JasperPrint jasperPrint = new JasperPrint();
        try {
            jasperPrint = reportsDAO.getCallReport(jasperReport,
                    currentReport,
                    companyTermsVO);
        } catch (JRException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return jasperPrint;
    }

    public JasperPrint getTransactionsListReport(JasperReport jasperReport, ReportsVO currentReport,TransactionVO searchTransaction) {
        try {
            currentReport.setTransactionsList(transactionService.searchTransactions(searchTransaction));
        } catch (TransactionException e) {
            e.printStackTrace();
        }

        JasperPrint jasperPrint = new JasperPrint();
        try {
            jasperPrint = reportsDAO.getTransactionsListReport(jasperReport, currentReport);
        } catch (JRException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return jasperPrint;
    }

    public JasperPrint getModelListReport(JasperReport jasperReport, ReportsVO currentReport, MakeAndModelVO searchMakeAndModelVO){
        currentReport.setMakeAndModelVOs(makeService.searchMakeVOs(searchMakeAndModelVO));
        JasperPrint jasperPrint = new JasperPrint();
        try {
            jasperPrint = reportsDAO.getModelListReport(jasperReport, currentReport);
        } catch (JRException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return jasperPrint;
    }

    public JasperPrint getErrorReport(JasperReport jasperReport, ReportsVO currentReport) {
        JasperPrint jasperPrint = new JasperPrint();
        try {
            jasperPrint = reportsDAO.getErrorReport(jasperReport, currentReport);
        } catch (JRException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return jasperPrint;
    }

    public JasperPrint getInvoiceReport(JasperReport jasperReport, ReportsVO currentReport) {
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
                if (invoiceVOs != null && invoiceVOs.size() > 0) {
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
                invoiceReportVO.setCompanyVATTIN(companyTermsVO.getCompanyVATTIN());
                invoiceReportVO.setCompanyCSTTIN(companyTermsVO.getCompanyCSTTIN());
            }

            currentReport.setInvoiceReportVO(invoiceReportVO);
        } catch (TransactionException e) {
            e.printStackTrace();
        } catch (InvoiceException e) {
            e.printStackTrace();
        }
        JasperPrint jasperPrint = new JasperPrint();
        try {
            jasperPrint = reportsDAO.getInvoiceReport(jasperReport, currentReport);
        } catch (JRException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return jasperPrint;
    }
}
