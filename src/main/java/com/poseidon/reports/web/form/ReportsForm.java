package com.poseidon.reports.web.form;

import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.reports.domain.ReportsVO;
import com.poseidon.transaction.domain.TransactionVO;

import java.util.List;
import java.util.StringJoiner;

/**
 * user: Suraj.
 * Date: Jun 3, 2012
 * Time: 10:41:08 AM
 */
public class ReportsForm {
    private ReportsVO currentReport;
    private ReportsVO searchReports;
    private List<ReportsVO> reportsVOs;
    private String loggedInUser;
    private String loggedInRole;
    private List<String> exportList;
    private TransactionVO txnReportTransactionVO;
    private TransactionVO invoiceListReportTransactionVO;
    private List<MakeVO> makeVOs;
    private List<String> statusList;
    private MakeAndModelVO modelReportMakeAndModelVO;

    public ReportsVO getCurrentReport() {
        return currentReport;
    }

    public void setCurrentReport(final ReportsVO currentReport) {
        this.currentReport = currentReport;
    }

    public ReportsVO getSearchReports() {
        return searchReports;
    }

    public void setSearchReports(final ReportsVO searchReports) {
        this.searchReports = searchReports;
    }

    public List<ReportsVO> getReportsVOs() {
        return reportsVOs;
    }

    public void setReportsVOs(final List<ReportsVO> reportsVOs) {
        this.reportsVOs = reportsVOs;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(final String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getLoggedInRole() {
        return loggedInRole;
    }

    public void setLoggedInRole(final String loggedInRole) {
        this.loggedInRole = loggedInRole;
    }

    public List<String> getExportList() {
        return exportList;
    }

    public void setExportList(final List<String> exportList) {
        this.exportList = exportList;
    }

    public TransactionVO getTxnReportTransactionVO() {
        return txnReportTransactionVO;
    }

    public void setTxnReportTransactionVO(final TransactionVO txnReportTransactionVO) {
        this.txnReportTransactionVO = txnReportTransactionVO;
    }

    public List<MakeVO> getMakeVOs() {
        return makeVOs;
    }

    public void setMakeVOs(final List<MakeVO> makeVOs) {
        this.makeVOs = makeVOs;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(final List<String> statusList) {
        this.statusList = statusList;
    }

    public MakeAndModelVO getModelReportMakeAndModelVO() {
        return modelReportMakeAndModelVO;
    }

    public void setModelReportMakeAndModelVO(final MakeAndModelVO modelReportMakeAndModelVO) {
        this.modelReportMakeAndModelVO = modelReportMakeAndModelVO;
    }

    public TransactionVO getInvoiceListReportTransactionVO() {
        return invoiceListReportTransactionVO;
    }

    public void setInvoiceListReportTransactionVO(final TransactionVO invoiceListReportTransactionVO) {
        this.invoiceListReportTransactionVO = invoiceListReportTransactionVO;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ReportsForm.class.getSimpleName() + "[", "]")
                .add("currentReport=" + currentReport)
                .add("searchReports=" + searchReports)
                .add("reportsVOs=" + reportsVOs)
                .add("loggedInUser='" + loggedInUser + "'")
                .add("loggedInRole='" + loggedInRole + "'")
                .add("exportList=" + exportList)
                .add("txnReportTransactionVO=" + txnReportTransactionVO)
                .add("invoiceListReportTransactionVO=" + invoiceListReportTransactionVO)
                .add("makeVOs=" + makeVOs)
                .add("statusList=" + statusList)
                .add("modelReportMakeAndModelVO=" + modelReportMakeAndModelVO)
                .toString();
    }
}
