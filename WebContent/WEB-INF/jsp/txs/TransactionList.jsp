<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Transaction List</title>
    <link rel="stylesheet" type="text/css" href="../css/mainStyles.css"/>
    <script type="text/javascript">
        function addNew() {
            document.forms[0].action = "AddTxn.htm";
            document.forms[0].submit();
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
        function search() {
            //if()
            document.forms[0].action = "SearchTxn.htm";
            document.forms[0].submit();
        }
        function isNumber(n) {
            return !isNaN(parseFloat(n)) && isFinite(n);
        }

        function clear() {
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
                document.forms[0].action = "EditTxn.htm";
                document.forms[0].submit();
            } else {
                for (var i = 0; i < checks.length; i++) {
                    if (checks[i].checked) {
                        userRow = document.getElementById("myTable").rows[i + 1];
                    }
                }
                document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
                document.forms[0].action = "EditTxn.htm";
                document.forms[0].submit();
            }
        }

        // delete
        function deleteTxn() {
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
                    document.forms[0].action = "DeleteTxn.htm";
                    document.forms[0].submit();
                } else {
                    for (var i = 0; i < checks.length; i++) {
                        if (checks[i].checked) {
                            userRow = document.getElementById("myTable").rows[i + 1];
                        }
                    }
                    document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
                    document.forms[0].action = "DeleteTxn.htm";
                    document.forms[0].submit();
                }
            }

        }


    </script>
</head>
<body style="background: #A9A9A9 ;">
<form:form method="POST" commandName="transactionForm" name="transactionForm" action="List.htm">
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <input type="hidden" name="id" id="id"/>

    <div id="content">
        <div class="wrap">
            <fieldset style="text-align:right;">
                <legend>Search Transactions</legend>
                <table style="margin:auto;top:50%;left:50%;">
                    <tr>
                        <td>
                            <label for="TagNo" style="font-size: .70em;">
                                Tag No :
                            </label>
                        </td>
                        <td>
                            <form:input path="searchTransaction.TagNo" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="TagNo"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="CustomerName" style="font-size: .70em;">
                                Customer Name :
                            </label>
                        </td>
                        <td>
                            <form:input path="searchTransaction.CustomerName" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="CustomerName"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="date" style="font-size: .70em;">
                                Reported Date :
                            </label>
                        </td>
                        <td>
                            <input type="text" value="will be used for date"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="SerialNo" style="font-size: .70em;">
                                Serial No :
                            </label>
                        </td>
                        <td>
                            <form:input path="searchTransaction.SerialNo" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="SerialNo"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="makeName" style="font-size: .70em;">
                                Make :
                            </label>
                        </td>
                        <td>
                            <form:select id="makeName" path="searchTransaction.makeName" tabindex="1"
                                         onkeypress="handleEnter(event);"
                                         cssStyle="border:3px double #CCCCCC; width: 200px;height:28px;">
                                <form:options items="${transactionForm.makeVOs}"
                                              itemValue="Id" itemLabel="makeName"/>
                            </form:select>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="ModelName" style="font-size: .70em;">
                                Model Name :
                            </label>
                        </td>
                        <td>
                            <form:input path="searchTransaction.ModelName" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="ModelName"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="Status" style="font-size: .70em;">
                                Status :
                            </label>
                        </td>
                        <td>
                            <form:select id="Status" path="searchTransaction.Status"
                                         onkeypress="handleEnter(event);"
                                         cssStyle="border:3px double #CCCCCC; width: 200px;height:25px;">
                                <form:options items="${transactionForm.statusList}" />
                            </form:select>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="includes" style="font-size: .70em;">
                                <spring:message code="user.includes" text="Includes"/>
                                <form:checkbox path="searchTransaction.includes" cssStyle="vertical-align:middle" id="includes" value="" />
                            </label>
                        </td>
                        <td>
                            <label for="startswith" style="font-size: .70em;">
                                <spring:message code="user.startsWith" text="Starts with"/>
                                <form:checkbox path="searchTransaction.startswith" cssStyle="vertical-align:middle" id="startswith" value="" />
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="12">&nbsp;</td>
                        <td>
                            <input class="btn" value="<spring:message code="poseidon.search" text="Search" />"
                                   type="button" onclick="javascript:search()"/>
                        </td>
                        <td>
                            <input class="btn" value="<spring:message code="poseidon.clear" text="Clear" />"
                                   type="button" onclick="javascript:clear()"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <br/>
            <fieldset>
                <legend>Transaction Details</legend>
                <table border="2" id="myTable" style="font-size: .60em;">
                    <thead>
                    <tr>
                        <th><spring:message code="poseidon.id" text="id"/></th>
                        <th>TagNo</th>
                        <th>Customer Name</th>
                        <th>Reported Date</th>
                        <th>Make</th>
                        <th>Model</th>
                        <th>Serial No</th>
                        <th>Status</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach items="${transactionForm.transactionsList}" var="iterationTxn">
                        <tr>
                            <td><input type="checkbox" name="checkField" onclick="javascript:checkCall(this)"
                                       value="<c:out value="${iterationTxn.id}" />"/></td>
                            <td><c:out value="${iterationTxn.tagNo}"/></td>
                            <td><c:out value="${iterationTxn.customerName}"/></td>
                            <td><c:out value="${iterationTxn.dateReported}"/></td>
                            <td><c:out value="${iterationTxn.makeName}"/></td>
                            <td><c:out value="${iterationTxn.modelName}"/></td>
                            <td><c:out value="${iterationTxn.serialNo}"/></td>
                            <td><c:out value="${iterationTxn.status}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <table style="margin:auto;top:50%;left:50%;">
                    <tr>
                        <td>
                            <br/>
                            <br/>
                            <input class="btn" value="Add New Transaction" type="button" onclick="javascript:addNew()"/>
                            <input class="btn" value="Edit Transaction" type="button" onclick="javascript:editMe()"/>
                            <input class="btn" value="Delete Transaction" type="button"
                                   onclick="javascript:deleteTxn()"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>
    </div>

</form:form>
</body>
</html>