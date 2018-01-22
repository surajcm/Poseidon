<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Transaction</title>
        <style type="text/css">
            table {
                margin: auto;
                top: 50%;
                left: 50%;
            }
        </style>
        <script type="text/javascript">
            var req;

            function save() {
                if (document.getElementById('dateReported').value.length == 0) {
                    alert("Please enter a valid Transaction Date");
                } else if (document.getElementById('productCategory').value.length == 0) {
                    alert("Please enter a valid Product Category");
                } else if (document.getElementById('serialNo').value.length == 0) {
                    alert("Please enter a valid Serial No");
                } else if ((document.getElementById('customerId').value.length == 0)
                        && (document.getElementById('customerName').value.length == 0
                        || document.getElementById('mobile').value.length == 0)) {
                    alert("Please enter a valid Customer Details");
                } else if (document.getElementById('makeId').value.length == 0) {
                    alert("Please enter a valid Make detail");
                } else if (document.getElementById('modelId').value.length == 0) {
                    alert("Please enter a valid Model detail");
                } else {
                    document.forms[0].action = "SaveTxn.htm";
                    document.forms[0].submit();
                }
            }

            //code to edit a user
            function clearOut() {
                document.getElementById("dateReported").value = "";
                document.getElementById("productCategory").value = "";
                document.getElementById("serialNo").value = "";
                document.getElementById("customerId").value = "";
                document.getElementById("customerName").value = "";
                document.getElementById("address1").value = "";
                document.getElementById("address2").value = "";
                document.getElementById("phoneNo").value = "";
                document.getElementById("mobile").value = "";
                document.getElementById("email").value = "";
                document.getElementById("contactPerson1").value = "";
                document.getElementById("contactMobile1").value = "";
                document.getElementById("contactPerson2").value = "";
                document.getElementById("contactMobile2").value = "";
                document.getElementById("notes").value = "";
                document.getElementById("makeId").value = document.getElementById('makeId').options[0].value;
                document.getElementById("modelId").value = document.getElementById('modelId').options[0].value;
                document.getElementById("accessories").value = "";
                document.getElementById("complaintReported").value = "";
                document.getElementById("complaintDiagonsed").value = "";
                document.getElementById("enggRemark").value = "";
                document.getElementById("repairAction").value = "";
                document.getElementById("notes").value = "";
            }

            function changeTheModel() {
                var selectMakeId = document.transactionForm.makeId.value;
                var url = "${contextPath}/txs/UpdateModelAjax.htm";
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
    </head>
    <body>
        <form:form method="POST" commandName="transactionForm" name="transactionForm">
            <form:hidden name="loggedInUser" path="loggedInUser"/>
            <form:hidden name="loggedInRole" path="loggedInRole"/>
            <%@include file="../myHeader.jsp" %>
            <div class="container">
                <div class="panel panel-primary">
                    <div class="panel-heading">Add Transaction</div>
                    <table style="margin:auto;top:50%;left:50%;">
                        <tr>
                            <td style="text-align:right;">
                                <label for="productCategory" class="control-label">Product Category :</label>
                                <label class="mandatory">*</label>
                            </td>
                            <td style="text-align:left;">
                                <form:input cssClass="form-control" path="currentTransaction.productCategory" id="productCategory"/>
                            </td>
                            <td colspan="2"> &nbsp;</td>
                            <td style="text-align:right;">
                                <label for="serialNo" class="control-label">Serial No :</label>
                                <label class="mandatory">*</label>
                            </td>
                            <td style="text-align:left;">
                                <form:input cssClass="form-control" path="currentTransaction.serialNo" id="serialNo"/>
                            </td>
                            <td colspan="2"> &nbsp;</td>
                            <td style="text-align:right;">
                                <label for="dateReported" class="control-label">Transaction Date :</label>
                                <label class="mandatory">*</label>
                            </td>
                            <td style="text-align:left;">
                                <div class="input-group">
                                    <form:input path="currentTransaction.dateReported" class="date-picker form-control" id = "dateReported" />
                                    <label for="reported" class="input-group-addon btn"><span class="fa fa-calendar"></span>
                                </div>
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
                                <form:input cssClass="form-control" path="customerVO.customerId" id="customerId"/>
                            </td>
                            <td colspan="8"><label style="font-size: .70em;color:blue;" class="control-label">Enter Customer Details in case of New Customer</label>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align:right;">
                                <label for="customerName" class="control-label">
                                    Customer Name :
                                </label>
                            </td>
                            <td style="text-align:left;">
                                <form:input path="customerVO.customerName" cssClass="form-control" id="customerName"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:right;">
                                <label for="address1" class="control-label">
                                    Address Line 1 :
                                </label>
                            </td>
                            <td style="text-align:left;">
                                <form:textarea path="customerVO.address1" cssClass="form-control" id="address1"/>
                            </td>
                            <td colspan="2"> &nbsp;</td>
                            <td style="text-align:right;">
                                <label for="address2" class="control-label">
                                    Address Line 2 :
                                </label>
                            </td>
                            <td style="text-align:left;">
                                <form:textarea path="customerVO.address2" cssClass="form-control" id="address2"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align:right;">
                                <label for="phoneNo" class="control-label">
                                    Phone :
                                </label>
                            </td>
                            <td style="text-align:left;">
                                <form:input path="customerVO.phoneNo" cssClass="form-control" id="phoneNo"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:right;">
                                <label for="mobile" class="control-label">
                                    Mobile :
                                </label>
                            </td>
                            <td style="text-align:left;">
                                <form:input path="customerVO.mobile" cssClass="form-control" id="mobile"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:right;">
                                <label for="email" class="control-label">
                                    Email :
                                </label>
                            </td>
                            <td style="text-align:left;">
                                <form:input path="customerVO.email" cssClass="form-control" id="email"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align:right;">
                                <label for="contactPerson1" class="control-label">
                                    Contact Person 1 :
                                </label>
                            </td>
                            <td style="text-align:left;">
                                <form:input path="customerVO.contactPerson1" cssClass="form-control" id="contactPerson1"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:right;">
                                <label for="contactMobile1" class="control-label">
                                    Mobile of Contact Person 1 :
                                </label>
                            </td>
                            <td style="text-align:left;">
                                <form:input path="customerVO.contactMobile1" cssClass="form-control"
                                            id="contactMobile1"/>
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
                                <form:input path="customerVO.contactPerson2" cssClass="form-control"
                                            id="contactPerson2"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:right;">
                                <label for="contactMobile2" class="control-label">
                                    Mobile of Contact Person 2 :
                                </label>
                            </td>
                            <td style="text-align:left;">
                                <form:input path="customerVO.contactMobile2" cssClass="form-control"
                                            id="contactMobile2"/>
                            </td>
                            <td colspan="2">&nbsp;</td>
                            <td style="text-align:right;">
                                <label for="notes" class="control-label">
                                    Notes :
                                </label>
                            </td>
                            <td style="text-align:left;">
                                <form:textarea path="customerVO.notes" cssClass="form-control" id="notes"/>
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
                            <td colspan="2"><a id="gotoNewModel" href="${contextPath}/make/addModel.htm"> Add New Model</a></td>
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
                                <label for="complaintDiagonsed" class="control-label">Complaint Diagnosed :</label>
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
                                <input class="btn btn-primary btn-success" value="Save" type="button" onclick="javascript:save();"/>
                                <input class="btn btn-primary" value="Clear" type="button" onclick="javascript:clearOut();"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </form:form>
        <script>
            $(document).ready(function() {
                $(function() {
                    $("#dateReported").datepicker();
                });
            });
        </script>
    </body>
</html>