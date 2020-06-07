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
    <spring:url value="/resources/images/Poseidon_Ico.ico" var="posIcon" />
    <link rel="shortcut icon" href="${posIcon}" />
    <link rel="stylesheet" href="/css/jquery-ui.css" type="text/css" />
    <link rel="stylesheet" href="/css/font-awesome.min.css" type="text/css" />
    <link rel="stylesheet" href="/css/bootstrap.min.css"  type="text/css" />
    <link rel="stylesheet" href="/css/custom.css" type="text/css" />
    <title>Edit Customer</title>
    <style type="text/css">
	    table {
            margin:auto;
            top:50%;
            left:50%;
        }
        .foottable {
            margin:auto;
            top:50%;
            left:50%;
        }
    </style>
    <script type="text/javascript">
        function update(){
            if (document.getElementById('customerName').value == null ||
                    document.getElementById('customerName').value.length == 0) {
                alert("Please enter a valid Customer Name");
            } else if (document.getElementById('mobile').value == null
                    && document.getElementById('mobile').value.length == 0) {
                alert("Please enter a valid Mobile Number");
            } else {
                document.forms[0].action = "updateCustomer.htm";
                document.forms[0].submit();
            }
        }

        function cancel() {
            document.forms[0].action = "List.htm";
            document.forms[0].submit();
        }
    </script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body>
    <form:form method="POST" modelAttribute="customerForm">
        <form:hidden name="loggedInUser" path="loggedInUser"/>
        <form:hidden name="loggedInRole" path="loggedInRole"/>
        <form:hidden name="currentCustomerVO.customerId" path="currentCustomerVO.customerId" />
        <%@include file="../navbar.jsp" %>
        <div class="container">
            <div class="wrap">
                <div class="panel panel-primary">
                    <div class="panel-heading">Edit Customer</div>
                    <table style="margin:auto;top:50%;left:50%;">
                        <tr>
                            <td>
                                <label for="customerName" class="control-label">
                                    Customer Name :
                                </label>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:left;">
                                <form:input path="currentCustomerVO.customerName" cssClass="form-control" id="customerName"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:right;">
                                <label for="address1" class="control-label">
                                    Address Line 1 :
                                </label>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:left;">
                                <form:textarea path="currentCustomerVO.address1" cssClass="form-control" id="address1"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:right;">
                                <label for="address2" class="control-label">
                                    Address Line 2 :
                                </label>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:left;">
                                <form:textarea path="currentCustomerVO.address2" cssClass="form-control" id="address2"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="14">&nbsp;</td>
                        </tr>
                        <tr>
                            <td style="text-align:right;">
                                <label for="phoneNo" class="control-label">
                                    Phone :
                                </label>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:left;">
                                <form:input path="currentCustomerVO.phoneNo" cssClass="form-control" id="phoneNo"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:right;">
                                <label for="mobile" class="control-label">
                                    Mobile :
                                </label>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:left;">
                                <form:input path="currentCustomerVO.mobile" cssClass="form-control" id="mobile"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:right;">
                                <label for="email" class="control-label">
                                    Email :
                                </label>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:left;">
                                <form:input path="currentCustomerVO.email" cssClass="form-control" id="email"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="14">&nbsp;</td>
                        </tr>
                        <tr>
                            <td style="text-align:right;">
                                <label for="contactPerson1" class="control-label">
                                    Contact Person 1 :
                                </label>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:left;">
                                <form:input path="currentCustomerVO.contactPerson1" cssClass="form-control" id="contactPerson1"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:right;">
                                <label for="contactMobile1" class="control-label">
                                    Mobile of Contact Person 1 :
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
                            <td style="text-align:right;">
                                <label for="contactPerson2" class="control-label">
                                    Contact Person 2 :
                                </label>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:left;">
                                <form:input path="currentCustomerVO.contactPerson2" cssClass="form-control" id="contactPerson2"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:right;">
                                <label for="contactMobile2" class="control-label">
                                    Mobile of Contact Person 2 :
                                </label>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:left;">
                                <form:input path="currentCustomerVO.contactMobile2" cssClass="form-control" id="contactMobile2"/>
                            </td>
                            <td colspan="6">&nbsp;</td>
                        </tr>
                        <tr>
                            <td colspan="14">&nbsp;</td>
                        </tr>
                        <tr>
                            <td style="text-align:right;">
                                <label for="notes" class="control-label">
                                    Notes :
                                </label>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:left;">
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
                                <input class="btn btn-primary btn-success" value="Update" type="button" onclick="javascript:update()"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <input class="btn btn-primary" value="Cancel" type="button" onclick="javascript:cancel()"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <script src="/js/core/jquery-3.2.1.min.js"></script>
        <script src="/js/core/popper.min.js"></script>
        <script src="/js/core/bootstrap.min.js"></script>
        <script src="/js/core/jquery-ui.min.js"></script>
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
