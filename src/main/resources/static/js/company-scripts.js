function editMe() {
    makeEverythingWritable();
    hideEditAndShowUpdate();
}

function makeEverythingWritable() {
    document.getElementById("companyName").style.background = "#FFFFFF";
    document.getElementById('companyName').readOnly = false;
    document.getElementById("companyPhoneNumber").style.background = "#FFFFFF";
    document.getElementById('companyPhoneNumber').readOnly = false;
    document.getElementById("companyEmail").style.background = "#FFFFFF";
    document.getElementById('companyEmail').readOnly = false;
    document.getElementById("companyWebsite").style.background = "#FFFFFF";
    document.getElementById('companyWebsite').readOnly = false;
    document.getElementById("companyAddress").style.background = "#FFFFFF";
    document.getElementById('companyAddress').readOnly = false;
    document.getElementById("companyTerms").style.background = "#FFFFFF";
    document.getElementById('companyTerms').readOnly = false;
    document.getElementById("vat_tin").style.background = "#FFFFFF";
    document.getElementById('vat_tin').readOnly = false;
    document.getElementById("cst_tin").style.background = "#FFFFFF";
    document.getElementById('cst_tin').readOnly = false;
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
    document.getElementById('companyPhoneNumber').readOnly = true;
    document.getElementById("companyPhoneNumber").style.background = "#A9A9A9";
    document.getElementById('companyEmail').readOnly = true;
    document.getElementById("companyEmail").style.background = "#A9A9A9";
    document.getElementById('companyWebsite').readOnly = true;
    document.getElementById("companyWebsite").style.background = "#A9A9A9";
    document.getElementById('companyAddress').readOnly = true;
    document.getElementById("companyAddress").style.background = "#A9A9A9";
    document.getElementById('companyTerms').readOnly = true;
    document.getElementById("companyTerms").style.background = "#A9A9A9";
    document.getElementById('vat_tin').readOnly = true;
    document.getElementById("vat_tin").style.background = "#A9A9A9";
    document.getElementById('cst_tin').readOnly = true;
    document.getElementById("cst_tin").style.background = "#A9A9A9";
}

function updateCompanyDetails() {
    document.forms[0].action = "updateCompanyDetails.htm";
    document.forms[0].submit();
}

function clearOut() {
    document.getElementById('companyName').value = "";
    document.getElementById('companyPhoneNumber').value = "";
    document.getElementById('companyEmail').value = "";
    document.getElementById('companyWebsite').value = "";
    document.getElementById('companyAddress').value = "";
    document.getElementById('companyTerms').value = "";
    document.getElementById('vat_tin').value = "";
    document.getElementById('cst_tin').value = "";
}

function cancelMe() {
    document.forms[0].action = "List.htm";
    document.forms[0].submit();
}