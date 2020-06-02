//code to update user
function UpdateMe(){
    if(document.getElementById('name').value.length == 0) {
        document.getElementById('name').style.background = 'Yellow';
        alert(" Please enter The name");
    } else if(document.getElementById('loginId').value.length == 0) {
        document.getElementById('name').style.background = 'White';
        document.getElementById('loginId').style.background = 'Yellow';
        alert(" Please enter the Login Id");
    } else if(document.getElementById('psw').value.length == 0) {
        document.getElementById('name').style.background = 'White';
        document.getElementById('loginId').style.background = 'White';
        document.getElementById('psw').style.background = 'Yellow';
        alert(" Please enter the password");
    } else if (document.getElementById('role').value == document.getElementById('role').options[0].value ) {
        document.getElementById('name').style.background = 'White';
        document.getElementById('loginId').style.background = 'White';
        document.getElementById('psw').style.background = 'White';
        document.getElementById('role').style.background = 'Yellow';
        alert(" Please select a valid role");
    }else {
        document.getElementById('name').style.background = 'White';
        document.getElementById('loginId').style.background = 'White';
        document.getElementById('psw').style.background = 'White';
        document.getElementById('role').style.background = 'White';
        document.forms[0].action = "UpdateUser.htm";
        document.forms[0].submit();
    }
}

//code to clear
function CancelMe(){
    document.forms[0].action="ListAll.htm";
    document.forms[0].submit();
}