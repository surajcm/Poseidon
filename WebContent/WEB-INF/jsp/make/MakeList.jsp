<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Make List</title>
    <link rel="stylesheet" type="text/css" href="../css/mainStyles.css"/>
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
    </style>
    <script type="text/javascript">

        function listAllModel() {
            document.forms[0].action = "ModelList.htm";
            document.forms[0].submit();
        }

        function addNewMake() {
            document.forms[0].action = "addMake.htm";
            document.forms[0].submit();
        }

        function clearOut(){
            document.getElementById("makeName").value = document.getElementById('makeName').options[0].value;
            document.getElementById('modelName').value ="";
            document.getElementById('includes').checked = false;
            document.getElementById('startswith').checked = false;
        }

        function search() {
            document.forms[0].action = "searchModel.htm";
            document.forms[0].submit();
        }

        function printMe() {
            document.forms[0].action = "printMake.htm";
            document.forms[0].submit();
        }

        //validation before edit
        function editMake() {
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
                document.forms[0].action = "editMake.htm";
                document.forms[0].submit();
            } else {
                for (var i = 0; i < checks.length; i++) {
                    if (checks[i].checked) {
                        userRow = document.getElementById("myTable").rows[i + 1];
                    }
                }
                document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
                document.forms[0].action = "editMake.htm";
                document.forms[0].submit();
            }
        }

        // delete
        function deleteMake() {
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

        //code to delete
        function deleteRow() {
            var answer = confirm(" Are you sure you wanted to delete the user ");
            if (answer) {
                //if yes then delete
                var userRow;
                var checks = document.getElementsByName('checkField');
                if (checks.checked) {
                    userRow = document.getElementById("myTable").rows[0];
                    document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
                    document.forms[0].action = "deleteMake.htm";
                    document.forms[0].submit();
                } else {
                    for (var i = 0; i < checks.length; i++) {
                        if (checks[i].checked) {
                            userRow = document.getElementById("myTable").rows[i + 1];
                        }
                    }
                    document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
                    document.forms[0].action = "deleteMake.htm";
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
            var options = {};
            $( "#effect" ).hide( "blind", options, 8000);
        }
    </script>

</head>
<body style="background: #A9A9A9 ;" onload="javascript:hideAlerts()">
<form:form method="POST" commandName="makeForm" name="makeForm">
    <input type="hidden" name="id" id="id"/>
    <form:hidden name="loggedInUser" path="loggedInUser"/>
    <form:hidden name="loggedInRole" path="loggedInRole"/>
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <div id="content">
        <div class="wrap">
            <fieldset style="text-align:right;">
                <legend>Search Model</legend>
                <table style="margin:auto;top:50%;left:50%;">
                    <tr>
                        <td>
                            <label for="makeName" style="font-size: .70em;">
                                Make Name :
                            </label>
                        </td>
                        <td>
                            <form:select id="makeName" path="searchMakeAndModelVO.makeName" tabindex="1"
                                         cssStyle="border:3px double #CCCCCC; width: 200px;height:25px;">
                                <form:option value="0" label="-- Select --"/>
                                <form:options items="${makeForm.makeVOs}"
                                              itemValue="id" itemLabel="makeName"/>
                            </form:select>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="modelName" style="font-size: .70em;">
                                Model Name :
                            </label>
                        </td>
                        <td>
                            <form:input path="searchMakeAndModelVO.modelName"
                                        cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="modelName"/>
                        </td>
                    <tr>
                    <tr>
                        <td colspan="2">
                            <label for="includes" style="font-size: .70em;">
                                <spring:message code="user.includes" text="Includes"/>
                                <form:checkbox path="searchMakeAndModelVO.includes" cssStyle="vertical-align:middle"
                                               id="includes" value=""/>
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td colspan="2">
                            <label for="startswith" style="font-size: .70em;">
                                <spring:message code="user.startsWith" text="Starts with"/>
                                <form:checkbox path="searchMakeAndModelVO.startswith" cssStyle="vertical-align:middle"
                                               id="startswith" value=""/>
                            </label>
                        </td>
                    <tr>
                    <tr>
                        <td colspan="2">
                            <input class="btn" value="Search" type="button" onclick="javascript:search()"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td colspan="2">
                            <input class="btn" value="Clear" type="button" onclick="javascript:clearOut()"/>
                        </td>
                    <tr>
                </table>
            </fieldset>
            <br/>
            <c:if test="${makeForm.statusMessage!=null}">
                <div  id="effect"class="<c:out value="${makeForm.statusMessageType}"/>">
                    <c:out value="${makeForm.statusMessage}"/>
                </div>
            </c:if>
            <fieldset>
                <legend>Make Details</legend>
                <table border="2" id="myTable" style="font-size: .60em;">
                    <thead>
                    <tr>
                        <th><spring:message code="poseidon.id" text="id"/></th>
                        <th>Make Name</th>
                        <th>Description</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach items="${makeForm.makeAndModelVOs}" var="iterationMake">
                        <tr>
                            <td><input type="checkbox" name="checkField" onclick="javascript:checkCall(this)"
                                       value="<c:out value="${iterationMake.makeId}" />"/></td>
                            <td><c:out value="${iterationMake.makeName}"/></td>
                            <td><c:out value="${iterationMake.description}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <table style="margin:auto;top:50%;left:50%;">
                    <tr>
                        <td colspan="5">
                            <br/>
                            <br/>
                        <td>
                    </tr>
                    <tr>
                        <td>
                            <input class="btn" value="Model List" type="button" onclick="javascript:listAllModel()"/>
                        </td>
                        <td>
                            <input class="btn" value="Add Make" type="button" onclick="javascript:addNewMake()"/>
                        </td>
                        <td>
                            <input class="btn" value="Edit Make" type="button" onclick="javascript:editMake()"/>
                        </td>
                        <td>
                            <input class="btn" value="Delete Make" type="button" onclick="javascript:deleteMake()"/>
                        </td>
                        <td>
                            <input class="btn" value="Print MakeList" type="button" onclick="javascript:printMe()"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>
    </div>
</form:form>

</body>
</html>