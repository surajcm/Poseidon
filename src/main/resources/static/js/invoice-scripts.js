

function hideAlerts() {
    document.getElementById('inventory').text = "Invoice <span class='sr-only'>Invoice</span>";
}

function addInvoice() {
    document.forms[0].action = "addInvoice.htm";
    document.forms[0].submit();
}

function search() {
    document.forms[0].action = "SearchInvoice.htm";
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

//validation before edit
function validateSelection() {
    let check = 'false';
    let count = 0;
    // get all check boxes
    const checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            return true;
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
                    return true;
                } else {
                    alert("Only one row can be edited at a time, please select one row ");
                    return false;
                }
            } else {
                alert("No rows selected, please select one row ");
                return false;
            }
        }
    }
}

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
    document.forms[0].action = "EditInvoice.htm";
    document.forms[0].submit();
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
        document.forms[0].action = "DeleteInvoice.htm";
        document.forms[0].submit();
    }

}

function addSmartInvoice() {
    let saveModal = document.getElementById("saveModal");
    saveModal.style.display = "block";
    let detail = document.getElementById("invoiceModalBody");
    detail.innerHTML = "";
    detail.appendChild(invoiceOnModal());
}

function invoiceOnModal() {
    let formValidInvoice = document.createElement("form");
    formValidInvoice.setAttribute("class", "row g-3 needs-validation");
    formValidInvoice.novalidate = true;

    let divTag = document.createElement("div");
    divTag.setAttribute("class", "col-md-6");
    divTag.appendChild(tagNumberDropDown());

    let divDescription = document.createElement("div");
    divDescription.setAttribute("class", "col-md-6");
    let txtDescription = aTextArea("addDescription", "Description", true);
    divDescription.appendChild(txtDescription);
    let tt2 = document.createElement("div");
    tt2.setAttribute("class", "invalid-tooltip");
    tt2.innerHTML = "Please provide a valid description.";
    divDescription.appendChild(tt2);

    let divQuantity = document.createElement("div");
    divQuantity.setAttribute("class", "col-md-4");
    let txtQuantity = aTextBox("addQuantity", "Quantity", true);
    divQuantity.appendChild(txtQuantity);
    let tt3 = document.createElement("div");
    tt3.setAttribute("class", "invalid-tooltip");
    tt3.innerHTML = "Please provide a valid Quantity";
    divQuantity.appendChild(tt3);

    let divRate = document.createElement("div");
    divRate.setAttribute("class", "col-md-4");
    let txtRate = aTextBox("addRate", "Rate", true);
    divRate.appendChild(txtRate);
    let tt4 = document.createElement("div");
    tt4.setAttribute("class", "invalid-tooltip");
    tt4.innerHTML = "Please provide a valid Rate";
    divRate.appendChild(tt4);

    let divAmount = document.createElement("div");
    divAmount.setAttribute("class", "col-md-4");
    let txtAmount = aTextBox("addAmount", "Amount", false);
    divAmount.appendChild(txtAmount);

    formValidInvoice.appendChild(divTag);
    formValidInvoice.appendChild(divDescription);
    formValidInvoice.appendChild(divQuantity);
    formValidInvoice.appendChild(divRate);
    formValidInvoice.appendChild(divAmount);
    return formValidInvoice;
}

function tagNumberDropDown() {
    let selectTag = document.createElement("select");
    selectTag.setAttribute("class", "form-select");
    selectTag.setAttribute("id", "addTagNumber");
    ajaxAllTagNumbers();
    return selectTag;
}

function ajaxAllTagNumbers() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', "/invoice/tagNumbers.htm", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                console.log(xhr.responseText);
                setTagNumberAsOption(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
        }
    };
    xhr.send();
}

function setTagNumberAsOption(textReturned) {
    const elementList = JSON.parse(textReturned);
    let addTagNumber = document.getElementById("addTagNumber");
    for (let i = 0; i < elementList.length; i++) {
        let singleOption = document.createElement("option");
        singleOption.text = elementList[i];
        singleOption.value = elementList[i];
        addTagNumber.appendChild(singleOption);
    }
}

function saveFromModal() {
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
        callAjax(addTagNumber, addDescription, addQuantity, addRate, addAmount);
    }
}

function callAjax(addTagNumber, addDescription, addQuantity, addRate, addAmount) {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "/invoice/saveInvoiceAjax.htm", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    xhr.setRequestHeader(header, token);
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
    myTable.appendChild(setCaption("Invoice Details"));
    const thead = tableHead();
    myTable.appendChild(thead);
    myTable.appendChild(tableBodyCreation(textReturned));
}

function showStatus(status) {
    let detail = document.getElementById("invoiceModalBody");
    detail.innerHTML = "";
    let saveModal = document.getElementById("saveModal");
    saveModal.style.display = "none";
    detail.appendChild(statusAsDiv(status));
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
    console.log("on edit screen");
    let rowCheck = validateEditModalSelection();
    if (rowCheck) {
        editInvoiceModal();
        //setIdForChange();
        //getUserForEdit();
    }
}

function validateEditModalSelection() {
    let detail = document.getElementById("invoiceEditModalBody");
    detail.innerHTML = "";
    let check = 'false';
    let count = selectedRowCount();
    if (count > 0) {
        check = 'true';
    }
    if (check === 'true') {
        if (count === 1) {
            return true;
        } else {
            detail.innerHTML = "<p>Only one row can be selected at a time, please select one row</p>";
        }
    } else {
        detail.innerHTML = "<p>No rows selected, please select one row</p>";
    }
}

function editInvoiceModal() {

}