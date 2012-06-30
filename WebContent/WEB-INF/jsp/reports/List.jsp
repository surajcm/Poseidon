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
    <script type="text/javascript">
        function fetchMakeReport() {
            document.reportsForm.target = 'reportContent';
            var url = 'getMakeDetailsReport.htm';
            //var url = 'reports.htm?action=getMakeDetailsReport';
            //url = url + "?rptfilename=makeListReport";
            document.reportsForm.action = url;
            document.reportsForm.submit();
            document.reportsForm.target = '';
        }

        function fetchCallReport() {
            document.reportsForm.target = 'reportContent';
            var url = 'getCallReport.htm';
            document.reportsForm.action = url;
            document.reportsForm.submit();
            document.reportsForm.target = '';
        }
    </script>
</head>
<body style="background: #A9A9A9 ;">
<form:form method="POST" commandName="reportsForm" name="reportsForm">
    <form:hidden name="loggedInUser" path="loggedInUser"/>
    <form:hidden name="loggedInRole" path="loggedInRole"/>
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <div id="content">
        <div class="wrap">
            <fieldset style="text-align:right;">
                <legend>Generate Call Report</legend>
                <table style="margin:auto;top:50%;left:50%;">
                    <tr>
                        <td>
                            <label for="tagNo" style="font-size: .70em;">
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
                            <label for="exportTo" style="font-size: .70em;">
                                Export To :
                            </label>
                        </td>
                        <td>
                            <form:select id="exportTo" path="currentReport.exportTo"
                                         onkeypress="handleEnter(event);"
                                         cssStyle="border:3px double #CCCCCC; width: 200px;height:25px;">
                                <form:option value=""><spring:message code="common.select"
                                                                      text="<-- Select -->"/></form:option>
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