<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/images/Poseidon_Ico.ico" >
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Log In</title>
    <link rel="stylesheet" type="text/css" href="../css/logInStyles.css" />
    <script type="text/javascript">
        function submitLogIn(){
            //code to submit the page
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
                    <img src="<%=request.getContextPath()%>/images/Poseidon_.jpg" style="margin:0px; width:200px; height:180px"/>
                </td>
                <td>
                    <form:form method="POST" commandName="userForm">
                        <fieldset>
                            <p class="">
                                <label for="loginId"><spring:message code="poseidon.username" text="User Name:" /></label>
                                <form:input path="loginId" cssClass="textboxes" id="loginId" />
                                <form:errors path="loginId" />
                            </p>
                            <p class="">
                                <label for="password"><spring:message code="poseidon.password" text="Password:" /></label>
                                <form:password path="password" cssClass="textboxes" id="psw" />
                                <form:errors path="password" />
                            </p>
                            <p class="button">
                                <input class="btn" value="Log In" type="submit" onclick="javascript:submitLogIn()" />
                            </p>
                        </fieldset>

                    </form:form>
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