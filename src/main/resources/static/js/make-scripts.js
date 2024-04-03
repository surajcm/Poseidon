function hideAlerts() {
    document.getElementById('make').text = "Make <span class='sr-only'>Make</span>";
}

function listAllModel() {
    document.forms[0].action = "ModelList";
    document.forms[0].submit();
}

function clearOut() {
    document.getElementById("makeName").value = document.getElementById('makeName').options[0].value;
    document.getElementById('modelName').value = "";
    document.getElementById('includes').checked = false;
    document.getElementById('startswith').checked = false;
}

function searchMakes() {
    document.forms[0].action = "searchMake";
    document.forms[0].submit();
}

function rewriteTable(textReturned) {
    document.getElementById('myTable').innerHTML = "";
    const myTable = document.getElementById("myTable");
    myTable.setAttribute("class", "table table-bordered table-striped table-hover caption-top");
    let cap = document.createElement("caption");
    cap.innerHTML = "Make Details";
    myTable.appendChild(cap);
    const thead = document.createElement("thead");
    thead.setAttribute("class", "table-dark");
    const tr1 = document.createElement("tr");
    const th1 = document.createElement("th");
    th1.innerHTML = "#";
    th1.setAttribute("scope", "col");
    tr1.appendChild(th1);
    const th2 = document.createElement("th");
    th2.innerHTML = "Make Name";
    th2.setAttribute("scope", "col");
    tr1.appendChild(th2);
    const th3 = document.createElement("th");
    th3.innerHTML = "Description";
    th3.setAttribute("scope", "col");
    tr1.appendChild(th3);
    const th4 = document.createElement("th");
    th4.setAttribute("scope", "col");
    tr1.appendChild(th4);
    thead.appendChild(tr1);
    myTable.appendChild(thead);
    const makeList = JSON.parse(textReturned);
    const tbody = document.createElement("tbody");
    for (let i = 0; i < makeList.length; i++) {
        const singleMake = makeList[i];
        const trx = document.createElement("tr");
        const td1 = document.createElement("th");
        td1.setAttribute("scope", "row");
        const inCheck = document.createElement("input");
        inCheck.setAttribute("type", "checkbox");
        inCheck.setAttribute("name", "checkField");
        inCheck.setAttribute("onclick", "javascript:checkCall(this)");
        inCheck.setAttribute("value", singleMake.id);
        td1.appendChild(inCheck);
        trx.appendChild(td1);
        const td2 = document.createElement("td");
        td2.innerHTML = singleMake.makeName;
        trx.appendChild(td2);
        const td3 = document.createElement("td");
        td3.innerHTML = singleMake.description;
        trx.appendChild(td3);
        const td4 = document.createElement("td");
        td4.appendChild(editLink(singleMake.id));
        td4.appendChild(emptySpace());
        td4.appendChild(deleteLink(singleMake.id));
        trx.appendChild(td4);
        tbody.appendChild(trx);
    }
    myTable.appendChild(tbody);
}

function emptySpace() {
    const span = document.createElement("span");
    span.innerHTML = "&nbsp;";
    return span;
}

function editLink(makeId) {
    const editAnchor = document.createElement("a");
    editAnchor.setAttribute("class", "fa-regular fa-pen-to-square");
    editAnchor.setAttribute("href", "#");
    editAnchor.setAttribute("data-bs-toggle", "modal");
    editAnchor.setAttribute("data-bs-target", "#editMakeModal");
    editAnchor.setAttribute("onclick", "javascript:editMakeNew(" + makeId + ");");
    editAnchor.setAttribute("title", "Edit this make");
    return editAnchor;
}

function deleteLink(makeId) {
    const deleteAnchor = document.createElement("a");
    deleteAnchor.setAttribute("class", "fa-solid fa-trash");
    deleteAnchor.setAttribute("href", "/make/delete/" + makeId);
    deleteAnchor.setAttribute("data-bs-toggle", "modal");
    deleteAnchor.setAttribute("data-bs-target", "#confirmModal");
    deleteAnchor.setAttribute("makeId", makeId);
    deleteAnchor.setAttribute("title", "Delete this make");
    deleteAnchor.addEventListener('click', function(event) {
        event.preventDefault();
        document.getElementById('confirmText').innerText = 'Are you sure you want to delete make ID ' + makeId + '?';
        // Set the href of the confirmation button to the href of the delete link
        const confirmButton = document.querySelector('#confirmModal .btn-success');
        confirmButton.href = this.href;
    });

    return deleteAnchor;
}

function addMake() {
    let saveSmartMake = document.getElementById("saveSmartMake");
    saveSmartMake.style.display = "block";
    let detail = document.getElementById("makeModalBody");
    detail.innerHTML = "";
    let formValidMake = makeOnModal();
    detail.appendChild(formValidMake);
}

function makeOnModal() {
    let formValidMake = document.createElement("form");
    formValidMake.setAttribute("class", "row g-3 needs-validation");
    formValidMake.novalidate = true;

    let divName = document.createElement("div");
    divName.setAttribute("class", "col-md-4");
    let txtMakeName = document.createElement("input");
    txtMakeName.setAttribute("type", "text");
    txtMakeName.setAttribute("class", "form-control");
    txtMakeName.setAttribute("placeholder", "Make Name");
    txtMakeName.setAttribute("id", "modalMakeName");
    txtMakeName.required = true;
    divName.appendChild(txtMakeName);

    let divDescription = document.createElement("div");
    divDescription.setAttribute("class", "col-md-4");
    let txtDescription = document.createElement("input");
    txtDescription.setAttribute("type", "text");
    txtDescription.setAttribute("class", "form-control");
    txtDescription.setAttribute("placeholder", "Description");
    txtDescription.setAttribute("id", "modalMakeDescription");
    txtDescription.required = true;
    divDescription.appendChild(txtDescription);

    let tt2 = document.createElement("div");
    tt2.setAttribute("class", "invalid-tooltip");
    tt2.innerHTML = "Please provide a valid makeName.";
    divDescription.appendChild(tt2);

    formValidMake.appendChild(divName);
    formValidMake.appendChild(divDescription);
    return formValidMake;
}

function saveMakeFromModal() {
    let modalMakeName = document.getElementById("modalMakeName").value;
    let modalDescription = document.getElementById("modalMakeDescription").value;
    console.log("At saveFromModal ,modalMakeName is :"+modalMakeName +", and modalDescription ="+modalDescription)
    let forms = document.getElementsByClassName('needs-validation');
    let allFieldsAreValid = true;

    if (forms[0].checkValidity() === false) {
        allFieldsAreValid = false;
        if (modalMakeName.length === 0) {
            document.getElementById("modalMakeName").setAttribute("class", "form-control is-invalid");
        } else {
            document.getElementById("modalMakeName").setAttribute("class", "form-control was-validated");
        }
    }
    if (allFieldsAreValid) {
        saveMake(modalMakeName, modalDescription);
    }
}

function saveMake(modalMakeName, modalDescription) {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "/make/saveMake", true);
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
    xhr.send("selectMakeName=" + modalMakeName + "&selectMakeDesc=" + modalDescription + "&${_csrf.parameterName}=${_csrf.token}");
}

function showStatus(status) {
    let detail = document.getElementById("makeModalBody");
    detail.innerHTML = "";
    let saveSmartMake = document.getElementById("saveSmartMake");
    saveSmartMake.style.display = "none";
    detail.appendChild(statusMessage(status, 'ADD'));
}

function editMakeNew(id) {
    document.getElementById("id").value = id;
    editMakeModal();
    getMakeForEdit();
}

function editMakeModal() {
    let makeEditModalBody = document.getElementById("editMakeModal");
    makeEditModalBody.style.display = "block";
    let updateMake = document.getElementById("updateMake");
    updateMake.style.display = "block";
    let detail = document.getElementById("makeEditModalBody");
    detail.innerHTML = "";

    let formValidMake = makeOnModal();
    detail.appendChild(formValidMake);
}

function getMakeForEdit() {
    let id = document.getElementById("id").value;
    let xhr = new XMLHttpRequest();
    xhr.open('GET', "/make/makeForEdit" + "?id=" + id, true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                console.log("/make/makeForEdit response is " + xhr.responseText);
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
    let modalMakeName = document.getElementById("modalMakeName");
    let modalMakeDescription = document.getElementById("modalMakeDescription");
    for (const [key, value] of Object.entries(modelMap)) {
        console.log("key and value are " + key + " " + value);
        modalMakeName.value = key;
        modalMakeDescription.value = value;
    }
}

function showEditError() {
    let detail = document.getElementById("makeEditModalBody");
    detail.innerHTML = "";
    let updateMake = document.getElementById("updateMake");
    updateMake.style.display = "none";
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
    let modalMakeName = document.getElementById("modalMakeName").value;
    let modalMakeDescription = document.getElementById("modalMakeDescription").value;
    let forms = document.getElementsByClassName('needs-validation');
    let allFieldsAreValid = true;

    if (forms[0].checkValidity() === false) {
        allFieldsAreValid = false;
        if (modalMakeName.length === 0) {
            document.getElementById("modalMakeName").setAttribute("class", "form-control is-invalid");
        } else {
            document.getElementById("modalMakeName").setAttribute("class", "form-control was-validated");
        }
    }
    if (allFieldsAreValid) {
        console.log("All fields are valid, calling callAjaxUpdate");
        let productId = document.getElementById("id").value
        makeUpdate(productId, modalMakeName, modalMakeDescription);
    }
}


function makeUpdate(productId, makeName, description) {
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', "/make/updateMake", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                //console.log("makeUpdate success :" + xhr.responseText);
                rewriteTable(xhr.responseText);
                showUpdateStatus(true);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
            showUpdateStatus(false);
        }
    };
    xhr.send("id=" + productId + "&makeName=" + makeName + "&description=" + description);
}

function showUpdateStatus(status) {
    let detail = document.getElementById("makeEditModalBody");
    detail.innerHTML = "";
    let updateMake = document.getElementById("updateMake");
    updateMake.style.display = "none";
    detail.appendChild(statusMessage(status, 'UPDATE'));
}