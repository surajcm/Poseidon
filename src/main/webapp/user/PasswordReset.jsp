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
    <script type="text/javascript" src="/js/password-reset-scripts.js"></script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body onload="hideAlerts()">
    <form method="POST" action="listAll.htm" class="needs-validation" novalidate>
        <input type="hidden" name="id" id="id" />
        <input type="hidden" name="addInProgress" id="addInProgress" />
        <%@include file="../navbar.jsp" %>
        <div class="row paddingBottom20">
            <div class="container col-4">
                <div id="status" >
                    <c:if test="${userForm.statusMessage!=null}">
                        <div class="alert alert-<c:out value='${userForm.statusMessageType}'/>">
                            <a class="close" data-dismiss="alert" href="#" aria-hidden="true">x</a>
                            <c:out value="${userForm.statusMessage}"/>
                        </div>
                    </c:if>
                </div>
                <div class="row">
                    <h6 class="text-center col-12 mb-0">Reset Password</h6>
                    <sub class="text-right text-muted col-12"><a href="#" tabindex="-1"><i class="far fa-edit"></i></a></sub>
                </div>
                <div class="dropdown-divider mb-3"></div>
                <div class="form-group row">
                    <label for="current" class="col-4 col-form-label-sm text-right">Current Password:</label>
                    <div class="col-8">
                        <div class="input-group">
                            <input id="current" name="text" type="password" class="form-control form-control-sm" onkeypress="clearMessage();" required/>
                            <div id="current_message" class="invalid-tooltip">Please fill out current password</div>
                        </div>
                    </div>
                </div>
                <div class="form-group row align-items-center">
                    <label for="newPass" class="col-4 col-form-label-sm text-right">New Password:</label>
                    <div class="col-8">
                      <div class="input-group">
                        <input id="newPass" name="text" type="password" class="form-control form-control-sm" onkeypress="clearMessage();" required/>
                        <div id="newPass_message" class="invalid-tooltip">Please fill out new password</div>
                      </div>
                    </div>
                </div>
                <div class="form-group row align-items-center">
                    <label for="repeat" class="col-4 col-form-label-sm text-right">Repeat Password:</label>
                    <div class="col-8">
                      <div class="input-group">
                        <input id="repeat" name="text" type="password" class="form-control form-control-sm" onkeypress="clearMessage();" required/>
                        <div id="repeat_message" class="invalid-tooltip">Please fill out repeat password</div>
                      </div>
                    </div>
                </div>
                <div class="form-group row align-items-center">
                    <label for="reset" class="col-4 col-form-label-sm text-right"></label>
                    <div class="col-8">
                      <div class="form-group">
                        <button type="button" id="reset" class="btn btn-primary" onclick="passwordChange()">Reset password</button>
                        <button type="button" class="btn btn-primary" onclick="clearAll()">Cancel</button>
                      </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="/js/core/jquery-3.2.1.min.js"></script>
        <script src="/js/core/popper.min.js"></script>
        <script src="/js/core/bootstrap.min.js"></script>
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
