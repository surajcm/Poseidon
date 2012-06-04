<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Add Transaction</title>
		<link rel="stylesheet" type="text/css" href="../css/mainStyles.css" />
        <script type="text/javascript">

			//code to add New user
			function save(){
				document.forms[0].action="SaveTxn.htm";
				document.forms[0].submit();
			}

		    //code to edit a user
			function clear(){
				document.getElementById("TagNo").value ="";
				document.getElementById("customerId").value ="";
			}
		</script>
  </head>
  <body style="background: #A9A9A9 ;" >
  <form:form method="POST" commandName="transactionForm" name="transactionForm" >
            <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
            <form:hidden name="loggedInUser" path="loggedInUser" />
	        <form:hidden name="loggedInRole" path="loggedInRole" />
            <div id="content">
                <table class="myTable">
                    <tr>
                        <td>
                            <label for="TagNo">Tag No</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td>
                            <form:input path="currentTransaction.TagNo" cssClass="textboxes" id="TagNo" />
                        </td>
						<td> &nbsp;</td>
						<td>
                            <label for="customerId">Customer Id</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td>
                            <form:input path="currentTransaction.customerId" cssClass="textboxes" id="customerId" />
                        </td>
						<td> &nbsp;</td>
						<td>
                            <label for="productCategory">Product Category</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td>
                            <form:input path="currentTransaction.productCategory" cssClass="textboxes" id="productCategory" />
                        </td>
					</tr>
					<tr>
                        <td>
                            <label for="makeId">MakeId</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td>
                            <form:input path="currentTransaction.makeId" cssClass="textboxes" id="makeId" />
                        </td>
						<td> &nbsp;</td>
						<td>
                            <label for="modelId">modelId</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td>
                            <form:input path="currentTransaction.modelId" cssClass="textboxes" id="modelId" />
                        </td>
						<td> &nbsp;</td>
						<td>
                            <label for="serialNo">serialNo</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td>
                            <form:input path="currentTransaction.serialNo" cssClass="textboxes" id="serialNo" />
                        </td>
					</tr>
					<tr>
                        <td>
                            <label for="accessories">accessories</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td colspan="2">
							<form:textarea path="currentTransaction.accessories" rows="5" cols="30" cssClass="textboxes" id="accessories" />
                        </td>
						<td> &nbsp;</td>
						<td>
                            <label for="complaintReported">complaintReported</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td colspan="2">
							<form:textarea path="currentTransaction.complaintReported" rows="5" cols="30" cssClass="textboxes" id="complaintReported" />
                        </td>
					</tr>
					<tr>
                        <td>
                            <label for="complaintDiagonsed">complaintDiagonsed</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td colspan="2">
							<form:textarea path="currentTransaction.complaintDiagonsed" rows="5" cols="30" cssClass="textboxes" id="complaintDiagonsed" />
                        </td>
						<td> &nbsp;</td>
						<td>
                            <label for="enggRemark">enggRemark</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td colspan="2">
							<form:textarea path="currentTransaction.enggRemark" rows="5" cols="30" cssClass="textboxes" id="enggRemark" />
                        </td>
					</tr>
					<tr>
                        <td>
                            <label for="repairAction">repairAction</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td colspan="2">
							<form:textarea path="currentTransaction.repairAction" rows="5" cols="30" cssClass="textboxes" id="repairAction" />
                        </td>
						<td> &nbsp;</td>
						<td>
                            <label for="notes">notes</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td colspan="2">
							<form:textarea path="currentTransaction.notes" rows="5" cols="30" cssClass="textboxes" id="notes" />
                        </td>
					</tr>
                    <tr>
                        <td colspan="7">
                            &nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td colspan="5">
                            &nbsp;
                        </td>
                        <td colspan="2">
                            <input class="btn" value="Save" type="button" onclick="javascript:save();" />
                            <input class="btn" value="Clear" type="button" onclick="javascript:clear();" />
                        </td>
                    </tr>
                </table>
            </div>
  </form:form>

  </body>
</html>