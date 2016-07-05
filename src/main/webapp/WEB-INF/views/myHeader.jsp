<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Suraj">
    <spring:url value="/resources/images/Poseidon_Ico.ico" var="posIcon" />
    <link rel="shortcut icon" href="${posIcon}" >
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome-ie7.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
        function listMe(){
            document.forms[0].action="<%=request.getContextPath()%>"+"/user/ListAll.htm";
            document.forms[0].submit();
        }

        function goToHome(){
            document.forms[0].action="<%=request.getContextPath()%>"+"/user/ToHome.htm";
            document.forms[0].submit();
        }

        function LogMeOut(){
            document.forms[0].action="<%=request.getContextPath()%>"+"/user/LogMeOut.htm";
            document.forms[0].submit();
        }

        function MakeMe(){
            document.forms[0].action="<%=request.getContextPath()%>" + "/make/ModelList.htm";
            document.forms[0].submit();
        }

        function fetchCustomers(){
            document.forms[0].action="<%=request.getContextPath()%>" + "/customer/List.htm";
            document.forms[0].submit();
        }

        function fetchTerms(){
            document.forms[0].action="<%=request.getContextPath()%>" + "/company/List.htm";
            document.forms[0].submit();
        }

        function fetchTransactions(){
            document.forms[0].action="<%=request.getContextPath()%>" + "/txs/List.htm";
            document.forms[0].submit();
        }

        function fetchReport(){
            document.forms[0].action="<%=request.getContextPath()%>" + "/reports/List.htm";
            document.forms[0].submit();
        }

        function fetchInvoice(){
            document.forms[0].action="<%=request.getContextPath()%>" + "/invoice/ListInvoice.htm";
            document.forms[0].submit();
        }
    </script>
</head>
<body>
<div class="navbar-wrapper">
    <nav class="navbar navbar-inverse navbar-static-top">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <spring:url value="/resources/images/Poseidon_Ico_NEW.png" var="posIcon2" />
        <a class="navbar-brand" href="#"onclick="javascript:goToHome();" ><img src="${posIcon2}" /><p>Poseidon</p></a>
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav">
                <script type="text/javascript">
                    if ( document.forms[0].loggedInRole != null
                            && document.forms[0].loggedInRole.value != null
                            && document.forms[0].loggedInRole.value == 'ADMIN'){
                        document.write("<li id='user'><a href='#' onclick='javascript:listMe();' >User</a></li>");
                    }
                </script>
                <li id="makeme"><a href="#" onclick="javascript:MakeMe();" >Make</a></li>
                <li id="customermgt"><a href="#" onclick="javascript:fetchCustomers();" >Customer</a></li>
                <li id="companymgt"><a href="#" onclick="javascript:fetchTerms();" >Company</a></li>
                <li id="txnmgt"><a href="#" onclick="javascript:fetchTransactions();" >Transactions</a></li>
                <li id="invmgt"><a href="#" onclick="javascript:fetchInvoice();" >Invoice</a></li>
                <li id="reportmgt"><a href="#" onclick="javascript:fetchReport();" >Report</a></li>
            </ul>
            <ul class="nav navbar-nav pull-right">
                <li class="dropdown">
                    <script type="text/javascript">
                        if ( document.forms[0].loggedInUser != null
                                && document.forms[0].loggedInUser.value != null
                                && document.forms[0].loggedInUser.value.length > 0){
                            document.write("<a href='#' class='dropdown-toggle' data-toggle='dropdown'>"+document.forms[0].loggedInUser.value+"<b class='caret'></b></a>");
                        } else {
                            document.write("<a href='#' class='dropdown-toggle' data-toggle='dropdown'>Unknown User<b class='caret'></b></a>");
                        }
                    </script>
                    <ul class="dropdown-menu">
                        <li><a href="#" onclick="javascript:LogMeOut();">Log Out</a></li>
                    </ul>
                </li>
            </ul>
        </div><!--/.nav-collapse -->
    </nav>
</div>
<script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
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