<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome</title>
    <style type="text/css">
    	.jumbotron {
            display: flex;
            align-items: center;
            height: 600px;
            background-image: url("/resources/images/poseidon_god_of_the_sea.jpg");
            background-repeat: no-repeat;
            background-size:  contain;
            background-position: center;
        }
    </style>
</head>
<body>
    <form:form method="POST" action="listAll.htm" modelAttribute="userForm">
        <input type="hidden" name="id" id="id" />
        <form:hidden name="loggedInUser" path="loggedInUser" />
        <form:hidden name="loggedInRole" path="loggedInRole" />
        <%@include file="/myHeader.jsp" %>
        <c:if test="${pageContext.request.userPrincipal.name != null}">
            <form id="logoutForm" method="POST" action="${contextPath}/logout">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </c:if>
        <section class="jumbotron">

        </section>
    </form:form>
</body>
</html>
