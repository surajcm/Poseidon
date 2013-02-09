<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome</title>
    <link rel="stylesheet" type="text/css" href="../css/mainStyles.css" />
<style type="text/css">html {
		  font-size: 100%;
		  -webkit-text-size-adjust: 100%;
		  -ms-text-size-adjust: 100%;
		}
		button,
		input,
		select,
		textarea {
		  margin: 0;
		  font-size: 100%;
		  vertical-align: middle;
		}

		button,
		input {
		  *overflow: visible;
		  line-height: normal;
		}

		button::-moz-focus-inner,
		input::-moz-focus-inner {
		  padding: 0;
		  border: 0;
		}

		button,
		input[type="button"],
		input[type="reset"],
		input[type="submit"] {
		  cursor: pointer;
		  -webkit-appearance: button;
		}
		input[type="search"] {
		  -webkit-box-sizing: content-box;
			 -moz-box-sizing: content-box;
				  box-sizing: content-box;
		  -webkit-appearance: textfield;
		}

		input[type="search"]::-webkit-search-decoration,
		input[type="search"]::-webkit-search-cancel-button {
		  -webkit-appearance: none;
		}

		textarea {
		  overflow: auto;
		  vertical-align: top;
		}

		.clearfix {
		  *zoom: 1;
		}

		.clearfix:before,
		.clearfix:after {
		  display: table;
		  line-height: 0;
		  content: "";
		}

		.clearfix:after {
		  clear: both;
		}

		.hide-text {
		  font: 0/0 a;
		  color: transparent;
		  text-shadow: none;
		  background-color: transparent;
		  border: 0;
		}

		.input-block-level {
		  display: block;
		  width: 100%;
		  min-height: 30px;
		  -webkit-box-sizing: border-box;
			 -moz-box-sizing: border-box;
				  box-sizing: border-box;
		}

		body {
		  margin: 0;
		  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
		  font-size: 14px;
		  line-height: 20px;
		  color: #333333;
		  background-color: #ffffff;
		}
    </style>
</head>
<body style="background: #A9A9A9 ;">
<form:form method="POST" commandName="userForm" name="userForm" action="listAll.htm" >
    <input type="hidden" name="id" id="id" />
    <form:hidden name="loggedInUser" path="loggedInUser" />
    <form:hidden name="loggedInRole" path="loggedInRole" />
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <table width="100%" height="100%">
        <tr>
            <td>
                &nbsp;
            </td>
            <td align="center">
                <img src="<%=request.getContextPath()%>/images/poseidon_god_of_the_sea.jpg" style="margin:0px; width:800px; height:600px;"/>
            </td>
            <td>
                &nbsp;
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>
