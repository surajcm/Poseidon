<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Suraj">
    <spring:url value="/resources/images/Poseidon_Ico.ico" var="posIcon" />
    <link rel="shortcut icon" href="${posIcon}" />
    <link rel="stylesheet" href="/css/bootstrap.min.css"  type="text/css" />
    <link rel="stylesheet" href="/css/custom.css" type="text/css" />
    <sec:csrfMetaTags/>
    <title>Make List</title>
    <script type="text/javascript" src="/js/make-scripts.js"></script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body onload="javascript:hideAlerts()">
    <form:form method="POST" modelAttribute="makeForm" >
        <input type="hidden" name="id" id="id"/>
        <form:hidden name="loggedInUser" path="loggedInUser"/>
        <form:hidden name="loggedInRole" path="loggedInRole"/>
        <%@include file="../navbar.jsp" %>
        <div  class="container">
            <div class="wrap">
                <div class="card">
                    <div class="card-header">
                        Search Model
                    </div>
                    <div class="card-body">
                        <div class="card-text">
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="makeName">Make Name :</label>
                                    <form:select id="makeName" path="searchMakeAndModelVO.makeName" cssClass="form-control" tabindex="1">
                                        <form:option value="0" label="-- Select --"/>
                                        <form:options items="${makeForm.makeVOs}"
                                                      itemValue="id" itemLabel="makeName"/>
                                    </form:select>
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="modelName">Model Name :</label>
                                    <form:input path="searchMakeAndModelVO.modelName" cssClass="form-control" id="modelName"/>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <div class="form-check">
                                      <form:checkbox path="searchMakeAndModelVO.includes" ccssClass="form-check-input"
                                                       id="includes" value=""/>
                                      <label class="form-check-label" for="includes">
                                        <spring:message code="user.includes" text="Includes" />
                                      </label>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">    
                                    <div class="form-check">
                                      <form:checkbox path="searchMakeAndModelVO.startswith" cssClass="form-check-input"
                                                       id="startswith" value=""/>
                                      <label class="form-check-label" for="startsWith">
                                        <spring:message code="user.startsWith" text="Starts with" />
                                      </label>
                                    </div>
                                </div>
                                <input class="btn btn-primary" value="Search" type="button" onclick="javascript:search()"/>
                                <input class="btn btn-primary" value="Clear" type="button" onclick="javascript:clearOut()"/>
                            </div>
                        </div>        
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
                                <input class="btn btn-primary" value="Add Make" type="button" onclick="javascript:addSimpleMake()"/>
                            </td>
                            <td>
                                <input class="btn btn-primary" value="Edit Make" type="button" onclick="javascript:editMake()"/>
                            </td>
                            <td>
                                <input class="btn btn-primary" value="Delete Make" type="button" onclick="javascript:deleteMake()"/>
                            </td>
                            <td>
                                <input class="btn btn-primary btn-block" value="Save Make" type="button" onclick="javascript:saveSimpleMake()"/>
                            <td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <script src="/js/core/jquery-3.2.1.min.js"></script>
        <script src="/js/core/popper.min.js"></script>
        <script src="/js/core/bootstrap.min.js"></script>
        <script>
            $(document).ready(function() {
                //Handles menu drop down
                $('.dropdown-menu').find('form').click(function (e) {
                    e.stopPropagation();
                });
            });
        </script>
    </form:form>
</body>
</html>