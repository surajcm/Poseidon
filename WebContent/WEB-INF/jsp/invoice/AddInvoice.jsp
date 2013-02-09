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
<style type="text/css">html {
		  font-size: 100%;
		  -webkit-text-size-adjust: 100%;
		  -ms-text-size-adjust: 100%;
		}
		button,
		input,
		select,
		textarea {
		  margin: 0;
		  font-size: 100%;
		  vertical-align: middle;
		}

		button,
		input {
		  *overflow: visible;
		  line-height: normal;
		}

		button::-moz-focus-inner,
		input::-moz-focus-inner {
		  padding: 0;
		  border: 0;
		}

		button,
		input[type="button"],
		input[type="reset"],
		input[type="submit"] {
		  cursor: pointer;
		  -webkit-appearance: button;
		}
		input[type="search"] {
		  -webkit-box-sizing: content-box;
			 -moz-box-sizing: content-box;
				  box-sizing: content-box;
		  -webkit-appearance: textfield;
		}

		input[type="search"]::-webkit-search-decoration,
		input[type="search"]::-webkit-search-cancel-button {
		  -webkit-appearance: none;
		}

		textarea {
		  overflow: auto;
		  vertical-align: top;
		}

		.clearfix {
		  *zoom: 1;
		}

		.clearfix:before,
		.clearfix:after {
		  display: table;
		  line-height: 0;
		  content: "";
		}

		.clearfix:after {
		  clear: both;
		}

		.hide-text {
		  font: 0/0 a;
		  color: transparent;
		  text-shadow: none;
		  background-color: transparent;
		  border: 0;
		}

		.input-block-level {
		  display: block;
		  width: 100%;
		  min-height: 30px;
		  -webkit-box-sizing: border-box;
			 -moz-box-sizing: border-box;
				  box-sizing: border-box;
		}

		body {
		  margin: 0;
		  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
		  font-size: 14px;
		  line-height: 20px;
		  color: #333333;
		  background-color: #ffffff;
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
                <table style="margin:auto;top:50%;left:50%;">
                    <tr>
                        <td style="text-align:right;">
                            <label for="tagNo" style="font-size: .70em;">
                                Tag No
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentInvoiceVO.tagNo" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="tagNo"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="description" style="font-size: .70em;">
                                Description
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td colspan="7" style="text-align:left;">
                            <form:textarea path="currentInvoiceVO.description" rows="5" cols="30" cssStyle="border:3px double #CCCCCC; width: 500px;height:20px;"
                                           id="description"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="quantity" style="font-size: .70em;">
                                Quantity
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentInvoiceVO.quantity" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="quantity" onkeyup="javascript:multiplyFromQty()"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="rate" style="font-size: .70em;">
                                Rate
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentInvoiceVO.rate" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="rate" onkeyup="javascript:multiplyFromRate()" />
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="amount" style="font-size: .70em;">
                                Amount
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentInvoiceVO.amount" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="amount"/>
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
