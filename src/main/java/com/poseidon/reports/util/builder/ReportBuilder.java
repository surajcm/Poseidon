package com.poseidon.reports.util.builder;

import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignLine;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.LineStyleEnum;

import java.awt.Color;

/**
 * Base class for building JasperReports with common functionality.
 */
public abstract class ReportBuilder {

    protected static final Color LIGHT_GRAY = new Color(204, 204, 204); // #CCCCCC

    /**
     * Creates a standard Arial normal style.
     */
    protected JRDesignStyle createNormalStyle() {
        JRDesignStyle normalStyle = new JRDesignStyle();
        normalStyle.setName("Arial_Normal");
        normalStyle.setDefault(true);
        normalStyle.setFontName("Arial");
        normalStyle.setFontSize(12f);
        normalStyle.setPdfFontName("Helvetica");
        normalStyle.setPdfEncoding("Cp1252");
        return normalStyle;
    }

    /**
     * Creates a standard Arial bold style.
     */
    protected JRDesignStyle createBoldStyle() {
        JRDesignStyle boldStyle = new JRDesignStyle();
        boldStyle.setName("Arial_Bold");
        boldStyle.setFontName("Arial");
        boldStyle.setFontSize(12f);
        boldStyle.setBold(true);
        boldStyle.setPdfFontName("Helvetica-Bold");
        boldStyle.setPdfEncoding("Cp1252");
        return boldStyle;
    }

    /**
     * Helper method to add a horizontal line to a band.
     */
    protected void addHorizontalLine(final JRDesignBand band,
                                   final int xX,
                                   final int yY,
                                   final int width,
                                   final int height) {
        addLine(band, xX, yY, width, height);
    }

    /**
     * Helper method to add a vertical line to a band.
     */
    protected void addVerticalLine(final JRDesignBand band,
                                 final int xX,
                                 final int yY,
                                 final int width,
                                 final int height) {
        addLine(band, xX, yY, width, height);
    }

    private static void addLine(final JRDesignBand band,
                                final int xX,
                                final int yY,
                                final int width,
                                final int height) {
        JRDesignLine line = new JRDesignLine();
        line.setX(xX);
        line.setY(yY);
        line.setWidth(width);
        line.setHeight(height);
        line.getLinePen().setLineStyle(LineStyleEnum.SOLID);
        band.addElement(line);
    }

    /**
     * Creates a field with the specified name and class.
     */
    protected JRDesignField createField(final String name, final Class<?> valueClass) {
        JRDesignField field = new JRDesignField();
        field.setName(name);
        field.setValueClass(valueClass);
        return field;
    }

    /**
     * Sets up basic report properties for the jasper design.
     */
    protected void setupBasicReportProperties(final JasperDesign jasperDesign,
                                            final String name,
                                            final int pageWidth,
                                            final int pageHeight,
                                            final int columnWidth) {
        jasperDesign.setName(name);
        jasperDesign.setPageWidth(pageWidth);
        jasperDesign.setPageHeight(pageHeight);
        jasperDesign.setColumnWidth(columnWidth);
        jasperDesign.setLeftMargin(20);
        jasperDesign.setRightMargin(20);
        jasperDesign.setTopMargin(20);
        jasperDesign.setBottomMargin(20);
    }
}

