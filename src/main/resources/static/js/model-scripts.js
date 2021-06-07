
function listAllMake() {
    document.forms[0].action = "MakeList.htm";
    document.forms[0].submit();
}

function search() {
    document.forms[0].action = "searchModel.htm";
    document.forms[0].submit();
}

function clearOut() {
    document.getElementById("makeName").value = document.getElementById('makeName').options[0].value;
    document.getElementById('modelName').value ="";
    document.getElementById('includes').checked = false;
    document.getElementById('startswith').checked = false;
}

function AddModel() {
    let saveModal = document.getElementById("saveModal");
    saveModal.style.display = "block";
    let detail = document.getElementById("modelModalBody");
    detail.innerHTML = "";

    let formValidModel = document.createElement("form");
    formValidModel.setAttribute("class","needs-validation");
    formValidModel.novalidate = true;

    let divModelAdd = document.createElement("div");
    divModelAdd.setAttribute("class","form-row align-items-left");
    let divName = document.createElement("div");
    divName.setAttribute("class","form-group col-md-4");
    let selectMakeName = document.createElement("select");
    selectMakeName.setAttribute("class","form-control");
    selectMakeName.setAttribute("id","modalMakeName");
    divName.appendChild(selectMakeName);
    //ajax and add

    getAllMakeIdsAndNames();

    let divModelName = document.createElement("div");
    divModelName.setAttribute("class","form-group col-md-4");
    let txtModelName = document.createElement("input");
    txtModelName.setAttribute("type","text");
    txtModelName.setAttribute("class","form-control");
    txtModelName.setAttribute("placeholder","Model Name");
    txtModelName.setAttribute("id","modalModelName");
    txtModelName.required = true;
    divModelName.appendChild(txtModelName);
    let tt2 = document.createElement("div");
    tt2.setAttribute("class","invalid-tooltip");
    tt2.innerHTML = "Please provide a valid modelName.";
    divModelName.appendChild(tt2);

    divModelAdd.appendChild(divName);
    divModelAdd.appendChild(divModelName);
    formValidModel.appendChild(divModelAdd);
    detail.appendChild(formValidModel);
}

function getAllMakeIdsAndNames() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', "/make/getAllMakeIdsAndNames.htm",true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function() {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                console.log("Response is "+ xhr.responseText)
                addMakesToSelect(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
        }
    };
    xhr.send();
}

function addMakesToSelect(response) {
    console.log("Response is "+ response);
    let modelMap = JSON.parse(response);
    let modalMakeName = document.getElementById('modalMakeName');
    for (const [key, value] of Object.entries(modelMap)) {
        let mainOption = document.createElement("option");
        mainOption.text = value;
        mainOption.value = key;
        modalMakeName.appendChild(mainOption);
    }
}

function saveFromModal() {
    let modalMakeName = document.getElementById("modalMakeName").value;
    let modalModelName = document.getElementById("modalModelName").value;
    let forms = document.getElementsByClassName('needs-validation');
    let allFieldsAreValid = true;

    if (forms[0].checkValidity() === false) {
        allFieldsAreValid = false;
        if (modalModelName.length === 0) {
            document.getElementById("modalModelName").setAttribute("class","form-control is-invalid");
        } else {
            document.getElementById("modalModelName").setAttribute("class","form-control was-validated");
        }
    }
    if(allFieldsAreValid) {
        ajaxSaveFromModal(modalMakeName,modalModelName);
    }
}


function ajaxSaveFromModal(modalMakeName, modalModelName) {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "/make/saveModelAjax.htm",true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
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
    xhr.send("selectMakeId=" + modalMakeName + "&selectModelName=" + modalModelName);
}

function showStatus(status) {
    let detail = document.getElementById("modelModalBody");
    detail.innerHTML = "";
    let saveModal = document.getElementById("saveModal");
    saveModal.style.display = "none";
    let divStatus = document.createElement("div");
    divStatus.setAttribute("class","pop-status");
    let imgSuccess = document.createElement("img");

    divStatus.appendChild(imgSuccess);
    let statusMessage = document.createElement("h3");
    if(status) {
        imgSuccess.setAttribute("src","/img/tick.png");
        statusMessage.innerHTML = "Successfully added a new model !!";
    } else {
        imgSuccess.setAttribute("src","/img/cross.svg");
        statusMessage.innerHTML = "Failed to save !!";
    }
    divStatus.appendChild(statusMessage);
    detail.appendChild(divStatus);
}


//validation before edit
function editModel() {
    let check = 'false';
    let count = 0;
    // get all check boxes
    let checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            editRow();
        } else {
            for (let i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    check = 'true';
                    count = count + 1;
                }
            }
            //check for validity
            if (check === 'true') {
                if (count === 1) {
                    editRow();
                } else {
                    alert(" Only one row can be edited at a time, please select one row ");
                }
            } else {
                alert(" No rows selected, please select one row ");
            }
        }
    }
}

//real edit
function editRow() {
    let userRow;
    let checks = document.getElementsByName('checkField');
    if (checks.checked) {
        userRow = document.getElementById("myTable").rows[0];
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        document.forms[0].action = "editModel.htm";
        document.forms[0].submit();
    } else {
        for (let i = 0; i < checks.length; i++) {
            if (checks[i].checked) {
                userRow = document.getElementById("myTable").rows[i + 1];
            }
        }
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        document.forms[0].action = "editModel.htm";
        document.forms[0].submit();
    }
}

// delete
function deleteModel() {
    let check = 'false';
    let count = 0;
    // get all check boxes
    let checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            deleteRow();
        } else {
            for (let i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    check = 'true';
                    count = count + 1;
                }
            }
            //check for validity
            if (check === 'true') {
                if (count === 1) {
                    deleteRow();
                } else {
                    alert(" Only one row can be deleted at a time, please select one row ");
                }
            } else {
                alert(" No rows selected, please select one row ");
            }
        }
    }
}

//code to delete
function deleteRow() {
    let answer = confirm(" Are you sure you wanted to delete the user ");
    if (answer) {
        //if yes then delete
        let userRow;
        let checks = document.getElementsByName('checkField');
        if (checks.checked) {
            userRow = document.getElementById("myTable").rows[0];
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            document.forms[0].action = "deleteModel.htm";
            document.forms[0].submit();
        } else {
            for (let i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    userRow = document.getElementById("myTable").rows[i + 1];
                }
            }
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            document.forms[0].action = "deleteModel.htm";
            document.forms[0].submit();
        }
    }

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

function hideAlerts(){
    document.getElementById('make').text = "Make <span class='sr-only'>Make</span>";
}

function simpleAdd() {
    let myTable = document.getElementById("myTable");
    let d = document.createElement("tr");
    let dCheck = document.createElement("td");
    d.appendChild(dCheck);

    let inCheck = document.createElement("input");
    inCheck.setAttribute("type","checkbox");
    inCheck.setAttribute("name","checkField");
    inCheck.setAttribute("onclick","javascript:checkCall(this)");
    dCheck.appendChild(inCheck);

    let dMake = document.createElement("td");
    d.appendChild(dMake);

    let inMake = document.createElement("select");
    inMake.setAttribute("class", "form-control");
    inMake.setAttribute("id", "newMakeName");
    let opLength = document.getElementById("makeName").options.length;
    for(let i = 1; i <= opLength; i++) {
        let newOption = document.createElement("option");
        newOption.text = document.getElementById("makeName").options[i-1].text;
        newOption.value = document.getElementById("makeName").options[i-1].value;
        inMake.appendChild(newOption);
    }
    dMake.appendChild(inMake);

    let dModel = document.createElement("td");
    d.appendChild(dModel);

    let inModel = document.createElement("input");
    inModel.setAttribute("type","text");
    inModel.setAttribute("class", "form-control");
    inModel.setAttribute("id", "newModelName");
    dModel.appendChild(inModel);

    myTable.appendChild(d);
}

function saveSimpleModel() {
    let e = document.forms[0].newMakeName;
    let selectMakeId = e.options[e.selectedIndex].value;
    let selectModelName = document.forms[0].newModelName.value;
    $.ajax({
        type: "POST",
        url: "${contextPath}/make/saveModelAjax.htm",
        data: "selectMakeId=" + selectMakeId + "&selectModelName=" + selectModelName + "&${_csrf.parameterName}=${_csrf.token}",
        success: function(response) {
            //alert(response);
            if (response !== "") {
                rewriteTable(response);
            }
        },error: function(e){
            alert('Error: ' + e);
        }
    });
}

function rewriteTable(textReturned) {
    document.getElementById('myTable').innerHTML = "";
    let myTable = document.getElementById("myTable");
    let thead = document.createElement("thead");
    let tr1 = document.createElement("tr");
    let th1 = document.createElement("th");
    th1.innerHTML = "#";
    th1.setAttribute("class","text-center");
    tr1.appendChild(th1);
    let th2 = document.createElement("th");
    th2.innerHTML = "Make Name";
    th2.setAttribute("class","text-center");
    tr1.appendChild(th2);
    let th3 = document.createElement("th");
    th3.innerHTML = "Model Name";
    th3.setAttribute("class","text-center");
    tr1.appendChild(th3);
    thead.appendChild(tr1);
    myTable.appendChild(thead);
    let modelList = JSON.parse(textReturned);
    let tbody = document.createElement("tbody");
    for (let i = 0; i < modelList.length; i++) {
        let singleModel = modelList[i];
        let trx = document.createElement("tr");
        let td1 = document.createElement("td");
        let inCheck = document.createElement("input");
        inCheck.setAttribute("type","checkbox");
        inCheck.setAttribute("name","checkField");
        inCheck.setAttribute("onclick","javascript:checkCall(this)");
        inCheck.setAttribute("value",singleModel.id);
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
    //todo: optional message saving update is done !!
}

function editModel2() {
    let rowCheck = validateEditModalSelection();
    if (rowCheck) {
        editModelModal();
        setIdForChange();
        getModelForEdit();
    }
}

function setIdForChange() {
    let userRow;
    let checks = document.getElementsByName('checkField');
    if (checks.checked) {
        userRow = document.getElementById("myTable").rows[0];
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
    } else {
        for (let i = 0; i < checks.length ; i++){
            if(checks[i].checked) {
                userRow = document.getElementById("myTable").rows[i+1];
            }
        }
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
    }
}

function editModelModal() {
    let modelEditModalBody = document.getElementById("editModelModal");
    modelEditModalBody.style.display = "block";
    let detail = document.getElementById("modelEditModalBody");
    detail.innerHTML = "";

    let formValidModel = document.createElement("form");
    formValidModel.setAttribute("class","needs-validation2");
    formValidModel.novalidate = true;

    let divModelAdd = document.createElement("div");
    divModelAdd.setAttribute("class","form-row align-items-left");
    let divName = document.createElement("div");
    divName.setAttribute("class","form-group col-md-4");
    let selectMakeName = document.createElement("select");
    selectMakeName.setAttribute("class","form-control");
    selectMakeName.setAttribute("id","modalMakeName");
    divName.appendChild(selectMakeName);
    //ajax and add

    getAllMakeIdsAndNames();

    let divModelName = document.createElement("div");
    divModelName.setAttribute("class","form-group col-md-4");
    let txtModelName = document.createElement("input");
    txtModelName.setAttribute("type","text");
    txtModelName.setAttribute("class","form-control");
    txtModelName.setAttribute("placeholder","Model Name");
    txtModelName.setAttribute("id","modalModelName");
    txtModelName.required = true;
    divModelName.appendChild(txtModelName);
    let tt2 = document.createElement("div");
    tt2.setAttribute("class","invalid-tooltip");
    tt2.innerHTML = "Please provide a valid modelName.";
    divModelName.appendChild(tt2);

    divModelAdd.appendChild(divName);
    divModelAdd.appendChild(divModelName);
    formValidModel.appendChild(divModelAdd);
    detail.appendChild(formValidModel);
}

function validateEditModalSelection() {
    let check ='false';
    let count = 0;
    // get all check boxes
    let checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            return true;
        } else {
            for(let i = 0 ; i < checks.length ; i++ ) {
                if (checks[i].checked) {
                    check = 'true';
                    count = count + 1;
                }
            }
            //check for validity
            let detail = document.getElementById("modelEditModalBody");
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
    }
}

function getModelForEdit() {
    let id = document.getElementById("id").value;
    let xhr = new XMLHttpRequest();
    xhr.open('GET', "/make/getForEdit.htm" + "?id=" + id,true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function() {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                console.log("/make/getForEdit.htm response is "+xhr.responseText);
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
    let makeDropDown = document.getElementById("modalMakeName");
    for (const [key, value] of Object.entries(modelMap)) {
        console.log("key and value are "+key + " "+ value);
        for (let option of makeDropDown.options) {
            if (option.value === key) {
                option.selected = true;
            }
        }
        //document.getElementById("modalMakeName").value = key;
        document.getElementById("modalModelName").value = value;
    }
}

function showEditError() {
    let detail = document.getElementById("editModelModal");
    detail.innerHTML = "";
    let updateModal = document.getElementById("updateModal");
    updateModal.style.display = "none";
    let divStatus = document.createElement("div");
    divStatus.setAttribute("class","pop-status");
    let imgSuccess = document.createElement("img");

    divStatus.appendChild(imgSuccess);
    let statusMessage = document.createElement("h3");
    imgSuccess.setAttribute("src","/img/cross.svg");
    statusMessage.innerHTML = "Failed to populate data !!";
    divStatus.appendChild(statusMessage);
    detail.appendChild(divStatus);
}

function updateFromModal() {
    let modalMakeName = document.getElementById("modalMakeName").value;
    let modalModelName = document.getElementById("modalModelName").value;
    let forms = document.getElementsByClassName('needs-validation2');
    let allFieldsAreValid = true;

    if (forms[0].checkValidity() === false) {
        allFieldsAreValid = false;
        if (modalModelName.length === 0) {
            document.getElementById("modalModelName").setAttribute("class","form-control is-invalid");
        } else {
            document.getElementById("modalModelName").setAttribute("class","form-control was-validated");
        }
    }
    if(allFieldsAreValid) {
        console.log("All fields are valid, calling callAjaxUpdate")
        callAjaxUpdate(modalMakeName,modalModelName);
    }
}

function callAjaxUpdate(modalMakeName,modalModelName) {
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', "/make/updateModelAjax.htm",true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function() {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                console.log("callAjaxUpdate success :"+xhr.responseText);
                //rewriteTable("callAjaxUpdate success :"+xhr.responseText);
                //showUpdateStatus(true);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
            //showUpdateStatus(false);
        }
    };
    xhr.send("modalMakeName="+modalMakeName+ "&modalModelName=" + modalModelName);
}