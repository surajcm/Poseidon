function hideAlerts() {
    document.getElementById('customer').text = "Customer <span class='sr-only'>Customer</span>";
}

function addCustomer() {
    document.forms[0].action = "addCustomer.htm";
    document.forms[0].submit();
}

function search() {
    if (document.getElementById("customerId").value === ""
        || isNumber(document.getElementById("customerId").value)) {
        document.forms[0].action = "searchCustomer.htm";
        document.forms[0].submit();
    } else {
        alert("Incorrect customerId format found, Please update the field with a numeric value");
    }
}

function isNumber(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

function clearOut() {
    document.getElementById("customerId").value = "";
    document.getElementById("customerName").value = "";
    document.getElementById("mobile").value = "";
    document.getElementById('includes').checked = false;
    document.getElementById('startsWith').checked = false;
}

function validateSelection() {
    let check = 'false';
    let count = selectedRowCount();
    if (count > 0) {
        check = 'true';
    }
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

function selectedRowCount() {
    let checks = document.getElementsByName('checkField');
    let count = 0;
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            count = 1;
        } else {
            for (let i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    count = count + 1;
                }
            }
        }
    }
    return count;
}

function editCustomer() {
    let rowCheck = validateSelection();
    if (rowCheck) {
        editRow();
    }
}

function editRow() {
    let userRow = selectedRow();
    document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
    document.forms[0].action = "editCust.htm";
    document.forms[0].submit();
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
        document.forms[0].action = "deleteCust.htm";
        document.forms[0].submit();
    }
}

//preventing multiple checks
function checkCall(e) {
    const min = e.value;
    const checks = document.getElementsByName('checkField');
    for (let i = 0; i < checks.length; i++) {
        if (checks[i].value !== min) {
            checks[i].checked = false;
        }
    }
}

function viewCustomer() {
    let rowCheck = validateSelection();
    if (rowCheck) {
        viewRow();
    }
}

function viewRow() {
    let userRow = selectedRow();
    let customerId = userRow.cells[0].childNodes[0].value;
    callAjax(customerId);
}


function callAjax(customerId) {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', "viewCustomer.htm", true);
    const token = document.querySelector("meta[name='_csrf']").content;
    const header = document.querySelector("meta[name='_csrf_header']").content;
    xhr.setRequestHeader(header, token);
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
    const lbId = document.createElement("label");
    lbId.setAttribute("class", "form-label");
    lbId.textContent = "Customer Id : ";
    divId.appendChild(lbId);
    const txtId = document.createElement("label");
    txtId.setAttribute("class", "form-label");
    txtId.textContent = customer.customerId;
    divId.appendChild(txtId);
    const divCustomer = document.createElement("div");
    divCustomer.setAttribute("class", "col-md-6");
    const lbName = document.createElement("label");
    lbName.setAttribute("class", "form-label");
    lbName.textContent = "Customer Name : ";
    divCustomer.appendChild(lbName);
    const txtName = document.createElement("label");
    txtName.setAttribute("class", "form-label");
    txtName.textContent = customer.customerName;
    divCustomer.appendChild(txtName);

    const divAddress = document.createElement("div");
    divAddress.setAttribute("class", "col-md-12");
    const lbAddress = document.createElement("label");
    lbAddress.setAttribute("class", "form-label");
    lbAddress.textContent = "Address : ";
    divAddress.appendChild(lbAddress);
    const txtAddress = document.createElement("label");
    txtAddress.setAttribute("class", "form-label");
    txtAddress.textContent = customer.address;
    divAddress.appendChild(txtAddress);

    const divPhone = document.createElement("div");
    divPhone.setAttribute("class", "col-md-4");
    const lbPhone = document.createElement("label");
    lbPhone.setAttribute("class", "form-label");
    lbPhone.textContent = "Phone No : ";
    divPhone.appendChild(lbPhone);
    const txtPhone = document.createElement("label");
    txtPhone.setAttribute("class", "form-label");
    txtPhone.textContent = customer.phoneNo;
    divPhone.appendChild(txtPhone);
    const divMobile = document.createElement("div");
    divMobile.setAttribute("class", "col-md-4");
    const lbMobile = document.createElement("label");
    lbMobile.setAttribute("class", "form-label");
    lbMobile.textContent = "Mobile : ";
    divMobile.appendChild(lbMobile);
    const txtMobile = document.createElement("label");
    txtMobile.setAttribute("class", "form-label");
    txtMobile.textContent = customer.mobile;
    divMobile.appendChild(txtMobile);
    const divEmail = document.createElement("div");
    divEmail.setAttribute("class", "col-md-4");
    const lbEmail = document.createElement("label");
    lbEmail.setAttribute("class", "form-label");
    lbEmail.textContent = "Email : ";
    divEmail.appendChild(lbEmail);
    const txtEmail = document.createElement("label");
    txtEmail.setAttribute("class", "form-label");
    txtEmail.textContent = customer.email;
    divEmail.appendChild(txtEmail);

    const divPerson = document.createElement("div");
    divPerson.setAttribute("class", "col-md-6");
    const lbPerson = document.createElement("label");
    lbPerson.setAttribute("class", "form-label");
    lbPerson.textContent = "Contact Person : ";
    divPerson.appendChild(lbPerson);
    const txtPerson = document.createElement("label");
    txtPerson.setAttribute("class", "form-label");
    txtPerson.textContent = customer.contactPerson;
    divPerson.appendChild(txtPerson);
    const divPMobile = document.createElement("div");
    divPMobile.setAttribute("class", "col-md-6");
    const lbPMobile = document.createElement("label");
    lbPMobile.setAttribute("class", "form-label");
    lbPMobile.textContent = "Contact Mobile : ";
    divPMobile.appendChild(lbPMobile);
    const txtPMobile = document.createElement("label");
    txtPMobile.setAttribute("class", "form-label");
    txtPMobile.textContent = customer.contactMobile;
    divPMobile.appendChild(txtPMobile);


    const divNotes = document.createElement("div");
    divNotes.setAttribute("class", "col-md-12");
    const lbNotes = document.createElement("label");
    lbNotes.setAttribute("class", "form-label");
    lbNotes.textContent = "Notes : ";
    divNotes.appendChild(lbNotes);
    const txtNotes = document.createElement("label");
    txtNotes.setAttribute("class", "form-label");
    txtNotes.textContent = customer.notes;
    divNotes.appendChild(txtNotes);

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
    let formValidCustomer = customerOnModal();
    detail.appendChild(formValidCustomer);
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

function aTextBox(id, placeHolder, isRequired) {
    let txtBox = document.createElement("input");
    txtBox.setAttribute("type", "text");
    txtBox.setAttribute("class", "form-control");
    txtBox.setAttribute("placeholder", placeHolder);
    txtBox.setAttribute("id", id);
    if (isRequired) {
        txtBox.required = true;
    }
    return txtBox;
}

function aTextArea(id, placeHolder, isRequired) {
    let txtArea = document.createElement("textarea");
    txtArea.setAttribute("rows", "3");
    txtArea.setAttribute("class", "form-control");
    txtArea.setAttribute("placeholder", placeHolder);
    txtArea.setAttribute("id", id);
    if (isRequired) {
        txtArea.required = true;
    }
    return txtArea;
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
        ajaxSaveCustomer(modalCustomerName.value,
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

function ajaxSaveCustomer(modalCustomerName,
                          modalAddress,
                          modalPhone,
                          modalMobile,
                          modalEmail,
                          modalContact,
                          modalContactMobile,
                          modalNotes) {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "/customer/saveCustomerAjax.htm", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                //rewriteTable(xhr.responseText);
                //showStatus(true);
                console.log("response received :" + xhr.responseText)
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
            //showStatus(false);
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