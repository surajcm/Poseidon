<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Transaction List</title>
<link rel="stylesheet" type="text/css" href="../css/mainStyles.css"/>
<link rel="stylesheet" type="text/css" href="../css/ui-lightness/jquery-ui-1.8.21.custom.css"/>
<script type="text/javascript" src="../js/jquery-1.7.2.min.js" language="javascript" ></script>
<script type="text/javascript" src="../js/jquery-ui-1.8.21.custom.min.js" language="javascript" ></script>
<style type="text/css">

    .info, .success, .error {
        border: 1px solid;
        margin: 10px 0px;
        padding: 15px 10px 15px 50px;
        background-repeat: no-repeat;
        background-position: 10px center;
    }

    .info {
        color: #00529B;
        background-color: #BDE5F8;
        background-image: url( "<%=request.getContextPath()%>/images/Info.png" );
    }

    .success {
        color: #4F8A10;
        background-color: #DFF2BF;
        background-image: url( '<%=request.getContextPath()%>/images/Success.png' );
    }

    .error {
        color: #D8000C;
        background-color: #FFBABA;
        background-image: url( '<%=request.getContextPath()%>/images/Error.png' );
    }
	
	.textfieldMyStyle 
	{
		border:3px double #CCCCCC;
		width: 200px;
		height:20px;
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
	html {
		  font-size: 100%;
		  -webkit-text-size-adjust: 100%;
		  -ms-text-size-adjust: 100%;
		}
		button,
		input,
		select,
		textarea {
		  margin: 0;
		  font-size: 100%;
		  vertical-align: middle;
		}

		button,
		input {
		  *overflow: visible;
		  line-height: normal;
		}

		button::-moz-focus-inner,
		input::-moz-focus-inner {
		  padding: 0;
		  border: 0;
		}

		button,
		input[type="button"],
		input[type="reset"],
		input[type="submit"] {
		  cursor: pointer;
		  -webkit-appearance: button;
		}
		input[type="search"] {
		  -webkit-box-sizing: content-box;
			 -moz-box-sizing: content-box;
				  box-sizing: content-box;
		  -webkit-appearance: textfield;
		}

		input[type="search"]::-webkit-search-decoration,
		input[type="search"]::-webkit-search-cancel-button {
		  -webkit-appearance: none;
		}

		textarea {
		  overflow: auto;
		  vertical-align: top;
		}

		.clearfix {
		  *zoom: 1;
		}

		.clearfix:before,
		.clearfix:after {
		  display: table;
		  line-height: 0;
		  content: "";
		}

		.clearfix:after {
		  clear: both;
		}

		.hide-text {
		  font: 0/0 a;
		  color: transparent;
		  text-shadow: none;
		  background-color: transparent;
		  border: 0;
		}

		.input-block-level {
		  display: block;
		  width: 100%;
		  min-height: 30px;
		  -webkit-box-sizing: border-box;
			 -moz-box-sizing: border-box;
				  box-sizing: border-box;
		}

		body {
		  margin: 0;
		  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
		  font-size: 14px;
		  line-height: 20px;
		  color: #333333;
		  background-color: #ffffff;
		}
	
</style>
<script type="text/javascript">
var req;

function addNew() {
    document.forms[0].action = "AddTxn.htm";
    document.forms[0].submit();
}
//preventing multiple checks
function checkCall(e) {
    var min = e.value;
    var checks = document.getElementsByName('checkField');
    for (var i = 0; i < checks.length; i++) {
        if (checks[i].value != min) {
            checks[i].checked = false;
        }
    }
}
function search() {
    document.forms[0].action = "SearchTxn.htm";
    document.forms[0].submit();
}
function isNumber(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

function clearOut() {
    document.getElementById('TagNo').value = "";
    document.getElementById('CustomerName').value = "";
    document.getElementById('startDate').value = "";
    document.getElementById('endDate').value = "";
    document.getElementById('SerialNo').value = "";
    document.getElementById("makeId").value = document.getElementById('makeId').options[0].value;
    document.getElementById("modelId").value = document.getElementById('modelId').options[0].value;
    document.getElementById("Status").value = document.getElementById('Status').options[0].value;
    document.getElementById('includes').checked = false;
    document.getElementById('startswith').checked = false;
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
            document.transactionForm.modelId.options[0] = new Option("<-- Select -->", "");
            for (var i = 1; i <= (resultIds.length); i++) {
                document.transactionForm.modelId.options[i] = new Option(resultNames[i - 1], resultIds[i - 1]);
            }
        } else {
            document.transactionForm.modelId.options.length = 0;
            document.transactionForm.modelId.options[0] = new Option("<-- Select -->", "");
        }
    }
}
//validation before edit
function editMe() {
    var check = 'false';
    var count = 0;
    // get all check boxes
    var checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            editRow();
        } else {
            for (var i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    check = 'true';
                    count = count + 1;
                }
            }
            //check for validity
            if (check = 'true') {
                if (count == 1) {
                    editRow();
                } else {
                    alert(" Only one row can be edited at a time, please select one row ");
                }
            } else {
                alert(" No rows selected, please select one row ");
            }
        }
    }
}

//real edit
function editRow() {
    var userRow;
    var checks = document.getElementsByName('checkField');
    if (checks.checked) {
        userRow = document.getElementById("myTable").rows[0];
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        document.forms[0].action = "EditTxn.htm";
        document.forms[0].submit();
    } else {
        for (var i = 0; i < checks.length; i++) {
            if (checks[i].checked) {
                userRow = document.getElementById("myTable").rows[i + 1];
            }
        }
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        document.forms[0].action = "EditTxn.htm";
        document.forms[0].submit();
    }
}

// delete
function deleteTxn() {
    var check = 'false';
    var count = 0;
    // get all check boxes
    var checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            deleteRow();
        } else {
            for (var i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    check = 'true';
                    count = count + 1;
                }
            }
            //check for validity
            if (check = 'true') {
                if (count == 1) {
                    deleteRow();
                } else {
                    alert(" Only one row can be deleted at a time, please select one row ");
                }
            } else {
                alert(" No rows selected, please select one row ");
            }
        }
    }
}

//code to delete a user
function deleteRow() {
    var answer = confirm(" Are you sure you wanted to delete the Txn ");
    if (answer) {
        //if yes then delete
        var userRow;
        var checks = document.getElementsByName('checkField');
        if (checks.checked) {
            userRow = document.getElementById("myTable").rows[0];
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            document.forms[0].action = "DeleteTxn.htm";
            document.forms[0].submit();
        } else {
            for (var i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    userRow = document.getElementById("myTable").rows[i + 1];
                }
            }
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            document.forms[0].action = "DeleteTxn.htm";
            document.forms[0].submit();
        }
    }

}


function invoiceTxn() {
    var check = 'false';
    var count = 0;
    // get all check boxes
    var checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            invoiceRow();
        } else {
            for (var i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    check = 'true';
                    count = count + 1;
                }
            }
            //check for validity
            if (check = 'true') {
                if (count == 1) {
                    invoiceRow();
                } else {
                    alert(" Only one row can be invoiced at a time, please select one row ");
                }
            } else {
                alert(" No rows selected, please select one row ");
            }
        }
    }
}

function invoiceRow() {

    var userRow;
    var checks = document.getElementsByName('checkField');
    if (checks.checked) {
        userRow = document.getElementById("myTable").rows[0];
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        document.forms[0].action="<%=request.getContextPath()%>" + "/invoice/InvoiceTxn.htm";
        document.forms[0].submit();
    } else {
        for (var i = 0; i < checks.length; i++) {
            if (checks[i].checked) {
                userRow = document.getElementById("myTable").rows[i + 1];
            }
        }
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        document.forms[0].action="<%=request.getContextPath()%>" + "/invoice/InvoiceTxn.htm";
        document.forms[0].submit();
    }
}

function hideAlerts(){
    var options = {};
    $( "#effect" ).hide( "blind", options, 8000);
}


</script>
<script>
    $(function() {
        $( "#startDate" ).datepicker({ dateFormat: "dd/mm/yy" });
        $( "#endDate" ).datepicker({ dateFormat: "dd/mm/yy" });
    });
</script>
</head>
<body style="background: #A9A9A9 ;" onload="javascript:hideAlerts()">
<form:form method="POST" commandName="transactionForm" name="transactionForm" action="List.htm">
    <form:hidden name="loggedInUser" path="loggedInUser"/>
    <form:hidden name="loggedInRole" path="loggedInRole"/>
    <input type="hidden" name="id" id="id"/>
    <%@include file="/WEB-INF/jsp/myHeader.jsp" %>

    <div id="content">
        <div class="wrap">
            <fieldset>
                <legend>Search Transactions</legend>
                <table>
                    <tr>
                        <td>
                            <label for="TagNo">
                                Tag No :
                            </label>
                        </td>
                        <td>
                            <form:input path="searchTransaction.TagNo" cssClass="textfieldMyStyle" id="TagNo"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="CustomerName">
                                Customer Name :
                            </label>
                        </td>
                        <td>
                            <form:input path="searchTransaction.CustomerName" cssClass="textfieldMyStyle" id="CustomerName"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="startDate">
                                Reported Date (From) :
                            </label>
                        </td>
                        <td>
                            <form:input path="searchTransaction.startDate" cssClass="textfieldMyStyle" id="startDate"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="endDate" style="font-size: .70em;">
                                Reported Date (To) :
                            </label>
                        </td>
                        <td>
                            <form:input path="searchTransaction.endDate" cssClass="textfieldMyStyle" id="endDate"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="SerialNo">
                                Serial No :
                            </label>
                        </td>
                        <td>
                            <form:input path="searchTransaction.SerialNo" cssClass="textfieldMyStyle" id="SerialNo"/>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="makeId">
                                Make :
                            </label>
                        </td>
                        <td>
                            <form:select id="makeId" path="searchTransaction.makeId" tabindex="1"
                                         onchange="changeTheModel();"
                                         cssClass="textfieldMyStyle">
                                <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                <form:options items="${transactionForm.makeVOs}"
                                              itemValue="Id" itemLabel="makeName"/>
                            </form:select>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="modelId">
                                Model Name :
                            </label>
                        </td>
                        <td>
                            <form:select id="modelId" path="searchTransaction.modelId" tabindex="1" cssClass="textfieldMyStyle">
                                <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                <form:options items="${transactionForm.makeAndModelVOs}"
                                              itemValue="modelId" itemLabel="modelName"/>
                            </form:select>
                        </td>
                        <td colspan="2">&nbsp;</td>
                        <td>
                            <label for="Status">
                                Status :
                            </label>
                        </td>
                        <td>
                            <form:select id="Status" path="searchTransaction.Status" cssClass="textfieldMyStyle">
                                <form:option value=""><spring:message code="common.select" text="<-- Select -->"/></form:option>
                                <form:options items="${transactionForm.statusList}" />
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="14">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="12">&nbsp;</td>
                        <td>
                            <label for="includes">
                                <spring:message code="user.includes" text="Includes"/>
                                <form:checkbox path="searchTransaction.includes" cssStyle="vertical-align:middle" id="includes" value="" />
                            </label>
                        </td>
                        <td>
                            <label for="startswith" style="font-size: .70em;">
                                <spring:message code="user.startsWith" text="Starts with"/>
                                <form:checkbox path="searchTransaction.startswith" cssStyle="vertical-align:middle" id="startswith" value="" />
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="12">&nbsp;</td>
                        <td>
                            <input class="btn" value="<spring:message code="poseidon.search" text="Search" />"
                                   type="button" onclick="javascript:search()"/>
                        </td>
                        <td>
                            <input class="btn" value="<spring:message code="poseidon.clear" text="Clear" />"
                                   type="button" onclick="javascript:clearOut()"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <c:if test="${transactionForm.statusMessage!=null}">
                <div  id="effect" class="<c:out value='${transactionForm.statusMessageType}'/>">
                    <c:out value="${transactionForm.statusMessage}"/>
                </div>
            </c:if>
            <fieldset>
                <legend>Transaction Details</legend>
                <table border="2" id="myTable">
                    <thead>
                    <tr>
                        <th><spring:message code="poseidon.id" text="id"/></th>
                        <th>TagNo</th>
                        <th>Customer Name</th>
                        <th>Reported Date</th>
                        <th>Make</th>
                        <th>Model</th>
                        <th>Serial No</th>
                        <th>Status</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach items="${transactionForm.transactionsList}" var="iterationTxn">
                        <tr>
                            <td><input type="checkbox" name="checkField" onclick="javascript:checkCall(this)"
                                       value="<c:out value='${iterationTxn.id}' />"/></td>
                            <td><c:out value="${iterationTxn.tagNo}"/></td>
                            <td><c:out value="${iterationTxn.customerName}"/></td>
                            <td><c:out value="${iterationTxn.dateReported}"/></td>
                            <td><c:out value="${iterationTxn.makeName}"/></td>
                            <td><c:out value="${iterationTxn.modelName}"/></td>
                            <td><c:out value="${iterationTxn.serialNo}"/></td>
                            <td><c:out value="${iterationTxn.status}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <table>
                    <tr>
                        <td>
                            <br/>
                            <br/>
                            <input class="btn" value="Add New Transaction" type="button" onclick="javascript:addNew()"/>
                            <input class="btn" value="Edit Transaction" type="button" onclick="javascript:editMe()"/>
                            <input class="btn" value="Delete Transaction" type="button" onclick="javascript:deleteTxn()"/>
                            <input class="btn" value="Invoice Transaction" type="button" onclick="javascript:invoiceTxn()"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>
    </div>

</form:form>
</body>
</html>
