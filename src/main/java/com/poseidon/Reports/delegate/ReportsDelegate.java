package com.poseidon.Reports.delegate;

import com.poseidon.CompanyTerms.domain.CompanyTermsVO;
import com.poseidon.CompanyTerms.service.CompanyTermsService;
import com.poseidon.Make.domain.MakeAndModelVO;
import com.poseidon.Make.service.MakeService;
import com.poseidon.Reports.domain.ReportsVO;
import com.poseidon.Reports.service.ReportsService;
import com.poseidon.Transaction.domain.TransactionReportVO;
import com.poseidon.Transaction.domain.TransactionVO;
import com.poseidon.Transaction.exception.TransactionException;
import com.poseidon.Transaction.service.TransactionService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.util.List;

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
        CompanyTermsVO companyTermsVO = getCompanyTermsService().listCompanyTerms();
        TransactionReportVO transactionVO = getTransactionService().fetchTransactionFromTag(currentReport.getTagNo());
        currentReport.setTransactionReportVO(transactionVO);
        return getReportsService().getCallReport(jasperReport,
                currentReport,
                companyTermsVO);
    }

    public JasperPrint getTransactionsListReport(JasperReport jasperReport,
                                                 ReportsVO currentReport,
                                                 TransactionVO searchTransaction) throws TransactionException {
        currentReport.setTransactionsList(getTransactionService().searchTransactions(searchTransaction));
        return getReportsService().getTransactionsListReport(jasperReport,currentReport);
    }

    public JasperPrint getModelListReport(JasperReport jasperReport,
                                          ReportsVO currentReport,
                                          MakeAndModelVO searchMakeAndModelVO) {
        currentReport.setMakeAndModelVOs(getMakeService().searchMakeVOs(searchMakeAndModelVO));
        return getReportsService().getModelListReport(jasperReport,currentReport);
    }

    public JasperPrint getErrorReport(JasperReport jasperReport, ReportsVO currentReport) {
        return  getReportsService().getErrorReport(jasperReport,currentReport);
    }
}
