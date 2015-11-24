<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/images/Poseidon_Ico.ico" >
    <link href="../css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="../css/bootstrap-responsive.css" rel="stylesheet" type="text/css">
    <link href="../css/bootstrap-theme.css" rel="stylesheet" type="text/css">
    <link href="../css/font-awesome.css" rel="stylesheet" type="text/css">
    <link href="../css/font-awesome-ie7.css" rel="stylesheet" type="text/css">
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
    <div class="navbar navbar-inverse navbar-static-top">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#"onclick="javascript:goToHome();" ><img src="<%=request.getContextPath()%>/images/Poseidon_Ico_NEW.png" />Poseidon</a>
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
                        }else {
                            document.write("<a href='#' class='dropdown-toggle' data-toggle='dropdown'>Unknown User<b class='caret'></b></a>");
                        }
                    </script>
                    <ul class="dropdown-menu">
                        <li><a href="#" onclick="javascript:LogMeOut();">Log Out</a></li>
                    </ul>
                </li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>
<script src="../js/jquery-latest.js"></script>
<script src="../js/bootstrap.js"></script>
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