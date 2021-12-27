package com.poseidon.reports.web.controller;

import com.poseidon.init.util.CommonUtils;
import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.make.service.MakeService;
import com.poseidon.reports.domain.ExportList;
import com.poseidon.reports.domain.InvoiceStatus;
import com.poseidon.reports.domain.ReportsVO;
import com.poseidon.reports.service.ReportsService;
import com.poseidon.reports.util.ReportingConfigurations;
import com.poseidon.reports.web.form.ReportsForm;
import com.poseidon.transaction.domain.TransactionVO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
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
    private static final String REPORTS = "/resources/reports";
    private static final String COMPILE_REPORT = "Going to compile report";
    private static final String JRXML = ".jrxml";
    private static final String FILENAME = "attachment;filename=";
    private static final String X_FRAME_OPTIONS = "X-Frame-Options";
    private static final String SAME_ORIGIN = "SAMEORIGIN";
    private static final String TEXT_HTML = "text/html";
    private static final String CONTENT_DISPOSITION = "Content-disposition";
    private static final String CONTENT_DISPOSITION1 = "Content-Disposition";
    private final ReportsService reportsService;

    private final MakeService makeService;

    public ReportsController(final ReportsService reportsService, final MakeService makeService) {
        this.reportsService = reportsService;
        this.makeService = makeService;
    }

    /**
     * list reports.
     *
     * @param reportsForm form
     * @return view
     */
    @PostMapping("/reports/List.htm")
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
        reportsForm.setModelReportMakeAndModelVO(getSearchMakeAndModelVO());
        reportsForm.setTxnReportTransactionVO(getSearchTransaction());
        reportsForm.setInvoiceListReportTransactionVO(getSearchTransaction());
        model.addAttribute("reportsForm", reportsForm);
        //return new ModelAndView("reports/List", "reportsForm", reportsForm);
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
     * getMakeDetailsReport.
     *
     * @param httpServletRequest  HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param reportsForm         ReportsForm
     */
    @RequestMapping(value = "/reports/getMakeDetailsReport.htm", method = {RequestMethod.GET, RequestMethod.POST})
    public void getMakeDetailsReport(final HttpServletRequest httpServletRequest,
                                     final HttpServletResponse httpServletResponse,
                                     final ReportsForm reportsForm) {
        LOG.info("GetMakeDetailsReport method of ReportsController ");
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
                generateJasperReport(httpServletResponse, jasperPrint, reportFileName, reportType);
            }
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    }

    /**
     * getCallReport.
     *
     * @param httpServletRequest  HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param reportsForm         ReportsForm
     */
    @RequestMapping(value = "/reports/getCallReport.htm", method = {RequestMethod.GET, RequestMethod.POST})
    public void getCallReport(final HttpServletRequest httpServletRequest,
                              final HttpServletResponse httpServletResponse,
                              final ReportsForm reportsForm) {
        LOG.info("GetCallReport method of ReportsController ");
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
            generateJasperReport(httpServletResponse, jasperPrint, reportFileName, reportType);
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
            getErrorReport(httpServletRequest, httpServletResponse, reportsForm);
        }
    }

    /**
     * getTransactionsListReport.
     *
     * @param httpServletRequest  HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param reportsForm         ReportsForm
     */
    @RequestMapping(value = "/reports/getTransactionsListReport.htm", method = {RequestMethod.GET, RequestMethod.POST})
    public void getTransactionsListReport(final HttpServletRequest httpServletRequest,
                                          final HttpServletResponse httpServletResponse,
                                          final ReportsForm reportsForm) {
        LOG.info("GetTransactionsListReport method of ReportsController ");
        var sanitizedReportsForm = CommonUtils.sanitizedString(reportsForm.toString());
        LOG.info(FORM_DETAILS, sanitizedReportsForm);
        try {
            if (reportsForm.getCurrentReport() == null) {
                reportsForm.setCurrentReport(new ReportsVO());
            }
            var reportFileName = "transactionsListReport";
            var jasperReport = createJasperReport(reportFileName);
            var reportsVO = reportsForm.getCurrentReport();
            //modify the dates
            var newTransactionVO = modifyDateFormat(reportsForm.getTxnReportTransactionVO());
            var jasperPrint = reportsService.getTransactionsListReport(jasperReport,
                    reportsVO,
                    newTransactionVO);
            var reportType = ExportList.fromName(reportsVO.getExportTo());
            generateJasperReport(httpServletResponse, jasperPrint, reportFileName, reportType);
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
        if (!transactionVO.getEndDate() .isBlank()) {
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
    @RequestMapping(value = "/reports/getModelListReport.htm", method = {RequestMethod.GET, RequestMethod.POST})
    public void getModelListReport(final HttpServletRequest httpServletRequest,
                                   final HttpServletResponse httpServletResponse,
                                   final ReportsForm reportsForm) {
        LOG.info("GetModelListReport method of ReportsController ");
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
            generateJasperReport(httpServletResponse, jasperPrint, reportFileName, reportType);
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    }

    /**
     * getInvoiceReport.
     *
     * @param httpServletRequest  HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param reportsForm         ReportsForm
     */
    @RequestMapping(value = "/reports/getInvoiceReport.htm", method = {RequestMethod.GET, RequestMethod.POST})
    public void getInvoiceReport(final HttpServletRequest httpServletRequest,
                                 final HttpServletResponse httpServletResponse,
                                 final ReportsForm reportsForm) {
        LOG.info("GetInvoiceReport method of ReportsController ");
        var sanitizedReportsForm = CommonUtils.sanitizedString(reportsForm.toString());
        LOG.info(FORM_DETAILS, sanitizedReportsForm);
        try {
            if (reportsForm.getCurrentReport() == null) {
                reportsForm.setCurrentReport(new ReportsVO());
            }
            var reportFileName = "serviceBillReport";
            var jasperReport = createJasperReport(reportFileName);
            var jasperPrint = reportsService.getInvoiceReport(jasperReport, reportsForm.getCurrentReport());
            var reportType = ExportList.fromName(reportsForm.getCurrentReport().getExportTo());
            generateJasperReport(httpServletResponse, jasperPrint, reportFileName, reportType);
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
            getErrorReport(httpServletRequest, httpServletResponse, reportsForm);
        }
    }

    /**
     * getErrorReport.
     *
     * @param httpServletRequest  HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param reportsForm         ReportsForm
     */
    @RequestMapping(value = "/reports/getErrorReport.htm", method = {RequestMethod.GET, RequestMethod.POST})
    public void getErrorReport(final HttpServletRequest httpServletRequest,
                               final HttpServletResponse httpServletResponse,
                               final ReportsForm reportsForm) {
        LOG.info("GetErrorReport method of ReportsController ");
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
            generateJasperReport(httpServletResponse, jasperPrint, reportFileName, reportType);
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    }

    private JasperReport createJasperReport(final String reportFileName) throws JRException {
        var path = getReportPath();
        LOG.info(COMPILE_REPORT);
        return JasperCompileManager.compileReport(path + '/' + reportFileName + JRXML);
    }

    private String getReportPath() {
        var path = "";
        var attr = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (attr != null) {
            path = attr.getRequest().getSession().getServletContext().getRealPath(REPORTS);
        }
        return path;
    }

    /**
     * This method is for generating the jasper report.
     *
     * @param httpServletResponse the current HTTP response
     * @param jasperPrint         jasperPrint instance
     * @param reportFileName      reportFileName instance
     * @param reportType          reportType instance
     */
    private void generateJasperReport(final HttpServletResponse httpServletResponse,
                                      final JasperPrint jasperPrint,
                                      final String reportFileName,
                                      final ExportList reportType) {
        try {
            LOG.info("In generateJasperReport method");
            httpServletResponse.setHeader(X_FRAME_OPTIONS, SAME_ORIGIN);
            LOG.info("ReportFileName : {} , ReportType {} ", reportFileName, reportType);
            switch (reportType) {
                case EXCEL -> {
                    httpServletResponse.setContentType("application/vnd.ms-excel");
                    httpServletResponse.setHeader(CONTENT_DISPOSITION1, FILENAME + reportFileName + ";");
                    generateExcelReport(httpServletResponse, jasperPrint);
                }
                case PDF -> {
                    var mimetype = httpServletResponse.getContentType();
                    httpServletResponse.setContentType((mimetype != null) ? mimetype : "application/pdf");
                    httpServletResponse.setHeader(CONTENT_DISPOSITION1,
                            FILENAME + reportFileName + ";");
                    generatePDFReport(httpServletResponse, jasperPrint);
                }
                case WORD -> {
                    httpServletResponse.addHeader(CONTENT_DISPOSITION,
                            FILENAME + reportFileName + ".doc;");
                    httpServletResponse.setContentType("application/vnd.ms-word");
                    generateWordReport(httpServletResponse, jasperPrint);
                }
                default -> {
                    httpServletResponse.setContentType(TEXT_HTML);
                    generateHTMLReport(httpServletResponse, jasperPrint);
                }
            }
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    }

    private void generateHTMLReport(final HttpServletResponse httpServletResponse,
                                    final JasperPrint jasperPrint) throws JRException, IOException {
        var htmlExporter = new HtmlExporter();
        htmlExporter.setExporterInput(ReportingConfigurations.exporter(jasperPrint));
        htmlExporter.setConfiguration(ReportingConfigurations.configurationForHTML());
        var outputStream = new ByteArrayOutputStream();
        htmlExporter.setExporterOutput(ReportingConfigurations.exportHTML(outputStream));
        htmlExporter.exportReport();
        writeBytesToStream(httpServletResponse, outputStream);
    }

    private void generateWordReport(final HttpServletResponse httpServletResponse,
                                    final JasperPrint jasperPrint) throws JRException, IOException {
        var exporter = new JRDocxExporter();
        exporter.setExporterInput(ReportingConfigurations.exporter(jasperPrint));
        exporter.setConfiguration(ReportingConfigurations.docxReportConfiguration());
        var outputStream = new ByteArrayOutputStream();
        exporter.setExporterOutput(ReportingConfigurations.exporterOutput(outputStream));
        exporter.exportReport();
        writeBytesToStream(httpServletResponse, outputStream);
    }

    private void generatePDFReport(final HttpServletResponse httpServletResponse,
                                   final JasperPrint jasperPrint) throws JRException, IOException {

        var pdfExporter = new JRPdfExporter();
        pdfExporter.setExporterInput(ReportingConfigurations.exporter(jasperPrint));
        pdfExporter.setConfiguration(ReportingConfigurations.pdfReportConfiguration());
        var outputStream = new ByteArrayOutputStream();
        pdfExporter.setExporterOutput(ReportingConfigurations.exporterOutput(outputStream));
        pdfExporter.exportReport();
        writeBytesToStream(httpServletResponse, outputStream);
    }

    private void generateExcelReport(final HttpServletResponse httpServletResponse,
                                     final JasperPrint jasperPrint) throws JRException, IOException {
        var xlsExporter = new JRXlsExporter();
        xlsExporter.setExporterInput(ReportingConfigurations.exporter(jasperPrint));
        xlsExporter.setConfiguration(ReportingConfigurations.configurationReportXls());
        var outputStream = new ByteArrayOutputStream();
        xlsExporter.setExporterOutput(ReportingConfigurations.exporterOutput(outputStream));
        xlsExporter.exportReport();
        writeBytesToStream(httpServletResponse, outputStream);
    }

    private void writeBytesToStream(final HttpServletResponse httpServletResponse,
                                    final ByteArrayOutputStream outputStream1) throws IOException {
        byte[] output = outputStream1.toByteArray();
        var outputStream = httpServletResponse.getOutputStream();
        httpServletResponse.setContentLength(output.length);
        outputStream.write(output, 0, output.length);
        outputStream.flush();
        outputStream.close();
    }


    private TransactionVO getSearchTransaction() {
        var searchVO = new TransactionVO();
        searchVO.setModelId(0L);
        searchVO.setMakeId(0L);
        return searchVO;
    }

    private MakeAndModelVO getSearchMakeAndModelVO() {
        var searchVO = new MakeAndModelVO();
        searchVO.setMakeId(0L);
        searchVO.setModelId(0L);
        return searchVO;
    }

}
