<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Add New Customer</title>
    <link rel="stylesheet" type="text/css" href="../css/mainStyles.css"/>
    <script type="text/javascript">
        function save() {
            document.forms[0].action = "saveCustomer.htm";
            document.forms[0].submit();
        }

        function clear() {

        }
    </script>

</head>
<body style="background: #A9A9A9 ;">
<form:form method="POST" commandName="customerForm" name="customerForm">
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <form:hidden name="loggedInUser" path="loggedInUser"/>
    <form:hidden name="loggedInRole" path="loggedInRole"/>
    <div id="content">
        <div class="wrap">
            <fieldset style="text-align:left;">
                <legend>Add Customer</legend>
                <table style="margin:auto;top:50%;left:50%;">
                    <tr>
                        <td>
                            <label for="customerName" style="font-size: .70em;">
                                Customer Name
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentCustomerVO.customerName" cssClass="textboxes" id="customerName"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="address1" style="font-size: .70em;">
                                Address Line 1
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:textarea path="currentCustomerVO.address1" rows="5" cols="30" cssClass="textboxes"
                                           id="address1"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="address2" style="font-size: .70em;">
                                Address Line 2
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:textarea path="currentCustomerVO.address2" rows="5" cols="30" cssClass="textboxes"
                                           id="address2"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="address2" style="font-size: .70em;">
                                Phone
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentCustomerVO.phoneNo" cssClass="textboxes" id="phoneNo"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="mobile" style="font-size: .70em;">
                                Mobile
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentCustomerVO.mobile" cssClass="textboxes" id="mobile"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="email" style="font-size: .70em;">
                                email
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentCustomerVO.email" cssClass="textboxes" id="email"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="contactPerson1" style="font-size: .70em;">
                                Contact Person 1
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentCustomerVO.contactPerson1" cssClass="textboxes"
                                        id="contactPerson1"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="contactMobile1" style="font-size: .70em;">
                                Mobile of Contact Person 1
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentCustomerVO.contactMobile1" cssClass="textboxes"
                                        id="contactMobile1"/>
                        </td>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="contactPerson2" style="font-size: .70em;">
                                Contact Person 2
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentCustomerVO.contactPerson2" cssClass="textboxes"
                                        id="contactPerson2"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="contactMobile2" style="font-size: .70em;">
                                Mobile of Contact Person 2
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentCustomerVO.contactMobile2" cssClass="textboxes"
                                        id="contactMobile2"/>
                        </td>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="notes" style="font-size: .70em;">
                                Notes
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:textarea path="currentCustomerVO.notes" rows="5" cols="30" cssClass="textboxes"
                                           id="notes"/>
                        </td>
                        <td colspan="12">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="12">&nbsp;</td>
                        <td>
                            <input class="btn" value="Save" type="button" onclick="javascript:save()"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <input class="btn" value="Clear" type="button" onclick="javascript:clear()"/>
                        </td>
                    </tr>
                </table>

            </fieldset>
        </div>
    </div>
</form:form>

</body>
</html>