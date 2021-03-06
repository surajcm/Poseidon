<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <spring:url value="/resources/images/Poseidon_Ico.ico" var="posIcon" />
    <link rel="shortcut icon" href="${posIcon}" >
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Log In</title>
    <link href="/css/bootstrap-5.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="/css/logInStyles.css"  type="text/css" />
    <script type="text/javascript">
        function submitLogIn() {
            document.forms[0].action="LogIn.htm";
            document.forms[0].submit();
        }
    </script>
</head>
<body>
    <main>
        <section class="d-flex align-items-center my-5 mt-lg-6 mb-lg-5">
            <div class="container">
                <div class="row justify-content-center form-bg-image" data-background-lg="/resources/images/Poseidon_clear1.svg" style="background: rgba(0, 0, 0, 0) url('/resources/images/Poseidon_clear1.svg') repeat scroll 0% 0%;">
                    <div class="col-12 d-flex align-items-center justify-content-center">
                        <div class="bg-white shadow-soft border rounded border-light p-4 p-lg-5 w-100 fmxw-500" style="opacity: 0.9;">
                            <div class="text-center text-md-center mb-4 mt-md-0">
                                <h1 class="mb-0 h3">Sign in Poseidon</h1>
                            </div>
                            <form method="post" action="${contextPath}/login"  class="mt-4" id="login-nav">
                                <div class="form-group mb-4"><label for="email">Your Email</label>
                                    <div class="input-group">
                                        <span>${message}</span>
                                        <span>${error}</span>
                                        <span class="input-group-text" id="basic-addon1"><span class="fas fa-envelope"></span></span>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input id="username" name="username" type="email" class="form-control" placeholder="example@company.com" id="email" autofocus="true" required="true">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="form-group mb-4"><label for="password">Your Password</label>
                                        <div class="input-group"><span class="input-group-text" id="basic-addon2"><span class="fas fa-unlock-alt"></span></span> <input type="password" placeholder="Password" class="form-control" id="password" name="password" required="true">
                                        </div>
                                    </div>
                                    <div class="d-flex justify-content-between align-items-top mb-4">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" value="" id="remember"> <label class="form-check-label mb-0" for="remember">Remember me</label>
                                        </div>
                                        <div>
                                            <a href="./forgot-password.html" class="small text-right">Lost password?</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="d-grid">
                                    <button type="submit" class="btn btn-dark">Sign in</button>
                                </div>
                            </form>
                            <div class="mt-3 mb-4 text-center">
                            </div>
                            <div class="d-flex justify-content-center my-4">
                            </div>
                            <div class="d-flex justify-content-center align-items-center mt-4">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </main>
    <script src="/js/bootstrap-5.min.js"></script>
</body>
</html>