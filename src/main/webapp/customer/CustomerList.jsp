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
    <link rel="stylesheet" href="/css/bootstrap-5.min.css"  type="text/css" />
    <link rel="stylesheet" href="/css/custom.css" type="text/css" />
    <title>Customer List</title>
    <script type="text/javascript" src="/js/common-scripts.js"></script>
    <script type="text/javascript" src="/js/customer-scripts.js"></script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body onload="hideAlerts()">
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
                        <div class="row g-3">
                            <div class="col-md-2">
                              <label for="customerId" class="form-label">Customer Id :</label>
                              <form:input cssClass="form-control" path="searchCustomerVO.customerId" id="customerId"/>
                            </div>
                            <div class="col-md-6">
                              <label for="customerName" class="form-label">Customer Name :</label>
                              <form:input cssClass="form-control" path="searchCustomerVO.customerName" id="customerName"/>
                            </div>
                            <div class="col-md-4">
                                <label for="mobile" class="form-label">Mobile :</label>
                                <form:input cssClass="form-control" path="searchCustomerVO.mobile" id="mobile"/>
                            </div>
                            <div class="col-md-6">
                                <div class="form-check">
                                    <form:checkbox path="searchCustomerVO.includes" cssClass="form-check-input" id="includes" value="" />
                                    <label class="form-check-label" for="includes">
                                        <spring:message code="user.includes" text="Includes" />
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-check">
                                    <form:checkbox path="searchCustomerVO.startsWith" cssClass="form-check-input" id="startswith" value="" />
                                    <label class="form-check-label" for="startsWith">
                                        <spring:message code="user.startsWith" text="Starts with" />
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <input type="button" class="btn btn-primary"
                                           value="<spring:message code='poseidon.search' text='Search' />"
                                           onclick="search()"/>
                            </div>
                            <div class="col-md-6">
                                <input type="button"  class="btn btn-primary"
                                           value="<spring:message code='poseidon.clear' text='Clear' />"
                                           onclick="clearOut()"/>
                            </div>
                        </div>
                    </div>
                </div>
                <br />
                <br />
                <c:if test="${customerForm.statusMessage!=null}">
                    <div class="alert alert-<c:out value="${customerForm.statusMessageType}"/> alert-dismissible fade show" role="alert">
                        <c:out value="${customerForm.statusMessage}"/>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <table id='myTable' class="table table-bordered table-striped table-hover caption-top">
                    <caption>Customer Details</caption>
                    <thead class="table-dark">
                    <tr>
                        <th scope="col">&nbsp;</th>
                        <th scope="col"><spring:message code="poseidon.id" text="id"/></th>
                        <th scope="col">Name</th>
                        <th scope="col">Address</th>
                        <th scope="col">Phone</th>
                        <th scope="col">Mobile</th>
                        <th scope="col">Email</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${customerForm.customerVOs}" var="iterationCust">
                        <tr>
                            <th scope="row"><input type="checkbox" name="checkField" onclick="checkCall(this)"
                                       value="<c:out value="${iterationCust.customerId}" />"/></th>
                            <td><c:out value="${iterationCust.customerId}"/></td>
                            <td><c:out value="${iterationCust.customerName}"/></td>
                            <td><c:out value="${iterationCust.address}"/></td>
                            <td><c:out value="${iterationCust.phoneNo}"/></td>
                            <td><c:out value="${iterationCust.mobile}"/></td>
                            <td><c:out value="${iterationCust.email}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="row g-3">
                    <div class="col-md-3">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#newCustomer"
                        onclick="addSmartCustomer();">Add New Customer</button>
                    </div>
                    <div class="col-md-3">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#editCustomer"
                            onclick="editSmartCustomer();">Edit customer</button>
                    </div>
                    <div class="col-md-3">
                        <input type="button" class="btn btn-primary" value="View Customer" data-bs-toggle="modal" data-bs-target="#customerDetail" onclick="viewCustomer()"/>
                    </div>
                    <div class="col-md-3">
                        <input type="button" class="btn btn-primary" value="Delete Customer" onclick="deleteCustomer()"/>
                    </div>
                </div>
                <div id="newCustomer" class="modal fade" tabindex="-1" aria-labelledby="newCustomer" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Add new Customer</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div id="newCustomerBody" class="modal-body">
                                <p>Lets add some customers....</p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" id="saveSmartCustomer" class="btn btn-primary" onclick="saveFromModal();">Save</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="editCustomer" class="modal fade" tabindex="-1" aria-labelledby="editCustomer" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Edit Customer</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div id="editCustomerBody" class="modal-body">
                                <p>Lets edit customer....</p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" id="updateSmartCustomer" class="btn btn-primary" onclick="updateFromModal();">Update</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="customerDetail" class="modal fade" tabindex="-1" aria-labelledby="customerDetail" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Customer Details</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div id="detail" class="modal-body">
                                <p>Details of the customer.....</p>
                            </div>
                        </div>
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
