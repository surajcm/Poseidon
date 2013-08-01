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
    <style type="text/css">
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
<body>
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
                        <td>
                            <label for="tagNo">
                                Tag No
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentInvoiceVO.tagNo" id="tagNo"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="description">
                                Description
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td colspan="7">
                            <form:textarea path="currentInvoiceVO.description" id="description"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="quantity">
                                Quantity
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentInvoiceVO.quantity" id="quantity" onkeyup="multiplyFromQty()"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="rate">
                                Rate
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentInvoiceVO.rate" id="rate" onkeyup="multiplyFromRate()" />
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="amount">
                                Amount
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentInvoiceVO.amount" id="amount"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="16">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="12">&nbsp;</td>
                        <td>
                            <input class="btn btn-primary" value="Save" type="button" onclick="javascript:save()"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <input class="btn btn-primary" value="Clear" type="button" onclick="javascript:clearOut()"/>
                        </td>
                    </tr>
                </table>

            </fieldset>
        </div>
    </div>
</form:form>

</body>
</html>
