<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome</title>
</head>
<body>
<form:form method="POST" commandName="userForm" name="userForm" action="listAll.htm" >
    <input type="hidden" name="id" id="id" />
    <form:hidden name="loggedInUser" path="loggedInUser" />
    <form:hidden name="loggedInRole" path="loggedInRole" />
    <%@include file="/myHeader.jsp" %>
    <table width="100%" height="100%">
        <tr>
            <td>&nbsp;

            </td>
            <td align="center">
                <spring:url value="/resources/images/poseidon_god_of_the_sea.jpg" var="main_img" />
                <img src="${main_img}" style="margin:0px; width:800px; height:600px;"/>
            </td>
            <td>&nbsp;

            </td>
        </tr>
    </table>

</form:form>
</body>
</html>
