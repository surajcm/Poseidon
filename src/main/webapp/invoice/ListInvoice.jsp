<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <title>Invoice List</title>
    <script type="text/javascript" src="/js/common-scripts.js"></script>
    <script type="text/javascript" src="/js/invoice-scripts.js"></script>
    <script type="text/javascript" src="/js/transaction-and-invoice.js"></script>
    <script type="text/javascript" src="/js/navbar-scripts.js"></script>
</head>
<body onload="hideAlerts()">
    <form:form method="POST" modelAttribute="invoiceForm" >
        <form:hidden name="loggedInUser" path="loggedInUser" />
        <form:hidden name="loggedInRole" path="loggedInRole" />
        <input type="hidden" name="id" id="id"/>
        <%@include file="../navbar.jsp" %>
        <div class="container">
            <div class="wrap">
                <div class="card">
                    <div class="card-header">
                        Search Invoice
                    </div>
                    <div class="card-body">
                        <div class="row g-3">
                            <div class="col-md-3">
                              <label for="invoiceId" class="form-label"> Invoice Id :</label>
                              <form:input cssClass="form-control" path="searchInvoiceVo.id" id="invoiceId"/>
                            </div>
                            <div class="col-md-3">
                              <label for="description" class="form-label">Description :</label>
                              <form:input cssClass="form-control" path="searchInvoiceVo.description" id="description"/>
                            </div>
                            <div class="col-md-3">
                              <label for="serialNo" class="form-label">Serial No :</label>
                              <form:input cssClass="form-control" path="searchInvoiceVo.serialNo" id="serialNo"/>
                            </div>
                            <div class="col-md-3">
                              <label for="tagNo" class="form-label">Tag No :</label>
                              <form:input cssClass="form-control" path="searchInvoiceVo.tagNo"  id="tagNo"/>
                            </div>
                            <div class="col-md-4">
                                <label for="amount" class="form-label">Amount :</label>
                                <form:input cssClass="form-control" path="searchInvoiceVo.amount"  id="amount"/>
                            </div>
                            <div class="col-md-4">
                                <div class="form-check">
                                    <form:checkbox path="searchInvoiceVo.greater" cssClass="form-check-input" id="greater" value="" />
                                    <label class="form-check-label" for="greater">
                                        Greater than & Equal
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-check">
                                    <form:checkbox path="searchInvoiceVo.lesser" cssClass="form-check-input" id="lesser" value="" />
                                    <label class="form-check-label" for="lesser">
                                    Lesser than & Equal
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-check">
                                    <form:checkbox path="searchInvoiceVo.includes" cssClass="form-check-input" id="includes" value="" />
                                    <label class="form-check-label" for="includes">
                                    <spring:message code="user.includes" text="Includes" />
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-check">
                                    <form:checkbox path="searchInvoiceVo.startsWith" cssClass="form-check-input" id="startswith" value="" />
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
                <c:if test="${invoiceForm.statusMessage!=null}">
                    <div class="alert alert-<c:out value="${invoiceForm.statusMessageType}"/> alert-dismissible fade show" role="alert">
                        <c:out value="${invoiceForm.statusMessage}"/>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <table id='myTable' class="table table-bordered table-striped table-hover caption-top">
                    <caption>Invoice Details</caption>
                    <thead class="table-dark">
                        <tr>
                            <th scope="col">&nbsp;</th>
                            <th scope="col">Invoice Id</th>
                            <th scope="col">Customer Name</th>
                            <th scope="col">Tag No</th>
                            <th scope="col">Item Description</th>
                            <th scope="col">Serial No</th>
                            <th scope="col">Total Amount</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${invoiceForm.invoiceVos}" var="iterationInv">
                        <tr>
                            <th scope="row"><input type="checkbox" name="checkField" onclick="checkCall(this)"
                                       value="<c:out value="${iterationInv.id}" />"/></th>
                            <td><c:out value="${iterationInv.id}"/></td>
                            <td><c:out value="${iterationInv.customerName}"/></td>
                            <td><c:out value="${iterationInv.tagNo}"/></td>
                            <td><c:out value="${iterationInv.description}"/></td>
                            <td><c:out value="${iterationInv.serialNo}"/></td>
                            <td><c:out value="${iterationInv.amount}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="row g-3">
                    <div class="col-md-4">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#newInvoiceModal"
                        onclick="addSmartInvoiceOnInvoicePage();">Add New Invoice</button>
                    </div>
                    <div class="col-md-4">
                        <button type="button" class="btn btn-primary"  data-bs-toggle="modal" data-bs-target="#editInvoiceModal"
                        onclick="editSmartInvoice();">
                            Edit Smart Invoice
                        </button>
                    </div>
                    <div class="col-md-4">
                        <button type="button" class="btn btn-primary" onclick="deleteInvoice()">
                            Delete Invoice
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div id="newInvoiceModal" class="modal fade" tabindex="-1" aria-labelledby="newInvoiceModal" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Add new Invoice</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div id="invoiceModalBody" class="modal-body">
                        <p>Lets add some invoices....</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="saveModal" class="btn btn-primary" onclick="saveFromModal();">Save</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="editInvoiceModal" class="modal fade" tabindex="-1" aria-labelledby="editInvoiceModal" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit Invoice</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div id="invoiceEditModalBody" class="modal-body">
                        <p>Lets edit the invoice</p>
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
