<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Terms And Company details</title>
    <link rel="stylesheet" type="text/css" href="../css/mainStyles.css"/>


</head>
<body  style="background: #A9A9A9 ;">
<form:form method="POST" commandName="companyTermsForm" name="companyTermsForm">
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <form:hidden name="loggedInUser" path="loggedInUser"/>
    <form:hidden name="loggedInRole" path="loggedInRole"/>
    <div id="content">
        <div class="wrap">
            <fieldset style="text-align:right;">
                <legend>Company Terms and Details List</legend>
                <table border="2" id="myTable" style="font-size: .60em;">
                    <thead>
                    <tr>
                        <th>Terms And Conditions</th>
                        <th>Company Details</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr>
                        <td><c:out value="${companyTermsForm.currentCompanyTermsVO.termsAndConditions}"/></td>
                        <td><c:out value="${companyTermsForm.currentCompanyTermsVO.companyDetails}"/></td>
                    </tr>
                    </tbody>
                </table>
                <table style="margin:auto;top:50%;left:50%;">
                    <tr>
                        <td>
                            <br/>
                            <br/>
                            <input class="btn" value="Edit Terms" type="button" onclick="javascript:editMe()"/>
                            <input class="btn" value="Edit Company Details" type="button" onclick="javascript:editMe()"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>
    </div>
</form:form>

</body>
</html>