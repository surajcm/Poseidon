<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Suraj">
    <spring:url value="/img/Poseidon_Ico.ico" var="posIcon" />
    <link rel="shortcut icon" href="${posIcon}" />
    <link rel="stylesheet" href="/css/bootstrap.min.css"  type="text/css" />
    <link rel="stylesheet" href="/css/custom.css" type="text/css" />
    <title>Add New Customer</title>
    <script type="text/javascript">
        function save() {
            if(document.getElementById('customerName').value.length == 0) {
                document.getElementById('customerName').style.background = 'Yellow';
                alert("Please enter a valid Customer Name");
            } else if(document.getElementById('mobile').value.length == 0) {
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
            document.getElementById('address').value = "";
            document.getElementById('phoneNo').value = "";
            document.getElementById('mobile').value = "";
            document.getElementById('email').value = "";
            document.getElementById('contactPerson').value = "";
            document.getElementById('contactMobile').value = "";
            document.getElementById('notes').value = "";
        }
    </script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body>
<form:form method="POST" modelAttribute="customerForm">
    <form:hidden name="loggedInUser" path="loggedInUser"/>
    <form:hidden name="loggedInRole" path="loggedInRole"/>
    <%@include file="../navbar.jsp" %>
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
                            <label for="address" class="control-label">
                                Address
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:textarea path="currentCustomerVO.address" cssClass="form-control" id="address"/>
                        </td>
                        <td colspan="6">&nbsp;</td>
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
                            <label for="contactPerson" class="control-label">
                                Contact Person
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:input path="currentCustomerVO.contactPerson" cssClass="form-control" id="contactPerson"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="contactMobile" class="control-label">
                                Mobile of Contact Person
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:left;">
                            <form:input path="currentCustomerVO.contactMobile" cssClass="form-control" id="contactMobile"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="notes" class="control-label">
                                Notes
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <form:textarea path="currentCustomerVO.notes" cssClass="form-control" id="notes"/>
                        </td>
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
    <script src="/js/core/jquery-3.2.1.min.js"></script>
    <script src="/js/core/popper.min.js"></script>
    <script src="/js/core/bootstrap.min.js"></script>
    <script>
        $(document).ready(function() {
            //Handles menu drop down
            $('.dropdown-menu').find('form').click(function (e) {
                e.stopPropagation();
            });
        });
    </script>
</form:form>

</body>
</html>
