function listAllModel() {
    document.forms[0].action = "ModelList.htm";
    document.forms[0].submit();
}

function clearOut(){
    document.getElementById("makeName").value = document.getElementById('makeName').options[0].value;
    document.getElementById('modelName').value ="";
    document.getElementById('includes').checked = false;
    document.getElementById('startswith').checked = false;
}

function search() {
    document.forms[0].action = "searchModel.htm";
    document.forms[0].submit();
}

function printMe() {
    document.forms[0].action = "printMake.htm";
    document.forms[0].submit();
}

//validation before edit
function editMake() {
    let check = 'false';
    let count = 0;
    // get all check boxes
    const checks = document.getElementsByName('checkField');
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
    const checks = document.getElementsByName('checkField');
    if (checks.checked) {
        userRow = document.getElementById("myTable").rows[0];
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        document.forms[0].action = "editMake.htm";
        document.forms[0].submit();
    } else {
        for (var i = 0; i < checks.length; i++) {
            if (checks[i].checked) {
                userRow = document.getElementById("myTable").rows[i + 1];
            }
        }
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        document.forms[0].action = "editMake.htm";
        document.forms[0].submit();
    }
}

// delete
function deleteMake() {
    let check = 'false';
    let count = 0;
    // get all check boxes
    const checks = document.getElementsByName('checkField');
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
    const answer = confirm(" Are you sure you wanted to delete the make ");
    if (answer) {
        //if yes then delete
        let userRow;
        const checks = document.getElementsByName('checkField');
        if (checks.checked) {
            userRow = document.getElementById("myTable").rows[0];
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            document.forms[0].action = "deleteMake.htm";
            document.forms[0].submit();
        } else {
            for (var i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    userRow = document.getElementById("myTable").rows[i + 1];
                }
            }
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            document.forms[0].action = "deleteMake.htm";
            document.forms[0].submit();
        }
    }
}

//preventing multiple checks
function checkCall(e) {
    const min = e.value;
    const checks = document.getElementsByName('checkField');
    for (let i = 0; i < checks.length; i++) {
        if (checks[i].value !== min) {
            checks[i].checked = false;
        }
    }
}

function hideAlerts(){
    document.getElementById('make').text = "Make <span class='sr-only'>Make</span>";
}

function addSimpleMake(){
    const myTable = document.getElementById("myTable");

    const d = document.createElement("tr");
    const dCheck = document.createElement("td");
    d.appendChild(dCheck);

    const inCheck = document.createElement("input");
    inCheck.setAttribute("type","checkbox");
    inCheck.setAttribute("name","checkField");
    inCheck.setAttribute("onclick","javascript:checkCall(this)");
    dCheck.appendChild(inCheck);

    const dMake = document.createElement("td");
    d.appendChild(dMake);

    const inMake = document.createElement("input");
    inMake.setAttribute("type","text");
    inMake.setAttribute("class", "form-control");
    inMake.setAttribute("id", "newMakeName");
    dMake.appendChild(inMake);

    const dDesc = document.createElement("td");
    d.appendChild(dDesc);

    const inDesc = document.createElement("input");
    inDesc.setAttribute("type","text");
    inDesc.setAttribute("class", "form-control");
    inDesc.setAttribute("id", "newMakeDesc");
    dDesc.appendChild(inDesc);

    myTable.appendChild(d);
}

function saveSimpleMake() {
    let selectMakeName = document.forms[0].newMakeName.value;
    let selectMakeDesc = document.forms[0].newMakeDesc.value;
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "/make/saveMakeAjax.htm",true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function() {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                rewriteTable(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
        }
    };
    xhr.send("selectMakeName="+selectMakeName+ "&selectMakeDesc=" + selectMakeDesc + "&${_csrf.parameterName}=${_csrf.token}" );
}

function rewriteTable(textReturned) {
    document.getElementById('myTable').innerHTML = "";
    const myTable = document.getElementById("myTable");
    const thead = document.createElement("thead");
    const tr1 = document.createElement("tr");
    const th1 = document.createElement("th");
    th1.innerHTML = "#";
    th1.setAttribute("class","text-center");
    tr1.appendChild(th1);
    const th2 = document.createElement("th");
    th2.innerHTML = "Make Name";
    th2.setAttribute("class","text-center");
    tr1.appendChild(th2);
    const th3 = document.createElement("th");
    th3.innerHTML = "Description";
    th3.setAttribute("class","text-center");
    tr1.appendChild(th3);
    thead.appendChild(tr1);
    myTable.appendChild(thead);
    const makeList = JSON.parse(textReturned);
    const tbody = document.createElement("tbody");
    for (let i = 0; i < makeList.length; i++) {
        const singleMake = makeList[i];
        const trx = document.createElement("tr");
        const td1 = document.createElement("td");
        const inCheck = document.createElement("input");
        inCheck.setAttribute("type","checkbox");
        inCheck.setAttribute("name","checkField");
        inCheck.setAttribute("onclick","javascript:checkCall(this)");
        inCheck.setAttribute("value",singleMake.id);
        td1.appendChild(inCheck);
        trx.appendChild(td1);
        const td2 = document.createElement("td");
        td2.innerHTML = singleMake.makeName;
        trx.appendChild(td2);
        const td3 = document.createElement("td");
        td3.innerHTML = singleMake.description;
        trx.appendChild(td3);
        tbody.appendChild(trx);
    }
    myTable.appendChild(tbody);
    //todo: optional message saving update is done !!

}