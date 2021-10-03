function hideAlerts() {
    document.getElementById('transaction').text = "Transactions <span class='sr-only'>Transactions</span>";
}

function addNew() {
    document.forms[0].action = "AddTxn.htm";
    document.forms[0].submit();
}

//preventing multiple checks
function checkCall(e) {
    let min = e.value;
    let checks = document.getElementsByName('checkField');
    for (let i = 0; i < checks.length; i++) {
        if (checks[i].value !== min) {
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

function changeTheModel() {
    const selectMakeId = document.getElementById("makeId").value;
    console.log(selectMakeId);
    const xhr = new XMLHttpRequest();
    xhr.open('POST', "/txs/UpdateModelAjax.htm" + "?selectMakeId=" + selectMakeId, true);
    const token = document.querySelector("meta[name='_csrf']").content;
    const header = document.querySelector("meta[name='_csrf_header']").content;
    xhr.setRequestHeader(header, token);
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
    let check = 'false';
    let count = 0;
    // get all check boxes
    const checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            editRow();
        } else {
            for (let i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    check = 'true';
                    count = count + 1;
                }
            }
            //check for validity
            if (check === 'true') {
                if (count === 1) {
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
    let userRow;
    const checks = document.getElementsByName('checkField');
    if (checks.checked) {
        userRow = document.getElementById("myTable").rows[0];
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        document.forms[0].action = "EditTxn.htm";
        document.forms[0].submit();
    } else {
        for (let i = 0; i < checks.length; i++) {
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
    let check = 'false';
    let count = 0;
    // get all check boxes
    const checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            deleteRow();
        } else {
            for (let i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    check = 'true';
                    count = count + 1;
                }
            }
            //check for validity
            if (check === 'true') {
                if (count === 1) {
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
    const answer = confirm(" Are you sure you wanted to delete the Txn ");
    if (answer) {
        //if yes then delete
        let userRow;
        const checks = document.getElementsByName('checkField');
        if (checks.checked) {
            userRow = document.getElementById("myTable").rows[0];
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            document.forms[0].action = "DeleteTxn.htm";
            document.forms[0].submit();
        } else {
            for (let i = 0; i < checks.length; i++) {
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
    let check = 'false';
    let count = 0;
    // get all check boxes
    let checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            invoiceRow();
        } else {
            for (let i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    check = 'true';
                    count = count + 1;
                }
            }
            //check for validity
            if (check === 'true') {
                if (count === 1) {
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
    let userRow;
    let checks = document.getElementsByName('checkField');
    if (checks.checked) {
        userRow = document.getElementById("myTable").rows[0];
    } else {
        for (let i = 0; i < checks.length; i++) {
            if (checks[i].checked) {
                userRow = document.getElementById("myTable").rows[i + 1];
            }
        }
    }
    document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
    document.forms[0].action = "/invoice/InvoiceTxn.htm";
    document.forms[0].submit();
}


