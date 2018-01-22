package com.poseidon.reports.domain;

import com.poseidon.invoice.domain.InvoiceReportVO;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.domain.TransactionVO;

import java.util.List;
import java.util.Locale;

/**
 * user: Suraj
 * Date: Jun 3, 2012
 * Time: 10:39:11 AM
 */
public class ReportsVO {
    private Locale locale;
    private String exportTo;
    private String rptfilename;
    private List<MakeVO> makeVOList;
    private String tagNo;
    private List<TransactionVO> transactionsList;
    private List<MakeAndModelVO> makeAndModelVOs;
    private TransactionReportVO transactionReportVO;
    private InvoiceReportVO invoiceReportVO;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getExportTo() {
        return exportTo;
    }

    public void setExportTo(String exportTo) {
        this.exportTo = exportTo;
    }

    public String getRptfilename() {
        return rptfilename;
    }

    public void setRptfilename(String rptfilename) {
        this.rptfilename = rptfilename;
    }

    public List<MakeVO> getMakeVOList() {
        return makeVOList;
    }

    public void setMakeVOList(List<MakeVO> makeVOList) {
        this.makeVOList = makeVOList;
    }

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public List<TransactionVO> getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(List<TransactionVO> transactionsList) {
        this.transactionsList = transactionsList;
    }

    public List<MakeAndModelVO> getMakeAndModelVOs() {
        return makeAndModelVOs;
    }

    public void setMakeAndModelVOs(List<MakeAndModelVO> makeAndModelVOs) {
        this.makeAndModelVOs = makeAndModelVOs;
    }

    public TransactionReportVO getTransactionReportVO() {
        return transactionReportVO;
    }

    public void setTransactionReportVO(TransactionReportVO transactionReportVO) {
        this.transactionReportVO = transactionReportVO;
    }

    public InvoiceReportVO getInvoiceReportVO() {
        return invoiceReportVO;
    }

    public void setInvoiceReportVO(InvoiceReportVO invoiceReportVO) {
        this.invoiceReportVO = invoiceReportVO;
    }

    @Override
    public String toString() {
        return "ReportsVO{" +
                "locale=" + locale +
                ", exportTo='" + exportTo + '\'' +
                ", rptfilename='" + rptfilename + '\'' +
                ", makeVOList=" + makeVOList +
                ", tagNo='" + tagNo + '\'' +
                ", transactionsList=" + transactionsList +
                ", makeAndModelVOs=" + makeAndModelVOs +
                ", transactionReportVO=" + transactionReportVO +
                ", invoiceReportVO=" + invoiceReportVO +
                '}';
    }
}