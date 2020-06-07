<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

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

    <style type="text/css">
        .foottable {
            margin:auto;
            top:50%;
            left:50%;
        }
    </style>
    <title>Reports List</title>
    <script type="text/javascript" src="/js/report-scripts.js"></script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body onload="javascript:selectMenu()">
    <form:form method="POST" modelAttribute="reportsForm" >
        <form:hidden name="loggedInUser" path="loggedInUser"/>
        <form:hidden name="loggedInRole" path="loggedInRole"/>
        <form:hidden name="exportTo" path="currentReport.exportTo" id="exportTo"/>
        <form:hidden name="tagNo" path="currentReport.tagNo" id="tagNo"/>
        <%@include file="../navbar.jsp" %>
        <div class="container">
            <ul class="nav nav-tabs">
                <li class="nav-item">
                    <a class="nav-link active" href="#callReport" data-toggle="tab">Generate Call Report :</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#modelReport" data-toggle="tab">Generate Make/Model Report :</a></li>
                <li>
                    <a class="nav-link" href="#txnReport" data-toggle="tab">Generate TransactionsList Report :</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#invoiceReport" data-toggle="tab">Generate Invoice Report :</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#invoiceListReport" data-toggle="tab">Generate InvoiceList Report :</a>
                </li>
            </ul>
        <div class="tabbable">
        <div class="tab-content">
        <div class="tab-pane active" id="callReport">
            <br />
            <table>
                <tr>
                    <td>
                        <label for="tagNo" class="control-label">
                            Tag To :
                        </label>
                    </td>
                    <td>
                        <input type="text" id="callTagNo" class="form-control" />
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <label for="exportTo" class="control-label">
                            Export To :
                        </label>
                    </td>
                    <td>
                        <select class="form-control" id="callExportValue" >
                            <option value=""></option>
                            <c:forEach var="n" items="${reportsForm.exportList}" varStatus="rowCounter">
                                <option value="${n}">${n}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <input class="btn btn-primary" value="Fetch Call Report" type="button"
                               onclick="javascript:fetchCallReport()"/>
                    </td>
                    <td>
                        <input class="btn btn-primary" value="Clear" type="button" onclick="javascript:clearOut()"/>
                    </td>
                </tr>
            </table>
        </div>
        <div class="tab-pane" id="modelReport">
            <br />
            <table>
                <tr>
                    <td>
                        <label class="control-label">
                            Make Name :
                        </label>
                    </td>
                    <td>
                        <form:select id="modelReportMakeName" cssClass="form-control" path="modelReportMakeAndModelVO.makeId">
                            <form:option value="0" label="-- Select --"/>
                            <form:options items="${reportsForm.makeVOs}" itemValue="id" itemLabel="makeName"/>
                        </form:select>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <label class="control-label" >
                            Model Name :
                        </label>
                    </td>
                    <td>
                        <form:input path="modelReportMakeAndModelVO.modelName" cssClass="form-control" id="modelReportModelName"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <label class="control-label">
                            <spring:message code="user.includes" text="Includes"/>
                            <form:checkbox path="modelReportMakeAndModelVO.includes" cssStyle="vertical-align:middle"
                                           id="includes" value=""/>
                        </label>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td colspan="2">
                        <label class="control-label">
                            <spring:message code="user.startsWith" text="Starts with"/>
                            <form:checkbox path="modelReportMakeAndModelVO.startswith" cssStyle="vertical-align:middle"
                                           id="startswith" value=""/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="exportTo" class="control-label" >
                            Export To :
                        </label>
                    </td>
                    <td>
                        <select id="makeExportValue" class="form-control" >
                            <option value=""></option>
                            <c:forEach var="n" items="${reportsForm.exportList}" varStatus="rowCounter">
                                <option value="${n}">${n}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td colspan="2">
                        <input class="btn btn-primary" value="Fetch Make Report" type="button"
                               onclick="javascript:fetchMakeReport()"/>
                    </td>
                    <td>
                        <input class="btn btn-primary" value="Fetch Model List Report" type="button"
                               onclick="javascript:fetchModelListReport()"/>
                    </td>
                    <td>
                        <input class="btn btn-primary" value="Clear" type="button" onclick="javascript:clearOut()"/>
                    </td>
                </tr>
            </table>
        </div>
        <div class="tab-pane" id="txnReport">
            <br />
            <table>
                <tr>
                    <td>
                        <label for="TagNo"  class="control-label" >
                            Tag No :
                        </label>
                    </td>
                    <td>
                        <form:input path="txnReportTransactionVO.TagNo" cssClass="form-control" id="txnReportTagNo"/>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <label for="CustomerName"  class="control-label" >
                            Customer Name :
                        </label>
                    </td>
                    <td>
                        <form:input path="txnReportTransactionVO.CustomerName" cssClass="form-control" id="txnReportCustomerName"/>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <label for="startDate" class="control-label" >
                            Reported Date (From) :
                        </label>
                    </td>
                    <td>
                        <form:input path="txnReportTransactionVO.startDate" cssClass="form-control" id="txnReportStartDate"/>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <label for="endDate"  class="control-label">
                            Reported Date (To) :
                        </label>
                    </td>
                    <td>
                        <form:input path="txnReportTransactionVO.endDate" cssClass="form-control" id="txnReportEndDate"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="14">&nbsp;</td>
                </tr>
                <tr>
                    <td>
                        <label for="SerialNo"  class="control-label">
                            Serial No :
                        </label>
                    </td>
                    <td>
                        <form:input path="txnReportTransactionVO.SerialNo" cssClass="form-control" id="txnReportSerialNo"/>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <label for="makeId" class="control-label">
                            Make :
                        </label>
                    </td>
                    <td>
                        <form:select id="txnReportMakeId" path="txnReportTransactionVO.makeId" cssClass="form-control" tabindex="1"
                                     onchange="javascript:changeTheModel();">
                            <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                            <form:options items="${reportsForm.makeVOs}"
                                          itemValue="id" itemLabel="makeName" />
                        </form:select>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <label for="modelId"  class="control-label">
                            Model Name :
                        </label>
                    </td>
                    <td>
                        <form:select id="txnReportModelId" path="txnReportTransactionVO.modelId" cssClass="form-control" tabindex="1">
                            <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                        </form:select>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <label for="Status"  class="control-label">
                            Status :
                        </label>
                    </td>
                    <td>
                        <form:select id="txnReportStatus" path="txnReportTransactionVO.Status" cssClass="form-control">
                            <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                            <form:options items="${reportsForm.statusList}" />
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td colspan="14">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="12">&nbsp;</td>
                    <td>
                        <label for="includes" class="control-label" >
                            <spring:message code="user.includes" text="Includes"/>
                            <form:checkbox path="txnReportTransactionVO.includes" cssStyle="vertical-align:middle" id="txnReportIncludes" value="" />
                        </label>
                    </td>
                    <td>
                        <label for="startswith" class="control-label">
                            <spring:message code="user.startsWith" text="Starts with"/>
                            <form:checkbox path="txnReportTransactionVO.startswith" cssStyle="vertical-align:middle" id="txnReportStartswith" value="" />
                        </label>
                    </td>
                </tr>
                <tr>
                    <td colspan="8">&nbsp;</td>
                    <td>
                        <label for="exportTo" class="control-label">
                            Export To :
                        </label>
                    </td>
                    <td>
                        <select id="txnExportValue"  class="form-control">
                            <option value=""></option>
                            <c:forEach var="n" items="${reportsForm.exportList}" varStatus="rowCounter">
                                <option value="${n}">${n}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <input class="btn btn-primary" value="Fetch Transactions List Report" type="button"
                               onclick="javascript:fetchTransactionsListReport()"/>
                    </td>
                    <td>
                        <input class="btn btn-primary" value="Clear" type="button" onclick="javascript:clearOut()"/>
                    </td>
                </tr>
            </table>
        </div>
        <div class="tab-pane" id="invoiceReport">
            <br />
            <table>
                <tr>
                    <td>
                        <label for="tagNo" class="control-label">
                            Tag To :
                        </label>
                    </td>
                    <td>
                        <input type="text" id="invoiceTagNo" class="form-control"/>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <label for="exportTo" class="control-label" >
                            Export To :
                        </label>
                    </td>
                    <td>
                        <select id="invoiceExportValue" class="form-control" >
                            <option value=""></option>
                            <c:forEach var="n" items="${reportsForm.exportList}" varStatus="rowCounter">
                                <option value="${n}">${n}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <input class="btn btn-primary" value="Fetch Invoice Report" type="button"
                               onclick="javascript:fetchInvoiceReport()"/>
                    </td>
                    <td>
                        <input class="btn btn-primary" value="Clear" type="button" onclick="javascript:clearOut()"/>
                    </td>
                </tr>
            </table>
        </div>
        <div class="tab-pane" id="invoiceListReport">
            <br />
            <table>
                <tr>
                    <td>
                        <label for="TagNo" class="control-label">
                            Tag No :
                        </label>
                    </td>
                    <td>
                        <form:input path="invoiceListReportTransactionVO.TagNo"  class="form-control" id="invoiceListTagNo"/>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <label for="CustomerName" class="control-label">
                            Customer Name :
                        </label>
                    </td>
                    <td>
                        <form:input path="invoiceListReportTransactionVO.CustomerName" cssClass="form-control" id="invoiceListCustomerName"/>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <label for="startDate" class="control-label">
                            Reported Date (From) :
                        </label>
                    </td>
                    <td>
                        <form:input path="invoiceListReportTransactionVO.startDate" cssClass="form-control" id="invoiceListStartDate"/>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <label for="endDate" class="control-label">
                            Reported Date (To) :
                        </label>
                    </td>
                    <td>
                        <form:input path="invoiceListReportTransactionVO.endDate" cssClass="form-control" id="invoiceListEndDate"/>
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
                        <form:input path="invoiceListReportTransactionVO.SerialNo" cssClass="form-control" id="invoiceListSerialNo"/>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <label for="makeId" class="control-label">
                            Make :
                        </label>
                    </td>
                    <td>
                        <form:select id="invoiceListMakeId" path="invoiceListReportTransactionVO.makeId" tabindex="1"  cssClass="form-control"
                                     onchange="javascript:changeTheTxnModel();">
                            <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                            <form:options items="${reportsForm.makeVOs}"
                                          itemValue="Id" itemLabel="makeName"/>
                        </form:select>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <label for="modelId" class="control-label">
                            Model Name :
                        </label>
                    </td>
                    <td>
                        <form:select id="invoiceListModelId" path="invoiceListReportTransactionVO.modelId" tabindex="1" cssClass="form-control">
                            <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                        </form:select>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <label for="Status" class="control-label">
                            Status :
                        </label>
                    </td>
                    <td>
                        <form:select id="invoiceListStatus" path="invoiceListReportTransactionVO.Status" cssClass="form-control">
                            <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                            <form:options items="${reportsForm.statusList}" />
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
                            <form:checkbox path="invoiceListReportTransactionVO.includes" cssStyle="vertical-align:middle" id="invoiceListIncludes" value="" />
                        </label>
                    </td>
                    <td>
                        <label for="startswith" class="control-label">
                            <spring:message code="user.startsWith" text="Starts with"/>
                            <form:checkbox path="invoiceListReportTransactionVO.startswith" cssStyle="vertical-align:middle" id="invoiceListStartswith" value="" />
                        </label>
                    </td>
                </tr>
                <tr>
                    <td colspan="8">&nbsp;</td>
                    <td>
                        <label for="exportTo" class="control-label">
                            Export To :
                        </label>
                    </td>
                    <td>
                        <select id="txnExportValue" class="form-control">
                            <option value=""></option>
                            <c:forEach var="n" items="${reportsForm.exportList}" varStatus="rowCounter">
                                <option value="${n}">${n}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <input class="btn btn-primary" value="Fetch Invoice List Report" type="button"
                               onclick="javascript:fetchTransactionsListReport()"/>
                    </td>
                    <td>
                        <input class="btn btn-primary" value="Clear" type="button" onclick="javascript:clearOut()"/>
                    </td>
                </tr>
            </table>
        </div>
        </div>
        </div> <!-- /tabbable -->
        <br />
        <br />
        <div class="wrap">
            <div class="panel panel-primary">
                <div class="panel-heading">Report :</div>
                <iframe name="reportContent" id="reportContent" width="100%" height="420px" frameborder="0">
                </iframe>
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
                    $( "#tabs" ).tabs();
                    $( "#startDate" ).datepicker({ dateFormat: "dd/mm/yy" });
                    $( "#endDate" ).datepicker({ dateFormat: "dd/mm/yy" });
                });
            });
        </script>
    </form:form>
</body>
</html>