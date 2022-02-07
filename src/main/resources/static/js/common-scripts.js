function checkCall(e) {
    const min = e.value;
    const checks = document.getElementsByName('checkField');
    for (let i = 0; i < checks.length; i++) {
        if (checks[i].value !== min) {
            checks[i].checked = false;
        }
    }
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

function validateEditModalSelection(elem) {
    let detail = document.getElementById(elem);
    detail.innerHTML = "";
    let check = 'false';
    let count = selectedRowCount();
    if (count > 0) {
        check = 'true';
    }
    if (check === 'true') {
        if (count === 1) {
            setIdFromSelectionInTable();
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

function setIdFromSelectionInTable() {
    let selectedRow;
    let checks = document.getElementsByName('checkField');
    if (checks.checked) {
        selectedRow = document.getElementById("myTable").rows[0];
    } else {
        for (let i = 0; i < checks.length; i++) {
            if (checks[i].checked) {
                selectedRow = document.getElementById("myTable").rows[i + 1];
            }
        }
    }
    document.getElementById("id").value = selectedRow.cells[0].childNodes[0].value;
}

function tableHead() {
    const thead = document.createElement("thead");
    thead.setAttribute("class", "table-dark");
    thead.appendChild(tableHeaderRow());
    return thead;
}

function tableHeader(text) {
    const th1 = document.createElement("th");
    th1.innerHTML = text;
    th1.setAttribute("scope", "col");
    return th1;
}

function checkBoxInaRow(id) {
    const inCheck = document.createElement("input");
    inCheck.setAttribute("type", "checkbox");
    inCheck.setAttribute("name", "checkField");
    inCheck.setAttribute("onclick", "javascript:checkCall(this)");
    inCheck.setAttribute("value", id);
    return inCheck;
}

function tdElement(value) {
    const td2 = document.createElement("td");
    td2.innerHTML = value;
    return td2;
}

function sideHeader(id) {
    const td1 = document.createElement("th");
    td1.setAttribute("scope", "row");
    const inCheck = checkBoxInaRow(id);
    td1.appendChild(inCheck);
    return td1;
}

function tableBodyCreation(textReturned) {
    const elementList = JSON.parse(textReturned);
    const tbody = document.createElement("tbody");
    for (let i = 0; i < elementList.length; i++) {
        const singleElement = elementList[i];
        const trx = singleRowInTheTable(singleElement);
        tbody.appendChild(trx);
    }
    return tbody;
}

function setCaption(caption) {
    let cap = document.createElement("caption");
    cap.innerHTML = caption;
    return cap;
}

function statusMessage(status, update) {
    let divStatus = document.createElement("div");
    divStatus.setAttribute("class", "pop-status");

    let imgSuccess = document.createElement("img");
    imgSuccess.setAttribute("src", statusMessageImg(status));
    divStatus.appendChild(imgSuccess);

    let statusMessage = document.createElement("h3");
    if (update === 'ADD') {
        statusMessage.innerHTML = statusMessageForAdd(status);
    } else if (update === 'UPDATE') {
        statusMessage.innerHTML = statusMessageUpdate(status);
    } else {
        statusMessage.innerHTML = statusMessageForInvoice(status);
    }
    divStatus.appendChild(statusMessage);
    return divStatus;
}

function statusMessageUpdate(status) {
    let statusMessage;
    if (status) {
        statusMessage = "Successfully Updated !!";
    } else {
        statusMessage = "Failed to save !!";
    }
    return statusMessage;
}

function statusMessageForAdd(status) {
    let statusMessage;
    if (status) {
        statusMessage = "Successfully Added !!";
    } else {
        statusMessage = "Failed to save !!";
    }
    return statusMessage;
}

function statusMessageForInvoice(status) {
    let statusMessage;
    if (status) {
        statusMessage = "Successfully Invoiced !!";
    } else {
        statusMessage = "Failed to invoice !!";
    }
    return statusMessage;
}

function statusMessageImg(status) {
    let statusImage;
    if (status) {
        statusImage = "/img/tick.png";
    } else {
        statusImage = "/img/cross.svg";
    }
    return statusImage;
}

function generateLabel(text) {
    const labelElement = document.createElement("label");
    labelElement.setAttribute("class", "form-label");
    labelElement.textContent = text;
    return labelElement;
}

function generateTextAsLabelField(text) {
    const textElement = document.createElement("label");
    textElement.setAttribute("class", "form-label");
    textElement.textContent = text;
    return textElement;
}

function isNumber(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

function aTextBox(id, placeHolder, isRequired) {
    let txtBox = document.createElement("input");
    txtBox.setAttribute("type", "text");
    txtBox.setAttribute("class", "form-control");
    txtBox.setAttribute("placeholder", placeHolder);
    txtBox.setAttribute("id", id);
    if (isRequired) {
        txtBox.required = true;
    }
    return txtBox;
}

function aTextArea(id, placeHolder, isRequired) {
    let txtArea = document.createElement("textarea");
    txtArea.setAttribute("rows", "3");
    txtArea.setAttribute("class", "form-control");
    txtArea.setAttribute("placeholder", placeHolder);
    txtArea.setAttribute("id", id);
    if (isRequired) {
        txtArea.required = true;
    }
    return txtArea;
}

function setIdForChange() {
    let checks = document.getElementsByName('checkField');
    let rowInTable;
    if (checks.checked) {
        rowInTable = document.getElementById("myTable").rows[0];
    } else {
        for (let i = 0; i < checks.length; i++) {
            if (checks[i].checked) {
                rowInTable = document.getElementById("myTable").rows[i + 1];
            }
        }
    }
    document.getElementById("id").value = rowInTable.cells[0].childNodes[0].value;
}
