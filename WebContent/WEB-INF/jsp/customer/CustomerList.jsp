<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Customer List</title>
    <link rel="stylesheet" type="text/css" href="../css/mainStyles.css" />
    <!--link rel="stylesheet" type="text/css" href="../css/bootstrap.css" /-->
    <link rel="stylesheet" type="text/css" href="../css/ui-lightness/jquery-ui-1.8.21.custom.css"/>
    <script type="text/javascript" src="../js/jquery-1.7.2.min.js" language="javascript" ></script>
    <script type="text/javascript" src="../js/jquery-ui-1.8.21.custom.min.js" language="javascript" ></script>
    <style type="text/css">

        .info, .success, .error {
            border: 1px solid;
            margin: 10px 0px;
            padding: 15px 10px 15px 50px;
            background-repeat: no-repeat;
            background-position: 10px center;
        }

        .info {
            color: #00529B;
            background-color: #BDE5F8;
            background-image: url( "<%=request.getContextPath()%>/images/Info.png" );
        }

        .success {
            color: #4F8A10;
            background-color: #DFF2BF;
            background-image: url( '<%=request.getContextPath()%>/images/Success.png' );
        }

        .error {
            color: #D8000C;
            background-color: #FFBABA;
            background-image: url( '<%=request.getContextPath()%>/images/Error.png' );
        }
		
		.textfieldMyStyle {
			border:3px double #CCCCCC;
			width: 200px;
			height:20px;
		}
        .foottable {
            margin:auto;
            top:50%;
            left:50%;
        }
		fieldset {
			text-align:right;
		}
		
		table {
			margin:auto;
			top:50%;
			left:50%;
		}
		</style>
        <script type="text/javascript">
            function addCust(){
                document.forms[0].action = "addCust.htm";
                document.forms[0].submit();
            }

            function search(){
                if(document.getElementById("customerId").value == ""
                        || isNumber(document.getElementById("customerId").value)) {
                    document.forms[0].action = "searchCustomer.htm";
                    document.forms[0].submit();
                }else{
                    alert("Incorrect customerId format found, Please update the field with a numeric value");
                }
            }

            function isNumber(n) {
                return !isNaN(parseFloat(n)) && isFinite(n);
            }

            function clearOut(){
                document.getElementById("customerId").value = "";
                document.getElementById("customerName").value = "";
                document.getElementById("mobile").value = "";
                document.getElementById('includes').checked = false;
                document.getElementById('startsWith').checked = false;
            }
            //validation before edit 
			function editCust(){
				var check ='false';
			    var count = 0;
                // get all check boxes
			    var checks = document.getElementsByName('checkField');
			    if(checks){
			        //if total number of rows is one
			        if(checks.checked){
			           editRow();
			        }else{
			           for(var i = 0 ; i < checks.length ; i++ ) {
			               if(checks[i].checked){
			                   check = 'true';
			                   count = count + 1;
			               }
			           }
                        //check for validity
                        if(check = 'true'){
                            if(count == 1){
                                editRow();
                            }else{
                                alert(" Only one row can be edited at a time, please select one row ");
                            }
                        }else{
                            alert(" No rows selected, please select one row ");
                        }
			        }
			    }
			}

			//real edit
			function editRow(){
	            var userRow;
                var checks = document.getElementsByName('checkField');
                if(checks.checked){
                    userRow = document.getElementById("myTable").rows[0];
                    document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
                    document.forms[0].action="editCust.htm";
                    document.forms[0].submit();
                }else{
                    for(var i = 0; i < checks.length ; i++){
                        if(checks[i].checked) {
                            userRow = document.getElementById("myTable").rows[i+1];
                        }
                    }
                    document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
                    document.forms[0].action="editCust.htm";
                    document.forms[0].submit();
                }
            }

            // delete
			function deleteCust(){
			    var check ='false';
			    var count = 0;
                // get all check boxes
			    var checks = document.getElementsByName('checkField');
			    if(checks){
			        //if total number of rows is one
			        if(checks.checked){
			           deleteRow();
			        }else{
			           for(var i = 0 ; i < checks.length ; i++ ) {
			               if(checks[i].checked){
			                   check = 'true';
			                   count = count + 1;
			               }
			           }
                        //check for validity
                        if(check = 'true'){
                            if(count == 1){
                                deleteRow();
                            }else{
                                alert(" Only one row can be deleted at a time, please select one row ");
                            }
                        }else{
                            alert(" No rows selected, please select one row ");
                        }
			        }
			    }
			}

            //code to delete
			function deleteRow(){
			    var answer = confirm(" Are you sure you wanted to delete the user ");
			    if(answer){
			        //if yes then delete
			        var userRow;
                    var checks = document.getElementsByName('checkField');
                    if(checks.checked){
                        userRow = document.getElementById("myTable").rows[0];
                        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
                        document.forms[0].action="deleteCust.htm";
				        document.forms[0].submit();
                    }else{
                        for(var i = 0; i < checks.length ; i++){
                            if(checks[i].checked) {
                                userRow = document.getElementById("myTable").rows[i+1];
                            }
                        }
                        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
                        document.forms[0].action="deleteCust.htm";
                        document.forms[0].submit();
                    }
			    }

			}
            //preventing multiple checks
			function checkCall(e){
			    var min = e.value;
			    var checks = document.getElementsByName('checkField');
			    for(var i = 0; i < checks.length ; i++){
			        if(checks[i].value != min) {
			            checks[i].checked = false;
			        }
			    }
			}
            function hideAlerts(){
                var options = {};
                $( "#effect" ).hide( "blind", options, 8000);
            }
            
        </script>

  </head>
  <body onload="javascript:hideAlerts()">
  <form:form method="POST" commandName="customerForm" name="customerForm" >
        <input type="hidden" name="id" id="id" />
        <form:hidden name="loggedInUser" path="loggedInUser" />
        <form:hidden name="loggedInRole" path="loggedInRole" />
        <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
        <div id="content">
                <div class="wrap">
                    <fieldset>
                        <legend>Search Customers</legend>
                        <table>
                            <tr>
                                <td>
                                    <label for="customerId">
                                        Customer Id :
                                    </label>
                                </td>
                                <td>
                                    <form:input path="searchCustomerVO.customerId" cssClass="textfieldMyStyle" id="customerId"/>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="customerName">
                                        Customer Name :
                                    </label>
                                </td>
                                <td>
                                    <form:input path="searchCustomerVO.customerName" cssClass="textfieldMyStyle" id="customerName"/>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="mobile">
                                        Mobile :
                                    </label>
                                </td>
                                <td>
                                    <form:input path="searchCustomerVO.mobile" cssClass="textfieldMyStyle" id="mobile"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="8">&nbsp;</td>
                                <td>
                                    <label for="includes">
                                        <spring:message code="user.includes" text="Includes"/>
                                        <form:checkbox path="searchCustomerVO.includes" cssStyle="vertical-align:middle"
                                                       id="includes" value=""/>
                                    </label>
                                </td>
                                <td>
                                    <label for="startsWith">
                                        <spring:message code="user.startsWith" text="Starts with"/>
                                        <form:checkbox path="searchCustomerVO.startsWith" cssStyle="vertical-align:middle"
                                                       id="startsWith" value=""/>
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="10">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="8">&nbsp;</td>
                                <td>
                                    <input class="btn" value="<spring:message code="poseidon.search" text="Search" />"
                                           type="button" onclick="javascript:search()"/>
                                </td>
                                <td>
                                    <input class="btn" value="<spring:message code="poseidon.clear" text="Clear" />"
                                           type="button" onclick="javascript:clearOut()"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <c:if test="${customerForm.statusMessage!=null}">
                        <div  id="effect" class="<c:out value="${customerForm.statusMessageType}"/>">
                            <c:out value="${customerForm.statusMessage}"/>
                        </div>
                    </c:if>
                    <fieldset>
                        <legend>Customer Details</legend>
                        <table border="2" id="myTable">
                            <thead>
                            <tr>
                                <th>&nbsp;</th>
                                <th><spring:message code="poseidon.id" text="id"/></th>
                                <th>Name</th>
                                <th>Address Line1</th>
                                <th>Address Line21</th>
                                <th>Phone</th>
                                <th>Mobile</th>
                                <th>Email</th>
                                <th>Contact Person 1</th>
                                <th>Contact Phone No1</th>
                                <th>Contact Person 1</th>
                                <th>Contact Phone No1</th>
                                <th>Notes</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${customerForm.customerVOs}" var="iterationCust">
                                <tr>
                                    <td><input type="checkbox" name="checkField" onclick="javascript:checkCall(this)"
                                               value="<c:out value="${iterationCust.customerId}" />"/></td>
                                    <td><c:out value="${iterationCust.customerId}"/></td>
                                    <td><c:out value="${iterationCust.customerName}"/></td>
                                    <td><c:out value="${iterationCust.address1}"/></td>
                                    <td><c:out value="${iterationCust.address2}"/></td>
                                    <td><c:out value="${iterationCust.phoneNo}"/></td>
                                    <td><c:out value="${iterationCust.mobile}"/></td>
                                    <td><c:out value="${iterationCust.email}"/></td>
                                    <td><c:out value="${iterationCust.contactPerson1}"/></td>
                                    <td><c:out value="${iterationCust.contactMobile1}"/></td>
                                    <td><c:out value="${iterationCust.contactPerson2}"/></td>
                                    <td><c:out value="${iterationCust.contactMobile2}"/></td>
                                    <td><c:out value="${iterationCust.notes}"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <table>
                            <tr>
                                <td>
                                    <br/>
                                    <br/>
                                    <input class="btn" value="Add New Customer" type="button" onclick="javascript:addCust()"/>
                                    <input class="btn" value="Edit Customer" type="button" onclick="javascript:editCust()"/>
                                    <input class="btn" value="Delete Customer" type="button" onclick="javascript:deleteCust()"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </div>
            <div>
  </form:form>

  </body>
</html>
