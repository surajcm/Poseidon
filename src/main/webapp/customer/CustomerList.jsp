<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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
    <title>Customer List</title>
    <script type="text/javascript" src="/js/customer-scripts.js"></script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body onload="javascript:hideAlerts()">
    <form:form method="POST" modelAttribute="customerForm">
        <input type="hidden" name="id" id="id" />
        <form:hidden name="loggedInUser" path="loggedInUser" />
        <form:hidden name="loggedInRole" path="loggedInRole" />
        <%@include file="../navbar.jsp" %>
        <div class="container">
            <div class="wrap">
                <div class="card">
                    <div class="card-header">
                        Search Customers
                    </div>
                    <div class="card-body">
                        <div class="card-text">
                            <div class="form-row">
                                <div class="form-group col-md-2">
                                  <label for="name">Customer Id :</label>
                                  <form:input cssClass="form-control" path="searchCustomerVO.customerId" id="customerId"/>
                                </div>
                                <div class="form-group col-md-6">
                                  <label for="loginId">Customer Name :</label>
                                  <form:input cssClass="form-control" path="searchCustomerVO.customerName" id="customerName"/>
                                </div>
                                <div class="form-group col-md-4">
                                    <label for="loginId">Mobile :</label>
                                    <form:input cssClass="form-control" path="searchCustomerVO.mobile" id="mobile"/>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <div class="form-check">
                                        <form:checkbox path="searchCustomerVO.includes" cssClass="form-check-input" id="includes" value="" />
                                        <label class="form-check-label" for="includes">
                                            <spring:message code="user.includes" text="Includes" />
                                        </label>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">    
                                    <div class="form-check">
                                        <form:checkbox path="searchCustomerVO.startsWith" cssClass="form-check-input" id="startswith" value="" />
                                        <label class="form-check-label" for="startsWith">
                                            <spring:message code="user.startsWith" text="Starts with" />
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <input class="btn btn-primary"
                                           value="<spring:message code='poseidon.search' text='Search' />"
                                           type="button" onclick="javascript:search()"/>
                        <input class="btn btn-primary"
                                           value="<spring:message code='poseidon.clear' text='Clear' />"
                                           type="button" onclick="javascript:clearOut()"/>
                    </div>
                </div>
                <br />
                <br />
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
                            <th>Address</th>
                            <th>Phone</th>
                            <th>Mobile</th>
                            <th>Email</th>
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
                                <td><c:out value="${iterationCust.phoneNo}"/></td>
                                <td><c:out value="${iterationCust.mobile}"/></td>
                                <td><c:out value="${iterationCust.email}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <table>
                        <tr>
                            <td>
                                <br/>
                                <br/>
                                <input class="btn btn-primary" value="Add New Customer" type="button" onclick="javascript:addCustomer()"/>
                                <input class="btn btn-primary" value="Edit Customer" type="button" onclick="javascript:editCustomer()"/>
                                <input class="btn btn-primary" value="View Customer" type="button" data-toggle="modal" data-target="#customerDetail" onclick="javascript:viewCustomer()"/>
                                <input class="btn btn-primary" value="Delete Customer" type="button" onclick="javascript:deleteCustomer()"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div id="customerDetail" class="modal fade bd-example-modal-lg" role="dialog">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Customer Details</h4>
                            <button type="button" class="close" data-dismiss="modal">x</button>
                        </div>
                        <div id="detail" class="modal-body">
                            <p>Details of the customer.....</p>
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
