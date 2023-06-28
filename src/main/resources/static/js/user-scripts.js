function hideAlerts() {
  document.getElementById("user").text =
    "User <span class='sr-only'>User</span>";
}

function search() {
  document.forms[0].action = "searchUser";
  document.forms[0].submit();
}

function clearOut() {
  document.getElementById("name").value = "";
  document.getElementById("email").value = "";
  document.getElementById("role").value =
    document.getElementById("role").options[0].value;
}

function rewriteTable(textReturned) {
  document.getElementById("myTable").innerHTML = "";
  let myTable = document.getElementById("myTable");
  myTable.setAttribute(
    "class",
    "table table-bordered table-striped table-hover caption-top"
  );
  myTable.appendChild(setCaption("User Details"));
  const thead = tableHead();
  myTable.appendChild(thead);
  myTable.appendChild(tableBodyCreation(textReturned));
}

function singleRowInTheTable(singleUser) {
  //console.log(singleUser);
  let trx = document.createElement("tr");
  trx.appendChild(photoIconOnTD());
  trx.appendChild(tdElement(singleUser.name));
  trx.appendChild(tdElement(singleUser.email));
  trx.appendChild(tdElement(showAllRoles(singleUser.roles)));
  trx.appendChild(enabledSwitcher(singleUser.enabled));
  trx.appendChild(editDeleteIcons(singleUser.id));
  return trx;
}
function photoIconOnTD() {
  const td2 = document.createElement("td");
  const photoSpan = document.createElement("span");
  photoSpan.setAttribute("class", "fa-solid fa-user fa-3x");
  td2.appendChild(photoSpan);
  return td2;
}

function enabledSwitcher(enabled) {
  const td2 = document.createElement("td");
  const enabledLink = document.createElement("a");
  if (enabled) {
    enabledLink.setAttribute("class", "fa-solid fa-circle-check");
  } else {
    enabledLink.setAttribute("class", "fa-solid fa-circle icon-dark");
  }
  enabledLink.setAttribute("href", "");
  td2.appendChild(enabledLink);
  return td2;
}
function editDeleteIcons(id) {
  const td2 = document.createElement("td");
  const editor = editIcon(id);
  const deleter = deleteIcon(id);
  td2.appendChild(editor);
  let str = td2.innerHTML;
  str += "&nbsp";
  td2.innerHTML = str;
  td2.appendChild(deleter);
  return td2;
}

function editIcon(id) {
  const editor = document.createElement("a");
  editor.setAttribute("class", "fa-regular fa-pen-to-square");
  editor.setAttribute("href", "");
  editor.setAttribute("data-bs-toggle", "modal");
  editor.setAttribute("data-bs-target", "#editUserModal");
  editor.setAttribute("title", "Edit this user");
  editor.setAttribute("onclick", "javascript:editUser(" + id + ")");
  return editor;
}

function deleteIcon(id) {
  const deleter = document.createElement("a");
  deleter.setAttribute("class", "fa-solid fa-trash");
  deleter.setAttribute("href", "/user/deleteUser/" + id);
  deleter.setAttribute("onclick", "javascript:deleteUser(event)");
  deleter.setAttribute("userId", id);
  deleter.setAttribute("title", "Delete this user");
  deleter.setAttribute("data-bs-toggle", "modal");
  deleter.setAttribute("data-bs-target", "#confirmModal");
  return deleter;
}

function showAllRoles(roles) {
  let allRoles = Array();
  for (let i = 0; i < roles.length; i++) {
    allRoles.push(roles[i].name);
  }
  return "[" + allRoles.join(",") + "]";
}

function tableHeaderRow() {
  let tr1 = document.createElement("tr");
  tr1.appendChild(tableHeader("Photo"));
  tr1.appendChild(tableHeader("Name"));
  tr1.appendChild(tableHeader("email"));
  tr1.appendChild(tableHeader("Roles"));
  tr1.appendChild(tableHeader("Enabled"));
  tr1.appendChild(tableHeader(""));
  return tr1;
}

function addNewUser() {
  let saveModal = document.getElementById("saveModal");
  saveModal.style.display = "block";
  let detail = document.getElementById("userModalBody");
  detail.innerHTML = "";
  detail.appendChild(userOnModal());
}

function userOnModal() {
  let formValidUser = document.createElement("form");
  formValidUser.setAttribute("class", "row g-3 needs-validation");
  formValidUser.novalidate = true;

  let divName = document.createElement("div");
  divName.setAttribute("class", "col-md-6");

  let txtName = aTextBox("addName", "Name", true);
  divName.appendChild(txtName);

  let tt1 = document.createElement("div");
  tt1.setAttribute("class", "invalid-tooltip");
  tt1.innerHTML = "Please provide a valid name.";
  divName.appendChild(tt1);

  let divEmail = document.createElement("div");
  divEmail.setAttribute("class", "col-md-6");
  let txtEmail = aTextBox("addEmail", "email", true);
  divEmail.appendChild(txtEmail);
  let tt2 = document.createElement("div");
  tt2.setAttribute("class", "invalid-tooltip");
  tt2.innerHTML = "Please provide a valid email.";
  divEmail.appendChild(tt2);

  let divRoles = document.createElement("div");
  divRoles.setAttribute("class", "col-md-12");
  divRoles.setAttribute("id", "divRoles");
  populateAllRoles("");

  let divPhotos1 = document.createElement("div");
  divPhotos1.setAttribute("class", "col-md-3");
  let photoLabel = document.createElement("label");
  photoLabel.textContent = "Photos :";
  divPhotos1.appendChild(photoLabel);

  let divPhotos2 = document.createElement("div");
  divPhotos2.setAttribute("class", "col-md-3");
  let fileSelector = document.createElement("input");
  fileSelector.setAttribute("type", "file");
  fileSelector.setAttribute("id", "fileImage");
  fileSelector.setAttribute("class", "form-control");
  fileSelector.setAttribute("accept", "image/png, image/jpeg");
  fileSelector.setAttribute("onchange", "javascript:showImageThumbnail(this);");
  divPhotos2.appendChild(fileSelector);

  let divPhotos3 = document.createElement("div");
  divPhotos3.setAttribute("class", "col-md-6");
  let thumbnail = document.createElement("img");
  thumbnail.setAttribute("id", "thumbnail");
  thumbnail.setAttribute("name", "image");
  thumbnail.setAttribute("src", "/img/default-user-photo.svg");
  thumbnail.setAttribute("alt", "Photo preview");
  thumbnail.setAttribute("style", "max-width:50%;");
  thumbnail.setAttribute("class", "icon-photo img-fluid img-thumbnail");
  divPhotos3.appendChild(thumbnail);

  formValidUser.appendChild(divName);
  formValidUser.appendChild(divEmail);
  formValidUser.appendChild(divRoles);
  formValidUser.appendChild(divPhotos1);
  formValidUser.appendChild(divPhotos2);
  formValidUser.appendChild(divPhotos3);
  return formValidUser;
}

function showImageThumbnail(fileInput) {
  let file = fileInput.files[0];
  let fileSize = fileInput.files[0].size;
  if (fileSize > 1048576) {
    fileInput.setCustomValidity("You must choose an image less than 1MB !!");
    fileInput.reportValidity();
  } else {
    fileInput.setCustomValidity("");
    let reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = (function (f) {
      return function (e) {
        let thumb = document.getElementById("thumbnail");
        thumb.setAttribute("src", e.target.result);
      };
    })(file);
  }
}

function saveFromModal() {
  const addName = document.getElementById("addName").value;
  const addEmail = document.getElementById("addEmail").value;
  const thumbnail = document.getElementById("fileImage").files[0];
  console.log("thumbnail src is :" + thumbnail);
  const forms = document.getElementsByClassName("needs-validation");
  let allFieldsAreValid = true;
  let addRole = [];
  const checkboxes = document.querySelectorAll("input[type=checkbox]:checked");
  for (let i = 0; i < checkboxes.length; i++) {
    addRole.push(checkboxes[i].value);
    console.log(checkboxes[i].value);
  }
  if (checkboxes.length === 0 || forms[0].checkValidity() === false) {
    allFieldsAreValid = false;
    if (addName.length === 0) {
      document
        .getElementById("addName")
        .setAttribute("class", "form-control is-invalid");
    } else {
      document
        .getElementById("addName")
        .setAttribute("class", "form-control was-validated");
    }
    if (addEmail.length === 0) {
      document
        .getElementById("addEmail")
        .setAttribute("class", "form-control is-invalid");
    } else {
      document
        .getElementById("addEmail")
        .setAttribute("class", "form-control was-validated");
    }
    console.log(addRole.length);
    const allChecks = document.querySelectorAll("input[type=checkbox]");
    if (checkboxes.length === 0) {
      //this is not working, lets fix it later
      for (let i = 0; i < allChecks.length; i++) {
        allChecks[i].nextElementSibling.setAttribute(
          "class",
          "form-check-label is-invalid"
        );
      }
    }
  }

  if (allFieldsAreValid) {
    saveThisUser(addName, addEmail, addRole, thumbnail);
  }
}

function saveThisUser(addName, addEmail, addRole, thumbnail) {
  let xhr = new XMLHttpRequest();
  xhr.open("POST", "/user/saveUser", true);
  let token = document.querySelector("meta[name='_csrf']").content;
  let header = document.querySelector("meta[name='_csrf_header']").content;
  //xhr.setRequestHeader(header, token);
  let formData = new FormData();
  if (typeof thumbnail !== "undefined") {
    formData.append('thumbnail', thumbnail, thumbnail.name);
  }
  formData.append("selectName", addName);
  formData.append("selectLogin", addEmail);
  formData.append("selectRole", addRole);
  xhr.onload = function () {
    if (xhr.status === 200) {
      if (xhr.responseText != null) {
        //console.log(xhr.responseText);
        rewriteTable(xhr.responseText);
        showStatus(true);
      }
    } else if (xhr.status !== 200) {
      console.log("Request failed.  Returned status of " + xhr.status);
      showStatus(false);
    }
  };
  xhr.send(formData);
}

function showStatus(status) {
  let detail = document.getElementById("userModalBody");
  detail.innerHTML = "";
  let saveModal = document.getElementById("saveModal");
  saveModal.style.display = "none";
  detail.appendChild(statusMessage(status, "ADD"));
}

function deleteUser(e) {
  let yesButton = document.getElementById("yesButton");
  let parentHref = e.target.href;
  //console.log(parentHref);
  yesButton.setAttribute("href", parentHref);
  return false;
}

function editUser(id) {
  document.getElementById("id").value = id;
  showEditModal();
  getUserForEdit(id);
}

function showEditModal() {
  let updateModal = document.getElementById("updateModal");
  updateModal.style.display = "block";
  let detail = document.getElementById("userEditModalBody");
  detail.innerHTML = "";
  detail.appendChild(formValidUserForEdit());
}

function formValidUserForEdit() {
  let formValidUser = document.createElement("form");
  formValidUser.setAttribute("class", "row g-3 needs-validation");
  formValidUser.novalidate = true;

  let divName = document.createElement("div");
  divName.setAttribute("class", "col-md-6");
  let txtName = aTextBox("updateName", "Name", true);
  divName.appendChild(txtName);

  let divEmail = document.createElement("div");
  divEmail.setAttribute("class", "col-md-6");
  let txtEmail = aTextBox("updateEmail", "email", true);
  divEmail.appendChild(txtEmail);

  let divRoles = document.createElement("div");
  divRoles.setAttribute("class", "col-md-12");
  divRoles.setAttribute("id", "divRoles");

  let divPhotos1 = document.createElement("div");
  divPhotos1.setAttribute("class", "col-md-3");
  let photoLabel = document.createElement("label");
  photoLabel.textContent = "Photos :";
  divPhotos1.appendChild(photoLabel);

  let divPhotos2 = document.createElement("div");
  divPhotos2.setAttribute("class", "col-md-3");
  let fileSelector = document.createElement("input");
  fileSelector.setAttribute("type", "file");
  fileSelector.setAttribute("id", "fileImage");
  fileSelector.setAttribute("class", "form-control");
  fileSelector.setAttribute("accept", "image/png, image/jpeg");
  fileSelector.setAttribute("onchange", "javascript:showImageThumbnail(this);");
  divPhotos2.appendChild(fileSelector);

  let divPhotos3 = document.createElement("div");
  divPhotos3.setAttribute("class", "col-md-6");
  let thumbnail = document.createElement("img");
  thumbnail.setAttribute("id", "thumbnail");
  thumbnail.setAttribute("name", "image");
  thumbnail.setAttribute("src", "/img/default-user-photo.svg");
  thumbnail.setAttribute("alt", "Photo preview");
  thumbnail.setAttribute("style", "max-width:50%;");
  thumbnail.setAttribute("class", "icon-photo img-fluid img-thumbnail");
  divPhotos3.appendChild(thumbnail);

  formValidUser.appendChild(divName);
  formValidUser.appendChild(divEmail);
  formValidUser.appendChild(divRoles);
  formValidUser.appendChild(divPhotos1);
  formValidUser.appendChild(divPhotos2);
  formValidUser.appendChild(divPhotos3);

  return formValidUser;
}

function getUserForEdit(id) {
  let xhr = new XMLHttpRequest();
  xhr.open("GET", "/user/getForEdit" + "?id=" + id, true);
  let token = document.querySelector("meta[name='_csrf']").content;
  let header = document.querySelector("meta[name='_csrf_header']").content;
  ///xhr.setRequestHeader(header, token);
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr.onload = function () {
    if (xhr.status === 200) {
      if (xhr.responseText != null) {
        console.log(xhr.responseText);
        populateDataForEdit(xhr.responseText);
      }
    } else if (xhr.status !== 200) {
      console.log("Request failed.  Returned status of " + xhr.status);
      showEditError();
    }
  };
  xhr.send();
}

function showEditError() {
  let detail = document.getElementById("userEditModalBody");
  detail.innerHTML = "";
  let updateModal = document.getElementById("updateModal");
  updateModal.style.display = "none";
  let divStatus = document.createElement("div");
  divStatus.setAttribute("class", "pop-status");
  let imgSuccess = document.createElement("img");

  divStatus.appendChild(imgSuccess);
  let statusMessage = document.createElement("h3");
  imgSuccess.setAttribute("src", "/img/cross.svg");
  statusMessage.innerHTML = "Failed to populate data !!";
  divStatus.appendChild(statusMessage);
  detail.appendChild(divStatus);
}

function populateDataForEdit(textReturned) {
  let user = JSON.parse(textReturned);
  document.getElementById("updateName").value = user.name;
  document.getElementById("updateEmail").value = user.email;
  //console.log(user.roles);
  let roleValues = [];
  if (user.roles.length !== 0) {
    for (let i = 0; i < user.roles.length; i++) {
      roleValues.push(user.roles[i].id);
    }
  }
  // let's get all roles
  populateAllRoles(roleValues);
  //document.getElementById("updateRole").value = user.roles[0].id;
  document.getElementById("thumbnail").src = user.photosImagePath;
}

function populateAllRoles(activeRoles) {
  let xhr = new XMLHttpRequest();
  xhr.open("GET", "/roles/", true);
  let token = document.querySelector("meta[name='_csrf']").content;
  let header = document.querySelector("meta[name='_csrf_header']").content;
  ///xhr.setRequestHeader(header, token);
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr.onload = function () {
    if (xhr.status === 200) {
      if (xhr.responseText != null) {
        //console.log(xhr.responseText);
        fillRolesToCheckBox(xhr.responseText, activeRoles);
      }
    } else if (xhr.status !== 200) {
      console.log("Request failed.  Returned status of " + xhr.status);
    }
  };
  xhr.send();
}

function fillRolesToCheckBox(textReturned, activeRoles) {
  let roles = JSON.parse(textReturned);
  console.log("selected role is " + activeRoles);
  let rolesDiv = document.getElementById("divRoles");
  for (const [key, value] of Object.entries(roles)) {
    //console.log("key and value are " + key + " " + value);
    let checkDiv = roleSingleCheckAndLabel(key, value, activeRoles);
    rolesDiv.appendChild(checkDiv);
  }
}

function roleSingleCheckAndLabel(key, value, activeRoles) {
  let checkDiv = document.createElement("div");
  checkDiv.setAttribute("class", "form-check");
  let check = roleCheckBoxes(key, activeRoles);
  let labelForCheck = roleLabelsWithValue(value);
  checkDiv.appendChild(check);
  checkDiv.appendChild(labelForCheck);
  return checkDiv;
}

function roleCheckBoxes(key, activeRoles) {
  let check = document.createElement("input");
  check.setAttribute("type", "checkbox");
  check.setAttribute("value", key);
  check.setAttribute("class", "form-check-input");
  if (activeRoles.length !== 0) {
    for (let i = 0; i < activeRoles.length; i++) {
      if (activeRoles[i] === parseInt(key)) {
        check.checked = true;
      }
    }
  }
  return check;
}

function roleLabelsWithValue(message) {
  let labelForCheck = document.createElement("label");
  labelForCheck.setAttribute("class", "form-check-label");
  labelForCheck.innerHTML = message;
  return labelForCheck;
}

function updateFromModal() {
  const updateName = document.getElementById("updateName").value;
  const updateEmail = document.getElementById("updateEmail").value;
  const thumbnail = document.getElementById("fileImage").files[0];
  console.log("thumbnail src is :" + thumbnail);
  const forms = document.getElementsByClassName("needs-validation");
  let allFieldsAreValid = true;

  let updateRole = [];
  const checkboxes = document.querySelectorAll("input[type=checkbox]:checked");
  for (let i = 0; i < checkboxes.length; i++) {
    updateRole.push(checkboxes[i].value);
    console.log(checkboxes[i].value);
  }

  if (checkboxes.length === 0 || forms[0].checkValidity() === false) {
    allFieldsAreValid = false;
    if (updateName.length === 0) {
      document
        .getElementById("updateName")
        .setAttribute("class", "form-control is-invalid");
    } else {
      document
        .getElementById("updateName")
        .setAttribute("class", "form-control was-validated");
    }
    if (updateEmail.length === 0) {
      document
        .getElementById("updateEmail")
        .setAttribute("class", "form-control is-invalid");
    } else {
      document
        .getElementById("updateEmail")
        .setAttribute("class", "form-control was-validated");
    }
    console.log(updateRole.length);
    const allChecks = document.querySelectorAll("input[type=checkbox]");
    if (checkboxes.length === 0) {
      //this is not working, lets fix it later
      for (let i = 0; i < allChecks.length; i++) {
        allChecks[i].nextElementSibling.setAttribute(
          "class",
          "form-check-label is-invalid"
        );
      }
    }
  }

  if (allFieldsAreValid) {
    callAjaxUpdate(updateName, updateEmail, updateRole, thumbnail);
  }
}

function callAjaxUpdate(updateName, updateEmail, updateRole, thumbnail) {
  let id = document.getElementById("id").value;
  let xhr = new XMLHttpRequest();
  xhr.open("PUT", "/user/updateUser", true);
  let token = document.querySelector("meta[name='_csrf']").content;
  let header = document.querySelector("meta[name='_csrf_header']").content;
  //xhr.setRequestHeader(header, token);
  //xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  let formData = new FormData();
  if (typeof thumbnail !== "undefined") {
    formData.append('thumbnail', thumbnail, thumbnail.name);
  }
  formData.append("id", id);
  formData.append("name", updateName);
  formData.append("email", updateEmail);
  formData.append("roles", updateRole);
  xhr.onload = function () {
    if (xhr.status === 200) {
      if (xhr.responseText != null) {
        rewriteTable(xhr.responseText);
        showUpdateStatus(true);
      }
    } else if (xhr.status !== 200) {
      console.log("Request failed.  Returned status of " + xhr.status);
      showUpdateStatus(false);
    }
  };
  xhr.send(formData);
}

function showUpdateStatus(status) {
  let detail = document.getElementById("userEditModalBody");
  detail.innerHTML = "";
  let updateModal = document.getElementById("updateModal");
  updateModal.style.display = "none";
  detail.appendChild(statusMessage(status, "UPDATE"));
}
