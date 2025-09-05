package com.poseidon.reports.util.builder;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignRectangle;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
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
        createErrorReportStyles(jasperDesign);

        // Create group band
        JRDesignBand groupHeaderBand = new JRDesignBand();
        groupHeaderBand.setHeight(58);

        // Create red background frame for error message
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

        // Create group
        JRDesignGroup group2 = new JRDesignGroup();
        JRDesignExpression groupExpr = new JRDesignExpression();
        groupExpr.setText("(int)($V{REPORT_COUNT}/5)");
        group2.setExpression(groupExpr);

        ((JRDesignSection) group2.getGroupHeaderSection()).addBand(groupHeaderBand);
        ((JRDesignSection) group2.getGroupFooterSection()).addBand(new JRDesignBand());
        jasperDesign.addGroup(group2);

        return JasperCompileManager.compileReport(jasperDesign);
    }

    private void createErrorReportStyles(final JasperDesign jasperDesign) throws JRException {
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
    }
}

