<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Make and Model List</title>
    <style type="text/css">
        table {
            margin:auto;
            top:50%;
            left:50%;
        }
    </style>
    <script type="text/javascript" src="/js/model-scripts.js"></script>
</head>
<body onload="javascript:hideAlerts()">
<form:form method="POST" modelAttribute="makeForm" >
    <input type="hidden" name="id" id="id"/>
    <form:hidden name="loggedInUser" path="loggedInUser"/>
    <form:hidden name="loggedInRole" path="loggedInRole"/>
    <%@include file="../myHeader.jsp" %>
    <div  class="container">
        <div class="wrap">
            <div class="card">
                <div class="card-header">
                    Search Model
                </div>
                <div class="card-body">
                    <table>
                        <tr>
                            <td>
                                <label for="makeName" class="control-label">
                                    Make Name :
                                </label>
                            </td>
                            <td>
                                <form:select cssClass="form-control" id="makeName" path="searchMakeAndModelVO.makeId" tabindex="1"
                                             onkeypress="handleEnter(event);" >
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
                                <form:input cssClass="form-control" path="searchMakeAndModelVO.modelName" id="modelName"/>
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
            </div>
            <br/>
            <br/>
            <c:if test="${makeForm.statusMessage!=null}">
                <div class="alert alert-<c:out value="${makeForm.statusMessageType}"/>">
                    <a class="close" data-dismiss="alert" href="#" aria-hidden="true">x</a>
                    <c:out value="${makeForm.statusMessage}"/>
                </div>
            </c:if>
            <div class="panel panel-primary">
                <div class="panel-heading">Model Details</div>
                <table id='myTable' class="table table-bordered table-striped table-hover">
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
                        <td colspan="5">
                            <br/>
                            <br/>
                        <td>
                    </tr>
                    <tr>
                        <td>
                            <input class="btn btn-primary" value="Make List" type="button" onclick="javascript:listAllMake()"/>
                        </td>
                        <td>
                            <input class="btn btn-primary" value="Add Model" type="button" onclick="javascript:simpleAdd()"/>
                        </td>
                        <td>
                            <input class="btn btn-primary" value="Edit Model" type="button" onclick="javascript:editModel()"/>
                        </td>
                        <td>
                            <input class="btn btn-primary" value="Delete Model" type="button" onclick="javascript:deleteModel()"/>
                        </td>
                        <td>
                            <input class="btn btn-primary" value="Save Model" type="button" onclick="javascript:saveSimpleModel()"/>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</form:form>

</body>
</html>