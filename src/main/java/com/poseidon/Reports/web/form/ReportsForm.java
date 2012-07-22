package com.poseidon.Reports.web.form;

import com.poseidon.Make.domain.MakeVO;
import com.poseidon.Reports.domain.ReportsVO;
import com.poseidon.Transaction.domain.TransactionVO;

import java.util.List;

/**
 * User: Suraj
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
    private TransactionVO searchTransaction;
    private List<MakeVO> makeVOs;
    private List<String> statusList;


    public ReportsVO getCurrentReport() {
        return currentReport;
    }

    public void setCurrentReport(ReportsVO currentReport) {
        this.currentReport = currentReport;
    }

    public ReportsVO getSearchReports() {
        return searchReports;
    }

    public void setSearchReports(ReportsVO searchReports) {
        this.searchReports = searchReports;
    }

    public List<ReportsVO> getReportsVOs() {
        return reportsVOs;
    }

    public void setReportsVOs(List<ReportsVO> reportsVOs) {
        this.reportsVOs = reportsVOs;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getLoggedInRole() {
        return loggedInRole;
    }

    public void setLoggedInRole(String loggedInRole) {
        this.loggedInRole = loggedInRole;
    }

    public List<String> getExportList() {
        return exportList;
    }

    public void setExportList(List<String> exportList) {
        this.exportList = exportList;
    }

    public TransactionVO getSearchTransaction() {
        return searchTransaction;
    }

    public void setSearchTransaction(TransactionVO searchTransaction) {
        this.searchTransaction = searchTransaction;
    }

    public List<MakeVO> getMakeVOs() {
        return makeVOs;
    }

    public void setMakeVOs(List<MakeVO> makeVOs) {
        this.makeVOs = makeVOs;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    @Override
    public String toString() {
        return "ReportsForm{" +
                "currentReport=" + currentReport +
                ", searchReports=" + searchReports +
                ", reportsVOs=" + reportsVOs +
                ", loggedInUser='" + loggedInUser + '\'' +
                ", loggedInRole='" + loggedInRole + '\'' +
                ", exportList=" + exportList +
                '}';
    }
}
