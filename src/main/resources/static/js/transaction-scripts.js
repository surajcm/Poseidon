function hideAlerts() {
    document.getElementById('transaction').text = "Transactions <span class='sr-only'>Transactions</span>";
}

function addNew() {
    document.forms[0].action = "addTxn";
    document.forms[0].submit();
}

function search() {
    document.forms[0].action = "searchTxn";
    document.forms[0].submit();
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

function changeTheModel() {
    const selectMakeId = document.getElementById("makeId").value;
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
        document.forms[0].modelId.options.length = mmList - 1;
        document.forms[0].modelId.options[0] = new Option("<-- Select -->", "");
        for (let i = 0; i < mmList.length; i++) {
            console.log("i is " + i);
            const singleMM = mmList[i];
            console.log("singleMM is " + singleMM.modelName);
            document.forms[0].modelId.options[i + 1] = new Option(singleMM.modelName, singleMM.id);
        }
    } else {
        document.forms[0].modelId.options.length = 0;
        document.forms[0].modelId.options[0] = new Option("<-- Select -->", "");
    }
}

//validation before edit
function editMe() {
    let rowCheck = validateSelection();
    if (rowCheck) {
        editRow();
    }
}

//real edit
function editRow() {
    let userRow;
    const checks = document.getElementsByName('checkField');
    if (checks.checked) {
        userRow = document.getElementById("myTable").rows[0];
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        document.forms[0].action = "editTxn";
        document.forms[0].submit();
    } else {
        for (let i = 0; i < checks.length; i++) {
            if (checks[i].checked) {
                userRow = document.getElementById("myTable").rows[i + 1];
            }
        }
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        document.forms[0].action = "editTxn";
        document.forms[0].submit();
    }
}

function deleteTxn() {
    let rowCheck = validateSelection();
    if (rowCheck) {
        deleteRow();
    }
}

function deleteRow() {
    const answer = confirm(" Are you sure you wanted to delete the Txn ");
    if (answer) {
        //if yes then delete
        let userRow;
        const checks = document.getElementsByName('checkField');
        if (checks.checked) {
            userRow = document.getElementById("myTable").rows[0];
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            document.forms[0].action = "deleteTxn";
            document.forms[0].submit();
        } else {
            for (let i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    userRow = document.getElementById("myTable").rows[i + 1];
                }
            }
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            document.forms[0].action = "deleteTxn";
            document.forms[0].submit();
        }
    }
}

function callAjaxForAddingInvoice(addTagNumber, addDescription, addQuantity, addRate, addAmount) {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "/invoice/saveInvoiceForTxn", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                console.log(xhr.responseText);
                rewriteTable(xhr.responseText);
                showStatus(true);
            } else {
                showStatus(false);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
            showStatus(false);
        }
    };
    xhr.send("addTagNumber=" + addTagNumber +
        "&addDescription=" + addDescription +
        "&addQuantity=" + addQuantity +
        "&addRate=" + addRate +
        "&addAmount=" + addAmount);
}

function rewriteTable(textReturned) {
    document.getElementById('myTable').innerHTML = "";
    let myTable = document.getElementById("myTable");
    myTable.setAttribute("class", "table table-bordered table-striped table-hover caption-top");
    myTable.appendChild(setCaption("Transaction List"));
    const thead = tableHead();
    myTable.appendChild(thead);
    myTable.appendChild(tableBodyCreation(textReturned));
}

function tableHeaderRow() {
    let tr1 = document.createElement("tr");
    const th1 = tableHeader("id");
    tr1.appendChild(th1);
    tr1.appendChild(tableHeader("Tag No"));
    tr1.appendChild(tableHeader("Customer Name"));
    tr1.appendChild(tableHeader("Reported Date"));
    tr1.appendChild(tableHeader("Make"));
    tr1.appendChild(tableHeader("Model"));
    tr1.appendChild(tableHeader("Serial No"));
    tr1.appendChild(tableHeader("Status"));
    return tr1;
}

function singleRowInTheTable(singleTransaction) {
    let trx = document.createElement("tr");
    trx.appendChild(sideHeader(singleTransaction.id));
    trx.appendChild(tdElement(singleTransaction.tagNo));
    trx.appendChild(tdElement(singleTransaction.customerName));
    trx.appendChild(tdElement(singleTransaction.dateReported));
    trx.appendChild(tdElement(singleTransaction.makeName));
    trx.appendChild(tdElement(singleTransaction.modelName));
    trx.appendChild(tdElement(singleTransaction.serialNo));
    trx.appendChild(tdElement(singleTransaction.status));
    return trx;
}