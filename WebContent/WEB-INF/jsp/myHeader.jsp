<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
		menuFirstLine {
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
                alert("Going to MakeManagement Page");
            }

            function fetchCustomers(){
                alert("Going to Customer Management Page");
            }

            function fetchTerms(){
                alert("Going to Terms and conditions and Custemer details Page");
            }

            function fetchTransactions(){
                document.forms[0].action="<%=request.getContextPath()%>" + "/txs/List.htm";
				document.forms[0].submit();
            }

            function fetchReport(){
                alert("Going to Reports Page");
            }
		</script>
	</head>
    <body>
        <table id="menuimg" border="0"  width="100%" style="font-size: .70em;" >
            <tr>
				<td colspan="2">
                <%
                    String DATE_FORMAT = "dd-MMM-yyyy";
                    String TIME_NOW = "HH:mm";
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat(DATE_FORMAT);
                    java.text.SimpleDateFormat fmt_time = new java.text.SimpleDateFormat(TIME_NOW);
                %>
				</td>
			</tr>
			<tr>
                <td align="middle" colspan="2" >
					<label class="menuFirstLine">Welcome </label>
					<label class="menuFirstLine"><c:out value="${forms[0].loggedInUser}" /></label>|
					<label class="menuFirstLine"><%=fmt.format(cal.getTime()) %></label>|
					<label class="menuFirstLine"><%=fmt_time.format(cal.getTime()) %></label>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<br/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<hr/>
				</td>
			</tr>
			<tr>
				<c:set var="roleFinder" value="admin" />
				<c:choose>
					<c:when test="${forms[0].loggedInRole eq roleFinder}">
						<td>
							<label for="Home" style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
                            <label onclick="javascript:goToHome();" for="Home" style="font-weight: bold;color:white;font-size: .70em;"  onMouseOver="this.style.cursor='pointer'"  >Home</label>
							<label for="Home" style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
							<label onclick="javascript:listMe();" for="User" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >UserManagement</label>
                            <label for="Home" style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
							<label onclick="javascript:MakeMe();" for="User" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >MakeManagement</label>
                            <label for="Home" style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
							<label onclick="javascript:fetchCustomers();" for="User" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >CustomerManagement</label>
                            <label for="Home" style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
							<label onclick="javascript:fetchTerms();" for="User" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >CompanyTermsManagement</label>
                            <label for="Home" style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
							<label onclick="javascript:fetchTransactions();" for="User" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >TransactionsManagement</label>
                            <label for="Home" style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
							<label onclick="javascript:fetchReport();" for="User" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >ReportManagement</label>
                            <label for="Home" style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
						</td>
						<td align="right" height="0px"  onclick="javascript:LogMeOut();">
							<label for="help" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >Help</label>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<label for="Out" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >LogOut</label>
						</td>
					</c:when>
					<c:otherwise>
						<td>
							<label for="Home" style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
                            <label onclick="javascript:MakeMe();" for="User" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >MakeManagement</label>
                            <label for="Home" style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
							<label onclick="javascript:fetchCustomers();" for="User" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >CustomerManagement</label>
                            <label for="Home" style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
							<label onclick="javascript:fetchTerms();" for="User" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >CompanyTermsManagement</label>
                            <label for="Home" style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
							<label onclick="javascript:fetchTransactions();" for="User" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >TransactionsManagement</label>
                            <label for="Home" style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
							<label onclick="javascript:fetchReport();" for="User" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >ReportManagement</label>
                            <label for="Home" style="font-weight: bold;color:white;font-size: .90em;"  onMouseOver="this.style.cursor='pointer'"  >|</label>
						</td>
						<td align="right" height="0px"  onclick="javascript:LogMeOut();">
							<label for="help" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >Help</label>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<label for="Out" style="font-weight: bold;color:white;font-size: .70em;" onMouseOver="this.style.cursor='pointer'" >LogOut</label>
						</td>
					</c:otherwise>
				</c:choose>
            </tr>
			<tr>
				<td colspan="2">
					<hr/>
				</td>
			</tr>
        </table>
    </body>
</html>