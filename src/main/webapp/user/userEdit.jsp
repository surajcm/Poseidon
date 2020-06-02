<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit User</title>
    <style type="text/css">
	table {
		margin:auto;
		top:50%;
		left:50%;
	}
	</style>
	<script type="text/javascript" src="/js/user-edit-scripts.js"></script>
</head>
<body>
    <form:form method="POST" modelAttribute="userForm" action="listAll.htm" >
        <form:hidden name="loggedInUser" path="loggedInUser" />
        <form:hidden name="loggedInRole" path="loggedInRole" />
        <%@include file="../myHeader.jsp" %>
        <div id="content">
            <div class="wrap">
                <div class="panel panel-primary">
                    <div class="panel-heading">Edit User</div>
                    <c:if test="${!empty userForm.user}">
                        <form:hidden path="user.id" />
                        <table style="margin:auto;top:50%;left:50%;">
                            <tr>
                                <td>
                                    <label for="name" class="control-label">
                                        <spring:message code="poseidon.username" text="User Name:" /> :
                                    </label>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <form:input path="user.name" cssClass="form-control" id="name" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4">&nbsp;</td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="loginId" class="control-label">
                                        <spring:message code="poseidon.loginId" text="loginId"/> :
                                    </label>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <form:input path="user.loginId" cssClass="form-control" id="loginId"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4">&nbsp;</td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="psw" class="control-label">
                                        <spring:message code="poseidon.password" text="Password:" /> :
                                    </label>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <form:input path="user.password" cssClass="form-control" id="psw" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4">&nbsp;</td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="role" class="control-label">
                                        <spring:message code="poseidon.role" text="Role:" /> :
                                    </label>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td align="left">
                                    <form:select id="role" path="user.role" onkeypress="handleEnter(event);" cssClass="form-control" >
                                        <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                        <form:options items="${userForm.roleList}" />
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4">&nbsp;</td>
                            </tr>
                            <tr>
                                <td>
                                    <input class="btn btn-primary btn-success" value="Update" type="button" onclick="javascript:UpdateMe();" />
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <input class="btn btn-primary" value="Cancel" type="button" onclick="javascript:CancelMe();" />
                                </td>
                            </tr>
                        </table>
                    </c:if>
                </div>
            </div>
        </div>
    </form:form>
</body>
</html>