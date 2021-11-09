
function addSmartInvoiceOnTransaction() {
    let rowCheck = validateSelection();
    if (rowCheck) {
        addSmartInvoice();
    }
}

function addSmartInvoice() {
    let saveModal = document.getElementById("saveModal");
    saveModal.style.display = "block";
    let detail = document.getElementById("invoiceModalBody");
    detail.innerHTML = "";
    detail.appendChild(invoiceOnModal());
    let txtQuantity = document.getElementById('addQuantity');
    txtQuantity.onkeyup = function() {multiplyFromQty()};
    let txtRate = document.getElementById('addRate');
    txtRate.onkeyup = function() {multiplyFromRate()};
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

function multiplyFromRate() {
    console.log('inside multiplyFromRate')
    let quantity = document.getElementById('addQuantity').value;
    let rate = document.getElementById('addRate').value;
    if (quantity.length > 0) {
        let amount = quantity * rate;
        document.getElementById('addAmount').value = amount;
        console.log('amount = '+amount)
    }
}

function multiplyFromQty() {
    console.log('inside multiplyFromQty')
    let quantity = document.getElementById('addQuantity').value;
    let rate = document.getElementById('addRate').value;
    if (quantity.length > 0 && rate > 0) {
        let amount = quantity * rate;
        document.getElementById('addAmount').value = amount;
        console.log('amount = '+amount)
    }
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

function showStatus(status) {
    let detail = document.getElementById("invoiceModalBody");
    detail.innerHTML = "";
    let saveModal = document.getElementById("saveModal");
    saveModal.style.display = "none";
    detail.appendChild(statusAsDiv(status));
}

