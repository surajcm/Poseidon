package com.poseidon.Reports.delegate;

import com.poseidon.CompanyTerms.delegate.CompanyTermsDelegate;
import com.poseidon.CompanyTerms.domain.CompanyTermsVO;
import com.poseidon.CompanyTerms.service.CompanyTermsService;
import com.poseidon.Make.delegate.MakeDelegate;
import com.poseidon.Make.service.MakeService;
import com.poseidon.Reports.domain.ReportsVO;
import com.poseidon.Reports.service.ReportsService;
import com.poseidon.Transaction.domain.TransactionReportVO;
import com.poseidon.Transaction.domain.TransactionVO;
import com.poseidon.Transaction.exception.TransactionException;
import com.poseidon.Transaction.service.TransactionService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.jdbc.datasource.DataSourceUtils;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Suraj
 * Date: Jun 3, 2012
 * Time: 10:39:00 AM
 */
public class ReportsDelegate {
    private ReportsService reportsService;
    private CompanyTermsService companyTermsService;
    private TransactionService transactionService;
    private MakeService makeService;

    public ReportsService getReportsService() {
        return reportsService;
    }

    public void setReportsService(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    public CompanyTermsService getCompanyTermsService() {
        return companyTermsService;
    }

    public void setCompanyTermsService(CompanyTermsService companyTermsService) {
        this.companyTermsService = companyTermsService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public MakeService getMakeService() {
        return makeService;
    }

    public void setMakeService(MakeService makeService) {
        this.makeService = makeService;
    }

    public List<ReportsVO> generateDailyReport() {
        return getReportsService().generateDailyReport();
    }

    public JasperPrint getMakeDetailsChart(JasperReport jasperReport, ReportsVO currentReport) throws JRException {
        currentReport.setMakeVOList(getMakeService().fetchMakes());
        return getReportsService().getMakeDetailsChart(jasperReport,currentReport);

    }

    public JasperPrint getCallReport(JasperReport jasperReport,ReportsVO currentReport) throws TransactionException {
        CompanyTermsVO companyTermsVO =getCompanyTermsService().listCompanyTerms();
        TransactionReportVO transactionVO = getTransactionService().fetchTransactionFromTag(currentReport.getTagNo());
        return getReportsService().getCallReport(jasperReport,
                currentReport,
                companyTermsVO,
                transactionVO);
    }

    public JasperPrint getTransactionsListReport(JasperReport jasperReport, ReportsVO currentReport) throws TransactionException {
        TransactionVO searchTransaction = new TransactionVO();
        searchTransaction.setTagNo("WON2N12");
        searchTransaction.setIncludes(Boolean.FALSE);
        searchTransaction.setStartswith(Boolean.FALSE);
        currentReport.setTransactionsList(getTransactionService().searchTransactions(searchTransaction));
        return getReportsService().getTransactionsListReport(jasperReport,currentReport);
    }
}
