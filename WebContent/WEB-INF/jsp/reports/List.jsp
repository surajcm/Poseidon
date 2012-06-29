<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reports List</title>
        <link rel="stylesheet" type="text/css" href="../css/mainStyles.css" />
        <script type="text/javascript">
            function fetchMakeReport(){
                document.reportsForm.target='reportContent';
                var url = 'getMakeDetailsReport.htm';
                //var url = 'reports.htm?action=getMakeDetailsReport';
                //url = url + "?rptfilename=makeListReport";
                document.reportsForm.action=url;
                document.reportsForm.submit();
                document.reportsForm.target = '';
            }
        </script>
    </head>
    <body>
        <form:form method="POST" commandName="reportsForm" name="reportsForm" >
        <form:hidden name="loggedInUser" path="loggedInUser" />
        <form:hidden name="loggedInRole" path="loggedInRole" />
        <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
            <div id="content">
                <div class="wrap">
                    <fieldset style="text-align:right;">
                        <legend>Search Model</legend>
                        <table style="margin:auto;top:50%;left:50%;">
                            <tr>
                                <td colspan="2">
                                    <input class="btn" value="Fetch Make Report" type="button" onclick="javascript:fetchMakeReport()"/>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td colspan="2">
                                    <input class="btn" value="Clear" type="button" onclick="javascript:clearOut()"/>
                                </td>
                            <tr>
                        </table>
                    </fieldset>
                    </div>
                <table class="myTable">
                    <fieldset style="width: 99%;height: 80%">
                        <table border="0" cellpadding="0px" cellspacing="0px" width="100%">
                            <tr>
                                <td valign="top">
                                    <iframe name="reportContent" id="reportContent" width="100%" height="420px" frameborder="0">
                                    </iframe>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </table>
            </div>
        </form:form>
    </body>
</html>