

function selectMenu() {
    document.getElementById('report').text = "Report <span class='sr-only'>Report</span>";
}

function fetchCallReport() {
    let valid = false;
    const callExportValue = document.getElementById('callExportValue');
    document.getElementById('exportTo').value = callExportValue.options[callExportValue.selectedIndex].text;
    const callTag = document.getElementById('callTagNo');
    document.getElementById('tagNo').value = callTag.value;
    if (callTag.value.length === 0) {
        callTag.setAttribute("class", "form-control is-invalid");
    } else {
        callTag.setAttribute("class", "form-control was-validated");
        valid = true;
    }
    if (valid) {
        document.forms[0].target = 'reportContent';
        document.forms[0].action = 'getCallReport.htm';
        document.forms[0].submit();
        document.forms[0].target = '';
    }
}

function fetchMakeReport() {
    const callExportValue = document.getElementById('makeExportValue');
    document.getElementById('exportTo').value = callExportValue.options[callExportValue.selectedIndex].text;
    document.forms[0].target = 'reportContent';
    document.forms[0].action = 'getMakeDetailsReport.htm';
    document.forms[0].submit();
    document.forms[0].target = '';
}

function fetchModelListReport() {
    let valid = false;
    const callExportValue = document.getElementById('makeExportValue');
    const modelReportMakeName = document.getElementById('modelReportMakeName');
    if (modelReportMakeName.value === "0") {
        modelReportMakeName.setAttribute("class", "form-select is-invalid");
        console.log("Empty modelReportMakeName");
    } else {
        modelReportMakeName.setAttribute("class", "form-select was-validated");
        valid = true;
    }
    if (valid) {
        document.getElementById('exportTo').value = callExportValue.options[callExportValue.selectedIndex].text;
        document.forms[0].target = 'reportContent';
        document.forms[0].action = 'getModelListReport.htm';
        document.forms[0].submit();
        document.forms[0].target = '';
    }
}

function fetchTransactionsListReport() {
    const callExportValue = document.getElementById('txnExportValue');
    document.getElementById('exportTo').value = callExportValue.options[callExportValue.selectedIndex].text;
    document.forms[0].target = 'reportContent';
    document.forms[0].action = 'getTransactionsListReport.htm';
    document.forms[0].submit();
    document.forms[0].target = '';
}


function fetchInvoiceReport() {
    let valid = false;
    const callExportValue = document.getElementById('invoiceExportValue');
    document.getElementById('exportTo').value = callExportValue.options[callExportValue.selectedIndex].text;
    const invoiceTagNo = document.getElementById('invoiceTagNo');
    document.getElementById('tagNo').value = invoiceTagNo.value;
    if (invoiceTagNo.value.length === 0) {
        invoiceTagNo.setAttribute("class", "form-control is-invalid");
        console.log("Empty invoiceTagNo");
    } else {
        invoiceTagNo.setAttribute("class", "form-control was-validated");
        valid = true;
    }
    if (valid) {
        document.forms[0].target = 'reportContent';
        document.forms[0].action = 'getInvoiceReport.htm';
        document.forms[0].submit();
        document.forms[0].target = '';
    }
}

function changeTheTxnModel() {
    const selectMakeId = document.searchTransaction.makeId.value;
    let url = "${contextPath}/txs/UpdateModelAjax.htm";
    url = url + "?selectMakeId=" + selectMakeId;
    bustcacheparameter = (url.indexOf("?") != -1) ? "&" + new Date().getTime() : "?" + new Date().getTime();
    createAjaxRequest();
    if (req) {
        req.onreadystatechange = stateChangeOnTxn;
        req.open("POST", url + bustcacheparameter, true);
        req.send(url + bustcacheparameter);
    }
}

function changeTheModel() {
    const selectMakeId = document.getElementById('txnReportMakeId').value;
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
        let textReturned = req.responseText;
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
            document.forms[0].modelId.options.length = resultIds.length - 1;
            document.forms[0].modelId.options[0] = new Option("<-- Select -->", "");
            for (let i = 1; i <= (resultIds.length); i++) {
                document.forms[0].modelId.options[i] = new Option(resultNames[i - 1], resultIds[i - 1]);
            }
        } else {
            document.forms[0].modelId.options.length = 0;
            document.forms[0].modelId.options[0] = new Option("<-- Select -->", "");
        }
    }
}

function stateChangeOnTxn() {
    if (req.readyState === 4 &&
        (req.status === 200 || window.location.href.indexOf("http") === -1)) {
        let textReturned = req.responseText;
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
            document.searchTransaction.modelId.options.length = resultIds.length - 1;
            document.searchTransaction.modelId.options[0] = new Option("<-- Select -->", "");
            for (let i = 1; i <= (resultIds.length); i++) {
                document.searchTransaction.modelId.options[i] = new Option(resultNames[i - 1], resultIds[i - 1]);
            }
        } else {
            document.searchTransaction.modelId.options.length = 0;
            document.searchTransaction.modelId.options[0] = new Option("<-- Select -->", "");
        }
    }
}
