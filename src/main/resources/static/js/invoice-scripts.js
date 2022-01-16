function hideAlerts() {
    document.getElementById('inventory').text = "Invoice <span class='sr-only'>Invoice</span>";
}

function search() {
    document.forms[0].action = "SearchInvoice";
    document.forms[0].submit();
}

function clearOut() {
    document.getElementById('invoiceId').value = "";
    document.getElementById('description').value = "";
    document.getElementById('serialNo').value = "";
    document.getElementById('tagNo').value = "";
    document.getElementById('amount').value = "";
    document.getElementById('greater').checked = false;
    document.getElementById('greater').checked = false;
    document.getElementById('lesser').checked = false;
    document.getElementById('startsWith').checked = false;
}

// delete
function deleteInvoice() {
    let rowCheck = validateSelection();
    if (rowCheck) {
        deleteRow();
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
        } else {
            for (let i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    userRow = document.getElementById("myTable").rows[i + 1];
                }
            }
        }
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        if (document.getElementById('amount').value.length === 0) {
            document.getElementById('amount').value = "0.0";
        }
        document.forms[0].action = "DeleteInvoice";
        document.forms[0].submit();
    }

}

function rewriteTable(textReturned) {
    document.getElementById('myTable').innerHTML = "";
    let myTable = document.getElementById("myTable");
    myTable.setAttribute("class", "table table-bordered table-striped table-hover caption-top");
    myTable.appendChild(setCaption("Invoice Details"));
    const thead = tableHead();
    myTable.appendChild(thead);
    myTable.appendChild(tableBodyCreation(textReturned));
}


function tableHeaderRow() {
    let tr1 = document.createElement("tr");
    const th1 = tableHeader("");
    tr1.appendChild(th1);
    tr1.appendChild(tableHeader("Invoice Id"));
    tr1.appendChild(tableHeader("Customer Name"));
    tr1.appendChild(tableHeader("Tag No"));
    tr1.appendChild(tableHeader("Item Description"));
    tr1.appendChild(tableHeader("Serial No"));
    tr1.appendChild(tableHeader("Total Amount"));
    return tr1;
}

function singleRowInTheTable(singleInvoice) {
    let trx = document.createElement("tr");
    trx.appendChild(sideHeader(singleInvoice.id));
    trx.appendChild(tdElement(singleInvoice.id));
    trx.appendChild(tdElement(singleInvoice.customerName));
    trx.appendChild(tdElement(singleInvoice.tagNo));
    trx.appendChild(tdElement(singleInvoice.description));
    trx.appendChild(tdElement(singleInvoice.serialNo));
    trx.appendChild(tdElement(singleInvoice.amount));
    return trx;
}

function editSmartInvoice() {
    let rowCheck = validateEditModalSelection('invoiceEditModalBody');
    if (rowCheck) {
        editInvoiceModal();
        setIdForChange();
        getInvoiceForEdit();
    }
}

function editInvoiceModal() {
    let updateModal = document.getElementById("updateModal");
    updateModal.style.display = "block";
    let detail = document.getElementById("invoiceEditModalBody");
    detail.innerHTML = "";
    detail.appendChild(invoiceOnModal());
    let txtQuantity = document.getElementById('addQuantity');
    txtQuantity.onkeyup = function () {
        multiplyFromQty()
    };
    let txtRate = document.getElementById('addRate');
    txtRate.onkeyup = function () {
        multiplyFromRate()
    };
}

function getInvoiceForEdit() {
    let id = document.getElementById("id").value;
    let xhr = new XMLHttpRequest();
    xhr.open('GET', "/invoice/getForEdit" + "?id=" + id, true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                console.log(xhr.responseText);
                populateDataForEdit(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
            showEditError();
        }
    };
    xhr.send();
}

function showEditError() {
    let detail = document.getElementById("invoiceEditModalBody");
    detail.innerHTML = "";
    let updateModal = document.getElementById("updateModal");
    updateModal.style.display = "none";
    let divStatus = document.createElement("div");
    divStatus.setAttribute("class", "pop-status");
    let imgSuccess = document.createElement("img");

    divStatus.appendChild(imgSuccess);
    let statusMessage = document.createElement("h3");
    imgSuccess.setAttribute("src", "/img/cross.svg");
    statusMessage.innerHTML = "Failed to populate data !!";
    divStatus.appendChild(statusMessage);
    detail.appendChild(divStatus);
}

function populateDataForEdit(textReturned) {
    let invoice = JSON.parse(textReturned);
    let key = invoice.tagNo;
    let tagDropDown = document.getElementById("addTagNumber");
    //console.log('key is'+key);
    for (let option of tagDropDown.options) {
        //console.log('option.value'+option.value);
        if (option.value === key) {
            option.selected = true;
            console.log("option set to " + option.value);
        }
    }
    document.getElementById("addDescription").value = invoice.description;
    document.getElementById("addQuantity").value = invoice.quantity;
    document.getElementById("addRate").value = invoice.rate;
    document.getElementById("addAmount").value = invoice.amount;
}

function updateFromModal() {
    let addTagNumber = document.getElementById("addTagNumber").value;
    let addDescription = document.getElementById("addDescription").value;
    let addQuantity = document.getElementById("addQuantity").value;
    let addRate = document.getElementById("addRate").value;
    let addAmount = document.getElementById("addAmount").value;
    let forms = document.getElementsByClassName('needs-validation');

    let allFieldsAreValid = true;

    if (forms[0].checkValidity() === false) {
        allFieldsAreValid = false;
        if (addTagNumber.length === 0) {
            document.getElementById("addTagNumber").setAttribute("class", "form-control is-invalid");
        } else {
            document.getElementById("addTagNumber").setAttribute("class", "form-control was-validated");
        }
        if (addDescription.length === 0) {
            document.getElementById("addDescription").setAttribute("class", "form-control is-invalid");
        } else {
            document.getElementById("addDescription").setAttribute("class", "form-control was-validated");
        }
        if (addQuantity.length === 0) {
            document.getElementById("addQuantity").setAttribute("class", "form-control is-invalid");
        } else {
            document.getElementById("addQuantity").setAttribute("class", "form-control was-validated");
        }
        if (addRate.length === 0) {
            document.getElementById("addRate").setAttribute("class", "form-control is-invalid");
        } else {
            document.getElementById("addRate").setAttribute("class", "form-control was-validated");
        }
    }

    if (allFieldsAreValid) {
        console.log('all fields are valid');
        callAjaxUpdate(addTagNumber, addDescription, addQuantity, addRate, addAmount);
    }
}

function callAjaxUpdate(addTagNumber, addDescription, addQuantity, addRate, addAmount) {
    let id = document.getElementById("id").value;
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', "/invoice/updateInvoice", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                console.log(xhr.responseText);
                rewriteTable(xhr.responseText);
                showUpdateStatus(true);
            } else {
                showUpdateStatus(false);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
            showUpdateStatus(false);
        }
    };
    xhr.send("id=" + id +
        "&addTagNumber=" + addTagNumber +
        "&addDescription=" + addDescription +
        "&addQuantity=" + addQuantity +
        "&addRate=" + addRate +
        "&addAmount=" + addAmount);
}

function showUpdateStatus(status) {
    let detail = document.getElementById("invoiceEditModalBody");
    detail.innerHTML = "";
    let updateModal = document.getElementById("updateModal");
    updateModal.style.display = "none";
    detail.appendChild(statusMessage(status, 'INVOICE'));
}


function callAjaxForAddingInvoice(addTagNumber, addDescription, addQuantity, addRate, addAmount) {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "/invoice/saveInvoice", true);
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