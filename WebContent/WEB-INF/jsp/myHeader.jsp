<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/images/Poseidon_Ico.ico" >
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="../css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="../css/bootstrap-responsive.css" rel="stylesheet" type="text/css">

    <!-- HTML5 shim for IE backwards compatibility -->
    <!--[if lt IE 9]>
    <script src="js/html5.js"></script>
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
<nav class="navbar navbar-inverse">
    <div class="navbar-inner">
        <a id="hometab" class="brand" href="#" onclick="javascript:goToHome();" >Poseidon</a>
        <ul class="nav">
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
        <div class="pull-right">
            <ul class="nav pull-right">
                <li class="dropdown">
                    <a href="#" class="pull-right" onclick="javascript:LogMeOut();" >Log Out</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<table id="menuimg" border="0"  width="100%" >
    <tr>
        <td colspan="2">
            <table width="100%">
                <tr>
                    <td width="20%">
                        <img src="<%=request.getContextPath()%>/images/Poseidon.png" style="margin:0px; width:150px; height:50px"/>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<script src="js/jquery-latest.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-dropdown.js"></script>

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