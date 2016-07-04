package com.poseidon.Reports.service.impl;

import com.poseidon.CompanyTerms.domain.CompanyTermsVO;
import com.poseidon.Reports.service.ReportsService;
import com.poseidon.Reports.domain.ReportsVO;
import com.poseidon.Reports.dao.ReportsDAO;
import com.poseidon.Reports.exception.ReportsException;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Suraj
 * Date: Jun 3, 2012
 * Time: 10:40:26 AM
 */
public class ReportsServiceImpl implements ReportsService{
    private ReportsDAO reportsDAO;
    private final Logger LOG = LoggerFactory.getLogger(ReportsServiceImpl.class);

    public ReportsDAO getReportsDAO() {
        return reportsDAO;
    }

    public void setReportsDAO(ReportsDAO reportsDAO) {
        this.reportsDAO = reportsDAO;
    }

    public List<ReportsVO> generateDailyReport() {
        List<ReportsVO> reportsVOs = null;
        try {
            reportsVOs = getReportsDAO().generateDailyReport();
        }catch (ReportsException e){
            LOG.error(e.getLocalizedMessage());
        }
        return reportsVOs;
    }

    public JasperPrint getMakeDetailsChart(JasperReport jasperReport, ReportsVO currentReport) {
        JasperPrint jasperPrint = new JasperPrint();
        try {
            jasperPrint = getReportsDAO().getMakeDetailsChart(jasperReport, currentReport);
        } catch (JRException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return jasperPrint;
    }

    public JasperPrint getCallReport(JasperReport jasperReport,
                                     ReportsVO currentReport,
                                     CompanyTermsVO companyTermsVO) {
        JasperPrint jasperPrint = new JasperPrint();
        try {
            jasperPrint = getReportsDAO().getCallReport(jasperReport,
                    currentReport,
                    companyTermsVO);
        } catch (JRException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return jasperPrint;
    }

    public JasperPrint getTransactionsListReport(JasperReport jasperReport, ReportsVO currentReport) {
        JasperPrint jasperPrint = new JasperPrint();
        try {
            jasperPrint = getReportsDAO().getTransactionsListReport(jasperReport, currentReport);
        } catch (JRException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return jasperPrint;
    }

    public JasperPrint getModelListReport(JasperReport jasperReport, ReportsVO currentReport) {
        JasperPrint jasperPrint = new JasperPrint();
        try {
            jasperPrint = getReportsDAO().getModelListReport(jasperReport, currentReport);
        } catch (JRException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return jasperPrint;
    }

    public JasperPrint getErrorReport(JasperReport jasperReport, ReportsVO currentReport) {
        JasperPrint jasperPrint = new JasperPrint();
        try {
            jasperPrint = getReportsDAO().getErrorReport(jasperReport, currentReport);
        } catch (JRException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return jasperPrint;
    }

    public JasperPrint getInvoiceReport(JasperReport jasperReport, ReportsVO currentReport) {
        JasperPrint jasperPrint = new JasperPrint();
        try {
            jasperPrint = getReportsDAO().getInvoiceReport(jasperReport, currentReport);
        } catch (JRException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return jasperPrint;
    }
}
