package com.poseidon.reports.util.builder;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Factory for creating different types of report builders.
 */
public final class ReportBuilderFactory {

    private ReportBuilderFactory() {
        // Private constructor to prevent instantiation
    }

    public static JasperReport createReport(final String reportType) throws JRException {
        return switch (reportType) {
            case "makeListReport" -> new MakeDetailsReportBuilder().build();
            case "modelListReport" -> new ModelDetailsReportBuilder().build();
            case "callReport" -> new CallReportBuilder().build();
            case "errorReport" -> new ErrorReportBuilder().build();
            case "serviceBillReport" -> new ServiceBillReportBuilder().build();
            case "transactionsListReport" -> new TransactionsListReportBuilder().build();
            default -> throw new IllegalArgumentException("Unknown report type: " + reportType);
        };
    }
}

