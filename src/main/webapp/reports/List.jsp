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
                    <div class="card">
                        <div class="card-body">
                            <div class="row g-3">
                                <div class="col-md-3">
                                    <input type="text" id="callTagNo" class="form-control" placeholder="Tag Number" aria-label="callTagNo" aria-describedby="basic-addon1">
                                </div>
                                <div class="col-md-3">
                                    <div class="input-group mb-3">
                                        <label class="input-group-text" for="callExportValue">Export To</label>
                                        <select class="form-select" id="callExportValue" >
                                            <option selected>Choose...</option>
                                            <c:forEach var="n" items="${reportsForm.exportList}" varStatus="rowCounter">
                                                <option value="${n}">${n}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <button type="button" class="btn btn-primary" onclick="fetchCallReport()" >Fetch Call Report</button>
                                </div>
                                <div class="col-md-3">
                                    <button type="button" class="btn btn-primary" onclick="clearOut()" >Clear</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" id="modelReport" role="tabpanel" aria-labelledby="model-tab">
                    <div class="card">
                        <div class="card-body">
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <div class="input-group mb-3">
                                        <label class="input-group-text" for="modelReportMakeName">Make Name</label>
                                        <select class="form-select" id ="modelReportMakeName" name="modelReportMakeAndModelVO.makeId" >
                                            <option value="0" selected>Choose...</option>
                                            <c:forEach var="n" items="${reportsForm.makeVOs}">
                                                <option value="${n.id}">${n.makeName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <input type="text" id="modelReportModelName" name="modelReportMakeAndModelVO.modelName" class="form-control" placeholder="Model Name" aria-label="callTagNo" aria-describedby="basic-addon1">
                                </div>
                                <div class="col-md-6">
                                    <div class="form-check">
                                        <form:checkbox path="modelReportMakeAndModelVO.includes" cssClass="form-check-input" id="includes" value="" />
                                        <label class="form-check-label" for="includes">
                                            <spring:message code="user.includes" text="Includes" />
                                        </label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-check">
                                        <form:checkbox path="modelReportMakeAndModelVO.startswith" cssClass="form-check-input" id="startswith" value="" />
                                        <label class="form-check-label" for="startsWith">
                                        <spring:message code="user.startsWith" text="Starts with" />
                                        </label>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="input-group mb-3">
                                        <label class="input-group-text" for="makeExportValue">Export To</label>
                                        <select class="form-select" id="makeExportValue" >
                                            <option value="0" selected>Choose...</option>
                                            <c:forEach var="n" items="${reportsForm.exportList}" varStatus="rowCounter">
                                                <option value="${n}">${n}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <button type="button" class="btn btn-primary" onclick="fetchMakeReport()" >Fetch Make Report</button>
                                </div>
                                <div class="col-md-3">
                                    <button type="button" class="btn btn-primary" onclick="fetchModelListReport()" >Fetch Model List Report</button>
                                </div>
                                <div class="col-md-3">
                                    <button type="button" class="btn btn-primary" onclick="clearOut()" >Clear</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" id="txnReport" role="tabpanel" aria-labelledby="txn-tab">
                    <div class="card">
                        <div class="card-body">
                            <div class="row g-3">
                                <div class="col-md-3">
                                    <input type="text" id="txnReportTagNo" name="txnReportTransactionVO.TagNo" class="form-control" placeholder="Tag Number" aria-label="txnReportTagNo" aria-describedby="basic-addon1">
                                </div>
                                <div class="col-md-3">
                                    <input type="text" id="txnReportCustomerName" name="txnReportTransactionVO.CustomerName" class="form-control" placeholder="Customer Name" aria-label="txnReportCustomerName" aria-describedby="basic-addon1">
                                </div>
                                <div class="col-md-3">
                                    <div class="input-group mb-3">
                                        <span class="input-group-text">Reported Date (From)</span>
                                        <input type="date" id="txnReportStartDate" class="form-control datepicker" name="txnReportTransactionVO.startDate" aria-label="Reported Date (From)">
                                        <span class="input-group-text"><img src="/img/calendar3.svg" alt="" width="16" height="16" title="calendar" /></span>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="input-group mb-3">
                                        <span class="input-group-text">Reported Date (To)</span>
                                        <input type="date" id="txnReportEndDate" class="form-control datepicker" name="txnReportTransactionVO.endDate" aria-label="Reported Date (To)">
                                        <span class="input-group-text"><img src="/img/calendar3.svg" alt="" width="16" height="16" title="calendar" /></span>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <input type="text" id="txnReportSerialNo" name="txnReportTransactionVO.SerialNo" class="form-control" placeholder="Serial Number" aria-label="txnReportSerialNo" aria-describedby="basic-addon1">
                                </div>
                                <div class="col-md-3">
                                    <div class="input-group mb-3">
                                        <label class="input-group-text" for="txnReportMakeId">Make</label>
                                        <select class="form-select" id ="txnReportMakeId" name="txnReportTransactionVO.makeId" onchange="changeTheModel();" >
                                        <option value="" selected>Choose...</option>
                                        <c:forEach var="n" items="${reportsForm.makeVOs}">
                                            <option value="${n.id}">${n.makeName}</option>
                                        </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="input-group mb-3">
                                        <label class="input-group-text" for="txnReportModelId">Model</label>
                                        <select class="form-select" id ="txnReportModelId" name="txnReportTransactionVO.modelId" >
                                            <option value="" selected>Choose...</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="input-group mb-3">
                                        <label class="input-group-text" for="txnReportStatus">Status</label>
                                        <select class="form-select" id ="txnReportStatus" name="txnReportTransactionVO.Status" >
                                        <option value="" selected>Choose...</option>
                                        <c:forEach var="n" items="${reportsForm.statusList}">
                                            <option value="${n}">${n}</option>
                                        </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <label for="includes" class="control-label" >
                                    <spring:message code="user.includes" text="Includes"/>
                                    <form:checkbox path="txnReportTransactionVO.includes" cssStyle="vertical-align:middle" id="txnReportIncludes" value="" />
                                    </label>
                                </div>
                                <div class="col-md-6">
                                    <label for="startswith" class="control-label">
                                    <spring:message code="user.startsWith" text="Starts with"/>
                                    <form:checkbox path="txnReportTransactionVO.startswith" cssStyle="vertical-align:middle" id="txnReportStartswith" value="" />
                                    </label>
                                </div>
                                <div class="col-md-4">
                                    <div class="input-group mb-3">
                                        <label class="input-group-text" for="txnExportValue1">Export To</label>
                                        <select class="form-select" id="txnExportValue1" >
                                        <option selected>Choose...</option>
                                        <c:forEach var="n" items="${reportsForm.exportList}" varStatus="rowCounter">
                                            <option value="${n}">${n}</option>
                                        </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <button type="button" class="btn btn-primary" onclick="fetchTransactionsListReport()" >Fetch Transactions List Report</button>
                                </div>
                                <div class="col-md-4">
                                    <button type="button" class="btn btn-primary" onclick="clearOut()" >Clear</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" id="invoiceReport" role="tabpanel" aria-labelledby="invoice-tab">
                    <div class="card">
                        <div class="card-body">
                            <div class="row g-3">
                                <div class="col-md-3">
                                    <input type="text" id="invoiceTagNo" class="form-control" placeholder="Tag Number" aria-label="invoiceTagNo" aria-describedby="basic-addon1">
                                </div>
                                <div class="col-md-3">
                                    <div class="input-group mb-3">
                                        <label class="input-group-text" for="invoiceExportValue">Export To</label>
                                        <select class="form-select" id="invoiceExportValue" >
                                            <option selected>Choose...</option>
                                            <c:forEach var="n" items="${reportsForm.exportList}" varStatus="rowCounter">
                                                <option value="${n}">${n}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <button type="button" class="btn btn-primary" onclick="fetchInvoiceReport()" >Fetch Invoice Report</button>
                                </div>
                                <div class="col-md-3">
                                    <button type="button" class="btn btn-primary" onclick="clearOut()" >Clear</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" id="invoiceListReport" role="tabpanel" aria-labelledby="invoice-list-tab">
                    <div class="card">
                        <div class="card-body">
                            <div class="row g-3">
                                <div class="col-md-3">
                                    <label for="invoiceListTagNo" class="control-label">
                                    Tag No :
                                    </label>
                                    <form:input path="invoiceListReportTransactionVO.TagNo"  class="form-control" id="invoiceListTagNo"/>
                                </div>
                                <div class="col-md-3">
                                    <label for="invoiceListCustomerName" class="control-label">
                                    Customer Name :
                                    </label>
                                    <form:input path="invoiceListReportTransactionVO.CustomerName" cssClass="form-control" id="invoiceListCustomerName"/>
                                </div>
                                <div class="col-md-3">
                                    <label for="invoiceListStartDate" class="control-label">
                                    Reported Date (From) :
                                    </label>
                                    <form:input path="invoiceListReportTransactionVO.startDate" cssClass="form-control" id="invoiceListStartDate"/>
                                    <label for="startDate" class="input-group-addon btn">
                                        <img src="/img/calendar3.svg" alt="" width="16" height="16" title="calendar" />
                                    </label>
                                </div>
                                <div class="col-md-3">
                                    <label for="invoiceListEndDate" class="control-label">
                                    Reported Date (To) :
                                    </label>
                                    <form:input path="invoiceListReportTransactionVO.endDate" cssClass="form-control" id="invoiceListEndDate"/>
                                    <label for="endDate" class="input-group-addon btn">
                                    <img src="/img/calendar3.svg" alt="" width="16" height="16" title="calendar" />
                                    </label>
                                </div>
                                <div class="col-md-3">
                                    <label for="invoiceListSerialNo" class="control-label">
                                    Serial No :
                                    </label>
                                    <form:input path="invoiceListReportTransactionVO.SerialNo" cssClass="form-control" id="invoiceListSerialNo"/>
                                </div>
                                <div class="col-md-3">
                                    <label for="invoiceListMakeId" class="control-label">
                                    Make :
                                    </label>
                                    <form:select id="invoiceListMakeId" path="invoiceListReportTransactionVO.makeId" tabindex="1"  cssClass="form-select"
                                                 onchange="javascript:changeTheTxnModel();">
                                        <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                        <form:options items="${reportsForm.makeVOs}"
                                                      itemValue="Id" itemLabel="makeName"/>
                                    </form:select>
                                </div>
                                <div class="col-md-3">
                                    <label for="invoiceListModelId" class="control-label">
                                    Model Name :
                                    </label>
                                    <form:select id="invoiceListModelId" path="invoiceListReportTransactionVO.modelId" tabindex="1" cssClass="form-control">
                                        <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                    </form:select>
                                </div>
                                <div class="col-md-3">
                                    <label for="invoiceListStatus" class="control-label">
                                    Status :
                                    </label>
                                    <form:select id="invoiceListStatus" path="invoiceListReportTransactionVO.Status" cssClass="form-select">
                                        <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                        <form:options items="${reportsForm.statusList}" />
                                    </form:select>
                                </div>
                                <div class="col-md-6">
                                    <label for="invoiceListIncludes" class="control-label">
                                    <spring:message code="user.includes" text="Includes"/>
                                    <form:checkbox path="invoiceListReportTransactionVO.includes" cssStyle="vertical-align:middle" id="invoiceListIncludes" value="" />
                                    </label>
                                </div>
                                <div class="col-md-6">
                                    <label for="invoiceListStartswith" class="control-label">
                                    <spring:message code="user.startsWith" text="Starts with"/>
                                    <form:checkbox path="invoiceListReportTransactionVO.startswith" cssStyle="vertical-align:middle" id="invoiceListStartswith" value="" />
                                    </label>
                                </div>
                                <div class="col-md-4">
                                    <label for="txnExportValue" class="control-label">
                                    Export To :
                                    </label>
                                    <select id="txnExportValue" class="form-select">
                                    <option value=""></option>
                                    <c:forEach var="n" items="${reportsForm.exportList}" varStatus="rowCounter">
                                        <option value="${n}">${n}</option>
                                    </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-4">
                                    <input class="btn btn-primary" value="Fetch Invoice List Report" type="button"
                                    onclick="fetchTransactionsListReport()"/>
                                </div>
                                <div class="col-md-4">
                                    <input class="btn btn-primary" value="Clear" type="button" onclick="clearOut()"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br />
            <br />
            <div class="wrap">
                <div class="card">
                    <div class="card-header">Report :</div>
                    <iframe name="reportContent" id="reportContent" width="100%" height="420px" title="Report">
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