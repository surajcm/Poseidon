<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Edit User</title>
		<link rel="stylesheet" type="text/css" href="../css/mainStyles.css" />
		<script type="text/javascript">

			//code to update user
			function UpdateMe(){
				document.forms[0].action="UpdateUser.htm";
				document.forms[0].submit();
			}

		    //code to clear
			function CancelMe(){
				document.forms[0].action="ListAll.htm";
				document.forms[0].submit();
			}

		</script>
	</head>
	<body>
        <form:form method="POST" commandName="userForm" name="userForm" action="listAll.htm" >
            <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
            <form:hidden name="loggedInUser" path="loggedInUser" />
	        <form:hidden name="loggedInRole" path="loggedInRole" />
            <div id="content">
                <c:if test="${!empty userForm.user}">
					<form:hidden path="user.id" />
				    <table class="myTable">
					    <tr>
						    <td>
							    <label for="name"><spring:message code="poseidon.username" text="User Name:" /></label>
								<label class="mandatory">*</label>
							</td>
							<td>
								<form:input path="user.name" cssClass="textboxes" id="name" />
							</td>
						</tr>
						<tr>
							<td>
							    <label for="password"><spring:message code="poseidon.password" text="Password:" /></label>
								<label class="mandatory">*</label>
							</td>
							<td>
								<form:input path="user.password" cssClass="textboxes" id="psw" />
							</td>
						</tr>
						<tr>
							<td>
								<label for="name"><spring:message code="poseidon.role" text="Role:" /></label>
								<label class="mandatory">*</label>
							</td>
							<td>
								<form:select path="user.role" cssClass="textboxes" id="role" >
									<form:option value="admin">Admin</form:option>
									<form:option value="guest">Guest</form:option>
								</form:select>
							</td>
						</tr>
						<tr>
							<td>
								<input class="btn" value="Update" type="button" onclick="javascript:UpdateMe();" />
							</td>
							<td>
								<input class="btn" value="Cancel" type="button" onclick="javascript:CancelMe();" />
							</td>
						</tr>
					</table>
			    </c:if>
            </div>
        </form:form>
    </body
</html>