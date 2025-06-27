package com.poseidon.reports.util;

import com.poseidon.make.domain.MakeAndModelVO;
import com.poseidon.reports.domain.ExportList;
import com.poseidon.transaction.domain.TransactionVO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.pdf.JRPdfExporter;
import net.sf.jasperreports.poi.export.JRXlsExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class ReportsUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ReportsUtil.class);
    private static final String FILENAME = "attachment;filename=";
    private static final String X_FRAME_OPTIONS = "X-Frame-Options";
    private static final String SAME_ORIGIN = "SAMEORIGIN";
    private static final String TEXT_HTML = "text/html";
    private static final String CONTENT_DISPOSITION = "Content-disposition";
    private static final String CONTENT_DISPOSITION1 = "Content-Disposition";

    public TransactionVO getSearchTransaction() {
        var searchVO = new TransactionVO();
        searchVO.setModelId(0L);
        searchVO.setMakeId(0L);
        return searchVO;
    }

    public MakeAndModelVO getSearchMakeAndModelVO() {
        var searchVO = new MakeAndModelVO();
        searchVO.setMakeId(0L);
        searchVO.setModelId(0L);
        return searchVO;
    }

    /**
     * This method is for generating the jasper report.
     *
     * @param httpServletResponse the current HTTP response
     * @param jasperPrint         jasperPrint instance
     * @param reportFileName      reportFileName instance
     * @param reportType          reportType instance
     */
    public void generateJasperReport(final HttpServletResponse httpServletResponse,
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
                    httpServletResponse.setHeader(CONTENT_DISPOSITION1, FILENAME + reportFileName + ".xls;");
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
                            FILENAME + reportFileName + ".docx;");
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
        xlsExporter.setConfiguration(ReportingConfigurations.configurationReportXlsx());
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
}
