
function tableHead() {
    const thead = document.createElement("thead");
    thead.setAttribute("class", "table-dark");
    const tr1 = tableHeaderRow();
    thead.appendChild(tr1);
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

function statusAsDiv(status) {
    let divStatus = document.createElement("div");
    divStatus.setAttribute("class", "pop-status");

    let imgSuccess = document.createElement("img");
    imgSuccess.setAttribute("src", statusMessageImg(status));
    divStatus.appendChild(imgSuccess);

    let statusMessage = document.createElement("h3");
    statusMessage.innerHTML = statusMessageText(status);
    divStatus.appendChild(statusMessage);
    return divStatus;
}

function statusMessageText(status) {
    let statusMessage;
    if (status) {
        statusMessage = "Successfully added a new Customer !!";
    } else {
        statusMessage = "Failed to save !!";
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
    return text;
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
