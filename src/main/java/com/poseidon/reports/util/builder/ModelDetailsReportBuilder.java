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

import java.awt.Color;

/**
 * Builder for Model Details Report.
 */
public class ModelDetailsReportBuilder extends ReportBuilder {

    public JasperReport build() throws JRException {
        JasperDesign jasperDesign = new JasperDesign();

        setupBasicReportProperties(jasperDesign, "modelListReport", 540, 842, 500);

        // Create and add styles
        jasperDesign.addStyle(createNormalStyle());
        jasperDesign.addStyle(createBoldStyle());

        // Add fields
        addFields(jasperDesign);

        // Create bands
        jasperDesign.setTitle(createTitleBand());
        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(createDetailBand());

        return JasperCompileManager.compileReport(jasperDesign);
    }

    private void addFields(final JasperDesign jasperDesign) throws JRException {
        jasperDesign.addField(createField("id", java.math.BigInteger.class));
        jasperDesign.addField(createField("makeName", java.lang.String.class));
        jasperDesign.addField(createField("modelName", java.lang.String.class));
    }

    private JRDesignBand createTitleBand() {
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
        titleText.setBold(true);
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
        makeNameHeader.setBold(true);
        makeNameHeader.setText("Make Name");
        makeNameHeader.setFontSize(20f);
        titleBand.addElement(makeNameHeader);

        JRDesignStaticText modelNameHeader = new JRDesignStaticText();
        modelNameHeader.setX(254);
        modelNameHeader.setY(41);
        modelNameHeader.setWidth(244);
        modelNameHeader.setHeight(32);
        modelNameHeader.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        modelNameHeader.setBold(true);
        modelNameHeader.setText("Model Name");
        modelNameHeader.setFontSize(20f);
        titleBand.addElement(modelNameHeader);

        // Add borders
        addBorders(titleBand);

        return titleBand;
    }

    private void addBorders(final JRDesignBand titleBand) {
        // Horizontal lines
        addHorizontalLine(titleBand, 0, 0, 498, 1);
        addHorizontalLine(titleBand, 0, 34, 498, 1);
        addHorizontalLine(titleBand, 1, 73, 497, 1);

        // Vertical lines
        addVerticalLine(titleBand, 0, 1, 1, 33);
        addVerticalLine(titleBand, 498, 0, 1, 33);
        addVerticalLine(titleBand, 0, 36, 1, 37);
        addVerticalLine(titleBand, 498, 35, 1, 37);
        addVerticalLine(titleBand, 250, 36, 1, 36);
    }

    private JRDesignBand createDetailBand() {
        JRDesignBand detailBand = new JRDesignBand();
        detailBand.setHeight(36);

        // Add make name field
        JRDesignTextField makeNameField = new JRDesignTextField();
        makeNameField.setX(2);
        makeNameField.setY(1);
        makeNameField.setWidth(244);
        makeNameField.setHeight(34);
        makeNameField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        makeNameField.setFontSize(18f);

        JRDesignExpression makeNameExpr = new JRDesignExpression();
        makeNameExpr.setText("$F{makeName}");
        makeNameField.setExpression(makeNameExpr);
        detailBand.addElement(makeNameField);

        // Add model name field
        JRDesignTextField modelNameField = new JRDesignTextField();
        modelNameField.setX(254);
        modelNameField.setY(0);
        modelNameField.setWidth(244);
        modelNameField.setHeight(34);
        modelNameField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        modelNameField.setFontSize(18f);

        JRDesignExpression modelNameExpr = new JRDesignExpression();
        modelNameExpr.setText("$F{modelName}");
        modelNameField.setExpression(modelNameExpr);
        detailBand.addElement(modelNameField);

        // Add borders
        addVerticalLine(detailBand, 0, 0, 1, 35);
        addVerticalLine(detailBand, 250, 0, 1, 35);
        addVerticalLine(detailBand, 498, 0, 1, 34);
        addHorizontalLine(detailBand, 0, 35, 498, 1);

        return detailBand;
    }
}

