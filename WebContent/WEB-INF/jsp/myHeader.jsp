<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/images/Poseidon_Ico.ico" >
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style type="text/css">
        #menuimg {
            background-repeat: repeat-x;
            background-color: #A9A9A9;
            margin:auto;
            border-collapse:collapse;
        }

        div.menu {
            float: left;
            margin-left: 10px;
            font-size: 0.5em;
            font-family: Verdana, Arial, Helvetica, sans-serif;
            color:white
        }

        div.popup a:hover { background-color: #faa; }
        div.menu:hover div:first-child { border-bottom: none; }
        div.menu div.popup { display: none; }
        div.menu:hover div.popup {
            display: block;
            background-color: #99f;
        }
        div.menu div {
            width: 175px;
            background-color: #66f;
            padding: 5px;
            border: solid 2px blue;
        }

        #menuFirstLine {
            font-weight: bold;
            font-size: 08;
            font-family: Verdana, Arial, Helvetica, sans-serif;
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
<table id="menuimg" border="0"  width="100%" >
    <tr>
        <td colspan="2">
            <table width="100%">
                <tr>
                    <td width="20%">
                        <img src="<%=request.getContextPath()%>/images/Poseidon.png" style="margin:0px; width:150px; height:50px"/>
                        <%
                            String DATE_FORMAT = "dd-MMM-yyyy";
                            String TIME_NOW = "HH:mm";
                            java.util.Calendar cal = java.util.Calendar.getInstance();
                            java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat(DATE_FORMAT);
                            java.text.SimpleDateFormat fmt_time = new java.text.SimpleDateFormat(TIME_NOW);
                        %>
                    </td>
                    <td align="center" valign="middle" >
                        <label class="menuFirstLine">Welcome </label>
                        <label class="menuFirstLine">
                            <script type="text/javascript">
                                if(document.forms[0].loggedInUser != null) {
                                    document.write(document.forms[0].loggedInUser.value);
                                }
                            </script>
                        </label>|
                        <label class="menuFirstLine"><%=fmt.format(cal.getTime()) %></label>|
                        <label class="menuFirstLine"><%=fmt_time.format(cal.getTime()) %></label>
                    </td>
                    <td width="20%">
                        &nbsp;
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <hr/>
        </td>
    </tr>
    <tr>
        <td>
            <label style="font-weight: bold;color:white;"  onMouseOver="this.style.cursor='pointer'"  > | </label>
            <label onclick="javascript:goToHome();" style="font-weight: bold;color:white;font-size: .70em;"  onMouseOver="this.style.cursor='pointer'"  > Home </label>
            <script type="text/javascript">
                if ( document.forms[0].loggedInRole != null
                        && document.forms[0].loggedInRole.value != null
                        && document.forms[0].loggedInRole.value == 'ADMIN'){
                    document.write("<label style=\"font-weight: bold;color:white;font-size: .90em;\"  onMouseOver=\"this.style.cursor='pointer'\"  > | </label>");
                    document.write("<label onclick=\"javascript:listMe();\" style=\"font-weight: bold;color:white;font-size: .70em;\" onMouseOver=\"this.style.cursor='pointer'\" > UserManagement </label>");
                }
            </script>
            <label style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
            <label onclick="javascript:MakeMe();" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >MakeManagement</label>
            <label style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
            <label onclick="javascript:fetchCustomers();" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >CustomerManagement</label>
            <label style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
            <label onclick="javascript:fetchTerms();" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >CompanyManagement</label>
            <label style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
            <label onclick="javascript:fetchTransactions();" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >TransactionsManagement</label>
            <label style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
            <label onclick="javascript:fetchInvoice();" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >InvoiceManagement</label>
            <label style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
            <label onclick="javascript:fetchReport();" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >ReportManagement</label>
            <label style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
        </td>
        <td align="right" height="0px"  onclick="javascript:LogMeOut();">
            <label style="font-weight: bold;color:white;font-size: .90em;" onMouseOver="this.style.cursor='pointer'" >Help</label>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <label style="font-weight: bold;color:white;font-size: .90em;" onMouseOver="this.style.cursor='pointer'" >LogOut</label>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <hr/>
        </td>
    </tr>
</table>
</body>
</html>