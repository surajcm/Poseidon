package com.poseidon.Reports.delegate;

import com.poseidon.CompanyTerms.domain.CompanyTermsVO;
import com.poseidon.CompanyTerms.service.CompanyTermsService;
import com.poseidon.Invoice.domain.InvoiceReportVO;
import com.poseidon.Invoice.domain.InvoiceVO;
import com.poseidon.Invoice.exception.InvoiceException;
import com.poseidon.Invoice.service.InvoiceService;
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
    private InvoiceService invoiceService;

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

    public InvoiceService getInvoiceService() {
        return invoiceService;
    }

    public void setInvoiceService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public JasperPrint getMakeDetailsChart(JasperReport jasperReport, ReportsVO currentReport) throws JRException {
        currentReport.setMakeVOList(getMakeService().fetchMakes());
        return getReportsService().getMakeDetailsChart(jasperReport, currentReport);

    }

    public JasperPrint getCallReport(JasperReport jasperReport, ReportsVO currentReport) throws TransactionException {
        CompanyTermsVO companyTermsVO = getCompanyTermsService().listCompanyTerms();
        TransactionReportVO transactionVO;
        try{
        transactionVO = getTransactionService().fetchTransactionFromTag(currentReport.getTagNo());
        }catch (TransactionException e){
            e.printStackTrace();
            throw new TransactionException(e.getMessage());
        }
        currentReport.setTransactionReportVO(transactionVO);
        return getReportsService().getCallReport(jasperReport,
                currentReport,
                companyTermsVO);
    }

    public JasperPrint getTransactionsListReport(JasperReport jasperReport,
                                                 ReportsVO currentReport,
                                                 TransactionVO searchTransaction) throws TransactionException {
        currentReport.setTransactionsList(getTransactionService().searchTransactions(searchTransaction));
        return getReportsService().getTransactionsListReport(jasperReport, currentReport);
    }

    public JasperPrint getModelListReport(JasperReport jasperReport,
                                          ReportsVO currentReport,
                                          MakeAndModelVO searchMakeAndModelVO) {
        currentReport.setMakeAndModelVOs(getMakeService().searchMakeVOs(searchMakeAndModelVO));
        return getReportsService().getModelListReport(jasperReport, currentReport);
    }

    public JasperPrint getErrorReport(JasperReport jasperReport, ReportsVO currentReport) {
        return getReportsService().getErrorReport(jasperReport, currentReport);
    }

    public JasperPrint getInvoiceReport(JasperReport jasperReport, ReportsVO currentReport) {
        try {
            InvoiceReportVO invoiceReportVO = new InvoiceReportVO();
            CompanyTermsVO companyTermsVO = getCompanyTermsService().listCompanyTerms();
            TransactionReportVO transactionVO = getTransactionService().fetchTransactionFromTag(currentReport.getTagNo());
            if (transactionVO != null) {
                InvoiceVO searchInvoiceVO = new InvoiceVO();
                searchInvoiceVO.setTagNo(transactionVO.getTagNo());
                searchInvoiceVO.setStartsWith(Boolean.FALSE);
                searchInvoiceVO.setIncludes(Boolean.FALSE);
                List<InvoiceVO> invoiceVOs = getInvoiceService().findInvoices(searchInvoiceVO);
                if (invoiceVOs != null && invoiceVOs.size() > 0) {
                    InvoiceVO invoiceVO = invoiceVOs.get(0);
                    invoiceReportVO.setInvoiceId(invoiceVO.getId());
                    invoiceReportVO.setTagNo(transactionVO.getTagNo());
                    invoiceReportVO.setCustomerId(transactionVO.getCustomerId());
                    invoiceReportVO.setCustomerName(transactionVO.getCustomerName());
                    invoiceReportVO.setCustomerAddress1(transactionVO.getAddress1());
                    invoiceReportVO.setCustomerAddress2(transactionVO.getAddress2());
                    invoiceReportVO.setDescription(invoiceVO.getDescription());
                    invoiceReportVO.setSerialNo(invoiceVO.getSerialNo());
                    invoiceReportVO.setQuantity(invoiceVO.getQuantity());
                    invoiceReportVO.setRate(invoiceVO.getRate());
                    invoiceReportVO.setAmount(invoiceVO.getAmount());
                    invoiceReportVO.setTotalAmount(invoiceVO.getAmount());
                }
            }
            if (companyTermsVO != null) {
                invoiceReportVO.setCompanyName(companyTermsVO.getCompanyName());
                invoiceReportVO.setCompanyAddress(companyTermsVO.getCompanyAddress());
                invoiceReportVO.setCompanyPhoneNumber(companyTermsVO.getCompanyPhoneNumber());
                invoiceReportVO.setCompanyWebsite(companyTermsVO.getCompanyWebsite());
                invoiceReportVO.setCompanyEmail(companyTermsVO.getCompanyEmail());
                invoiceReportVO.setCompanyTerms(companyTermsVO.getCompanyTerms());
                invoiceReportVO.setCompanyVATTIN(companyTermsVO.getCompanyVATTIN());
                invoiceReportVO.setCompanyCSTTIN(companyTermsVO.getCompanyCSTTIN());
            }

            currentReport.setInvoiceReportVO(invoiceReportVO);
        } catch (TransactionException e) {
            e.printStackTrace();
        } catch (InvoiceException e) {
            e.printStackTrace();
        }
        return getReportsService().getInvoiceReport(jasperReport, currentReport);
    }
}
