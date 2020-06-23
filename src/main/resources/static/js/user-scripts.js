// delete user
function deleteUser() {
    var check ='false';
    var count = 0;
    // get all check boxes
    var checks = document.getElementsByName('checkField');
    if(checks){
        //if total number of rows is one
        if (checks.checked) {
            deleteRow();
        } else {
            for(var i = 0 ; i < checks.length ; i++ ) {
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

//code to delete a user
function deleteRow() {
    var answer = confirm(" Are you sure you wanted to delete the user ");
    if (answer){
        //if yes then delete
        var userRow;
        var checks = document.getElementsByName('checkField');
        if (checks.checked){
            userRow = document.getElementById("myTable").rows[0];
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            document.forms[0].action="DeleteUser.htm";
            document.forms[0].submit();
        } else {
            for(var i = 0; i < checks.length ; i++){
                if(checks[i].checked) {
                    userRow = document.getElementById("myTable").rows[i+1];
                }
            }
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            document.forms[0].action="DeleteUser.htm";
            document.forms[0].submit();
        }
    }
}

//preventing multiple checks
function checkCall(e) {
    var min = e.value;
    var checks = document.getElementsByName('checkField');
    for(var i = 0; i < checks.length ; i++){
        if(checks[i].value != min) {
            checks[i].checked = false;
        }
    }
}

//validation before edit a user
function editMe(){
    var check ='false';
    var count = 0;
    // get all check boxes
    var checks = document.getElementsByName('checkField');
    if (checks){
        //if total number of rows is one
        if (checks.checked){
            editRow();
        } else {
            for(var i = 0 ; i < checks.length ; i++ ) {
                if(checks[i].checked){
                    check = 'true';
                    count = count + 1;
                }
            }
            //check for validity
            if(check = 'true'){
                if(count == 1){
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
function editRow(){
    var userRow;
    var checks = document.getElementsByName('checkField');
    if(checks.checked){
        userRow = document.getElementById("myTable").rows[0];
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        document.forms[0].action="EditUser.htm";
        document.forms[0].submit();
    } else {
        for(var i = 0; i < checks.length ; i++){
            if(checks[i].checked) {
                userRow = document.getElementById("myTable").rows[i+1];
            }
        }
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        document.forms[0].action="EditUser.htm";
        document.forms[0].submit();
    }
}

function search() {
    document.forms[0].action="SearchUser.htm";
    document.forms[0].submit();
}

function addNewUser() {
    var isAddInProgress = document.getElementById("id").value;
    //alert(isAddInProgress);
    if (isAddInProgress != "TRUE") {
        document.getElementById("id").value = "TRUE";
        addUser();
    }
}

function addUser() {
    //todo: add check for multiple calls
    var myTable = document.getElementById("myTable");

    var d = document.createElement("tr");
    var dCheck = document.createElement("td");
    d.appendChild(dCheck);

    var inCheck = document.createElement("input");
    inCheck.setAttribute("type","checkbox");
    inCheck.setAttribute("name","checkField");
    inCheck.setAttribute("onclick","javascript:checkCall(this)");
    dCheck.appendChild(inCheck);

    var dUser = document.createElement("td");
    d.appendChild(dUser);

    var inUser = document.createElement("input");
    inUser.setAttribute("type","text");
    inUser.setAttribute("class", "form-control");
    inUser.setAttribute("id", "newName");
    dUser.appendChild(inUser);

    var dLogIn = document.createElement("td");
    d.appendChild(dLogIn);

    var inLogin = document.createElement("input");
    inLogin.setAttribute("type","text");
    inLogin.setAttribute("class", "form-control");
    inLogin.setAttribute("id", "newLogin");
    dLogIn.appendChild(inLogin);

    var dRole = document.createElement("td");
    d.appendChild(dRole);

    var inRole = document.createElement("select");
    inRole.setAttribute("class", "form-control");
    inRole.setAttribute("id", "newRole");
    // get these values from somewhere else
    var op1 = new Option();
    op1.value = "ADMIN";
    op1.text = "ADMIN";
    inRole.options.add(op1);
    var op2 = new Option();
    op2.value = "GUEST";
    op2.text = "GUEST";
    inRole.options.add(op2);
    dRole.appendChild(inRole);
    myTable.appendChild(d);
}

function clearOut(){
    document.getElementById("name").value = "";
    document.getElementById("loginId").value = "";
    document.getElementById("role").value = document.getElementById('role').options[0].value;
}

function hideAlerts(){
    document.getElementById('user').text = "User <span class='sr-only'>User</span>";
}

function simpleSave(){
    var selectName = document.forms[0].newName.value;
    var selectLogin = document.forms[0].newLogin.value;
    var selectRole = document.forms[0].newRole.value;
    $.ajax({
        type: "POST",
        url: "${contextPath}/user/saveUserAjax.htm",
        data: "selectName=" + selectName + "&selectLogin=" + selectLogin + "&selectRole=" + selectRole + "&${_csrf.parameterName}=${_csrf.token}",
        success: function(response) {
            //alert(response);
            if (response != "") {
                rewriteTable(response);
            }
        },error: function(e){
            alert('Error: ' + e);
            //alert(data.responseText);
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
    th2.innerHTML = "Name";
    th2.setAttribute("class","text-center");
    tr1.appendChild(th2);

    var th3 = document.createElement("th");
    th3.innerHTML = "loginId";
    th3.setAttribute("class","text-center");
    tr1.appendChild(th3);


    var th4 = document.createElement("th");
    th4.innerHTML = "Role";
    th4.setAttribute("class","text-center");
    tr1.appendChild(th4);

    thead.appendChild(tr1);
    myTable.appendChild(thead);
    var userList = JSON.parse(textReturned);
    var tbody = document.createElement("tbody");
    for (var i = 0; i < userList.length; i++) {
        var singleUser = userList[i];
        var trx = document.createElement("tr");
        var td1 = document.createElement("td");
        var inCheck = document.createElement("input");
        inCheck.setAttribute("type","checkbox");
        inCheck.setAttribute("name","checkField");
        inCheck.setAttribute("onclick","javascript:checkCall(this)");
        inCheck.setAttribute("value",singleUser.id);
        td1.appendChild(inCheck);
        trx.appendChild(td1);
        var td2 = document.createElement("td");
        td2.innerHTML = singleUser.name;
        trx.appendChild(td2);
        var td3 = document.createElement("td");
        td3.innerHTML = singleUser.loginId;
        trx.appendChild(td3);

        var td4 = document.createElement("td");
        td4.innerHTML = singleUser.role;
        trx.appendChild(td4);

        tbody.appendChild(trx);
    }
    myTable.appendChild(tbody);
    //todo: optional message saving update is done !!
    var isAddInProgress = document.getElementById("id").value;
    isAddInProgress = "FALSE";
}

function addNewUserModal() {
    var detail = document.getElementById("userModalBody");
    detail.innerHTML = "";
    var divFirstRow = document.createElement("div");
    divFirstRow.setAttribute("class","form-row align-items-left");
    var divName = document.createElement("div");
    divName.setAttribute("class","form-group col-md-4");
    var lbName = document.createElement("label");
    lbName.textContent = "Name : ";
    divName.appendChild(lbName);
    var txtName = document.createElement("input");
    txtName.setAttribute("type","text");
    txtName.setAttribute("class","form-control");
    divName.appendChild(txtName);
    
    var divLoginId = document.createElement("div");
    divLoginId.setAttribute("class","form-group col-md-4");
    var lbloginId = document.createElement("label");
    lbloginId.textContent = "Login Id : ";
    divLoginId.appendChild(lbloginId);
    var txtLoginId = document.createElement("input");
    txtLoginId.setAttribute("type","text");
    txtLoginId.setAttribute("class","form-control");
    divLoginId.appendChild(txtLoginId);

    var divRole = document.createElement("div");
    divRole.setAttribute("class","form-group col-md-4");
    var lbRole = document.createElement("label");
    lbRole.textContent = "Role : ";
    divRole.appendChild(lbRole);
    var selectRole = document.createElement("select");
    selectRole.setAttribute("class","form-control");
    var adminOption = document.createElement("option");
    adminOption.text = 'ADMIN';
    adminOption.value = 'ADMIN';
    var guestOption = document.createElement("option");
    guestOption.text = 'GUEST';
    guestOption.value = 'GUEST';
    selectRole.appendChild(adminOption);
    selectRole.appendChild(guestOption);
    divRole.appendChild(selectRole);

    divFirstRow.appendChild(divName);
    divFirstRow.appendChild(divLoginId);
    divFirstRow.appendChild(divRole);
    detail.appendChild(divFirstRow);
}