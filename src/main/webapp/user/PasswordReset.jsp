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
    <link rel="stylesheet" href="/css/bootstrap-5.min.css"  type="text/css" />
    <link rel="stylesheet" href="/css/custom.css" type="text/css" />
    <title>Password Reset</title>
    <script type="text/javascript" src="/js/password-reset-scripts.js"></script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body onload="hideAlerts()">
    <form method="POST" action="listAll.htm" class="needs-validation" novalidate>
        <input type="hidden" name="id" id="id" />
        <input type="hidden" name="addInProgress" id="addInProgress" />
        <%@include file="../navbar5.jsp" %>
        <div id="status" >
        <c:if test="${userForm.statusMessage!=null}">
            <div class="alert alert-<c:out value='${userForm.statusMessageType}'/> alert-dismissible fade show" role="alert">
            <c:out value="${userForm.statusMessage}"/>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        </div>
        <div class="row paddingBottom20">
            <div id="passwordResetModal" class="container col-4">
                <div class="row">
                    <h6 class="text-center col-12 mb-0">Reset Password</h6>
                    <sub class="text-right text-muted col-12"><a href="#" tabindex="-1"><i class="far fa-edit"></i></a></sub>
                </div>
                <div class="dropdown-divider mb-3"></div>
                <div class="mb-3 row">
                    <label for="current" class="col-sm-2 col-form-label">Current Password:</label>
                    <div class="col-sm-10">
                        <input id="current" type="password" class="form-control" onkeypress="clearMessage();" required/>
                        <div id="current_message" class="invalid-tooltip">Please fill out current password</div>
                    </div>
                </div>
                <div class="mb-3 row">
                    <label for="newPass" class="col-sm-2 col-form-label">New Password:</label>
                    <div class="col-sm-10">
                        <input id="newPass" type="password" class="form-control" onkeypress="clearMessage();" required/>
                        <div id="newPass_message" class="invalid-tooltip">Please fill out new password</div>
                    </div>
                </div>
                <div class="mb-3 row">
                    <label for="repeat" class="col-sm-2 col-form-label">Repeat Password:</label>
                    <div class="col-sm-10">
                        <input id="repeat" type="password" class="form-control" onkeypress="clearMessage();" required/>
                        <div id="repeat_message" class="invalid-tooltip">Please fill out repeat password</div>
                    </div>
                </div>
                <div class="mb-3 row">
                    <label for="reset" class="col-sm-2 col-form-label"></label>
                    <div class="col-sm-10">
                        <button type="button" id="reset" class="btn btn-primary" onclick="passwordChange()">Reset password</button>
                        <button type="button" class="btn btn-primary" onclick="clearAll()">Cancel</button>
                    </div>
                </div>
            </div>
        </div>
        <script src="/js/core/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="/js/core/popper.min.js" type="text/javascript"></script>
        <script src="/js/core/bootstrap-5.min.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                //Handles menu drop down
                $('.dropdown-menu').find('form').click(function (e) {
                    e.stopPropagation();
                });
            });
        </script>
    </form>
</body>
</html>
