function validateSelection() {
    var check ='false';
    var count = 0;
    // get all check boxes
    var checks = document.getElementsByName('checkField');
    if(checks){
        //if total number of rows is one
        if (checks.checked) {
            return true;
        } else {
            for(var i = 0 ; i < checks.length ; i++ ) {
                if (checks[i].checked) {
                    check = 'true';
                    count = count + 1;
                }
            }
            //check for validity
            if (check = 'true') {
                if (count == 1) {
                    return true;
                } else {
                    alert(" Only one row can be selected at a time, please select one row ");
                }
            } else {
                alert(" No rows selected, please select one row ");
            }
        }
    }
}

function setIdForChange() {
    var userRow;
    var checks = document.getElementsByName('checkField');
    if(checks.checked){
        userRow = document.getElementById("myTable").rows[0];
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
    } else {
        for(var i = 0; i < checks.length ; i++){
            if(checks[i].checked) {
                userRow = document.getElementById("myTable").rows[i+1];
            }
        }
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
    }
}

function deleteUser() {
    rowCheck = validateSelection();
    if (rowCheck) {
        deleteRow();
    }
}

//code to delete a user
function deleteRow() {
    var answer = confirm(" Are you sure you wanted to delete the user ");
    if (answer) {
        setIdForChange();
        document.forms[0].action="DeleteUser.htm";
        document.forms[0].submit();
    }
}


//validation before edit a user
function editMe() {
    rowCheck = validateSelection();
    if(rowCheck) {
        setIdForChange();
        document.forms[0].action="EditUser.htm";
        document.forms[0].submit();
    }
}

function search() {
    document.forms[0].action="SearchUser.htm";
    document.forms[0].submit();
}

function clearOut() {
    document.getElementById("name").value = "";
    document.getElementById("email").value = "";
    document.getElementById("role").value = document.getElementById('role').options[0].value;
}

function hideAlerts(){
    document.getElementById('user').text = "User <span class='sr-only'>User</span>";
}

//preventing multiple checks
function checkCall(e) {
    var min = e.value;
    var checks = document.getElementsByName('checkField');
    for(var i = 0; i < checks.length ; i++){
        if(checks[i].value != min) {
            checks[i].checked = false;
        }
    }
}

function rewriteTable(textReturned) {
    document.getElementById('myTable').innerHTML = "";
    var myTable = document.getElementById("myTable");
    var thead = document.createElement("thead");
    var tr1 = document.createElement("tr");
    var th1 = document.createElement("th");
    th1.innerHTML = "#";
    th1.setAttribute("class","text-center");
    tr1.appendChild(th1);

    var th2 = document.createElement("th");
    th2.innerHTML = "Name";
    th2.setAttribute("class","text-center");
    tr1.appendChild(th2);

    var th3 = document.createElement("th");
    th3.innerHTML = "email";
    th3.setAttribute("class","text-center");
    tr1.appendChild(th3);


    var th4 = document.createElement("th");
    th4.innerHTML = "Role";
    th4.setAttribute("class","text-center");
    tr1.appendChild(th4);

    thead.appendChild(tr1);
    myTable.appendChild(thead);
    var userList = JSON.parse(textReturned);
    var tbody = document.createElement("tbody");
    for (var i = 0; i < userList.length; i++) {
        var singleUser = userList[i];
        var trx = document.createElement("tr");
        var td1 = document.createElement("td");
        var inCheck = document.createElement("input");
        inCheck.setAttribute("type","checkbox");
        inCheck.setAttribute("name","checkField");
        inCheck.setAttribute("onclick","javascript:checkCall(this)");
        inCheck.setAttribute("value",singleUser.id);
        td1.appendChild(inCheck);
        trx.appendChild(td1);
        var td2 = document.createElement("td");
        td2.innerHTML = singleUser.name;
        trx.appendChild(td2);
        var td3 = document.createElement("td");
        td3.innerHTML = singleUser.email;
        trx.appendChild(td3);

        var td4 = document.createElement("td");
        td4.innerHTML = singleUser.role;
        trx.appendChild(td4);

        tbody.appendChild(trx);
    }
    myTable.appendChild(tbody);
}

function addNewUser() {
    var saveModal = document.getElementById("saveModal");
    saveModal.style.display = "block";
    var detail = document.getElementById("userModalBody");
    detail.innerHTML = "";

    var formValidUser = document.createElement("form");
    formValidUser.setAttribute("class","needs-validation");
    formValidUser.novalidate = true;

    var divUserAdd = document.createElement("div");
    divUserAdd.setAttribute("class","form-row align-items-left");
    var divName = document.createElement("div");
    divName.setAttribute("class","form-group col-md-4");
    var txtName = document.createElement("input");
    txtName.setAttribute("type","text");
    txtName.setAttribute("class","form-control");
    txtName.setAttribute("placeholder","Name");
    txtName.setAttribute("id","addName");
    txtName.required = true;
    divName.appendChild(txtName);
    var tt1 = document.createElement("div");
    tt1.setAttribute("class","invalid-tooltip");
    tt1.innerHTML = "Please provide a valid name.";
    divName.appendChild(tt1);

    var divEmail = document.createElement("div");
    divEmail.setAttribute("class","form-group col-md-4");
    var txtEmail = document.createElement("input");
    txtEmail.setAttribute("type","text");
    txtEmail.setAttribute("class","form-control");
    txtEmail.setAttribute("placeholder","email");
    txtEmail.setAttribute("id","addEmail");
    txtEmail.required = true;
    divEmail.appendChild(txtEmail);
    var tt2 = document.createElement("div");
    tt2.setAttribute("class","invalid-tooltip");
    tt2.innerHTML = "Please provide a valid email.";
    divEmail.appendChild(tt2);

    var divRole = document.createElement("div");
    divRole.setAttribute("class","form-group col-md-4");
    var selectRole = document.createElement("select");
    selectRole.setAttribute("class","form-control");
    selectRole.setAttribute("id","addRole");
    var adminOption = document.createElement("option");
    adminOption.text = 'ADMIN';
    adminOption.value = 'ADMIN';
    var guestOption = document.createElement("option");
    guestOption.text = 'GUEST';
    guestOption.value = 'GUEST';
    selectRole.appendChild(adminOption);
    selectRole.appendChild(guestOption);
    divRole.appendChild(selectRole);

    divUserAdd.appendChild(divName);
    divUserAdd.appendChild(divEmail);
    divUserAdd.appendChild(divRole);
    formValidUser.appendChild(divUserAdd);
    detail.appendChild(formValidUser);
}

function saveFromModal() {
    var addName = document.getElementById("addName").value;
    var addEmail = document.getElementById("addEmail").value;
    var addRole = document.getElementById("addRole").value;
    var forms = document.getElementsByClassName('needs-validation');
    var allFieldsAreValid = true;
    
    if (forms[0].checkValidity() === false) {
        allFieldsAreValid = false;
        if (addName.length === 0) {
            document.getElementById("addName").setAttribute("class","form-control is-invalid");   
        } else {
            document.getElementById("addName").setAttribute("class","form-control was-validated");   
        }
        if (addEmail.length === 0) {
            document.getElementById("addEmail").setAttribute("class","form-control is-invalid");
        } else {
            document.getElementById("addEmail").setAttribute("class","form-control was-validated");
        }
    }

    if(allFieldsAreValid) {
        callAjax(addName,addEmail,addRole);
    }
}

function callAjax(addName,addEmail,addRole) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "/user/saveUserAjax.htm",true);
    var token = document.querySelector("meta[name='_csrf']").content;
    var header = document.querySelector("meta[name='_csrf_header']").content;
    xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function() {
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
    xhr.send("selectName=" + addName + "&selectLogin=" + addEmail + "&selectRole=" + addRole );
}

function showStatus(status) {
    var detail = document.getElementById("userModalBody");
    detail.innerHTML = "";
    var saveModal = document.getElementById("saveModal");
    saveModal.style.display = "none";
    var divStatus = document.createElement("div");
    divStatus.setAttribute("class","pop-status");
    var imgSuccess = document.createElement("img");
    
    divStatus.appendChild(imgSuccess);
    var statusMessage = document.createElement("h3");
    if(status) {
        imgSuccess.setAttribute("src","/img/tick.png");
        statusMessage.innerHTML = "Successfully added a new user !!";    
    } else {
        imgSuccess.setAttribute("src","/img/cross.svg");
        statusMessage.innerHTML = "Failed to save !!";
    }
    divStatus.appendChild(statusMessage);
    detail.appendChild(divStatus);
}

function resetUser() {
    rowCheck = validateSelection();
    if (rowCheck) {
        setIdForChange();
        //submit ajax request for resetting password !!!
        console.log("going to reset password on : "+ document.getElementById("id").value);
        ajaxPasswordExpire();
    }
}

function ajaxPasswordExpire() {
    var id = document.getElementById("id").value;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "/user/passwordExpire.htm",true);
    var token = document.querySelector("meta[name='_csrf']").content;
    var header = document.querySelector("meta[name='_csrf_header']").content;
    xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function() {
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

function validateEditModalSelection() {
    var detail = document.getElementById("userEditModalBody");
    detail.innerHTML = "";
    var check ='false';
    var count = 0;
    // get all check boxes
    var checks = document.getElementsByName('checkField');
    if(checks){
        //if total number of rows is one
        if (checks.checked) {
            return true;
        } else {
            for(var i = 0 ; i < checks.length ; i++ ) {
                if (checks[i].checked) {
                    check = 'true';
                    count = count + 1;
                }
            }
            //check for validity
            if (check = 'true') {
                if (count == 1) {
                    return true;
                } else {
                detail.innerHTML = "<p>Only one row can be selected at a time, please select one row</p>";
                }
            } else {
                detail.innerHTML = "<p>No rows selected, please select one row</p>";
            }
        }
    }
}

function editUser() {
    rowCheck = validateEditModalSelection();
    if(rowCheck) {
        editUserModal();
        setIdForChange();
        var user_id = document.getElementById("id").value;
        // also update the fields
        getUserForEdit();
    }
}

function editUserModal() {
    var updateModal = document.getElementById("updateModal");
    updateModal.style.display = "block";
    var detail = document.getElementById("userEditModalBody");
    detail.innerHTML = "";

    var formValidUser = document.createElement("form");
    formValidUser.setAttribute("class","needs-validation2");
    formValidUser.novalidate = true;

    var divUserEdit = document.createElement("div");
    divUserEdit.setAttribute("class","form-row align-items-left");
    var divName = document.createElement("div");
    divName.setAttribute("class","form-group col-md-4");
    var txtName = document.createElement("input");
    txtName.setAttribute("type","text");
    txtName.setAttribute("class","form-control");
    txtName.setAttribute("placeholder","Name");
    txtName.setAttribute("id","updateName");
    txtName.required = true;
    divName.appendChild(txtName);

    var divEmail = document.createElement("div");
    divEmail.setAttribute("class","form-group col-md-4");
    var txtEmail = document.createElement("input");
    txtEmail.setAttribute("type","text");
    txtEmail.setAttribute("class","form-control");
    txtEmail.setAttribute("placeholder","email");
    txtEmail.setAttribute("id","updateEmail");
    txtEmail.required = true;
    divEmail.appendChild(txtEmail);

    var divRole = document.createElement("div");
    divRole.setAttribute("class","form-group col-md-4");
    var selectRole = document.createElement("select");
    selectRole.setAttribute("class","form-control");
    selectRole.setAttribute("id","updateRole");
    var adminOption = document.createElement("option");
    adminOption.text = 'ADMIN';
    adminOption.value = 'ADMIN';
    var guestOption = document.createElement("option");
    guestOption.text = 'GUEST';
    guestOption.value = 'GUEST';
    selectRole.appendChild(adminOption);
    selectRole.appendChild(guestOption);
    divRole.appendChild(selectRole);

    divUserEdit.appendChild(divName);
    divUserEdit.appendChild(divEmail);
    divUserEdit.appendChild(divRole);
    formValidUser.appendChild(divUserEdit);
    detail.appendChild(formValidUser);
}


function getUserForEdit() {
    var id = document.getElementById("id").value;
    var xhr = new XMLHttpRequest();
    xhr.open('GET', "/user/getForEdit.htm" + "?id=" + id,true);
    var token = document.querySelector("meta[name='_csrf']").content;
    var header = document.querySelector("meta[name='_csrf_header']").content;
    xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function() {
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
    var detail = document.getElementById("userEditModalBody");
    detail.innerHTML = "";
    var updateModal = document.getElementById("updateModal");
    updateModal.style.display = "none";
    var divStatus = document.createElement("div");
    divStatus.setAttribute("class","pop-status");
    var imgSuccess = document.createElement("img");

    divStatus.appendChild(imgSuccess);
    var statusMessage = document.createElement("h3");
    imgSuccess.setAttribute("src","/img/cross.svg");
    statusMessage.innerHTML = "Failed to populate data !!";
    divStatus.appendChild(statusMessage);
    detail.appendChild(divStatus);
}

function populateDataForEdit(textReturned) {
    var userMap = JSON.parse(textReturned);
    document.getElementById("updateName").value = userMap.name;
    document.getElementById("updateEmail").value = userMap.email;
    document.getElementById("updateRole").value = userMap.role;
}

function updateFromModal() {
    var updateName = document.getElementById("updateName").value;
    var updateEmail = document.getElementById("updateEmail").value;
    var updateRole = document.getElementById("updateRole").value;
    var forms = document.getElementsByClassName('needs-validation2');
    var allFieldsAreValid = true;

    if (forms[0].checkValidity() === false) {
        allFieldsAreValid = false;
        if (updateName.length === 0) {
            document.getElementById("updateName").setAttribute("class","form-control is-invalid");
        } else {
            document.getElementById("updateName").setAttribute("class","form-control was-validated");
        }
        if (updateEmail.length === 0) {
            document.getElementById("updateEmail").setAttribute("class","form-control is-invalid");
        } else {
            document.getElementById("updateEmail").setAttribute("class","form-control was-validated");
        }
    }

    if(allFieldsAreValid) {
        callAjaxUpdate(updateName,updateEmail,updateRole);
    }
}

function callAjaxUpdate(updateName,updateEmail,updateRole) {
    var id = document.getElementById("id").value;
    var xhr = new XMLHttpRequest();
    xhr.open('PUT', "/user/updateUserAjax.htm",true);
    var token = document.querySelector("meta[name='_csrf']").content;
    var header = document.querySelector("meta[name='_csrf_header']").content;
    xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function() {
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
    xhr.send("id="+id+
    "&name=" + updateName + "&email=" + updateEmail + "&role=" + updateRole );
}

function showUpdateStatus(status) {
    var detail = document.getElementById("userEditModalBody");
    detail.innerHTML = "";
    var updateModal = document.getElementById("updateModal");
    updateModal.style.display = "none";
    var divStatus = document.createElement("div");
    divStatus.setAttribute("class","pop-status");
    var imgSuccess = document.createElement("img");

    divStatus.appendChild(imgSuccess);
    var statusMessage = document.createElement("h3");
    if(status) {
        imgSuccess.setAttribute("src","/img/tick.png");
        statusMessage.innerHTML = "Successfully updated the user !!";
    } else {
        imgSuccess.setAttribute("src","/img/cross.svg");
        statusMessage.innerHTML = "Failed to update !!";
    }
    divStatus.appendChild(statusMessage);
    detail.appendChild(divStatus);
}