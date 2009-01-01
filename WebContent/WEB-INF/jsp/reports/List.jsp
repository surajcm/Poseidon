<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Reports List</title>
    <link rel="stylesheet" type="text/css" href="../css/mainStyles.css"/>
    <link rel="stylesheet" type="text/css" href="../css/ui-lightness/jquery-ui-1.8.21.custom.css"/>
    <script type="text/javascript" src="../js/jquery-1.7.2.min.js" language="javascript" ></script>
    <script type="text/javascript" src="../js/jquery-ui-1.8.21.custom.min.js" language="javascript" ></script>
    <script type="text/javascript">
        function fetchMakeReport() {
            document.reportsForm.target = 'reportContent';
            document.reportsForm.action = 'getMakeDetailsReport.htm';
            document.reportsForm.submit();
            document.reportsForm.target = '';
        }

        function fetchCallReport() {
            document.reportsForm.target = 'reportContent';
            document.reportsForm.action = 'getCallReport.htm';
            document.reportsForm.submit();
            document.reportsForm.target = '';
        }

        function fetchTransactionsListReport() {
            document.reportsForm.target = 'reportContent';
            document.reportsForm.action = 'getTransactionsListReport.htm';
            document.reportsForm.submit();
            document.reportsForm.target = '';
        }

        function fetchModelListReport() {
            document.reportsForm.target = 'reportContent';
            document.reportsForm.action = 'getModelListReport.htm';
            document.reportsForm.submit();
            document.reportsForm.target = '';
        }

        function fetchInvoiceReport() {
            document.reportsForm.target = 'reportContent';
            document.reportsForm.action = 'getInvoiceReport.htm';
            document.reportsForm.submit();
            document.reportsForm.target = '';
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
    </script>
    <script>
        $(function() {
            $( "#tabs" ).tabs();
            $( "#startDate" ).datepicker({ dateFormat: "dd/mm/yy" });
            $( "#endDate" ).datepicker({ dateFormat: "dd/mm/yy" });
        });
    </script>
</head>
<body style="background: #A9A9A9 ;">
<form:form method="POST" commandName="reportsForm" name="reportsForm">
    <form:hidden name="loggedInUser" path="loggedInUser"/>
    <form:hidden name="loggedInRole" path="loggedInRole"/>
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <div id="content">
        <div class="wrap">
            <div id="tabs" style="font-size:10px;">
                <ul>
                    <li><a href="#callReport">Generate Call Report :</a></li>
                    <li><a href="#modelReport">Generate Make/Model Report :</a></li>
                    <li><a href="#txnReport">Generate TransactionsList Report :</a></li>
                    <li><a href="#invoiceReport">Generate Invoice Report :</a></li>
                </ul>
                <div id="callReport">
                    <fieldset style="text-align:right;">
                        <legend>Generate Call Report :</legend>
                        <table style="margin:auto;top:50%;left:50%;">
                            <tr>
                                <td>
                                    <label for="tagNo">
                                        Tag To :
                                    </label>
                                </td>
                                <td>
                                    <form:input path="currentReport.tagNo"
                                                cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;font-size: .70em;"
                                                id="tagNo"/>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="exportTo" >
                                        Export To :
                                    </label>
                                </td>
                                <td>
                                    <form:select id="exportTo" path="currentReport.exportTo"
                                                 onkeypress="handleEnter(event);"
                                                 cssStyle="border:3px double #CCCCCC; width: 200px;height:25px;">
                                        <form:option value="">
                                            <spring:message code="common.select" text="<-- Select -->"/>
                                        </form:option>
                                        <form:options items="${reportsForm.exportList}"/>
                                    </form:select>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <input class="btn" value="Fetch Call Report" type="button"
                                           onclick="javascript:fetchCallReport()"/>
                                </td>
                                <td>
                                    <input class="btn" value="Clear" type="button" onclick="javascript:clearOut()"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </div>
                <div id="modelReport">
                    <fieldset>
                        <legend>Generate Make/ModelList Report :</legend>
                        <table style="margin:auto;top:50%;left:50%;">
                            <tr>
                                <td>
                                    <label>
                                        Make Name :
                                    </label>
                                </td>
                                <td>
                                    <form:select id="makeName" path="searchMakeAndModelVO.makeId" tabindex="1"
                                                 cssStyle="border:3px double #CCCCCC; width: 200px;height:25px;">
                                        <form:option value="0" label="-- Select --"/>
                                        <form:options items="${reportsForm.makeVOs}"
                                                      itemValue="id" itemLabel="makeName"/>
                                    </form:select>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label >
                                        Model Name :
                                    </label>
                                </td>
                                <td>
                                    <form:input path="searchMakeAndModelVO.modelName"
                                                cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="modelName"/>
                                </td>
                            <tr>
                            <tr>
                                <td colspan="2">
                                    <label>
                                        <spring:message code="user.includes" text="Includes"/>
                                        <form:checkbox path="searchMakeAndModelVO.includes" cssStyle="vertical-align:middle"
                                                       id="includes" value=""/>
                                    </label>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td colspan="2">
                                    <label>
                                        <spring:message code="user.startsWith" text="Starts with"/>
                                        <form:checkbox path="searchMakeAndModelVO.startswith" cssStyle="vertical-align:middle"
                                                       id="startswith" value=""/>
                                    </label>
                                </td>
                            <tr>
                            <tr>
                                <td colspan="2">
                                    <input class="btn" value="Fetch Make Report" type="button"
                                           onclick="javascript:fetchMakeReport()"/>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <input class="btn" value="Fetch Model List Report" type="button"
                                           onclick="javascript:fetchModelListReport()"/>
                                </td>
                                <td>
                                    <input class="btn" value="Clear" type="button" onclick="javascript:clearOut()"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </div>
                <div id="txnReport">
                    <fieldset>
                        <legend>Generate TransactionsList Report :</legend>
                        <table style="margin:auto;top:50%;left:50%;">
                            <tr>
                                <td>
                                    <label for="TagNo"  >
                                        Tag No :
                                    </label>
                                </td>
                                <td>
                                    <form:input path="searchTransaction.TagNo" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;font-size: .70em;" id="TagNo"/>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="CustomerName"  >
                                        Customer Name :
                                    </label>
                                </td>
                                <td>
                                    <form:input path="searchTransaction.CustomerName" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;font-size: .70em;" id="CustomerName"/>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="startDate"  >
                                        Reported Date (From) :
                                    </label>
                                </td>
                                <td>
                                    <form:input path="searchTransaction.startDate" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;font-size: .70em;" id="startDate"/>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="endDate"  >
                                        Reported Date (To) :
                                    </label>
                                </td>
                                <td>
                                    <form:input path="searchTransaction.endDate" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;font-size: .70em;" id="endDate"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="14">&nbsp;</td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="SerialNo"  >
                                        Serial No :
                                    </label>
                                </td>
                                <td>
                                    <form:input path="searchTransaction.SerialNo" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;font-size: .70em;" id="SerialNo"/>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="makeId"  >
                                        Make :
                                    </label>
                                </td>
                                <td>
                                    <form:select id="makeId" path="searchTransaction.makeId" tabindex="1"
                                                 onchange="changeTheModel();"
                                                 cssStyle="border:3px double #CCCCCC; width: 200px;height:28px;">
                                        <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                        <form:options items="${reportsForm.makeVOs}"
                                                      itemValue="Id" itemLabel="makeName"/>
                                    </form:select>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="modelId"  >
                                        Model Name :
                                    </label>
                                </td>
                                <td>
                                    <form:select id="modelId" path="searchTransaction.modelId" tabindex="1"
                                                 cssStyle="border:3px double #CCCCCC; width: 200px;height:25px;">
                                        <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                    </form:select>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="Status"  >
                                        Status :
                                    </label>
                                </td>
                                <td>
                                    <form:select id="Status" path="searchTransaction.Status"
                                                 cssStyle="border:3px double #CCCCCC; width: 200px;height:25px;">
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
                                    <label for="includes"  >
                                        <spring:message code="user.includes" text="Includes"/>
                                        <form:checkbox path="searchTransaction.includes" cssStyle="vertical-align:middle" id="includes" value="" />
                                    </label>
                                </td>
                                <td>
                                    <label for="startswith"  >
                                        <spring:message code="user.startsWith" text="Starts with"/>
                                        <form:checkbox path="searchTransaction.startswith" cssStyle="vertical-align:middle" id="startswith" value="" />
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="12">&nbsp;</td>
                                <td>
                                    <input class="btn" value="Fetch Transactions List Report" type="button"
                                           onclick="javascript:fetchTransactionsListReport()"/>
                                </td>
                                <td>
                                    <input class="btn" value="Clear" type="button" onclick="javascript:clearOut()"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </div>
                <div id="invoiceReport">
                    <fieldset>
                        <legend>Generate Invoice Report :</legend>
                        <table style="margin:auto;top:50%;left:50%;">
                            <tr>
                                <td>
                                    <input class="btn" value="Fetch Invoice Report" type="button"
                                           onclick="javascript:fetchInvoiceReport()"/>
                                </td>
                                <td>
                                    <input class="btn" value="Clear" type="button" onclick="javascript:clearOut()"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </div>
            </div>
            <fieldset>
                <legend>Report :</legend>
                <iframe name="reportContent" id="reportContent" width="100%" height="420px" frameborder="0">
                </iframe>
            </fieldset>
        </div>
    </div>
</form:form>
</body>
</html>