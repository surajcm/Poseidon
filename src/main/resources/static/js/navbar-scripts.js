function listMe() {
    document.forms[0].action = "/user/ListAll.htm";
    document.forms[0].submit();
}

function goToHome() {
    document.forms[0].action = "/user/ToHome";
    document.forms[0].submit();
}

function LogMeOut() {
    document.forms[0].setAttribute("th:action", "@{/LogMeOut}");
    //document.forms[0].action = "/LogMeOut";
    document.forms[0].submit();
}

function resetPassword() {
    document.forms[0].action = "/user/PasswordReset.htm";
    document.forms[0].submit();
}

function fetchModels() {
    document.forms[0].action = "/make/ModelList.htm";
    document.forms[0].submit();
}

function fetchCustomers() {
    document.forms[0].action = "/customer/List.htm";
    document.forms[0].submit();
}

function fetchCompany() {
    document.forms[0].action = "/company/Company.htm";
    document.forms[0].submit();
}

function fetchTransactions() {
    document.forms[0].action = "/txs/List.htm";
    document.forms[0].submit();
}

function fetchReport() {
    document.forms[0].action = "/reports/List.htm";
    document.forms[0].submit();
}

function fetchInvoice() {
    document.forms[0].action = "/invoice/ListInvoice.htm";
    document.forms[0].submit();
}