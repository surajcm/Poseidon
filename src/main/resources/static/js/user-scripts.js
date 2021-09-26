

function hideAlerts() {
    document.getElementById('user').text = "User <span class='sr-only'>User</span>";
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
            alert("Only one row can be selected at a time, please select one row ");
            return false;
        }
    } else {
        alert("No rows selected, please select one row ");
        return false;
    }
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

function setIdForChange() {
    let checks = document.getElementsByName('checkField');
    let userRow;
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
}

function deleteUser() {
    let rowCheck = validateSelection();
    if (rowCheck) {
        deleteRow();
    }
}

//code to delete a user
function deleteRow() {
    let answer = confirm("Are you sure you wanted to delete the user ");
    if (answer) {
        setIdForChange();
        document.forms[0].action = "DeleteUser.htm";
        document.forms[0].submit();
    }
}

function search() {
    document.forms[0].action = "SearchUser.htm";
    document.forms[0].submit();
}

function clearOut() {
    document.getElementById("name").value = "";
    document.getElementById("email").value = "";
    document.getElementById("role").value = document.getElementById('role').options[0].value;
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

function rewriteTable(textReturned) {
    document.getElementById('myTable').innerHTML = "";
    let myTable = document.getElementById("myTable");
    myTable.setAttribute("class", "table table-bordered table-striped table-hover caption-top");
    let cap = document.createElement("caption");
    cap.innerHTML="User Details";
    myTable.appendChild(cap);
    let thead = document.createElement("thead");
    thead.setAttribute("class", "table-dark");
    let tr1 = document.createElement("tr");
    let th1 = document.createElement("th");
    th1.innerHTML = "id";
    th1.setAttribute("scope", "col");
    tr1.appendChild(th1);
    let th2 = document.createElement("th");
    th2.innerHTML = "Name";
    th2.setAttribute("scope", "col");
    tr1.appendChild(th2);
    let th3 = document.createElement("th");
    th3.innerHTML = "email";
    th3.setAttribute("scope", "col");
    tr1.appendChild(th3);
    let th4 = document.createElement("th");
    th4.innerHTML = "Role";
    th4.setAttribute("scope", "col");
    tr1.appendChild(th4);

    thead.appendChild(tr1);
    myTable.appendChild(thead);
    let userList = JSON.parse(textReturned);
    let tbody = document.createElement("tbody");
    for (let i = 0; i < userList.length; i++) {
        let singleUser = userList[i];
        let trx = document.createElement("tr");
        let td1 = document.createElement("th");
        td1.setAttribute("scope", "row");
        let inCheck = document.createElement("input");
        inCheck.setAttribute("type", "checkbox");
        inCheck.setAttribute("name", "checkField");
        inCheck.setAttribute("onclick", "javascript:checkCall(this)");
        inCheck.setAttribute("value", singleUser.id);
        td1.appendChild(inCheck);
        trx.appendChild(td1);
        let td2 = document.createElement("td");
        td2.innerHTML = singleUser.name;
        trx.appendChild(td2);
        let td3 = document.createElement("td");
        td3.innerHTML = singleUser.email;
        trx.appendChild(td3);
        let td4 = document.createElement("td");
        td4.innerHTML = singleUser.role;
        trx.appendChild(td4);
        tbody.appendChild(trx);
    }
    myTable.appendChild(tbody);
}

function addNewUser() {
    console.log("adding new user");
    let saveModal = document.getElementById("saveModal");
    saveModal.style.display = "block";
    let detail = document.getElementById("userModalBody");
    detail.innerHTML = "";

    let formValidUser = document.createElement("form");
    formValidUser.setAttribute("class", "row g-3 needs-validation");
    formValidUser.novalidate = true;

    let divName = document.createElement("div");
    divName.setAttribute("class", "col-md-4");
    let txtName = document.createElement("input");
    txtName.setAttribute("type", "text");
    txtName.setAttribute("class", "form-control");
    txtName.setAttribute("placeholder", "Name");
    txtName.setAttribute("id", "addName");
    txtName.required = true;
    divName.appendChild(txtName);
    let tt1 = document.createElement("div");
    tt1.setAttribute("class", "invalid-tooltip");
    tt1.innerHTML = "Please provide a valid name.";
    divName.appendChild(tt1);

    let divEmail = document.createElement("div");
    divEmail.setAttribute("class", "col-md-4");
    let txtEmail = document.createElement("input");
    txtEmail.setAttribute("type", "text");
    txtEmail.setAttribute("class", "form-control");
    txtEmail.setAttribute("placeholder", "email");
    txtEmail.setAttribute("id", "addEmail");
    txtEmail.required = true;
    divEmail.appendChild(txtEmail);
    let tt2 = document.createElement("div");
    tt2.setAttribute("class", "invalid-tooltip");
    tt2.innerHTML = "Please provide a valid email.";
    divEmail.appendChild(tt2);

    let divRole = document.createElement("div");
    divRole.setAttribute("class", "col-md-4");
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
    divRole.appendChild(selectRole);

    formValidUser.appendChild(divName);
    formValidUser.appendChild(divEmail);
    formValidUser.appendChild(divRole);
    detail.appendChild(formValidUser);
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
    xhr.open('POST', "/user/saveUserAjax.htm", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    xhr.setRequestHeader(header, token);
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
    let divStatus = document.createElement("div");
    divStatus.setAttribute("class", "pop-status");
    let imgSuccess = document.createElement("img");

    divStatus.appendChild(imgSuccess);
    let statusMessage = document.createElement("h3");
    if (status) {
        imgSuccess.setAttribute("src", "/img/tick.png");
        statusMessage.innerHTML = "Successfully added a new user !!";
    } else {
        imgSuccess.setAttribute("src", "/img/cross.svg");
        statusMessage.innerHTML = "Failed to save !!";
    }
    divStatus.appendChild(statusMessage);
    detail.appendChild(divStatus);
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
    xhr.open('POST', "/user/passwordExpire.htm", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                //rewriteTable(xhr.responseText);
                console.log(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
        }
    };
    xhr.send("id=" + id);
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

    let formValidUser = document.createElement("form");
    formValidUser.setAttribute("class", "row g-3 needs-validation");
    formValidUser.novalidate = true;

    let divName = document.createElement("div");
    divName.setAttribute("class", "col-md-4");
    let txtName = document.createElement("input");
    txtName.setAttribute("type", "text");
    txtName.setAttribute("class", "form-control");
    txtName.setAttribute("placeholder", "Name");
    txtName.setAttribute("id", "updateName");
    txtName.required = true;
    divName.appendChild(txtName);

    let divEmail = document.createElement("div");
    divEmail.setAttribute("class", "col-md-4");
    let txtEmail = document.createElement("input");
    txtEmail.setAttribute("type", "text");
    txtEmail.setAttribute("class", "form-control");
    txtEmail.setAttribute("placeholder", "email");
    txtEmail.setAttribute("id", "updateEmail");
    txtEmail.required = true;
    divEmail.appendChild(txtEmail);

    let divRole = document.createElement("div");
    divRole.setAttribute("class", "col-md-4");
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
    divRole.appendChild(selectRole);

    formValidUser.appendChild(divName);
    formValidUser.appendChild(divEmail);
    formValidUser.appendChild(divRole);
    detail.appendChild(formValidUser);
}

function getUserForEdit() {
    let id = document.getElementById("id").value;
    let xhr = new XMLHttpRequest();
    xhr.open('GET', "/user/getForEdit.htm" + "?id=" + id, true);
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
    xhr.open('PUT', "/user/updateUserAjax.htm", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    xhr.setRequestHeader(header, token);
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
    let divStatus = document.createElement("div");
    divStatus.setAttribute("class", "pop-status");
    let imgSuccess = document.createElement("img");

    divStatus.appendChild(imgSuccess);
    let statusMessage = document.createElement("h3");
    if (status) {
        imgSuccess.setAttribute("src", "/img/tick.png");
        statusMessage.innerHTML = "Successfully updated the user !!";
    } else {
        imgSuccess.setAttribute("src", "/img/cross.svg");
        statusMessage.innerHTML = "Failed to update !!";
    }
    divStatus.appendChild(statusMessage);
    detail.appendChild(divStatus);
}