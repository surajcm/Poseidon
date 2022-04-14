function passwordChange() {
    console.log("going to reset password");
    //if any are empty show a message
    let forms = document.getElementsByClassName('needs-validation');
    let current = document.getElementById("current").value;
    let newPass = document.getElementById("newPass").value;
    let repeat = document.getElementById("repeat").value;

    if (forms[0].checkValidity() === false) {
        if (current.length === 0) {
            document.getElementById("current").setAttribute("class", "form-control is-invalid");
        } else {
            document.getElementById("current").setAttribute("class", "form-control was-validated");
        }
        if (newPass.length === 0) {
            document.getElementById("newPass").setAttribute("class", "form-control is-invalid");
        } else {
            document.getElementById("newPass").setAttribute("class", "form-control was-validated");
        }
        if (repeat.length === 0) {
            document.getElementById("repeat").setAttribute("class", "form-control is-invalid");
        } else {
            document.getElementById("repeat").setAttribute("class", "form-control was-validated");
        }
        return;
    }
    if (newPass === current) {
        console.log("Password entered in both current and new fields are equal");
        // show error
        return;
    }
    if (newPass === repeat) {
        console.log("both passwords are equal");
        let passRegex = /\w{7,14}$/;
        if (newPass.match(passRegex)) {
            console.log('Correct, going to save it');
            changePasswordAndSaveIt(current, newPass);
        } else {
            console.log('not strong enough');
            document.getElementById("newPass").setAttribute("class", "form-control is-invalid");
            document.getElementById("repeat").setAttribute("class", "form-control is-invalid");
            document.getElementById("newPass_message").innerHTML = "The password entered is not strong enough";
            document.getElementById("repeat_message").innerHTML = "The password entered is not strong enough";
        }
    } else {
        console.log("both passwords are not equal");
        document.getElementById("repeat").setAttribute("class", "form-control is-invalid");
        document.getElementById("repeat_message").innerHTML = "The password entered are not equal";
    }
}

function changePasswordAndSaveIt(current, newPass) {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "/user/changePasswordAndSaveIt", true);
    let token = document.querySelector("meta[name='_csrf']").content;
    let header = document.querySelector("meta[name='_csrf_header']").content;
    //xhr.setRequestHeader(header, token);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (xhr.responseText != null) {
                console.log('Request succeeded.' + xhr.responseText);
                setMessagesAfterSave(xhr.responseText);
            }
        } else if (xhr.status !== 200) {
            console.log('Request failed.  Returned status of ' + xhr.status);
            //showUpdateStatus(false);
        }
    };
    xhr.send("current=" + current + "&newPass=" + newPass);
}

function setMessagesAfterSave(responseText) {
    console.log('Response is ' + responseText);
    let userMap = JSON.parse(responseText);
    if (userMap.message != null) {
        document.getElementById("current").setAttribute("class", "form-control is-invalid");
        document.getElementById("current_message").innerHTML = userMap.message;
    }
    let detail = document.getElementById("passwordResetModal");
    detail.innerHTML = "";
    let divStatus = document.createElement("div");
    divStatus.setAttribute("class", "pop-status");
    let imgStatus = document.createElement("img");
    let statusMessage = document.createElement("h3");
    if (userMap.success != null) {
        statusMessage.innerHTML = userMap.success;
        imgStatus.setAttribute("src", "/img/tick.png");
    } else {
        statusMessage.innerHTML = userMap.message;
        imgStatus.setAttribute("src", "/img/cross.svg");
    }
    divStatus.appendChild(imgStatus);
    divStatus.appendChild(statusMessage);
    detail.appendChild(divStatus);
}

function clearMessage() {
    document.getElementById("current").setAttribute("class", "form-control form-control-sm");
    document.getElementById("newPass").setAttribute("class", "form-control form-control-sm");
    document.getElementById("repeat").setAttribute("class", "form-control form-control-sm");
}

function clearAll() {
    document.getElementById("current").value = "";
    document.getElementById("newPass").value = "";
    document.getElementById("repeat").value = "";
}

function hideAlerts() {
    document.getElementById('user').text = "User <span class='sr-only'>User</span>";
}

function justClose() {
    console.log("not yet implemented");
}