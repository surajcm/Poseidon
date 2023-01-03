function hideAlerts() {
    document.getElementById('user').text = "User <span class='sr-only'>User</span>";
}

function search() {
    document.forms[0].action = "searchUser";
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
    //console.log(singleUser);
    let trx = document.createElement("tr");
    trx.appendChild(sideHeader(singleUser.id));
    trx.appendChild(photoIconOnTD());
    trx.appendChild(tdElement(singleUser.name));
    trx.appendChild(tdElement(singleUser.email));
    trx.appendChild(tdElement(showAllRoles(singleUser.roles)));
    trx.appendChild(enabledSwitcher(singleUser.enabled));
    trx.appendChild(editDeleteIcons(singleUser.id));
    return trx;
}
function photoIconOnTD() {
    const td2 = document.createElement("td");
    const photoSpan = document.createElement("span")
    photoSpan.setAttribute("class", "fa-solid fa-user fa-3x");
    td2.appendChild(photoSpan);
    return td2;
}

function enabledSwitcher(enabled) {
    const td2 = document.createElement("td");
    const enabledLink = document.createElement("a");
    if (enabled) {
        enabledLink.setAttribute("class", "fa-solid fa-circle-check");
    } else {
        enabledLink.setAttribute("class", "fa-solid fa-circle icon-dark");
    }
    enabledLink.setAttribute("href", "");
    td2.appendChild(enabledLink);
    return td2;
}
function editDeleteIcons(id) {
    const td2 = document.createElement("td");
    const editor = editIcon(id);
    const deleter = deleteIcon(id);
    td2.appendChild(editor);
    let str = td2.innerHTML;
    str += '&nbsp';
    td2.innerHTML = str;
    td2.appendChild(deleter);
    return td2;
}

function editIcon(id) {
    const editor = document.createElement("a");
    editor.setAttribute("class", "fa-regular fa-pen-to-square");
    editor.setAttribute("href", "");
    editor.setAttribute("data-bs-toggle", "modal");
    editor.setAttribute("data-bs-target", "#editUserModal");
    editor.setAttribute("title", "Edit this user");
    editor.setAttribute("onclick", "javascript:editUser("+id+")");
    return editor;
}

function deleteIcon(id) {
    const deleter = document.createElement("a");
    deleter.setAttribute("class", "fa-solid fa-trash");
    deleter.setAttribute("href", "/user/deleteUser/" + id);
    deleter.setAttribute("onclick", "javascript:deleteUser(event)");
    deleter.setAttribute("userId", id);
    deleter.setAttribute("title", "Delete this user");
    deleter.setAttribute("data-bs-toggle", "modal");
    deleter.setAttribute("data-bs-target", "#confirmModal");
    return deleter;
}

function showAllRoles(roles) {
    let allRoles = Array();
    for (let i = 0; i < roles.length; i++) {
        allRoles.push(roles[i].name);
    }
    return "[" + allRoles.join(",") + "]";
}

function tableHeaderRow() {
    let tr1 = document.createElement("tr");
    const th1 = tableHeader("");
    tr1.appendChild(th1);
    tr1.appendChild(tableHeader("Photo"));
    tr1.appendChild(tableHeader("Name"));
    tr1.appendChild(tableHeader("email"));
    tr1.appendChild(tableHeader("Roles"));
    tr1.appendChild(tableHeader("Enabled"));
    tr1.appendChild(tableHeader(""));
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
    // let's make an ajax call and get all roles
    getAllRolesToDropDown(selectRole);
    return selectRole;
}

function createRolesFromJson(textReturned, selectRole) {
    //console.log("/roles/ response is " + textReturned);
    let idNameMap = JSON.parse(textReturned);
    for (const [key, value] of Object.entries(idNameMap)) {
        //console.log("key and value are " + key + " " + value);
        let singleRole = document.createElement("option");
        singleRole.text = value;
        singleRole.value = key;
        selectRole.appendChild(singleRole);
    }
}

function getAllRolesToDropDown(selectRole) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', "/roles/", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                createRolesFromJson(xhr.responseText, selectRole);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
            //showError();
        }
    };
    xhr.send();
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
        saveThisUser(addName, addEmail, addRole);
    }
}

function saveThisUser(addName, addEmail, addRole) {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "/user/saveUser", true);
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

function deleteUser(e) {
    let yesButton = document.getElementById("yesButton");
    let parentHref = e.target.href;
    //console.log(parentHref);
    yesButton.setAttribute("href", parentHref);
    return false;
}

function editUser(id) {
    editUserModal();
    document.getElementById("id").value = id;
    getUserForEdit(id);
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
    // let's make an ajax call and get all roles
    getAllRolesToDropDown(selectRole);
    return selectRole;
}

function getUserForEdit(id) {
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
    let user = JSON.parse(textReturned);
    document.getElementById("updateName").value = user.name;
    document.getElementById("updateEmail").value = user.email;
    console.log(user.roles);
    document.getElementById("updateRole").value = user.roles[0].id;
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
    xhr.open('PUT', "/user/updateUser", true);
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