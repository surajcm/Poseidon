<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Transaction</title>
        <link rel="stylesheet" type="text/css" href="../css/mainStyles.css"/>
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.css"/>
        <link rel="stylesheet" type="text/css" href="../css/ui-lightness/jquery-ui-1.8.21.custom.css"/>
        <script type="text/javascript" src="../js/jquery-1.7.2.min.js" language="javascript"></script>
        <script type="text/javascript" src="../js/jquery-ui-1.8.21.custom.min.js" language="javascript"></script>
        <style type="text/css">
            .textfieldMyStyle {
                border:3px double #CCCCCC;
                width: 200px;
                height:20px;
            }
            .foottable {
                margin:auto;
                top:50%;
                left:50%;
            }
            fieldset
            {
                text-align:right;
            }

            table
            {
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
    <body style="background: #A9A9A9 ;">
        <form:form method="POST" commandName="transactionForm" name="transactionForm" >
            <form:hidden name="loggedInUser" path="loggedInUser" />
            <form:hidden name="loggedInRole" path="loggedInRole" />
            <form:hidden name="id" path="currentTransaction.id" />
            <%@include file="/WEB-INF/jsp/myHeader.jsp" %>
            <div id="content">
                <fieldset>
                <legend>Edit Transaction</legend>
                <table class="myTable" width="100%" >
                    <tr>
                        <td style="text-align:right;">
                            <label for="productCategory"">Product Category :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="currentTransaction.productCategory" cssClass="textfieldMyStyle"
                                        id="productCategory"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="serialNo">Serial No :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="currentTransaction.serialNo" cssClass="textfieldMyStyle" id="serialNo"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="dateReported">Transaction Date :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="currentTransaction.dateReported" cssClass="textfieldMyStyle" id="dateReported"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="10"> &nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="customerId">Customer Id :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.customerId" cssClass="textfieldMyStyle" id="customerId"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td colspan="2">
                            <input class="btn" value="Edit Customer Details" type="button" onclick="javascript:editThisCustomer();"/>
                        </td>
                        <td colspan="4"> &nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="customerName">
                                Customer Name :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.customerName"cssClass="textfieldMyStyle"  cssStyle="background: #A9A9A9 ;"
                                        id="customerName" readonly="true"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="address1">
                                Address Line 1 :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="customerVO.address1" rows="5" cols="30" cssClass="textfieldMyStyle" cssStyle="background: #A9A9A9 ;"
                                           id="address1" readonly="true"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="address2">
                                Address Line 2 :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="customerVO.address2" rows="5" cols="30" cssClass="textfieldMyStyle" cssStyle="background: #A9A9A9 ;"
                                           id="address2" readonly="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="phoneNo">
                                Phone :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.phoneNo" cssClass="textfieldMyStyle" cssStyle="background: #A9A9A9 ;"
                                        id="phoneNo" readonly="true"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="mobile">
                                Mobile :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.mobile" cssClass="textfieldMyStyle" cssStyle="background: #A9A9A9 ;"
                                        id="mobile" readonly="true"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="email">
                                Email :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.email" cssClass="textfieldMyStyle" cssStyle="background: #A9A9A9 ;"
                                        id="email" readonly="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="contactPerson1">
                                Contact Person 1 :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.contactPerson1" cssClass="textfieldMyStyle" cssStyle="background: #A9A9A9 ;"
                                        id="contactPerson1" readonly="true"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="contactMobile1">
                                Mobile of Contact Person 1 :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.contactMobile1" cssClass="textfieldMyStyle" cssStyle="background: #A9A9A9 ;"
                                        id="contactMobile1" readonly="true"/>
                        </td>
                        <td colspan="4">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="contactPerson2">
                                Contact Person 2 :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.contactPerson2" cssClass="textfieldMyStyle" cssStyle="background: #A9A9A9 ;"
                                        id="contactPerson2" readonly="true"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="contactMobile2">
                                Mobile of Contact Person 2 :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:input path="customerVO.contactMobile2" cssClass="textfieldMyStyle" cssStyle="background: #A9A9A9 ;"
                                        id="contactMobile2" readonly="true"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td style="text-align:right;">
                            <label for="notes">
                                Notes :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="customerVO.notes" rows="5" cols="30" cssClass="textfieldMyStyle" cssStyle="background: #A9A9A9 ;"
                                           id="notes" readonly="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="10">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="makeId">Make :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:select id="makeId" path="currentTransaction.makeId" tabindex="1"
                                         onkeypress="handleEnter(event);" onchange="changeTheModel();"
                                         cssClass="textfieldMyStyle">
                                <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                <form:options items="${transactionForm.makeVOs}"
                                              itemValue="Id" itemLabel="makeName"/>
                            </form:select>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="modelId">Model :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:select id="modelId" path="currentTransaction.modelId" tabindex="1"
                                         onkeypress="handleEnter(event);"
                                         cssClass="textfieldMyStyle">
                                <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                <form:options items="${transactionForm.makeAndModelVOs}"
                                              itemValue="modelId" itemLabel="modelName"/>
                            </form:select>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="status">
                                Status :
                            </label>
                        </td>
                        <td style="text-align:left;">
                            <form:select id="status" path="currentTransaction.status"
                                         onkeypress="handleEnter(event);"
                                         cssClass="textfieldMyStyle">
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
                            <label for="accessories">Accessories :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="currentTransaction.accessories" rows="5" cols="30" cssClass="textfieldMyStyle"
                                           id="accessories"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="complaintReported">Complaint Reported :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="currentTransaction.complaintReported" rows="5" cols="30"
                                           cssClass="textfieldMyStyle"
                                           id="complaintReported"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="complaintDiagonsed">Complaint Diagnosed :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="currentTransaction.complaintDiagonsed" rows="5" cols="30"
                                           cssClass="textfieldMyStyle"
                                           id="complaintDiagonsed"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <label for="enggRemark">Engineer Remarks :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="currentTransaction.enggRemark" rows="5" cols="30" cssClass="textfieldMyStyle"
                                           id="enggRemark"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="repairAction">Repair Action :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="currentTransaction.repairAction" rows="5" cols="30" cssClass="textfieldMyStyle"
                                           id="repairAction"/>
                        </td>
                        <td colspan="2"> &nbsp;</td>
                        <td style="text-align:right;">
                            <label for="notes">Notes :</label>
                            <label class="mandatory">*</label>
                        </td>
                        <td style="text-align:left;">
                            <form:textarea path="currentTransaction.notes" rows="5" cols="30" cssClass="textfieldMyStyle"
                                           id="notes"/>
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
                            <input class="btn" value="Update" type="button" onclick="javascript:update();"/>
                            <input class="btn" value="Cancel" type="button" onclick="javascript:cancel();"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>
    </form:form>
    </body>
</html>
