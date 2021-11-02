

function hideAlerts() {
    document.getElementById('inventory').text = "Invoice <span class='sr-only'>Invoice</span>";
}

function addInvoice() {
    document.forms[0].action = "addInvoice.htm";
    document.forms[0].submit();
}

function search() {
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
function validateSelection() {
    let check = 'false';
    let count = 0;
    // get all check boxes
    const checks = document.getElementsByName('checkField');
    if (checks) {
        //if total number of rows is one
        if (checks.checked) {
            return true;
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
                    return true;
                } else {
                    alert("Only one row can be edited at a time, please select one row ");
                    return false;
                }
            } else {
                alert("No rows selected, please select one row ");
                return false;
            }
        }
    }
}

function editMe() {
    let rowCheck = validateSelection();
    if (rowCheck) {
        editRow();
    }
}

//real edit
function editRow() {
    let userRow;
    const checks = document.getElementsByName('checkField');
    if (checks.checked) {
        userRow = document.getElementById("myTable").rows[0];
    } else {
        for (let i = 0; i < checks.length; i++) {
            if (checks[i].checked) {
                userRow = document.getElementById("myTable").rows[i + 1];
            }
        }
    }
    document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
    if (document.getElementById('amount').value.length === 0) {
        document.getElementById('amount').value = "0.0";
    }
    document.forms[0].action = "EditInvoice.htm";
    document.forms[0].submit();
}

// delete
function deleteInvoice() {
    let rowCheck = validateSelection();
    if (rowCheck) {
        deleteRow();
    }
}

//code to delete a user
function deleteRow() {
    const answer = confirm(" Are you sure you wanted to delete the Txn ");
    if (answer) {
        //if yes then delete
        let userRow;
        const checks = document.getElementsByName('checkField');
        if (checks.checked) {
            userRow = document.getElementById("myTable").rows[0];
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            if (document.getElementById('amount').value.length === 0) {
                document.getElementById('amount').value = "0.0";
            }
            document.forms[0].action = "DeleteInvoice.htm";
            document.forms[0].submit();
        } else {
            for (let i = 0; i < checks.length; i++) {
                if (checks[i].checked) {
                    userRow = document.getElementById("myTable").rows[i + 1];
                }
            }
            document.getElementById("id").value = userRow.cells[0].childNodes[0].value;
            if (document.getElementById('amount').value.length === 0) {
                document.getElementById('amount').value = "0.0";
            }
            document.forms[0].action = "DeleteInvoice.htm";
            document.forms[0].submit();
        }
    }

}

function addSmartInvoice() {

}