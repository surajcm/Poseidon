package com.poseidon.reports.util.builder;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignLine;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.LineStyleEnum;
import net.sf.jasperreports.engine.type.TextAdjustEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;

import java.awt.Color;

/**
 * Builder for Call Report.
 */
public class CallReportBuilder extends ReportBuilder {

    public JasperReport build() throws JRException {
        JasperDesign jasperDesign = new JasperDesign();

        setupBasicReportProperties(jasperDesign, "callReport", 595, 842, 555);

        // Create and add styles
        jasperDesign.addStyle(createNormalStyle());
        jasperDesign.addStyle(createBoldStyle());

        // Add fields
        addCallReportFields(jasperDesign);

        // Create bands
        jasperDesign.setTitle(createCallReportTitleBand());
        jasperDesign.setPageHeader(createCallReportPageHeaderBand());
        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(createCallReportDetailBand());
        jasperDesign.setSummary(createCallReportSummaryBand());

        return JasperCompileManager.compileReport(jasperDesign);
    }

    private void addCallReportFields(final JasperDesign jasperDesign) throws JRException {
        // ID field
        jasperDesign.addField(createField("id", java.math.BigInteger.class));
        jasperDesign.addField(createField("tagNo", java.lang.String.class));
        jasperDesign.addField(createField("dateReported", java.time.LocalDateTime.class));

        // Customer information fields
        jasperDesign.addField(createField("customerName", java.lang.String.class));
        jasperDesign.addField(createField("address", java.lang.String.class));
        jasperDesign.addField(createField("phone", java.lang.String.class));

        // Equipment information fields
        jasperDesign.addField(createField("makeName", java.lang.String.class));
        jasperDesign.addField(createField("modelName", java.lang.String.class));
        jasperDesign.addField(createField("serialNo", java.lang.String.class));
        jasperDesign.addField(createField("accessories", java.lang.String.class));

        // Complaint and diagnosis fields
        jasperDesign.addField(createField("complaintReported", java.lang.String.class));
        jasperDesign.addField(createField("complaintDiagnosed", java.lang.String.class));
        jasperDesign.addField(createField("enggRemark", java.lang.String.class));
        jasperDesign.addField(createField("status", java.lang.String.class));

        // Company information fields
        jasperDesign.addField(createField("companyName", java.lang.String.class));
        jasperDesign.addField(createField("companyAddress", java.lang.String.class));
        jasperDesign.addField(createField("companyPhoneNumber", java.lang.String.class));
        jasperDesign.addField(createField("companyWebsite", java.lang.String.class));
        jasperDesign.addField(createField("companyEmail", java.lang.String.class));
        jasperDesign.addField(createField("termsAndConditions", java.lang.String.class));
    }

    private JRDesignBand createCallReportTitleBand() {
        JRDesignBand titleBand = new JRDesignBand();
        titleBand.setHeight(100);

        // Company name header
        JRDesignTextField companyNameField = new JRDesignTextField();
        companyNameField.setX(0);
        companyNameField.setY(0);
        companyNameField.setWidth(300);
        companyNameField.setHeight(30);
        companyNameField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        companyNameField.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
        companyNameField.setFontSize(20f);
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

        // Add border
        addHorizontalLine(titleBand, 0, 95, 555, 1);

        return titleBand;
    }

    private JRDesignBand createCallReportPageHeaderBand() {
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

    private JRDesignBand createCallReportDetailBand() {
        JRDesignBand detailBand = new JRDesignBand();
        detailBand.setHeight(500);

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

    private JRDesignBand createCallReportSummaryBand() {
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
}
