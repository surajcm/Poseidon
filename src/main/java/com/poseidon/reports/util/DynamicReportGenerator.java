package com.poseidon.reports.util;

import com.poseidon.reports.util.builder.ReportBuilderFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Utility class to create JasperReports dynamically without using JRXML files.
 * This approach avoids XML parsing issues completely.
 *
 * <p>
 * Refactored to use the Builder pattern with separate classes for each report type.
 * </p>
 */
public final class DynamicReportGenerator {

    private DynamicReportGenerator() {
        // Private constructor to prevent instantiation
    }

    /**
     * Creates a dynamic "Make Details" report.
     *
     * @return The compiled JasperReport
     * @throws JRException if there is an error creating the report
     */
    public static JasperReport createMakeDetailsReport() throws JRException {
        return ReportBuilderFactory.createReport("makeListReport");
    }

    /**
     * Creates a dynamic "Model Details" report.
     *
     * @return The compiled JasperReport
     * @throws JRException if there is an error creating the report
     */
    public static JasperReport createModelDetailsReport() throws JRException {
        return ReportBuilderFactory.createReport("modelListReport");
    }

    /**
     * Creates a dynamic "Call Report".
     *
     * @return The compiled JasperReport
     * @throws JRException if there is an error creating the report
     */
    public static JasperReport createCallReport() throws JRException {
        return ReportBuilderFactory.createReport("callReport");
    }

    /**
     * Creates a dynamic "Error Report".
     *
     * @return The compiled JasperReport
     * @throws JRException if there is an error creating the report
     */
    public static JasperReport createErrorReport() throws JRException {
        return ReportBuilderFactory.createReport("errorReport");
    }

    /**
     * Creates a dynamic "Service Bill Report".
     *
     * @return The compiled JasperReport
     * @throws JRException if there is an error creating the report
     */
    public static JasperReport createServiceBillReport() throws JRException {
        return ReportBuilderFactory.createReport("serviceBillReport");
    }

    /**
     * Creates a dynamic "Transactions List Report".
     *
     * @return The compiled JasperReport
     * @throws JRException if there is an error creating the report
     */
    public static JasperReport createTransactionsListReport() throws JRException {
        return ReportBuilderFactory.createReport("transactionsListReport");
    }

}
