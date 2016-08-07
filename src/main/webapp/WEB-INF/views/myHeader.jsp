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
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.3/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.3/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.3/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
    <!--link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/0.5.8/css/bootstrap-material-design.min.css"-->
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome-ie7.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
        .foottable {
            margin:auto;
            top:50%;
            left:50%;
        }
        body {
            padding-top: 90px;
            font-family: 'Lato',sans-serif;
        }
        /* responsive nav stacked liked 3.x */
        @media(max-width:48em) {
            .navbar .navbar-nav>.nav-item {
                float: none;
                margin-left: .1rem;
            }
            .navbar .navbar-nav {
                float:none !important;
            }
            .navbar .collapse.in, .navbar .collapsing  {
                clear:both;
            }
        }

        .navbar-toggle:focus,
        .navbar-toggle:active {
            outline: 0;
        }
    </style>
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
<nav class="navbar navbar-fixed-top navbar-dark bg-primary">
    <div class="container">
        <button class="navbar-toggler hidden-sm-up" type="button" data-toggle="collapse" data-target="#collapsingNavbar">
            ☰
        </button>
        <div class="collapse navbar-toggleable-xs" id="collapsingNavbar">
            <spring:url value="/resources/images/Poseidon_Ico_NEW2.png" var="posIcon2" />
            <a class="navbar-brand" href="#" onclick="javascript:goToHome();"><span><img src="${posIcon2}"alt="Poseidon"/></span>Poseidon</a>
            <ul class="nav navbar-nav">
                <script type="text/javascript">
                    if ( document.forms[0].loggedInRole != null
                            && document.forms[0].loggedInRole.value != null
                            && document.forms[0].loggedInRole.value == 'ADMIN'){
                        document.write("<li class='nav-item' id='user'><a href='#' class='nav-link' onclick='javascript:listMe();' >User</a></li>");
                    }
                </script>
                <li class="nav-item" id="makeme"><a class="nav-link" href="#" onclick="javascript:MakeMe();" >Make</a></li>
                <li class="nav-item" id="customermgt"><a class="nav-link" href="#" onclick="javascript:fetchCustomers();" >Customer</a></li>
                <li class="nav-item" id="companymgt"><a class="nav-link" href="#" onclick="javascript:fetchTerms();" >Company</a></li>
                <li class="nav-item" id="txnmgt"><a class="nav-link" href="#" onclick="javascript:fetchTransactions();" >Transactions</a></li>
                <li class="nav-item" id="invmgt"><a class="nav-link" href="#" onclick="javascript:fetchInvoice();" >Invoice</a></li>
                <li class="nav-item" id="reportmgt"><a class="nav-link" href="#" onclick="javascript:fetchReport();" >Report</a></li>
            </ul>
            <ul class="nav navbar-nav pull-xs-right">
                <li class="nav-item">
                    <div class="dropdown">
                        <script type="text/javascript">
                            if ( document.forms[0].loggedInUser != null
                                    && document.forms[0].loggedInUser.value != null
                                    && document.forms[0].loggedInUser.value.length > 0){
                                document.write("<a href='#' id='dropdownMenu1' class='nav-link dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>"+document.forms[0].loggedInUser.value+"</a>");
                            } else {
                                document.write("<a href='#' id='dropdownMenu1' class='nav-link dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>Unknown User</a>");
                            }
                        </script>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenu1">
                            <a href="#" class="dropdown-item" onclick="javascript:LogMeOut();">Log Out</a>
                        </div>
                    </div>
                </li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>
<script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.3/js/bootstrap.min.js"></script>
<script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
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
