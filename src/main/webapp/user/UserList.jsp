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
    <link rel="stylesheet" href="/css/bootstrap-5.min.css"  type="text/css" />
    <link rel="stylesheet" href="/css/custom.css" type="text/css" />
    <title><spring:message code="poseidon.userListPage" text="User List" /></title>
    <script type="text/javascript" src="/js/common-scripts.js"></script>
    <script type="text/javascript" src="/js/user-scripts.js"></script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body onload="hideAlerts()">
    <form:form method="POST" action="listAll.htm" modelAttribute="userForm">
        <input type="hidden" name="id" id="id" />
        <input type="hidden" name="addInProgress" id="addInProgress" />
        <form:hidden name="loggedInUser" path="loggedInUser" />
        <form:hidden name="loggedInRole" path="loggedInRole" />
        <%@include file="../navbar5.jsp" %>
        <div class="container">
            <div class="wrap">
                <div class="card">
                    <div class="card-header">
                        <spring:message code="user.searchUser" text="Search User Details" />
                    </div>
                    <div class="card-body">
                        <div class="row g-3">
                            <div class="col-md-4">
                                <label for="name" class="form-label"><spring:message code="poseidon.name" text="Name" /> :</label>
                                <form:input cssClass="form-control" path="searchUser.name" id="name" />
                            </div>
                            <div class="col-md-4">
                                <label for="email" class="form-label"><spring:message code="poseidon.email" text="email" /> :</label>
                                <form:input cssClass="form-control" path="searchUser.email" id="email" />
                            </div>
                            <div class="col-md-4">
                                <label for="role" class="form-label"><spring:message code="poseidon.role" text="Role" /> :</label>
                                <form:select id="role" path="searchUser.role" cssClass="form-select"
                                             onkeypress="handleEnter(event);">
                                    <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                    <form:options items="${userForm.roleList}" />
                                </form:select>
                            </div>
                            <div class="col-md-6">
                                <div class="form-check">
                                    <form:checkbox path="includes" cssClass="form-check-input" id="includes" value="" />
                                    <label class="form-check-label" for="includes">
                                    <spring:message code="user.includes" text="Includes" />
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-check">
                                    <form:checkbox path="startsWith" cssClass="form-check-input" id="startswith" value="" />
                                    <label class="form-check-label" for="startsWith">
                                    <spring:message code="user.startsWith" text="Starts with" />
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <button type="button" class="btn btn-primary" onclick="search()" ><spring:message code='poseidon.search' text='Search' /></button>
                            </div>
                            <div class="col-md-6">
                                <button type="button" class="btn btn-primary" onclick="clearOut()"><spring:message code='poseidon.clear' text='Clear' /></button>
                            </div>
                        </div>
                    </div>
                </div>
                <br />
                <br />
                <c:if test="${userForm.statusMessage!=null}">
                    <div class="alert alert-<c:out value='${userForm.statusMessageType}'/>  alert-dismissible fade show" role="alert">
                        <c:out value="${userForm.statusMessage}"/>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <table id='myTable' class="table table-bordered table-striped table-hover caption-top">
                    <caption><spring:message code="user.userDetails" text="User Details" /></caption>
                    <thead class="table-dark">
                        <tr>
                            <th scope="col"><spring:message code="poseidon.id" text="id" /></th>
                            <th scope="col"><spring:message code="poseidon.name" text="Name" /></th>
                            <th scope="col"><spring:message code="poseidon.email" text="email" /></th>
                            <th scope="col"><spring:message code="poseidon.role" text="Role" /></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${userForm.userVOs}" var="iterationUser">
                            <tr>
                                <th scope="row"><input type="checkbox" name="checkField" onclick="checkCall(this)"
                                           value='<c:out value="${iterationUser.id}" />' /></th>
                                <td><c:out value="${iterationUser.name}" /></td>
                                <td><c:out value="${iterationUser.email}" /></td>
                                <td><c:out value="${iterationUser.role}" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="row g-3">
                    <div class="col-md-3">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#newUserModal"
                            onclick="addNewUser();">Add New User</button>
                    </div>
                    <div class="col-md-3">
                        <button type="button" class="btn btn-primary"  data-bs-toggle="modal" data-bs-target="#editUserModal"
                            onclick="editUser();">
                            <spring:message code='poseidon.edit' text='Edit User' />
                        </button>
                    </div>
                    <div class="col-md-3">
                        <button type="button" class="btn btn-primary" onclick="deleteUser()">
                            <spring:message code='poseidon.delete' text='Delete User' />
                        </button>
                    </div>
                    <div class="col-md-3">
                        <button type="button" class="btn btn-primary" onclick="resetUser()">
                            Reset Password
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div id="newUserModal" class="modal fade" tabindex="-1" aria-labelledby="newUserModal" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Add new User</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div id="userModalBody" class="modal-body">
                        <p>Lets add some users....</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="saveModal" class="btn btn-primary" onclick="saveFromModal()">Save</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="editUserModal" class="modal fade" tabindex="-1" aria-labelledby="editUserModal" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit User</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div id="userEditModalBody" class="modal-body">
                        <p>Lets edit the user</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="updateModal" class="btn btn-primary" onclick="updateFromModal()">Update</button>
                    </div>
                </div>
            </div>
        </div>
        <script src="/js/core/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="/js/core/popper.min.js" type="text/javascript"></script>
        <script src="/js/core/bootstrap-5.min.js" type="text/javascript"></script>
        <script type="text/javascript">
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
