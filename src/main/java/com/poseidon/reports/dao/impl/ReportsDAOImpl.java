package com.poseidon.reports.dao.impl;

import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.invoice.domain.InvoiceReportVO;
import com.poseidon.reports.dao.ReportsDAO;
import com.poseidon.reports.domain.ReportsVO;
import com.poseidon.reports.exception.ReportsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.poseidon.transaction.domain.TransactionReportVO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * user: Suraj
 * Date: Jun 3, 2012
 * Time: 10:40:06 AM
 */
public class ReportsDAOImpl extends JdbcDaoSupport implements ReportsDAO {

    public List<ReportsVO> generateDailyReport() throws ReportsException {
        return null;
    }

    public JasperPrint getMakeDetailsChart(JasperReport jasperReport, ReportsVO currentReport) throws JRException {
        JasperPrint jasperPrint;
        Map<String, Object> params = new HashMap<String, Object>();
        jasperPrint = JasperFillManager.fillReport(jasperReport, params,  new JRBeanCollectionDataSource(currentReport.getMakeVOList()));
        return jasperPrint;
    }

    public JasperPrint getCallReport(JasperReport jasperReport,
                                     ReportsVO currentReport,
                                     CompanyTermsVO companyTermsVO) throws JRException {
        JasperPrint jasperPrint = null;
        Map<String, Object> params = new HashMap<String, Object>();
        if (companyTermsVO != null) {
            currentReport.getTransactionReportVO().setCompanyName(companyTermsVO.getCompanyName());
            currentReport.getTransactionReportVO().setCompanyAddress(companyTermsVO.getCompanyAddress());
            currentReport.getTransactionReportVO().setCompanyPhoneNumber(companyTermsVO.getCompanyPhoneNumber());
            currentReport.getTransactionReportVO().setCompanyWebsite(companyTermsVO.getCompanyWebsite());
            currentReport.getTransactionReportVO().setCompanyEmail(companyTermsVO.getCompanyEmail());
            currentReport.getTransactionReportVO().setCompanyTerms(companyTermsVO.getCompanyTerms());
        }
        List<TransactionReportVO> reportVOs = new ArrayList<TransactionReportVO>();
        reportVOs.add(currentReport.getTransactionReportVO());
        jasperPrint = JasperFillManager.fillReport(jasperReport, params,  new JRBeanCollectionDataSource(reportVOs));
        return jasperPrint;
    }

    public JasperPrint getTransactionsListReport(JasperReport jasperReport, ReportsVO currentReport) throws JRException {
        JasperPrint jasperPrint;
        Map<String, Object> params = new HashMap<String, Object>();
        jasperPrint = JasperFillManager.fillReport(jasperReport, params,  new JRBeanCollectionDataSource(currentReport.getTransactionsList()));
        return jasperPrint;
    }

    public JasperPrint getModelListReport(JasperReport jasperReport, ReportsVO currentReport) throws JRException {
        JasperPrint jasperPrint;
        Map<String, Object> params = new HashMap<String, Object>();
        jasperPrint = JasperFillManager.fillReport(jasperReport, params,  new JRBeanCollectionDataSource(currentReport.getMakeAndModelVOs()));
        return jasperPrint;
    }

    public JasperPrint getErrorReport(JasperReport jasperReport, ReportsVO currentReport) throws JRException {
        JasperPrint jasperPrint;
        Map<String, Object> params = new HashMap<String, Object>();
        jasperPrint = JasperFillManager.fillReport(jasperReport, params,  new JRBeanCollectionDataSource(new ArrayList<Object>()));
        return jasperPrint;
    }

    @Override
    public JasperPrint getInvoiceReport(JasperReport jasperReport, ReportsVO currentReport) throws JRException {
        JasperPrint jasperPrint;
        Map<String, Object> params = new HashMap<String, Object>();
        List<InvoiceReportVO> invoiceReportVOs = new ArrayList<InvoiceReportVO>();
        invoiceReportVOs.add(currentReport.getInvoiceReportVO());
        jasperPrint = JasperFillManager.fillReport(jasperReport, params,  new JRBeanCollectionDataSource(invoiceReportVOs));
        return jasperPrint;
    }

}
