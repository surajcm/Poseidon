package com.poseidon.reports.web.controller;

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
import com.poseidon.util.CommonUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * user: Suraj.
 * Date: Jun 3, 2012
 * Time: 10:40:47 AM
 */
@Controller
//@RequestMapping("/reports")
@SuppressWarnings("unused")
public class ReportsController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportsController.class);
    private static final String FORM_DETAILS = " form details are {}";
    private static final String REPORTS = "/resources/reports";
    private static final String COMPILE_REPORT = "Going to compile report";
    private static final String JRXML = ".jrxml";
    private static final String FILENAME = "attachment;filename=";
    private static final String X_FRAME_OPTIONS = "X-Frame-Options";
    private static final String SAMEORIGIN = "SAMEORIGIN";
    private static final String TEXT_HTML = "text/html";
    @Autowired
    private ReportsService reportsService;

    @Autowired
    private MakeService makeService;

    /**
     * list reports.
     *
     * @param reportsForm form
     * @return view
     */
    @PostMapping("/reports/List.htm")
    public ModelAndView list(final ReportsForm reportsForm) {
        LOG.info("List method of ReportsController, form details are : {}",
                CommonUtils.sanitizedString(reportsForm.toString()));
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
        return new ModelAndView("reports/List", "reportsForm", reportsForm);
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
     * @return ModelAndView
     */
    @RequestMapping(value = "/reports/getMakeDetailsReport.htm", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getMakeDetailsReport(final HttpServletRequest httpServletRequest,
                                             final HttpServletResponse httpServletResponse,
                                             final ReportsForm reportsForm) {
        LOG.info("GetMakeDetailsReport method of ReportsController ");
        LOG.info(FORM_DETAILS, CommonUtils.sanitizedString(reportsForm.toString()));
        try {
            if (reportsForm.getCurrentReport() == null) {
                reportsForm.setCurrentReport(new ReportsVO());
            }
            if (reportsForm.getCurrentReport() != null) {
                reportsForm.getCurrentReport().setLocale(Locale.US);
                var reportFileName = "makeListReport";
                reportsForm.getCurrentReport().setRptfilename(reportFileName);
                var jasperReport = createJasperReport(reportFileName);
                var jasperPrint = reportsService.getMakeDetailsChart(jasperReport,
                        reportsForm.getCurrentReport());
                LOG.info(jasperPrint.toString());
                var reportType = ExportList.fromName(reportsForm.getCurrentReport().getExportTo());
                generateJasperReport(httpServletResponse, jasperPrint, reportFileName, reportType);
            }
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return null;
    }

    /**
     * getCallReport.
     *
     * @param httpServletRequest  HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param reportsForm         ReportsForm
     * @return ModelAndView
     */
    @RequestMapping(value = "/reports/getCallReport.htm", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getCallReport(final HttpServletRequest httpServletRequest,
                                      final HttpServletResponse httpServletResponse,
                                      final ReportsForm reportsForm) {
        LOG.info("GetCallReport method of ReportsController ");
        LOG.info(FORM_DETAILS, CommonUtils.sanitizedString(reportsForm.toString()));
        try {
            if (reportsForm.getCurrentReport() == null) {
                reportsForm.setCurrentReport(new ReportsVO());
            }
            reportsForm.getCurrentReport().setLocale(Locale.US);
            var reportFileName = "callReport";
            reportsForm.getCurrentReport().setRptfilename(reportFileName);
            var jasperReport = createJasperReport(reportFileName);
            var jasperPrint = reportsService.getCallReport(jasperReport, reportsForm.getCurrentReport());
            LOG.info(jasperPrint.toString());
            var reportType = ExportList.fromName(reportsForm.getCurrentReport().getExportTo());
            generateJasperReport(httpServletResponse, jasperPrint, reportFileName, reportType);
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
            return getErrorReport(httpServletRequest, httpServletResponse, reportsForm);
        }
        return null;
    }

    /**
     * getTransactionsListReport.
     *
     * @param httpServletRequest  HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param reportsForm         ReportsForm
     * @return ModelAndView
     */
    @RequestMapping(value = "/reports/getTransactionsListReport.htm", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getTransactionsListReport(final HttpServletRequest httpServletRequest,
                                                  final HttpServletResponse httpServletResponse,
                                                  final ReportsForm reportsForm) {
        LOG.info("GetTransactionsListReport method of ReportsController ");
        LOG.info(FORM_DETAILS, CommonUtils.sanitizedString(reportsForm.toString()));
        try {
            if (reportsForm.getCurrentReport() == null) {
                reportsForm.setCurrentReport(new ReportsVO());
            }
            reportsForm.getCurrentReport().setLocale(Locale.US);
            var reportFileName = "transactionsListReport";
            reportsForm.getCurrentReport().setRptfilename(reportFileName);
            var jasperReport = createJasperReport(reportFileName);
            var jasperPrint = reportsService.getTransactionsListReport(jasperReport,
                    reportsForm.getCurrentReport(),
                    reportsForm.getTxnReportTransactionVO());
            LOG.info(jasperPrint.toString());
            var reportType = ExportList.fromName(reportsForm.getCurrentReport().getExportTo());
            generateJasperReport(httpServletResponse, jasperPrint, reportFileName, reportType);
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return null;
    }

    /**
     * getModelListReport.
     *
     * @param httpServletRequest  HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param reportsForm         ReportsForm
     * @return ModelAndView
     */
    @RequestMapping(value = "/reports/getModelListReport.htm", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getModelListReport(final HttpServletRequest httpServletRequest,
                                           final HttpServletResponse httpServletResponse,
                                           final ReportsForm reportsForm) {
        LOG.info("GetModelListReport method of ReportsController ");
        LOG.info(FORM_DETAILS, CommonUtils.sanitizedString(reportsForm.toString()));
        try {
            if (reportsForm.getCurrentReport() == null) {
                reportsForm.setCurrentReport(new ReportsVO());
            }
            reportsForm.getCurrentReport().setLocale(Locale.US);
            var reportFileName = "modelListReport";
            reportsForm.getCurrentReport().setRptfilename(reportFileName);
            var jasperReport = createJasperReport(reportFileName);
            var jasperPrint = reportsService.getModelListReport(jasperReport,
                    reportsForm.getCurrentReport(),
                    reportsForm.getModelReportMakeAndModelVO());
            LOG.info(jasperPrint.toString());
            var reportType = ExportList.fromName(reportsForm.getCurrentReport().getExportTo());
            generateJasperReport(httpServletResponse, jasperPrint, reportFileName, reportType);
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return null;
    }

    /**
     * getErrorReport.
     *
     * @param httpServletRequest  HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param reportsForm         ReportsForm
     * @return ModelAndView
     */
    @RequestMapping(value = "/reports/getErrorReport.htm", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getErrorReport(final HttpServletRequest httpServletRequest,
                                       final HttpServletResponse httpServletResponse,
                                       final ReportsForm reportsForm) {
        LOG.info("GetErrorReport method of ReportsController ");
        LOG.info(FORM_DETAILS, CommonUtils.sanitizedString(reportsForm.toString()));
        try {
            if (reportsForm.getCurrentReport() == null) {
                reportsForm.setCurrentReport(new ReportsVO());
            }
            reportsForm.getCurrentReport().setLocale(Locale.US);
            var reportFileName = "errorReport";
            reportsForm.getCurrentReport().setRptfilename(reportFileName);
            var jasperReport = createJasperReport(reportFileName);
            var jasperPrint = reportsService.getErrorReport(jasperReport, reportsForm.getCurrentReport());
            LOG.info(jasperPrint.toString());
            var reportType = ExportList.fromName(reportsForm.getCurrentReport().getExportTo());
            generateJasperReport(httpServletResponse, jasperPrint, reportFileName, reportType);
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return null;
    }

    /**
     * getInvoiceReport.
     *
     * @param httpServletRequest  HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param reportsForm         ReportsForm
     * @return ModelAndView
     */
    @RequestMapping(value = "/reports/getInvoiceReport.htm", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getInvoiceReport(final HttpServletRequest httpServletRequest,
                                         final HttpServletResponse httpServletResponse,
                                         final ReportsForm reportsForm) {
        LOG.info("GetInvoiceReport method of ReportsController ");
        LOG.info(FORM_DETAILS, CommonUtils.sanitizedString(reportsForm.toString()));
        try {
            if (reportsForm.getCurrentReport() == null) {
                reportsForm.setCurrentReport(new ReportsVO());
            }
            reportsForm.getCurrentReport().setLocale(Locale.US);
            var reportFileName = "serviceBillReport";
            reportsForm.getCurrentReport().setRptfilename(reportFileName);
            var jasperReport = createJasperReport(reportFileName);
            var jasperPrint = reportsService.getInvoiceReport(jasperReport, reportsForm.getCurrentReport());
            LOG.info(jasperPrint.toString());
            var reportType = ExportList.fromName(reportsForm.getCurrentReport().getExportTo());
            generateJasperReport(httpServletResponse, jasperPrint, reportFileName, reportType);
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return getErrorReport(httpServletRequest, httpServletResponse, reportsForm);
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
            httpServletResponse.setHeader(X_FRAME_OPTIONS, SAMEORIGIN);
            switch (reportType) {
                case EXCEL -> {
                    LOG.info("ExcelReport -- > reportFileName ---> " + reportFileName + reportType);
                    generateExcelReport(httpServletResponse, jasperPrint, reportFileName);
                }
                case PDF -> {
                    LOG.info("PDF -- > reportFileName ---> " + reportFileName + reportType);
                    generatePDFReport(httpServletResponse, jasperPrint, reportFileName);
                }
                case WORD -> {
                    LOG.info("WORD -- > reportFileName ---> " + reportFileName + reportType);
                    generateWordReport(httpServletResponse, jasperPrint, reportFileName);
                }
                default -> generateHTMLReport(httpServletResponse, jasperPrint);
            }
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    }

    private void generateHTMLReport(final HttpServletResponse httpServletResponse,
                                    final JasperPrint jasperPrint) throws JRException, IOException {
        httpServletResponse.setContentType(TEXT_HTML);
        var htmlExporter = new HtmlExporter();
        htmlExporter.setExporterInput(ReportingConfigurations.exporter(jasperPrint));
        htmlExporter.setConfiguration(ReportingConfigurations.configurationForHTML());
        var baos = new ByteArrayOutputStream();
        htmlExporter.setExporterOutput(ReportingConfigurations.exportHTML(baos));
        htmlExporter.exportReport();
        byte[] output = baos.toByteArray();
        httpServletResponse.setContentLength(output.length);
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(output, 0, output.length);
        outputStream.flush();
        outputStream.close();
    }

    private void generateWordReport(final HttpServletResponse httpServletResponse,
                                    final JasperPrint jasperPrint,
                                    final String reportFileName) throws JRException, IOException {
        httpServletResponse.addHeader("Content-disposition",
                FILENAME + reportFileName + ".doc;");
        httpServletResponse.setContentType("application/vnd.ms-word");
        var exporter = new JRDocxExporter();
        exporter.setExporterInput(ReportingConfigurations.exporter(jasperPrint));
        exporter.setConfiguration(ReportingConfigurations.docxReportConfiguration());
        var baos = new ByteArrayOutputStream();
        exporter.setExporterOutput(ReportingConfigurations.exporterOutput(baos));
        exporter.exportReport();
        byte[] output = baos.toByteArray();
        httpServletResponse.setContentLength(output.length);
        httpServletResponse.getOutputStream().write(output, 0, output.length);
        httpServletResponse.getOutputStream().flush();
        httpServletResponse.getOutputStream().close();
    }

    private void generatePDFReport(final HttpServletResponse httpServletResponse,
                                   final JasperPrint jasperPrint,
                                   final String reportFileName) throws JRException, IOException {
        var mimetype = httpServletResponse.getContentType();
        httpServletResponse.setContentType((mimetype != null) ? mimetype : "application/pdf");
        httpServletResponse.setHeader("Content-Disposition",
                FILENAME + reportFileName + ";");
        var pdfExporter = new JRPdfExporter();
        pdfExporter.setExporterInput(ReportingConfigurations.exporter(jasperPrint));
        pdfExporter.setConfiguration(ReportingConfigurations.pdfReportConfiguration());
        var baos = new ByteArrayOutputStream();
        pdfExporter.setExporterOutput(ReportingConfigurations.exporterOutput(baos));
        pdfExporter.exportReport();
        byte[] output = baos.toByteArray();
        httpServletResponse.setContentLength(output.length);
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(output, 0, output.length);
        outputStream.flush();
        outputStream.close();
    }

    private void generateExcelReport(final HttpServletResponse httpServletResponse,
                                     final JasperPrint jasperPrint,
                                     final String reportFileName) throws JRException, IOException {
        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-Disposition", FILENAME + reportFileName + ";");
        var xlsExporter = new JRXlsExporter();
        xlsExporter.setExporterInput(ReportingConfigurations.exporter(jasperPrint));
        xlsExporter.setConfiguration(ReportingConfigurations.configurationReportXls());
        var baos = new ByteArrayOutputStream();
        xlsExporter.setExporterOutput(ReportingConfigurations.exporterOutput(baos));
        xlsExporter.exportReport();
        byte[] output = baos.toByteArray();
        httpServletResponse.setContentLength(output.length);
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
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
