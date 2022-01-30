function save() {
    if (document.getElementById('dateReported').value.length === 0) {
        alert("Please enter a valid Transaction Date");
    } else if (document.getElementById('productCategory').value.length === 0) {
        alert("Please enter a valid Product Category");
    } else if (document.getElementById('serialNo').value.length === 0) {
        alert("Please enter a valid Serial No");
    } else if ((document.getElementById('customerId').value.length === 0)
        && (document.getElementById('customerName').value.length === 0
            || document.getElementById('mobile').value.length === 0)) {
        alert("Please enter a valid Customer Details");
    } else if (document.getElementById('makeId').value.length === 0) {
        alert("Please enter a valid Make detail");
    } else if (document.getElementById('modelId').value.length === 0) {
        alert("Please enter a valid Model detail");
    } else {
        document.forms[0].action = "saveTxn";
        document.forms[0].submit();
    }
}

//code to edit a user
function clearOut() {
    document.getElementById("dateReported").value = "";
    document.getElementById("productCategory").value = "";
    document.getElementById("serialNo").value = "";
    document.getElementById("customerId").value = "";
    document.getElementById("customerName").value = "";
    document.getElementById("address1").value = "";
    document.getElementById("address2").value = "";
    document.getElementById("phoneNo").value = "";
    document.getElementById("mobile").value = "";
    document.getElementById("email").value = "";
    document.getElementById("contactPerson1").value = "";
    document.getElementById("contactMobile1").value = "";
    document.getElementById("contactPerson2").value = "";
    document.getElementById("contactMobile2").value = "";
    document.getElementById("notes").value = "";
    document.getElementById("makeId").value = document.getElementById('makeId').options[0].value;
    document.getElementById("modelId").value = document.getElementById('modelId').options[0].value;
    document.getElementById("accessories").value = "";
    document.getElementById("complaintReported").value = "";
    document.getElementById("complaintDiagnosed").value = "";
    document.getElementById("enggRemark").value = "";
    document.getElementById("repairAction").value = "";
    document.getElementById("notes").value = "";
}

function changeTheModel() {
    const selectMakeId = document.getElementById('makeId').value;
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "/txs/updateModel", true);
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

function searchForCustomer() {
    const searchCustomerId = document.getElementById('searchCustomerId').value;
    const searchCustomerName = document.getElementById('searchCustomerName').value;
    const searchMobile = document.getElementById('searchMobile').value;
    console.log("inside searchForCustomer with id "+searchCustomerId +
        " and name "+searchCustomerName +
        " and mobile "+searchMobile);
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "/customer/searchFromTransaction", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                console.log(xhr.responseText);
                rewriteTable(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
        }
    };
    xhr.send("searchCustomerId=" + searchCustomerId +
        "&searchCustomerName=" + searchCustomerName +
        "&searchMobile=" + searchMobile +
        "&${_csrf.parameterName}=${_csrf.token}");

}

function rewriteTable(textReturned) {
    document.getElementById('detail').innerHTML = "";
    const myTable = document.getElementById("detail");
    myTable.setAttribute("class", "table table-bordered table-striped table-hover caption-top");
    myTable.appendChild(setCaption("Customer Details"));
    const thead = tableHead();
    myTable.appendChild(thead);
    myTable.appendChild(tableBodyCreation(textReturned));
    document.getElementById('searchCustomer').style.display = "none";
    const footer = document.getElementById('edit-footer');
    const pickMe = createChooseButton();
    footer.appendChild(pickMe);
}

function createChooseButton() {
    const button = document.createElement("button");
    button.id = "pickMe";
    button.className = "btn btn-primary";
    button.textContent = "Select";
    button.type = "button";
    button.onclick = function () {
        chooseCustomer()
    };
    return button;
}

function chooseCustomer() {
    const rowCheck = validateCustomerSelection();
    if (rowCheck) {
        let rowId = findSelectedRowId();
        console.log("Customer Id is : "+rowId);
        getSpecificCustomer(rowId);
        document.getElementById('modal-close').click();
    }
}

function getSpecificCustomer(customerId) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', "/customer/getForEdit" + "?id=" + customerId, true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                console.log(xhr.responseText);
                putCustomerInAddTxn(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
        }
    };
    xhr.send();
}

function putCustomerInAddTxn(textReturned) {
    let customerInfo = JSON.parse(textReturned);
    document.getElementById("customerId").value = customerInfo.customerId;
    document.getElementById("customerName").value = customerInfo.customerName;
    document.getElementById("address1").value = customerInfo.address;
    document.getElementById("phoneNo").value = customerInfo.phoneNo;
    document.getElementById("mobile").value = customerInfo.mobile;
    document.getElementById("email").value = customerInfo.email;
}

function findSelectedRowId() {
    let checks = document.getElementsByName('checkField');
    let rowId;
    if (checks.length === 1) {
        rowId = checks[0].value;
    } else {
        for (let i = 0; i < checks.length; i++) {
            if (checks[i].checked) {
                rowId = checks[i].value;
            }
        }
    }
    return rowId;
}

function validateCustomerSelection() {
    let check = 'false';
    const count = getRowCount();
    if (count > 0) {
        check = 'true';
    }
    if (check === 'true') {
        if (count === 1) {
            return true;
        } else {
            alert("Only one row can be selected at a time, please select one row");
            return false;
        }
    } else {
        alert("No rows selected, please select one row");
        return false;
    }
}

function getRowCount() {
    let checks = document.getElementsByName('checkField');
    let count = 0;
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            count = 1;
        } else {
            if (checks.length === 1) {
                count = 1;
            } else {
                for (let i = 0; i < checks.length; i++) {
                    if (checks[i].checked) {
                        count = count + 1;
                    }
                }
            }
        }
    }
    return count;
}

function tableHeaderRow() {
    const tr1 = document.createElement("tr");
    const th1 = tableHeader("#");
    tr1.appendChild(th1);
    tr1.appendChild(tableHeader("id"));
    tr1.appendChild(tableHeader("Name"));
    tr1.appendChild(tableHeader("Address"));
    tr1.appendChild(tableHeader("Mobile"));
    tr1.appendChild(tableHeader("Email"));
    return tr1;
}

function singleRowInTheTable(singleCustomer) {
    const trx = document.createElement("tr");
    trx.appendChild(sideHeader(singleCustomer.customerId));
    trx.appendChild(tdElement(singleCustomer.customerId));
    trx.appendChild(tdElement(singleCustomer.customerName));
    trx.appendChild(tdElement(singleCustomer.address));
    trx.appendChild(tdElement(singleCustomer.mobile));
    trx.appendChild(tdElement(singleCustomer.email));
    return trx;
}