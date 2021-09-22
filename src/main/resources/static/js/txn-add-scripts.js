function save() {
    if (document.getElementById('dateReported').value.length === 0) {
        alert("Please enter a valid Transaction Date");
    } else if (document.getElementById('productCategory').value.length === 0) {
        alert("Please enter a valid Product Category");
    } else if (document.getElementById('serialNo').value.length === 0) {
        alert("Please enter a valid Serial No");
    } else if ((document.getElementById('customerId').value.length === 0)
        && (document.getElementById('customerName').value.length === 0
            || document.getElementById('mobile').value.length === 0)) {
        alert("Please enter a valid Customer Details");
    } else if (document.getElementById('makeId').value.length === 0) {
        alert("Please enter a valid Make detail");
    } else if (document.getElementById('modelId').value.length === 0) {
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

var req;

function changeTheModel() {
    const selectMakeId = document.transactionForm.makeId.value;
    let url = "${contextPath}/txs/UpdateModelAjax.htm";
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
    if (req.readyState === 4 &&
        (req.status === 200 || window.location.href.indexOf("http") === -1)) {
        textReturned = req.responseText;
        if (textReturned !== "") {
            const fullContent = textReturned.split("#start#");
            const resultIds = [];
            const resultNames = [];
            let k = 0;

            for (let j = 0; j < fullContent.length; j++) {
                if (fullContent[j].length > 0) {
                    resultIds[k] = fullContent[j].split("#id#")[1];
                    const testing = fullContent[j].split("#id#")[2];
                    resultNames[k] = testing.split("#modelName#")[1];
                    k++;
                }
            }
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