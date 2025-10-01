package com.poseidon.reports.util.builder;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignRectangle;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.OrientationEnum;

import java.awt.Color;

/**
 * Builder for Error Report.
 */
public class ErrorReportBuilder extends ReportBuilder {

    public JasperReport build() throws JRException {
        JasperDesign jasperDesign = new JasperDesign();

        // Set basic report properties for landscape orientation
        setupBasicReportProperties(jasperDesign, "errorReport", 595, 842, 555);
        jasperDesign.setOrientation(OrientationEnum.LANDSCAPE);

        // Create and configure styles
        jasperDesign.addStyle(createTitleStyle());
        jasperDesign.addStyle(createRowStyle());

        jasperDesign.setTitle(createTitleBand());
        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(createDetailBand());

        return JasperCompileManager.compileReport(jasperDesign);
    }

    private JRDesignStyle createRowStyle() {
        JRDesignStyle rowStyle = new JRDesignStyle();
        rowStyle.setName("Row");
        rowStyle.setMode(ModeEnum.TRANSPARENT);
        rowStyle.setFontSize(12f);
        rowStyle.setFontName("Arial");
        rowStyle.setPdfFontName("Helvetica");
        rowStyle.setPdfEncoding("Cp1252");

        return rowStyle;
    }

    private JRDesignStyle createTitleStyle() {
        JRDesignStyle titleStyle = new JRDesignStyle();
        titleStyle.setName("Title");
        titleStyle.setForecolor(Color.WHITE);
        titleStyle.setFontName("Arial");
        titleStyle.setFontSize(50f);
        titleStyle.setBold(true);
        titleStyle.setPdfFontName("Helvetica-Bold");
        titleStyle.setPdfEncoding("Cp1252");
        return titleStyle;
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
        titleRect.setHeight(36);
        titleRect.setBackcolor(LIGHT_GRAY);
        titleBand.addElement(titleRect);

        JRDesignRectangle headerRect = new JRDesignRectangle();
        headerRect.setX(1);
        headerRect.setY(37);
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
        titleText.setBold(true);
        titleText.setText("Error Report");
        titleText.setFontSize(24f);
        titleBand.addElement(titleText);


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
        JRDesignBand groupHeaderBand = new JRDesignBand();
        groupHeaderBand.setHeight(58);

        // Create red background frame for error message
        JRDesignRectangle errorFrame = new JRDesignRectangle();
        errorFrame.setX(1);
        errorFrame.setY(1);
        errorFrame.setWidth(244);
        errorFrame.setHeight(31);
        errorFrame.setBackcolor(new Color(204, 0, 0)); // #CC0000
        groupHeaderBand.addElement(errorFrame);

        // Add error message text
        JRDesignStaticText errorText = new JRDesignStaticText();
        errorText.setX(2);
        errorText.setY(2);
        errorText.setWidth(220);
        errorText.setHeight(20);
        errorText.setText("An error has been Occurred !!!");
        errorText.setFontName("Arial");
        errorText.setFontSize(36f);
        groupHeaderBand.addElement(errorText);
        return groupHeaderBand;
    }


}

