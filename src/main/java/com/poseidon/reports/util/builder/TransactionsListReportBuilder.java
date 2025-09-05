package com.poseidon.reports.util.builder;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignRectangle;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;

/**
 * Builder for Transactions List Report.
 */
public class TransactionsListReportBuilder extends ReportBuilder {

    public JasperReport build() throws JRException {
        JasperDesign jasperDesign = new JasperDesign();

        setupBasicReportProperties(jasperDesign, "transactionsListReport", 842, 595, 802);

        // Create and add styles
        jasperDesign.addStyle(createNormalStyle());
        jasperDesign.addStyle(createBoldStyle());

        // Add fields
        addTransactionFields(jasperDesign);

        // Create bands
        jasperDesign.setTitle(createTransactionTitleBand());
        jasperDesign.setColumnHeader(createTransactionColumnHeaderBand());
        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(createTransactionDetailBand());

        return JasperCompileManager.compileReport(jasperDesign);
    }

    private void addTransactionFields(final JasperDesign jasperDesign) throws JRException {
        // Update field names to match those in TransactionVO (from log)
        jasperDesign.addField(createField("tagNo", java.lang.String.class)); // Used as invoice number
        jasperDesign.addField(createField("customerName", java.lang.String.class));
        jasperDesign.addField(createField("dateReported", java.lang.String.class));
        // Add any other fields from TransactionVO that might be useful
        jasperDesign.addField(createField("makeName", java.lang.String.class));
        jasperDesign.addField(createField("modelName", java.lang.String.class));
        jasperDesign.addField(createField("serialNo", java.lang.String.class));
        jasperDesign.addField(createField("status", java.lang.String.class));
    }

    private JRDesignBand createTransactionTitleBand() {
        JRDesignBand titleBand = new JRDesignBand();
        titleBand.setHeight(80);

        // Title background
        JRDesignRectangle titleRect = new JRDesignRectangle();
        titleRect.setX(0);
        titleRect.setY(10);
        titleRect.setWidth(802);
        titleRect.setHeight(40);
        titleRect.setBackcolor(LIGHT_GRAY);
        titleBand.addElement(titleRect);

        // Title text
        JRDesignStaticText titleText = new JRDesignStaticText();
        titleText.setX(0);
        titleText.setY(10);
        titleText.setWidth(802);
        titleText.setHeight(40);
        titleText.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        titleText.setText("Transactions List Report");
        titleText.setFontSize(28f);
        titleText.setBold(true);
        titleBand.addElement(titleText);

        // Date range info
        JRDesignStaticText dateRangeText = new JRDesignStaticText();
        dateRangeText.setX(20);
        dateRangeText.setY(55);
        dateRangeText.setWidth(400);
        dateRangeText.setHeight(20);
        dateRangeText.setText("Transaction Summary Report");
        dateRangeText.setFontSize(14f);
        titleBand.addElement(dateRangeText);

        return titleBand;
    }

    private JRDesignBand createTransactionColumnHeaderBand() {
        JRDesignBand columnHeaderBand = new JRDesignBand();
        columnHeaderBand.setHeight(30);

        // Header background
        JRDesignRectangle headerRect = new JRDesignRectangle();
        headerRect.setX(0);
        headerRect.setY(0);
        headerRect.setWidth(802);
        headerRect.setHeight(30);
        headerRect.setBackcolor(LIGHT_GRAY);
        columnHeaderBand.addElement(headerRect);

        // Column headers - updated to match available fields
        int xPos = 5;
        int[] colWidths = {80, 80, 150, 100, 100, 100, 80, 112};
        String[] headers = {"TagNo", "Customer Name", "Reported Date", "Make", "Model", "Serial no", "Status"};

        for (int i = 0; i < headers.length; i++) {
            JRDesignStaticText header = new JRDesignStaticText();
            header.setX(xPos);
            header.setY(5);
            header.setWidth(colWidths[i]);
            header.setHeight(20);
            header.setText(headers[i]);
            header.setBold(true);
            header.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
            header.setFontSize(11f);
            columnHeaderBand.addElement(header);

            // Add vertical separators
            if (i < headers.length - 1) {
                addVerticalLine(columnHeaderBand, xPos + colWidths[i], 0, 1, 30);
            }

            xPos += colWidths[i];
        }

        // Add border around header
        addHorizontalLine(columnHeaderBand, 0, 0, 802, 1);
        addHorizontalLine(columnHeaderBand, 0, 29, 802, 1);
        addVerticalLine(columnHeaderBand, 0, 0, 1, 30);
        addVerticalLine(columnHeaderBand, 801, 0, 1, 30);

        return columnHeaderBand;
    }

    private JRDesignBand createTransactionDetailBand() {
        JRDesignBand detailBand = new JRDesignBand();
        detailBand.setHeight(25);

        int xPos = 5;
        int[] colWidths = {80, 80, 150, 100, 100, 100, 80, 112};
        // Updated to match the fields in TransactionVO
        String[] fieldNames = {
                "tagNo",
                "customerName",
                "dateReported",
                "makeName",
                "modelName",
                "serialNo",
                "status"};

        for (int i = 0; i < fieldNames.length; i++) {
            JRDesignTextField field = new JRDesignTextField();
            field.setX(xPos);
            field.setY(2);
            field.setWidth(colWidths[i] - 2);
            field.setHeight(20);
            field.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
            field.setFontSize(10f);

            JRDesignExpression expr = new JRDesignExpression();
            expr.setText("$F{" + fieldNames[i] + "}");
            field.setExpression(expr);
            detailBand.addElement(field);

            // Add vertical separators
            if (i < fieldNames.length - 1) {
                addVerticalLine(detailBand, xPos + colWidths[i], 0, 1, 25);
            }

            xPos += colWidths[i];
        }

        // Add borders
        addHorizontalLine(detailBand, 0, 24, 802, 1);
        addVerticalLine(detailBand, 0, 0, 1, 25);
        addVerticalLine(detailBand, 801, 0, 1, 25);

        return detailBand;
    }
}
