<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>User Add</title>
		<link rel="stylesheet" type="text/css" href="../css/mainStyles.css" />
		<script type="text/javascript">

			//code to add New user
			function save(){
				document.forms[0].action="SaveUser.htm";
				document.forms[0].submit();
			}

		    //code to edit a user
			function clear(){
				document.getElementById("name").value ="";
				document.getElementById("psw").value ="";
			}
		</script>
	</head>
	<body>
        <form:form method="POST" commandName="userForm" name="userForm" >
            <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
            <form:hidden name="loggedInUser" path="loggedInUser" />
	        <form:hidden name="loggedInRole" path="loggedInRole" />
            <div id="content">
                <table class="myTable">
                    <tr>
                        <td>
                            <label for="name"><spring:message code="poseidon.username" text="User Name" /></label>
                            <label class="mandatory">*</label>
                        </td>
                        <td>
                            <form:input path="user.name" cssClass="textboxes" id="name" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="name"><spring:message code="poseidon.loginId" text="loginId" /></label>
                            <label class="mandatory">*</label>
                        </td>
                        <td>
                            <form:input path="user.loginId" cssClass="textboxes" id="loginId" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="password"><spring:message code="poseidon.password" text="Password" /></label>
                            <label class="mandatory">*</label>
                        </td>
                        <td>
                            <form:password path="user.password" cssClass="textboxes" id="psw" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label	for="role"><spring:message code="poseidon.role" text="Role" /></label>
                            <label class="mandatory">*</label>
                        </td>
                        <td>
                            <form:select path="user.role" cssClass="textboxes" id="role" >
                                <form:option value="admin"><spring:message code="poseidon.admin" text="admin" /></form:option>
                                <form:option value="guest"><spring:message code="poseidon.guest" text="guest" /></form:option>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            &nbsp;
                        </td>
                        <td>
                            <input class="btn" value="Save" type="button" onclick="javascript:save();" />
                            <input class="btn" value="Clear" type="button" onclick="javascript:clear();" />
                        </td>
                    </tr>
                </table>
		    </div>
        </form:form>
	</body>
</html>
