function hideAlerts() {
    document.getElementById('customer').text = "Customer <span class='sr-only'>Customer</span>";
}

function search() {
    if (document.getElementById("customerId").value === ""
        || isNumber(document.getElementById("customerId").value)) {
        document.forms[0].action = "searchCustomer";
        document.forms[0].submit();
    } else {
        alert("Incorrect customerId format found, Please update the field with a numeric value");
    }
}

function clearOut() {
    document.getElementById("customerId").value = "";
    document.getElementById("customerName").value = "";
    document.getElementById("mobile").value = "";
    document.getElementById('includes').checked = false;
    document.getElementById('startsWith').checked = false;
}

function selectedRow() {
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
    return userRow;
}

function deleteCustomer() {
    let rowCheck = validateSelection();
    if (rowCheck) {
        deleteRow();
    }
}

function deleteRow() {
    const answer = confirm(" Are you sure you wanted to delete the user ");
    if (answer) {
        let userRow = selectedRow();
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        document.forms[0].action = "deleteCustomer";
        document.forms[0].submit();
    }
}

function viewCustomer() {
    let rowCheck = validateCustomerSelection();
    console.log("rowCheck returned :"+rowCheck);
    if (rowCheck) {
        viewRow();
    }
}

function validateCustomerSelection() {
    let detail = document.getElementById("detail");
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

function viewRow() {
    let userRow = selectedRow();
    let customerId = userRow.cells[0].childNodes[0].value;
    viewCustomerDetails(customerId);
}


function viewCustomerDetails(customerId) {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', "viewCustomer", true);
    const token = document.querySelector("meta[name='_csrf']").content;
    const header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            console.log('Response is ' + xhr.responseText);
            if (xhr.responseText != null) {
                fillModal(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
        }
    };
    xhr.send("customerId=" + customerId);
}

function fillModal(textReturned) {
    const customer = JSON.parse(textReturned);
    document.getElementById('detail').innerHTML = "";
    let row = document.createElement("div");
    row.setAttribute("class", "row g-3");
    const detail = document.getElementById("detail");
    const divId = document.createElement("div");
    divId.setAttribute("class", "col-md-6");
    divId.appendChild(generateLabel("Customer Id : "));
    divId.appendChild(generateTextAsLabelField(customer.customerId));
    const divCustomer = document.createElement("div");
    divCustomer.setAttribute("class", "col-md-6");
    divCustomer.appendChild(generateLabel("Customer Name : "));
    divCustomer.appendChild(generateTextAsLabelField(customer.customerName));

    const divAddress = document.createElement("div");
    divAddress.setAttribute("class", "col-md-12");
    divAddress.appendChild(generateLabel("Address : "));
    divAddress.appendChild(generateTextAsLabelField(customer.address));

    const divPhone = document.createElement("div");
    divPhone.setAttribute("class", "col-md-4");
    divPhone.appendChild(generateLabel("Phone No : "));
    divPhone.appendChild(generateTextAsLabelField(customer.phoneNo));
    const divMobile = document.createElement("div");
    divMobile.setAttribute("class", "col-md-4");
    divMobile.appendChild(generateLabel("Mobile : "));
    divMobile.appendChild(generateTextAsLabelField(customer.mobile));
    const divEmail = document.createElement("div");
    divEmail.setAttribute("class", "col-md-4");
    divEmail.appendChild(generateLabel("Email : "));
    divEmail.appendChild(generateTextAsLabelField(customer.email));

    const divPerson = document.createElement("div");
    divPerson.setAttribute("class", "col-md-6");
    divPerson.appendChild(generateLabel("Contact Person : "));
    divPerson.appendChild(generateTextAsLabelField(customer.contactPerson));
    const divPMobile = document.createElement("div");
    divPMobile.setAttribute("class", "col-md-6");
    divPMobile.appendChild(generateLabel("Contact Mobile : "));
    divPMobile.appendChild(generateTextAsLabelField(customer.contactMobile));

    const divNotes = document.createElement("div");
    divNotes.setAttribute("class", "col-md-12");
    divNotes.appendChild(generateLabel("Notes : "));
    divNotes.appendChild(generateTextAsLabelField(customer.notes));

    row.appendChild(divId);
    row.appendChild(divCustomer);
    row.appendChild(divAddress);
    row.appendChild(divPhone);
    row.appendChild(divMobile);
    row.appendChild(divEmail);
    row.appendChild(divPerson);
    row.appendChild(divPMobile);
    row.appendChild(divNotes);
    detail.appendChild(row);
}

function addSmartCustomer() {
    let saveCustomer = document.getElementById("saveSmartCustomer");
    saveCustomer.style.display = "block";
    let detail = document.getElementById("newCustomerBody");
    detail.innerHTML = "";
    detail.appendChild(customerOnModal());
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

function editCustomerModal() {
    let footer = document.getElementById("edit-footer");
    footer.style.display = "block";
    let updateModal = document.getElementById("updateSmartCustomer");
    updateModal.style.display = "block";
    let detail = document.getElementById("editCustomerBody");
    detail.innerHTML = "";

    let formValidCustomer = customerOnModal();
    detail.appendChild(formValidCustomer);
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
    xhr.open('POST', "/customer/saveCustomer", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null && xhr.responseText !== "") {
                rewriteTable(xhr.responseText);
                showStatus(true);
            } else {
                console.log("Empty response");
                showStatus(false);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
            showStatus(false);
        }
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

function rewriteTable(textReturned) {
    //console.log("response received :" + textReturned);
    document.getElementById('myTable').innerHTML = "";
    const myTable = document.getElementById("myTable");
    myTable.setAttribute("class", "table table-bordered table-striped table-hover caption-top");
    myTable.appendChild(setCaption("Customer Details"));
    const thead = tableHead();
    myTable.appendChild(thead);
    myTable.appendChild(tableBodyCreation(textReturned));
}

function tableHeaderRow() {
    const tr1 = document.createElement("tr");
    const th1 = tableHeader("#");
    tr1.appendChild(th1);
    tr1.appendChild(tableHeader("id"));
    tr1.appendChild(tableHeader("Name"));
    tr1.appendChild(tableHeader("Address"));
    tr1.appendChild(tableHeader("Phone"));
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
    trx.appendChild(tdElement(singleCustomer.phoneNo));
    trx.appendChild(tdElement(singleCustomer.mobile));
    trx.appendChild(tdElement(singleCustomer.email));
    return trx;
}

function showStatus(status) {
    let detail = document.getElementById("newCustomerBody");
    detail.innerHTML = "";
    let saveSmartCustomer = document.getElementById("saveSmartCustomer");
    saveSmartCustomer.style.display = "none";
    detail.appendChild(statusMessage(status, 'ADD'));
}

function editSmartCustomer() {
    console.log("Inside edit smart customer");
    let rowCheck = validateEditCustomerSelection();
    if (rowCheck) {
        editCustomerModal();
        setIdForChange();
        getCustomerForEdit();
    }
}

function validateEditCustomerSelection() {
    let footer = document.getElementById("edit-footer");
    footer.style.display = "none";
    let detail = document.getElementById("editCustomerBody");
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

function getCustomerForEdit() {
    let id = document.getElementById("id").value;
    let xhr = new XMLHttpRequest();
    xhr.open('GET', "/customer/getForEdit" + "?id=" + id, true);
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
    let id = document.getElementById("id").value;
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

function showUpdateStatus(status) {
    let detail = document.getElementById("editCustomerBody");
    detail.innerHTML = "";
    let updateSmartCustomer = document.getElementById("updateSmartCustomer");
    updateSmartCustomer.style.display = "none";
    detail.appendChild(statusMessage(status, 'UPDATE'));
}