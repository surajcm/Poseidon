<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit Invoice</title>
    <link rel="stylesheet" type="text/css" href="../css/mainStyles.css"/>
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.css"/>
    <style type="text/css">
        .textfieldMyStyle {
            border:3px double #CCCCCC;
            width: 200px;
            height:20px;
        }
        .foottable {
            margin:auto;
            top:50%;
            left:50%;
        }
        fieldset
        {
            text-align:right;
        }

        table
        {
            margin:auto;
            top:50%;
            left:50%;
        }
    </style>
    <script type="text/javascript">
        function update(){
            if(document.getElementById('tagNo').value == null ||
                    document.getElementById('tagNo').value.length == 0){
                alert("Please enter a valid Tag No");
            }else if(document.getElementById('quantity').value == null
                    && document.getElementById('quantity').value.length == 0){
                alert("Please enter a valid Quantity");
            }else if(document.getElementById('rate').value == null
                    && document.getElementById('rate').value.length == 0){
                alert("Please enter a valid Rate");
            } else {
                document.forms[0].action = "updateInvoice.htm";
                document.forms[0].submit();
            }
        }
        function cancel() {
            document.forms[0].action = "ListInvoice.htm";
            document.forms[0].submit();
        }
    </script>
</head>
<body style="background: #A9A9A9 ;">
<form:form method="POST" commandName="invoiceForm" name="invoiceForm">
    <form:hidden name="loggedInUser" path="loggedInUser" />
    <form:hidden name="loggedInRole" path="loggedInRole" />
    <form:hidden name="id" path="currentInvoiceVO.id" />
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <div id="content">
        <div class="wrap">
            <fieldset>
                <legend>Edit Invoice</legend>
                <table>
                    <tr>
                        <td style="text-align:right;">
                            <label for="tagNo">
                                Tag No
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentInvoiceVO.tagNo" cssClass="textfieldMyStyle" id="tagNo"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="description">
                                Description
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td colspan="7" style="text-align:left;">
                            <form:textarea path="currentInvoiceVO.description" rows="5" cols="30" cssClass="textfieldMyStyle"
                                           id="description"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="quantity">
                                Quantity
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentInvoiceVO.quantity" cssClass="textfieldMyStyle" id="quantity"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="rate">
                                Rate
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentInvoiceVO.rate" cssClass="textfieldMyStyle" id="rate"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="amount">
                                Amount
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentInvoiceVO.amount" cssClass="textfieldMyStyle" id="amount"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="16">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="12">&nbsp;</td>
                        <td>
                            <input class="btn" value="Update" type="button" onclick="javascript:update()"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <input class="btn" value="Cancel" type="button" onclick="javascript:cancel()"/>
                        </td>
                    </tr>
                </table>

            </fieldset>
        </div>
    </div>
</form:form>

</body>
</html>
