package com.poseidon.reports.util.builder;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;

/**
 * Builder for Service Bill Report.
 */
public class ServiceBillReportBuilder extends ReportBuilder {

    public JasperReport build() throws JRException {
        JasperDesign jasperDesign = new JasperDesign();

        setupBasicReportProperties(jasperDesign, "serviceBillReport", 720, 842, 680);

        // Create and add styles
        jasperDesign.addStyle(createNormalStyle());
        jasperDesign.addStyle(createBoldStyle());

        // Add fields
        addServiceBillFields(jasperDesign);

        // Create bands
        jasperDesign.setTitle(createServiceBillTitleBand());
        jasperDesign.setPageHeader(createServiceBillPageHeaderBand());
        jasperDesign.setColumnHeader(createServiceBillColumnHeaderBand());
        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(createServiceBillDetailBand());

        return JasperCompileManager.compileReport(jasperDesign);
    }

    private void addServiceBillFields(final JasperDesign jasperDesign) throws JRException {
        // Updated fields based on TransactionVO from logs
        jasperDesign.addField(createField("invoiceId", java.lang.Long.class));
        jasperDesign.addField(createField("tagNo", java.lang.String.class)); // Used as invoice number
        jasperDesign.addField(createField("customerName", java.lang.String.class));
        jasperDesign.addField(createField("customerAddress", java.lang.String.class));
        jasperDesign.addField(createField("description", java.lang.String.class));
        jasperDesign.addField(createField("serialNo", java.lang.String.class));
        jasperDesign.addField(createField("quantity", java.lang.Integer.class));
        jasperDesign.addField(createField("rate", java.lang.Double.class));
        jasperDesign.addField(createField("amount", java.lang.Double.class));
        jasperDesign.addField(createField("totalAmount", java.lang.Double.class));
        jasperDesign.addField(createField("companyName", java.lang.String.class));
        jasperDesign.addField(createField("companyAddress", java.lang.String.class));
        jasperDesign.addField(createField("companyPhoneNumber", java.lang.String.class));
        jasperDesign.addField(createField("companyWebsite", java.lang.String.class));
        jasperDesign.addField(createField("companyEmail", java.lang.String.class));
        jasperDesign.addField(createField("companyVatTin", java.lang.String.class));
        jasperDesign.addField(createField("companyCstTin", java.lang.String.class));
    }

    private JRDesignBand createServiceBillTitleBand() {
        JRDesignBand titleBand = new JRDesignBand();
        titleBand.setHeight(141);

        // Invoice title
        JRDesignStaticText invoiceTitle = new JRDesignStaticText();
        invoiceTitle.setX(253);
        invoiceTitle.setY(0);
        invoiceTitle.setWidth(210);
        invoiceTitle.setHeight(25);
        invoiceTitle.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        invoiceTitle.setFontName("Arial");
        invoiceTitle.setFontSize(18f);
        invoiceTitle.setText("SERVICE BILL");
        invoiceTitle.setBold(true);
        titleBand.addElement(invoiceTitle);

        // Company name
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

        // Company address
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


        // Add border line
        // Top border line
        addHorizontalLine(titleBand, 2, 25, 677, 2);
        // Left vertical line
        addVerticalLine(titleBand, 1, 26, 1, 111);
        // Bottom border line
        addHorizontalLine(titleBand, 0, 138, 679, 1);
        // Right vertical line
        addVerticalLine(titleBand, 680, 27, 1, 110);
        // Vertical line between company info and invoice details
        addVerticalLine(titleBand, 292, 27, 1, 110);
        // Horizontal line dividing invoice details
        addHorizontalLine(titleBand, 293, 80, 386, 1);
        // Left small vertical line
        addVerticalLine(titleBand, 88, 27, 1, 110);
        // Vertical divider in invoice details section
        addVerticalLine(titleBand, 476, 27, 1, 52);
        addVerticalLine(titleBand, 476, 81, 1, 56);

        return titleBand;
    }

    private JRDesignBand createServiceBillPageHeaderBand() {
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
        addVerticalLine(pageHeaderBand, 1, 1, 1, 140);
        // Right vertical line
        addVerticalLine(pageHeaderBand, 680, 1, 1, 140);
        // Bottom border line
        addHorizontalLine(pageHeaderBand, 1, 142, 678, 1);

        return pageHeaderBand;
    }

    /**
     * Creates the column header band for the Service Bill Report.
     */
    private JRDesignBand createServiceBillColumnHeaderBand() {
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
        addVerticalLine(columnHeaderBand, 1, 0, 1, 19);
        // Right vertical line
        addVerticalLine(columnHeaderBand, 680, 0, 1, 19);
        // Bottom border line
        addHorizontalLine(columnHeaderBand, 1, 20, 678, 1);
        // Vertical divider lines
        addVerticalLine(columnHeaderBand, 50, 0, 1, 19);
        addVerticalLine(columnHeaderBand, 365, 0, 1, 19);
        addVerticalLine(columnHeaderBand, 439, 0, 1, 19);
        addVerticalLine(columnHeaderBand, 526, 0, 1, 19);

        return columnHeaderBand;
    }

    private JRDesignBand createServiceBillDetailBand() {
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
        addVerticalLine(detailBand, 1, 0, 1, 340);
        // Right vertical line
        addVerticalLine(detailBand, 680, 0, 1, 340);
        // Vertical divider lines for the item row
        addVerticalLine(detailBand, 50, 0, 1, 68);
        addVerticalLine(detailBand, 365, 0, 1, 68);
        addVerticalLine(detailBand, 439, 0, 1, 68);
        addVerticalLine(detailBand, 526, 0, 1, 68);

        // Bottom line for the item row
        addHorizontalLine(detailBand, 1, 68, 679, 1);
        // Total section top line
        addHorizontalLine(detailBand, 439, 290, 241, 1);
        // Total section bottom line
        addHorizontalLine(detailBand, 439, 330, 241, 1);
        // Divider between Total label and amount
        addHorizontalLine(detailBand, 526, 290, 1, 40);
        return detailBand;
    }

}
