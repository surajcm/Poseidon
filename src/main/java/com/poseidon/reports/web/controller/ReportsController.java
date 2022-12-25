package com.poseidon.reports.web.controller;

import com.poseidon.init.util.CommonUtils;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.make.service.MakeService;
import com.poseidon.reports.domain.ExportList;
import com.poseidon.reports.domain.InvoiceStatus;
import com.poseidon.reports.domain.ReportsVO;
import com.poseidon.reports.service.ReportsService;
import com.poseidon.reports.util.ReportsUtil;
import com.poseidon.reports.web.form.ReportsForm;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.user.service.UserService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * user: Suraj.
 * Date: Jun 3, 2012
 * Time: 10:40:47 AM
 */
@Controller
@SuppressWarnings("unused")
public class ReportsController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportsController.class);
    private static final String FORM_DETAILS = " form details are {}";
    private static final String REPORTS = "/reports";
    private static final String COMPILE_REPORT = "Going to compile report";
    private static final String JRXML = ".jrxml";

    private final ReportsService reportsService;
    private final MakeService makeService;
    private final UserService userService;
    private final ReportsUtil reportsUtil;

    public ReportsController(final ReportsService reportsService,
                             final MakeService makeService,
                             final UserService userService,
                             final ReportsUtil reportsUtil) {
        this.reportsService = reportsService;
        this.makeService = makeService;
        this.userService = userService;
        this.reportsUtil = reportsUtil;
    }

    /**
     * list reports.
     *
     * @param reportsForm form
     * @return view
     */
    @PostMapping("/reports/list")
    public String list(final ReportsForm reportsForm, final Model model) {
        var sanitizedReportsForm = CommonUtils.sanitizedString(reportsForm.toString());
        LOG.info("List method of ReportsController, form details are : {}",
                sanitizedReportsForm);
        List<ReportsVO> reportsVOs = null;
        try {
            reportsVOs = reportsService.generateDailyReport();
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        if (reportsVOs != null) {
            reportsVOs.forEach(reportsVO -> LOG.info(" reportsVO is {}", reportsVO));
            reportsForm.setReportsVOs(reportsVOs);
        }
        //get all the make list for displaying in search
        List<MakeVO> makeVOs = fetchMakeVOS();
        if (makeVOs != null) {
            makeVOs.forEach(makeVO -> LOG.info("make vo is {}", makeVO));
            reportsForm.setMakeVOs(makeVOs);
        }
        reportsForm.setSearchReports(new ReportsVO());
        reportsForm.setLoggedInRole(reportsForm.getLoggedInRole());
        reportsForm.setLoggedInUser(reportsForm.getLoggedInUser());
        reportsForm.setExportList(ExportList.asList());
        reportsForm.setStatusList(InvoiceStatus.asList());
        reportsForm.setCurrentReport(new ReportsVO());
        reportsForm.setModelReportMakeAndModelVO(reportsUtil.getSearchMakeAndModelVO());
        reportsForm.setTxnReportTransactionVO(reportsUtil.getSearchTransaction());
        reportsForm.setInvoiceListReportTransactionVO(reportsUtil.getSearchTransaction());
        model.addAttribute("reportsForm", reportsForm);
        return "reports/List";
    }

    private List<MakeVO> fetchMakeVOS() {
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = makeService.fetchMakes();
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return makeVOs;
    }

    /**
     * makeDetailsReport.
     *
     * @param httpServletRequest  HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param reportsForm         ReportsForm
     */
    @PostMapping("/reports/makeDetailsReport")
    public void makeDetailsReport(final HttpServletRequest httpServletRequest,
                                     final HttpServletResponse httpServletResponse,
                                     final ReportsForm reportsForm) {
        LOG.info("MakeDetailsReport method of ReportsController ");
        var sanitizedReportsForm = CommonUtils.sanitizedString(reportsForm.toString());
        LOG.info(FORM_DETAILS, sanitizedReportsForm);
        try {
            if (reportsForm.getCurrentReport() == null) {
                reportsForm.setCurrentReport(new ReportsVO());
            }
            if (reportsForm.getCurrentReport() != null) {
                var reportFileName = "makeListReport";
                var jasperReport = createJasperReport(reportFileName);
                var jasperPrint = reportsService.getMakeDetailsChart(jasperReport,
                        reportsForm.getCurrentReport());
                var reportType = ExportList.fromName(reportsForm.getCurrentReport().getExportTo());
                reportsUtil.generateJasperReport(httpServletResponse, jasperPrint, reportFileName, reportType);
            }
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    }

    /**
     * CallReport.
     *
     * @param httpServletRequest  HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param reportsForm         ReportsForm
     */
    @PostMapping("/reports/callReport")
    public void callReport(final HttpServletRequest httpServletRequest,
                              final HttpServletResponse httpServletResponse,
                              final ReportsForm reportsForm) {
        LOG.info("callReport method of ReportsController ");
        var sanitizedReportsForm = CommonUtils.sanitizedString(reportsForm.toString());
        LOG.info(FORM_DETAILS, sanitizedReportsForm);
        try {
            if (reportsForm.getCurrentReport() == null) {
                reportsForm.setCurrentReport(new ReportsVO());
            }
            var reportFileName = "callReport";
            var jasperReport = createJasperReport(reportFileName);
            var jasperPrint = reportsService.getCallReport(jasperReport, reportsForm.getCurrentReport());
            var reportType = ExportList.fromName(reportsForm.getCurrentReport().getExportTo());
            reportsUtil.generateJasperReport(httpServletResponse, jasperPrint, reportFileName, reportType);
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
            generateErrorReport(httpServletRequest, httpServletResponse, reportsForm);
        }
    }

    /**
     * transactionsListReport.
     *
     * @param httpServletRequest  HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param reportsForm         ReportsForm
     */
    @PostMapping("/reports/transactionsListReport")
    public void transactionsListReport(final HttpServletRequest httpServletRequest,
                                          final HttpServletResponse httpServletResponse,
                                          final ReportsForm reportsForm) {
        LOG.info("TransactionsListReport method of ReportsController ");
        var sanitizedReportsForm = CommonUtils.sanitizedString(reportsForm.toString());
        LOG.info(FORM_DETAILS, sanitizedReportsForm);
        try {
            if (reportsForm.getCurrentReport() == null) {
                reportsForm.setCurrentReport(new ReportsVO());
            }
            var reportFileName = "transactionsListReport";
            var jasperReport = createJasperReport(reportFileName);
            var reportsVO = reportsForm.getCurrentReport();
            //todo : modify the dates
            var newTransactionVO = modifyDateFormat(reportsForm.getTxnReportTransactionVO());
            var jasperPrint = reportsService.getTransactionsListReport(jasperReport,
                    reportsVO,
                    newTransactionVO);
            var reportType = ExportList.fromName(reportsVO.getExportTo());
            reportsUtil.generateJasperReport(httpServletResponse, jasperPrint, reportFileName, reportType);
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    }

    private TransactionVO modifyDateFormat(final TransactionVO transactionVO) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var toFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        if (!transactionVO.getStartDate().isBlank()) {
            var dateTime = LocalDate.parse(
                    transactionVO.getStartDate(), formatter).atStartOfDay();
            transactionVO.setStartDate(dateTime.format(toFormatter));
        }
        if (!transactionVO.getEndDate().isBlank()) {
            var dateTime = LocalDate.parse(
                    transactionVO.getEndDate(), formatter).atStartOfDay();
            transactionVO.setEndDate(dateTime.format(toFormatter));
        }
        return transactionVO;
    }


    /**
     * getModelListReport.
     *
     * @param httpServletRequest  HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param reportsForm         ReportsForm
     */
    @PostMapping("/reports/modelListReport")
    public void generateModelListReport(final HttpServletRequest httpServletRequest,
                                        final HttpServletResponse httpServletResponse,
                                        final ReportsForm reportsForm) {
        LOG.info("ModelListReport method of ReportsController ");
        var sanitizedReportsForm = CommonUtils.sanitizedString(reportsForm.toString());
        LOG.info(FORM_DETAILS, sanitizedReportsForm);
        try {
            if (reportsForm.getCurrentReport() == null) {
                reportsForm.setCurrentReport(new ReportsVO());
            }
            var reportFileName = "modelListReport";
            var jasperReport = createJasperReport(reportFileName);
            var jasperPrint = reportsService.getModelListReport(jasperReport,
                    reportsForm.getCurrentReport(),
                    reportsForm.getModelReportMakeAndModelVO());
            var reportType = ExportList.fromName(reportsForm.getCurrentReport().getExportTo());
            reportsUtil.generateJasperReport(httpServletResponse, jasperPrint, reportFileName, reportType);
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    }

    /**
     * invoiceReport.
     *
     * @param httpServletRequest  HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param reportsForm         ReportsForm
     */
    @PostMapping("/reports/invoiceReport")
    public void generateInvoiceReport(final HttpServletRequest httpServletRequest,
                                      final HttpServletResponse httpServletResponse,
                                      final ReportsForm reportsForm) {
        LOG.info("InvoiceReport method of ReportsController ");
        var sanitizedReportsForm = CommonUtils.sanitizedString(reportsForm.toString());
        LOG.info(FORM_DETAILS, sanitizedReportsForm);
        var companyCode = activeCompanyCode();
        try {
            if (reportsForm.getCurrentReport() == null) {
                reportsForm.setCurrentReport(new ReportsVO());
            }
            var reportFileName = "serviceBillReport";
            var jasperReport = createJasperReport(reportFileName);
            var jasperPrint = reportsService.getInvoiceReport(jasperReport,
                    reportsForm.getCurrentReport(),
                    companyCode);
            var reportType = ExportList.fromName(reportsForm.getCurrentReport().getExportTo());
            reportsUtil.generateJasperReport(httpServletResponse, jasperPrint, reportFileName, reportType);
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
            generateErrorReport(httpServletRequest, httpServletResponse, reportsForm);
        }
    }

    private String activeCompanyCode() {
        var currentLoggedInUser = currentLoggedInUser();
        var user = userService.findUserFromName(currentLoggedInUser);
        return user.getCompanyCode();
    }

    private String currentLoggedInUser() {
        var username = "";
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            username = auth.getName();
        }
        return username;
    }

    /**
     * getErrorReport.
     *
     * @param httpServletRequest  HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param reportsForm         ReportsForm
     */
    @PostMapping("/reports/errorReport")
    public void generateErrorReport(final HttpServletRequest httpServletRequest,
                                    final HttpServletResponse httpServletResponse,
                                    final ReportsForm reportsForm) {
        LOG.info("ErrorReport method of ReportsController ");
        var sanitizedReportsForm = CommonUtils.sanitizedString(reportsForm.toString());
        LOG.info(FORM_DETAILS, sanitizedReportsForm);
        try {
            if (reportsForm.getCurrentReport() == null) {
                reportsForm.setCurrentReport(new ReportsVO());
            }
            var reportFileName = "errorReport";
            var jasperReport = createJasperReport(reportFileName);
            var jasperPrint = reportsService.getErrorReport(jasperReport);
            var reportType = ExportList.fromName(reportsForm.getCurrentReport().getExportTo());
            reportsUtil.generateJasperReport(httpServletResponse, jasperPrint, reportFileName, reportType);
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    }

    private JasperReport createJasperReport(final String reportFileName) throws JRException {
        String filePath = "";
        try {
            var file = new ClassPathResource(REPORTS + '/' + reportFileName + JRXML).getFile();
            filePath = file.getAbsolutePath();
        } catch (IOException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        LOG.info(COMPILE_REPORT);
        return JasperCompileManager.compileReport(filePath);
    }
}
