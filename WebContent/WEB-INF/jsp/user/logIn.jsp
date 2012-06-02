<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
                <form:form method="POST" commandName="userForm">
                    <div class="sign_in">
                        <fieldset>
                            <p class="">
                                <label for="name"><spring:message code="poseidon.username" text="User Name:" /></label>
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
                    </div>
                </form:form>
            </div>
        </div>
	</body>
</html>