<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Make and Model List</title>
		<link rel="stylesheet" type="text/css" href="../css/mainStyles.css" />


  </head>
  <body style="background: #A9A9A9 ;">
  <form:form method="POST" commandName="makeForm" name="makeForm" >
            <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
            <form:hidden name="loggedInUser" path="loggedInUser" />
	        <form:hidden name="loggedInRole" path="loggedInRole" />
            <div id="content">
				<div class="wrap">
					<fieldset style="text-align:right;">
						<legend>Search Make /Model</legend>
						<table style="margin:auto;top:50%;left:50%;">
							<tr>
								<td>
									<label for="MakeName" style="font-size: .70em;">
										Make Name
									</label>
								</td>
								<td>
									<form:input path="searchMakeVO.makeName" cssClass="textboxes" id="makeName"/>
									<form:errors path="searchMakeVO.makeName"/>
								</td>
								<td colspan="2">&nbsp;</td>
								<td>
									<label for="modelName" style="font-size: .70em;">
										modelName
									</label>
								</td>
								<td>
									<form:input path="searchMakeVO.modelName" cssClass="textboxes" id="modelName"/>
									<form:errors path="searchMakeVO.modelName"/>
								</td>
							<tr>
							<tr>
								<td colspan="2">
									<label for="includes" style="font-size: .70em;">
										<spring:message code="user.includes" text="Includes"/>
										<input type="checkbox" name="includes" value="includes"/>
									</label>
								</td>
								<td colspan="2">&nbsp;</td>
								<td colspan="2">
									<label for="startswith" style="font-size: .70em;">
										<spring:message code="user.startsWith" text="Starts with"/>
										<input type="checkbox" name="startswith" value="startswith"/>
									</label>
								</td>
							<tr>
							<tr>
								<td colspan="2">
									<input class="btn" value="Search" type="button" onclick="javascript:search()"/>
								</td>
								<td colspan="2">&nbsp;</td>
								<td colspan="2">
									<input class="btn" value="Clear" type="button" onclick="javascript:clear()"/>
								</td>
							<tr>
						</table>
					</fieldset>
					<br/>
					<fieldset>
						<legend>Model Details</legend>
						<table border="2" id="myTable" style="font-size: .60em;">
							<thead>
							<tr>
								<th><spring:message code="poseidon.id" text="id"/></th>
								<th>Make Name</th>
								<th>Model Name</th>
							</tr>
							</thead>

							<tbody>
							<c:forEach items="${makeForm.makeVOs}" var="iterationMake">
								<tr>
									<td><input type="checkbox" name="checkField" onclick="javascript:checkCall(this)"
											   value="<c:out value="${iterationMake.id}" />"/></td>
									<td><c:out value="${iterationMake.makeName}"/></td>
									<td><c:out value="${iterationMake.modelName}"/></td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						<table style="margin:auto;top:50%;left:50%;">
							<tr>
								<td colspan="3">
									<br/>
									<br/>
								<td>
							</tr>
							<tr>
								<td>
									<input class="btn" value="Add Make" type="button" onclick="javascript:addNew()"/>
								</td>
								<td>
									<input class="btn" value="Edit Make" type="button" onclick="javascript:editMe()"/>
								</td>
								<td>
									<input class="btn" value="Delete Make" type="button" onclick="javascript:deleteTxn()"/>
								</td>
							</tr>
							<tr>
								<td>
									<input class="btn" value="Add Model" type="button" onclick="javascript:addNew()"/>
								</td>
								<td>
									<input class="btn" value="Edit Model" type="button" onclick="javascript:editMe()"/>
								</td>
								<td>
									<input class="btn" value="Delete Model" type="button" onclick="javascript:deleteTxn()"/>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
			</div>
  </form:form>

  </body>
</html>