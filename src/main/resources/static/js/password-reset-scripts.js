function passwordChange() {
    console.log("going to reset password");
    //if any are empty show a message
    var current = document.getElementById("current").value;
    var newPass = document.getElementById("newPass").value;
    var repeat = document.getElementById("repeat").value;
    if (newPass === repeat) {
    console.log("both passwords are equal");
        // try submitting
    } else {
        // show error message
        console.log("both passwords are not equal");
        let statusMessage = document.getElementById("status");
        let divStatus = document.createElement("div");
        divStatus.setAttribute("class","alert alert-error");
        //divStatus.innerHTML = "hello";
        var anc = document.createElement("a");
        divStatus.setAttribute("class","close");
        divStatus.setAttribute("data-dismiss","alert");
        divStatus.setAttribute("href","#");
        divStatus.setAttribute("aria-hidden","true");
        divStatus.setAttribute("value","x");
        divStatus.appendChild(anc);
        var para = document.createElement("p");
        divStatus.setAttribute("value","Password mismatch !!!");
        divStatus.appendChild(para);
        statusMessage.appendChild(divStatus);
    }
}

function clearAll() {
    document.getElementById("current").value = "";
    document.getElementById("newPass").value = "";
    document.getElementById("repeat").value = "";
}

function hideAlerts(){
    document.getElementById('user').text = "User <span class='sr-only'>User</span>";
}