package com.poseidon.Reports.dao;

import com.poseidon.CompanyTerms.domain.CompanyTermsVO;
import com.poseidon.Reports.domain.ReportsVO;
import com.poseidon.Reports.exception.ReportsException;
import com.poseidon.Transaction.domain.TransactionReportVO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.sql.SQLException;
import java.util.List;

/**
 * User: Suraj
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
