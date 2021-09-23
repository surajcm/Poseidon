function update() {
    if (document.getElementById('tagNo').value == null ||
        document.getElementById('tagNo').value.length === 0) {
        alert("Please enter a valid Tag No");
    } else if (document.getElementById('quantity').value == null
        && document.getElementById('quantity').value.length === 0) {
        alert("Please enter a valid Quantity");
    } else if (document.getElementById('rate').value == null
        && document.getElementById('rate').value.length === 0) {
        alert("Please enter a valid Rate");
    } else {
        document.forms[0].action = "updateInvoice.htm";
        document.forms[0].submit();
    }
}

function cancel() {
    document.forms[0].action = "ListInvoice.htm";
    document.forms[0].submit();
}