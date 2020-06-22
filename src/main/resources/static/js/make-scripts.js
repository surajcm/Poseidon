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
    var check = 'false';
    var count = 0;
    // get all check boxes
    var checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            editRow();
        } else {
            for (var i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    check = 'true';
                    count = count + 1;
                }
            }
            //check for validity
            if (check = 'true') {
                if (count == 1) {
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
    var userRow;
    var checks = document.getElementsByName('checkField');
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
    var check = 'false';
    var count = 0;
    // get all check boxes
    var checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            deleteRow();
        } else {
            for (var i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    check = 'true';
                    count = count + 1;
                }
            }
            //check for validity
            if (check = 'true') {
                if (count == 1) {
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
    var answer = confirm(" Are you sure you wanted to delete the make ");
    if (answer) {
        //if yes then delete
        var userRow;
        var checks = document.getElementsByName('checkField');
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
    var min = e.value;
    var checks = document.getElementsByName('checkField');
    for (var i = 0; i < checks.length; i++) {
        if (checks[i].value != min) {
            checks[i].checked = false;
        }
    }
}

function hideAlerts(){
    document.getElementById('make').text = "Make <span class='sr-only'>Make</span>";
}

function addSimpleMake(){
    var myTable = document.getElementById("myTable");

    var d = document.createElement("tr");
    var dCheck = document.createElement("td");
    d.appendChild(dCheck);

    var inCheck = document.createElement("input");
    inCheck.setAttribute("type","checkbox");
    inCheck.setAttribute("name","checkField");
    inCheck.setAttribute("onclick","javascript:checkCall(this)");
    dCheck.appendChild(inCheck);

    var dMake = document.createElement("td");
    d.appendChild(dMake);

    var inMake = document.createElement("input");
    inMake.setAttribute("type","text");
    inMake.setAttribute("class", "form-control");
    inMake.setAttribute("id", "newMakeName");
    dMake.appendChild(inMake);

    var dDesc = document.createElement("td");
    d.appendChild(dDesc);

    var inDesc = document.createElement("input");
    inDesc.setAttribute("type","text");
    inDesc.setAttribute("class", "form-control");
    inDesc.setAttribute("id", "newMakeDesc");
    dDesc.appendChild(inDesc);

    myTable.appendChild(d);
}

function saveSimpleMake() {
    var selectMakeName = document.forms[0].newMakeName.value;
    var selectMakeDesc = document.forms[0].newMakeDesc.value;
    $.ajax({
        type: "POST",
        url: "${contextPath}/make/saveMakeAjax.htm",
        data: "selectMakeName=" + selectMakeName + "&selectMakeDesc=" + selectMakeDesc + "&${_csrf.parameterName}=${_csrf.token}",
        success: function(response) {
            //alert(response);
            if (response != "") {
                rewriteTable(response);
            }
        },error: function(e){
            alert('Error: ' + e);
        }
    });
}

function rewriteTable(textReturned) {
    document.getElementById('myTable').innerHTML = "";
    var myTable = document.getElementById("myTable");
    var thead = document.createElement("thead");
    var tr1 = document.createElement("tr");
    var th1 = document.createElement("th");
    th1.innerHTML = "#";
    th1.setAttribute("class","text-center");
    tr1.appendChild(th1);
    var th2 = document.createElement("th");
    th2.innerHTML = "Make Name";
    th2.setAttribute("class","text-center");
    tr1.appendChild(th2);
    var th3 = document.createElement("th");
    th3.innerHTML = "Description";
    th3.setAttribute("class","text-center");
    tr1.appendChild(th3);
    thead.appendChild(tr1);
    myTable.appendChild(thead);
    var makeList = JSON.parse(textReturned);
    var tbody = document.createElement("tbody");
    for (var i = 0; i < makeList.length; i++) {
        var singleMake = makeList[i];
        var trx = document.createElement("tr");
        var td1 = document.createElement("td");
        var inCheck = document.createElement("input");
        inCheck.setAttribute("type","checkbox");
        inCheck.setAttribute("name","checkField");
        inCheck.setAttribute("onclick","javascript:checkCall(this)");
        inCheck.setAttribute("value",singleMake.id);
        td1.appendChild(inCheck);
        trx.appendChild(td1);
        var td2 = document.createElement("td");
        td2.innerHTML = singleMake.makeName;
        trx.appendChild(td2);
        var td3 = document.createElement("td");
        td3.innerHTML = singleMake.description;
        trx.appendChild(td3);
        tbody.appendChild(trx);
    }
    myTable.appendChild(tbody);
    //todo: optional message saving update is done !!

}