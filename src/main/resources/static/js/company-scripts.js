function editMe() {
    makeEverythingWritable();
    hideEditAndShowUpdate();
}

function makeEverythingWritable() {
    document.getElementById("companyName").style.background = "#FFFFFF";
    document.getElementById('companyName').readOnly = false;
    document.getElementById("companyPhone").style.background = "#FFFFFF";
    document.getElementById('companyPhone').readOnly = false;
    document.getElementById("companyEmail").style.background = "#FFFFFF";
    document.getElementById('companyEmail').readOnly = false;
    document.getElementById("companyWebsite").style.background = "#FFFFFF";
    document.getElementById('companyWebsite').readOnly = false;
    document.getElementById("companyAddress").style.background = "#FFFFFF";
    document.getElementById('companyAddress').readOnly = false;
    document.getElementById("terms").style.background = "#FFFFFF";
    document.getElementById('terms').readOnly = false;
    document.getElementById("companyCode").style.background = "#FFFFFF";
    document.getElementById('companyCode').readOnly = false;
    document.getElementById("vatTin").style.background = "#FFFFFF";
    document.getElementById('vatTin').readOnly = false;
    document.getElementById("cstTin").style.background = "#FFFFFF";
    document.getElementById('cstTin').readOnly = false;
}

function hideEditAndShowUpdate() {
    document.getElementById('edit').style.visibility = 'hidden';
    document.getElementById('update').style.visibility = 'visible';
    document.getElementById('clear').style.visibility = 'visible';
    document.getElementById('cancel').style.visibility = 'visible';
}

function hideUpdate() {
    document.getElementById('company').text = "Company <span class='sr-only'>Company</span>";
    document.getElementById('update').style.visibility = 'hidden';
    document.getElementById('clear').style.visibility = 'hidden';
    document.getElementById('cancel').style.visibility = 'hidden';
    makeEverythingReadOnly();
}

function makeEverythingReadOnly() {
    document.getElementById('companyName').readOnly = true;
    document.getElementById("companyName").style.background = "#A9A9A9";
    document.getElementById('companyPhone').readOnly = true;
    document.getElementById("companyPhone").style.background = "#A9A9A9";
    document.getElementById('companyEmail').readOnly = true;
    document.getElementById("companyEmail").style.background = "#A9A9A9";
    document.getElementById('companyWebsite').readOnly = true;
    document.getElementById("companyWebsite").style.background = "#A9A9A9";
    document.getElementById('companyAddress').readOnly = true;
    document.getElementById("companyAddress").style.background = "#A9A9A9";
    document.getElementById('terms').readOnly = true;
    document.getElementById("terms").style.background = "#A9A9A9";
    document.getElementById('companyCode').readOnly = true;
    document.getElementById("companyCode").style.background = "#A9A9A9";
    document.getElementById('vatTin').readOnly = true;
    document.getElementById("vatTin").style.background = "#A9A9A9";
    document.getElementById('cstTin').readOnly = true;
    document.getElementById("cstTin").style.background = "#A9A9A9";
}

function updateCompanyDetails() {
    document.forms[0].action = "updateCompanyDetails";
    document.forms[0].submit();
}

function clearOut() {
    document.getElementById('companyName').value = "";
    document.getElementById('companyPhone').value = "";
    document.getElementById('companyEmail').value = "";
    document.getElementById('companyWebsite').value = "";
    document.getElementById('companyAddress').value = "";
    document.getElementById('terms').value = "";
    document.getElementById('companyCode').value = "";
    document.getElementById('vatTin').value = "";
    document.getElementById('cstTin').value = "";
}

function cancelMe() {
    document.forms[0].action = "Company";
    document.forms[0].submit();
}