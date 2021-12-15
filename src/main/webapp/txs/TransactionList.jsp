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
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta name="description" content="">
    <meta name="author" content="Suraj">
    <spring:url value="/img/Poseidon_Ico.ico" var="posIcon" />
    <link rel="shortcut icon" href="${posIcon}" />
    <link rel="stylesheet" href="/css/jquery-ui.css" type="text/css" />
    <link rel="stylesheet" href="/css/core/bootstrap-5.min.css"  type="text/css" />
    <link rel="stylesheet" href="/css/custom.css" type="text/css" />
    <title>Transaction List</title>
    <script type="text/javascript" src="/js/common-scripts.js"></script>
    <script type="text/javascript" src="/js/transaction-scripts.js"></script>
    <script type="text/javascript" src="/js/transaction-and-invoice.js"></script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body onload="hideAlerts()">
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
                        <div class="row g-3">
                            <div class="col-md-3">
                                <form:input cssClass="form-control" path="searchTransaction.TagNo" id="TagNo" placeholder="Tag Number" />
                            </div>
                            <div class="col-md-3">
                              <form:input cssClass="form-control" path="searchTransaction.CustomerName" id="CustomerName" placeholder="Customer Name" />
                            </div>
                            <div class="col-md-3">
                                <div class="input-group mb-3">
                                    <span class="input-group-text">Reported Date (From)</span>
                                    <form:input path="searchTransaction.startDate" cssClass="date-picker form-control" id="startDate" />
                                    <span class="input-group-text"><img src="/img/calendar3.svg" alt="" width="16" height="16" title="calendar" /></span>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="input-group mb-3">
                                    <span class="input-group-text">Reported Date (To)</span>
                                    <form:input path="searchTransaction.endDate" cssClass="date-picker form-control" id="endDate" />
                                    <span class="input-group-text"><img src="/img/calendar3.svg" alt="" width="16" height="16" title="calendar" /></span>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <form:input cssClass="form-control" path="searchTransaction.SerialNo" id="SerialNo" placeholder="Serial Number"/>
                            </div>
                            <div class="col-md-3">
                                <div class="input-group mb-3">
                                    <label for="makeId" class="input-group-text">Make</label>
                                    <form:select id="makeId" cssClass="form-select" path="searchTransaction.makeId" tabindex="1" onchange="changeTheModel();">
                                        <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                        <form:options items="${transactionForm.makeVOs}" itemValue="Id" itemLabel="makeName"/>
                                    </form:select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="input-group mb-3">
                                    <label for="modelId" class="input-group-text">Model</label>
                                    <form:select id="modelId" cssClass="form-select" path="searchTransaction.modelId" tabindex="2" disabled="true">
                                        <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                        <form:options items="${transactionForm.makeAndModelVOs}"
                                                  itemValue="modelId" itemLabel="modelName"/>
                                    </form:select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="input-group mb-3">
                                    <label for="Status" class="input-group-text">Status</label>
                                    <form:select id="Status" cssClass="form-select" path="searchTransaction.Status">
                                        <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                        <form:options items="${transactionForm.statusList}" />
                                    </form:select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-check">
                                    <form:checkbox path="searchTransaction.includes" cssClass="form-check-input" id="includes" value="" />
                                        <label class="form-check-label" for="includes">
                                        <spring:message code="user.includes" text="Includes" />
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-check">
                                    <form:checkbox path="searchTransaction.startswith" cssClass="form-check-input" id="startswith" value="" />
                                        <label class="form-check-label" for="startsWith">
                                        <spring:message code="user.startsWith" text="Starts with" />
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <button type="button" class="btn btn-primary" onclick="search()"><spring:message code='poseidon.search' text='Search' /></button>
                            </div>
                            <div class="col-md-6">
                                <button type="button" class="btn btn-primary" onclick="clearOut()"><spring:message code='poseidon.clear' text='Clear' /></button>
                            </div>
                        </div>
                    </div>
                </div>
                <br />
                <br />
                <c:if test="${transactionForm.statusMessage!=null}">
                    <div class="alert alert-<c:out value='${transactionForm.statusMessageType}'/> alert-dismissible fade show" role="alert">
                        <c:out value="${transactionForm.statusMessage}"/>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <table id='myTable' class="table table-bordered table-striped table-hover caption-top">
                <caption>Transaction List</caption>
                    <thead class="table-dark">
                        <tr>
                            <th scope="col"><spring:message code="poseidon.id" text="id"/></th>
                            <th scope="col">TagNo</th>
                            <th scope="col">Customer Name</th>
                            <th scope="col">Reported Date</th>
                            <th scope="col">Make</th>
                            <th scope="col">Model</th>
                            <th scope="col">Serial No</th>
                            <th scope="col">Status</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${transactionForm.transactionsList}" var="iterationTxn">
                        <tr>
                            <th scope="row"><input type="checkbox" name="checkField" onclick="checkCall(this)"
                                       value="<c:out value='${iterationTxn.id}' />"/></th>
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
                <div class="row g-3">
                    <div class="col-md-3">
                        <button type="button" class="btn btn-primary" onclick="addNew()">Add New Transaction</button>
                    </div>
                    <div class="col-md-3">
                        <button type="button" class="btn btn-primary" onclick="editMe()">Edit Transaction</button>
                    </div>
                    <div class="col-md-3">
                        <button type="button" class="btn btn-primary" onclick="deleteTxn()">Delete Transaction</button>
                    </div>
                    <div class="col-md-3">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#newInvoiceModal"
                            onclick="addSmartInvoiceOnTransaction();">Invoice Transaction</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="newInvoiceModal" class="modal fade" tabindex="-1" aria-labelledby="newInvoiceModal" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Invoice transaction</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div id="invoiceModalBody" class="modal-body">
                        <p>Invoice the transaction....</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="saveModal" class="btn btn-primary" onclick="saveFromModal();">Save</button>
                    </div>
                </div>
            </div>
        </div>
        <script src="/js/core/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="/js/core/popper.min.js" type="text/javascript"></script>
        <script src="/js/core/bootstrap-5.min.js" type="text/javascript"></script>
        <script src="/js/core/jquery-ui.min.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $(function() {
                    $("#startDate").datepicker();
                    $("#endDate").datepicker();
                });
                $('#makeId').change(function() {
                    $("#modelId").prop("disabled", false);
                });
            });
        </script>
    </form:form>
</body>
</html>
