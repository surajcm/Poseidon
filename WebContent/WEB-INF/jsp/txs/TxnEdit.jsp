<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Transaction</title>
        <link rel="stylesheet" type="text/css" href="../css/ui-lightness/jquery-ui-1.8.21.custom.css"/>
        <script type="text/javascript" src="../js/jquery-1.7.2.min.js" language="javascript"></script>
        <script type="text/javascript" src="../js/jquery-ui-1.8.21.custom.min.js" language="javascript"></script>
        <style type="text/css">
            table {
                margin:auto;
                top:50%;
                left:50%;
            }
        </style>
        <script type="text/javascript">
            var req;

            function update() {
                 if(document.getElementById('productCategory').value.length == 0){
                    alert("Please enter a valid Product Category");
                }else if(document.getElementById('serialNo').value.length == 0){
                    alert("Please enter a valid Serial No");
                }else if(document.getElementById('customerId').value.length == 0){
                        if(document.getElementById('customerName').value.length == 0
                                || document.getElementById('mobile').value.length == 0){
                            alert("Please enter a valid Customer Details");
                        }
                }else if(document.getElementById('makeId').value.length == 0){
                    alert("Please enter a valid Make detail");
                }else if(document.getElementById('modelId').value.length == 0){
                    alert("Please enter a valid Model detail");
                } else {
                    document.forms[0].action = "updateTxn.htm";
                    document.forms[0].submit();
                }
            }

            function cancel() {
                document.forms[0].action = "List.htm";
                document.forms[0].submit();
            }
            function editThisCustomer(){
                if(document.getElementById("customerId") != null){
                    document.forms[0].action = "<%=request.getContextPath()%>" + "/customer/editCustomer.htm"+
                            "?customerId=" +document.getElementById("customerId").value;
                    document.forms[0].submit();
                }else{
                    alert("Unable to get the customer Details !!!");
                }
            }

            function changeTheModel() {
                var selectMakeId = document.transactionForm.makeId.value;
                var url = "<%=request.getContextPath()%>" + "/txs/UpdateModelAjax.htm";
                url = url + "?selectMakeId=" + selectMakeId;
                bustcacheparameter = (url.indexOf("?") != -1) ? "&" + new Date().getTime() : "?" + new Date().getTime();
                createAjaxRequest();
                if (req) {
                    req.onreadystatechange = stateChange;
                    req.open("POST", url + bustcacheparameter, true);
                    req.send(url + bustcacheparameter);
                }
            }

            function createAjaxRequest() {
                if (window.XMLHttpRequest) {
                    req = new XMLHttpRequest();
                } else if (window.ActiveXObject) {
                    try {
                        req = new ActiveXObject("Msxml2.XMLHTTP");
                    } catch (e) {
                        try {
                            req = new ActiveXObject("Microsoft.XMLHTTP");
                        } catch (e) {
                        }
                    }
                }
            }

            function stateChange() {
                if (req.readyState == 4 && (req.status == 200 || window.location.href.indexOf("http") == -1)) {
                    textReturned = req.responseText;
                    if (textReturned != "") {
                        var fullContent = textReturned.split("#start#");
                        var resultIds = new Array();
                        var resultNames = new Array();
                        var k = 0;
                        var j = 0;
                        var t = 0;

                        for (j = 0; j < fullContent.length; j++) {
                            if (fullContent[j].length > 0) {
                                resultIds[k] = fullContent[j].split("#id#")[1];
                                var testing = fullContent[j].split("#id#")[2];
                                resultNames[k] = testing.split("#modelName#")[1];
                                k++;
                            }
                        }
                        var l = 0;
                        document.transactionForm.modelId.options.length = resultIds.length - 1;
                        document.transactionForm.modelId.options[0] = new Option("<---- Select ---->", "");
                        for (var i = 1; i <= (resultIds.length); i++) {
                            document.transactionForm.modelId.options[i] = new Option(resultNames[i - 1], resultIds[i - 1]);
                        }
                    } else {
                        document.transactionForm.modelId.options.length = 0;
                        document.transactionForm.modelId.options[0] = new Option("<---- Select ---->", "");
                    }
                }
            }
        </script>
        <script>
            $(function () {
                $("#dateReported").datepicker({ dateFormat:"dd/mm/yy" });
            });
        </script>
    </head>
    <body>
        <form:form method="POST" commandName="transactionForm" name="transactionForm" >
            <form:hidden name="loggedInUser" path="loggedInUser" />
            <form:hidden name="loggedInRole" path="loggedInRole" />
            <form:hidden name="id" path="currentTransaction.id" />
            <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
            <div class="container">
            <div class="panel panel-primary">
                <div class="panel-heading">Edit Transaction</div>
                <table style="margin:auto;top:50%;left:50%;">
                    <tr>
                        <td style="text-align:right;">
                            <label for="productCategory" class="control-label">Product Category :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="currentTransaction.productCategory" id="productCategory"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="serialNo" class="control-label">Serial No :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="currentTransaction.serialNo" id="serialNo" cssClass="form-control"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="dateReported" class="control-label">Transaction Date :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="currentTransaction.dateReported" id="dateReported" cssClass="form-control"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="10"> &nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="customerId" class="control-label">Customer Id :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.customerId" id="customerId" cssClass="form-control"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td colspan="2">
                            <input class="btn btn-primary" value="Edit Customer Details" type="button" onclick="javascript:editThisCustomer();"/>
                        </td>
                        <td colspan="4"> &nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="customerName" class="control-label">
                                Customer Name :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.customerName" id="customerName" cssStyle="background: #A9A9A9 ;" cssClass="form-control" readonly="true"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="address1" class="control-label">
                                Address Line 1 :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="customerVO.address1" cssStyle="background: #A9A9A9 ;" cssClass="form-control"
                                           id="address1" readonly="true"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="address2" class="control-label">
                                Address Line 2 :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="customerVO.address2" cssStyle="background: #A9A9A9 ;" cssClass="form-control"
                                           id="address2" readonly="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="phoneNo" class="control-label">
                                Phone :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.phoneNo" cssStyle="background: #A9A9A9 ;" cssClass="form-control"
                                        id="phoneNo" readonly="true"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="mobile" class="control-label">
                                Mobile :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.mobile" cssStyle="background: #A9A9A9 ;" cssClass="form-control"
                                        id="mobile" readonly="true"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="email" class="control-label">
                                Email :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.email" cssStyle="background: #A9A9A9 ;" cssClass="form-control"
                                        id="email" readonly="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="contactPerson1" class="control-label">
                                Contact Person 1 :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.contactPerson1" cssStyle="background: #A9A9A9 ;" cssClass="form-control"
                                        id="contactPerson1" readonly="true"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="contactMobile1" class="control-label">
                                Mobile of Contact Person 1 :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.contactMobile1" cssStyle="background: #A9A9A9 ;" cssClass="form-control"
                                        id="contactMobile1" readonly="true"/>
                        </td>
                        <td colspan="4">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="contactPerson2" class="control-label">
                                Contact Person 2 :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.contactPerson2" cssStyle="background: #A9A9A9 ;" cssClass="form-control"
                                        id="contactPerson2" readonly="true"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="contactMobile2" class="control-label">
                                Mobile of Contact Person 2 :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.contactMobile2" cssStyle="background: #A9A9A9 ;" cssClass="form-control"
                                        id="contactMobile2" readonly="true"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;" class="control-label">
                            <label for="notes">
                                Notes :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="customerVO.notes" cssStyle="background: #A9A9A9 ;" cssClass="form-control"
                                           id="notes" readonly="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="10">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="makeId" class="control-label">Make :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:select id="makeId" path="currentTransaction.makeId" tabindex="1" cssClass="form-control"
                                         onkeypress="handleEnter(event);" onchange="changeTheModel();">
                                <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                <form:options items="${transactionForm.makeVOs}"
                                              itemValue="Id" itemLabel="makeName"/>
                            </form:select>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="modelId" class="control-label">Model :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:select id="modelId" path="currentTransaction.modelId" tabindex="2" cssClass="form-control"
                                         onkeypress="handleEnter(event);">
                                <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                <form:options items="${transactionForm.makeAndModelVOs}"
                                              itemValue="modelId" itemLabel="modelName"/>
                            </form:select>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="status" class="control-label">
                                Status :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:select id="status" path="currentTransaction.status" cssClass="form-control"
                                         onkeypress="handleEnter(event);">
                                <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                <form:options items="${transactionForm.statusList}" />
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="10">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="accessories" class="control-label">Accessories :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="currentTransaction.accessories" cssClass="form-control" id="accessories"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="complaintReported" class="control-label">Complaint Reported :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="currentTransaction.complaintReported" cssClass="form-control" id="complaintReported"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="complaintDiagonsed">Complaint Diagnosed :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="currentTransaction.complaintDiagonsed" cssClass="form-control" id="complaintDiagonsed"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="enggRemark" class="control-label">Engineer Remarks :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="currentTransaction.enggRemark" cssClass="form-control" id="enggRemark"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="repairAction" class="control-label">Repair Action :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="currentTransaction.repairAction" cssClass="form-control" id="repairAction"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="notes" class="control-label">Notes :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="currentTransaction.notes" cssClass="form-control" id="notes"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="10">
                            &nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td colspan="8">
                            &nbsp;
                        </td>
                        <td colspan="2">
                            <input class="btn btn-primary btn-success" value="Update" type="button" onclick="javascript:update();"/>
                            <input class="btn btn-primary" value="Cancel" type="button" onclick="javascript:cancel();"/>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </form:form>
    </body>
</html>
