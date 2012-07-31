<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Invoice List</title>
    <link rel="stylesheet" type="text/css" href="../css/mainStyles.css" />
</head>
<body style="background: #A9A9A9 ;">
    <form:form method="POST" commandName="invoiceForm" name="invoiceForm" >
        <form:hidden name="loggedInUser" path="loggedInUser" />
        <form:hidden name="loggedInRole" path="loggedInRole" />
        <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
        <div id="content">
            <div class="wrap">
                <fieldset style="text-align:right;">
                    <legend>Search Invoice</legend>
                    <table style="margin:auto;top:50%;left:50%;">
                        <tr>
                            <td>
                                <label for="id" style="font-size: .70em;">
                                    Invoice Id :
                                </label>
                            </td>
                            <td>
                                <form:input path="searchInvoiceVO.id" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="id"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="Description" style="font-size: .70em;">
                                    Description :
                                </label>
                            </td>
                            <td>
                                <form:input path="searchInvoiceVO.description" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="description"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td>
                                <label for="serialNo" style="font-size: .70em;">
                                    Serial No :
                                </label>
                            </td>
                            <td>
                                <form:input path="searchInvoiceVO.serialNo" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="serialNo"/>
                            </td>
                        </tr>
                    </table>
                </fieldset>
                <c:if test="${invoiceForm.statusMessage!=null}">
                    <div  id="effect" class="<c:out value="${invoiceForm.statusMessageType}"/>">
                        <c:out value="${invoiceForm.statusMessage}"/>
                    </div>
                </c:if>
                <fieldset>
                    <legend>Invoice Details</legend>
                    <table border="2" id="myTable" style="font-size: .60em;">
                    </table>
                </fieldset>
            </div>
        </div>
    </form:form>
</body>
</html>