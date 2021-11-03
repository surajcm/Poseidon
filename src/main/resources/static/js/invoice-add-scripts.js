function save() {
    if (document.getElementById('tagNo').value.length == 0) {
        alert("Please enter a valid Tag No");
    } else if (document.getElementById('quantity').value.length == 0) {
        alert("Please enter a valid Quantity");
    } else if (document.getElementById('rate').value.length == 0) {
        alert("Please enter a valid Rate");
    } else if (document.getElementById('amount').value.length == 0) {
        alert("Please enter a valid Amount");
    } else {
        document.forms[0].action = "saveInvoice.htm";
        document.forms[0].submit();
    }
}

function clearOut() {
    document.getElementById('tagNo').value = "";
    document.getElementById('description').value = "";
    document.getElementById('quantity').value = "";
    document.getElementById('rate').value = "";
    document.getElementById('amount').value = "";
}

function multiplyFromRate() {
    if(document.getElementById('quantity').value.length > 0) {
        document.getElementById('amount').value =
            document.getElementById('quantity').value * document.getElementById('rate').value;
    }
}

function multiplyFromQty() {
    if(document.getElementById('quantity').value.length > 0 && document.getElementById('rate').value > 0) {
        document.getElementById('amount').value =
            document.getElementById('quantity').value * document.getElementById('rate').value;
    }
}