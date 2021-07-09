package com.poseidon.reports.web.controller;

import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.make.domain.MakeVO;
import com.poseidon.make.service.MakeService;
import com.poseidon.reports.domain.ReportsVO;
import com.poseidon.reports.service.ReportsService;
import com.poseidon.reports.web.form.ReportsForm;
import com.poseidon.transaction.domain.TransactionVO;
import com.poseidon.util.CommonUtils;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporterParameter;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.export.XlsReportConfiguration;
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
import java.util.Arrays;
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
        List<MakeVO> makeVOs = null;
        try {
            makeVOs = makeService.fetchMakes();
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
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
                var reportType = reportsForm.getCurrentReport().getExportTo();
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
            var reportType = reportsForm.getCurrentReport().getExportTo();
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
            var reportType = reportsForm.getCurrentReport().getExportTo();
            reportsForm.getCurrentReport().setRptfilename(reportFileName);
            var jasperReport = createJasperReport(reportFileName);
            var jasperPrint = reportsService.getTransactionsListReport(jasperReport,
                    reportsForm.getCurrentReport(),
                    reportsForm.getTxnReportTransactionVO());
            LOG.info(jasperPrint.toString());
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
            var reportType = reportsForm.getCurrentReport().getExportTo();
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
            var reportType = reportsForm.getCurrentReport().getExportTo();
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
            var reportType = reportsForm.getCurrentReport().getExportTo();
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
        ServletRequestAttributes attr = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
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
                                      final String reportType) {
        try {
            LOG.info("In generateJasperReport method");
            httpServletResponse.setHeader("X-Frame-Options", "SAMEORIGIN");
            if (ExportList.EXCEL.name().equalsIgnoreCase(reportType)) {
                generateExcelReport(httpServletResponse, jasperPrint, reportFileName, reportType);
            } else if (ExportList.PDF.name().equalsIgnoreCase(reportType)) {
                generatePDFReport(httpServletResponse, jasperPrint, reportFileName, reportType);
            } else if (ExportList.WORD.name().equalsIgnoreCase(reportType)) {
                generateWordReport(httpServletResponse, jasperPrint, reportFileName, reportType);
            } else {
                generateHTMLReport(httpServletResponse, jasperPrint);
            }
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    }

    private void generateHTMLReport(final HttpServletResponse httpServletResponse,
                                    final JasperPrint jasperPrint) throws JRException, IOException {
        httpServletResponse.setContentType("text/html");
        SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();
        //configuration.se
        JRAbstractExporter jrExporter = new HtmlExporter();
        jrExporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT, jasperPrint);
        var baos = new ByteArrayOutputStream();
        jrExporter.setParameter(JRHtmlExporterParameter.OUTPUT_STREAM, baos);
        jrExporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                Boolean.TRUE);
        jrExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
        jrExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                Boolean.FALSE);
        /*jrExporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                        Boolean.FALSE);*/
        jrExporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                Boolean.TRUE);
        jrExporter.exportReport();
        byte[] output = baos.toByteArray();
        httpServletResponse.setContentLength(output.length);
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(output, 0, output.length);
        outputStream.flush();
        outputStream.close();
    }

    private void generateWordReport(final HttpServletResponse httpServletResponse,
                                    final JasperPrint jasperPrint,
                                    final String reportFileName,
                                    final String reportType) throws JRException, IOException {
        LOG.info("WORD -- > reportFileName ---> " + reportFileName + reportType);
        var docxReport = new ByteArrayOutputStream();
        var exporter = new JRDocxExporter();
        exporter.setParameter(JRDocxExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRDocxExporterParameter.OUTPUT_STREAM, docxReport);
        exporter.setParameter(JRDocxExporterParameter.FLEXIBLE_ROW_HEIGHT, Boolean.TRUE);
        exporter.setParameter(JRDocxExporterParameter.CHARACTER_ENCODING, "UTF-8");
        exporter.exportReport();
        // Send response
        byte[] bytes = docxReport.toByteArray();
        httpServletResponse.addHeader("Content-disposition",
                FILENAME + reportFileName + ".doc;");
        httpServletResponse.setContentType("application/vnd.ms-word");
        httpServletResponse.setContentLength(bytes.length);
        httpServletResponse.getOutputStream().write(bytes, 0, bytes.length);
        httpServletResponse.getOutputStream().flush();
        httpServletResponse.getOutputStream().close();
    }

    private void generatePDFReport(final HttpServletResponse httpServletResponse,
                                   final JasperPrint jasperPrint,
                                   final String reportFileName,
                                   final String reportType) throws JRException, IOException {
        LOG.info("PDF -- > reportFileName ---> " + reportFileName + reportType);
        var mimetype = httpServletResponse.getContentType();
        httpServletResponse.setContentType((mimetype != null) ? mimetype : "application/pdf");
        httpServletResponse.setHeader("Content-Disposition",
                FILENAME + reportFileName + ";");
        JRExporter jrPdfExporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
        jrPdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        jrPdfExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        jrPdfExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        jrPdfExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
        var baos = new ByteArrayOutputStream();
        jrPdfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
        jrPdfExporter.exportReport();
        byte[] output = baos.toByteArray();
        httpServletResponse.setContentLength(output.length);
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(output, 0, output.length);
        outputStream.flush();
        outputStream.close();
    }

    private void generateExcelReport(final HttpServletResponse httpServletResponse,
                                     final JasperPrint jasperPrint,
                                     final String reportFileName,
                                     final String reportType) throws JRException, IOException {
        LOG.info("ExcelReport -- > reportFileName ---> " + reportFileName + reportType);
        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-Disposition", FILENAME + reportFileName + ";");
        JRAbstractExporter jrExporter = new JRXlsExporter();
        XlsReportConfiguration config = new SimpleXlsxReportConfiguration();
        //config.isDetectCellType()
        //jrExporter.setConfiguration();
        jrExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
        var baos = new ByteArrayOutputStream();
        jrExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
        jrExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
        jrExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                Boolean.TRUE);
        jrExporter.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER,
                Boolean.TRUE);
        jrExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                Boolean.FALSE);
        jrExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
        jrExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                Boolean.TRUE);
        jrExporter.setParameter(JRXlsExporterParameter.OFFSET_X, 1);
        jrExporter.setParameter(JRXlsExporterParameter.OFFSET_Y, 1);
        jrExporter.exportReport();
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

    private enum InvoiceStatus {
        NEW,
        ACCEPTED,
        VERIFIED,
        CLOSED,
        REJECTED;

        public static List<String> asList() {
            return Arrays.stream(InvoiceStatus.values()).map(Enum::name).toList();
        }
    }

    private enum ExportList {
        EXCEL,
        WORD,
        PDF;

        public static List<String> asList() {
            return Arrays.stream(ExportList.values()).map(Enum::name).toList();
        }
    }
}
