package com.poseidon.Reports.web.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.poseidon.Reports.delegate.ReportsDelegate;
import com.poseidon.Reports.web.form.ReportsForm;
import com.poseidon.Reports.domain.ReportsVO;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Locale;

/**
 * User: Suraj
 * Date: Jun 3, 2012
 * Time: 10:40:47 AM
 */
public class ReportsController extends MultiActionController {
    private ReportsDelegate reportsDelegate;
    private final Log log = LogFactory.getLog(ReportsController.class);

    public ReportsDelegate getReportsDelegate() {
        return reportsDelegate;
    }

    public void setReportsDelegate(ReportsDelegate reportsDelegate) {
        this.reportsDelegate = reportsDelegate;
    }

    public ModelAndView List(HttpServletRequest request,
                             HttpServletResponse response, ReportsForm reportsForm) {
        log.info(" Inside List method of ReportsController ");
        log.info(" form details are" + reportsForm);

        List<ReportsVO> reportsVOs = null;
        try {
            reportsVOs = getReportsDelegate().generateDailyReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (reportsVOs != null) {
            for (ReportsVO reportsVO : reportsVOs) {
                log.info(" reportsVO is " + reportsVO);
            }
            reportsForm.setReportsVOs(reportsVOs);
        }
        reportsForm.setSearchReports(new ReportsVO());
        reportsForm.setLoggedInRole(reportsForm.getLoggedInRole());
        reportsForm.setLoggedInUser(reportsForm.getLoggedInUser());
        return new ModelAndView("reports/List", "reportsForm", reportsForm);
    }

    public ModelAndView getMakeDetailsReport(HttpServletRequest httpServletRequest,
                                               HttpServletResponse httpServletResponse,
                                               ReportsForm reportsForm) {
        log.info(" Inside getMakeDetailsReport method of ReportsController ");
        log.info(" form details are" + reportsForm);
        JasperReport jasperReport = null;
        JasperPrint jasperPrint = null;
        try {
            logger.info("Locale-->" + httpServletRequest.getLocale());
            reportsForm.setCurrentReport(new ReportsVO());
            reportsForm.getCurrentReport().setLocale(Locale.US);

            String rptfilename = "makeListReport";//httpServletRequest.getParameter("rptfilename");
            String reportType = reportsForm.getCurrentReport().getExportTo();
            reportsForm.getCurrentReport().setRptfilename(rptfilename);
            String path = getServletContext().getRealPath("/reports");
            log.info(" going to compile report");
            jasperReport = JasperCompileManager.compileReport(path + '/' + rptfilename + ".jrxml");

            jasperPrint = getReportsDelegate().getMakeDetailsChart(jasperReport,
                    reportsForm.getCurrentReport());
            logger.info(jasperPrint.toString());
            getJasperReport(httpServletRequest, httpServletResponse, jasperPrint, rptfilename, reportType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is used for generating the jasper report
     *
     * @param httpServletRequest the current HTTP request
     * @param httpServletResponse the current HTTP response
     * @param jasperPrint jasperPrint instance
     * @param rptfilename rptfilename instance
     * @param reportType reportType instance
     */
    protected void getJasperReport(HttpServletRequest httpServletRequest,
                                   HttpServletResponse httpServletResponse,
                                   JasperPrint jasperPrint,
                                   String rptfilename,
                                   String reportType) {
        JRAbstractExporter jrExporter = null;
        byte[] output = null;
        ServletOutputStream ouputStream = null;
        try {
            logger.info("****In getJasperReport method****");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //context = new ClassPathXmlApplicationContext(VAL_CONTEXT_FILE_LOC);
            if ("EXCEL".equalsIgnoreCase(reportType)) {
                logger.info("excelReport--rptfilename---" + reportType);
                httpServletResponse.setContentType("application/vnd.ms-excel");
                httpServletResponse.setHeader("Content-Disposition",
                        "attachment;filename=" + rptfilename + ";");
                jrExporter = new JRXlsExporter();
                jrExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                jrExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
                jrExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,baos);
                jrExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
                jrExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                                Boolean.TRUE);
                jrExporter.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER,
                                Boolean.TRUE);
                jrExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                            Boolean.FALSE);
                jrExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
                jrExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                        Boolean.TRUE);
                jrExporter.setParameter(JRXlsExporterParameter.OFFSET_X,new Integer(1));
                jrExporter.setParameter(JRXlsExporterParameter.OFFSET_Y,new Integer(1));
                jrExporter.exportReport();
                output = baos.toByteArray();
                httpServletResponse.setContentLength(output.length);
                ouputStream = httpServletResponse.getOutputStream();
                ouputStream.write(output, 0, output.length);
                ouputStream.flush();
                ouputStream.close();
            } else if ("PDF".equalsIgnoreCase(reportType)) {
                logger.info("PDF--rptfilename---" + reportType);
                ServletOutputStream op = httpServletResponse.getOutputStream();
                String mimetype = httpServletResponse.getContentType();
                httpServletResponse.setContentType((mimetype != null) ? mimetype
                        : "application/pdf");
                httpServletResponse.setHeader("Content-Disposition",
                        "attachment;filename=" + rptfilename + ";");
                JRExporter jrPdfExporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
                jrPdfExporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
                jrPdfExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                                Boolean.TRUE);
                jrPdfExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                        Boolean.FALSE);
                jrPdfExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
                jrPdfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                        baos);
                jrPdfExporter.exportReport();
                output = baos.toByteArray();
                httpServletResponse.setContentLength(output.length);
                ouputStream = httpServletResponse.getOutputStream();
                ouputStream.write(output, 0, output.length);
                ouputStream.flush();
                ouputStream.close();
            } else {
                httpServletResponse.setContentType("text/html");
                jrExporter = new JRHtmlExporter();
                jrExporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT,jasperPrint);
                jrExporter.setParameter(JRHtmlExporterParameter.OUTPUT_STREAM,baos);
                jrExporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                                Boolean.TRUE);
                jrExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
                jrExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                        Boolean.FALSE);
                jrExporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                        Boolean.FALSE);
                jrExporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                        Boolean.TRUE);
                jrExporter.exportReport();
                output = baos.toByteArray();
                httpServletResponse.setContentLength(output.length);
                ouputStream = httpServletResponse.getOutputStream();
                ouputStream.write(output, 0, output.length);
                ouputStream.flush();
                ouputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
