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
        document.forms[0].action = 'callReport';
        document.forms[0].submit();
        document.forms[0].target = '';
    }
}

function fetchMakeReport() {
    const callExportValue = document.getElementById('makeExportValue');
    document.getElementById('exportTo').value = callExportValue.options[callExportValue.selectedIndex].text;
    document.forms[0].target = 'reportContent';
    document.forms[0].action = 'makeDetailsReport';
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
        document.forms[0].action = 'modelListReport';
        document.forms[0].submit();
        document.forms[0].target = '';
    }
}

function fetchTransactionsListReport() {
    const txnExportValue1 = document.getElementById('txnExportValue1');
    document.getElementById('exportTo').value = txnExportValue1.options[txnExportValue1.selectedIndex].text;
    document.forms[0].target = 'reportContent';
    document.forms[0].action = 'transactionsListReport';
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
        document.forms[0].action = 'invoiceReport';
        document.forms[0].submit();
        document.forms[0].target = '';
    }
}

function changeTheModel() {
    const selectMakeId = document.getElementById('txnReportMakeId').value;
    console.log(selectMakeId);
    const xhr = new XMLHttpRequest();
    xhr.open('POST', "/txs/updateModel" + "?selectMakeId=" + selectMakeId, true);
    const token = document.querySelector("meta[name='_csrf']").content;
    const header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                stateChange(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
        }
    };
    xhr.send();
}

function stateChange(textReturned) {
    console.log("Received :" + textReturned);
    if (textReturned !== "") {
        const mmList = JSON.parse(textReturned);
        const txnReportModelId = document.getElementById('txnReportModelId');
        txnReportModelId.options.length = mmList - 1;
        txnReportModelId.options[0] = new Option("<-- Select -->", "");
        for (let i = 0; i < mmList.length; i++) {
            console.log("i is " + i);
            const singleMM = mmList[i];
            console.log("singleMM is " + singleMM.modelName);
            txnReportModelId.options[i + 1] = new Option(singleMM.modelName, singleMM.id);
        }
    } else {
        txnReportModelId.options.length = 0;
        txnReportModelId.options[0] = new Option("<-- Select -->", "");
    }
}

function stateChangeOnInvoice(textReturned) {
    console.log("Received :" + textReturned);
    if (textReturned !== "") {
        const mmList = JSON.parse(textReturned);
        const invoiceListModelId = document.getElementById('invoiceListModelId');
        invoiceListModelId.options.length = mmList - 1;
        invoiceListModelId.options[0] = new Option("<-- Select -->", "");
        for (let i = 0; i < mmList.length; i++) {
            console.log("i is " + i);
            const singleMM = mmList[i];
            console.log("singleMM is " + singleMM.modelName);
            invoiceListModelId.options[i + 1] = new Option(singleMM.modelName, singleMM.id);
        }
    } else {
        invoiceListModelId.options.length = 0;
        invoiceListModelId.options[0] = new Option("<-- Select -->", "");
    }
}
