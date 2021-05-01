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
        document.forms[0].action = "/customer/editCustomer.htm"+
                "?customerId=" +document.getElementById("customerId").value;
        document.forms[0].submit();
    }else{
        alert("Unable to get the customer Details !!!");
    }
}

function changeTheModel() {
    var selectMakeId = document.transactionForm.makeId.value;
    var url = "/txs/UpdateModelAjax.htm";
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