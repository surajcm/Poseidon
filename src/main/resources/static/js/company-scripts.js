function editMe() {
    makeEverythingWritable();
    hideEditAndShowUpdate();
}

function makeEverythingWritable() {
    document.getElementById("name").style.background = "#FFFFFF";
    document.getElementById('name').readOnly = false;
    document.getElementById("name").disabled = false;
    document.getElementById("phone").style.background = "#FFFFFF";
    document.getElementById('phone').readOnly = false;
    document.getElementById("phone").disabled = false;

    document.getElementById("email").style.background = "#FFFFFF";
    document.getElementById('email').readOnly = false;
    document.getElementById("email").disabled = false;

    document.getElementById("website").style.background = "#FFFFFF";
    document.getElementById('website').readOnly = false;
    document.getElementById("website").disabled = false;

    document.getElementById("address").style.background = "#FFFFFF";
    document.getElementById('address').readOnly = false;
    document.getElementById("address").disabled = false;

    document.getElementById("terms").style.background = "#FFFFFF";
    document.getElementById('terms').readOnly = false;
    document.getElementById("terms").disabled = false;

    document.getElementById("code").style.background = "#FFFFFF";
    document.getElementById('code').readOnly = false;
    document.getElementById("code").disabled = false;

    document.getElementById("vatTin").style.background = "#FFFFFF";
    document.getElementById('vatTin').readOnly = false;
    document.getElementById("vatTin").disabled = false;

    document.getElementById("cstTin").style.background = "#FFFFFF";
    document.getElementById('cstTin').readOnly = false;
    document.getElementById("cstTin").disabled = false;

}

function hideEditAndShowUpdate() {
    document.getElementById('edit').style.visibility = 'hidden';
    document.getElementById('update').style.visibility = 'visible';
}

function hideUpdate() {
    document.getElementById('update').style.visibility = 'hidden';
    makeEverythingReadOnly();
}

function makeEverythingReadOnly() {
    document.getElementById('name').readOnly = true;
    document.getElementById("name").style.background = "#A9A9A9";
    document.getElementById('phone').readOnly = true;
    document.getElementById("phone").style.background = "#A9A9A9";
    document.getElementById('email').readOnly = true;
    document.getElementById("email").style.background = "#A9A9A9";
    document.getElementById('website').readOnly = true;
    document.getElementById("website").style.background = "#A9A9A9";
    document.getElementById('address').readOnly = true;
    document.getElementById("address").style.background = "#A9A9A9";
    document.getElementById('terms').readOnly = true;
    document.getElementById("terms").style.background = "#A9A9A9";
    document.getElementById('code').readOnly = true;
    document.getElementById("code").style.background = "#A9A9A9";
    document.getElementById('vatTin').readOnly = true;
    document.getElementById("vatTin").style.background = "#A9A9A9";
    document.getElementById('cstTin').readOnly = true;
    document.getElementById("cstTin").style.background = "#A9A9A9";
}

function updateCompanyDetails() {
    document.forms[0].action = "/company/update";
    document.forms[0].submit();
}
