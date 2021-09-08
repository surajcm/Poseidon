package com.poseidon.reports.service;

import com.poseidon.company.domain.CompanyTermsVO;
import com.poseidon.company.service.CompanyTermsService;
import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.invoice.exception.InvoiceException;
import com.poseidon.invoice.service.impl.InvoiceService;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.service.MakeService;
import com.poseidon.reports.dao.ReportsDAO;
import com.poseidon.reports.domain.ReportsVO;
import com.poseidon.reports.exception.ReportsException;
import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.exception.TransactionException;
import com.poseidon.transaction.service.TransactionService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ReportsServiceTest {
    private final ReportsDAO reportsDAO = Mockito.mock(ReportsDAO.class);
    private final MakeService makeService = Mockito.mock(MakeService.class);
    private final TransactionService transactionService = Mockito.mock(TransactionService.class);
    private final CompanyTermsService companyTermsService = Mockito.mock(CompanyTermsService.class);
    private final InvoiceService invoiceService = Mockito.mock(InvoiceService.class);
    private final ReportsService reportsService = new ReportsService(reportsDAO,
            makeService, transactionService,
            companyTermsService, invoiceService);

    @Test
    void generateDailyReportSuccess() {
        Assertions.assertNotNull(reportsService.generateDailyReport());
    }

    @Test
    void generateDailyReportFailure() throws ReportsException {
        doThrow(new ReportsException(ReportsException.DATABASE_ERROR)).when(reportsDAO).generateDailyReport();
        Assertions.assertNull(reportsService.generateDailyReport());
    }

    @Test
    void getMakeDetailsChartSuccess() throws JRException {
        when(reportsDAO.getMakeDetailsChart(any(), any())).thenReturn(Mockito.mock(JasperPrint.class));
        Assertions.assertNotNull(reportsService.getMakeDetailsChart(
                Mockito.mock(JasperReport.class), new ReportsVO()));
    }

    @Test
    void getMakeDetailsChartFailure() throws JRException {
        when(reportsDAO.getMakeDetailsChart(any(), any())).thenThrow(new JRException("ERROR"));
        Assertions.assertNotNull(reportsService.getMakeDetailsChart(
                Mockito.mock(JasperReport.class), new ReportsVO()));
    }

    @Test
    void getCallReportSuccess() throws JRException {
        when(reportsDAO.getCallReport(any(), any(), any())).thenReturn(Mockito.mock(JasperPrint.class));
        Assertions.assertNotNull(reportsService.getCallReport(
                Mockito.mock(JasperReport.class), new ReportsVO()));
    }

    @Test
    void getTransactionsListReportSuccess() throws JRException {
        when(reportsDAO.getTransactionsListReport(any(), any())).thenReturn(Mockito.mock(JasperPrint.class));
        Assertions.assertNotNull(reportsService.getTransactionsListReport(
                Mockito.mock(JasperReport.class), new ReportsVO(), new TransactionVO()));
    }

    @Test
    void getModelListReportSuccess() throws JRException {
        when(reportsDAO.getModelListReport(any(), any())).thenReturn(Mockito.mock(JasperPrint.class));
        Assertions.assertNotNull(reportsService.getModelListReport(
                Mockito.mock(JasperReport.class), new ReportsVO(), new MakeAndModelVO()));
    }

    @Test
    void getModelListReportFailure() throws JRException {
        when(reportsDAO.getModelListReport(any(), any())).thenThrow(new JRException("ERROR"));
        Assertions.assertNotNull(reportsService.getModelListReport(
                Mockito.mock(JasperReport.class), new ReportsVO(), new MakeAndModelVO()));
    }

    @Test
    void getErrorReportSuccess() throws JRException {
        when(reportsDAO.getErrorReport(any(), any())).thenReturn(Mockito.mock(JasperPrint.class));
        Assertions.assertNotNull(reportsService.getErrorReport(Mockito.mock(JasperReport.class),
                new ReportsVO()));
    }

    @Test
    void getErrorReportFailure() throws JRException {
        when(reportsDAO.getErrorReport(any(), any())).thenThrow(new JRException("ERROR"));
        Assertions.assertNotNull(reportsService.getErrorReport(Mockito.mock(JasperReport.class),
                new ReportsVO()));
    }

    @Test
    void getInvoiceReportSuccess() throws JRException {
        when(reportsDAO.getInvoiceReport(any(), any())).thenReturn(Mockito.mock(JasperPrint.class));
        Assertions.assertNotNull(reportsService.getInvoiceReport(Mockito.mock(JasperReport.class),
                new ReportsVO()));
    }

    @Test
    void getInvoiceReportInvoiceFailure() throws JRException, TransactionException, InvoiceException {
        when(transactionService.fetchTransactionFromTag(any()))
                .thenReturn(Mockito.mock(TransactionReportVO.class));
        when(invoiceService.findInvoices(any())).thenThrow(new InvoiceException(new RuntimeException()));
        when(reportsDAO.getInvoiceReport(any(), any())).thenThrow(new JRException("ERROR"));
        Assertions.assertNotNull(reportsService.getInvoiceReport(Mockito.mock(JasperReport.class),
                new ReportsVO()));
    }

    @Test
    void getInvoiceReportCompanyFailure() throws JRException, TransactionException {
        when(transactionService.fetchTransactionFromTag(any()))
                .thenReturn(Mockito.mock(TransactionReportVO.class));
        when(companyTermsService.listCompanyTerms()).thenReturn(Optional.empty());
        when(reportsDAO.getInvoiceReport(any(), any())).thenThrow(new JRException("ERROR"));
        Assertions.assertNotNull(reportsService.getInvoiceReport(Mockito.mock(JasperReport.class),
                new ReportsVO()));
    }

    @Test
    void getInvoiceReportFailure() throws JRException, TransactionException, InvoiceException {
        when(transactionService.fetchTransactionFromTag(any()))
                .thenReturn(Mockito.mock(TransactionReportVO.class));
        List<InvoiceVO> invoiceVO = new ArrayList<>();
        invoiceVO.add(Mockito.mock(InvoiceVO.class));
        when(invoiceService.findInvoices(any())).thenReturn(invoiceVO);
        when(companyTermsService.listCompanyTerms()).thenReturn(Optional.of(Mockito.mock(CompanyTermsVO.class)));
        when(reportsDAO.getInvoiceReport(any(), any())).thenThrow(new JRException("ERROR"));
        Assertions.assertNotNull(reportsService.getInvoiceReport(Mockito.mock(JasperReport.class),
                new ReportsVO()));
    }

}