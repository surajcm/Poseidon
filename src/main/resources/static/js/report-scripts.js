function fetchCallReport() {
    document.getElementById('exportTo').value = document.getElementById('callExportValue').options[document.getElementById('callExportValue').selectedIndex].text;
    document.getElementById('tagNo').value = document.getElementById('callTagNo').value;
    document.forms[0].target = 'reportContent';
    document.forms[0].action = 'getCallReport.htm';
    document.forms[0].submit();
    document.forms[0].target = '';
}

function fetchMakeReport() {
    document.getElementById('exportTo').value = document.getElementById('makeExportValue').options[document.getElementById('makeExportValue').selectedIndex].text;
    document.forms[0].target = 'reportContent';
    document.forms[0].action = 'getMakeDetailsReport.htm';
    document.forms[0].submit();
    document.forms[0].target = '';
}

function fetchModelListReport() {
    document.getElementById('exportTo').value = document.getElementById('makeExportValue').options[document.getElementById('makeExportValue').selectedIndex].text;
    document.forms[0].target = 'reportContent';
    document.forms[0].action = 'getModelListReport.htm';
    document.forms[0].submit();
    document.forms[0].target = '';
}

function fetchTransactionsListReport() {
    document.getElementById('exportTo').value = document.getElementById('txnExportValue').options[document.getElementById('txnExportValue').selectedIndex].text;
    document.forms[0].target = 'reportContent';
    document.forms[0].action = 'getTransactionsListReport.htm';
    document.forms[0].submit();
    document.forms[0].target = '';
}


function fetchInvoiceReport() {
    document.getElementById('exportTo').value = document.getElementById('invoiceExportValue').options[document.getElementById('invoiceExportValue').selectedIndex].text;
    document.getElementById('tagNo').value = document.getElementById('invoiceTagNo').value;
    document.forms[0].target = 'reportContent';
    document.forms[0].action = 'getInvoiceReport.htm';
    document.forms[0].submit();
    document.forms[0].target = '';
}
function changeTheTxnModel(){
    var selectMakeId = document.searchTransaction.makeId.value;
    var url = "${contextPath}/txs/UpdateModelAjax.htm";
    url = url + "?selectMakeId=" + selectMakeId;
    bustcacheparameter = (url.indexOf("?") != -1) ? "&" + new Date().getTime() : "?" + new Date().getTime();
    createAjaxRequest();
    if (req) {
        req.onreadystatechange = stateChangeOnTxn;
        req.open("POST", url + bustcacheparameter, true);
        req.send(url + bustcacheparameter);
    }
}

function changeTheModel(){
    var selectMakeId = document.getElementById('makeId').value;
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
            document.forms[0].modelId.options.length = resultIds.length - 1;
            document.forms[0].modelId.options[0] = new Option("<-- Select -->", "");
            for (var i = 1; i <= (resultIds.length); i++) {
                document.forms[0].modelId.options[i] = new Option(resultNames[i - 1], resultIds[i - 1]);
            }
        } else {
            document.forms[0].modelId.options.length = 0;
            document.forms[0].modelId.options[0] = new Option("<-- Select -->", "");
        }
    }
}

function stateChangeOnTxn() {
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
            document.searchTransaction.modelId.options.length = resultIds.length - 1;
            document.searchTransaction.modelId.options[0] = new Option("<-- Select -->", "");
            for (var i = 1; i <= (resultIds.length); i++) {
                document.searchTransaction.modelId.options[i] = new Option(resultNames[i - 1], resultIds[i - 1]);
            }
        } else {
            document.searchTransaction.modelId.options.length = 0;
            document.searchTransaction.modelId.options[0] = new Option("<-- Select -->", "");
        }
    }
}
function selectMenu(){
    document.getElementById('report').text = "Report <span class='sr-only'>Report</span>";
}