package com.poseidon.reports.service;

import com.poseidon.company.dao.entities.CompanyTerms;
import com.poseidon.company.service.CompanyService;
import com.poseidon.init.util.CommonUtils;
import com.poseidon.invoice.domain.InvoiceReportVO;
import com.poseidon.invoice.domain.InvoiceVO;
import com.poseidon.invoice.service.InvoiceService;
import com.poseidon.make.dao.entities.Make;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.make.service.MakeService;
import com.poseidon.reports.dao.ReportsDAO;
import com.poseidon.reports.domain.ReportsVO;
import com.poseidon.transaction.domain.TransactionReportVO;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.transaction.service.TransactionService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * user: Suraj.
 * Date: Jun 3, 2012
 * Time: 10:40:26 AM
 */
@Service
public class ReportsService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportsService.class);
    private final ReportsDAO reportsDAO;
    private final MakeService makeService;
    private final TransactionService transactionService;
    private final CompanyService companyService;
    private final InvoiceService invoiceService;

    public ReportsService(final ReportsDAO reportsDAO,
                          final MakeService makeService,
                          final TransactionService transactionService,
                          final CompanyService companyService,
                          final InvoiceService invoiceService) {
        this.reportsDAO = reportsDAO;
        this.makeService = makeService;
        this.transactionService = transactionService;
        this.companyService = companyService;
        this.invoiceService = invoiceService;
    }

    /**
     * daily report.
     *
     * @return list of reports
     */
    public List<ReportsVO> generateDailyReport() {
        return reportsDAO.generateDailyReport();
    }

    /**
     * make details chart.
     *
     * @param jasperReport  jasperReport
     * @param currentReport currentReport
     * @return JasperPrint
     */
    public JasperPrint getMakeDetailsChart(final JasperReport jasperReport, final ReportsVO currentReport) {
        currentReport.setMakeVOList(convertMakeToMakeVO(makeService.fetchAllMakes()));
        var jasperPrint = new JasperPrint();
        try {
            jasperPrint = reportsDAO.getMakeDetailsChart(jasperReport, currentReport);
        } catch (JRException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return jasperPrint;
    }

    private List<MakeVO> convertMakeToMakeVO(final List<Make> makes) {
        return makes.stream().map(this::createMakeVO).toList();
    }

    private MakeVO createMakeVO(final Make make) {
        var makeVO = new MakeVO();
        makeVO.setId(make.getId());
        makeVO.setMakeName(make.getMakeName());
        makeVO.setDescription(make.getDescription());
        makeVO.setCreatedBy(make.getCreatedBy());
        makeVO.setModifiedBy(make.getModifiedBy());
        return makeVO;
    }

    /**
     * call report.
     *
     * @param jasperReport  jasperReport
     * @param currentReport currentReport
     * @return JasperPrint
     */
    public JasperPrint getCallReport(final JasperReport jasperReport, final ReportsVO currentReport) {
        var sanitizedTagNo = CommonUtils.sanitizedString(currentReport.getTagNo());
        LOG.info("For call report, fetching details of the tag number :{}", sanitizedTagNo);
        var transactionVO = getTransactionReportVO(sanitizedTagNo);
        currentReport.setTransactionReportVO(transactionVO);
        var jasperPrint = new JasperPrint();
        var companyTerms = getCompanyTerms();
        try {
            jasperPrint = reportsDAO.getCallReport(jasperReport, currentReport, companyTerms);
        } catch (JRException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return jasperPrint;
    }

    private CompanyTerms getCompanyTerms() {
        var companyTerms = companyService.listCompanyTerms("QC01");
        CompanyTerms terms = null;
        if (companyTerms.isPresent()) {
            terms = companyTerms.get();
        }
        return terms;
    }

    private TransactionReportVO getTransactionReportVO(final String tagNo) {
        return transactionService.fetchTransactionFromTag(tagNo);
    }

    /**
     * transaction list report.
     *
     * @param jasperReport      jasperReport
     * @param currentReport     currentReport
     * @param searchTransaction searchTransaction
     * @return JasperPrint
     */
    public JasperPrint getTransactionsListReport(final JasperReport jasperReport,
                                                 final ReportsVO currentReport,
                                                 final TransactionVO searchTransaction) {
        currentReport.setTransactionsList(getTransactionVOS(searchTransaction));
        var jasperPrint = new JasperPrint();
        try {
            jasperPrint = reportsDAO.getTransactionsListReport(jasperReport, currentReport);
        } catch (JRException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return jasperPrint;
    }

    private List<TransactionVO> getTransactionVOS(final TransactionVO searchTransaction) {
        return transactionService.searchTransactions(searchTransaction);
    }

    /**
     * model list report.
     *
     * @param jasperReport         jasperReport
     * @param currentReport        currentReport
     * @param searchMakeAndModelVO searchMakeAndModelVO
     * @return JasperPrint
     */
    public JasperPrint getModelListReport(final JasperReport jasperReport,
                                          final ReportsVO currentReport,
                                          final MakeAndModelVO searchMakeAndModelVO) {
        currentReport.setMakeAndModelVOs(makeService.searchMakeVOs(searchMakeAndModelVO));
        var jasperPrint = new JasperPrint();
        try {
            jasperPrint = reportsDAO.getModelListReport(jasperReport, currentReport);
        } catch (JRException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return jasperPrint;
    }

    /**
     * error report.
     *
      * @return JasperPrint
     */
    public JasperPrint getErrorReport(final JasperReport jasperReport) {
        var jasperPrint = new JasperPrint();
        try {
            jasperPrint = reportsDAO.getErrorReport(jasperReport);
        } catch (JRException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return jasperPrint;
    }

    /**
     * invoice report.
     *
     * @param jasperReport  jasperReport
     * @param currentReport currentReport
     * @return JasperPrint
     */
    public JasperPrint getInvoiceReport(final JasperReport jasperReport,
                                        final ReportsVO currentReport,
                                        final String companyCode) {
        var transaction = getTransactionReportVO(currentReport.getTagNo());
        var invoiceVO = getInvoiceVOFromTag(transaction);
        if (invoiceVO != null) {
            currentReport.setInvoiceReportVO(getInvoiceReportVO(transaction, invoiceVO, companyCode));
        }
        var jasperPrint = new JasperPrint();
        try {
            jasperPrint = reportsDAO.getInvoiceReport(jasperReport, currentReport);
        } catch (JRException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return jasperPrint;
    }

    private InvoiceVO getInvoiceVOFromTag(final TransactionReportVO transaction) {
        InvoiceVO invoiceVO = null;
        if (transaction != null) {
            invoiceVO = getInvoiceVOs(transaction.getTagNo());
        }
        return invoiceVO;
    }

    private InvoiceReportVO getInvoiceReportVO(final TransactionReportVO transactionVO,
                                               final InvoiceVO invoiceVO,
                                               final String companyCode) {
        var invoiceReportVO = new InvoiceReportVO();
        updateInvoiceInfo(invoiceReportVO, invoiceVO);
        updateTransactionInfo(invoiceReportVO, transactionVO);
        updateCompanyInfo(invoiceReportVO, companyCode);
        return invoiceReportVO;
    }

    private void updateInvoiceInfo(final InvoiceReportVO invoiceReportVO, final InvoiceVO invoiceVO) {
        invoiceReportVO.setInvoiceId(invoiceVO.getId());
        invoiceReportVO.setDescription(invoiceVO.getDescription());
        invoiceReportVO.setSerialNo(invoiceVO.getSerialNo());
        invoiceReportVO.setQuantity(invoiceVO.getQuantity());
        invoiceReportVO.setRate(invoiceVO.getRate());
        invoiceReportVO.setAmount(invoiceVO.getAmount());
        invoiceReportVO.setTotalAmount(invoiceVO.getAmount());
    }

    private void updateTransactionInfo(final InvoiceReportVO invoiceReportVO, final TransactionReportVO transactionVO) {
        invoiceReportVO.setTagNo(transactionVO.getTagNo());
        invoiceReportVO.setCustomerId(transactionVO.getCustomerId());
        invoiceReportVO.setCustomerName(transactionVO.getCustomerName());
        invoiceReportVO.setCustomerAddress(transactionVO.getAddress());
    }

    private void updateCompanyInfo(final InvoiceReportVO invoiceReportVO, final String companyCode) {
        var companyTerms = companyService.listCompanyTerms(companyCode);
        if (companyTerms.isPresent()) {
            invoiceReportVO.setCompanyName(companyTerms.get().getName());
            invoiceReportVO.setCompanyAddress(companyTerms.get().getAddress());
            invoiceReportVO.setCompanyPhoneNumber(companyTerms.get().getPhone());
            invoiceReportVO.setCompanyWebsite(companyTerms.get().getWebsite());
            invoiceReportVO.setCompanyEmail(companyTerms.get().getEmail());
            invoiceReportVO.setTermsAndConditions(companyTerms.get().getTerms());
            invoiceReportVO.setCompanyVatTin(companyTerms.get().getVatTin());
            invoiceReportVO.setCompanyCstTin(companyTerms.get().getCstTin());
        }
    }

    private InvoiceVO getInvoiceVOs(final String tagNo) {
        Optional<InvoiceVO> firstInvoice = invoiceService.fetchInvoiceVOFromTagNo(tagNo);
        //todo:this is not the right way
        return firstInvoice.orElse(null);
    }
}
