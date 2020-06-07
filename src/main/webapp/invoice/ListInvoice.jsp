<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <title>Invoice List</title>
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
    <script type="text/javascript" src="/js/invoice-scripts.js"></script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body onload="javascript:hideAlerts()">
    <form:form method="POST" modelAttribute="invoiceForm" >
        <form:hidden name="loggedInUser" path="loggedInUser" />
        <form:hidden name="loggedInRole" path="loggedInRole" />
        <input type="hidden" name="id" id="id"/>
        <%@include file="../navbar.jsp" %>
        <div class="container">
            <div class="wrap">
                <div class="card">
                    <div class="card-header">
                        Search Invoice
                    </div>
                    <div class="card-body">
                        <table>
                            <tr>
                                <td>
                                    <label for="id" class="control-label">
                                        Invoice Id :
                                    </label>
                                </td>
                                <td>
                                    <form:input cssClass="form-control" path="searchInvoiceVo.id" id="invoiceId"/>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="Description" class="control-label">
                                        Description :
                                    </label>
                                </td>
                                <td>
                                    <form:input cssClass="form-control" path="searchInvoiceVo.description" id="description"/>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="serialNo" class="control-label">
                                        Serial No :
                                    </label>
                                </td>
                                <td>
                                    <form:input cssClass="form-control" path="searchInvoiceVo.serialNo" id="serialNo"/>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="tagNo" class="control-label">
                                        Tag No :
                                    </label>
                                </td>
                                <td>
                                    <form:input cssClass="form-control" path="searchInvoiceVo.tagNo"  id="tagNo"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="amount" class="control-label">
                                        Amount :
                                    </label>
                                </td>
                                <td>
                                    <form:input cssClass="form-control" path="searchInvoiceVo.amount"  id="amount"/>
                                </td>
                                <td colspan="3">&nbsp;</td>
                                <td>
                                    <label for="greater" class="control-label">
                                        Greater than & Equal
                                        <form:checkbox path="searchInvoiceVo.greater" id="greater" value=""/>
                                    </label>
                                </td>
                                <td colspan="3">&nbsp;</td>
                                <td>
                                    <label for="lesser" class="control-label">
                                        Lesser than & Equal
                                        <form:checkbox path="searchInvoiceVo.lesser" id="lesser" value=""/>
                                    </label>
                                </td>
                                <td colspan="4">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="14">&nbsp;</td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>
                                    <label for="includes" class="control-label">
                                        <spring:message code="user.includes" text="Includes"/>
                                        <form:checkbox path="searchInvoiceVo.includes" id="includes" value=""/>
                                    </label>
                                </td>
                                <td colspan="3">&nbsp;</td>
                                <td>
                                    <label for="startsWith" class="control-label">
                                        <spring:message code="user.startsWith" text="Starts with"/>
                                        <form:checkbox path="searchInvoiceVo.startsWith" id="startsWith" value=""/>
                                    </label>
                                </td>
                                <td colspan="3">&nbsp;</td>
                                <td>
                                    <input class="btn btn-primary" value="<spring:message code="poseidon.search" text="Search" />"
                                           type="button" onclick="search()"/>
                                </td>
                                <td colspan="3">&nbsp;</td>
                                <td>
                                    <input class="btn btn-primary" value="<spring:message code="poseidon.clear" text="Clear" />"
                                           type="button" onclick="clearOut()"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <br />
                <br />
                <c:if test="${invoiceForm.statusMessage!=null}">
                    <div class="alert alert-<c:out value="${invoiceForm.statusMessageType}"/>">
                        <a class="close" data-dismiss="alert" href="#" aria-hidden="true">x</a>
                        <c:out value="${invoiceForm.statusMessage}"/>
                    </div>
                </c:if>
                <div class="panel panel-primary">
                    <div class="panel-heading">Invoice Details</div>
                    <table id='myTable' class="table table-bordered table-striped table-hover">
                        <thead>
                        <tr>
                            <th>&nbsp;</th>
                            <th>Invoice Id</th>
                            <th>Customer Name</th>
                            <th>Tag No</th>
                            <th>Item Description</th>
                            <th>Serial No</th>
                            <th>Total Amount</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${invoiceForm.invoiceVos}" var="iterationInv">
                            <tr>
                                <td><input type="checkbox" name="checkField" onclick="checkCall(this)"
                                           value="<c:out value="${iterationInv.id}" />"/></td>
                                <td><c:out value="${iterationInv.id}"/></td>
                                <td><c:out value="${iterationInv.customerName}"/></td>
                                <td><c:out value="${iterationInv.tagNo}"/></td>
                                <td><c:out value="${iterationInv.description}"/></td>
                                <td><c:out value="${iterationInv.serialNo}"/></td>
                                <td><c:out value="${iterationInv.amount}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <table>
                        <tr>
                            <td>
                                <br/>
                                <br/>
                                <input class="btn btn-primary" value="Add New Invoice" type="button" onclick="addInvoice()"/>
                                <input class="btn btn-primary" value="Edit Invoice" type="button" onclick="editMe()"/>
                                <input class="btn btn-primary" value="Delete Invoice" type="button" onclick="deleteInvoice()"/>
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
