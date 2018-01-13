package com.poseidon.reports.dao;

import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.reports.domain.ReportsVO;
import com.poseidon.reports.exception.ReportsException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.util.List;

/**
 * user: Suraj
 * Date: Jun 3, 2012
 * Time: 10:38:44 AM
 */
public interface ReportsDAO {
    List<ReportsVO> generateDailyReport() throws ReportsException;

    JasperPrint getMakeDetailsChart(JasperReport jasperReport, ReportsVO currentReport) throws  JRException;

    JasperPrint getCallReport(JasperReport jasperReport,
                                     ReportsVO currentReport,
                                     CompanyTermsVO companyTermsVO) throws JRException;

    JasperPrint getTransactionsListReport(JasperReport jasperReport, ReportsVO currentReport) throws JRException;

    JasperPrint getModelListReport(JasperReport jasperReport, ReportsVO currentReport) throws  JRException;

    JasperPrint getErrorReport(JasperReport jasperReport, ReportsVO currentReport)throws  JRException;

    JasperPrint getInvoiceReport(JasperReport jasperReport, ReportsVO currentReport) throws  JRException;
}
