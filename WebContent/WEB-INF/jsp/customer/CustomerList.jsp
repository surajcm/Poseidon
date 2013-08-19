<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Customer List</title>
    <style type="text/css">
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
            document.getElementById('customermgt').className = "active";
        }

    </script>

</head>
<body onload="javascript:hideAlerts()">
<form:form method="POST" commandName="customerForm" name="customerForm" >
<input type="hidden" name="id" id="id" />
<form:hidden name="loggedInUser" path="loggedInUser" />
<form:hidden name="loggedInRole" path="loggedInRole" />
<%@include file="/WEB-INF/jsp/myHeader.jsp" %>
<div class="container">
    <div class="wrap">
        <div class="panel panel-primary">
            <div class="panel-heading">Search Customers</div>
            <table>
                <tr>
                    <td>
                        <label for="customerId" class="control-label">
                            Customer Id :
                        </label>
                    </td>
                    <td>
                        <form:input cssClass="form-control" path="searchCustomerVO.customerId" id="customerId"/>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <label for="customerName" class="control-label">
                            Customer Name :
                        </label>
                    </td>
                    <td>
                        <form:input cssClass="form-control" path="searchCustomerVO.customerName" id="customerName"/>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <label for="mobile" class="control-label">
                            Mobile :
                        </label>
                    </td>
                    <td>
                        <form:input cssClass="form-control" path="searchCustomerVO.mobile" id="mobile"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="8">&nbsp;</td>
                    <td>
                        <label for="includes" class="control-label">
                            <spring:message code="user.includes" text="Includes"/>
                            <form:checkbox path="searchCustomerVO.includes" id="includes" value=""/>
                        </label>
                    </td>
                    <td>
                        <label for="startsWith" class="control-label">
                            <spring:message code="user.startsWith" text="Starts with"/>
                            <form:checkbox path="searchCustomerVO.startsWith" id="startsWith" value=""/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td colspan="10">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="8">&nbsp;</td>
                    <td>
                        <input class="btn btn-primary"
                               value="<spring:message code="poseidon.search" text="Search" />"
                               type="button" onclick="javascript:search()"/>
                    </td>
                    <td>
                        <input class="btn btn-primary"
                               value="<spring:message code="poseidon.clear" text="Clear" />"
                               type="button" onclick="javascript:clearOut()"/>
                    </td>
                </tr>
            </table>
        </div>
        <c:if test="${customerForm.statusMessage!=null}">
            <div class="alert alert-<c:out value="${customerForm.statusMessageType}"/>">
                <a class="close" data-dismiss="alert" href="#" aria-hidden="true">x</a>
                <c:out value="${customerForm.statusMessage}"/>
            </div>
        </c:if>
        <div class="panel panel-primary">
            <div class="panel-heading">Customer Details</div>
            <table id='myTable' class="table table-bordered table-striped table-hover">
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
                        <input class="btn btn-primary" value="Add New Customer" type="button" onclick="javascript:addCust()"/>
                        <input class="btn btn-primary" value="Edit Customer" type="button" onclick="javascript:editCust()"/>
                        <input class="btn btn-primary" value="Delete Customer" type="button" onclick="javascript:deleteCust()"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div>
        </form:form>

</body>
</html>
