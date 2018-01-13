package com.poseidon.Reports.service;

import com.poseidon.Make.domain.MakeAndModelVO;
import com.poseidon.Reports.domain.ReportsVO;
import com.poseidon.Transaction.domain.TransactionVO;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.util.List;

/**
 * User: Suraj
 * Date: Jun 3, 2012
 * Time: 10:39:48 AM
 */
public interface ReportsService {
    List<ReportsVO> generateDailyReport();

    JasperPrint getMakeDetailsChart(JasperReport jasperReport, ReportsVO currentReport);

    JasperPrint getCallReport(JasperReport jasperReport,
                                     ReportsVO currentReport);

    JasperPrint getTransactionsListReport(JasperReport jasperReport, ReportsVO currentReport,TransactionVO searchTransaction);

    JasperPrint getModelListReport(JasperReport jasperReport, ReportsVO currentReport,
                                   MakeAndModelVO searchMakeAndModelVO);

    JasperPrint getErrorReport(JasperReport jasperReport, ReportsVO currentReport);

    JasperPrint getInvoiceReport(JasperReport jasperReport, ReportsVO currentReport);
}
