<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Suraj">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <spring:url value="/img/Poseidon_Ico.ico" var="posIcon" />
    <link rel="shortcut icon" href="${posIcon}" />
    <link rel="stylesheet" href="/css/bootstrap.min.css"  type="text/css" />
    <link rel="stylesheet" href="/css/custom.css" type="text/css" />
    <title><spring:message code="poseidon.userListPage" text="User List" /></title>
    <script type="text/javascript" src="/js/user-scripts.js"></script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body onload="javascript:hideAlerts()">
    <form:form method="POST" action="listAll.htm" modelAttribute="userForm">
        <input type="hidden" name="id" id="id" />
        <input type="hidden" name="addInProgress" id="addInProgress" />
        <form:hidden name="loggedInUser" path="loggedInUser" />
        <form:hidden name="loggedInRole" path="loggedInRole" />
        <%@include file="../navbar.jsp" %>
        <div class="container">
            <div class="wrap">
                <div class="card">
                    <div class="card-header">
                        <spring:message code="user.searchUser" text="Search User Details" />
                    </div>
                    <div class="card-body">
                        <div class="card-text">
                            <div class="form-row">
                                <div class="form-group col-md-2 text-right">
                                    <label for="name"><spring:message code="poseidon.name" text="Name" /> :</label>
                                </div>
                                <div class="form-group col-md-2">
                                    <form:input cssClass="form-control" path="searchUser.name" id="name" />
                                </div>
                                <div class="form-group col-md-2 text-right">
                                    <label for="email"><spring:message code="poseidon.email" text="email" /> :</label>
                                </div>
                                <div class="form-group col-md-2">
                                    <form:input cssClass="form-control" path="searchUser.email" id="email" />
                                </div>
                                <div class="form-group col-md-2 text-right">
                                    <label for="role"><spring:message code="poseidon.role" text="Role" /> :</label>
                                </div>
                                <div class="form-group col-md-2">
                                    <form:select id="role" path="searchUser.role" cssClass="form-control"
                                             onkeypress="handleEnter(event);">
                                        <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                        <form:options items="${userForm.roleList}" />
                                    </form:select>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <div class="form-check">
                                      <form:checkbox path="searchUser.includes" cssClass="form-check-input" id="includes" value="" />
                                      <label class="form-check-label" for="includes">
                                        <spring:message code="user.includes" text="Includes" />
                                      </label>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">    
                                    <div class="form-check">
                                      <form:checkbox path="searchUser.startsWith" cssClass="form-check-input" id="startswith" value="" />
                                      <label class="form-check-label" for="startsWith">
                                        <spring:message code="user.startsWith" text="Starts with" />
                                      </label>
                                    </div>
                                </div>
                            </div>
                            <input class="btn btn-primary" value="<spring:message code='poseidon.search' text='Search' />" type="button" onclick="javascript:search()" />
                            <input class="btn btn-primary" value="<spring:message code='poseidon.clear' text='Clear' />" type="button" onclick="javascript:clearOut()" />
                        </div>
                    </div>
                </div>
                <br />
                <br />
                <c:if test="${userForm.statusMessage!=null}">
                    <div class="alert alert-<c:out value='${userForm.statusMessageType}'/>">
                        <a class="close" data-dismiss="alert" href="#" aria-hidden="true">x</a>
                        <c:out value="${userForm.statusMessage}"/>
                    </div>
                </c:if>
                <div class="panel panel-primary">
                    <div class="panel-heading"><spring:message code="user.userDetails" text="User Details" /></div>
                    <table id='myTable' class="table table-bordered table-striped table-hover">
                        <thead>
                            <tr>
                                <th><spring:message code="poseidon.id" text="id" /></th>
                                <th><spring:message code="poseidon.name" text="Name" /></th>
                                <th><spring:message code="poseidon.email" text="email" /></th>
                                <th><spring:message code="poseidon.role" text="Role" /></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${userForm.userVOs}" var="iterationUser">
                                <tr>
                                    <td><input type="checkbox" name="checkField" onclick="checkCall(this)"
                                               value='<c:out value="${iterationUser.id}" />' /></td>
                                    <td><c:out value="${iterationUser.name}" /></td>
                                    <td><c:out value="${iterationUser.email}" /></td>
                                    <td><c:out value="${iterationUser.role}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div class="form-row">
                        <div class="form-group">
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newUserModal" 
                                onclick="javascript:addNewUser()">Add New User</button>
                            <button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#editUserModal"
                                onclick="javascript:editUser()">
                                <spring:message code='poseidon.edit' text='Edit User' />
                            </button>
                            <button type="button" class="btn btn-primary" onclick="deleteUser()">
                                <spring:message code='poseidon.delete' text='Delete User' />
                            </button>
                            <button type="button" class="btn btn-primary" onclick="resetUser()">
                                Reset Password
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="newUserModal" class="modal fade bd-example-modal-lg" role="dialog">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Add new User</h4>
                        <button type="button" class="close" data-dismiss="modal">x</button>
                    </div>
                    <div id="userModalBody" class="modal-body">
                        <p>Lets add some users....</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="saveModal" class="btn btn-default" onclick="saveFromModal()">Save</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="editUserModal" class="modal fade bd-example-modal-lg" role="dialog">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Edit User</h4>
                        <button type="button" class="close" data-dismiss="modal">x</button>
                    </div>
                    <div id="userEditModalBody" class="modal-body">
                        <p>Lets edit the user</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="updateModal" class="btn btn-default" onclick="updateFromModal()">Update</button>
                    </div>
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
