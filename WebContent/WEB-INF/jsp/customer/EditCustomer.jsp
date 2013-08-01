<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit Customer</title>
    <link rel="stylesheet" type="text/css" href="../css/mainStyles.css"/>
    <style type="text/css">
	    table {
            margin:auto;
            top:50%;
            left:50%;
        }
    </style>
    <script type="text/javascript">
        function update(){
            if(document.getElementById('customerName').value == null ||
                    document.getElementById('customerName').value.length == 0){
                alert("Please enter a valid Customer Name");
            }else if(document.getElementById('mobile').value == null
                    && document.getElementById('mobile').value.length == 0){
                alert("Please enter a valid Mobile Number");
            } else {
                document.forms[0].action = "updateCustomer.htm";
                document.forms[0].submit();
            }
        }

        function cancel(){
            document.forms[0].action = "List.htm";
            document.forms[0].submit();
        }
    </script>

</head>
<body>
<form:form method="POST" commandName="customerForm" name="customerForm">
    <form:hidden name="loggedInUser" path="loggedInUser"/>
    <form:hidden name="loggedInRole" path="loggedInRole"/>
    <form:hidden name="currentCustomerVO.customerId" path="currentCustomerVO.customerId" />
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <div id="content">
        <div class="wrap">
            <fieldset>
                <legend>Edit Customer</legend>
                <table>
                    <tr>
                        <td>
                            <label for="customerName">
                                Customer Name :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.customerName" id="customerName"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="address1">
                                Address Line 1 :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:textarea path="currentCustomerVO.address1" id="address1"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="address2">
                                Address Line 2 :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:textarea path="currentCustomerVO.address2" id="address2"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="phoneNo">
                                Phone :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.phoneNo" id="phoneNo"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="mobile">
                                Mobile :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.mobile" id="mobile"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="email">
                                Email :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.email" id="email"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="contactPerson1">
                                Contact Person 1 :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.contactPerson1" id="contactPerson1"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="contactMobile1">
                                Mobile of Contact Person 1 :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.contactMobile1" id="contactMobile1"/>
                        </td>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="contactPerson2">
                                Contact Person 2 :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.contactPerson2" id="contactPerson2"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="contactMobile2">
                                Mobile of Contact Person 2 :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.contactMobile2" id="contactMobile2"/>
                        </td>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="notes">
                                Notes :
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:textarea path="currentCustomerVO.notes" id="notes"/>
                        </td>
                        <td colspan="12">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="12">&nbsp;</td>
                        <td>
                            <input class="btn btn-primary" value="Update" type="button" onclick="javascript:update()"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <input class="btn btn-primary" value="Cancel" type="button" onclick="javascript:cancel()"/>
                        </td>
                    </tr>
                </table>

            </fieldset>
        </div>
    </div>
</form:form>

</body>
</html>
