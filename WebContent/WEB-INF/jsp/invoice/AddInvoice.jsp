<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Add New Invoice</title>
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
        function save() {
            if(document.getElementById('tagNo').value.length == 0){
                alert("Please enter a valid Tag No");
            }else if(document.getElementById('quantity').value.length == 0){
                alert("Please enter a valid Quantity");
            }else if(document.getElementById('rate').value.length == 0){
                alert("Please enter a valid Rate");
            }else if(document.getElementById('amount').value.length == 0){
                alert("Please enter a valid Amount");
            } else {
                document.forms[0].action = "saveInvoice.htm";
                document.forms[0].submit();
            }
        }

        function clearOut() {
            document.getElementById('tagNo').value = "";
            document.getElementById('description').value = "";
            document.getElementById('quantity').value = "";
            document.getElementById('rate').value = "";
            document.getElementById('amount').value = "";
        }

        function multiplyFromRate(){
            if(document.getElementById('quantity').value.length > 0) {
                document.getElementById('amount').value = document.getElementById('quantity').value * document.getElementById('rate').value;
            }
        }

        function multiplyFromQty(){
            if(document.getElementById('quantity').value.length > 0 && document.getElementById('rate').value > 0) {
                document.getElementById('amount').value = document.getElementById('quantity').value * document.getElementById('rate').value;
            }
        }
    </script>
</head>
<body style="background: #A9A9A9 ;">
<form:form method="POST" commandName="invoiceForm" name="invoiceForm">
    <form:hidden name="loggedInUser" path="loggedInUser"/>
    <form:hidden name="loggedInRole" path="loggedInRole"/>
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <div id="content">
        <div class="wrap">
            <fieldset>
                <legend>Add Invoice</legend>
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
                            <form:input path="currentInvoiceVO.quantity" cssClass="textfieldMyStyle" id="quantity" onkeyup="javascript:multiplyFromQty()"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="rate">
                                Rate
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentInvoiceVO.rate" cssClass="textfieldMyStyle" id="rate" onkeyup="javascript:multiplyFromRate()" />
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
                            <input class="btn" value="Save" type="button" onclick="javascript:save()"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <input class="btn" value="Clear" type="button" onclick="javascript:clearOut()"/>
                        </td>
                    </tr>
                </table>

            </fieldset>
        </div>
    </div>
</form:form>

</body>
</html>
