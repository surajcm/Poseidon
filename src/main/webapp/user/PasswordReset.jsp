<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Suraj">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <spring:url value="/img/Poseidon_Ico.ico" var="posIcon" />
    <link rel="shortcut icon" href="${posIcon}" />
    <link rel="stylesheet" href="/css/bootstrap.min.css"  type="text/css" />
    <link rel="stylesheet" href="/css/custom.css" type="text/css" />
    <title>Password Reset</title>
    <script type="text/javascript" src="/js/user-scripts.js"></script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body onload="javascript:hideAlerts()">
    <form:form method="POST" action="listAll.htm" modelAttribute="userForm">
        <input type="hidden" name="id" id="id" />
        <input type="hidden" name="addInProgress" id="addInProgress" />
        <form:hidden name="loggedInUser" path="loggedInUser" />
        <form:hidden name="loggedInRole" path="loggedInRole" />
        <%@include file="../navbar.jsp" %>
        <div class="container">
            <div class="wrap">
                <c:if test="${userForm.statusMessage!=null}">
                    <div class="alert alert-<c:out value='${userForm.statusMessageType}'/>">
                        <a class="close" data-dismiss="alert" href="#" aria-hidden="true">x</a>
                        <c:out value="${userForm.statusMessage}"/>
                    </div>
                </c:if>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                    Reset Password
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newUserModal"
                                onclick="javascript:addNewUser()">Reset password</button>
                            <button type="button" class="btn btn-primary" onclick="javascript:editMe()">
                                Cancel
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="/js/core/jquery-3.2.1.min.js"></script>
        <script src="/js/core/popper.min.js"></script>
        <script src="/js/core/bootstrap.min.js"></script>
        <script>
            $(document).ready(function() {
                //Handles menu drop down
                $('.dropdown-menu').find('form').click(function (e) {
                    e.stopPropagation();
                });
            });
        </script>
    </form:form>
</body>
</html>
