function addInvoice() {
    /*if(document.getElementById('amount').value.length == 0){
     document.getElementById('amount').value = "0.0";
     }*/
    document.forms[0].action = "addInvoice.htm";
    document.forms[0].submit();
}

function search() {
    /*if(document.getElementById('amount').value.length == 0){
     document.getElementById('amount').value = "0.0";
     }*/
    document.forms[0].action = "SearchInvoice.htm";
    document.forms[0].submit();
}

function clearOut() {
    document.getElementById('invoiceId').value = "";
    document.getElementById('description').value = "";
    document.getElementById('serialNo').value = "";
    document.getElementById('tagNo').value = "";
    document.getElementById('amount').value = "";
    document.getElementById('greater').checked = false;
    document.getElementById('greater').checked = false;
    document.getElementById('lesser').checked = false;
    document.getElementById('startsWith').checked = false;
}

//validation before edit
function editMe() {
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
        if(document.getElementById('amount').value.length == 0){
            document.getElementById('amount').value = "0.0";
        }
        document.forms[0].action = "EditInvoice.htm";
        document.forms[0].submit();
    } else {
        for (var i = 0; i < checks.length; i++) {
            if (checks[i].checked) {
                userRow = document.getElementById("myTable").rows[i + 1];
            }
        }
        document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
        if(document.getElementById('amount').value.length == 0){
            document.getElementById('amount').value = "0.0";
        }
        document.forms[0].action = "EditInvoice.htm";
        document.forms[0].submit();
    }
}

// delete
function deleteInvoice() {
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

//code to delete a user
function deleteRow() {
    var answer = confirm(" Are you sure you wanted to delete the Txn ");
    if (answer) {
        //if yes then delete
        var userRow;
        var checks = document.getElementsByName('checkField');
        if (checks.checked) {
            userRow = document.getElementById("myTable").rows[0];
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            if(document.getElementById('amount').value.length == 0){
                document.getElementById('amount').value = "0.0";
            }
            document.forms[0].action = "DeleteInvoice.htm";
            document.forms[0].submit();
        } else {
            for (var i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    userRow = document.getElementById("myTable").rows[i + 1];
                }
            }
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            if(document.getElementById('amount').value.length == 0){
                document.getElementById('amount').value = "0.0";
            }
            document.forms[0].action = "DeleteInvoice.htm";
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

function hideAlerts() {
    document.getElementById('invoice').text = "Invoice <span class='sr-only'>Invoice</span>";
}