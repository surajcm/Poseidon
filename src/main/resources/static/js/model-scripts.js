function hideAlerts() {
    document.getElementById('make').text = "Make <span class='sr-only'>Make</span>";
}

function listAllMake() {
    document.forms[0].action = "MakeList";
    document.forms[0].submit();
}

function search() {
    document.forms[0].action = "searchModel";
    document.forms[0].submit();
}

function clearOut() {
    document.getElementById("makeName").value = document.getElementById('makeName').options[0].value;
    document.getElementById('modelName').value = "";
    document.getElementById('includes').checked = false;
    document.getElementById('startswith').checked = false;
}

function deleteModel() {
    let rowCheck = validateSelection();
    if (rowCheck) {
        deleteRow();
    }
}

function validateEditModalSelection() {
    let check = 'false';
    let count = selectedRowCount();
    if (count > 0) {
        check = 'true';
    }
    //check for validity
    let detail = document.getElementById("modelEditModalBody");
    if (check === 'true') {
        if (count === 1) {
            return true;
        } else {
            detail.innerHTML = "<p>Only one row can be selected at a time, please select one row</p>";
            return false;
        }
    } else {
        detail.innerHTML = "<p>No rows selected, please select one row</p>";
        return false;
    }
}

function validateSelection() {
    let check = 'false';
    let count = selectedRowCount();
    if (count > 0) {
        check = 'true';
    }
    //check for validity
    if (check === 'true') {
        if (count === 1) {
            return true;
        } else {
            alert("Only one row can be deleted at a time, please select one row ");
            return false;
        }
    } else {
        alert("No rows selected, please select one row ");
        return false;
    }
}

//code to delete
function deleteRow() {
    let answer = confirm("Are you sure you wanted to delete the user ");
    if (answer) {
        let userRow = selectedRow();
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        document.forms[0].action = "deleteModel";
        document.forms[0].submit();
    }
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

function getAllMakeIdsAndNames() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', "/make/getAllMakeIdsAndNames", true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                console.log("Response is " + xhr.responseText)
                addMakesToSelect(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
        }
    };
    xhr.send();
}

function addModel() {
    let saveModal = document.getElementById("saveModal");
    saveModal.style.display = "block";
    let detail = document.getElementById("modelModalBody");
    detail.innerHTML = "";

    let formValidModel = document.createElement("form");
    formValidModel.setAttribute("class", "row g-3 needs-validation");
    formValidModel.novalidate = true;

    let divName = document.createElement("div");
    divName.setAttribute("class", "col-md-4");
    let selectMakeName = document.createElement("select");
    selectMakeName.setAttribute("class", "form-select");
    selectMakeName.setAttribute("id", "modalMakeNameForUpdate");
    divName.appendChild(selectMakeName);
    //ajax and add

    getAllMakeIdsAndNames();

    let divModelName = document.createElement("div");
    divModelName.setAttribute("class", "col-md-4");
    let txtModelName = document.createElement("input");
    txtModelName.setAttribute("type", "text");
    txtModelName.setAttribute("class", "form-control");
    txtModelName.setAttribute("placeholder", "Model Name");
    txtModelName.setAttribute("id", "modalModelName");
    txtModelName.required = true;
    divModelName.appendChild(txtModelName);
    let tt2 = document.createElement("div");
    tt2.setAttribute("class", "invalid-tooltip");
    tt2.innerHTML = "Please provide a valid modelName.";
    divModelName.appendChild(tt2);

    formValidModel.appendChild(divName);
    formValidModel.appendChild(divModelName);
    detail.appendChild(formValidModel);
}


function addMakesToSelect(response) {
    console.log("Response is " + response);
    let modelMap = JSON.parse(response);
    let modalMakeName = document.getElementById('modalMakeNameForUpdate');
    for (const [key, value] of Object.entries(modelMap)) {
        let mainOption = document.createElement("option");
        mainOption.text = value;
        mainOption.value = key;
        modalMakeName.appendChild(mainOption);
    }
}

function saveFromModal() {
    let modalMakeName = document.getElementById("modalMakeNameForUpdate").value;
    let modalModelName = document.getElementById("modalModelName").value;
    let forms = document.getElementsByClassName('needs-validation');
    let allFieldsAreValid = true;

    if (forms[0].checkValidity() === false) {
        allFieldsAreValid = false;
        if (modalModelName.length === 0) {
            document.getElementById("modalModelName").setAttribute("class", "form-control is-invalid");
        } else {
            document.getElementById("modalModelName").setAttribute("class", "form-control was-validated");
        }
    }
    if (allFieldsAreValid) {
        saveModel(modalMakeName, modalModelName);
    }
}

function saveModel(modalMakeName, modalModelName) {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "/make/saveModel", true);
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
    xhr.send("selectMakeId=" + modalMakeName + "&selectModelName=" + modalModelName);
}

function showStatus(status) {
    let detail = document.getElementById("modelModalBody");
    detail.innerHTML = "";
    let saveModal = document.getElementById("saveModal");
    saveModal.style.display = "none";
    detail.appendChild(statusMessage(status, 'ADD'));
}

function rewriteTable(textReturned) {
    document.getElementById('myTable').innerHTML = "";
    let myTable = document.getElementById("myTable");
    myTable.setAttribute("class", "table table-bordered table-striped table-hover caption-top");
    let cap = document.createElement("caption");
    cap.innerHTML = "Model Details";
    myTable.appendChild(cap);
    let thead = document.createElement("thead");
    thead.setAttribute("class", "table-dark");
    let tr1 = document.createElement("tr");
    let th1 = document.createElement("th");
    th1.innerHTML = "id";
    th1.setAttribute("scope", "col");
    tr1.appendChild(th1);
    let th2 = document.createElement("th");
    th2.innerHTML = "Make Name";
    th2.setAttribute("scope", "col");
    tr1.appendChild(th2);
    let th3 = document.createElement("th");
    th3.innerHTML = "Model Name";
    th3.setAttribute("scope", "col");
    tr1.appendChild(th3);
    thead.appendChild(tr1);
    myTable.appendChild(thead);
    let modelList = JSON.parse(textReturned);
    let tbody = document.createElement("tbody");
    for (let i = 0; i < modelList.length; i++) {
        let singleModel = modelList[i];
        let trx = document.createElement("tr");
        let td1 = document.createElement("th");
        td1.setAttribute("scope", "row");
        let inCheck = document.createElement("input");
        inCheck.setAttribute("type", "checkbox");
        inCheck.setAttribute("name", "checkField");
        inCheck.setAttribute("onclick", "javascript:checkCall(this)");
        inCheck.setAttribute("value", singleModel.id);
        td1.appendChild(inCheck);
        trx.appendChild(td1);
        let td2 = document.createElement("td");
        td2.innerHTML = singleModel.makeName;
        trx.appendChild(td2);
        let td3 = document.createElement("td");
        td3.innerHTML = singleModel.modelName;
        trx.appendChild(td3);
        tbody.appendChild(trx);
    }
    myTable.appendChild(tbody);
}

function editModel() {
    let rowCheck = validateEditModalSelection();
    if (rowCheck) {
        editModelModal();
        setIdForChange();
        getModelForEdit();
    }
}

function editModelModal() {
    let modelEditModalBody = document.getElementById("editModelModal");
    modelEditModalBody.style.display = "block";
    let updateModal = document.getElementById("updateModal");
    updateModal.style.display = "block";
    let detail = document.getElementById("modelEditModalBody");
    detail.innerHTML = "";

    let formValidModel = document.createElement("form");
    formValidModel.setAttribute("class", "row g-3 needs-validation");
    formValidModel.novalidate = true;

    let divName = document.createElement("div");
    divName.setAttribute("class", "col-md-6");
    let selectMakeName = document.createElement("select");
    selectMakeName.setAttribute("class", "form-select");
    selectMakeName.setAttribute("id", "modalMakeNameForUpdate");
    divName.appendChild(selectMakeName);
    //ajax and add

    getAllMakeIdsAndNames();

    let divModelName = document.createElement("div");
    divModelName.setAttribute("class", "col-md-6");
    let txtModelName = document.createElement("input");
    txtModelName.setAttribute("type", "text");
    txtModelName.setAttribute("class", "form-control");
    txtModelName.setAttribute("placeholder", "Model Name");
    txtModelName.setAttribute("id", "modalModelNameForUpdate");
    txtModelName.required = true;
    divModelName.appendChild(txtModelName);
    let tt2 = document.createElement("div");
    tt2.setAttribute("class", "invalid-tooltip");
    tt2.innerHTML = "Please provide a valid modelName.";
    divModelName.appendChild(tt2);

    formValidModel.appendChild(divName);
    formValidModel.appendChild(divModelName);
    detail.appendChild(formValidModel);
}

function getModelForEdit() {
    let id = document.getElementById("id").value;
    let xhr = new XMLHttpRequest();
    xhr.open('GET', "/make/getForEdit" + "?id=" + id, true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                console.log("/make/getForEdit response is " + xhr.responseText);
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
    let modelMap = JSON.parse(textReturned);
    let makeDropDown = document.getElementById("modalMakeNameForUpdate");
    for (const [key, value] of Object.entries(modelMap)) {
        console.log("key and value are " + key + " " + value);
        for (let option of makeDropDown.options) {
            if (option.value === key) {
                option.selected = true;
                console.log("option set to " + option.value);
            }
        }
        document.getElementById("modalModelNameForUpdate").value = value;
    }
}

function showEditError() {
    let detail = document.getElementById("editModelModal");
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

function updateFromModal() {
    let modalMakeName = document.getElementById("modalMakeNameForUpdate").value;
    let modalModelName = document.getElementById("modalModelNameForUpdate").value;
    let forms = document.getElementsByClassName('needs-validation');
    let allFieldsAreValid = true;

    if (forms[0].checkValidity() === false) {
        allFieldsAreValid = false;
        if (modalModelName.length === 0) {
            document.getElementById("modalModelNameForUpdate").setAttribute("class", "form-control is-invalid");
        } else {
            document.getElementById("modalModelNameForUpdate").setAttribute("class", "form-control was-validated");
        }
    }
    if (allFieldsAreValid) {
        console.log("All fields are valid, calling Update");
        let productId = document.getElementById("id").value
        updateModel(productId, modalMakeName, modalModelName);
    }
}

function updateModel(productId, modalMakeName, modalModelName) {
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', "/make/updateModel", true);
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
    xhr.send("id=" + productId + "&modalMakeName=" + modalMakeName + "&modalModelName=" + modalModelName);
}

function showUpdateStatus(status) {
    let detail = document.getElementById("modelEditModalBody");
    detail.innerHTML = "";
    let updateModal = document.getElementById("updateModal");
    updateModal.style.display = "none";
    detail.appendChild(statusMessage(status, 'UPDATE'));
}