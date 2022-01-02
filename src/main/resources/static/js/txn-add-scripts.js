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
        document.forms[0].action = "SaveTxn.htm";
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
    document.getElementById("complaintDiagonsed").value = "";
    document.getElementById("enggRemark").value = "";
    document.getElementById("repairAction").value = "";
    document.getElementById("notes").value = "";
}

function changeTheNewModel() {
    const selectMakeId = document.getElementById('makeId').value;
    const xhr = new XMLHttpRequest();
    xhr.open('POST', "/txs/UpdateModelAjax.htm" + "?selectMakeId=" + selectMakeId, true);
    const token = document.querySelector("meta[name='_csrf']").content;
    const header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                stateChangeOnTxn(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
        }
    };
    xhr.send();
}

function stateChangeOnTxn(textReturned) {
    console.log("Received :" + textReturned);
    if (textReturned !== "") {
        const mmList = JSON.parse(textReturned);
        const modelId = document.getElementById('modelId');
        modelId.options.length = mmList - 1;
        modelId.options[0] = new Option("<-- Select -->", "");
        for (let i = 0; i < mmList.length; i++) {
            console.log("i is " + i);
            const singleMM = mmList[i];
            console.log("singleMM is " + singleMM.modelName);
            modelId.options[i + 1] = new Option(singleMM.modelName, singleMM.id);
        }
    } else {
        modelId.options.length = 0;
        modelId.options[0] = new Option("<-- Select -->", "");
    }
}