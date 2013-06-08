<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Add New Customer</title>
    <link rel="stylesheet" type="text/css" href="../css/mainStyles.css"/>
    <!--link rel="stylesheet" type="text/css" href="../css/bootstrap.css"/-->
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
            if(document.getElementById('customerName').value.length == 0){
                document.getElementById('customerName').style.background = 'Yellow';
                alert("Please enter a valid Customer Name");
            }else if(document.getElementById('mobile').value.length == 0){
                document.getElementById('customerName').style.background = 'White';
                document.getElementById('mobile').style.background = 'Yellow';
                alert("Please enter a valid Mobile Number");
            } else {
                document.getElementById('customerName').style.background = 'White';
                document.getElementById('mobile').style.background = 'White';
                document.forms[0].action = "saveCustomer.htm";
                document.forms[0].submit();
            }
        }

        function clearOut() {
            document.getElementById('customerName').value = "";
            document.getElementById('address1').value = "";
            document.getElementById('address2').value = "";
            document.getElementById('phoneNo').value = "";
            document.getElementById('mobile').value = "";
            document.getElementById('email').value = "";
            document.getElementById('contactPerson1').value = "";
            document.getElementById('contactMobile1').value = "";
            document.getElementById('contactPerson2').value = "";
            document.getElementById('contactMobile2').value = "";
            document.getElementById('notes').value = "";
        }
    </script>

</head>
<body>
<form:form method="POST" commandName="customerForm" name="customerForm">
    <form:hidden name="loggedInUser" path="loggedInUser"/>
    <form:hidden name="loggedInRole" path="loggedInRole"/>
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <div id="content">
        <div class="wrap">
            <fieldset>
                <legend>Add Customer</legend>
                <table>
                    <tr>
                        <td style="text-align:right;">
                            <label for="customerName">
                                Customer Name
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.customerName" cssClass="textfieldMyStyle" id="customerName"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="address1">
                                Address Line 1
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:textarea path="currentCustomerVO.address1" rows="5" cols="30" cssClass="textfieldMyStyle"
                                           id="address1"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="address2">
                                Address Line 2
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:textarea path="currentCustomerVO.address2" rows="5" cols="30" cssClass="textfieldMyStyle"
                                           id="address2"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="phoneNo">
                                Phone
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.phoneNo" cssClass="textfieldMyStyle" id="phoneNo"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="mobile" >
                                Mobile
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.mobile" cssClass="textfieldMyStyle" id="mobile"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="email">
                                email
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.email" cssClass="textfieldMyStyle" id="email"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="contactPerson1">
                                Contact Person 1
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.contactPerson1" cssClass="textfieldMyStyle"
                                        id="contactPerson1"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="contactMobile1">
                                Mobile of Contact Person 1
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.contactMobile1" cssClass="textfieldMyStyle"
                                        id="contactMobile1"/>
                        </td>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="contactPerson2">
                                Contact Person 2
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.contactPerson2" cssClass="textfieldMyStyle"
                                        id="contactPerson2"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="contactMobile2">
                                Mobile of Contact Person 2
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.contactMobile2" cssClass="textfieldMyStyle"
                                        id="contactMobile2"/>
                        </td>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="notes">
                                Notes
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:textarea path="currentCustomerVO.notes" rows="5" cols="30" cssClass="textfieldMyStyle"
                                           id="notes"/>
                        </td>
                        <td colspan="12">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
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
