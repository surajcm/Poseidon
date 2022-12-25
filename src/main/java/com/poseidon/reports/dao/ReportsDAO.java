package com.poseidon.reports.dao;

import com.poseidon.company.dao.entities.CompanyTerms;
import com.poseidon.reports.domain.ReportsVO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user: Suraj.
 * Date: Jun 3, 2012
 * Time: 10:40:06 AM
 */
@Repository
public class ReportsDAO {

    /**
     * generate daily report.
     *
     * @return list of reports vo
     */
    public List<ReportsVO> generateDailyReport() {
        return Collections.emptyList();
    }

    /**
     * get make details report.
     *
     * @param jasperReport  jasperReport
     * @param currentReport currentReport
     * @return JasperPrint
     * @throws JRException on error
     */
    public JasperPrint getMakeDetailsChart(final JasperReport jasperReport, final ReportsVO currentReport)
            throws JRException {
        Map<String, Object> params = new HashMap<>();
        return JasperFillManager.fillReport(jasperReport, params,
                new JRBeanCollectionDataSource(currentReport.getMakeVOList()));
    }

    /**
     * get call report.
     *
     * @param jasperReport   jasperReport
     * @param currentReport  currentReport
     * @param companyTerms companyTerms
     * @return JasperPrint
     * @throws JRException on error
     */
    public JasperPrint getCallReport(final JasperReport jasperReport,
                                     final ReportsVO currentReport,
                                     final CompanyTerms companyTerms) throws JRException {
        if (companyTerms != null) {
            currentReport.getTransactionReportVO().setCompanyName(companyTerms.getName());
            currentReport.getTransactionReportVO().setCompanyAddress(companyTerms.getCompanyAddress());
            currentReport.getTransactionReportVO().setCompanyPhoneNumber(companyTerms.getCompanyPhone());
            currentReport.getTransactionReportVO().setCompanyWebsite(companyTerms.getCompanyWebsite());
            currentReport.getTransactionReportVO().setCompanyEmail(companyTerms.getCompanyEmail());
            currentReport.getTransactionReportVO().setTermsAndConditions(companyTerms.getTerms());
            //todo : fix it
            currentReport.getTransactionReportVO().setDateReported(companyTerms.getCreatedOn());
        }
        var reportVOs =
                Collections.singletonList(currentReport.getTransactionReportVO());
        return JasperFillManager.fillReport(jasperReport, new HashMap<>(),
                new JRBeanCollectionDataSource(reportVOs));
    }

    /**
     * get transaction list report.
     *
     * @param jasperReport  jasperReport
     * @param currentReport currentReport
     * @return JasperPrint
     * @throws JRException on error
     */
    public JasperPrint getTransactionsListReport(final JasperReport jasperReport,
                                                 final ReportsVO currentReport) throws JRException {
        Map<String, Object> params = new HashMap<>();
        return JasperFillManager.fillReport(jasperReport, params,
                new JRBeanCollectionDataSource(currentReport.getTransactionsList()));
    }

    /**
     * get model list report.
     *
     * @param jasperReport  jasperReport
     * @param currentReport currentReport
     * @return JasperPrint
     * @throws JRException on error
     */
    public JasperPrint getModelListReport(final JasperReport jasperReport,
                                          final ReportsVO currentReport) throws JRException {
        Map<String, Object> params = new HashMap<>();
        return JasperFillManager.fillReport(jasperReport, params,
                new JRBeanCollectionDataSource(currentReport.getMakeAndModelVOs()));
    }

    /**
     * get error report.
     *
     * @param jasperReport  jasperReport
     * @return JasperPrint
     * @throws JRException on error
     */
    public JasperPrint getErrorReport(final JasperReport jasperReport) throws JRException {
        Map<String, Object> params = new HashMap<>();
        return JasperFillManager.fillReport(jasperReport, params,
                new JRBeanCollectionDataSource(new ArrayList<>()));
    }

    /**
     * get invoice report.
     *
     * @param jasperReport  jasperReport
     * @param currentReport currentReport
     * @return JasperPrint
     * @throws JRException on error
     */
    public JasperPrint getInvoiceReport(final JasperReport jasperReport,
                                        final ReportsVO currentReport) throws JRException {
        Map<String, Object> params = new HashMap<>();
        var invoiceReportVOs =
                Collections.singletonList(currentReport.getInvoiceReportVO());
        return JasperFillManager.fillReport(jasperReport, params,
                new JRBeanCollectionDataSource(invoiceReportVOs));
    }

}
