package com.poseidon.reports.domain;

import com.poseidon.invoice.domain.InvoiceReportVO;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.domain.TransactionVO;

import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;

/**
 * user: Suraj.
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

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public String getExportTo() {
        return exportTo;
    }

    public void setExportTo(final String exportTo) {
        this.exportTo = exportTo;
    }

    public String getRptfilename() {
        return rptfilename;
    }

    public void setRptfilename(final String rptfilename) {
        this.rptfilename = rptfilename;
    }

    public List<MakeVO> getMakeVOList() {
        return makeVOList;
    }

    public void setMakeVOList(final List<MakeVO> makeVOList) {
        this.makeVOList = makeVOList;
    }

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(final String tagNo) {
        this.tagNo = tagNo;
    }

    public List<TransactionVO> getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(final List<TransactionVO> transactionsList) {
        this.transactionsList = transactionsList;
    }

    public List<MakeAndModelVO> getMakeAndModelVOs() {
        return makeAndModelVOs;
    }

    public void setMakeAndModelVOs(final List<MakeAndModelVO> makeAndModelVOs) {
        this.makeAndModelVOs = makeAndModelVOs;
    }

    public TransactionReportVO getTransactionReportVO() {
        return transactionReportVO;
    }

    public void setTransactionReportVO(final TransactionReportVO transactionReportVO) {
        this.transactionReportVO = transactionReportVO;
    }

    public InvoiceReportVO getInvoiceReportVO() {
        return invoiceReportVO;
    }

    public void setInvoiceReportVO(final InvoiceReportVO invoiceReportVO) {
        this.invoiceReportVO = invoiceReportVO;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ReportsVO.class.getSimpleName() + "[", "]")
                .add("locale=" + locale)
                .add("exportTo='" + exportTo + "'")
                .add("rptfilename='" + rptfilename + "'")
                .add("makeVOList=" + makeVOList)
                .add("tagNo='" + tagNo + "'")
                .add("transactionsList=" + transactionsList)
                .add("makeAndModelVOs=" + makeAndModelVOs)
                .add("transactionReportVO=" + transactionReportVO)
                .add("invoiceReportVO=" + invoiceReportVO)
                .toString();
    }
}
