<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

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
        <link rel="stylesheet" href="/css/bootstrap.min.css"  type="text/css" />
        <link rel="stylesheet" href="/css/custom.css" type="text/css" />
        <title>Edit Transaction</title>
        <script type="text/javascript" src="/js/txn-edit-scripts.js"></script>
        <script type="text/javascript" src="/js/navbar-scripts.js"></script>
    </head>
    <body>
        <form:form method="POST" modelAttribute="transactionForm" >
            <form:hidden name="loggedInUser" path="loggedInUser" />
            <form:hidden name="loggedInRole" path="loggedInRole" />
            <form:hidden name="id" path="currentTransaction.id" />
            <%@include file="../navbar.jsp" %>
            <div class="container">
            <div class="panel panel-primary">
                <div class="panel-heading">Edit Transaction</div>
                <table style="margin:auto;top:50%;left:50%;">
                    <tr>
                        <td style="text-align:right;">
                            <label for="productCategory" class="control-label">Product Category :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="currentTransaction.productCategory" id="productCategory"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="serialNo" class="control-label">Serial No :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="currentTransaction.serialNo" id="serialNo" cssClass="form-control"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="dateReported" class="control-label">Transaction Date :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <div class="input-group">
                                <form:input path="currentTransaction.dateReported" class="date-picker form-control" id = "dateReported" />
                                <label for="reported" class="input-group-addon btn">
                                    <img src="/img/calendar3.svg" alt="" width="16" height="16" title="calendar" />
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="10"> &nbsp;</td>
                    </tr>
                    
                    <tr>
                        <td style="text-align:right;">
                            <label for="customerName" class="control-label">
                                Customer Name :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.customerName" id="customerName" cssStyle="background: #A9A9A9 ;" cssClass="form-control" readonly="true"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        
                        
                        
                        <td style="text-align:right;">
                            <label for="email" class="control-label">
                                Email :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.email" cssStyle="background: #A9A9A9 ;" cssClass="form-control"
                                        id="email" readonly="true"/>
                        </td>
                        
                        <td colspan="2">&nbsp;</td>
                        
                        <td colspan="2">
                            <input class="btn btn-primary" value="Edit Customer Details" type="button" onclick="javascript:editThisCustomer();"/>
                        </td>
                        
                    </tr>
                    <tr>
                        <td colspan="10">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="makeId" class="control-label">Make :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:select id="makeId" path="currentTransaction.makeId" tabindex="1" cssClass="form-control"
                                         onkeypress="handleEnter(event);" onchange="changeTheModel();">
                                <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                <form:options items="${transactionForm.makeVOs}"
                                              itemValue="Id" itemLabel="makeName"/>
                            </form:select>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="modelId" class="control-label">Model :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:select id="modelId" path="currentTransaction.modelId" tabindex="2" cssClass="form-control"
                                         onkeypress="handleEnter(event);">
                                <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                <form:options items="${transactionForm.makeAndModelVOs}"
                                              itemValue="modelId" itemLabel="modelName"/>
                            </form:select>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="status" class="control-label">
                                Status :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:select id="status" path="currentTransaction.status" cssClass="form-control"
                                         onkeypress="handleEnter(event);">
                                <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                <form:options items="${transactionForm.statusList}" />
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="10">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="accessories" class="control-label">Accessories :</label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="currentTransaction.accessories" cssClass="form-control" id="accessories"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="complaintReported" class="control-label">Complaint Reported :</label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="currentTransaction.complaintReported" cssClass="form-control" id="complaintReported"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="complaintDiagonsed">Complaint Diagnosed :</label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="currentTransaction.complaintDiagonsed" cssClass="form-control" id="complaintDiagonsed"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="enggRemark" class="control-label">Engineer Remarks :</label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="currentTransaction.enggRemark" cssClass="form-control" id="enggRemark"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="repairAction" class="control-label">Repair Action :</label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="currentTransaction.repairAction" cssClass="form-control" id="repairAction"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="notes" class="control-label">Notes :</label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="currentTransaction.notes" cssClass="form-control" id="notes"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="10">
                            &nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td colspan="8">
                            &nbsp;
                        </td>
                        <td colspan="2">
                            <input class="btn btn-primary btn-success" value="Update" type="button" onclick="javascript:update();"/>
                            <input class="btn btn-primary" value="Cancel" type="button" onclick="javascript:cancel();"/>
                        </td>
                    </tr>
                </table>
            </div>
            <script src="/js/core/jquery-3.2.1.min.js"></script>
            <script src="/js/core/popper.min.js"></script>
            <script src="/js/core/bootstrap.min.js"></script>
            <script src="/js/core/jquery-ui.min.js"></script>
            <script>
                $(document).ready(function()
                {
                    //Handles menu drop down
                    $('.dropdown-menu').find('form').click(function (e) {
                        e.stopPropagation();
                    });
                    $(function() {
                        $("#dateReported").datepicker();
                    });
                });
            </script>
        </form:form>
    </body>
</html>
