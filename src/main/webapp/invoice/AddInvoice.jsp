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
    <title>Add New Invoice</title>
    <script type="text/javascript" src="/js/invoice-add-scripts.js"></script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body>
    <form:form method="POST" modelAttribute="invoiceForm">
        <form:hidden name="loggedInUser" path="loggedInUser"/>
        <form:hidden name="loggedInRole" path="loggedInRole"/>
        <%@include file="../navbar.jsp" %>
        <div class="container">
            <div class="wrap">
                <div class="panel panel-primary">
                    <div class="panel-heading">Add Invoice</div>
                    <table style="margin:auto;top:50%;left:50%;">
                        <tr>
                            <td>
                                <label for="tagNo" class="control-label">
                                    Tag No
                                </label>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <form:input path="currentInvoiceVo.tagNo" cssClass="form-control" id="tagNo"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="description" class="control-label">
                                    Description
                                </label>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td colspan="7">
                                <form:textarea path="currentInvoiceVo.description" cssClass="form-control" id="description"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="quantity" class="control-label">
                                    Quantity
                                </label>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:left;">
                                <form:input path="currentInvoiceVo.quantity" id="quantity" cssClass="form-control" onkeyup="multiplyFromQty()"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="rate" class="control-label">
                                    Rate
                                </label>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <form:input path="currentInvoiceVo.rate" id="rate" cssClass="form-control" onkeyup="multiplyFromRate()" />
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="amount" class="control-label">
                                    Amount
                                </label>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <form:input path="currentInvoiceVo.amount" cssClass="form-control" id="amount"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="16">&nbsp;</td>
                        </tr>
                        <tr>
                            <td colspan="12">&nbsp;</td>
                            <td>
                                <input class="btn btn-primary btn-success" value="Save" type="button" onclick="javascript:save()"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <input class="btn btn-primary" value="Clear" type="button" onclick="javascript:clearOut()"/>
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
