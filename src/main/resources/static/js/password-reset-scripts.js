function passwordChange() {
    console.log("going to reset password");
    //if any are empty show a message
    let forms = document.getElementsByClassName('needs-validation');
    let current = document.getElementById("current").value;
    let newPass = document.getElementById("newPass").value;
    let repeat = document.getElementById("repeat").value;

    if (forms[0].checkValidity() === false) {
        if (current.length === 0) {
            document.getElementById("current").setAttribute("class","form-control is-invalid");
        } else {
            document.getElementById("current").setAttribute("class","form-control was-validated");
        }
        if (newPass.length === 0) {
            document.getElementById("newPass").setAttribute("class","form-control is-invalid");
        } else {
            document.getElementById("newPass").setAttribute("class","form-control was-validated");
        }
        if (current.length === 0) {
            document.getElementById("repeat").setAttribute("class","form-control is-invalid");
        } else {
            document.getElementById("repeat").setAttribute("class","form-control was-validated");
        }
        return;
    }
    if (newPass === repeat) {
        console.log("both passwords are equal");
        // try submitting
    } else {
        // show error message
        console.log("both passwords are not equal");
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