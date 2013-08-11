<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Invoice List</title>
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

    table {
        margin:auto;
        top:50%;
        left:50%;
    }
</style>
<script type="text/javascript">
    function addInvoice(){
        /*if(document.getElementById('amount').value.length == 0){
         document.getElementById('amount').value = "0.0";
         }*/
        document.forms[0].action = "addInvoice.htm";
        document.forms[0].submit();
    }
    function search() {
        /*if(document.getElementById('amount').value.length == 0){
         document.getElementById('amount').value = "0.0";
         }*/
        document.forms[0].action = "SearchInvoice.htm";
        document.forms[0].submit();
    }
    function clearOut() {
        document.getElementById('invoiceId').value = "";
        document.getElementById('description').value = "";
        document.getElementById('serialNo').value = "";
        document.getElementById('tagNo').value = "";
        document.getElementById('amount').value = "";
        document.getElementById('greater').checked = false;
        document.getElementById('greater').checked = false;
        document.getElementById('lesser').checked = false;
        document.getElementById('startsWith').checked = false;
    }
    //validation before edit
    function editMe() {
        var check = 'false';
        var count = 0;
        // get all check boxes
        var checks = document.getElementsByName('checkField');
        if (checks) {
            //if total number of rows is one
            if (checks.checked) {
                editRow();
            } else {
                for (var i = 0; i < checks.length; i++) {
                    if (checks[i].checked) {
                        check = 'true';
                        count = count + 1;
                    }
                }
                //check for validity
                if (check = 'true') {
                    if (count == 1) {
                        editRow();
                    } else {
                        alert(" Only one row can be edited at a time, please select one row ");
                    }
                } else {
                    alert(" No rows selected, please select one row ");
                }
            }
        }
    }

    //real edit
    function editRow() {
        var userRow;
        var checks = document.getElementsByName('checkField');
        if (checks.checked) {
            userRow = document.getElementById("myTable").rows[0];
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            if(document.getElementById('amount').value.length == 0){
                document.getElementById('amount').value = "0.0";
            }
            document.forms[0].action = "EditInvoice.htm";
            document.forms[0].submit();
        } else {
            for (var i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    userRow = document.getElementById("myTable").rows[i + 1];
                }
            }
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            if(document.getElementById('amount').value.length == 0){
                document.getElementById('amount').value = "0.0";
            }
            document.forms[0].action = "EditInvoice.htm";
            document.forms[0].submit();
        }
    }

    // delete
    function deleteInvoice() {
        var check = 'false';
        var count = 0;
        // get all check boxes
        var checks = document.getElementsByName('checkField');
        if (checks) {
            //if total number of rows is one
            if (checks.checked) {
                deleteRow();
            } else {
                for (var i = 0; i < checks.length; i++) {
                    if (checks[i].checked) {
                        check = 'true';
                        count = count + 1;
                    }
                }
                //check for validity
                if (check = 'true') {
                    if (count == 1) {
                        deleteRow();
                    } else {
                        alert(" Only one row can be deleted at a time, please select one row ");
                    }
                } else {
                    alert(" No rows selected, please select one row ");
                }
            }
        }
    }

    //code to delete a user
    function deleteRow() {
        var answer = confirm(" Are you sure you wanted to delete the Txn ");
        if (answer) {
            //if yes then delete
            var userRow;
            var checks = document.getElementsByName('checkField');
            if (checks.checked) {
                userRow = document.getElementById("myTable").rows[0];
                document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
                if(document.getElementById('amount').value.length == 0){
                    document.getElementById('amount').value = "0.0";
                }
                document.forms[0].action = "DeleteInvoice.htm";
                document.forms[0].submit();
            } else {
                for (var i = 0; i < checks.length; i++) {
                    if (checks[i].checked) {
                        userRow = document.getElementById("myTable").rows[i + 1];
                    }
                }
                document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
                if(document.getElementById('amount').value.length == 0){
                    document.getElementById('amount').value = "0.0";
                }
                document.forms[0].action = "DeleteInvoice.htm";
                document.forms[0].submit();
            }
        }

    }
    //preventing multiple checks
    function checkCall(e) {
        var min = e.value;
        var checks = document.getElementsByName('checkField');
        for (var i = 0; i < checks.length; i++) {
            if (checks[i].value != min) {
                checks[i].checked = false;
            }
        }
    }

    function hideAlerts(){
        document.getElementById('invmgt').className = "active";
        var options = {};
        $( "#effect" ).hide( "blind", options, 8000);
    }

</script>
</head>
<body onload="javascript:hideAlerts()">
<form:form method="POST" commandName="invoiceForm" name="invoiceForm" >
    <form:hidden name="loggedInUser" path="loggedInUser" />
    <form:hidden name="loggedInRole" path="loggedInRole" />
    <input type="hidden" name="id" id="id"/>
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <div id="content">
        <div class="wrap">
            <div class="panel panel-primary">
                <div class="panel-heading">Search Invoice</div>
                <table>
                    <tr>
                        <td>
                            <label for="id" class="control-label">
                                Invoice Id :
                            </label>
                        </td>
                        <td>
                            <form:input cssClass="form-control" path="searchInvoiceVO.id" id="invoiceId"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="Description" class="control-label">
                                Description :
                            </label>
                        </td>
                        <td>
                            <form:input cssClass="form-control" path="searchInvoiceVO.description" id="description"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="serialNo" class="control-label">
                                Serial No :
                            </label>
                        </td>
                        <td>
                            <form:input cssClass="form-control" path="searchInvoiceVO.serialNo" id="serialNo"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="tagNo" class="control-label">
                                Tag No :
                            </label>
                        </td>
                        <td>
                            <form:input cssClass="form-control" path="searchInvoiceVO.tagNo"  id="tagNo"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="amount" class="control-label">
                                Amount :
                            </label>
                        </td>
                        <td>
                            <form:input cssClass="form-control" path="searchInvoiceVO.amount"  id="amount"/>
                        </td>
                        <td colspan="3">&nbsp;</td>
                        <td>
                            <label for="greater" class="control-label">
                                Greater than & Equal
                                <form:checkbox path="searchInvoiceVO.greater" id="greater" value=""/>
                            </label>
                        </td>
                        <td colspan="3">&nbsp;</td>
                        <td>
                            <label for="lesser" class="control-label">
                                Lesser than & Equal
                                <form:checkbox path="searchInvoiceVO.lesser" id="lesser" value=""/>
                            </label>
                        </td>
                        <td colspan="4">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <label for="includes" class="control-label">
                                <spring:message code="user.includes" text="Includes"/>
                                <form:checkbox path="searchInvoiceVO.includes" id="includes" value=""/>
                            </label>
                        </td>
                        <td colspan="3">&nbsp;</td>
                        <td>
                            <label for="startsWith" class="control-label">
                                <spring:message code="user.startsWith" text="Starts with"/>
                                <form:checkbox path="searchInvoiceVO.startsWith" id="startsWith" value=""/>
                            </label>
                        </td>
                        <td colspan="3">&nbsp;</td>
                        <td>
                            <input class="btn btn-primary" value="<spring:message code="poseidon.search" text="Search" />"
                                   type="button" onclick="search()"/>
                        </td>
                        <td colspan="3">&nbsp;</td>
                        <td>
                            <input class="btn btn-primary" value="<spring:message code="poseidon.clear" text="Clear" />"
                                   type="button" onclick="clearOut()"/>
                        </td>
                    </tr>
                </table>
            </div>
            <c:if test="${invoiceForm.statusMessage!=null}">
                <div  id="effect" class="<c:out value="${invoiceForm.statusMessageType}"/>">
                    <c:out value="${invoiceForm.statusMessage}"/>
                </div>
            </c:if>
            <div class="panel panel-primary">
                <div class="panel-heading">Invoice Details</div>
                <table id='myTable' class="table table-bordered table-striped table-hover">
                    <thead>
                    <tr>
                        <th>&nbsp;</th>
                        <th>Invoice Id</th>
                        <th>Customer Name</th>
                        <th>Tag No</th>
                        <th>Item Description</th>
                        <th>Serial No</th>
                        <th>Total Amount</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${invoiceForm.invoiceVOs}" var="iterationInv">
                        <tr>
                            <td><input type="checkbox" name="checkField" onclick="checkCall(this)"
                                       value="<c:out value="${iterationInv.id}" />"/></td>
                            <td><c:out value="${iterationInv.id}"/></td>
                            <td><c:out value="${iterationInv.customerName}"/></td>
                            <td><c:out value="${iterationInv.tagNo}"/></td>
                            <td><c:out value="${iterationInv.description}"/></td>
                            <td><c:out value="${iterationInv.serialNo}"/></td>
                            <td><c:out value="${iterationInv.amount}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <table>
                    <tr>
                        <td>
                            <br/>
                            <br/>
                            <input class="btn btn-primary" value="Add New Invoice" type="button" onclick="addInvoice()"/>
                            <input class="btn btn-primary" value="Edit Invoice" type="button" onclick="editMe()"/>
                            <input class="btn btn-primary" value="Delete Invoice" type="button" onclick="deleteInvoice()"/>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</form:form>
</body>
</html>
