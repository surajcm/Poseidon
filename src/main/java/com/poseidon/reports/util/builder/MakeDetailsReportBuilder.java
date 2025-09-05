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

public class MakeDetailsReportBuilder extends ReportBuilder {
    public JasperReport build() throws JRException {
        JasperDesign jasperDesign = new JasperDesign();

        setupBasicReportProperties(jasperDesign, "makeListReport", 595, 842, 555);

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
        jasperDesign.addField(createField("description", java.lang.String.class));
    }

    private JRDesignBand createTitleBand() {
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
        // Fixed: don't use boldStyle variable directly as it's not in scope
        titleText.setBold(true);
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
        makeNameHeader.setBold(true);
        makeNameHeader.setText("Make Name");
        makeNameHeader.setFontSize(20f);
        titleBand.addElement(makeNameHeader);

        JRDesignStaticText descriptionHeader = new JRDesignStaticText();
        descriptionHeader.setX(254);
        descriptionHeader.setY(38);
        descriptionHeader.setWidth(244);
        descriptionHeader.setHeight(32);
        descriptionHeader.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        descriptionHeader.setBold(true);
        descriptionHeader.setText("Make Description");
        descriptionHeader.setFontSize(20f);
        titleBand.addElement(descriptionHeader);

        // Add borders
        addBorders(titleBand);

        return titleBand;
    }

    private void addBorders(final JRDesignBand titleBand) {
        // Horizontal lines
        addHorizontalLine(titleBand, 0, 0, 500, 1);
        addHorizontalLine(titleBand, 1, 36, 500, 1);
        addHorizontalLine(titleBand, 1, 72, 499, 1);

        // Vertical lines
        addVerticalLine(titleBand, 0, 0, 1, 37);
        addVerticalLine(titleBand, 0, 37, 1, 36);
        addVerticalLine(titleBand, 500, 0, 1, 36);
        addVerticalLine(titleBand, 500, 37, 1, 36);
        addVerticalLine(titleBand, 250, 37, 1, 35);
    }

    private JRDesignBand createDetailBand() {
        JRDesignBand detailBand = new JRDesignBand();
        detailBand.setHeight(40);

        // Add make name field
        JRDesignTextField makeNameField = new JRDesignTextField();
        makeNameField.setX(2);
        makeNameField.setY(1);
        makeNameField.setWidth(244);
        makeNameField.setHeight(31);
        makeNameField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        makeNameField.setFontSize(18f);

        JRDesignExpression makeNameExpr = new JRDesignExpression();
        makeNameExpr.setText("$F{makeName}");
        makeNameField.setExpression(makeNameExpr);
        detailBand.addElement(makeNameField);

        // Add description field
        JRDesignTextField descriptionField = new JRDesignTextField();
        descriptionField.setX(254);
        descriptionField.setY(0);
        descriptionField.setWidth(244);
        descriptionField.setHeight(34);
        descriptionField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        descriptionField.setFontSize(18f);

        JRDesignExpression descriptionExpr = new JRDesignExpression();
        descriptionExpr.setText("$F{description}");
        descriptionField.setExpression(descriptionExpr);
        detailBand.addElement(descriptionField);

        // Add borders for detail band
        addVerticalLine(detailBand, 0, 0, 1, 36);
        addVerticalLine(detailBand, 250, 0, 1, 36);
        addVerticalLine(detailBand, 500, 0, 1, 37);
        addHorizontalLine(detailBand, 0, 36, 500, 1);

        return detailBand;
    }
}
