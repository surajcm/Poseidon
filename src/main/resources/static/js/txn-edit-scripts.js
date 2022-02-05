function update() {
    if (document.getElementById('productCategory').value.length === 0) {
        alert("Please enter a valid Product Category");
    } else if (document.getElementById('serialNo').value.length === 0) {
        alert("Please enter a valid Serial No");
    } else if (document.getElementById('customerId').value.length === 0) {
        if (document.getElementById('customerName').value.length === 0
            || document.getElementById('mobile').value.length === 0) {
            alert("Please enter a valid Customer Details");
        }
    } else if (document.getElementById('makeId').value.length === 0) {
        alert("Please enter a valid Make detail");
    } else if (document.getElementById('modelId').value.length === 0) {
        alert("Please enter a valid Model detail");
    } else {
        let model = document.getElementById('modelId');
        console.log("model id is : "+ model.value);
        document.forms[0].action = "updateTxn";
        document.forms[0].submit();
    }
}

function cancel() {
    document.forms[0].action = "listTransactions";
    document.forms[0].submit();
}

function changeTheModel() {
    const selectMakeId = document.getElementById('makeId').value;
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "/txs/updateModel", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                rebuildDropDown(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
        }
    };
    xhr.send("selectMakeId=" + selectMakeId + "&${_csrf.parameterName}=${_csrf.token}");
}

function rebuildDropDown(textReturned) {
    const makeModelList = JSON.parse(textReturned);
    let model = document.getElementById('modelId');
    model.options.length = makeModelList.length;
    for (let i = 0; i < (makeModelList.length); i++) {
        const singleModel = makeModelList[i];
        model.options[i] = new Option(singleModel.modelName, singleModel.id);
    }
    document.getElementById('modelId').value = model.options[0].value;
}

function editCurrentCustomer() {
    const customerId = document.getElementById('customerId').value;
    console.log('In editCurrentCustomer, '+ customerId);
    //getFromId
    let detail = document.getElementById("editCustomerBody");
    detail.innerHTML = "";
    detail.appendChild(customerOnModal());
    getCustomerForEditAgain(customerId);
}

function customerOnModal() {
    let formValidCustomer = document.createElement("form");
    formValidCustomer.setAttribute("class", "row g-3 needs-validation");
    formValidCustomer.novalidate = true;

    let divName = document.createElement("div");
    divName.setAttribute("class", "col-md-6");
    let txtCustomerName = aTextBox("modalCustomerName", "Customer Name", true);
    divName.appendChild(txtCustomerName);

    let tt1 = document.createElement("div");
    tt1.setAttribute("class", "invalid-tooltip");
    tt1.innerHTML = "Please provide a valid Name";
    divName.appendChild(tt1);

    let divAddress = document.createElement("div");
    divAddress.setAttribute("class", "col-md-6");
    let txtAddress = aTextArea("modalAddress", "Address", true);
    divAddress.appendChild(txtAddress);

    let tt2 = document.createElement("div");
    tt2.setAttribute("class", "invalid-tooltip");
    tt2.innerHTML = "Please provide a valid Address";
    divAddress.appendChild(tt2);

    let divPhone = document.createElement("div");
    divPhone.setAttribute("class", "col-md-4");
    let txtPhone = aTextBox("modalPhone", "Phone", false);
    divPhone.appendChild(txtPhone);

    let divMobile = document.createElement("div");
    divMobile.setAttribute("class", "col-md-4");
    let txtMobile = aTextBox("modalMobile", "Mobile", true);
    divMobile.appendChild(txtMobile);

    let ttM = document.createElement("div");
    ttM.setAttribute("class", "invalid-tooltip");
    ttM.innerHTML = "Please provide a valid Mobile number";
    divMobile.appendChild(ttM);

    let divEmail = document.createElement("div");
    divEmail.setAttribute("class", "col-md-4");
    let txtEmail = aTextBox("modalEmail", "Email", false);
    divEmail.appendChild(txtEmail);

    let divContact = document.createElement("div");
    divContact.setAttribute("class", "col-md-4");
    let txtContact = aTextBox("modalContact", "Contact Person", false);
    divContact.appendChild(txtContact);

    let divContactMobile = document.createElement("div");
    divContactMobile.setAttribute("class", "col-md-4");
    let txtContactMobile = aTextBox("modalContactMobile", "Mobile of Contact Person", false);
    divContactMobile.appendChild(txtContactMobile);

    let divNotes = document.createElement("div");
    divNotes.setAttribute("class", "col-md-4");
    let txtNotes = aTextArea("modalNotes", "Notes", false);
    divNotes.appendChild(txtNotes);

    formValidCustomer.appendChild(divName);
    formValidCustomer.appendChild(divAddress);
    formValidCustomer.appendChild(divPhone);
    formValidCustomer.appendChild(divMobile);
    formValidCustomer.appendChild(divEmail);
    formValidCustomer.appendChild(divContact);
    formValidCustomer.appendChild(divContactMobile);
    formValidCustomer.appendChild(divNotes);
    return formValidCustomer;
}

function getCustomerForEditAgain(customerId) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', "/customer/getForEdit" + "?id=" + customerId, true);
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

function populateDataForEdit(textReturned) {
    let customerInfo = JSON.parse(textReturned);
    document.getElementById("modalCustomerName").value = customerInfo.customerName;
    document.getElementById("modalAddress").value = customerInfo.address;
    document.getElementById("modalPhone").value = customerInfo.phoneNo;
    document.getElementById("modalMobile").value = customerInfo.mobile;
    document.getElementById("modalEmail").value = customerInfo.email;
    document.getElementById("modalContact").value = customerInfo.contactPerson;
    document.getElementById("modalContactMobile").value = customerInfo.contactMobile;
    document.getElementById("modalNotes").value = customerInfo.notes;
}

function showEditError() {
    let detail = document.getElementById("editCustomerBody");
    detail.innerHTML = "";
    let updateModal = document.getElementById("updateSmartCustomer");
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

function updateFromModal() {
    let modalCustomerName = document.getElementById("modalCustomerName").value;
    let modalAddress = document.getElementById("modalAddress").value;
    let modalPhone = document.getElementById("modalPhone").value;
    let modalMobile = document.getElementById("modalMobile").value;
    let modalEmail = document.getElementById("modalEmail").value;
    let modalContact = document.getElementById("modalContact").value;
    let modalContactMobile = document.getElementById("modalContactMobile").value;
    let modalNotes = document.getElementById("modalNotes").value;

    let forms = document.getElementsByClassName('needs-validation');
    let allFieldsAreValid = true;

    if (forms[0].checkValidity() === false) {
        allFieldsAreValid = false;
        if (modalCustomerName.length === 0) {
            document.getElementById("modalCustomerName").setAttribute("class", "form-control is-invalid");
        } else {
            document.getElementById("modalCustomerName").setAttribute("class", "form-control was-validated");
        }
        if (modalAddress.length === 0) {
            document.getElementById("modalAddress").setAttribute("class", "form-control is-invalid");
        } else {
            document.getElementById("modalAddress").setAttribute("class", "form-control was-validated");
        }
        if (modalMobile.length === 0) {
            document.getElementById("modalMobile").setAttribute("class", "form-control is-invalid");
        } else {
            document.getElementById("modalMobile").setAttribute("class", "form-control was-validated");
        }
    }
    if (allFieldsAreValid) {
        console.log("all fields are valid");
        updateSmartCustomer(modalCustomerName, modalAddress, modalPhone,
            modalMobile, modalEmail, modalContact, modalContactMobile, modalNotes);
    }
}

function updateSmartCustomer(modalCustomerName, modalAddress, modalPhone,
                             modalMobile, modalEmail, modalContact, modalContactMobile, modalNotes) {
    const customerId = document.getElementById('customerId').value;
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', "/customer/updateCustomer", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                console.log(xhr.responseText);
                updateTransactionWithCustomer(xhr.responseText);
                showUpdateStatus(true);
            } else {
                console.log("Empty response");
                showUpdateStatus(false);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
            showUpdateStatus(false);
        }
    };
    xhr.send("id=" +
        customerId +
        "&modalCustomerName=" + modalCustomerName +
        "&modalAddress=" + modalAddress +
        "&modalPhone=" + modalPhone +
        "&modalMobile=" + modalMobile +
        "&modalEmail=" + modalEmail +
        "&modalContact=" + modalContact +
        "&modalContactMobile=" + modalContactMobile +
        "&modalNotes=" + modalNotes);
}

function updateTransactionWithCustomer(textReturned) {
    console.log("response received :" + textReturned);
    const customerId = document.getElementById('customerId').value;
    const elementList = JSON.parse(textReturned);
    for (let i = 0; i < elementList.length; i++) {
        const singleElement = elementList[i];
        if (parseInt(customerId) === parseInt(singleElement.customerId)) {
            document.getElementById('customerName').value = singleElement.customerName;
            document.getElementById('email').value = singleElement.email;
        }
    }
}

function changeCustomer() {
    const searchCustomerId = document.getElementById('searchCustomerId').value;
    const searchCustomerName = document.getElementById('searchCustomerName').value;
    const searchMobile = document.getElementById('searchMobile').value;
    console.log("inside searchForCustomer with id "+searchCustomerId +
        " and name "+searchCustomerName +
        " and mobile "+searchMobile);
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "/customer/searchFromTransaction", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                console.log(xhr.responseText);
                rewriteSearchCustomer(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
        }
    };
    xhr.send("searchCustomerId=" + searchCustomerId +
        "&searchCustomerName=" + searchCustomerName +
        "&searchMobile=" + searchMobile +
        "&${_csrf.parameterName}=${_csrf.token}");

}

function rewriteSearchCustomer(textReturned) {
    document.getElementById('detail').innerHTML = "";
    const myTable = document.getElementById("detail");
    myTable.setAttribute("class", "table table-bordered table-striped table-hover caption-top");
    myTable.appendChild(setCaption("Customer Details"));
    const thead = tableHead();
    myTable.appendChild(thead);
    myTable.appendChild(tableBodyCreation(textReturned));
    document.getElementById('searchCustomer').style.display = "none";
    const footer = document.getElementById('edit-footer');
    const pickMe = createChooseButton();
    footer.appendChild(pickMe);
}

function createChooseButton() {
    const button = document.createElement("button");
    button.id = "pickMe";
    button.className = "btn btn-primary";
    button.textContent = "Select";
    button.type = "button";
    button.onclick = function () {
        chooseCustomer()
    };
    return button;
}

function chooseCustomer() {
    const rowCheck = validateCustomerSelection();
    if (rowCheck) {
        let rowId = findSelectedRowId();
        console.log("Customer Id is : "+rowId);
        getSpecificCustomer(rowId);
        document.getElementById('modal-close').click();
    }
}

function findSelectedRowId() {
    let checks = document.getElementsByName('checkField');
    let rowId;
    if (checks.length === 1) {
        rowId = checks[0].value;
    } else {
        for (let i = 0; i < checks.length; i++) {
            if (checks[i].checked) {
                rowId = checks[i].value;
            }
        }
    }
    return rowId;
}

function validateCustomerSelection() {
    let check = 'false';
    const count = getRowCount();
    if (count > 0) {
        check = 'true';
    }
    if (check === 'true') {
        if (count === 1) {
            return true;
        } else {
            alert("Only one row can be selected at a time, please select one row");
            return false;
        }
    } else {
        alert("No rows selected, please select one row");
        return false;
    }
}

function getRowCount() {
    let checks = document.getElementsByName('checkField');
    let count = 0;
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            count = 1;
        } else {
            if (checks.length === 1) {
                count = 1;
            } else {
                for (let i = 0; i < checks.length; i++) {
                    if (checks[i].checked) {
                        count = count + 1;
                    }
                }
            }
        }
    }
    return count;
}

function tableHeaderRow() {
    const tr1 = document.createElement("tr");
    const th1 = tableHeader("#");
    tr1.appendChild(th1);
    tr1.appendChild(tableHeader("id"));
    tr1.appendChild(tableHeader("Name"));
    tr1.appendChild(tableHeader("Address"));
    tr1.appendChild(tableHeader("Mobile"));
    tr1.appendChild(tableHeader("Email"));
    return tr1;
}

function singleRowInTheTable(singleCustomer) {
    const trx = document.createElement("tr");
    trx.appendChild(sideHeader(singleCustomer.customerId));
    trx.appendChild(tdElement(singleCustomer.customerId));
    trx.appendChild(tdElement(singleCustomer.customerName));
    trx.appendChild(tdElement(singleCustomer.address));
    trx.appendChild(tdElement(singleCustomer.mobile));
    trx.appendChild(tdElement(singleCustomer.email));
    return trx;
}


function getSpecificCustomer(customerId) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', "/customer/getForEdit" + "?id=" + customerId, true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                console.log(xhr.responseText);
                putCustomerInEditTxn(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
        }
    };
    xhr.send();
}

function putCustomerInEditTxn(textReturned) {
    let customerInfo = JSON.parse(textReturned);
    document.getElementById("customerId").value = customerInfo.customerId;
    document.getElementById("customerName").value = customerInfo.customerName;
    document.getElementById("email").value = customerInfo.email;
}

function addNewCustomer() {
    let detail = document.getElementById("newCustomerBody");
    detail.innerHTML = "";
    detail.appendChild(customerOnModal());
}

function getCustomerForEdit() {
    let id = document.getElementById("customerVO.customerId").value;
    let xhr = new XMLHttpRequest();
    xhr.open('GET', "/customer/getForEdit" + "?id=" + id, true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    xhr.setRequestHeader(header, token);
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

function callAjaxUpdate(modalCustomerName, modalAddress, modalPhone,
                        modalMobile, modalEmail, modalContact, modalContactMobile, modalNotes) {
    const id = document.getElementById("customerVO.customerId").value;
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', "/customer/updateCustomer", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                console.log(xhr.responseText);
                rewriteTable(xhr.responseText);
                showUpdateStatus(true);
            } else {
                console.log("Empty response");
                showUpdateStatus(false);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
            showUpdateStatus(false);
        }
    };
    xhr.send("id=" +
        id +
        "&modalCustomerName=" + modalCustomerName +
        "&modalAddress=" + modalAddress +
        "&modalPhone=" + modalPhone +
        "&modalMobile=" + modalMobile +
        "&modalEmail=" + modalEmail +
        "&modalContact=" + modalContact +
        "&modalContactMobile=" + modalContactMobile +
        "&modalNotes=" + modalNotes);
}

function rewriteTable(textReturned) {
    const id = document.getElementById("customerVO.customerId").value;
    const elementList = JSON.parse(textReturned);
    for (let i = 0; i < elementList.length; i++) {
        const singleElement = elementList[i];
        if (parseInt(id) === parseInt(singleElement.customerId)) {
            document.getElementById("customerName").value = singleElement.customerName;
            document.getElementById("email").value = singleElement.email;
        }
    }
}

function showUpdateStatus(status) {
    let detail = document.getElementById("editCustomerBody");
    detail.innerHTML = "";
    let updateSmartCustomer = document.getElementById("updateCurrentCustomer");
    updateSmartCustomer.style.display = "none";
    detail.appendChild(statusMessage(status, 'UPDATE'));
}

function saveFromModal() {
    let modalCustomerName = document.getElementById("modalCustomerName");
    let modalAddress = document.getElementById("modalAddress");
    let modalPhone = document.getElementById("modalPhone");
    let modalMobile = document.getElementById("modalMobile");
    let modalEmail = document.getElementById("modalEmail");
    let modalContact = document.getElementById("modalContact");
    let modalContactMobile = document.getElementById("modalContactMobile");
    let modalNotes = document.getElementById("modalNotes");
    let forms = document.getElementsByClassName('needs-validation');
    let allFieldsAreValid = true;

    if (forms[0].checkValidity() === false) {
        let validName = markValidity(modalCustomerName);
        let validAddress = markValidity(modalAddress);
        let validMobile = markValidity(modalMobile);
        allFieldsAreValid = validName && validAddress && validMobile;
    }
    if (allFieldsAreValid) {
        console.log("all fields are valid, going to save");
        saveNewCustomer(modalCustomerName.value,
            modalAddress.value,
            modalPhone.value,
            modalMobile.value,
            modalEmail.value,
            modalContact.value,
            modalContactMobile.value,
            modalNotes.value);
    }
}

function markValidity(element) {
    if (element.value.length === 0) {
        element.setAttribute("class", "form-control is-invalid");
        return false;
    } else {
        element.setAttribute("class", "form-control was-validated");
        return true;
    }
}

function saveNewCustomer(modalCustomerName,
                         modalAddress,
                         modalPhone,
                         modalMobile,
                         modalEmail,
                         modalContact,
                         modalContactMobile,
                         modalNotes) {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "/customer/saveCustomer2", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null && xhr.responseText !== "") {
                //console.log(xhr.responseText);
                insertNewUser(xhr.responseText);
            } else {
                console.log("Empty response");
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
        }
        document.getElementById('new-modal-close').click();
    };
    xhr.send("modalCustomerName=" + modalCustomerName
        + "&modalAddress=" + modalAddress
        + "&modalPhone=" + modalPhone
        + "&modalMobile=" + modalMobile
        + "&modalEmail=" + modalEmail
        + "&modalContact=" + modalContact
        + "&modalContactMobile=" + modalContactMobile
        + "&modalNotes=" + modalNotes
        + "&${_csrf.parameterName}=${_csrf.token}");
}

function insertNewUser(textReturned) {
    const customer = JSON.parse(textReturned);
    document.getElementById("customerId").value = customer.customerId;
    document.getElementById("customerName").value = customer.customerName;
    document.getElementById("email").value = customer.email;
}