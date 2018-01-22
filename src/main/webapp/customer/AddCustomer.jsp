<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Add New Customer</title>
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
            if(document.getElementById('customerName').value.length == 0){
                document.getElementById('customerName').style.background = 'Yellow';
                alert("Please enter a valid Customer Name");
            } else if(document.getElementById('mobile').value.length == 0){
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
    <%@include file="../myHeader.jsp" %>
    <div class="container">
        <div class="wrap">
            <div class="panel panel-primary">
                <div class="panel-heading">Add Customer</div>
                <table style="margin:auto;top:50%;left:50%;">
                    <tr>
                        <td>
                            <label for="customerName" class="control-label">
                                Customer Name
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentCustomerVO.customerName" cssClass="form-control" id="customerName"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="address1" class="control-label">
                                Address Line 1
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:textarea path="currentCustomerVO.address1" cssClass="form-control" id="address1"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="address2" class="control-label">
                                Address Line 2
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:textarea path="currentCustomerVO.address2" cssClass="form-control" id="address2"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="phoneNo" class="control-label">
                                Phone
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentCustomerVO.phoneNo" cssClass="form-control" id="phoneNo"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="mobile" class="control-label" >
                                Mobile
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentCustomerVO.mobile" cssClass="form-control" id="mobile"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="email" class="control-label">
                                email
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentCustomerVO.email" cssClass="form-control" id="email"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="contactPerson1" class="control-label">
                                Contact Person 1
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentCustomerVO.contactPerson1" cssClass="form-control" id="contactPerson1"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="contactMobile1" class="control-label">
                                Mobile of Contact Person 1
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.contactMobile1" cssClass="form-control" id="contactMobile1"/>
                        </td>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="contactPerson2" class="control-label">
                                Contact Person 2
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentCustomerVO.contactPerson2" cssClass="form-control" id="contactPerson2"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="contactMobile2" class="control-label">
                                Mobile of Contact Person 2
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentCustomerVO.contactMobile2" cssClass="form-control" id="contactMobile2"/>
                        </td>
                        <td colspan="6">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="notes" class="control-label">
                                Notes
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:textarea path="currentCustomerVO.notes" cssClass="form-control" id="notes"/>
                        </td>
                        <td colspan="12">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="12">&nbsp;</td>
                        <td>
                            <input class="btn btn-primary btn-success" value="Save" type="button" onclick="save()"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <input class="btn btn-primary" value="Clear" type="button" onclick="clearOut()"/>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</form:form>

</body>
</html>
