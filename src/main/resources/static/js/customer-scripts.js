function addCustomer(){
    document.forms[0].action = "addCustomer.htm";
    document.forms[0].submit();
}

function search() {
    if(document.getElementById("customerId").value == ""
            || isNumber(document.getElementById("customerId").value)) {
        document.forms[0].action = "searchCustomer.htm";
        document.forms[0].submit();
    } else {
        alert("Incorrect customerId format found, Please update the field with a numeric value");
    }
}

function isNumber(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

function clearOut() {
    document.getElementById("customerId").value = "";
    document.getElementById("customerName").value = "";
    document.getElementById("mobile").value = "";
    document.getElementById('includes').checked = false;
    document.getElementById('startsWith').checked = false;
}
//validation before edit
function editCustomer() {
    var check ='false';
    var count = 0;
    // get all check boxes
    var checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            editRow();
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
        document.forms[0].action="editCust.htm";
        document.forms[0].submit();
    } else {
        for(var i = 0; i < checks.length ; i++) {
            if (checks[i].checked) {
                userRow = document.getElementById("myTable").rows[i+1];
            }
        }
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        document.forms[0].action="editCust.htm";
        document.forms[0].submit();
    }
}

// delete
function deleteCustomer() {
    var check ='false';
    var count = 0;
    // get all check boxes
    var checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            deleteRow();
        } else {
            for(var i = 0 ; i < checks.length ; i++ ) {
                if( checks[i].checked){
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
    var answer = confirm(" Are you sure you wanted to delete the user ");
    if (answer) {
        //if yes then delete
        var userRow;
        var checks = document.getElementsByName('checkField');
        if (checks.checked) {
            userRow = document.getElementById("myTable").rows[0];
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            document.forms[0].action="deleteCust.htm";
            document.forms[0].submit();
        } else {
            for(var i = 0; i < checks.length ; i++) {
                if(checks[i].checked) {
                    userRow = document.getElementById("myTable").rows[i+1];
                }
            }
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            document.forms[0].action="deleteCust.htm";
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

function hideAlerts() {
    document.getElementById('customer').text = "Customer <span class='sr-only'>Customer</span>";
}

function viewCustomer() {
    var check ='false';
    var count = 0;
    // get all check boxes
    var checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            viewRow();
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
                    viewRow();
                } else {
                    alert(" Only one row can be viewed at a time, please select one row ");
                }
            } else {
                alert(" No rows selected, please select one row ");
            }
        }
    }
}

function viewRow() {
    var userRow;
    var customerId;
    var checks = document.getElementsByName('checkField');
    if (checks.checked) {
        userRow = document.getElementById("myTable").rows[0];
        customerId = userRow.cells[0].childNodes[0].value;
    } else {
        for (var i = 0; i < checks.length ; i++) {
            if (checks[i].checked) {
                userRow = document.getElementById("myTable").rows[i+1];
            }
        }
        customerId = userRow.cells[0].childNodes[0].value;
    }
    callAjax(customerId);
}


function callAjax(customerId) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "viewCustomer.htm",true);
    var token = document.querySelector("meta[name='_csrf']").content;
    var header = document.querySelector("meta[name='_csrf_header']").content;
    xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function() {
        if (xhr.status === 200) {
            console.log('Response is ' + xhr.responseText);
            if (xhr.responseText != null) {
                fillModal(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
        }
    };
    xhr.send("customerId=" + customerId);
}

function fillModal(textReturned) {
    var customer = JSON.parse(textReturned);
    document.getElementById('detail').innerHTML = "";
    var detail = document.getElementById("detail");
    var divFirstRow = document.createElement("div");
    divFirstRow.setAttribute("class","form-row align-items-left");
    var divId = document.createElement("div");
    divId.setAttribute("class","form-group col-md-6");
    var lbId = document.createElement("label");
    lbId.textContent = "Customer Id : ";
    divId.appendChild(lbId);
    var txtId = document.createElement("label");
    txtId.textContent = customer.customerId;
    divId.appendChild(txtId);
    var divCustomer = document.createElement("div");
    divCustomer.setAttribute("class","form-group col-md-6");
    var lbName = document.createElement("label");
    lbName.textContent = "Customer Name : ";
    divCustomer.appendChild(lbName);
    var txtName = document.createElement("label");
    txtName.textContent = customer.customerName;
    divCustomer.appendChild(txtName);
    divFirstRow.appendChild(divId);
    divFirstRow.appendChild(divCustomer);

    var divAddress = document.createElement("div");
    divAddress.setAttribute("class","form-row align-items-left");
    var divAdd1 = document.createElement("div");
    divAdd1.setAttribute("class","form-group col-md-12");
    var lbAddress = document.createElement("label");
    lbAddress.textContent = "Address : ";
    divAdd1.appendChild(lbAddress);
    var txtAddress = document.createElement("label");
    txtAddress.textContent = customer.address1 + ' ' + customer.address2;
    divAdd1.appendChild(txtAddress);
    divAddress.appendChild(divAdd1);

    var divPhoneRow = document.createElement("div");
    divPhoneRow.setAttribute("class","form-row align-items-left");
    var divPhone = document.createElement("div");
    divPhone.setAttribute("class","form-group col-md-4");
    var lbPhone = document.createElement("label");
    lbPhone.textContent = "Phone No : ";
    divPhone.appendChild(lbPhone);
    var txtPhone = document.createElement("label");
    txtPhone.textContent = customer.phoneNo;
    divPhone.appendChild(txtPhone);
    var divMobile = document.createElement("div");
    divMobile.setAttribute("class","form-group col-md-4");
    var lbMobile = document.createElement("label");
    lbMobile.textContent = "Mobile : ";
    divMobile.appendChild(lbMobile);
    var txtMobile = document.createElement("label");
    txtMobile.textContent = customer.mobile;
    divMobile.appendChild(txtMobile);
    var divEmail= document.createElement("div");
    divEmail.setAttribute("class","form-group col-md-4");
    var lbEmail = document.createElement("label");
    lbEmail.textContent = "Email : ";
    divEmail.appendChild(lbEmail);
    var txtEmail = document.createElement("label");
    txtEmail.textContent = customer.email;
    divEmail.appendChild(txtEmail);
    divPhoneRow.appendChild(divPhone);
    divPhoneRow.appendChild(divMobile);
    divPhoneRow.appendChild(divEmail);

    var divFirstContact = document.createElement("div");
    divFirstContact.setAttribute("class","form-row align-items-left");
    var divP1 = document.createElement("div");
    divP1.setAttribute("class","form-group col-md-6");
    var lbP1 = document.createElement("label");
    lbP1.textContent = "Contact Person 1: ";
    divP1.appendChild(lbP1);
    var txtP1 = document.createElement("label");
    txtP1.textContent = customer.contactPerson1;
    divP1.appendChild(txtP1);
    var divP1Mobile1 = document.createElement("div");
    divP1Mobile1.setAttribute("class","form-group col-md-6");
    var lbP1Mobile = document.createElement("label");
    lbP1Mobile.textContent = "Contact Mobile 1 : ";
    divP1Mobile1.appendChild(lbP1Mobile);
    var txtP1Mobile = document.createElement("label");
    txtP1Mobile.textContent = customer.contactMobile1;
    divP1Mobile1.appendChild(txtP1Mobile);
    divFirstContact.appendChild(divP1);
    divFirstContact.appendChild(divP1Mobile1);

    var divSecondContact = document.createElement("div");
    divSecondContact.setAttribute("class","form-row align-items-left");
    var divP2 = document.createElement("div");
    divP2.setAttribute("class","form-group col-md-6");
    var lbP2 = document.createElement("label");
    lbP2.textContent = "Contact Person 2: ";
    divP2.appendChild(lbP2);
    var txtP2 = document.createElement("label");
    txtP2.textContent = customer.contactPerson2;
    divP2.appendChild(txtP2);
    var divP1Mobile2 = document.createElement("div");
    divP1Mobile2.setAttribute("class","form-group col-md-6");
    var lbP2Mobile = document.createElement("label");
    lbP2Mobile.textContent = "Contact Mobile 2 : ";
    divP1Mobile2.appendChild(lbP2Mobile);
    var txtP2Mobile = document.createElement("label");
    txtP2Mobile.textContent = customer.contactMobile2;
    divP1Mobile2.appendChild(txtP2Mobile);
    divSecondContact.appendChild(divP2);
    divSecondContact.appendChild(divP1Mobile2);

    var divNotes = document.createElement("div");
    divNotes.setAttribute("class","form-row align-items-left");
    var divNotes2 = document.createElement("div");
    divNotes2.setAttribute("class","form-group col-md-12");
    var lbNotes = document.createElement("label");
    lbNotes.textContent = "Notes : ";
    divNotes2.appendChild(lbNotes);
    var txtNotes = document.createElement("label");
    txtNotes.textContent = customer.notes;
    divNotes2.appendChild(txtNotes);
    divNotes.appendChild(divNotes2);

    detail.appendChild(divFirstRow);
    detail.appendChild(divAddress);
    detail.appendChild(divPhoneRow);
    detail.appendChild(divFirstContact);
    detail.appendChild(divSecondContact);
    detail.appendChild(divNotes);

}

