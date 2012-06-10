<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Add Transaction</title>
    <link rel="stylesheet" type="text/css" href="../css/mainStyles.css"/>
    <script type="text/javascript">

        //code to add New user
        function save() {
            //validate
            var custId = document.getElementsByName('customerId');
            //if(custId.value){

            //}
            document.forms[0].action = "SaveTxn.htm";
            document.forms[0].submit();
        }

        //code to edit a user
        function clear() {
            document.getElementById("TagNo").value = "";
            document.getElementById("customerId").value = "";
        }
    </script>
</head>
<body style="background: #A9A9A9 ;">
<form:form method="POST" commandName="transactionForm" name="transactionForm">
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
    <form:hidden name="loggedInUser" path="loggedInUser"/>
    <form:hidden name="loggedInRole" path="loggedInRole"/>
    <div id="content">
        <fieldset style="text-align:right;">
            <legend>Add Transaction</legend>
            <table class="myTable" width="100%">
                <tr>
                    <td>
                        <label for="TagNo" style="font-size: .70em;">Tag Number</label>
                        <label class="mandatory">*</label>
                    </td>
                    <td>
                        <form:input path="currentTransaction.TagNo" cssClass="textboxes" id="TagNo"/>
                    </td>
                    <td colspan="2"> &nbsp;</td>
                    <td>
                        <label for="productCategory" style="font-size: .70em;">Product Category</label>
                        <label class="mandatory">*</label>
                    </td>
                    <td>
                        <form:input path="currentTransaction.productCategory" cssClass="textboxes"
                                    id="productCategory"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="customerId" style="font-size: .70em;">Customer Id</label>
                        <label class="mandatory">*</label>
                    </td>
                    <td>
                        <form:input path="currentTransaction.customerId" cssClass="textboxes" id="customerId"/>
                    </td>
                    <td colspan="4"> &nbsp;</td>
                </tr>
                <tr>
                    <td>
                        <label for="makeId" style="font-size: .70em;">MakeId</label>
                        <label class="mandatory">*</label>
                    </td>
                    <td>
                        <form:select id="makeId" path="currentTransaction.makeId" tabindex="1"
                                         onkeypress="handleEnter(event);"
                                         cssClass="textboxes" cssStyle="height:20px">
                                <form:options items="${transactionForm.makeVOs}"
                                              itemValue="makeId" itemLabel="makeName"/>
                            </form:select>
                    </td>
                    <td colspan="2"> &nbsp;</td>
                    <td>
                        <label for="modelId" style="font-size: .70em;">ModelId</label>
                        <label class="mandatory">*</label>
                    </td>
                    <td>
                        <form:input path="currentTransaction.modelId" cssClass="textboxes" id="modelId"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="serialNo" style="font-size: .70em;">Serial No</label>
                        <label class="mandatory">*</label>
                    </td>
                    <td>
                        <form:input path="currentTransaction.serialNo" cssClass="textboxes" id="serialNo"/>
                    </td>
                    <td colspan="4"> &nbsp;</td>
                </tr>
                <tr>
                    <td>
                        <label for="accessories" style="font-size: .70em;">Accessories</label>
                        <label class="mandatory">*</label>
                    </td>
                    <td>
                        <form:textarea path="currentTransaction.accessories" rows="5" cols="30" cssClass="textboxes"
                                       id="accessories"/>
                    </td>
                    <td colspan="2"> &nbsp;</td>
                    <td>
                        <label for="complaintReported" style="font-size: .70em;">Complaint Reported</label>
                        <label class="mandatory">*</label>
                    </td>
                    <td>
                        <form:textarea path="currentTransaction.complaintReported" rows="5" cols="30"
                                       cssClass="textboxes"
                                       id="complaintReported"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="complaintDiagonsed" style="font-size: .70em;">Complaint Diagonsed</label>
                        <label class="mandatory">*</label>
                    </td>
                    <td>
                        <form:textarea path="currentTransaction.complaintDiagonsed" rows="5" cols="30"
                                       cssClass="textboxes"
                                       id="complaintDiagonsed"/>
                    </td>
                    <td colspan="2"> &nbsp;</td>
                    <td>
                        <label for="enggRemark" style="font-size: .70em;">Engineer Remarks</label>
                        <label class="mandatory">*</label>
                    </td>
                    <td>
                        <form:textarea path="currentTransaction.enggRemark" rows="5" cols="30" cssClass="textboxes"
                                       id="enggRemark"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="repairAction" style="font-size: .70em;">Repair Action</label>
                        <label class="mandatory">*</label>
                    </td>
                    <td>
                        <form:textarea path="currentTransaction.repairAction" rows="5" cols="30" cssClass="textboxes"
                                       id="repairAction"/>
                    </td>
                    <td colspan="2"> &nbsp;</td>
                    <td>
                        <label for="notes" style="font-size: .70em;">Notes</label>
                        <label class="mandatory">*</label>
                    </td>
                    <td>
                        <form:textarea path="currentTransaction.notes" rows="5" cols="30" cssClass="textboxes"
                                       id="notes"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="6">
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        &nbsp;
                    </td>
                    <td colspan="2">
                        <input class="btn" value="Save" type="button" onclick="javascript:save();"/>
                        <input class="btn" value="Clear" type="button" onclick="javascript:clear();"/>
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
</form:form>

</body>
</html>