<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Reports List</title>
    <script type="text/javascript">
        function fetchMakeReport() {
            document.getElementById('exportTo').value = document.getElementById('makeExportValue').options[document.getElementById('makeExportValue').selectedIndex].text;
            document.reportsForm.target = 'reportContent';
            document.reportsForm.action = 'getMakeDetailsReport.htm';
            document.reportsForm.submit();
            document.reportsForm.target = '';
        }

        function fetchCallReport() {
            document.getElementById('exportTo').value = document.getElementById('callExportValue').options[document.getElementById('callExportValue').selectedIndex].text;
            document.getElementById('tagNo').value = document.getElementById('callTagNo').value;
            document.reportsForm.target = 'reportContent';
            document.reportsForm.action = 'getCallReport.htm';
            document.reportsForm.submit();
            document.reportsForm.target = '';
        }

        function fetchTransactionsListReport() {
            document.getElementById('exportTo').value = document.getElementById('txnExportValue').options[document.getElementById('txnExportValue').selectedIndex].text;
            document.reportsForm.target = 'reportContent';
            document.reportsForm.action = 'getTransactionsListReport.htm';
            document.reportsForm.submit();
            document.reportsForm.target = '';
        }

        function fetchModelListReport() {
            document.getElementById('exportTo').value = document.getElementById('makeExportValue').options[document.getElementById('makeExportValue').selectedIndex].text;
            document.reportsForm.target = 'reportContent';
            document.reportsForm.action = 'getModelListReport.htm';
            document.reportsForm.submit();
            document.reportsForm.target = '';
        }

        function fetchInvoiceReport() {
            document.getElementById('exportTo').value = document.getElementById('invoiceExportValue').options[document.getElementById('invoiceExportValue').selectedIndex].text;
            document.getElementById('tagNo').value = document.getElementById('invoiceTagNo').value;
            document.reportsForm.target = 'reportContent';
            document.reportsForm.action = 'getInvoiceReport.htm';
            document.reportsForm.submit();
            document.reportsForm.target = '';
        }
        function changeTheTxnModel(){
            var selectMakeId = document.searchTransaction.makeId.value;
            var url = "<%=request.getContextPath()%>" + "/txs/UpdateModelAjax.htm";
            url = url + "?selectMakeId=" + selectMakeId;
            bustcacheparameter = (url.indexOf("?") != -1) ? "&" + new Date().getTime() : "?" + new Date().getTime();
            createAjaxRequest();
            if (req) {
                req.onreadystatechange = stateChangeOnTxn;
                req.open("POST", url + bustcacheparameter, true);
                req.send(url + bustcacheparameter);
            }
        }

        function changeTheModel(){
            var selectMakeId = document.reportsForm.makeId.value;
            var url = "<%=request.getContextPath()%>" + "/txs/UpdateModelAjax.htm";
            url = url + "?selectMakeId=" + selectMakeId;
            bustcacheparameter = (url.indexOf("?") != -1) ? "&" + new Date().getTime() : "?" + new Date().getTime();
            createAjaxRequest();
            if (req) {
                req.onreadystatechange = stateChange;
                req.open("POST", url + bustcacheparameter, true);
                req.send(url + bustcacheparameter);
            }
        }
        function createAjaxRequest() {
            if (window.XMLHttpRequest){
                req = new XMLHttpRequest() ;
            } else if (window.ActiveXObject) {
                try {
                    req = new ActiveXObject("Msxml2.XMLHTTP");
                } catch (e) {
                    try {
                        req = new ActiveXObject("Microsoft.XMLHTTP");
                    } catch (e) {
                    }
                }
            }
        }

        function stateChange() {
            if (req.readyState == 4 && (req.status == 200 || window.location.href.indexOf("http") == -1)) {
                textReturned = req.responseText;
                if(textReturned != "") {
                    var fullContent = textReturned.split("#start#");
                    var resultIds = new Array();
                    var resultNames = new Array();
                    var k = 0;
                    var j = 0;
                    var t = 0;

                    for (j = 0; j < fullContent.length; j++) {
                        if(fullContent[j].length > 0 ) {
                            resultIds[k] = fullContent[j].split("#id#")[1];
                            var testing = fullContent[j].split("#id#")[2];
                            resultNames[k] = testing.split("#modelName#")[1];
                            k++;
                        }
                    }
                    var l =0;
                    document.reportsForm.modelId.options.length = resultIds.length - 1;
                    document.reportsForm.modelId.options[0] = new Option("<-- Select -->", "");
                    for (var i = 1; i <= (resultIds.length); i++) {
                        document.reportsForm.modelId.options[i] = new Option(resultNames[i - 1], resultIds[i - 1]);
                    }
                } else {
                    document.reportsForm.modelId.options.length = 0;
                    document.reportsForm.modelId.options[0] = new Option("<-- Select -->", "");
                }
            }
        }

        function stateChangeOnTxn() {
            if (req.readyState == 4 && (req.status == 200 || window.location.href.indexOf("http") == -1)) {
                textReturned = req.responseText;
                if(textReturned != "") {
                    var fullContent = textReturned.split("#start#");
                    var resultIds = new Array();
                    var resultNames = new Array();
                    var k = 0;
                    var j = 0;
                    var t = 0;

                    for (j = 0; j < fullContent.length; j++) {
                        if(fullContent[j].length > 0 ) {
                            resultIds[k] = fullContent[j].split("#id#")[1];
                            var testing = fullContent[j].split("#id#")[2];
                            resultNames[k] = testing.split("#modelName#")[1];
                            k++;
                        }
                    }
                    var l =0;
                    document.searchTransaction.modelId.options.length = resultIds.length - 1;
                    document.searchTransaction.modelId.options[0] = new Option("<-- Select -->", "");
                    for (var i = 1; i <= (resultIds.length); i++) {
                        document.searchTransaction.modelId.options[i] = new Option(resultNames[i - 1], resultIds[i - 1]);
                    }
                } else {
                    document.searchTransaction.modelId.options.length = 0;
                    document.searchTransaction.modelId.options[0] = new Option("<-- Select -->", "");
                }
            }
        }
        function selectMenu(){
            document.getElementById('reportmgt').className = "active";
        }
    </script>
    <script>
        $(function() {
            $( "#tabs" ).tabs();
            $( "#startDate" ).datepicker({ dateFormat: "dd/mm/yy" });
            $( "#endDate" ).datepicker({ dateFormat: "dd/mm/yy" });
        });
    </script>
</head>
<body onload="javascript:selectMenu()">
<form:form method="POST" commandName="reportsForm" name="reportsForm">
<form:hidden name="loggedInUser" path="loggedInUser"/>
<form:hidden name="loggedInRole" path="loggedInRole"/>
<form:hidden name="exportTo" path="currentReport.exportTo" id="exportTo"/>
<form:hidden name="tagNo" path="currentReport.tagNo" id="tagNo"/>
<%@include file="/WEB-INF/jsp/myHeader.jsp" %>
<div class="container">
<ul class="nav nav-tabs">
    <li class="active"><a href="#callReport" data-toggle="tab">Generate Call Report :</a></li>
    <li><a href="#modelReport" data-toggle="tab">Generate Make/Model Report :</a></li>
    <li><a href="#txnReport" data-toggle="tab">Generate TransactionsList Report :</a></li>
    <li><a href="#invoiceReport" data-toggle="tab">Generate Invoice Report :</a></li>
    <li><a href="#invoiceListReport" data-toggle="tab">Generate InvoiceList Report :</a></li>
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
</form:form>
</body>
</html>