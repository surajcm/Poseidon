package com.poseidon.reports.service.impl;

import com.poseidon.company.service.CompanyTermsService;
import com.poseidon.invoice.service.InvoiceService;
import com.poseidon.make.service.MakeService;
import com.poseidon.reports.dao.ReportsDAO;
import com.poseidon.reports.domain.ReportsVO;
import com.poseidon.reports.exception.ReportsException;
import com.poseidon.transaction.exception.TransactionException;
import com.poseidon.transaction.service.TransactionService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ReportsServiceImplTest {
    private final ReportsServiceImpl reportsService = new ReportsServiceImpl();
    private final ReportsDAO reportsDAO = Mockito.mock(ReportsDAO.class);
    private final MakeService makeService = Mockito.mock(MakeService.class);
    private final TransactionService transactionService = Mockito.mock(TransactionService.class);
    private final CompanyTermsService companyTermsService = Mockito.mock(CompanyTermsService.class);
    private final InvoiceService invoiceService = Mockito.mock(InvoiceService.class);

    @BeforeEach
    public void setup() {
        Whitebox.setInternalState(reportsService, "reportsDAO", reportsDAO);
        Whitebox.setInternalState(reportsService, "makeService", makeService);
        Whitebox.setInternalState(reportsService, "transactionService", transactionService);
        Whitebox.setInternalState(reportsService, "companyTermsService", companyTermsService);
        Whitebox.setInternalState(reportsService, "invoiceService", invoiceService);
    }

    @Test
    public void generateDailyReportSuccess() {
        Assertions.assertNotNull(reportsService.generateDailyReport());
    }

    @Test
    public void generateDailyReportFailure() throws ReportsException {
        doThrow(new ReportsException(ReportsException.DATABASE_ERROR)).when(reportsDAO).generateDailyReport();
        Assertions.assertNull(reportsService.generateDailyReport());
    }

    @Test
    public void getMakeDetailsChartSuccess() throws JRException {
        when(reportsDAO.getMakeDetailsChart(any(), any())).thenReturn(Mockito.mock(JasperPrint.class));
        Assertions.assertNotNull(reportsService.getMakeDetailsChart(
                Mockito.mock(JasperReport.class), new ReportsVO()));
    }

    @Test
    public void getMakeDetailsChartFailure() throws JRException {
        when(reportsDAO.getMakeDetailsChart(any(), any())).thenThrow(new JRException("ERROR"));
        Assertions.assertNotNull(reportsService.getMakeDetailsChart(
                Mockito.mock(JasperReport.class), new ReportsVO()));
    }

    @Test
    public void getCallReportSuccess() throws JRException {
        when(reportsDAO.getCallReport(any(), any(), any())).thenReturn(Mockito.mock(JasperPrint.class));
        Assertions.assertNotNull(reportsService.getCallReport(
                Mockito.mock(JasperReport.class), new ReportsVO()));
    }

    @Test
    public void getCallReportFailure() throws TransactionException, JRException {
        when(transactionService.fetchTransactionFromTag(any()))
                .thenThrow(new TransactionException(TransactionException.DATABASE_ERROR));
        when(reportsDAO.getCallReport(any(), any(), any())).thenThrow(new JRException("ERROR"));
        Assertions.assertNotNull(reportsService.getCallReport(
                Mockito.mock(JasperReport.class), new ReportsVO()));
    }

}