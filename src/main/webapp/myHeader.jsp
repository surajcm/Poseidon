<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Suraj">
    <spring:url value="/resources/images/Poseidon_Ico.ico" var="posIcon" />
    <link rel="shortcut icon" href="${posIcon}" />
    <link rel="stylesheet" href="/css/jquery-ui.css" type="text/css" />
    <link rel="stylesheet" href="/css/font-awesome.min.css" type="text/css" />
    <link rel="stylesheet" href="/css/bootstrap.min.css"  type="text/css" />
    <link rel="stylesheet" href="/css/custom.css" type="text/css" />

    <style type="text/css">
        .foottable {
            margin:auto;
            top:50%;
            left:50%;
        }
    </style>
    <script type="text/javascript">
        function listMe(){
            document.forms[0].action="${contextPath}/user/ListAll.htm";
            document.forms[0].submit();
        }

        function goToHome(){
            document.forms[0].action="${contextPath}/user/ToHome.htm";
            document.forms[0].submit();
        }

        function LogMeOut(){
            document.forms[0].action="${contextPath}/user/LogMeOut.htm";
            document.forms[0].submit();
        }

        function MakeMe(){
            document.forms[0].action="${contextPath}/make/ModelList.htm";
            document.forms[0].submit();
        }

        function fetchCustomers(){
            document.forms[0].action="${contextPath}/customer/List.htm";
            document.forms[0].submit();
        }

        function fetchTerms(){
            document.forms[0].action="${contextPath}/company/List.htm";
            document.forms[0].submit();
        }

        function fetchTransactions(){
            document.forms[0].action="${contextPath}/txs/List.htm";
            document.forms[0].submit();
        }

        function fetchReport(){
            document.forms[0].action="${contextPath}/reports/List.htm";
            document.forms[0].submit();
        }

        function fetchInvoice(){
            document.forms[0].action="${contextPath}/invoice/ListInvoice.htm";
            document.forms[0].submit();
        }
    </script>
</head>
<body>
<nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-primary">
    <spring:url value="/resources/images/Poseidon_Menu.png" var="posIcon2" />
    <a class="navbar-brand" href="#" onclick="javascript:goToHome();"><span><img src="${posIcon2}" width="30" height="30" class="d-inline-block align-top" alt="Poseidon"/></span>Poseidon</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <c:choose>
                <c:when test="${pageContext.request.userPrincipal.name != null}">
                    <li class='nav-item' id='user'><a href='#' class='nav-link' onclick='javascript:listMe();' >User<span class="sr-only">(current)</span></a></li>
                </c:when>
            </c:choose>
            <li class="nav-item" id="makeme"><a class="nav-link" href="#" onclick="javascript:MakeMe();" >Make</a></li>
            <li class="nav-item" id="customermgt"><a class="nav-link" href="#" onclick="javascript:fetchCustomers();" >Customer</a></li>
            <li class="nav-item" id="companymgt"><a class="nav-link" href="#" onclick="javascript:fetchTerms();" >Company</a></li>
            <li class="nav-item" id="txnmgt"><a class="nav-link" href="#" onclick="javascript:fetchTransactions();" >Transactions</a></li>
            <li class="nav-item" id="invmgt"><a class="nav-link" href="#" onclick="javascript:fetchInvoice();" >Invoice</a></li>
            <li class="nav-item" id="reportmgt"><a class="nav-link" href="#" onclick="javascript:fetchReport();" >Report</a></li>
        </ul>
        <ul class="navbar-nav my-2 my-lg-0">
            <li class="nav-item dropdown">
                <c:choose>
                    <c:when test="${pageContext.request.userPrincipal.name != null}">
                        <a href='#' id='dropdownMenu1' class='nav-link dropdown-toggle' role="button" data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>${pageContext.request.userPrincipal.name} </a>
                    </c:when>
                    <c:otherwise>
                        <a href='#' id='dropdownMenu1' class='nav-link dropdown-toggle' role="button" data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>Unknown User</a>
                    </c:otherwise>
                </c:choose>
                <div class="dropdown-menu" aria-labelledby="dropdownMenu1">
                    <a href="#" class="dropdown-item" onclick="javascript:LogMeOut();">Log Out</a>
                </div>
            </li>
        </ul>
    </div><!--/.nav-collapse -->
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</nav>

<script src="/js/jquery-3.2.1.min.js"></script>
<script src="/js/popper.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/jquery-ui.min.js"></script>
<script>
    $(document).ready(function()
    {
        //Handles menu drop down
        $('.dropdown-menu').find('form').click(function (e) {
            e.stopPropagation();
        });
    });
</script>
</body>
</html>
