<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>User List</title>
		<link rel="stylesheet" type="text/css" href="../css/mainStyles.css" />
	</head>
	<body>
        <form:form method="POST" commandName="userForm" name="userForm" action="listAll.htm" >
        <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
        <input type="hidden" name="id" id="id" />
        <form:hidden name="loggedInUser" path="loggedInUser" />
	    <form:hidden name="loggedInRole" path="loggedInRole" />
        <div id="content">
            <div class="wrap">
                
			</div>
		</div>
		</form:form>
	</body>
</html>
