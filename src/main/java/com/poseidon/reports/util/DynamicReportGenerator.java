package com.poseidon.reports.util;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignLine;
import net.sf.jasperreports.engine.design.JRDesignRectangle;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.LineStyleEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.engine.type.TextAdjustEnum;

import java.awt.Color;
import java.util.Map;

/**
 * Utility class to create JasperReports dynamically without using JRXML files.
 * This approach avoids XML parsing issues completely.
 */
public final class DynamicReportGenerator {

    private static final Color LIGHT_GRAY = new Color(204, 204, 204); // #CCCCCC

    private DynamicReportGenerator() {
        // Private constructor to prevent instantiation
    }

    /**
     * Creates a dynamic "Make Details" report with the same structure as makeListReport.jrxml.
     *
     * @return The compiled JasperReport
     * @throws JRException if there is an error creating the report
     */
    public static JasperReport createMakeDetailsReport() throws JRException {
        JasperDesign jasperDesign = new JasperDesign();

        // Set basic report properties
        jasperDesign.setName("makeListReport");
        jasperDesign.setPageWidth(595);
        jasperDesign.setPageHeight(842);
        jasperDesign.setColumnWidth(555);
        jasperDesign.setLeftMargin(20);
        jasperDesign.setRightMargin(20);
        jasperDesign.setTopMargin(20);
        jasperDesign.setBottomMargin(20);

        // Create and configure styles
        JRDesignStyle normalStyle = new JRDesignStyle();
        normalStyle.setName("Arial_Normal");
        normalStyle.setDefault(true);
        normalStyle.setFontName("Arial");
        normalStyle.setFontSize(12f);
        normalStyle.setPdfFontName("Helvetica");
        normalStyle.setPdfEncoding("Cp1252");
        jasperDesign.addStyle(normalStyle);

        JRDesignStyle boldStyle = new JRDesignStyle();
        boldStyle.setName("Arial_Bold");
        boldStyle.setFontName("Arial");
        boldStyle.setFontSize(12f);
        boldStyle.setBold(true);
        boldStyle.setPdfFontName("Helvetica-Bold");
        boldStyle.setPdfEncoding("Cp1252");
        jasperDesign.addStyle(boldStyle);

        // Add fields
        JRDesignField idField = new JRDesignField();
        idField.setName("id");
        idField.setValueClass(java.math.BigInteger.class);
        jasperDesign.addField(idField);

        JRDesignField makeNameField = new JRDesignField();
        makeNameField.setName("makeName");
        makeNameField.setValueClass(java.lang.String.class);
        jasperDesign.addField(makeNameField);

        JRDesignField descriptionField = new JRDesignField();
        descriptionField.setName("description");
        descriptionField.setValueClass(java.lang.String.class);
        jasperDesign.addField(descriptionField);

        // Create title band
        JRDesignBand titleBand = new JRDesignBand();
        titleBand.setHeight(73);

        // Add title background rectangles
        JRDesignRectangle titleRect = new JRDesignRectangle();
        titleRect.setX(0);
        titleRect.setY(1);
        titleRect.setWidth(500);
        titleRect.setHeight(35);
        titleRect.setBackcolor(LIGHT_GRAY);
        titleBand.addElement(titleRect);

        JRDesignRectangle headerRect = new JRDesignRectangle();
        headerRect.setX(1);
        headerRect.setY(36);
        headerRect.setWidth(499);
        headerRect.setHeight(36);
        headerRect.setForecolor(Color.WHITE);
        headerRect.setBackcolor(LIGHT_GRAY);
        titleBand.addElement(headerRect);

        // Create title heading
        JRDesignStaticText titleText = new JRDesignStaticText();
        titleText.setX(28);
        titleText.setY(2);
        titleText.setWidth(463);
        titleText.setHeight(30);
        titleText.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        titleText.setStyle(boldStyle);
        titleText.setText("Make Details Report");
        titleText.setFontSize(24f);
        titleBand.addElement(titleText);

        // Add column headers
        JRDesignStaticText makeNameHeader = new JRDesignStaticText();
        makeNameHeader.setX(13);
        makeNameHeader.setY(38);
        makeNameHeader.setWidth(233);
        makeNameHeader.setHeight(32);
        makeNameHeader.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        makeNameHeader.setStyle(boldStyle);
        makeNameHeader.setText("Make Name");
        makeNameHeader.setFontSize(20f);
        titleBand.addElement(makeNameHeader);

        JRDesignStaticText descriptionHeader = new JRDesignStaticText();
        descriptionHeader.setX(254);
        descriptionHeader.setY(38);
        descriptionHeader.setWidth(244);
        descriptionHeader.setHeight(32);
        descriptionHeader.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        descriptionHeader.setStyle(boldStyle);
        descriptionHeader.setText("Make Description");
        descriptionHeader.setFontSize(20f);
        titleBand.addElement(descriptionHeader);

        // Add borders - Horizontal lines
        addHorizontalLine(titleBand, 0, 0, 500, 1); // Top border
        addHorizontalLine(titleBand, 1, 36, 500, 1); // Divider between title and header
        addHorizontalLine(titleBand, 1, 72, 499, 1); // Bottom border of header

        // Add borders - Vertical lines
        addVerticalLine(titleBand, 0, 0, 1, 37); // Left border of title
        addVerticalLine(titleBand, 0, 37, 1, 36); // Left border of header
        addVerticalLine(titleBand, 500, 0, 1, 36); // Right border of title
        addVerticalLine(titleBand, 500, 37, 1, 36); // Right border of header
        addVerticalLine(titleBand, 250, 37, 1, 35); // Divider between Make Name and Description columns

        // Add title band to the report
        jasperDesign.setTitle(titleBand);

        // Create detail band
        JRDesignBand detailBand = new JRDesignBand();
        detailBand.setHeight(40);

        // Add make name field to detail
        JRDesignTextField makeNameField1 = new JRDesignTextField();
        makeNameField1.setX(2);
        makeNameField1.setY(1);
        makeNameField1.setWidth(244);
        makeNameField1.setHeight(31);
        makeNameField1.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        makeNameField1.setStyle(normalStyle);
        makeNameField1.setFontSize(18f);

        JRDesignExpression makeNameExpr = new JRDesignExpression();
        makeNameExpr.setText("$F{makeName}");
        makeNameField1.setExpression(makeNameExpr);
        detailBand.addElement(makeNameField1);

        // Add description field to detail
        JRDesignTextField descriptionField1 = new JRDesignTextField();
        descriptionField1.setX(254);
        descriptionField1.setY(0);
        descriptionField1.setWidth(244);
        descriptionField1.setHeight(34);
        descriptionField1.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        descriptionField1.setStyle(normalStyle);
        descriptionField1.setFontSize(18f);

        JRDesignExpression descriptionExpr = new JRDesignExpression();
        descriptionExpr.setText("$F{description}");
        descriptionField1.setExpression(descriptionExpr);
        detailBand.addElement(descriptionField1);

        // Add borders for detail band - Vertical lines
        addVerticalLine(detailBand, 0, 0, 1, 36); // Left border
        addVerticalLine(detailBand, 250, 0, 1, 36); // Center divider
        addVerticalLine(detailBand, 500, 0, 1, 37); // Right border

        // Add borders for detail band - Horizontal line
        addHorizontalLine(detailBand, 0, 36, 500, 1); // Bottom border

        // Add detail band to the report
        ((JRDesignSection)jasperDesign.getDetailSection()).addBand(detailBand);

        // Compile and return the report
        return JasperCompileManager.compileReport(jasperDesign);
    }

    /**
     * Helper method to add a horizontal line to a band.
     */
    private static void addHorizontalLine(JRDesignBand band, int xX, int yY, int width, int height) {
        JRDesignLine line = new JRDesignLine();
        line.setX(xX);
        line.setY(yY);
        line.setWidth(width);
        line.setHeight(height);
        // Use compatible API instead of setLineStyle
        line.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        band.addElement(line);
    }

    /**
     * Helper method to add a vertical line to a band
     */
    private static void addVerticalLine(JRDesignBand band, int xX, int yY, int width, int height) {
        JRDesignLine line = new JRDesignLine();
        line.setX(xX);
        line.setY(yY);
        line.setWidth(width);
        line.setHeight(height);
        // Use compatible API instead of setLineStyle
        line.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        band.addElement(line);
    }

    /**
     * Creates and fills a JasperPrint with the given data source.
     *
     * @param dataSource the data source for the report
     * @param parameters report parameters
     * @return the filled JasperPrint ready for export
     * @throws JRException if there is an error filling the report
     */
    public static JasperPrint fillMakeDetailsReport(JRDataSource dataSource, Map<String, Object> parameters)
            throws JRException {
        JasperReport report = createMakeDetailsReport();
        return JasperFillManager.fillReport(report, parameters, dataSource);
    }

    /**
     * Creates a dynamic "Model Details" report with the same structure as modelListReport.jrxml.
     *
     * @return The compiled JasperReport
     * @throws JRException if there is an error creating the report
     */
    public static JasperReport createModelDetailsReport() throws JRException {
        JasperDesign jasperDesign = new JasperDesign();

        // Set basic report properties
        jasperDesign.setName("modelListReport");
        jasperDesign.setPageWidth(540);
        jasperDesign.setPageHeight(842);
        jasperDesign.setColumnWidth(500);
        jasperDesign.setLeftMargin(20);
        jasperDesign.setRightMargin(20);
        jasperDesign.setTopMargin(20);
        jasperDesign.setBottomMargin(20);

        // Create and configure styles
        JRDesignStyle normalStyle = new JRDesignStyle();
        normalStyle.setName("Arial_Normal");
        normalStyle.setDefault(true);
        normalStyle.setFontName("Arial");
        normalStyle.setFontSize(12f);
        normalStyle.setPdfFontName("Helvetica");
        normalStyle.setPdfEncoding("Cp1252");
        jasperDesign.addStyle(normalStyle);

        JRDesignStyle boldStyle = new JRDesignStyle();
        boldStyle.setName("Arial_Bold");
        boldStyle.setFontName("Arial");
        boldStyle.setFontSize(12f);
        boldStyle.setBold(true);
        boldStyle.setPdfFontName("Helvetica-Bold");
        boldStyle.setPdfEncoding("Cp1252");
        jasperDesign.addStyle(boldStyle);

        // Add fields
        JRDesignField idField = new JRDesignField();
        idField.setName("id");
        idField.setValueClass(java.math.BigInteger.class);
        jasperDesign.addField(idField);

        JRDesignField makeNameField = new JRDesignField();
        makeNameField.setName("makeName");
        makeNameField.setValueClass(java.lang.String.class);
        jasperDesign.addField(makeNameField);

        JRDesignField modelNameField = new JRDesignField();
        modelNameField.setName("modelName");
        modelNameField.setValueClass(java.lang.String.class);
        jasperDesign.addField(modelNameField);

        // Create title band
        JRDesignBand titleBand = new JRDesignBand();
        titleBand.setHeight(75);

        // Add title background rectangles
        JRDesignRectangle titleRect = new JRDesignRectangle();
        titleRect.setX(1);
        titleRect.setY(1);
        titleRect.setWidth(497);
        titleRect.setHeight(32);
        titleRect.setBackcolor(LIGHT_GRAY);
        titleBand.addElement(titleRect);

        JRDesignRectangle headerRect = new JRDesignRectangle();
        headerRect.setX(1);
        headerRect.setY(36);
        headerRect.setWidth(480);
        headerRect.setHeight(36);
        headerRect.setForecolor(Color.WHITE);
        headerRect.setBackcolor(LIGHT_GRAY);
        titleBand.addElement(headerRect);

        // Create title heading
        JRDesignStaticText titleText = new JRDesignStaticText();
        titleText.setX(1);
        titleText.setY(1);
        titleText.setWidth(480);
        titleText.setHeight(32);
        titleText.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        titleText.setStyle(boldStyle);
        titleText.setText("Model Details Report");
        titleText.setFontSize(26f);
        titleBand.addElement(titleText);

        // Add column headers
        JRDesignStaticText makeNameHeader = new JRDesignStaticText();
        makeNameHeader.setX(2);
        makeNameHeader.setY(38);
        makeNameHeader.setWidth(244);
        makeNameHeader.setHeight(32);
        makeNameHeader.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        makeNameHeader.setStyle(boldStyle);
        makeNameHeader.setText("Make Name");
        makeNameHeader.setFontSize(20f);
        titleBand.addElement(makeNameHeader);

        JRDesignStaticText modelNameHeader = new JRDesignStaticText();
        modelNameHeader.setX(254);
        modelNameHeader.setY(41);
        modelNameHeader.setWidth(244);
        modelNameHeader.setHeight(32);
        modelNameHeader.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        modelNameHeader.setStyle(boldStyle);
        modelNameHeader.setText("Model Name");
        modelNameHeader.setFontSize(20f);
        titleBand.addElement(modelNameHeader);

        // Add borders - Horizontal lines for title band
        addHorizontalLine(titleBand, 0, 0, 498, 1); // Top border
        addHorizontalLine(titleBand, 0, 34, 498, 1); // Line between title and header
        addHorizontalLine(titleBand, 1, 73, 497, 1); // Bottom line of header

        // Add borders - Vertical lines for title band
        addVerticalLine(titleBand, 0, 1, 1, 33); // Left border of title
        addVerticalLine(titleBand, 498, 0, 1, 33); // Right border of title
        addVerticalLine(titleBand, 0, 36, 1, 37); // Left border of header
        addVerticalLine(titleBand, 498, 35, 1, 37); // Right border of header
        addVerticalLine(titleBand, 250, 36, 1, 36); // Center divider between headers

        // Add title band to the report
        jasperDesign.setTitle(titleBand);

        // Create detail band
        JRDesignBand detailBand = new JRDesignBand();
        detailBand.setHeight(36); // Adjusted to match the border lines

        // Add make name field to detail
        JRDesignTextField makeNameField1 = new JRDesignTextField();
        makeNameField1.setX(2);
        makeNameField1.setY(1);
        makeNameField1.setWidth(244);
        makeNameField1.setHeight(34);
        makeNameField1.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        makeNameField1.setStyle(normalStyle);
        makeNameField1.setFontSize(18f);

        JRDesignExpression makeNameExpr = new JRDesignExpression();
        makeNameExpr.setText("$F{makeName}");
        makeNameField1.setExpression(makeNameExpr);
        detailBand.addElement(makeNameField1);

        // Add model name field to detail
        JRDesignTextField modelNameField1 = new JRDesignTextField();
        modelNameField1.setX(254);
        modelNameField1.setY(0);
        modelNameField1.setWidth(244);
        modelNameField1.setHeight(34);
        modelNameField1.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        modelNameField1.setStyle(normalStyle);
        modelNameField1.setFontSize(18f);

        JRDesignExpression modelNameExpr = new JRDesignExpression();
        modelNameExpr.setText("$F{modelName}");
        modelNameField1.setExpression(modelNameExpr);
        detailBand.addElement(modelNameField1);

        // Add borders for detail band
        addVerticalLine(detailBand, 0, 0, 1, 35); // Left border
        addVerticalLine(detailBand, 250, 0, 1, 35); // Center divider
        addVerticalLine(detailBand, 498, 0, 1, 34); // Right border
        addHorizontalLine(detailBand, 0, 35, 498, 1); // Bottom border

        // Add detail band to the report
        ((JRDesignSection)jasperDesign.getDetailSection()).addBand(detailBand);

        // Compile and return the report
        return JasperCompileManager.compileReport(jasperDesign);
    }

    /**
     * Creates and fills a Model Details JasperPrint with the given data source.
     *
     * @param dataSource the data source for the report
     * @param parameters report parameters
     * @return the filled JasperPrint ready for export
     * @throws JRException if there is an error filling the report
     */
    public static JasperPrint fillModelDetailsReport(JRDataSource dataSource, Map<String, Object> parameters)
            throws JRException {
        JasperReport report = createModelDetailsReport();
        return JasperFillManager.fillReport(report, parameters, dataSource);
    }

    /**
     * Creates a dynamic "Call Report" with the same structure as callReport.jrxml.
     *
     * @return The compiled JasperReport
     * @throws JRException if there is an error creating the report
     */
    public static JasperReport createCallReport() throws JRException {
        JasperDesign jasperDesign = new JasperDesign();

        // Set basic report properties
        jasperDesign.setName("callReport");
        jasperDesign.setPageWidth(595);
        jasperDesign.setPageHeight(842);
        jasperDesign.setColumnWidth(555);
        jasperDesign.setLeftMargin(20);
        jasperDesign.setRightMargin(20);
        jasperDesign.setTopMargin(20);
        jasperDesign.setBottomMargin(20);

        // Create and configure styles
        JRDesignStyle normalStyle = new JRDesignStyle();
        normalStyle.setName("Arial_Normal");
        normalStyle.setDefault(true);
        normalStyle.setFontName("Arial");
        normalStyle.setFontSize(11f);
        normalStyle.setPdfFontName("Helvetica");
        normalStyle.setPdfEncoding("Cp1252");
        jasperDesign.addStyle(normalStyle);

        JRDesignStyle boldStyle = new JRDesignStyle();
        boldStyle.setName("Arial_Bold");
        boldStyle.setFontName("Arial");
        boldStyle.setFontSize(12f);
        boldStyle.setBold(true);
        boldStyle.setPdfFontName("Helvetica-Bold");
        boldStyle.setPdfEncoding("Cp1252");
        jasperDesign.addStyle(boldStyle);

        // Add fields for the call report
        addCallReportFields(jasperDesign);

        // Create title band
        JRDesignBand titleBand = createCallReportTitleBand();
        jasperDesign.setTitle(titleBand);

        // Create page header
        JRDesignBand pageHeaderBand = createCallReportPageHeaderBand();
        jasperDesign.setPageHeader(pageHeaderBand);

        // Create detail band
        JRDesignBand detailBand = createCallReportDetailBand();
        ((JRDesignSection)jasperDesign.getDetailSection()).addBand(detailBand);

        // Create summary band with terms and conditions
        JRDesignBand summaryBand = createCallReportSummaryBand();
        jasperDesign.setSummary(summaryBand);

        // Compile and return the report
        return JasperCompileManager.compileReport(jasperDesign);
    }

    /**
     * Adds all required fields to the Call Report design
     */
    private static void addCallReportFields(JasperDesign jasperDesign) throws JRException {
        // ID field
        JRDesignField idField = new JRDesignField();
        idField.setName("id");
        idField.setValueClass(java.math.BigInteger.class);
        jasperDesign.addField(idField);

        // Tag number field
        JRDesignField tagNoField = new JRDesignField();
        tagNoField.setName("tagNo");
        tagNoField.setValueClass(java.lang.String.class);
        jasperDesign.addField(tagNoField);

        // Date reported field
        JRDesignField dateReportedField = new JRDesignField();
        dateReportedField.setName("dateReported");
        dateReportedField.setValueClass(java.time.LocalDateTime.class);
        jasperDesign.addField(dateReportedField);

        // Customer information fields
        JRDesignField customerNameField = new JRDesignField();
        customerNameField.setName("customerName");
        customerNameField.setValueClass(java.lang.String.class);
        jasperDesign.addField(customerNameField);

        JRDesignField addressField = new JRDesignField();
        addressField.setName("address");
        addressField.setValueClass(java.lang.String.class);
        jasperDesign.addField(addressField);

        JRDesignField phoneField = new JRDesignField();
        phoneField.setName("phone");
        phoneField.setValueClass(java.lang.String.class);
        jasperDesign.addField(phoneField);

        // Equipment information fields
        JRDesignField makeNameField = new JRDesignField();
        makeNameField.setName("makeName");
        makeNameField.setValueClass(java.lang.String.class);
        jasperDesign.addField(makeNameField);

        JRDesignField modelNameField = new JRDesignField();
        modelNameField.setName("modelName");
        modelNameField.setValueClass(java.lang.String.class);
        jasperDesign.addField(modelNameField);

        JRDesignField serialNoField = new JRDesignField();
        serialNoField.setName("serialNo");
        serialNoField.setValueClass(java.lang.String.class);
        jasperDesign.addField(serialNoField);

        JRDesignField accessoriesField = new JRDesignField();
        accessoriesField.setName("accessories");
        accessoriesField.setValueClass(java.lang.String.class);
        jasperDesign.addField(accessoriesField);

        // Complaint and diagnosis fields
        JRDesignField complaintReportedField = new JRDesignField();
        complaintReportedField.setName("complaintReported");
        complaintReportedField.setValueClass(java.lang.String.class);
        jasperDesign.addField(complaintReportedField);

        JRDesignField complaintDiagnosedField = new JRDesignField();
        complaintDiagnosedField.setName("complaintDiagnosed");
        complaintDiagnosedField.setValueClass(java.lang.String.class);
        jasperDesign.addField(complaintDiagnosedField);

        JRDesignField enggRemarkField = new JRDesignField();
        enggRemarkField.setName("enggRemark");
        enggRemarkField.setValueClass(java.lang.String.class);
        jasperDesign.addField(enggRemarkField);

        JRDesignField statusField = new JRDesignField();
        statusField.setName("status");
        statusField.setValueClass(java.lang.String.class);
        jasperDesign.addField(statusField);

        // Company information fields
        JRDesignField companyNameField = new JRDesignField();
        companyNameField.setName("companyName");
        companyNameField.setValueClass(java.lang.String.class);
        jasperDesign.addField(companyNameField);

        JRDesignField companyAddressField = new JRDesignField();
        companyAddressField.setName("companyAddress");
        companyAddressField.setValueClass(java.lang.String.class);
        jasperDesign.addField(companyAddressField);

        JRDesignField companyPhoneNumberField = new JRDesignField();
        companyPhoneNumberField.setName("companyPhoneNumber");
        companyPhoneNumberField.setValueClass(java.lang.String.class);
        jasperDesign.addField(companyPhoneNumberField);

        JRDesignField companyWebsiteField = new JRDesignField();
        companyWebsiteField.setName("companyWebsite");
        companyWebsiteField.setValueClass(java.lang.String.class);
        jasperDesign.addField(companyWebsiteField);

        JRDesignField companyEmailField = new JRDesignField();
        companyEmailField.setName("companyEmail");
        companyEmailField.setValueClass(java.lang.String.class);
        jasperDesign.addField(companyEmailField);

        JRDesignField termsAndConditionsField = new JRDesignField();
        termsAndConditionsField.setName("termsAndConditions");
        termsAndConditionsField.setValueClass(java.lang.String.class);
        jasperDesign.addField(termsAndConditionsField);
    }

    /**
     * Creates the title band for the Call Report
     */
    private static JRDesignBand createCallReportTitleBand() {
        JRDesignBand titleBand = new JRDesignBand();
        titleBand.setHeight(100);

        // Company name field
        JRDesignTextField companyNameField = new JRDesignTextField();
        companyNameField.setX(0);
        companyNameField.setY(10);
        companyNameField.setWidth(300);
        companyNameField.setHeight(30);
        companyNameField.setFontSize(18f);
        companyNameField.setBold(true);
        JRDesignExpression companyNameExpr = new JRDesignExpression();
        companyNameExpr.setText("$F{companyName}");
        companyNameField.setExpression(companyNameExpr);
        titleBand.addElement(companyNameField);

        // Company address field
        JRDesignTextField companyAddressField = new JRDesignTextField();
        companyAddressField.setX(360);
        companyAddressField.setY(18);
        companyAddressField.setWidth(179);
        companyAddressField.setHeight(36);
        companyAddressField.setFontSize(11f);
        JRDesignExpression companyAddressExpr = new JRDesignExpression();
        companyAddressExpr.setText("$F{companyAddress}");
        companyAddressField.setExpression(companyAddressExpr);
        titleBand.addElement(companyAddressField);

        // Company phone field
        JRDesignTextField companyPhoneField = new JRDesignTextField();
        companyPhoneField.setX(360);
        companyPhoneField.setY(54);
        companyPhoneField.setWidth(179);
        companyPhoneField.setHeight(14);
        JRDesignExpression companyPhoneExpr = new JRDesignExpression();
        companyPhoneExpr.setText("$F{companyPhoneNumber}");
        companyPhoneField.setExpression(companyPhoneExpr);
        titleBand.addElement(companyPhoneField);

        // Company website field
        JRDesignTextField companyWebsiteField = new JRDesignTextField();
        companyWebsiteField.setX(360);
        companyWebsiteField.setY(68);
        companyWebsiteField.setWidth(179);
        companyWebsiteField.setHeight(20);
        companyWebsiteField.setForecolor(new Color(51, 51, 255)); // #3333FF blue color
        JRDesignExpression companyWebsiteExpr = new JRDesignExpression();
        companyWebsiteExpr.setText("$F{companyWebsite}");
        companyWebsiteField.setExpression(companyWebsiteExpr);
        titleBand.addElement(companyWebsiteField);

        // Static text for title
        JRDesignStaticText titleText = new JRDesignStaticText();
        titleText.setX(180);
        titleText.setY(40);
        titleText.setWidth(200);
        titleText.setHeight(30);
        titleText.setText("CALL REPORT");
        titleText.setFontSize(16f);
        titleText.setBold(true);
        titleText.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        titleBand.addElement(titleText);

        // Line at the bottom of the title
        JRDesignLine line = new JRDesignLine();
        line.setX(0);
        line.setY(95);
        line.setWidth(555);
        line.setHeight(1);
        line.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        titleBand.addElement(line);

        return titleBand;
    }

    /**
     * Creates the page header band for the Call Report
     */
    private static JRDesignBand createCallReportPageHeaderBand() {
        JRDesignBand pageHeaderBand = new JRDesignBand();
        pageHeaderBand.setHeight(60);

        // Customer details section header
        JRDesignStaticText customerHeaderText = new JRDesignStaticText();
        customerHeaderText.setX(0);
        customerHeaderText.setY(5);
        customerHeaderText.setWidth(555);
        customerHeaderText.setHeight(20);
        customerHeaderText.setText("Customer Details");
        customerHeaderText.setFontSize(14f);
        customerHeaderText.setBold(true);
        pageHeaderBand.addElement(customerHeaderText);

        // Tag number field
        JRDesignStaticText tagNoLabel = new JRDesignStaticText();
        tagNoLabel.setX(380);
        tagNoLabel.setY(5);
        tagNoLabel.setWidth(80);
        tagNoLabel.setHeight(20);
        tagNoLabel.setText("Tag No:");
        tagNoLabel.setBold(true);
        pageHeaderBand.addElement(tagNoLabel);

        JRDesignTextField tagNoField = new JRDesignTextField();
        tagNoField.setX(460);
        tagNoField.setY(5);
        tagNoField.setWidth(95);
        tagNoField.setHeight(20);
        JRDesignExpression tagNoExpr = new JRDesignExpression();
        tagNoExpr.setText("$F{tagNo}");
        tagNoField.setExpression(tagNoExpr);
        pageHeaderBand.addElement(tagNoField);

        // Date reported field
        JRDesignStaticText dateLabel = new JRDesignStaticText();
        dateLabel.setX(380);
        dateLabel.setY(25);
        dateLabel.setWidth(80);
        dateLabel.setHeight(20);
        dateLabel.setText("Date:");
        dateLabel.setBold(true);
        pageHeaderBand.addElement(dateLabel);

        JRDesignTextField dateField = new JRDesignTextField();
        dateField.setX(460);
        dateField.setY(25);
        dateField.setWidth(95);
        dateField.setHeight(20);
        JRDesignExpression dateExpr = new JRDesignExpression();
        // Simplify expression to just pass the date object
        dateExpr.setText("$F{dateReported}");
        // Let JasperReports handle the formatting with the pattern
        dateField.setPattern("MM/dd/yyyy");
        dateField.setExpression(dateExpr);
        pageHeaderBand.addElement(dateField);

        // Line at the bottom of the header
        JRDesignLine line = new JRDesignLine();
        line.setX(0);
        line.setY(55);
        line.setWidth(555);
        line.setHeight(1);
        line.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        pageHeaderBand.addElement(line);

        return pageHeaderBand;
    }

    /**
     * Creates the detail band for the Call Report
     */
    private static JRDesignBand createCallReportDetailBand() {
        JRDesignBand detailBand = new JRDesignBand();
        detailBand.setHeight(500); // Height for all the customer and equipment details

        // Customer name field
        JRDesignStaticText customerNameLabel = new JRDesignStaticText();
        customerNameLabel.setX(10);
        customerNameLabel.setY(10);
        customerNameLabel.setWidth(100);
        customerNameLabel.setHeight(20);
        customerNameLabel.setText("Customer Name:");
        customerNameLabel.setBold(true);
        detailBand.addElement(customerNameLabel);

        JRDesignTextField customerNameField = new JRDesignTextField();
        customerNameField.setX(110);
        customerNameField.setY(10);
        customerNameField.setWidth(445);
        customerNameField.setHeight(20);
        JRDesignExpression customerNameExpr = new JRDesignExpression();
        customerNameExpr.setText("$F{customerName}");
        customerNameField.setExpression(customerNameExpr);
        detailBand.addElement(customerNameField);

        // Address field
        JRDesignStaticText addressLabel = new JRDesignStaticText();
        addressLabel.setX(10);
        addressLabel.setY(30);
        addressLabel.setWidth(100);
        addressLabel.setHeight(20);
        addressLabel.setText("Address:");
        addressLabel.setBold(true);
        detailBand.addElement(addressLabel);

        JRDesignTextField addressField = new JRDesignTextField();
        addressField.setX(110);
        addressField.setY(30);
        addressField.setWidth(445);
        addressField.setHeight(20);
        JRDesignExpression addressExpr = new JRDesignExpression();
        addressExpr.setText("$F{address}");
        addressField.setExpression(addressExpr);
        detailBand.addElement(addressField);

        // Phone field
        JRDesignStaticText phoneLabel = new JRDesignStaticText();
        phoneLabel.setX(10);
        phoneLabel.setY(50);
        phoneLabel.setWidth(100);
        phoneLabel.setHeight(20);
        phoneLabel.setText("Phone:");
        phoneLabel.setBold(true);
        detailBand.addElement(phoneLabel);

        JRDesignTextField phoneField = new JRDesignTextField();
        phoneField.setX(110);
        phoneField.setY(50);
        phoneField.setWidth(445);
        phoneField.setHeight(20);
        JRDesignExpression phoneExpr = new JRDesignExpression();
        phoneExpr.setText("$F{phone}");
        phoneField.setExpression(phoneExpr);
        detailBand.addElement(phoneField);

        // Equipment section header
        JRDesignStaticText equipmentHeaderText = new JRDesignStaticText();
        equipmentHeaderText.setX(0);
        equipmentHeaderText.setY(80);
        equipmentHeaderText.setWidth(555);
        equipmentHeaderText.setHeight(20);
        equipmentHeaderText.setText("Equipment Details");
        equipmentHeaderText.setFontSize(14f);
        equipmentHeaderText.setBold(true);
        detailBand.addElement(equipmentHeaderText);

        // Make field
        JRDesignStaticText makeLabel = new JRDesignStaticText();
        makeLabel.setX(10);
        makeLabel.setY(100);
        makeLabel.setWidth(100);
        makeLabel.setHeight(20);
        makeLabel.setText("Make:");
        makeLabel.setBold(true);
        detailBand.addElement(makeLabel);

        JRDesignTextField makeField = new JRDesignTextField();
        makeField.setX(110);
        makeField.setY(100);
        makeField.setWidth(445);
        makeField.setHeight(20);
        JRDesignExpression makeExpr = new JRDesignExpression();
        makeExpr.setText("$F{makeName}");
        makeField.setExpression(makeExpr);
        detailBand.addElement(makeField);

        // Model field
        JRDesignStaticText modelLabel = new JRDesignStaticText();
        modelLabel.setX(10);
        modelLabel.setY(120);
        modelLabel.setWidth(100);
        modelLabel.setHeight(20);
        modelLabel.setText("Model:");
        modelLabel.setBold(true);
        detailBand.addElement(modelLabel);

        JRDesignTextField modelField = new JRDesignTextField();
        modelField.setX(110);
        modelField.setY(120);
        modelField.setWidth(445);
        modelField.setHeight(20);
        JRDesignExpression modelExpr = new JRDesignExpression();
        modelExpr.setText("$F{modelName}");
        modelField.setExpression(modelExpr);
        detailBand.addElement(modelField);

        // Serial Number field
        JRDesignStaticText serialNoLabel = new JRDesignStaticText();
        serialNoLabel.setX(10);
        serialNoLabel.setY(140);
        serialNoLabel.setWidth(100);
        serialNoLabel.setHeight(20);
        serialNoLabel.setText("Serial No:");
        serialNoLabel.setBold(true);
        detailBand.addElement(serialNoLabel);

        JRDesignTextField serialNoField = new JRDesignTextField();
        serialNoField.setX(110);
        serialNoField.setY(140);
        serialNoField.setWidth(445);
        serialNoField.setHeight(20);
        JRDesignExpression serialNoExpr = new JRDesignExpression();
        serialNoExpr.setText("$F{serialNo}");
        serialNoField.setExpression(serialNoExpr);
        detailBand.addElement(serialNoField);

        // Accessories field
        JRDesignStaticText accessoriesLabel = new JRDesignStaticText();
        accessoriesLabel.setX(10);
        accessoriesLabel.setY(160);
        accessoriesLabel.setWidth(100);
        accessoriesLabel.setHeight(20);
        accessoriesLabel.setText("Accessories:");
        accessoriesLabel.setBold(true);
        detailBand.addElement(accessoriesLabel);

        JRDesignTextField accessoriesField = new JRDesignTextField();
        accessoriesField.setX(110);
        accessoriesField.setY(160);
        accessoriesField.setWidth(445);
        accessoriesField.setHeight(20);
        JRDesignExpression accessoriesExpr = new JRDesignExpression();
        accessoriesExpr.setText("$F{accessories}");
        accessoriesField.setExpression(accessoriesExpr);
        detailBand.addElement(accessoriesField);

        // Complaint section header
        JRDesignStaticText complaintHeaderText = new JRDesignStaticText();
        complaintHeaderText.setX(0);
        complaintHeaderText.setY(190);
        complaintHeaderText.setWidth(555);
        complaintHeaderText.setHeight(20);
        complaintHeaderText.setText("Complaint Information");
        complaintHeaderText.setFontSize(14f);
        complaintHeaderText.setBold(true);
        detailBand.addElement(complaintHeaderText);

        // Complaint Reported field
        JRDesignStaticText complaintReportedLabel = new JRDesignStaticText();
        complaintReportedLabel.setX(10);
        complaintReportedLabel.setY(210);
        complaintReportedLabel.setWidth(545);
        complaintReportedLabel.setHeight(20);
        complaintReportedLabel.setText("Complaint Reported:");
        complaintReportedLabel.setBold(true);
        detailBand.addElement(complaintReportedLabel);

        JRDesignTextField complaintReportedField = new JRDesignTextField();
        complaintReportedField.setX(10);
        complaintReportedField.setY(230);
        complaintReportedField.setWidth(545);
        complaintReportedField.setHeight(50);
        // Replace setStretchWithOverflow with setTextAdjust for compatibility
        complaintReportedField.setTextAdjust(TextAdjustEnum.STRETCH_HEIGHT);
        JRDesignExpression complaintReportedExpr = new JRDesignExpression();
        complaintReportedExpr.setText("$F{complaintReported}");
        complaintReportedField.setExpression(complaintReportedExpr);
        detailBand.addElement(complaintReportedField);

        // Complaint Diagnosed field
        JRDesignStaticText complaintDiagnosedLabel = new JRDesignStaticText();
        complaintDiagnosedLabel.setX(10);
        complaintDiagnosedLabel.setY(290);
        complaintDiagnosedLabel.setWidth(545);
        complaintDiagnosedLabel.setHeight(20);
        complaintDiagnosedLabel.setText("Complaint Diagnosed:");
        complaintDiagnosedLabel.setBold(true);
        detailBand.addElement(complaintDiagnosedLabel);

        JRDesignTextField complaintDiagnosedField = new JRDesignTextField();
        complaintDiagnosedField.setX(10);
        complaintDiagnosedField.setY(310);
        complaintDiagnosedField.setWidth(545);
        complaintDiagnosedField.setHeight(50);
        // Replace setStretchWithOverflow with setTextAdjust for compatibility
        complaintDiagnosedField.setTextAdjust(TextAdjustEnum.STRETCH_HEIGHT);
        JRDesignExpression complaintDiagnosedExpr = new JRDesignExpression();
        complaintDiagnosedExpr.setText("$F{complaintDiagnosed}");
        complaintDiagnosedField.setExpression(complaintDiagnosedExpr);
        detailBand.addElement(complaintDiagnosedField);

        // Engineering Remarks field
        JRDesignStaticText enggRemarkLabel = new JRDesignStaticText();
        enggRemarkLabel.setX(10);
        enggRemarkLabel.setY(370);
        enggRemarkLabel.setWidth(545);
        enggRemarkLabel.setHeight(20);
        enggRemarkLabel.setText("Engineering Remarks:");
        enggRemarkLabel.setBold(true);
        detailBand.addElement(enggRemarkLabel);

        JRDesignTextField enggRemarkField = new JRDesignTextField();
        enggRemarkField.setX(10);
        enggRemarkField.setY(390);
        enggRemarkField.setWidth(545);
        enggRemarkField.setHeight(50);
        enggRemarkField.setTextAdjust(TextAdjustEnum.STRETCH_HEIGHT);
        JRDesignExpression enggRemarkExpr = new JRDesignExpression();
        enggRemarkExpr.setText("$F{enggRemark}");
        enggRemarkField.setExpression(enggRemarkExpr);
        detailBand.addElement(enggRemarkField);

        // Status field
        JRDesignStaticText statusLabel = new JRDesignStaticText();
        statusLabel.setX(10);
        statusLabel.setY(450);
        statusLabel.setWidth(100);
        statusLabel.setHeight(20);
        statusLabel.setText("Status:");
        statusLabel.setBold(true);
        detailBand.addElement(statusLabel);

        JRDesignTextField statusField = new JRDesignTextField();
        statusField.setX(110);
        statusField.setY(450);
        statusField.setWidth(445);
        statusField.setHeight(20);
        JRDesignExpression statusExpr = new JRDesignExpression();
        statusExpr.setText("$F{status}");
        statusField.setExpression(statusExpr);
        detailBand.addElement(statusField);

        return detailBand;
    }

    /**
     * Creates the summary band for the Call Report with terms and conditions
     */
    private static JRDesignBand createCallReportSummaryBand() {
        JRDesignBand summaryBand = new JRDesignBand();
        summaryBand.setHeight(100);

        // Terms and conditions section header
        JRDesignStaticText termsHeaderText = new JRDesignStaticText();
        termsHeaderText.setX(0);
        termsHeaderText.setY(0);
        termsHeaderText.setWidth(555);
        termsHeaderText.setHeight(20);
        termsHeaderText.setText("Terms and Conditions");
        termsHeaderText.setFontSize(12f);
        termsHeaderText.setBold(true);
        summaryBand.addElement(termsHeaderText);

        // Terms and conditions content
        JRDesignTextField termsField = new JRDesignTextField();
        termsField.setX(10);
        termsField.setY(20);
        termsField.setWidth(545);
        termsField.setHeight(70);
        termsField.setTextAdjust(TextAdjustEnum.STRETCH_HEIGHT);
        JRDesignExpression termsExpr = new JRDesignExpression();
        termsExpr.setText("$F{termsAndConditions}");
        termsField.setExpression(termsExpr);
        summaryBand.addElement(termsField);

        return summaryBand;
    }

    /**
     * Creates and fills a Call Report JasperPrint with the given data source.
     *
     * @param dataSource the data source for the report
     * @param parameters report parameters
     * @return the filled JasperPrint ready for export
     * @throws JRException if there is an error filling the report
     */
    public static JasperPrint fillCallReport(JRDataSource dataSource, Map<String, Object> parameters)
            throws JRException {
        JasperReport report = createCallReport();
        return JasperFillManager.fillReport(report, parameters, dataSource);
    }

    /**
     * Creates a dynamic "Error Report" with the same structure as errorReport.jrxml.
     *
     * @return The compiled JasperReport
     * @throws JRException if there is an error creating the report
     */
    public static JasperReport createErrorReport() throws JRException {
        JasperDesign jasperDesign = new JasperDesign();

        // Set basic report properties for a landscape orientation
        jasperDesign.setName("errorReport");
        jasperDesign.setPageWidth(842);
        jasperDesign.setPageHeight(595);
        jasperDesign.setColumnWidth(802);
        jasperDesign.setLeftMargin(20);
        jasperDesign.setRightMargin(20);
        jasperDesign.setTopMargin(20);
        jasperDesign.setBottomMargin(20);
        jasperDesign.setOrientation(OrientationEnum.LANDSCAPE);

        // Create and configure styles
        JRDesignStyle titleStyle = new JRDesignStyle();
        titleStyle.setName("Title");
        titleStyle.setForecolor(Color.WHITE);
        titleStyle.setFontName("Times New Roman");
        titleStyle.setFontSize(50f);
        titleStyle.setPdfFontName("Times-Bold");
        jasperDesign.addStyle(titleStyle);

        JRDesignStyle rowStyle = new JRDesignStyle();
        rowStyle.setName("Row");
        rowStyle.setMode(ModeEnum.TRANSPARENT);
        rowStyle.setFontName("Times New Roman");
        rowStyle.setPdfFontName("Times-Roman");
        jasperDesign.addStyle(rowStyle);

        // Create group band
        JRDesignBand groupHeaderBand = new JRDesignBand();
        groupHeaderBand.setHeight(58);

        // Create red background frame for the error message
        JRDesignRectangle errorFrame = new JRDesignRectangle();
        errorFrame.setX(64);
        errorFrame.setY(0);
        errorFrame.setWidth(721);
        errorFrame.setHeight(58);
        errorFrame.setBackcolor(new Color(204, 0, 0)); // #CC0000
        groupHeaderBand.addElement(errorFrame);

        // Add error message text
        JRDesignStaticText errorText = new JRDesignStaticText();
        errorText.setX(148);
        errorText.setY(0);
        errorText.setWidth(554);
        errorText.setHeight(58);
        errorText.setText("An error has been Occurred !!!");
        errorText.setFontName("Arial");
        errorText.setFontSize(36f);
        groupHeaderBand.addElement(errorText);

        // Create group to contain the error message
        JRDesignGroup group2 = new JRDesignGroup();
        JRDesignExpression groupExpr = new JRDesignExpression();
        groupExpr.setText("(int)($V{REPORT_COUNT}/5)");
        group2.setExpression(groupExpr);

        // Set the header and footer bands using the correct section approach
        ((JRDesignSection)group2.getGroupHeaderSection()).addBand(groupHeaderBand);
        ((JRDesignSection)group2.getGroupFooterSection()).addBand(new JRDesignBand());
        jasperDesign.addGroup(group2);

        // Compile and return the report
        return JasperCompileManager.compileReport(jasperDesign);
    }

    /**
     * Creates and fills an Error Report with the given data source.
     *
     * @param parameters report parameters
     * @return the filled JasperPrint ready for export
     * @throws JRException if there is an error filling the report
     */
    public static JasperPrint fillErrorReport(Map<String, Object> parameters)
            throws JRException {
        JasperReport report = createErrorReport();
        // Use an empty datasource since the error report doesn't use any data
        return JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
    }

    /**
     * Creates a dynamic "Service Bill Report" with the same structure as serviceBillReport.jrxml.
     *
     * @return The compiled JasperReport
     * @throws JRException if there is an error creating the report
     */
    public static JasperReport createServiceBillReport() throws JRException {
        JasperDesign jasperDesign = new JasperDesign();

        // Set basic report properties
        jasperDesign.setName("serviceBillReport");
        jasperDesign.setPageWidth(720);
        jasperDesign.setPageHeight(842);
        jasperDesign.setColumnWidth(680);
        jasperDesign.setLeftMargin(20);
        jasperDesign.setRightMargin(20);
        jasperDesign.setTopMargin(20);
        jasperDesign.setBottomMargin(20);

        // Add fields for the service bill report
        addServiceBillReportFields(jasperDesign);

        // Create title band
        JRDesignBand titleBand = createServiceBillTitleBand();
        jasperDesign.setTitle(titleBand);

        // Create page header
        JRDesignBand pageHeaderBand = createServiceBillPageHeaderBand();
        jasperDesign.setPageHeader(pageHeaderBand);

        // Create column header
        JRDesignBand columnHeaderBand = createServiceBillColumnHeaderBand();
        jasperDesign.setColumnHeader(columnHeaderBand);

        // Create detail band
        JRDesignBand detailBand = createServiceBillDetailBand();
        ((JRDesignSection)jasperDesign.getDetailSection()).addBand(detailBand);

        // Compile and return the report
        return JasperCompileManager.compileReport(jasperDesign);
    }

    /**
     * Adds all required fields to the Service Bill Report design
     */
    private static void addServiceBillReportFields(JasperDesign jasperDesign) throws JRException {
        // Invoice ID field
        JRDesignField invoiceIdField = new JRDesignField();
        invoiceIdField.setName("invoiceId");
        invoiceIdField.setValueClass(java.lang.Long.class);
        jasperDesign.addField(invoiceIdField);

        // Tag Number field
        JRDesignField tagNoField = new JRDesignField();
        tagNoField.setName("tagNo");
        tagNoField.setValueClass(java.lang.String.class);
        jasperDesign.addField(tagNoField);

        // Customer information fields
        JRDesignField customerNameField = new JRDesignField();
        customerNameField.setName("customerName");
        customerNameField.setValueClass(java.lang.String.class);
        jasperDesign.addField(customerNameField);

        JRDesignField customerAddressField = new JRDesignField();
        customerAddressField.setName("customerAddress");
        customerAddressField.setValueClass(java.lang.String.class);
        jasperDesign.addField(customerAddressField);

        // Item fields
        JRDesignField descriptionField = new JRDesignField();
        descriptionField.setName("description");
        descriptionField.setValueClass(java.lang.String.class);
        jasperDesign.addField(descriptionField);

        JRDesignField serialNoField = new JRDesignField();
        serialNoField.setName("serialNo");
        serialNoField.setValueClass(java.lang.String.class);
        jasperDesign.addField(serialNoField);

        JRDesignField quantityField = new JRDesignField();
        quantityField.setName("quantity");
        quantityField.setValueClass(java.lang.Integer.class);
        jasperDesign.addField(quantityField);

        JRDesignField rateField = new JRDesignField();
        rateField.setName("rate");
        rateField.setValueClass(java.lang.Double.class);
        jasperDesign.addField(rateField);

        JRDesignField amountField = new JRDesignField();
        amountField.setName("amount");
        amountField.setValueClass(java.lang.Double.class);
        jasperDesign.addField(amountField);

        JRDesignField totalAmountField = new JRDesignField();
        totalAmountField.setName("totalAmount");
        totalAmountField.setValueClass(java.lang.Double.class);
        jasperDesign.addField(totalAmountField);

        // Company information fields
        JRDesignField companyNameField = new JRDesignField();
        companyNameField.setName("companyName");
        companyNameField.setValueClass(java.lang.String.class);
        jasperDesign.addField(companyNameField);

        JRDesignField companyAddressField = new JRDesignField();
        companyAddressField.setName("companyAddress");
        companyAddressField.setValueClass(java.lang.String.class);
        jasperDesign.addField(companyAddressField);

        JRDesignField companyPhoneNumberField = new JRDesignField();
        companyPhoneNumberField.setName("companyPhoneNumber");
        companyPhoneNumberField.setValueClass(java.lang.String.class);
        jasperDesign.addField(companyPhoneNumberField);

        JRDesignField companyWebsiteField = new JRDesignField();
        companyWebsiteField.setName("companyWebsite");
        companyWebsiteField.setValueClass(java.lang.String.class);
        jasperDesign.addField(companyWebsiteField);

        JRDesignField companyEmailField = new JRDesignField();
        companyEmailField.setName("companyEmail");
        companyEmailField.setValueClass(java.lang.String.class);
        jasperDesign.addField(companyEmailField);

        JRDesignField companyVatTinField = new JRDesignField();
        companyVatTinField.setName("companyVatTin");
        companyVatTinField.setValueClass(java.lang.String.class);
        jasperDesign.addField(companyVatTinField);

        JRDesignField companyCstTinField = new JRDesignField();
        companyCstTinField.setName("companyCstTin");
        companyCstTinField.setValueClass(java.lang.String.class);
        jasperDesign.addField(companyCstTinField);
    }

    /**
     * Creates the title band for the Service Bill Report
     */
    private static JRDesignBand createServiceBillTitleBand() {
        JRDesignBand titleBand = new JRDesignBand();
        titleBand.setHeight(141);

        // Service Bill Title
        JRDesignStaticText titleText = new JRDesignStaticText();
        titleText.setX(253);
        titleText.setY(0);
        titleText.setWidth(210);
        titleText.setHeight(25);
        titleText.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        titleText.setFontName("Arial");
        titleText.setFontSize(18f);
        titleText.setBold(true);
        titleText.setText("SERVICE BILL");
        titleBand.addElement(titleText);

        // Company information fields
        JRDesignTextField companyNameField = new JRDesignTextField();
        companyNameField.setX(101);
        companyNameField.setY(32);
        companyNameField.setWidth(180);
        companyNameField.setHeight(20);
        companyNameField.setFontName("Arial");
        companyNameField.setFontSize(11f);
        JRDesignExpression companyNameExpr = new JRDesignExpression();
        companyNameExpr.setText("$F{companyName}");
        companyNameField.setExpression(companyNameExpr);
        titleBand.addElement(companyNameField);

        JRDesignTextField companyAddressField = new JRDesignTextField();
        companyAddressField.setX(101);
        companyAddressField.setY(56);
        companyAddressField.setWidth(180);
        companyAddressField.setHeight(30);
        companyAddressField.setFontName("Arial");
        companyAddressField.setFontSize(11f);
        JRDesignExpression companyAddressExpr = new JRDesignExpression();
        companyAddressExpr.setText("$F{companyAddress}");
        companyAddressField.setExpression(companyAddressExpr);
        titleBand.addElement(companyAddressField);

        JRDesignTextField companyPhoneField = new JRDesignTextField();
        companyPhoneField.setX(101);
        companyPhoneField.setY(90);
        companyPhoneField.setWidth(180);
        companyPhoneField.setHeight(20);
        companyPhoneField.setFontName("Arial");
        companyPhoneField.setFontSize(11f);
        JRDesignExpression companyPhoneExpr = new JRDesignExpression();
        companyPhoneExpr.setText("$F{companyPhoneNumber}");
        companyPhoneField.setExpression(companyPhoneExpr);
        titleBand.addElement(companyPhoneField);

        JRDesignTextField companyWebsiteField = new JRDesignTextField();
        companyWebsiteField.setX(101);
        companyWebsiteField.setY(114);
        companyWebsiteField.setWidth(180);
        companyWebsiteField.setHeight(20);
        companyWebsiteField.setFontName("Arial");
        companyWebsiteField.setFontSize(11f);
        JRDesignExpression companyWebsiteExpr = new JRDesignExpression();
        companyWebsiteExpr.setText("$F{companyWebsite}");
        companyWebsiteField.setExpression(companyWebsiteExpr);
        titleBand.addElement(companyWebsiteField);

        // Invoice details section
        JRDesignStaticText invoiceNoLabel = new JRDesignStaticText();
        invoiceNoLabel.setX(296);
        invoiceNoLabel.setY(49);
        invoiceNoLabel.setWidth(70);
        invoiceNoLabel.setHeight(20);
        invoiceNoLabel.setFontName("Arial");
        invoiceNoLabel.setFontSize(11f);
        invoiceNoLabel.setText("INVOICE NO:");
        titleBand.addElement(invoiceNoLabel);

        JRDesignTextField invoiceNoField = new JRDesignTextField();
        invoiceNoField.setX(371);
        invoiceNoField.setY(49);
        invoiceNoField.setWidth(100);
        invoiceNoField.setHeight(20);
        invoiceNoField.setFontName("Arial");
        invoiceNoField.setFontSize(11f);
        JRDesignExpression invoiceNoExpr = new JRDesignExpression();
        invoiceNoExpr.setText("$F{invoiceId}");
        invoiceNoField.setExpression(invoiceNoExpr);
        titleBand.addElement(invoiceNoField);

        JRDesignStaticText supplierRefLabel = new JRDesignStaticText();
        supplierRefLabel.setX(298);
        supplierRefLabel.setY(109);
        supplierRefLabel.setWidth(73);
        supplierRefLabel.setHeight(20);
        supplierRefLabel.setFontName("Arial");
        supplierRefLabel.setFontSize(11f);
        supplierRefLabel.setText("Suppliers Ref :");
        titleBand.addElement(supplierRefLabel);

        JRDesignTextField tagNoField = new JRDesignTextField();
        tagNoField.setX(374);
        tagNoField.setY(109);
        tagNoField.setWidth(100);
        tagNoField.setHeight(20);
        tagNoField.setFontName("Arial");
        tagNoField.setFontSize(11f);
        JRDesignExpression tagNoExpr = new JRDesignExpression();
        tagNoExpr.setText("$F{tagNo}");
        tagNoField.setExpression(tagNoExpr);
        titleBand.addElement(tagNoField);

        JRDesignStaticText dateLabel = new JRDesignStaticText();
        dateLabel.setX(487);
        dateLabel.setY(54);
        dateLabel.setWidth(42);
        dateLabel.setHeight(20);
        dateLabel.setFontName("Arial");
        dateLabel.setFontSize(11f);
        dateLabel.setText("DATED :");
        titleBand.addElement(dateLabel);

        JRDesignTextField dateField = new JRDesignTextField();
        dateField.setX(580);
        dateField.setY(54);
        dateField.setWidth(100);
        dateField.setHeight(20);
        dateField.setFontName("Arial");
        dateField.setFontSize(11f);
        JRDesignExpression dateExpr = new JRDesignExpression();
        dateExpr.setText("new java.text.SimpleDateFormat(\"dd/MM/yyyy\").format(new java.util.Date())");
        dateField.setExpression(dateExpr);
        titleBand.addElement(dateField);

        JRDesignStaticText refLabel = new JRDesignStaticText();
        refLabel.setX(488);
        refLabel.setY(108);
        refLabel.setWidth(100);
        refLabel.setHeight(20);
        refLabel.setFontName("Arial");
        refLabel.setFontSize(11f);
        refLabel.setText("Other References : ");
        titleBand.addElement(refLabel);

        // Add border lines
        // Top border line
        JRDesignLine topLine = new JRDesignLine();
        topLine.setX(2);
        topLine.setY(25);
        topLine.setWidth(677);
        topLine.setHeight(2);
        topLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        titleBand.addElement(topLine);

        // Left vertical line
        JRDesignLine leftLine = new JRDesignLine();
        leftLine.setX(1);
        leftLine.setY(26);
        leftLine.setWidth(1);
        leftLine.setHeight(111);
        leftLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        titleBand.addElement(leftLine);

        // Bottom border line
        JRDesignLine bottomLine = new JRDesignLine();
        bottomLine.setX(0);
        bottomLine.setY(138);
        bottomLine.setWidth(679);
        bottomLine.setHeight(1);
        bottomLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        titleBand.addElement(bottomLine);

        // Right vertical line
        JRDesignLine rightLine = new JRDesignLine();
        rightLine.setX(680);
        rightLine.setY(27);
        rightLine.setWidth(1);
        rightLine.setHeight(110);
        rightLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        titleBand.addElement(rightLine);

        // Vertical line between company info and invoice details
        JRDesignLine middleLine1 = new JRDesignLine();
        middleLine1.setX(292);
        middleLine1.setY(27);
        middleLine1.setWidth(1);
        middleLine1.setHeight(110);
        middleLine1.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        titleBand.addElement(middleLine1);

        // Horizontal line dividing invoice details
        JRDesignLine middleLine2 = new JRDesignLine();
        middleLine2.setX(293);
        middleLine2.setY(80);
        middleLine2.setWidth(386);
        middleLine2.setHeight(1);
        middleLine2.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        titleBand.addElement(middleLine2);

        // Left small vertical line
        JRDesignLine leftSideBarrier = new JRDesignLine();
        leftSideBarrier.setX(88);
        leftSideBarrier.setY(27);
        leftSideBarrier.setWidth(1);
        leftSideBarrier.setHeight(110);
        leftSideBarrier.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        titleBand.addElement(leftSideBarrier);

        // Vertical divider in invoice details section
        JRDesignLine midBarrier1 = new JRDesignLine();
        midBarrier1.setX(476);
        midBarrier1.setY(27);
        midBarrier1.setWidth(1);
        midBarrier1.setHeight(52);
        midBarrier1.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        titleBand.addElement(midBarrier1);

        JRDesignLine midBarrier2 = new JRDesignLine();
        midBarrier2.setX(476);
        midBarrier2.setY(81);
        midBarrier2.setWidth(1);
        midBarrier2.setHeight(56);
        midBarrier2.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        titleBand.addElement(midBarrier2);

        return titleBand;
    }

    /**
     * Creates the page header band for the Service Bill Report
     */
    private static JRDesignBand createServiceBillPageHeaderBand() {
        JRDesignBand pageHeaderBand = new JRDesignBand();
        pageHeaderBand.setHeight(143);

        // Customer details section
        JRDesignStaticText toLabel = new JRDesignStaticText();
        toLabel.setX(15);
        toLabel.setY(13);
        toLabel.setWidth(30);
        toLabel.setHeight(20);
        toLabel.setFontName("Arial");
        toLabel.setFontSize(11f);
        toLabel.setText("TO :");
        pageHeaderBand.addElement(toLabel);

        JRDesignTextField customerNameField = new JRDesignTextField();
        customerNameField.setX(91);
        customerNameField.setY(13);
        customerNameField.setWidth(386);
        customerNameField.setHeight(20);
        customerNameField.setFontName("Arial");
        customerNameField.setFontSize(11f);
        JRDesignExpression customerNameExpr = new JRDesignExpression();
        customerNameExpr.setText("$F{customerName}");
        customerNameField.setExpression(customerNameExpr);
        pageHeaderBand.addElement(customerNameField);

        JRDesignTextField customerAddressField = new JRDesignTextField();
        customerAddressField.setX(91);
        customerAddressField.setY(37);
        customerAddressField.setWidth(386);
        customerAddressField.setHeight(40);
        customerAddressField.setFontName("Arial");
        customerAddressField.setFontSize(11f);
        JRDesignExpression customerAddressExpr = new JRDesignExpression();
        customerAddressExpr.setText("$F{customerAddress}");
        customerAddressField.setExpression(customerAddressExpr);
        pageHeaderBand.addElement(customerAddressField);

        // Add border lines
        // Left vertical line
        JRDesignLine leftLine = new JRDesignLine();
        leftLine.setX(1);
        leftLine.setY(1);
        leftLine.setWidth(1);
        leftLine.setHeight(140);
        leftLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        pageHeaderBand.addElement(leftLine);

        // Right vertical line
        JRDesignLine rightLine = new JRDesignLine();
        rightLine.setX(680);
        rightLine.setY(1);
        rightLine.setWidth(1);
        rightLine.setHeight(140);
        rightLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        pageHeaderBand.addElement(rightLine);

        // Bottom border line
        JRDesignLine bottomLine = new JRDesignLine();
        bottomLine.setX(1);
        bottomLine.setY(142);
        bottomLine.setWidth(678);
        bottomLine.setHeight(1);
        bottomLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        pageHeaderBand.addElement(bottomLine);

        return pageHeaderBand;
    }

    /**
     * Creates the column header band for the Service Bill Report
     */
    private static JRDesignBand createServiceBillColumnHeaderBand() {
        JRDesignBand columnHeaderBand = new JRDesignBand();
        columnHeaderBand.setHeight(22);

        // Column headers
        JRDesignStaticText slNoHeader = new JRDesignStaticText();
        slNoHeader.setX(15);
        slNoHeader.setY(0);
        slNoHeader.setWidth(30);
        slNoHeader.setHeight(20);
        slNoHeader.setFontName("Arial");
        slNoHeader.setFontSize(11f);
        slNoHeader.setText("SL NO");
        columnHeaderBand.addElement(slNoHeader);

        JRDesignStaticText itemDescHeader = new JRDesignStaticText();
        itemDescHeader.setX(55);
        itemDescHeader.setY(0);
        itemDescHeader.setWidth(305);
        itemDescHeader.setHeight(20);
        itemDescHeader.setFontName("Arial");
        itemDescHeader.setFontSize(11f);
        itemDescHeader.setText("Item Description");
        columnHeaderBand.addElement(itemDescHeader);

        JRDesignStaticText quantityHeader = new JRDesignStaticText();
        quantityHeader.setX(371);
        quantityHeader.setY(0);
        quantityHeader.setWidth(62);
        quantityHeader.setHeight(20);
        quantityHeader.setFontName("Arial");
        quantityHeader.setFontSize(11f);
        quantityHeader.setText("Quantity");
        columnHeaderBand.addElement(quantityHeader);

        JRDesignStaticText rateHeader = new JRDesignStaticText();
        rateHeader.setX(445);
        rateHeader.setY(0);
        rateHeader.setWidth(66);
        rateHeader.setHeight(20);
        rateHeader.setFontName("Arial");
        rateHeader.setFontSize(11f);
        rateHeader.setText("Rate");
        columnHeaderBand.addElement(rateHeader);

        JRDesignStaticText amountHeader = new JRDesignStaticText();
        amountHeader.setX(539);
        amountHeader.setY(0);
        amountHeader.setWidth(100);
        amountHeader.setHeight(20);
        amountHeader.setFontName("Arial");
        amountHeader.setFontSize(11f);
        amountHeader.setText("Amount");
        columnHeaderBand.addElement(amountHeader);

        // Add border lines
        // Left vertical line
        JRDesignLine leftLine = new JRDesignLine();
        leftLine.setX(1);
        leftLine.setY(0);
        leftLine.setWidth(1);
        leftLine.setHeight(19);
        leftLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        columnHeaderBand.addElement(leftLine);

        // Right vertical line
        JRDesignLine rightLine = new JRDesignLine();
        rightLine.setX(680);
        rightLine.setY(0);
        rightLine.setWidth(1);
        rightLine.setHeight(19);
        rightLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        columnHeaderBand.addElement(rightLine);

        // Bottom border line
        JRDesignLine bottomLine = new JRDesignLine();
        bottomLine.setX(1);
        bottomLine.setY(20);
        bottomLine.setWidth(678);
        bottomLine.setHeight(1);
        bottomLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        columnHeaderBand.addElement(bottomLine);

        // Vertical divider lines
        JRDesignLine divider1 = new JRDesignLine();
        divider1.setX(50);
        divider1.setY(0);
        divider1.setWidth(1);
        divider1.setHeight(19);
        divider1.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        columnHeaderBand.addElement(divider1);

        JRDesignLine divider2 = new JRDesignLine();
        divider2.setX(365);
        divider2.setY(0);
        divider2.setWidth(1);
        divider2.setHeight(19);
        divider2.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        columnHeaderBand.addElement(divider2);

        JRDesignLine divider3 = new JRDesignLine();
        divider3.setX(439);
        divider3.setY(0);
        divider3.setWidth(1);
        divider3.setHeight(19);
        divider3.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        columnHeaderBand.addElement(divider3);

        JRDesignLine divider4 = new JRDesignLine();
        divider4.setX(526);
        divider4.setY(0);
        divider4.setWidth(1);
        divider4.setHeight(19);
        divider4.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        columnHeaderBand.addElement(divider4);

        return columnHeaderBand;
    }

    /**
     * Creates the detail band for the Service Bill Report
     */
    private static JRDesignBand createServiceBillDetailBand() {
        JRDesignBand detailBand = new JRDesignBand();
        detailBand.setHeight(344);

        // Item details fields
        JRDesignTextField slNoField = new JRDesignTextField();
        slNoField.setX(15);
        slNoField.setY(12);
        slNoField.setWidth(30);
        slNoField.setHeight(20);
        slNoField.setFontName("Arial");
        slNoField.setFontSize(11f);
        JRDesignExpression slNoExpr = new JRDesignExpression();
        slNoExpr.setText("1");
        slNoField.setExpression(slNoExpr);
        detailBand.addElement(slNoField);

        JRDesignTextField descriptionField = new JRDesignTextField();
        descriptionField.setX(55);
        descriptionField.setY(12);
        descriptionField.setWidth(305);
        descriptionField.setHeight(20);
        descriptionField.setFontName("Arial");
        descriptionField.setFontSize(11f);
        JRDesignExpression descExpr = new JRDesignExpression();
        descExpr.setText("$F{description}");
        descriptionField.setExpression(descExpr);
        detailBand.addElement(descriptionField);

        JRDesignTextField quantityField = new JRDesignTextField();
        quantityField.setX(371);
        quantityField.setY(12);
        quantityField.setWidth(62);
        quantityField.setHeight(20);
        quantityField.setFontName("Arial");
        quantityField.setFontSize(11f);
        JRDesignExpression qtyExpr = new JRDesignExpression();
        qtyExpr.setText("$F{quantity}");
        quantityField.setExpression(qtyExpr);
        detailBand.addElement(quantityField);

        JRDesignTextField rateField = new JRDesignTextField();
        rateField.setX(445);
        rateField.setY(12);
        rateField.setWidth(66);
        rateField.setHeight(20);
        rateField.setFontName("Arial");
        rateField.setFontSize(11f);
        JRDesignExpression rateExpr = new JRDesignExpression();
        rateExpr.setText("$F{rate}");
        rateField.setExpression(rateExpr);
        detailBand.addElement(rateField);

        JRDesignTextField amountField = new JRDesignTextField();
        amountField.setX(539);
        amountField.setY(12);
        amountField.setWidth(100);
        amountField.setHeight(20);
        amountField.setFontName("Arial");
        amountField.setFontSize(11f);
        JRDesignExpression amountExpr = new JRDesignExpression();
        amountExpr.setText("$F{amount}");
        amountField.setExpression(amountExpr);
        detailBand.addElement(amountField);

        // Serial number field
        JRDesignStaticText slNoLabel = new JRDesignStaticText();
        slNoLabel.setX(55);
        slNoLabel.setY(47);
        slNoLabel.setWidth(45);
        slNoLabel.setHeight(20);
        slNoLabel.setFontName("Arial");
        slNoLabel.setFontSize(11f);
        slNoLabel.setText("SL NO:");
        detailBand.addElement(slNoLabel);

        JRDesignTextField serialNoField = new JRDesignTextField();
        serialNoField.setX(101);
        serialNoField.setY(47);
        serialNoField.setWidth(259);
        serialNoField.setHeight(20);
        serialNoField.setFontName("Arial");
        serialNoField.setFontSize(11f);
        JRDesignExpression serialNoExpr = new JRDesignExpression();
        serialNoExpr.setText("$F{serialNo}");
        serialNoField.setExpression(serialNoExpr);
        detailBand.addElement(serialNoField);

        // Total amount at the bottom
        JRDesignStaticText totalLabel = new JRDesignStaticText();
        totalLabel.setX(445);
        totalLabel.setY(300);
        totalLabel.setWidth(66);
        totalLabel.setHeight(20);
        totalLabel.setFontName("Arial");
        totalLabel.setFontSize(12f);
        totalLabel.setBold(true);
        totalLabel.setText("Total:");
        detailBand.addElement(totalLabel);

        JRDesignTextField totalAmountField = new JRDesignTextField();
        totalAmountField.setX(539);
        totalAmountField.setY(300);
        totalAmountField.setWidth(100);
        totalAmountField.setHeight(20);
        totalAmountField.setFontName("Arial");
        totalAmountField.setFontSize(12f);
        totalAmountField.setBold(true);
        JRDesignExpression totalExpr = new JRDesignExpression();
        totalExpr.setText("$F{totalAmount}");
        totalAmountField.setExpression(totalExpr);
        detailBand.addElement(totalAmountField);

        // Add border lines
        // Left vertical line
        JRDesignLine leftLine = new JRDesignLine();
        leftLine.setX(1);
        leftLine.setY(0);
        leftLine.setWidth(1);
        leftLine.setHeight(340);
        leftLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        detailBand.addElement(leftLine);

        // Right vertical line
        JRDesignLine rightLine = new JRDesignLine();
        rightLine.setX(680);
        rightLine.setY(0);
        rightLine.setWidth(1);
        rightLine.setHeight(340);
        rightLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        detailBand.addElement(rightLine);

        // Vertical divider lines for the item row
        JRDesignLine divider1 = new JRDesignLine();
        divider1.setX(50);
        divider1.setY(0);
        divider1.setWidth(1);
        divider1.setHeight(68);
        divider1.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        detailBand.addElement(divider1);

        JRDesignLine divider2 = new JRDesignLine();
        divider2.setX(365);
        divider2.setY(0);
        divider2.setWidth(1);
        divider2.setHeight(68);
        divider2.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        detailBand.addElement(divider2);

        JRDesignLine divider3 = new JRDesignLine();
        divider3.setX(439);
        divider3.setY(0);
        divider3.setWidth(1);
        divider3.setHeight(68);
        divider3.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        detailBand.addElement(divider3);

        JRDesignLine divider4 = new JRDesignLine();
        divider4.setX(526);
        divider4.setY(0);
        divider4.setWidth(1);
        divider4.setHeight(68);
        divider4.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        detailBand.addElement(divider4);

        // Bottom line for the item row
        JRDesignLine itemBottomLine = new JRDesignLine();
        itemBottomLine.setX(1);
        itemBottomLine.setY(68);
        itemBottomLine.setWidth(679);
        itemBottomLine.setHeight(1);
        itemBottomLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        detailBand.addElement(itemBottomLine);

        // Total section top line
        JRDesignLine totalTopLine = new JRDesignLine();
        totalTopLine.setX(439);
        totalTopLine.setY(290);
        totalTopLine.setWidth(241);
        totalTopLine.setHeight(1);
        totalTopLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        detailBand.addElement(totalTopLine);

        // Total section bottom line
        JRDesignLine totalBottomLine = new JRDesignLine();
        totalBottomLine.setX(439);
        totalBottomLine.setY(330);
        totalBottomLine.setWidth(241);
        totalBottomLine.setHeight(1);
        totalBottomLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        detailBand.addElement(totalBottomLine);

        // Divider between Total label and amount
        JRDesignLine totalDivider = new JRDesignLine();
        totalDivider.setX(526);
        totalDivider.setY(290);
        totalDivider.setWidth(1);
        totalDivider.setHeight(40);
        totalDivider.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        detailBand.addElement(totalDivider);

        return detailBand;
    }

    /**
     * Creates and fills a Service Bill Report with the given data source.
     *
     * @param dataSource the data source for the report
     * @param parameters report parameters
     * @return the filled JasperPrint ready for export
     * @throws JRException if there is an error filling the report
     */
    public static JasperPrint fillServiceBillReport(JRDataSource dataSource, Map<String, Object> parameters)
            throws JRException {
        JasperReport report = createServiceBillReport();
        return JasperFillManager.fillReport(report, parameters, dataSource);
    }

    /**
     * Creates a dynamic "Transactions List Report" with the same structure as transactionsListReport.jrxml.
     *
     * @return The compiled JasperReport
     * @throws JRException if there is an error creating the report
     */
    public static JasperReport createTransactionsListReport() throws JRException {
        JasperDesign jasperDesign = new JasperDesign();

        // Set basic report properties
        jasperDesign.setName("transactionsListReport");
        jasperDesign.setPageWidth(800);
        jasperDesign.setPageHeight(842);
        jasperDesign.setColumnWidth(760);
        jasperDesign.setLeftMargin(20);
        jasperDesign.setRightMargin(20);
        jasperDesign.setTopMargin(20);
        jasperDesign.setBottomMargin(20);

        // Add fields for the transactions list report
        addTransactionsListReportFields(jasperDesign);

        // Create title band
        JRDesignBand titleBand = createTransactionsListTitleBand();
        jasperDesign.setTitle(titleBand);

        // Create column header band
        JRDesignBand columnHeaderBand = createTransactionsListColumnHeaderBand();
        jasperDesign.setColumnHeader(columnHeaderBand);

        // Create detail band
        JRDesignBand detailBand = createTransactionsListDetailBand();
        ((JRDesignSection)jasperDesign.getDetailSection()).addBand(detailBand);

        // Compile and return the report
        return JasperCompileManager.compileReport(jasperDesign);
    }

    /**
     * Adds all required fields to the Transactions List Report design
     */
    private static void addTransactionsListReportFields(JasperDesign jasperDesign) throws JRException {
        // Tag Number field
        JRDesignField tagNoField = new JRDesignField();
        tagNoField.setName("tagNo");
        tagNoField.setValueClass(java.lang.String.class);
        jasperDesign.addField(tagNoField);

        // Customer name field
        JRDesignField customerNameField = new JRDesignField();
        customerNameField.setName("customerName");
        customerNameField.setValueClass(java.lang.String.class);
        jasperDesign.addField(customerNameField);

        // Date reported field
        JRDesignField dateReportedField = new JRDesignField();
        dateReportedField.setName("dateReported");
        dateReportedField.setValueClass(java.lang.String.class);
        jasperDesign.addField(dateReportedField);

        // Make name field
        JRDesignField makeNameField = new JRDesignField();
        makeNameField.setName("makeName");
        makeNameField.setValueClass(java.lang.String.class);
        jasperDesign.addField(makeNameField);

        // Model name field
        JRDesignField modelNameField = new JRDesignField();
        modelNameField.setName("modelName");
        modelNameField.setValueClass(java.lang.String.class);
        jasperDesign.addField(modelNameField);

        // Serial Number field
        JRDesignField serialNoField = new JRDesignField();
        serialNoField.setName("serialNo");
        serialNoField.setValueClass(java.lang.String.class);
        jasperDesign.addField(serialNoField);

        // Status field
        JRDesignField statusField = new JRDesignField();
        statusField.setName("status");
        statusField.setValueClass(java.lang.String.class);
        jasperDesign.addField(statusField);
    }

    /**
     * Creates the title band for the Transactions List Report
     */
    private static JRDesignBand createTransactionsListTitleBand() {
        JRDesignBand titleBand = new JRDesignBand();
        titleBand.setHeight(43);

        // Title
        JRDesignStaticText titleText = new JRDesignStaticText();
        titleText.setX(10);
        titleText.setY(7);
        titleText.setWidth(663);
        titleText.setHeight(28);
        titleText.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        titleText.setFontName("Arial");
        titleText.setFontSize(21f);
        titleText.setBold(true);
        titleText.setText("Transactions List Report");
        titleBand.addElement(titleText);

        // Add border lines
        // Top border line
        JRDesignLine topLine = new JRDesignLine();
        topLine.setX(0);
        topLine.setY(4);
        topLine.setWidth(710);
        topLine.setHeight(1);
        topLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        titleBand.addElement(topLine);

        // Bottom border line
        JRDesignLine bottomLine = new JRDesignLine();
        bottomLine.setX(0);
        bottomLine.setY(41);
        bottomLine.setWidth(709);
        bottomLine.setHeight(1);
        bottomLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        titleBand.addElement(bottomLine);

        // Left vertical line
        JRDesignLine leftLine = new JRDesignLine();
        leftLine.setX(0);
        leftLine.setY(5);
        leftLine.setWidth(1);
        leftLine.setHeight(35);
        leftLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        titleBand.addElement(leftLine);

        // Right vertical line
        JRDesignLine rightLine = new JRDesignLine();
        rightLine.setX(710);
        rightLine.setY(5);
        rightLine.setWidth(1);
        rightLine.setHeight(36);
        rightLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        titleBand.addElement(rightLine);

        return titleBand;
    }

    /**
     * Creates the column header band for the Transactions List Report
     */
    private static JRDesignBand createTransactionsListColumnHeaderBand() {
        JRDesignBand columnHeaderBand = new JRDesignBand();
        columnHeaderBand.setHeight(34);

        // Column headers
        JRDesignStaticText tagNoHeader = new JRDesignStaticText();
        tagNoHeader.setX(10);
        tagNoHeader.setY(5);
        tagNoHeader.setWidth(80);
        tagNoHeader.setHeight(20);
        tagNoHeader.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        tagNoHeader.setFontName("Arial");
        tagNoHeader.setFontSize(11f);
        tagNoHeader.setBold(true);
        tagNoHeader.setText("TagNo");
        columnHeaderBand.addElement(tagNoHeader);

        JRDesignStaticText customerNameHeader = new JRDesignStaticText();
        customerNameHeader.setX(110);
        customerNameHeader.setY(5);
        customerNameHeader.setWidth(80);
        customerNameHeader.setHeight(20);
        customerNameHeader.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        customerNameHeader.setFontName("Arial");
        customerNameHeader.setFontSize(11f);
        customerNameHeader.setBold(true);
        customerNameHeader.setText("Customer Name");
        columnHeaderBand.addElement(customerNameHeader);

        JRDesignStaticText reportedDateHeader = new JRDesignStaticText();
        reportedDateHeader.setX(220);
        reportedDateHeader.setY(5);
        reportedDateHeader.setWidth(80);
        reportedDateHeader.setHeight(20);
        reportedDateHeader.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        reportedDateHeader.setFontName("Arial");
        reportedDateHeader.setFontSize(11f);
        reportedDateHeader.setBold(true);
        reportedDateHeader.setText("Reported Date");
        columnHeaderBand.addElement(reportedDateHeader);

        JRDesignStaticText makeHeader = new JRDesignStaticText();
        makeHeader.setX(320);
        makeHeader.setY(5);
        makeHeader.setWidth(80);
        makeHeader.setHeight(20);
        makeHeader.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        makeHeader.setFontName("Arial");
        makeHeader.setFontSize(11f);
        makeHeader.setBold(true);
        makeHeader.setText("Make");
        columnHeaderBand.addElement(makeHeader);

        JRDesignStaticText modelHeader = new JRDesignStaticText();
        modelHeader.setX(420);
        modelHeader.setY(5);
        modelHeader.setWidth(80);
        modelHeader.setHeight(20);
        modelHeader.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        modelHeader.setFontName("Arial");
        modelHeader.setFontSize(11f);
        modelHeader.setBold(true);
        modelHeader.setText("Model");
        columnHeaderBand.addElement(modelHeader);

        JRDesignStaticText serialNoHeader = new JRDesignStaticText();
        serialNoHeader.setX(525);
        serialNoHeader.setY(5);
        serialNoHeader.setWidth(80);
        serialNoHeader.setHeight(20);
        serialNoHeader.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        serialNoHeader.setFontName("Arial");
        serialNoHeader.setFontSize(11f);
        serialNoHeader.setBold(true);
        serialNoHeader.setText("Serial no");
        columnHeaderBand.addElement(serialNoHeader);

        JRDesignStaticText statusHeader = new JRDesignStaticText();
        statusHeader.setX(625);
        statusHeader.setY(5);
        statusHeader.setWidth(80);
        statusHeader.setHeight(20);
        statusHeader.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        statusHeader.setFontName("Arial");
        statusHeader.setFontSize(11f);
        statusHeader.setBold(true);
        statusHeader.setText("Status");
        columnHeaderBand.addElement(statusHeader);

        // Add border lines
        // Left vertical line
        JRDesignLine leftLine = new JRDesignLine();
        leftLine.setX(0);
        leftLine.setY(0);
        leftLine.setWidth(1);
        leftLine.setHeight(30);
        leftLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        columnHeaderBand.addElement(leftLine);

        // Right vertical line
        JRDesignLine rightLine = new JRDesignLine();
        rightLine.setX(710);
        rightLine.setY(0);
        rightLine.setWidth(1);
        rightLine.setHeight(30);
        rightLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        columnHeaderBand.addElement(rightLine);

        // Bottom border line
        JRDesignLine bottomLine = new JRDesignLine();
        bottomLine.setX(0);
        bottomLine.setY(31);
        bottomLine.setWidth(710);
        bottomLine.setHeight(1);
        bottomLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        columnHeaderBand.addElement(bottomLine);

        // Vertical divider lines
        JRDesignLine divider1 = new JRDesignLine();
        divider1.setX(100);
        divider1.setY(0);
        divider1.setWidth(1);
        divider1.setHeight(30);
        divider1.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        columnHeaderBand.addElement(divider1);

        JRDesignLine divider2 = new JRDesignLine();
        divider2.setX(210);
        divider2.setY(0);
        divider2.setWidth(1);
        divider2.setHeight(30);
        divider2.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        columnHeaderBand.addElement(divider2);

        JRDesignLine divider3 = new JRDesignLine();
        divider3.setX(309);
        divider3.setY(0);
        divider3.setWidth(1);
        divider3.setHeight(30);
        divider3.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        columnHeaderBand.addElement(divider3);

        JRDesignLine divider4 = new JRDesignLine();
        divider4.setX(409);
        divider4.setY(0);
        divider4.setWidth(1);
        divider4.setHeight(30);
        divider4.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        columnHeaderBand.addElement(divider4);

        JRDesignLine divider5 = new JRDesignLine();
        divider5.setX(520);
        divider5.setY(0);
        divider5.setWidth(1);
        divider5.setHeight(30);
        divider5.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        columnHeaderBand.addElement(divider5);

        JRDesignLine divider6 = new JRDesignLine();
        divider6.setX(619);
        divider6.setY(1);
        divider6.setWidth(1);
        divider6.setHeight(30);
        divider6.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        columnHeaderBand.addElement(divider6);

        return columnHeaderBand;
    }

    /**
     * Creates the detail band for the Transactions List Report
     */
    private static JRDesignBand createTransactionsListDetailBand() {
        JRDesignBand detailBand = new JRDesignBand();
        detailBand.setHeight(40);

        // Detail fields
        JRDesignTextField tagNoField = new JRDesignTextField();
        tagNoField.setX(10);
        tagNoField.setY(10);
        tagNoField.setWidth(80);
        tagNoField.setHeight(20);
        tagNoField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        tagNoField.setFontName("Arial");
        tagNoField.setFontSize(11f);
        JRDesignExpression tagNoExpr = new JRDesignExpression();
        tagNoExpr.setText("$F{tagNo}");
        tagNoField.setExpression(tagNoExpr);
        detailBand.addElement(tagNoField);

        JRDesignTextField customerNameField = new JRDesignTextField();
        customerNameField.setX(110);
        customerNameField.setY(5);
        customerNameField.setWidth(80);
        customerNameField.setHeight(20);
        customerNameField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        customerNameField.setFontName("Arial");
        customerNameField.setFontSize(11f);
        JRDesignExpression customerNameExpr = new JRDesignExpression();
        customerNameExpr.setText("$F{customerName}");
        customerNameField.setExpression(customerNameExpr);
        detailBand.addElement(customerNameField);

        JRDesignTextField dateReportedField = new JRDesignTextField();
        dateReportedField.setX(220);
        dateReportedField.setY(5);
        dateReportedField.setWidth(80);
        dateReportedField.setHeight(20);
        dateReportedField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        dateReportedField.setFontName("Arial");
        dateReportedField.setFontSize(11f);
        JRDesignExpression dateReportedExpr = new JRDesignExpression();
        dateReportedExpr.setText("$F{dateReported}");
        dateReportedField.setExpression(dateReportedExpr);
        detailBand.addElement(dateReportedField);

        JRDesignTextField makeField = new JRDesignTextField();
        makeField.setX(320);
        makeField.setY(5);
        makeField.setWidth(80);
        makeField.setHeight(20);
        makeField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        makeField.setFontName("Arial");
        makeField.setFontSize(11f);
        JRDesignExpression makeExpr = new JRDesignExpression();
        makeExpr.setText("$F{makeName}");
        makeField.setExpression(makeExpr);
        detailBand.addElement(makeField);

        JRDesignTextField modelField = new JRDesignTextField();
        modelField.setX(420);
        modelField.setY(5);
        modelField.setWidth(80);
        modelField.setHeight(20);
        modelField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        modelField.setFontName("Arial");
        modelField.setFontSize(11f);
        JRDesignExpression modelExpr = new JRDesignExpression();
        modelExpr.setText("$F{modelName}");
        modelField.setExpression(modelExpr);
        detailBand.addElement(modelField);

        JRDesignTextField serialNoField = new JRDesignTextField();
        serialNoField.setX(524);
        serialNoField.setY(5);
        serialNoField.setWidth(80);
        serialNoField.setHeight(20);
        serialNoField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        serialNoField.setFontName("Arial");
        serialNoField.setFontSize(11f);
        JRDesignExpression serialNoExpr = new JRDesignExpression();
        serialNoExpr.setText("$F{serialNo}");
        serialNoField.setExpression(serialNoExpr);
        detailBand.addElement(serialNoField);

        JRDesignTextField statusField = new JRDesignTextField();
        statusField.setX(625);
        statusField.setY(5);
        statusField.setWidth(80);
        statusField.setHeight(20);
        statusField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        statusField.setFontName("Arial");
        statusField.setFontSize(11f);
        JRDesignExpression statusExpr = new JRDesignExpression();
        statusExpr.setText("$F{status}");
        statusField.setExpression(statusExpr);
        detailBand.addElement(statusField);

        // Add border lines
        // Left vertical line
        JRDesignLine leftLine = new JRDesignLine();
        leftLine.setX(0);
        leftLine.setY(1);
        leftLine.setWidth(1);
        leftLine.setHeight(34);
        leftLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        detailBand.addElement(leftLine);

        // Right vertical line
        JRDesignLine rightLine = new JRDesignLine();
        rightLine.setX(711);
        rightLine.setY(0);
        rightLine.setWidth(1);
        rightLine.setHeight(35);
        rightLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        detailBand.addElement(rightLine);

        // Bottom border line
        JRDesignLine bottomLine = new JRDesignLine();
        bottomLine.setX(0);
        bottomLine.setY(36);
        bottomLine.setWidth(710);
        bottomLine.setHeight(1);
        bottomLine.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        detailBand.addElement(bottomLine);

        // Vertical divider lines
        JRDesignLine divider1 = new JRDesignLine();
        divider1.setX(100);
        divider1.setY(0);
        divider1.setWidth(1);
        divider1.setHeight(36);
        divider1.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        detailBand.addElement(divider1);

        JRDesignLine divider2 = new JRDesignLine();
        divider2.setX(210);
        divider2.setY(0);
        divider2.setWidth(1);
        divider2.setHeight(36);
        divider2.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        detailBand.addElement(divider2);

        JRDesignLine divider3 = new JRDesignLine();
        divider3.setX(309);
        divider3.setY(0);
        divider3.setWidth(1);
        divider3.setHeight(36);
        divider3.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        detailBand.addElement(divider3);

        JRDesignLine divider4 = new JRDesignLine();
        divider4.setX(409);
        divider4.setY(0);
        divider4.setWidth(1);
        divider4.setHeight(36);
        divider4.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        detailBand.addElement(divider4);

        JRDesignLine divider5 = new JRDesignLine();
        divider5.setX(520);
        divider5.setY(0);
        divider5.setWidth(1);
        divider5.setHeight(36);
        divider5.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        detailBand.addElement(divider5);

        JRDesignLine divider6 = new JRDesignLine();
        divider6.setX(619);
        divider6.setY(2);
        divider6.setWidth(1);
        divider6.setHeight(32);
        divider6.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        detailBand.addElement(divider6);

        return detailBand;
    }

    /**
     * Creates and fills a Transactions List Report with the given data source.
     *
     * @param dataSource the data source for the report
     * @param parameters report parameters
     * @return the filled JasperPrint ready for export
     * @throws JRException if there is an error filling the report
     */
    public static JasperPrint fillTransactionsListReport(JRDataSource dataSource, Map<String, Object> parameters)
            throws JRException {
        JasperReport report = createTransactionsListReport();
        return JasperFillManager.fillReport(report, parameters, dataSource);
    }
}
