<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
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
    <title>Transaction List</title>
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
    <script type="text/javascript" src="/js/transaction-scripts.js"></script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body onload="javascript:hideAlerts()">
    <form:form method="POST" action="List.htm" modelAttribute="transactionForm" >
        <form:hidden name="loggedInUser" path="loggedInUser"/>
        <form:hidden name="loggedInRole" path="loggedInRole"/>
        <input type="hidden" name="id" id="id"/>
        <%@include file="../navbar.jsp" %>
        <div class="container">
            <div class="wrap">
                <div class="card">
                    <div class="card-header">
                        Search Transactions
                    </div>
                    <div class="card-body">
                        <table>
                            <tr>
                                <td>
                                    <label for="TagNo" class="control-label">
                                        Tag No :
                                    </label>
                                </td>
                                <td>
                                    <form:input class="form-control" path="searchTransaction.TagNo" id="TagNo"/>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="CustomerName" class="control-label">
                                        Customer Name :
                                    </label>
                                </td>
                                <td>
                                    <form:input class="form-control" path="searchTransaction.CustomerName" id="CustomerName"/>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="start" class="control-label">
                                        Reported Date (From) :
                                    </label>
                                </td>
                                <td>
                                    <div class="input-group">
                                        <form:input path="searchTransaction.startDate" class="date-picker form-control" id = "startDate" />
                                        <label for="start" class="input-group-addon btn"><span class="fa fa-calendar"></span>
                                    </div>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="end" class="control-label">
                                        Reported Date (To) :
                                    </label>
                                </td>
                                <td>
                                    <div class="input-group">
                                        <form:input path="searchTransaction.endDate" class="date-picker form-control" id = "endDate" />
                                        <label for="end" class="input-group-addon btn"><span class="fa fa-calendar"></span></label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="14">&nbsp;</td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="SerialNo" class="control-label">
                                        Serial No :
                                    </label>
                                </td>
                                <td>
                                    <form:input class="form-control" path="searchTransaction.SerialNo" id="SerialNo"/>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="makeId" class="control-label">
                                        Make :
                                    </label>
                                </td>
                                <td>
                                    <form:select id="makeId" cssClass="form-control" path="searchTransaction.makeId" tabindex="1" onchange="changeTheModel();">
                                        <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                        <form:options items="${transactionForm.makeVOs}" itemValue="Id" itemLabel="makeName"/>
                                    </form:select>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="modelId" class="control-label">
                                        Model Name :
                                    </label>
                                </td>
                                <td>
                                    <form:select id="modelId" cssClass="form-control" path="searchTransaction.modelId" tabindex="1">
                                        <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                        <form:options items="${transactionForm.makeAndModelVOs}"
                                                      itemValue="modelId" itemLabel="modelName"/>
                                    </form:select>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="Status" class="control-label">
                                        Status :
                                    </label>
                                </td>
                                <td>
                                    <form:select id="Status" cssClass="form-control" path="searchTransaction.Status">
                                        <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                        <form:options items="${transactionForm.statusList}" />
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="14">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="12">&nbsp;</td>
                                <td>
                                    <label for="includes" class="control-label">
                                        <spring:message code="user.includes" text="Includes"/>
                                        <form:checkbox path="searchTransaction.includes" cssStyle="vertical-align:middle" id="includes" value="" />
                                    </label>
                                </td>
                                <td>
                                    <label for="startswith" class="control-label">
                                        <spring:message code="user.startsWith" text="Starts with"/>
                                        <form:checkbox path="searchTransaction.startswith" cssStyle="vertical-align:middle" id="startswith" value="" />
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="12">&nbsp;</td>
                                <td>
                                    <input class="btn btn-primary" value="<spring:message code="poseidon.search" text="Search" />"
                                           type="button" onclick="javascript:search()"/>
                                </td>
                                <td>
                                    <input class="btn btn-primary" value="<spring:message code="poseidon.clear" text="Clear" />"
                                           type="button" onclick="javascript:clearOut()"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <br />
                <br />
                <c:if test="${transactionForm.statusMessage!=null}">
                    <div class="alert alert-<c:out value='${transactionForm.statusMessageType}'/>">
                        <a class="close" data-dismiss="alert" href="#" aria-hidden="true">x</a>
                        <c:out value="${transactionForm.statusMessage}"/>
                    </div>
                </c:if>
                <div class="panel panel-primary">
                    <div class="panel-heading">Transaction Details</div>
                    <table id='myTable' class="table table-bordered table-striped table-hover">
                        <thead>
                        <tr>
                            <th><spring:message code="poseidon.id" text="id"/></th>
                            <th>TagNo</th>
                            <th>Customer Name</th>
                            <th>Reported Date</th>
                            <th>Make</th>
                            <th>Model</th>
                            <th>Serial No</th>
                            <th>Status</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${transactionForm.transactionsList}" var="iterationTxn">
                            <tr>
                                <td><input type="checkbox" name="checkField" onclick="javascript:checkCall(this)"
                                           value="<c:out value='${iterationTxn.id}' />"/></td>
                                <td><c:out value="${iterationTxn.tagNo}"/></td>
                                <td><c:out value="${iterationTxn.customerName}"/></td>
                                <td><c:out value="${iterationTxn.dateReported}"/></td>
                                <td><c:out value="${iterationTxn.makeName}"/></td>
                                <td><c:out value="${iterationTxn.modelName}"/></td>
                                <td><c:out value="${iterationTxn.serialNo}"/></td>
                                <td><c:out value="${iterationTxn.status}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <table>
                        <tr>
                            <td>
                                <br/>
                                <br/>
                                <input class="btn btn-primary" value="Add New Transaction" type="button" onclick="javascript:addNew()"/>
                                <input class="btn btn-primary" value="Edit Transaction" type="button" onclick="javascript:editMe()"/>
                                <input class="btn btn-primary" value="Delete Transaction" type="button" onclick="javascript:deleteTxn()"/>
                                <input class="btn btn-primary" value="Invoice Transaction" type="button" onclick="javascript:invoiceTxn()"/>
                            </td>
                        </tr>
                    </table>
                    </fieldset>
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
                $(function() {
                    $("#startDate").datepicker();
                     $("#endDate").datepicker();
                });
            });
        </script>
    </form:form>
</body>
</html>
