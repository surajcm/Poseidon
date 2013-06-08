<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Make and Model List</title>
    <link rel="stylesheet" type="text/css" href="../css/mainStyles.css"/>
    <!--link rel="stylesheet" type="text/css" href="../css/bootstrap.css"/-->
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
		fieldset
		{
			text-align:right;
		}
		
		table
		{
			margin:auto;
			top:50%;
			left:50%;
			
		}
    </style>

    <script type="text/javascript">

        function listAllMake() {
            document.forms[0].action = "MakeList.htm";
            document.forms[0].submit();
        }

        function addNewModel() {
            document.forms[0].action = "addModel.htm";
            document.forms[0].submit();
        }
        function search() {
            document.forms[0].action = "searchModel.htm";
            document.forms[0].submit();
        }
        function clearOut(){
            document.getElementById("makeName").value = document.getElementById('makeName').options[0].value;
            document.getElementById('modelName').value ="";
            document.getElementById('includes').checked = false;
            document.getElementById('startswith').checked = false;
        }

        //validation before edit
        function editModel() {
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
                document.forms[0].action = "editModel.htm";
                document.forms[0].submit();
            } else {
                for (var i = 0; i < checks.length; i++) {
                    if (checks[i].checked) {
                        userRow = document.getElementById("myTable").rows[i + 1];
                    }
                }
                document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
                document.forms[0].action = "editModel.htm";
                document.forms[0].submit();
            }
        }

        // delete
        function deleteModel() {
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
                    document.forms[0].action = "deleteModel.htm";
                    document.forms[0].submit();
                } else {
                    for (var i = 0; i < checks.length; i++) {
                        if (checks[i].checked) {
                            userRow = document.getElementById("myTable").rows[i + 1];
                        }
                    }
                    document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
                    document.forms[0].action = "deleteModel.htm";
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
<body onload="javascript:hideAlerts()">
<form:form method="POST" commandName="makeForm" name="makeForm">
    <input type="hidden" name="id" id="id"/>
    <form:hidden name="loggedInUser" path="loggedInUser"/>
    <form:hidden name="loggedInRole" path="loggedInRole"/>
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <div id="content">
        <div class="wrap">
            <fieldset>
                <legend>Search Model</legend>
                <table>
                    <tr>
                        <td>
                            <label for="makeName">
                                Make Name :
                            </label>
                        </td>
                        <td>
                            <form:select id="makeName" path="searchMakeAndModelVO.makeId" tabindex="1"
                                         onkeypress="handleEnter(event);" cssClass="textfieldMyStyle">
                                <form:option value="0" label="-- Select --"/>
                                <form:options items="${makeForm.makeVOs}"
                                              itemValue="id" itemLabel="makeName"/>
                            </form:select>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="modelName">
                                Model Name :
                            </label>
                        </td>
                        <td>
                            <form:input path="searchMakeAndModelVO.modelName" cssClass="textfieldMyStyle" id="modelName"/>
                        </td>
                    <tr>
                    <tr>
                        <td colspan="2">
                            <label for="includes">
                                <spring:message code="user.includes" text="Includes"/>
                                <form:checkbox path="searchMakeAndModelVO.includes" cssStyle="vertical-align:middle"
                                               id="includes" value=""/>
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td colspan="2">
                            <label for="startswith">
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
                <legend>Model Details</legend>
                <table border="2" id="myTable">
                    <thead>
                    <tr>
                        <th><spring:message code="poseidon.id" text="id"/></th>
                        <th>Make Name</th>
                        <th>Model Name</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach items="${makeForm.makeAndModelVOs}" var="iterationMake">
                        <tr>
                            <td><input type="checkbox" name="checkField" onclick="javascript:checkCall(this)"
                                       value="<c:out value="${iterationMake.modelId}" />"/></td>
                            <td><c:out value="${iterationMake.makeName}"/></td>
                            <td><c:out value="${iterationMake.modelName}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <table>
                    <tr>
                        <td colspan="4">
                            <br/>
                            <br/>
                        <td>
                    </tr>
                    <tr>
                        <td>
                            <input class="btn" value="Make List" type="button" onclick="javascript:listAllMake()"/>
                        </td>
                        <td>
                            <input class="btn" value="Add Model" type="button" onclick="javascript:addNewModel()"/>
                        </td>
                        <td>
                            <input class="btn" value="Edit Model" type="button" onclick="javascript:editModel()"/>
                        </td>
                        <td>
                            <input class="btn" value="Delete Model" type="button" onclick="javascript:deleteModel()"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>
    </div>
</form:form>

</body>
</html>