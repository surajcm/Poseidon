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
        <title>Add Transaction</title>
        <script type="text/javascript" src="/js/txn-add-scripts.js"></script>
        <script type="text/javascript" src="/js/navbar-scripts.js"></script>
    </head>
    <body>
        <form:form method="POST" modelAttribute="transactionForm" >
            <form:hidden name="loggedInUser" path="loggedInUser"/>
            <form:hidden name="loggedInRole" path="loggedInRole"/>
            <%@include file="../navbar.jsp" %>
            <div class="container">
                <div class="wrap">
                    <div class="card">
                        <div class="card-header">
                            Add Transaction
                        </div>
                        <div class="card-body">
                            <div class="row g-3">
                                <div class="col-md-4">
                                    <label for="productCategory" class="form-label">Product Category : *</label>
                                    <form:input cssClass="form-control" path="currentTransaction.productCategory" id="productCategory"/>
                                </div>
                                <div class="col-md-4">
                                    <label for="serialNo" class="form-label">Serial No :*</label>
                                    <form:input cssClass="form-control" path="currentTransaction.serialNo" id="serialNo"/>
                                </div>
                                <div class="col-md-4">
                                    <label for="dateReported" class="form-label">Transaction Date :* <img src="/img/calendar3.svg" alt="" width="16" height="16" title="calendar" /></label>
                                    <form:input path="currentTransaction.dateReported" cssClass="date-picker form-control" id="dateReported" />
                                </div>
                                <div class="col-md-4">
                                    <label for="customerId" class="form-label">Customer Id :*</label>
                                    <form:input cssClass="form-control" path="customerVO.customerId" id="customerId"/>
                                </div>
                                <div class="col-md-8">
                                    <label for="customerId" class="form-label" style="font-size: .70em;color:blue;">Enter Customer Details in case of New Customer</label>
                                </div>
                                <div class="col-md-4">
                                    <label for="customerName" class="form-label">Customer Name :*</label>
                                    <form:input cssClass="form-control" path="customerVO.customerName" id="customerName"/>
                                </div>
                                <div class="col-md-8">
                                    <label for="address1" class="form-label">Address Line :</label>
                                    <form:textarea cssClass="form-control" path="customerVO.address" id="address1"/>
                                </div>
                                <div class="col-md-4">
                                    <label for="phoneNo" class="form-label">Phone :</label>
                                    <form:input cssClass="form-control" path="customerVO.phoneNo" id="phoneNo"/>
                                </div>
                                <div class="col-md-4">
                                    <label for="mobile" class="form-label">Mobile :</label>
                                    <form:input cssClass="form-control" path="customerVO.mobile" id="mobile"/>
                                </div>
                                <div class="col-md-4">
                                    <label for="email" class="form-label">Email :</label>
                                    <form:input cssClass="form-control" path="customerVO.email" id="email"/>
                                </div>
                                <div class="col-md-4">
                                    <label for="makeId" class="form-label">Make :* </label>
                                    <form:select id="makeId" path="currentTransaction.makeId" tabindex="1" cssClass="form-control"
                                                 onkeypress="handleEnter(event);" onchange="changeTheModel();">
                                        <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                        <form:options items="${transactionForm.makeVOs}"
                                                      itemValue="Id" itemLabel="makeName"/>
                                    </form:select>
                                </div>
                                <div class="col-md-4">
                                    <label for="modelId" class="form-label">Model :* </label>
                                        <form:select id="modelId" path="currentTransaction.modelId" tabindex="2" cssClass="form-control"
                                                     onkeypress="handleEnter(event);" disabled="true">
                                            <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                            <form:options items="${transactionForm.makeAndModelVOs}"
                                                          itemValue="modelId" itemLabel="modelName"/>
                                        </form:select>
                                </div>
                                <div class="col-md-4">
                                    <a id="gotoNewModel" href="${contextPath}/make/addModel.htm"> Add New Model</a>
                                </div>
                                <div class="col-md-4">
                                    <label for="accessories" class="form-label">Accessories :*</label>
                                    <form:textarea cssClass="form-control" path="currentTransaction.accessories" id="accessories"/>
                                </div>
                                <div class="col-md-4">
                                    <label for="complaintReported" class="form-label">Complaint Reported :*</label>
                                    <form:textarea cssClass="form-control" path="currentTransaction.complaintReported" id="complaintReported"/>
                                </div>
                                <div class="col-md-4">
                                    <label for="complaintDiagnosed" class="form-label">Complaint Diagnosed :*</label>
                                    <form:textarea cssClass="form-control" path="currentTransaction.complaintDiagnosed" id="complaintDiagnosed"/>
                                </div>
                                <div class="col-md-4">
                                    <label for="enggRemark" class="form-label">Engineer Remarks :*</label>
                                    <form:textarea cssClass="form-control" path="currentTransaction.enggRemark" id="enggRemark"/>
                                </div>
                                <div class="col-md-4">
                                    <label for="repairAction" class="form-label">Repair Action :*</label>
                                    <form:textarea cssClass="form-control" path="currentTransaction.repairAction" id="repairAction"/>
                                </div>
                                <div class="col-md-4">
                                    <label for="notes" class="form-label">Notes :*</label>
                                    <form:textarea cssClass="form-control" path="currentTransaction.notes" id="notes"/>
                                </div>
                                <div class="col-md-6">
                                    <button type="button" class="btn btn-primary btn-success" onclick="save();">
                                        Save
                                    </button>
                                </div>
                                <div class="col-md-6">
                                    <button type="button" class="btn btn-primary" onclick="clearOut();">Clear</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <script src="/js/core/jquery-3.2.1.min.js" type="text/javascript"></script>
            <script src="/js/core/popper.min.js" type="text/javascript"></script>
            <script src="/js/core/bootstrap-5.min.js" type="text/javascript"></script>
            <script src="/js/core/jquery-ui.min.js" type="text/javascript"></script>
            <script type="text/javascript">
                $(document).ready(function()
                {
                    $(function() {
                        $("#dateReported").datepicker();
                    });
                    $('#makeId').change(function() {
                        $("#modelId").prop("disabled", false);
                    });
                });
            </script>
        </form:form>
    </body>
</html>
