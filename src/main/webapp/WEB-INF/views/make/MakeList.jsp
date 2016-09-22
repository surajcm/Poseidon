<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Make List</title>
    <style type="text/css">
        table {
            margin:auto;
            top:50%;
            left:50%;
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
            document.getElementById('makeme').text = "Make <span class='sr-only'>Make</span>";
        }

        function addSimpleMake(){
            var myTable = document.getElementById("myTable");

            var d = document.createElement("tr");
            var dCheck = document.createElement("td");
            d.appendChild(dCheck);

            var inCheck = document.createElement("input");
            inCheck.setAttribute("type","checkbox");
            inCheck.setAttribute("name","checkField");
            inCheck.setAttribute("onclick","javascript:checkCall(this)");
            dCheck.appendChild(inCheck);

            var dMake = document.createElement("td");
            d.appendChild(dMake);

            var inMake = document.createElement("input");
            inMake.setAttribute("type","text");
            inMake.setAttribute("class", "form-control");
            inMake.setAttribute("id", "newMakeName");
            dMake.appendChild(inMake);

            var dDesc = document.createElement("td");
            d.appendChild(dDesc);

            var inDesc = document.createElement("input");
            inDesc.setAttribute("type","text");
            inDesc.setAttribute("class", "form-control");
            inDesc.setAttribute("id", "newMakeDesc");
            dDesc.appendChild(inDesc);

            myTable.appendChild(d);
        }

        function saveSimpleMake(){
            alert("You hit simple make save");
            var selectMakeName = document.makeForm.newMakeName.value;
            var selectMakeDesc = document.makeForm.newMakeDesc.value;
            var url = "<%=request.getContextPath()%>" + "/make/saveMakeAjax.htm";
            url = url + "?selectMakeName=" + selectMakeName;
            url = url + "&selectMakeDesc=" + selectMakeDesc;
            bustcacheparameter = (url.indexOf("?") != -1) ? "&" + new Date().getTime() : "?" + new Date().getTime();
            createAjaxRequest();
            if (req) {
                req.onreadystatechange = stateChange;
                req.open("POST", url + bustcacheparameter, true);
                req.send(url + bustcacheparameter);
            }
        }

        function createAjaxRequest() {
            if (window.XMLHttpRequest) {
                req = new XMLHttpRequest();
            } else if (window.ActiveXObject) {
                try {
                    req = new ActiveXObject("Msxml2.XMLHTTP");
                } catch (e) {
                    try {
                        req = new ActiveXObject("Microsoft.XMLHTTP");
                    } catch (e) {
                    }
                }
            }
        }

        function stateChange() {
                if (req.readyState == 4 && (req.status == 200 || window.location.href.indexOf("http") == -1)) {
                    textReturned = req.responseText;
                    if (textReturned != "") {
                        alert("Got ajax response");
                        alert(textReturned);
                    }
                }
            }

    </script>
</head>
<body onload="javascript:hideAlerts()">
    <form:form method="POST" commandName="makeForm" name="makeForm">
    <input type="hidden" name="id" id="id"/>
    <form:hidden name="loggedInUser" path="loggedInUser"/>
    <form:hidden name="loggedInRole" path="loggedInRole"/>
    <%@include file="/WEB-INF/views/myHeader.jsp" %>
    <div class="container">
        <div class="row clearfix">
            <div class="card card-primary">
                <div class="card-heading">Search Model</div>
                <table>
                    <tr>
                        <td>
                            <label for="makeName" class="control-label">
                                Make Name :
                            </label>
                        </td>
                        <td>
                            <form:select id="makeName" path="searchMakeAndModelVO.makeName" cssClass="form-control" tabindex="1">
                                <form:option value="0" label="-- Select --"/>
                                <form:options items="${makeForm.makeVOs}"
                                              itemValue="id" itemLabel="makeName"/>
                            </form:select>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="modelName" class="control-label">
                                Model Name :
                            </label>
                        </td>
                        <td>
                            <form:input path="searchMakeAndModelVO.modelName" cssClass="form-control" id="modelName"/>
                        </td>
                    <tr>
                    <tr>
                        <td colspan="2">
                            <label for="includes" class="control-label">
                                <spring:message code="user.includes" text="Includes"/>
                                <form:checkbox path="searchMakeAndModelVO.includes" cssStyle="vertical-align:middle"
                                               id="includes" value=""/>
                            </label>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td colspan="2">
                            <label for="startswith" class="control-label">
                                <spring:message code="user.startsWith" text="Starts with"/>
                                <form:checkbox path="searchMakeAndModelVO.startswith" cssStyle="vertical-align:middle"
                                               id="startswith" value=""/>
                            </label>
                        </td>
                    <tr>
                    <tr>
                        <td colspan="2">
                            <input class="btn btn-primary" value="Search" type="button" onclick="javascript:search()"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td colspan="2">
                            <input class="btn btn-primary" value="Clear" type="button" onclick="javascript:clearOut()"/>
                        </td>
                    <tr>
                </table>
            </div>
            <br/>
            <c:if test="${makeForm.statusMessage!=null}">
                <div class="alert alert-<c:out value="${makeForm.statusMessageType}"/>">
                    <a class="close" data-dismiss="alert" href="#" aria-hidden="true">x</a>
                    <c:out value="${makeForm.statusMessage}"/>
                </div>
            </c:if>
            <div class="panel panel-primary">
                <div class="panel-heading">Make Details</div>
                <table id='myTable' class="table table-bordered table-striped table-hover">
                    <thead>
                    <tr>
                        <th class="text-center">#</th>
                        <th class="text-center">Make Name</th>
                        <th class="text-center">Description</th>
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
                <table>
                    <tr>
                        <td colspan="5">
                            <br/>
                            <br/>
                        <td>
                    </tr>
                    <tr>
                        <td>
                            <input class="btn btn-primary" value="Model List" type="button" onclick="javascript:listAllModel()"/>
                        </td>
                        <td>
                            <input class="btn btn-primary" value="Add Make" type="button" onclick="javascript:addNewMake()"/>
                        </td>
                        <td>
                            <input class="btn btn-primary" value="Edit Make" type="button" onclick="javascript:editMake()"/>
                        </td>
                        <td>
                            <input class="btn btn-primary" value="Delete Make" type="button" onclick="javascript:deleteMake()"/>
                        </td>
                        <!--td>
                            <input class="btn" value="Print MakeList" type="button" onclick="javascript:printMe()"/>
                        </td-->
                    </tr>
                    <tr>
                        <td>
                            <input class="btn btn-primary btn-block" value="Simple Add" type="button" onclick="javascript:addSimpleMake()"/>
                        <td>
                        <td>
                            <input class="btn btn-primary btn-block" value="Simple Save" type="button" onclick="javascript:saveSimpleMake()"/>
                        <td>
                        <td colspan="3">
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    </form:form>
</body>
</html>
