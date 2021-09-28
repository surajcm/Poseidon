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
    <spring:url value="/img/Poseidon_Ico.ico" var="posIcon" />
    <link rel="shortcut icon" href="${posIcon}" />
    <link rel="stylesheet" href="/css/jquery-ui.css" type="text/css" />
    <link rel="stylesheet" href="/css/bootstrap-5.min.css"  type="text/css" />
    <link rel="stylesheet" href="/css/custom.css" type="text/css" />
    <title>Reports List</title>
    <script type="text/javascript" src="/js/report-scripts.js"></script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body onload="selectMenu()">
    <form:form method="POST" modelAttribute="reportsForm" >
        <form:hidden name="loggedInUser" path="loggedInUser"/>
        <form:hidden name="loggedInRole" path="loggedInRole"/>
        <form:hidden name="exportTo" path="currentReport.exportTo" id="exportTo"/>
        <form:hidden name="tagNo" path="currentReport.tagNo" id="tagNo"/>
        <%@include file="../navbar5.jsp" %>
        <div class="container">
            <ul class="nav nav-tabs" role="tablist">
                <li class="nav-item" role="presentation">
                    <button class="nav-link active" id="home-tab" data-bs-toggle="tab" data-bs-target="#callReport" type="button" role="tab" aria-controls="callReport" aria-selected="true">Generate Call Report :</button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="model-tab" data-bs-toggle="tab" data-bs-target="#modelReport" type="button" role="tab" aria-controls="modelReport" aria-selected="false">Generate Make/Model Report :</button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="txn-tab" data-bs-toggle="tab" data-bs-target="#txnReport" type="button" role="tab" aria-controls="txnReport" aria-selected="false">Generate TransactionsList Report :</button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="invoice-tab" data-bs-toggle="tab" data-bs-target="#invoiceReport" type="button" role="tab" aria-controls="invoiceReport" aria-selected="false">Generate Invoice Report :</button>
                </li>
                <li class="nav-item" role="presentation">
                 <button class="nav-link" id="invoice-list-tab" data-bs-toggle="tab" data-bs-target="#invoiceListReport" type="button" role="tab" aria-controls="invoiceListReport" aria-selected="false">Generate InvoiceList Report :</button>
                </li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane fade show active" id="callReport" role="tabpanel" aria-labelledby="home-tab">
                    <br />
                    <table>
                        <tr>
                            <td>
                                <label for="callTagNo" class="control-label">
                                    Tag To :
                                </label>
                            </td>
                            <td>
                                <input type="text" id="callTagNo" class="form-control" />
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="callExportValue" class="control-label">
                                    Export To :
                                </label>
                            </td>
                            <td>
                                <select class="form-select" id="callExportValue" >
                                    <option value=""></option>
                                    <c:forEach var="n" items="${reportsForm.exportList}" varStatus="rowCounter">
                                        <option value="${n}">${n}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <input class="btn btn-primary" value="Fetch Call Report" type="button"
                                       onclick="fetchCallReport()"/>
                            </td>
                            <td>
                                <input class="btn btn-primary" value="Clear" type="button" onclick="clearOut()"/>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="tab-pane fade" id="modelReport" role="tabpanel" aria-labelledby="model-tab">
                    <br />
                    <table>
                        <tr>
                            <td>
                                <label class="control-label">
                                    Make Name :
                                </label>
                            </td>
                            <td>
                                <form:select id="modelReportMakeName" cssClass="form-select" path="modelReportMakeAndModelVO.makeId">
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
                                <label for="makeExportValue" class="control-label" >
                                    Export To :
                                </label>
                            </td>
                            <td>
                                <select id="makeExportValue" class="form-select" >
                                    <option value=""></option>
                                    <c:forEach var="n" items="${reportsForm.exportList}" varStatus="rowCounter">
                                        <option value="${n}">${n}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td colspan="2">
                                <input class="btn btn-primary" value="Fetch Make Report" type="button"
                                       onclick="fetchMakeReport()"/>
                            </td>
                            <td>
                                <input class="btn btn-primary" value="Fetch Model List Report" type="button"
                                       onclick="fetchModelListReport()"/>
                            </td>
                            <td>
                                <input class="btn btn-primary" value="Clear" type="button" onclick="clearOut()"/>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="tab-pane fade" id="txnReport" role="tabpanel" aria-labelledby="txn-tab">
                    <br />
                    <table>
                        <tr>
                            <td>
                                <label for="txnReportTagNo"  class="control-label" >
                                    Tag No :
                                </label>
                            </td>
                            <td>
                                <form:input path="txnReportTransactionVO.TagNo" cssClass="form-control" id="txnReportTagNo"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="txnReportCustomerName"  class="control-label" >
                                    Customer Name :
                                </label>
                            </td>
                            <td>
                                <form:input path="txnReportTransactionVO.CustomerName" cssClass="form-control" id="txnReportCustomerName"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="txnReportStartDate" class="control-label" >
                                    Reported Date (From) :
                                </label>
                            </td>
                            <td>
                                <form:input path="txnReportTransactionVO.startDate" cssClass="form-control" id="txnReportStartDate"/>
                                <label for="startDate" class="input-group-addon btn">
                                    <img src="/img/calendar3.svg" alt="" width="16" height="16" title="calendar" />
                                </label>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="txnReportEndDate"  class="control-label">
                                    Reported Date (To) :
                                </label>
                            </td>
                            <td>
                                <form:input path="txnReportTransactionVO.endDate" cssClass="form-control" id="txnReportEndDate"/>
                                <label for="endDate" class="input-group-addon btn">
                                    <img src="/img/calendar3.svg" alt="" width="16" height="16" title="calendar" />
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="14">&nbsp;</td>
                        </tr>
                        <tr>
                            <td>
                                <label for="txnReportSerialNo"  class="control-label">
                                    Serial No :
                                </label>
                            </td>
                            <td>
                                <form:input path="txnReportTransactionVO.SerialNo" cssClass="form-control" id="txnReportSerialNo"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="txnReportMakeId" class="control-label">
                                    Make :
                                </label>
                            </td>
                            <td>
                                <form:select id="txnReportMakeId" path="txnReportTransactionVO.makeId" cssClass="form-select" tabindex="1"
                                             onchange="javascript:changeTheModel();">
                                    <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                    <form:options items="${reportsForm.makeVOs}"
                                                  itemValue="id" itemLabel="makeName" />
                                </form:select>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="txnReportModelId"  class="control-label">
                                    Model Name :
                                </label>
                            </td>
                            <td>
                                <form:select id="txnReportModelId" path="txnReportTransactionVO.modelId" cssClass="form-select" tabindex="1">
                                    <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                </form:select>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="txnReportStatus"  class="control-label">
                                    Status :
                                </label>
                            </td>
                            <td>
                                <form:select id="txnReportStatus" path="txnReportTransactionVO.Status" cssClass="form-select">
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
                                <label for="txnExportValue1" class="control-label">
                                    Export To :
                                </label>
                            </td>
                            <td>
                                <select id="txnExportValue1"  class="form-select">
                                    <option value=""></option>
                                    <c:forEach var="n" items="${reportsForm.exportList}" varStatus="rowCounter">
                                        <option value="${n}">${n}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <input class="btn btn-primary" value="Fetch Transactions List Report" type="button"
                                       onclick="fetchTransactionsListReport()"/>
                            </td>
                            <td>
                                <input class="btn btn-primary" value="Clear" type="button" onclick="clearOut()"/>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="tab-pane fade" id="invoiceReport" role="tabpanel" aria-labelledby="invoice-tab">
                    <br />
                    <table>
                        <tr>
                            <td>
                                <label for="invoiceTagNo" class="control-label">
                                    Tag To :
                                </label>
                            </td>
                            <td>
                                <input type="text" id="invoiceTagNo" class="form-control"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="invoiceExportValue" class="control-label" >
                                    Export To :
                                </label>
                            </td>
                            <td>
                                <select id="invoiceExportValue" class="form-select" >
                                    <option value=""></option>
                                    <c:forEach var="n" items="${reportsForm.exportList}" varStatus="rowCounter">
                                        <option value="${n}">${n}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <input class="btn btn-primary" value="Fetch Invoice Report" type="button"
                                       onclick="fetchInvoiceReport()"/>
                            </td>
                            <td>
                                <input class="btn btn-primary" value="Clear" type="button" onclick="clearOut()"/>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="tab-pane fade" id="invoiceListReport" role="tabpanel" aria-labelledby="invoice-list-tab">
                    <br />
                    <table>
                        <tr>
                            <td>
                                <label for="invoiceListTagNo" class="control-label">
                                    Tag No :
                                </label>
                            </td>
                            <td>
                                <form:input path="invoiceListReportTransactionVO.TagNo"  class="form-control" id="invoiceListTagNo"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="invoiceListCustomerName" class="control-label">
                                    Customer Name :
                                </label>
                            </td>
                            <td>
                                <form:input path="invoiceListReportTransactionVO.CustomerName" cssClass="form-control" id="invoiceListCustomerName"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="invoiceListStartDate" class="control-label">
                                    Reported Date (From) :
                                </label>
                            </td>
                            <td>
                                <form:input path="invoiceListReportTransactionVO.startDate" cssClass="form-control" id="invoiceListStartDate"/>
                                <label for="startDate" class="input-group-addon btn">
                                    <img src="/img/calendar3.svg" alt="" width="16" height="16" title="calendar" />
                                </label>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="invoiceListEndDate" class="control-label">
                                    Reported Date (To) :
                                </label>
                            </td>
                            <td>
                                <form:input path="invoiceListReportTransactionVO.endDate" cssClass="form-control" id="invoiceListEndDate"/>
                                <label for="endDate" class="input-group-addon btn">
                                    <img src="/img/calendar3.svg" alt="" width="16" height="16" title="calendar" />
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="14">&nbsp;</td>
                        </tr>
                        <tr>
                            <td>
                                <label for="invoiceListSerialNo" class="control-label">
                                    Serial No :
                                </label>
                            </td>
                            <td>
                                <form:input path="invoiceListReportTransactionVO.SerialNo" cssClass="form-control" id="invoiceListSerialNo"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="invoiceListMakeId" class="control-label">
                                    Make :
                                </label>
                            </td>
                            <td>
                                <form:select id="invoiceListMakeId" path="invoiceListReportTransactionVO.makeId" tabindex="1"  cssClass="form-select"
                                             onchange="javascript:changeTheTxnModel();">
                                    <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                    <form:options items="${reportsForm.makeVOs}"
                                                  itemValue="Id" itemLabel="makeName"/>
                                </form:select>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="invoiceListModelId" class="control-label">
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
                                <label for="invoiceListStatus" class="control-label">
                                    Status :
                                </label>
                            </td>
                            <td>
                                <form:select id="invoiceListStatus" path="invoiceListReportTransactionVO.Status" cssClass="form-select">
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
                                <label for="invoiceListIncludes" class="control-label">
                                    <spring:message code="user.includes" text="Includes"/>
                                    <form:checkbox path="invoiceListReportTransactionVO.includes" cssStyle="vertical-align:middle" id="invoiceListIncludes" value="" />
                                </label>
                            </td>
                            <td>
                                <label for="invoiceListStartswith" class="control-label">
                                    <spring:message code="user.startsWith" text="Starts with"/>
                                    <form:checkbox path="invoiceListReportTransactionVO.startswith" cssStyle="vertical-align:middle" id="invoiceListStartswith" value="" />
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="8">&nbsp;</td>
                            <td>
                                <label for="txnExportValue" class="control-label">
                                    Export To :
                                </label>
                            </td>
                            <td>
                                <select id="txnExportValue" class="form-select">
                                    <option value=""></option>
                                    <c:forEach var="n" items="${reportsForm.exportList}" varStatus="rowCounter">
                                        <option value="${n}">${n}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <input class="btn btn-primary" value="Fetch Invoice List Report" type="button"
                                       onclick="fetchTransactionsListReport()"/>
                            </td>
                            <td>
                                <input class="btn btn-primary" value="Clear" type="button" onclick="clearOut()"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <br />
            <br />
            <div class="wrap">
                <div class="card">
                    <div class="card-header">Report :</div>
                    <iframe name="reportContent" id="reportContent" width="100%" height="420px">
                    </iframe>
                </div>
            </div>
        </div>
        <script src="/js/core/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="/js/core/popper.min.js" type="text/javascript"></script>
        <script src="/js/core/bootstrap-5.min.js" type="text/javascript"></script>
        <script src="/js/core/jquery-ui.min.js" type="text/javascript"></script>
        <script type="text/javascript">
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