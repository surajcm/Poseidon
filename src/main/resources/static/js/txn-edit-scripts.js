function update() {
    if (document.getElementById('productCategory').value.length === 0) {
        alert("Please enter a valid Product Category");
    } else if (document.getElementById('serialNo').value.length === 0) {
        alert("Please enter a valid Serial No");
    } else if (document.getElementById('customerId').value.length === 0) {
        if (document.getElementById('customerName').value.length === 0
            || document.getElementById('mobile').value.length === 0) {
            alert("Please enter a valid Customer Details");
        }
    } else if (document.getElementById('makeId').value.length === 0) {
        alert("Please enter a valid Make detail");
    } else if (document.getElementById('modelId').value.length === 0) {
        alert("Please enter a valid Model detail");
    } else {
        let model = document.getElementById('modelId');
        document.getElementById('modelId').value = model.value;
        document.forms[0].action = "updateTxn.htm";
        document.forms[0].submit();
    }
}

function cancel() {
    document.forms[0].action = "List.htm";
    document.forms[0].submit();
}

function changeTheModel() {
    const selectMakeId = document.getElementById('makeId').value;
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "/txs/UpdateModelAjax.htm", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                rebuildDropDown(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
        }
    };
    xhr.send("selectMakeId=" + selectMakeId + "&${_csrf.parameterName}=${_csrf.token}");
}

function rebuildDropDown(textReturned) {
    const makeModelList = JSON.parse(textReturned);
    let model = document.getElementById('modelId');
    model.options.length = makeModelList.length;
    for (let i = 0; i < (makeModelList.length); i++) {
        const singleModel = makeModelList[i];
        model.options[i] = new Option(singleModel.modelName, singleModel.id);
    }
    document.getElementById('modelId').value = model.options[0].value;
}

function editSmartCustomer() {
    editCustomerModal();
    getCustomerForEdit()
}

function getCustomerForEdit() {
    let id = document.getElementById("customerVO.customerId").value;
    let xhr = new XMLHttpRequest();
    xhr.open('GET', "/customer/getForEdit" + "?id=" + id, true);
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

function callAjaxUpdate(modalCustomerName, modalAddress, modalPhone,
                        modalMobile, modalEmail, modalContact, modalContactMobile, modalNotes) {
    const id = document.getElementById("customerVO.customerId").value;
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', "/customer/updateCustomer", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    xhr.setRequestHeader(header, token);
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

function rewriteTable(textReturned) {
    const id = document.getElementById("customerVO.customerId").value;
    const elementList = JSON.parse(textReturned);
    for (let i = 0; i < elementList.length; i++) {
        const singleElement = elementList[i];
        if (parseInt(id) === parseInt(singleElement.customerId)) {
            document.getElementById("customerName").value = singleElement.customerName;
            document.getElementById("email").value = singleElement.email;
        }
    }
}