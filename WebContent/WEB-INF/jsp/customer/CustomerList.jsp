<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Customer List</title>
		<link rel="stylesheet" type="text/css" href="../css/mainStyles.css" />


  </head>
  <body  style="background: #A9A9A9 ;">
  <form:form method="POST" commandName="customerForm" name="customerForm" >
            <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
            <form:hidden name="loggedInUser" path="loggedInUser" />
	        <form:hidden name="loggedInRole" path="loggedInRole" />
            <div id="content">
                <div class="wrap">
                    <fieldset style="text-align:right;">
                        <legend>Search Customers</legend>
                        <table style="margin:auto;top:50%;left:50%;">
                            <tr>
                                <td>
                                    <label for="customerId" style="font-size: .70em;">
                                        customerId
                                    </label>
                                </td>
                                <td>
                                    <form:input path="searchCustomerVO.customerId" cssClass="textboxes" id="customerId"/>
                                    <form:errors path="searchCustomerVO.customerId"/>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="customerName" style="font-size: .70em;">
                                        Customer Name
                                    </label>
                                </td>
                                <td>
                                    <form:input path="searchCustomerVO.customerName" cssClass="textboxes" id="customerName"/>
                                    <form:errors path="searchCustomerVO.customerName"/>
                                </td>
                                <td colspan="2">&nbsp;</td>
                                <td>
                                    <label for="mobile" style="font-size: .70em;">
                                        mobile
                                    </label>
                                </td>
                                <td>
                                    <form:input path="searchCustomerVO.mobile" cssClass="textboxes" id="mobile"/>
                                    <form:errors path="searchCustomerVO.mobile"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="8">&nbsp;</td>
                                <td>
                                    <label for="includes" style="font-size: .70em;">
                                        <spring:message code="user.includes" text="Includes"/>
                                        <input type="checkbox" name="includes" value="includes"/>
                                    </label>
                                </td>
                                <td>
                                    <label for="startswith" style="font-size: .70em;">
                                        <spring:message code="user.startsWith" text="Starts with"/>
                                        <input type="checkbox" name="startswith" value="startswith"/>
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="10">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="8">&nbsp;</td>
                                <td>
                                    <input class="btn" value="<spring:message code="poseidon.search" text="Search" />"
                                           type="button" onclick="javascript:search()"/>
                                </td>
                                <td>
                                    <input class="btn" value="<spring:message code="poseidon.clear" text="Clear" />"
                                           type="button" onclick="javascript:clear()"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <br/>
                    <fieldset>
                        <legend>Customer Details</legend>
                        <table border="2" id="myTable" style="font-size: .60em;">
                            <thead>
                            <tr>
                                <th><spring:message code="poseidon.id" text="id"/></th>
                                <th>Name</th>
                                <th>Address Line1</th>
                                <th>Address Line21</th>
                                <th>Phone</th>
                                <th>Mobile</th>
                                <th>Email</th>
                                <th>Contact Person 1</th>
                                <th>Contact Phone No1</th>
                                <th>Contact Person 1</th>
                                <th>Contact Phone No1</th>
                                <th>Notes</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${customerForm.customerVOs}" var="iterationCust">
                                <tr>
                                    <td><input type="checkbox" name="checkField" onclick="javascript:checkCall(this)"
                                               value="<c:out value="${iterationCust.customerId}" />"/></td>
                                    <td><c:out value="${iterationCust.customerName}"/></td>
                                    <td><c:out value="${iterationCust.address1}"/></td>
                                    <td><c:out value="${iterationCust.address2}"/></td>
                                    <td><c:out value="${iterationCust.phoneNo}"/></td>
                                    <td><c:out value="${iterationCust.Mobile}"/></td>
                                    <td><c:out value="${iterationCust.email}"/></td>
                                    <td><c:out value="${iterationCust.ContactPerson1}"/></td>
                                    <td><c:out value="${iterationCust.ContactPh1}"/></td>
                                    <td><c:out value="${iterationCust.ContactPerson2}"/></td>
                                    <td><c:out value="${iterationCust.ContactPh2}"/></td>
                                    <td><c:out value="${iterationCust.Note}"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <table style="margin:auto;top:50%;left:50%;">
                            <tr>
                                <td>
                                    <br/>
                                    <br/>
                                    <input class="btn" value="Add New Customer" type="button" onclick="javascript:addNew()"/>
                                    <input class="btn" value="Edit Customer" type="button" onclick="javascript:editMe()"/>
                                    <input class="btn" value="Delete Customer" type="button" onclick="javascript:deleteTxn()"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </div>
            <div>
  </form:form>

  </body>
</html>