

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

