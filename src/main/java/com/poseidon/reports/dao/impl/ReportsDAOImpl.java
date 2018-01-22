package com.poseidon.reports.dao.impl;

import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.invoice.domain.InvoiceReportVO;
import com.poseidon.reports.dao.ReportsDAO;
import com.poseidon.reports.domain.ReportsVO;
import com.poseidon.reports.exception.ReportsException;
import com.poseidon.transaction.domain.TransactionReportVO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user: Suraj
 * Date: Jun 3, 2012
 * Time: 10:40:06 AM
 */
@Repository
public class ReportsDAOImpl implements ReportsDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ReportsVO> generateDailyReport() throws ReportsException {
        return null;
    }

    public JasperPrint getMakeDetailsChart(JasperReport jasperReport, ReportsVO currentReport) throws JRException {
        JasperPrint jasperPrint;
        Map<String, Object> params = new HashMap<>();
        jasperPrint = JasperFillManager.fillReport(jasperReport, params,  new JRBeanCollectionDataSource(currentReport.getMakeVOList()));
        return jasperPrint;
    }

    public JasperPrint getCallReport(JasperReport jasperReport,
                                     ReportsVO currentReport,
                                     CompanyTermsVO companyTermsVO) throws JRException {
        JasperPrint jasperPrint;
        Map<String, Object> params = new HashMap<>();
        if (companyTermsVO != null) {
            currentReport.getTransactionReportVO().setCompanyName(companyTermsVO.getCompanyName());
            currentReport.getTransactionReportVO().setCompanyAddress(companyTermsVO.getCompanyAddress());
            currentReport.getTransactionReportVO().setCompanyPhoneNumber(companyTermsVO.getCompanyPhoneNumber());
            currentReport.getTransactionReportVO().setCompanyWebsite(companyTermsVO.getCompanyWebsite());
            currentReport.getTransactionReportVO().setCompanyEmail(companyTermsVO.getCompanyEmail());
            currentReport.getTransactionReportVO().setCompanyTerms(companyTermsVO.getCompanyTerms());
        }
        List<TransactionReportVO> reportVOs = new ArrayList<>();
        reportVOs.add(currentReport.getTransactionReportVO());
        jasperPrint = JasperFillManager.fillReport(jasperReport, params,  new JRBeanCollectionDataSource(reportVOs));
        return jasperPrint;
    }

    public JasperPrint getTransactionsListReport(JasperReport jasperReport, ReportsVO currentReport) throws JRException {
        JasperPrint jasperPrint;
        Map<String, Object> params = new HashMap<>();
        jasperPrint = JasperFillManager.fillReport(jasperReport, params,  new JRBeanCollectionDataSource(currentReport.getTransactionsList()));
        return jasperPrint;
    }

    public JasperPrint getModelListReport(JasperReport jasperReport, ReportsVO currentReport) throws JRException {
        JasperPrint jasperPrint;
        Map<String, Object> params = new HashMap<>();
        jasperPrint = JasperFillManager.fillReport(jasperReport, params,  new JRBeanCollectionDataSource(currentReport.getMakeAndModelVOs()));
        return jasperPrint;
    }

    public JasperPrint getErrorReport(JasperReport jasperReport, ReportsVO currentReport) throws JRException {
        JasperPrint jasperPrint;
        Map<String, Object> params = new HashMap<>();
        jasperPrint = JasperFillManager.fillReport(jasperReport, params,  new JRBeanCollectionDataSource(new ArrayList<Object>()));
        return jasperPrint;
    }

    @Override
    public JasperPrint getInvoiceReport(JasperReport jasperReport, ReportsVO currentReport) throws JRException {
        JasperPrint jasperPrint;
        Map<String, Object> params = new HashMap<>();
        List<InvoiceReportVO> invoiceReportVOs = new ArrayList<>();
        invoiceReportVOs.add(currentReport.getInvoiceReportVO());
        jasperPrint = JasperFillManager.fillReport(jasperReport, params,  new JRBeanCollectionDataSource(invoiceReportVOs));
        return jasperPrint;
    }

}