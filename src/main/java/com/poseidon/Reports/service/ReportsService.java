package com.poseidon.Reports.service;

import com.poseidon.CompanyTerms.domain.CompanyTermsVO;
import com.poseidon.Reports.domain.ReportsVO;
import com.poseidon.Transaction.domain.TransactionReportVO;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 3, 2012
 * Time: 10:39:48 AM
 */
public interface ReportsService {
    public List<ReportsVO> generateDailyReport();

    public JasperPrint getMakeDetailsChart(JasperReport jasperReport, ReportsVO currentReport);

    public JasperPrint getCallReport(JasperReport jasperReport,
                                     ReportsVO currentReport,
                                     CompanyTermsVO companyTermsVO);

    public JasperPrint getTransactionsListReport(JasperReport jasperReport, ReportsVO currentReport);

    public JasperPrint getModelListReport(JasperReport jasperReport, ReportsVO currentReport);

    public JasperPrint getErrorReport(JasperReport jasperReport, ReportsVO currentReport);

    public JasperPrint getInvoiceReport(JasperReport jasperReport, ReportsVO currentReport);
}
