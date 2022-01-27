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
                //rebuildDropDown(xhr.responseText);
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
