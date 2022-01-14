function hideAlerts() {
    document.getElementById('user').text = "User <span class='sr-only'>User</span>";
}

function validateEditModalSelection() {
    let detail = document.getElementById("userEditModalBody");
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

function deleteUser() {
    let rowCheck = validateSelection();
    if (rowCheck) {
        deleteRow();
    }
}

function deleteRow() {
    let answer = confirm("Are you sure you wanted to delete the user ");
    if (answer) {
        setIdForChange();
        document.forms[0].action = "DeleteUser";
        document.forms[0].submit();
    }
}

function search() {
    document.forms[0].action = "SearchUser";
    document.forms[0].submit();
}

function clearOut() {
    document.getElementById("name").value = "";
    document.getElementById("email").value = "";
    document.getElementById("role").value = document.getElementById('role').options[0].value;
}

function rewriteTable(textReturned) {
    document.getElementById('myTable').innerHTML = "";
    let myTable = document.getElementById("myTable");
    myTable.setAttribute("class", "table table-bordered table-striped table-hover caption-top");
    myTable.appendChild(setCaption("User Details"));
    const thead = tableHead();
    myTable.appendChild(thead);
    myTable.appendChild(tableBodyCreation(textReturned));
}

function singleRowInTheTable(singleUser) {
    let trx = document.createElement("tr");
    trx.appendChild(sideHeader(singleUser.id));
    trx.appendChild(tdElement(singleUser.name));
    trx.appendChild(tdElement(singleUser.email));
    trx.appendChild(tdElement(singleUser.role));
    return trx;
}

function tableHeaderRow() {
    let tr1 = document.createElement("tr");
    const th1 = tableHeader("id");
    tr1.appendChild(th1);
    tr1.appendChild(tableHeader("Name"));
    tr1.appendChild(tableHeader("email"));
    tr1.appendChild(tableHeader("Role"));
    return tr1;
}

function addNewUser() {
    let saveModal = document.getElementById("saveModal");
    saveModal.style.display = "block";
    let detail = document.getElementById("userModalBody");
    detail.innerHTML = "";
    detail.appendChild(userOnModal());
}

function userOnModal() {
    let formValidUser = document.createElement("form");
    formValidUser.setAttribute("class", "row g-3 needs-validation");
    formValidUser.novalidate = true;

    let divName = document.createElement("div");
    divName.setAttribute("class", "col-md-4");

    let txtName = aTextBox("addName", "Name", true);
    divName.appendChild(txtName);

    let tt1 = document.createElement("div");
    tt1.setAttribute("class", "invalid-tooltip");
    tt1.innerHTML = "Please provide a valid name.";
    divName.appendChild(tt1);

    let divEmail = document.createElement("div");
    divEmail.setAttribute("class", "col-md-4");
    let txtEmail = aTextBox("addEmail", "email", true);
    divEmail.appendChild(txtEmail);
    let tt2 = document.createElement("div");
    tt2.setAttribute("class", "invalid-tooltip");
    tt2.innerHTML = "Please provide a valid email.";
    divEmail.appendChild(tt2);

    let divRole = document.createElement("div");
    divRole.setAttribute("class", "col-md-4");
    divRole.appendChild(selectRole());

    formValidUser.appendChild(divName);
    formValidUser.appendChild(divEmail);
    formValidUser.appendChild(divRole);
    return formValidUser;
}

function selectRole() {
    let selectRole = document.createElement("select");
    selectRole.setAttribute("class", "form-select");
    selectRole.setAttribute("id", "addRole");
    let adminOption = document.createElement("option");
    adminOption.text = 'ADMIN';
    adminOption.value = 'ADMIN';
    let guestOption = document.createElement("option");
    guestOption.text = 'GUEST';
    guestOption.value = 'GUEST';
    selectRole.appendChild(adminOption);
    selectRole.appendChild(guestOption);
    return selectRole;
}

function saveFromModal() {
    let addName = document.getElementById("addName").value;
    let addEmail = document.getElementById("addEmail").value;
    let addRole = document.getElementById("addRole").value;
    let forms = document.getElementsByClassName('needs-validation');
    let allFieldsAreValid = true;

    if (forms[0].checkValidity() === false) {
        allFieldsAreValid = false;
        if (addName.length === 0) {
            document.getElementById("addName").setAttribute("class", "form-control is-invalid");
        } else {
            document.getElementById("addName").setAttribute("class", "form-control was-validated");
        }
        if (addEmail.length === 0) {
            document.getElementById("addEmail").setAttribute("class", "form-control is-invalid");
        } else {
            document.getElementById("addEmail").setAttribute("class", "form-control was-validated");
        }
    }

    if (allFieldsAreValid) {
        callAjax(addName, addEmail, addRole);
    }
}

function callAjax(addName, addEmail, addRole) {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "/user/saveUserAjax", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                rewriteTable(xhr.responseText);
                showStatus(true);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
            showStatus(false);
        }
    };
    xhr.send("selectName=" + addName + "&selectLogin=" + addEmail + "&selectRole=" + addRole);
}

function showStatus(status) {
    let detail = document.getElementById("userModalBody");
    detail.innerHTML = "";
    let saveModal = document.getElementById("saveModal");
    saveModal.style.display = "none";
    detail.appendChild(statusMessage(status, 'ADD'));
}

function resetUser() {
    let rowCheck = validateSelection();
    if (rowCheck) {
        setIdForChange();
        console.log("going to reset password on : " + document.getElementById("id").value);
        ajaxPasswordExpire();
    }
}

function ajaxPasswordExpire() {
    let id = document.getElementById("id").value;
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "/user/passwordExpire", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                console.log(xhr.responseText);
                showResetStatus(true);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
        }
    };
    xhr.send("id=" + id);
}

function showResetStatus(status) {
    let detail = document.getElementById("resetModalBody");
    detail.innerHTML = "";
    detail.appendChild(statusMessage(status, 'UPDATE'));
}

function editUser() {
    let rowCheck = validateEditModalSelection();
    if (rowCheck) {
        editUserModal();
        setIdForChange();
        getUserForEdit();
    }
}

function editUserModal() {
    let updateModal = document.getElementById("updateModal");
    updateModal.style.display = "block";
    let detail = document.getElementById("userEditModalBody");
    detail.innerHTML = "";
    detail.appendChild(formValidUserForEdit());
}

function formValidUserForEdit() {
    let formValidUser = document.createElement("form");
    formValidUser.setAttribute("class", "row g-3 needs-validation");
    formValidUser.novalidate = true;

    let divName = document.createElement("div");
    divName.setAttribute("class", "col-md-4");
    let txtName = aTextBox("updateName", "Name", true);
    divName.appendChild(txtName);

    let divEmail = document.createElement("div");
    divEmail.setAttribute("class", "col-md-4");
    let txtEmail = aTextBox("updateEmail", "email", true);
    divEmail.appendChild(txtEmail);

    let divRole = document.createElement("div");
    divRole.setAttribute("class", "col-md-4");
    divRole.appendChild(selectRoleForUpdate());
    formValidUser.appendChild(divName);
    formValidUser.appendChild(divEmail);
    formValidUser.appendChild(divRole);
    return formValidUser;
}

function selectRoleForUpdate() {
    let selectRole = document.createElement("select");
    selectRole.setAttribute("class", "form-select");
    selectRole.setAttribute("id", "updateRole");
    let adminOption = document.createElement("option");
    adminOption.text = 'ADMIN';
    adminOption.value = 'ADMIN';
    let guestOption = document.createElement("option");
    guestOption.text = 'GUEST';
    guestOption.value = 'GUEST';
    selectRole.appendChild(adminOption);
    selectRole.appendChild(guestOption);
    return selectRole;
}

function getUserForEdit() {
    let id = document.getElementById("id").value;
    let xhr = new XMLHttpRequest();
    xhr.open('GET', "/user/getForEdit" + "?id=" + id, true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    ///xhr.setRequestHeader(header, token);
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
    let detail = document.getElementById("userEditModalBody");
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
    let userMap = JSON.parse(textReturned);
    document.getElementById("updateName").value = userMap.name;
    document.getElementById("updateEmail").value = userMap.email;
    document.getElementById("updateRole").value = userMap.role;
}

function updateFromModal() {
    let updateName = document.getElementById("updateName").value;
    let updateEmail = document.getElementById("updateEmail").value;
    let updateRole = document.getElementById("updateRole").value;
    let forms = document.getElementsByClassName('needs-validation');
    let allFieldsAreValid = true;

    if (forms[0].checkValidity() === false) {
        allFieldsAreValid = false;
        if (updateName.length === 0) {
            document.getElementById("updateName").setAttribute("class", "form-control is-invalid");
        } else {
            document.getElementById("updateName").setAttribute("class", "form-control was-validated");
        }
        if (updateEmail.length === 0) {
            document.getElementById("updateEmail").setAttribute("class", "form-control is-invalid");
        } else {
            document.getElementById("updateEmail").setAttribute("class", "form-control was-validated");
        }
    }

    if (allFieldsAreValid) {
        callAjaxUpdate(updateName, updateEmail, updateRole);
    }
}

function callAjaxUpdate(updateName, updateEmail, updateRole) {
    let id = document.getElementById("id").value;
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', "/user/updateUserAjax", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                rewriteTable(xhr.responseText);
                showUpdateStatus(true);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
            showUpdateStatus(false);
        }
    };
    xhr.send("id=" + id + "&name=" + updateName + "&email=" + updateEmail + "&role=" + updateRole);
}

function showUpdateStatus(status) {
    let detail = document.getElementById("userEditModalBody");
    detail.innerHTML = "";
    let updateModal = document.getElementById("updateModal");
    updateModal.style.display = "none";
    detail.appendChild(statusMessage(status, 'UPDATE'));
}