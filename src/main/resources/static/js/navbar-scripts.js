function listMe() {
    document.forms[0].action = "/user/listAll";
    document.forms[0].submit();
}

function goToHome() {
    document.forms[0].action = "/home";
    document.forms[0].submit();
}

function logMeOut() {
    document.forms[0].action = "/logMeOut";
    document.forms[0].submit();
}

function resetPassword() {
    document.forms[0].action = "/user/passwordReset";
    document.forms[0].submit();
}

function fetchModels() {
    document.forms[0].action = "/make/ModelList";
    document.forms[0].submit();
}

function fetchCustomers() {
    document.forms[0].action = "/customer/List";
    document.forms[0].submit();
}

function fetchCompany() {
    document.forms[0].action = "/company/company";
    document.forms[0].submit();
}

function fetchTransactions() {
    document.forms[0].action = "/txs/listTransactions";
    document.forms[0].submit();
}

function fetchReport() {
    document.forms[0].action = "/reports/list";
    document.forms[0].submit();
}

function fetchInvoice() {
    document.forms[0].action = "/invoice/ListInvoice";
    document.forms[0].submit();
}