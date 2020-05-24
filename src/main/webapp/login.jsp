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
    <spring:url value="/resources/css/logInStyles.css" var="logInStyles" />
    <link href="${logInStyles}" rel="stylesheet" />
    <script type="text/javascript">
        function submitLogIn() {
            document.forms[0].action="LogIn.htm";
            document.forms[0].submit();
        }
    </script>
</head>
<body>
<div id="signin" class="content">
    <div class="box_wrapper">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td bgcolor="#000000" colspan="2">&nbsp;<td>
                <td rowspan="3" bgcolor="#000000">&nbsp;&nbsp;&nbsp;&nbsp;<td>
            </tr>
            <tr>
                <td align="right" bgcolor="#000000">
                    <spring:url value="/resources/images/Poseidon_.jpg" var="login_img" />
                    <img src="${login_img}" style="margin:0px; width:200px; height:180px"/>
                </td>
                <td>
                    <form class="form" role="form" method="post" action="${contextPath}/login" accept-charset="UTF-8" id="login-nav">
                        <div>
                            <p>
                                 <span>${message}</span>
                                 <span>${error}</span>
                                 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                             </p>
                            <p>
                                 <label class="sr-only" for="exampleInputEmail2">loginId</label>
                                 <input name="username" type="text" class="textboxes" placeholder="Username"
                                                    autofocus="true" required/>
                            </p>
                            <p>
                                 <label class="sr-only" for="exampleInputPassword2">Password</label>
                                 <input name="password" type="password" class="textboxes" placeholder="Password" required/>
                            </p>
                            <br />
                            <p class="button">
                                 <button type="submit" class="btn">Sign in</button>
                            </p>
                            <p class="checkbox">
                                 <label class="sr-only" >
                                    <input type="checkbox" /> keep me logged-in
                                 </label>
                            </p>
                        </div>
                    </form>
                </td>
            </tr>
            <tr>
                <td colspan="2" bgcolor="#000000">&nbsp;<td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>