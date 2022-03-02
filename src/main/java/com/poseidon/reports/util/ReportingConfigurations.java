package com.poseidon.reports.util;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

import java.io.OutputStream;

public class ReportingConfigurations {

    private ReportingConfigurations() {
    }

    public static SimpleXlsReportConfiguration configurationReportXls() {
        var xlsReportConfiguration = new SimpleXlsReportConfiguration();
        xlsReportConfiguration.setOnePagePerSheet(false);
        xlsReportConfiguration.setWhitePageBackground(true);
        xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
        xlsReportConfiguration.setRemoveEmptySpaceBetweenColumns(true);
        xlsReportConfiguration.setDetectCellType(true);
        xlsReportConfiguration.setFontSizeFixEnabled(false);
        xlsReportConfiguration.setIgnoreCellBorder(true);
        return xlsReportConfiguration;
    }

    public static SimpleHtmlReportConfiguration configurationForHTML() {
        var configuration = new SimpleHtmlReportConfiguration();
        configuration.setWhitePageBackground(true);
        //configuration.setRemoveEmptySpaceBetweenRows(true);
        configuration.setWrapBreakWord(true);
        return configuration;
    }

    public static SimpleDocxReportConfiguration docxReportConfiguration() {
        var configuration = new SimpleDocxReportConfiguration();
        configuration.setFlexibleRowHeight(true);
        return configuration;
    }

    public static SimplePdfReportConfiguration pdfReportConfiguration() {
        var configuration = new SimplePdfReportConfiguration();
        configuration.setSizePageToContent(true);
        return configuration;
    }

    public static SimpleExporterInput exporter(final JasperPrint jasperPrint) {
        return new SimpleExporterInput(jasperPrint);
    }

    public static SimpleOutputStreamExporterOutput exporterOutput(final OutputStream outputStream) {
        return new SimpleOutputStreamExporterOutput(outputStream);
    }

    public static SimpleHtmlExporterOutput exportHTML(final OutputStream outputStream) {
        return new SimpleHtmlExporterOutput(outputStream);
    }
}
