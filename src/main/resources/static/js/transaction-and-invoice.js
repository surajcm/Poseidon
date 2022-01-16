function addSmartInvoiceOnTransaction() {
    let rowCheck = validateEditModalSelection('invoiceModalBody');
    if (rowCheck) {
        addSmartInvoice(true);
    }
}

function addSmartInvoiceOnInvoicePage() {
    addSmartInvoice(false);
}

function addSmartInvoice(fromInvoice) {
    let saveModal = document.getElementById("saveModal");
    saveModal.style.display = "block";
    let detail = document.getElementById("invoiceModalBody");
    detail.innerHTML = "";
    detail.appendChild(invoiceOnModal(fromInvoice));
    let txtQuantity = document.getElementById('addQuantity');
    txtQuantity.onkeyup = function () {
        multiplyFromQty()
    };
    let txtRate = document.getElementById('addRate');
    txtRate.onkeyup = function () {
        multiplyFromRate()
    };
}


function invoiceOnModal(fromInvoice) {
    let formValidInvoice = document.createElement("form");
    formValidInvoice.setAttribute("class", "row g-3 needs-validation");
    formValidInvoice.novalidate = true;

    let divTag = document.createElement("div");
    divTag.setAttribute("class", "col-md-6");
    divTag.appendChild(tagNumbers(fromInvoice));

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

function tagNumbers(fromInvoice) {
    let invoiceElem;
    if (fromInvoice) {
        const id = document.getElementById("id").value;
        invoiceElem = aTextBox("addTagNumber", "TagNumber", true);
        invoiceElem.disabled = true;
        getInvoiceIdAndDescription(id);
    } else {
        invoiceElem = document.createElement("select");
        invoiceElem.setAttribute("class", "form-select");
        invoiceElem.setAttribute("id", "addTagNumber");
        ajaxAllTagNumbers();
    }
    return invoiceElem;
}

function getInvoiceIdAndDescription(id) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', "/invoice/addInvoice" + "?id=" + id, true);
    const token = document.querySelector("meta[name='_csrf']").content;
    const header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                console.log(xhr.responseText);
                setTagNumberAndDescription(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
        }
    };
    xhr.send();
}

function setTagNumberAndDescription(textReturned) {
    const element = JSON.parse(textReturned);
    let addTagNumber = document.getElementById("addTagNumber");
    let addDescription = document.getElementById("addDescription");
    addTagNumber.value = element.tagNo;
    addDescription.value = element.description;
}

function ajaxAllTagNumbers() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', "/invoice/tagNumbers", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
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
        console.log('amount = ' + amount)
    }
}

function multiplyFromQty() {
    console.log('inside multiplyFromQty')
    let quantity = document.getElementById('addQuantity').value;
    let rate = document.getElementById('addRate').value;
    if (quantity.length > 0 && rate > 0) {
        let amount = quantity * rate;
        document.getElementById('addAmount').value = amount;
        console.log('amount = ' + amount)
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

function addValidation(addTagNumber, addDescription, addQuantity, addRate) {
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
        addValidation(addTagNumber, addDescription, addQuantity, addRate);
    }

    if (allFieldsAreValid) {
        console.log('all fields are valid');
        callAjaxForAddingInvoice(addTagNumber, addDescription, addQuantity, addRate, addAmount);
    }
}


function showStatus(status) {
    let detail = document.getElementById("invoiceModalBody");
    detail.innerHTML = "";
    let saveModal = document.getElementById("saveModal");
    saveModal.style.display = "none";
    detail.appendChild(statusMessage(status, 'INVOICE'));
}

