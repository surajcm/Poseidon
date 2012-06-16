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
        var req;
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

        function changeTheModel(){
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
            if (window.XMLHttpRequest){
                req = new XMLHttpRequest() ;
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
                if(textReturned != "") {
                    var fullContent = textReturned.split("#start#");
                    var resultIds = new Array();
                    var resultNames = new Array();
                    var k = 0;
                    var j = 0;
                    var t = 0;

                    for (j = 0; j < fullContent.length; j++) {
                        if(fullContent[j].length > 0 ) {
                            resultIds[k] = fullContent[j].split("#id#")[1];
                            var testing = fullContent[j].split("#id#")[2];
                            resultNames[k] = testing.split("#modelName#")[1];
                            k++;
                        }
                    }
                    var l =0;
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
<body style="background: #A9A9A9 ;">
<form:form method="POST" commandName="transactionForm" name="transactionForm">
<%@include file="/WEB-INF/jsp/myHeader.jsp" %>
<form:hidden name="loggedInUser" path="loggedInUser"/>
<form:hidden name="loggedInRole" path="loggedInRole"/>
<div id="content">
<fieldset>
<legend>Add Transaction</legend>
<table class="myTable" width="100%" >
<tr>
    <td style="text-align:right;">
        <label for="TagNo" style="font-size: .70em;">Tag Number :</label>
        <label class="mandatory">*</label>
    </td>
    <td style="text-align:left;">
        <form:input path="currentTransaction.TagNo" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="TagNo"/>
    </td>
    <td colspan="2"> &nbsp;</td>
    <td style="text-align:right;">
        <label for="productCategory" style="font-size: .70em;">Product Category :</label>
        <label class="mandatory">*</label>
    </td>
    <td style="text-align:left;">
        <form:input path="currentTransaction.productCategory" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;"
                    id="productCategory"/>
    </td>
    <td colspan="2"> &nbsp;</td>
    <td style="text-align:right;">
        <label for="serialNo" style="font-size: .70em;">Serial No :</label>
        <label class="mandatory">*</label>
    </td>
    <td style="text-align:left;">
        <form:input path="currentTransaction.serialNo" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="serialNo"/>
    </td>
</tr>
<tr>
    <td colspan="10"> &nbsp;</td>
</tr>
<tr>
    <td style="text-align:right;">
        <label for="customerId" style="font-size: .70em;">Customer Id :</label>
        <label class="mandatory">*</label>
    </td>
    <td style="text-align:left;">
        <form:input path="customerVO.customerId" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="customerId"/>
    </td>
    <td colspan="8"> <label style="font-size: .70em;color:blue;">Enter Customer Details in case of New Customer</label> </td>
</tr>
<tr>
    <td style="text-align:right;">
        <label for="customerName" style="font-size: .70em;">
            Customer Name :
        </label>
    </td>
    <td style="text-align:left;">
        <form:input path="customerVO.customerName" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="customerName"/>
    </td>
    <td colspan="2">&nbsp;</td>
    <td style="text-align:right;">
        <label for="address1" style="font-size: .70em;">
            Address Line 1 :
        </label>
    </td>
    <td style="text-align:left;">
        <form:textarea path="customerVO.address1" rows="5" cols="30" cssStyle="border:3px double #CCCCCC; width: 200px;height:40px;"
                       id="address1"/>
    </td>
    <td colspan="2"> &nbsp;</td>
    <td style="text-align:right;">
        <label for="address2" style="font-size: .70em;">
            Address Line 2 :
        </label>
    </td>
    <td style="text-align:left;">
        <form:textarea path="customerVO.address2" rows="5" cols="30" cssStyle="border:3px double #CCCCCC; width: 200px;height:40px;"
                       id="address2"/>
    </td>
</tr>
<tr>
    <td style="text-align:right;">
        <label for="phoneNo" style="font-size: .70em;">
            Phone :
        </label>
    </td>
    <td style="text-align:left;">
        <form:input path="customerVO.phoneNo" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="phoneNo"/>
    </td>
    <td colspan="2">&nbsp;</td>
    <td style="text-align:right;">
        <label for="mobile" style="font-size: .70em;">
            Mobile :
        </label>
    </td>
    <td style="text-align:left;">
        <form:input path="customerVO.mobile" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="mobile"/>
    </td>
    <td colspan="2">&nbsp;</td>
    <td style="text-align:right;">
        <label for="email" style="font-size: .70em;">
            Email :
        </label>
    </td>
    <td style="text-align:left;">
        <form:input path="customerVO.email" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;" id="email"/>
    </td>
</tr>
<tr>
    <td style="text-align:right;">
        <label for="contactPerson1" style="font-size: .70em;">
            Contact Person 1 :
        </label>
    </td>
    <td style="text-align:left;">
        <form:input path="customerVO.contactPerson1" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;"
                    id="contactPerson1"/>
    </td>
    <td colspan="2">&nbsp;</td>
    <td style="text-align:right;">
        <label for="contactMobile1" style="font-size: .70em;">
            Mobile of Contact Person 1 :
        </label>
    </td>
    <td style="text-align:left;">
        <form:input path="customerVO.contactMobile1" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;"
                    id="contactMobile1"/>
    </td>
    <td colspan="4">&nbsp;</td>
</tr>
<tr>
    <td style="text-align:right;">
        <label for="contactPerson2" style="font-size: .70em;">
            Contact Person 2 :
        </label>
    </td>
    <td style="text-align:left;">
        <form:input path="customerVO.contactPerson2" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;"
                    id="contactPerson2"/>
    </td>
    <td colspan="2">&nbsp;</td>
    <td style="text-align:right;">
        <label for="contactMobile2" style="font-size: .70em;">
            Mobile of Contact Person 2 :
        </label>
    </td>
    <td style="text-align:left;">
        <form:input path="customerVO.contactMobile2" cssStyle="border:3px double #CCCCCC; width: 200px;height:20px;"
                    id="contactMobile2"/>
    </td>
    <td colspan="2">&nbsp;</td>
    <td style="text-align:right;">
        <label for="notes" style="font-size: .70em;">
            Notes :
        </label>
    </td>
    <td style="text-align:left;">
        <form:textarea path="customerVO.notes" rows="5" cols="30" cssStyle="border:3px double #CCCCCC; width: 200px;height:40px;"
                       id="notes"/>
    </td>
</tr>
<tr>
    <td colspan="10">&nbsp;</td>
</tr>
<tr>
    <td style="text-align:right;">
        <label for="makeId" style="font-size: .70em;">Make :</label>
        <label class="mandatory">*</label>
    </td>
    <td style="text-align:left;">
        <form:select id="makeId" path="currentTransaction.makeId" tabindex="1"
                     onkeypress="handleEnter(event);" onchange="changeTheModel();"
                     cssStyle="border:3px double #CCCCCC; width: 200px;height:25px;">
            <form:options items="${transactionForm.makeVOs}"
                          itemValue="Id" itemLabel="makeName"/>
        </form:select>
    </td>
    <td colspan="2"> &nbsp;</td>
    <td style="text-align:right;">
        <label for="modelId" style="font-size: .70em;">Model :</label>
        <label class="mandatory">*</label>
    </td>
    <td style="text-align:left;">
        <form:select id="modelId" path="currentTransaction.modelId" tabindex="1"
                     onkeypress="handleEnter(event);"
                     cssStyle="border:3px double #CCCCCC; width: 200px;height:25px;">
            <form:options items="${transactionForm.makeAndModelVOs}"
                          itemValue="modelId" itemLabel="modelName"/>
        </form:select>
    </td>
    <td colspan="2"> &nbsp;</td>
    <td colspan="2"> <a id="gotoNewModel" href="<%=request.getContextPath()%>/make/addModel.htm" > Add New Model</a></td>
</tr>
<tr>
    <td colspan="10">&nbsp;</td>
</tr>
<tr>
    <td style="text-align:right;">
        <label for="accessories" style="font-size: .70em;">Accessories :</label>
        <label class="mandatory">*</label>
    </td>
    <td style="text-align:left;">
        <form:textarea path="currentTransaction.accessories" rows="5" cols="30" cssStyle="border:3px double #CCCCCC; width: 200px;height:40px;"
                       id="accessories"/>
    </td>
    <td colspan="2"> &nbsp;</td>
    <td style="text-align:right;">
        <label for="complaintReported" style="font-size: .70em;">Complaint Reported :</label>
        <label class="mandatory">*</label>
    </td>
    <td style="text-align:left;">
        <form:textarea path="currentTransaction.complaintReported" rows="5" cols="30"
                       cssStyle="border:3px double #CCCCCC; width: 200px;height:40px;"
                       id="complaintReported"/>
    </td>
    <td colspan="2"> &nbsp;</td>
    <td style="text-align:right;">
        <label for="complaintDiagonsed" style="font-size: .70em;">Complaint Diagnosed :</label>
        <label class="mandatory">*</label>
    </td>
    <td style="text-align:left;">
        <form:textarea path="currentTransaction.complaintDiagonsed" rows="5" cols="30"
                       cssStyle="border:3px double #CCCCCC; width: 200px;height:40px;"
                       id="complaintDiagonsed"/>
    </td>
</tr>
<tr>
    <td style="text-align:right;">
        <label for="enggRemark" style="font-size: .70em;">Engineer Remarks :</label>
        <label class="mandatory">*</label>
    </td>
    <td style="text-align:left;">
        <form:textarea path="currentTransaction.enggRemark" rows="5" cols="30" cssStyle="border:3px double #CCCCCC; width: 200px;height:40px;"
                       id="enggRemark"/>
    </td>
    <td colspan="2"> &nbsp;</td>
    <td style="text-align:right;">
        <label for="repairAction" style="font-size: .70em;">Repair Action :</label>
        <label class="mandatory">*</label>
    </td>
    <td style="text-align:left;">
        <form:textarea path="currentTransaction.repairAction" rows="5" cols="30" cssStyle="border:3px double #CCCCCC; width: 200px;height:40px;"
                       id="repairAction"/>
    </td>
    <td colspan="2"> &nbsp;</td>
    <td style="text-align:right;">
        <label for="notes" style="font-size: .70em;">Notes :</label>
        <label class="mandatory">*</label>
    </td>
    <td style="text-align:left;">
        <form:textarea path="currentTransaction.notes" rows="5" cols="30" cssStyle="border:3px double #CCCCCC; width: 200px;height:40px;"
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