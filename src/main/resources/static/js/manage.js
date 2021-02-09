window.onload = function () {
    generateAjaxDataTable();
    initErrorCss();
    hideSuccessMessage();
    initButtons();
    initEnterButton();
    initInputFeedback();
    initLocaleSetter();
}

function initLocaleSetter(){
    document.querySelector("#enLocale").addEventListener("click",function(){sessionStorage.setItem("lang","en")})
    document.querySelector("#huLocale").addEventListener("click",function(){sessionStorage.setItem("lang","hu")})
}

function initInputFeedback(){
    $('.searchInput').on('keyup', function() {
        if (this.value.length > 0) {
            this.classList.add("feedback");
        }
        if(this.value.length === 0){
            this.classList.remove("feedback");
        }
    });
}

function searchField() {
    let inputValues = document.querySelectorAll(".searchInput");
    let valuesObject = {};

    if($('#conditionToggle').prop('checked')){
        valuesObject["condition"] = "And";
    } else {
        valuesObject["condition"] = "Or";
    }

    for(let i = 0; i < inputValues.length;i++){
        valuesObject[inputValues[i].title] = inputValues[i].value;
    }
    generateAjaxDataTableByCriteria(valuesObject);
}

function deleteUser(id) {
    let springmessage = ""
    let lang = sessionStorage.getItem("lang");
    if(lang === "hu"){
        springMessage = sessionStorage.getItem("deletePrompt,hu")
    } else {
        springMessage = sessionStorage.getItem("deletePrompt,en")
    }
    let confirm = window.confirm(springMessage);
    if (confirm) {
        fetch("/deleteUser/" + id)
            .then(function (response) {
                showDeleteSuccessAndReload();
            })
            .catch(error => console.log(error));
        return false;
    }
}

function showDeleteSuccessAndReload() {
    document.querySelector("#deleteMessage").setAttribute("style", "display:block;color:green;");
    $('#userTable').DataTable().destroy();
    generateAjaxDataTable();
    setTimeout(function () {
        document.querySelector("#deleteMessage").setAttribute("style", "display:none;");
    }, 4000);
}

function generateDataSrcForDataTable(response){
    let data = response.userEntities;
    let entities = [];
    for (let i = 0; i < data.length; i++) {
        let row = {
            rows: response.start + i + 1,
            userid: data[i].userid,
            name: data[i].name,
            email: data[i].email,
            address: data[i].address,
            phone: data[i].phone,
            role: data[i].role,
            orgs: data[i].orgs,
        };
        entities.push(row);
    }
    return entities;
}

function generateAjaxDataTable() {
    $('#userTable').DataTable({
        "processing": true,
        "serverSide": true,
        "stateSave": true,
        "ajax": {
            'type': 'GET',
            'url': '/getUsersForPage/',
            dataSrc: function(response){return generateDataSrcForDataTable(response)}
        },
        "recordsTotal": "recordsTotal",
        "recordsFiltered": "recordsFiltered",
        "rowId": "userid",
        "pagingType": "numbers",
        columns: [
            {data: "userid"},
            {data: "name"},
            {data: "email"},
            {data: "address"},
            {data: "phone"},
            {data: "role"},
            {data: function (data) {return data.orgs.map(org => org.name).join("<br>");}},
            {"defaultContent": "<button class='btn btn-danger' onclick='deleteUser(this.parentElement.parentElement.id)'>delete</button>"},
            {"defaultContent": "<button class='btn btn-warning' onclick='getUser(this.parentElement.parentElement.id)'>update</button>"}
        ]
    });
}

function adduser() {
    adduserdiv = document.querySelector("#adduserdiv");
    adduserdiv.setAttribute("style", "display:block;");
}

function initAddUserPanel() {
    document.querySelector("#idInput").value = "";
    document.querySelector("#nameInput").value = "";
    document.querySelector("#emailInput").value = "";
    document.querySelector("#phoneInput").value = "";
    document.querySelector("#emailInput").value = "";
    document.querySelector("#addressInput").value = "";
    document.querySelector("#roleInput").value = "";
}

function getUser(id) {
    fetch("/getUser/" + id)
        .then(function (response) {
            return response.json();
        })
        .then(function (user) {
            filluserDiv(user);
        });
}

function filluserDiv(user) {
    let adduserdiv = document.querySelector("#adduserdiv");
    adduserdiv.setAttribute("style", "display:block");
    document.querySelector("#idInput").value = user.userid;
    document.querySelector("#nameInput").value = user.name;
    document.querySelector("#emailInput").value = user.email;
    document.querySelector("#phoneInput").value = user.phone;
    document.querySelector("#emailInput").value = user.email;
    document.querySelector("#addressInput").value = user.address;
    document.querySelector("#roleInput").value = user.role;
    document.querySelector("#versionInput").value = user.version;
    document.querySelector("#orgModal").addEventListener("click", function () {
        getDataForModal(user.orgs, user.userid);
    }, false);
    document.querySelector("#orgModal").setAttribute("userId", user.userid);
}

function getDataForModal(userOrgs, userid) {
    fetch("/getOrgs/")
        .then(function (response) {
            return response.json();
        })
        .then(function (orgs) {
            populateOrgModal(userOrgs, orgs.orgEntities, userid)
        });
}

function populateOrgModal(userOrgs, allOrgs, userid) {
    let orgModalBody = document.querySelector("#orgModalBody");
    let orgSelect = document.querySelector("#orgSelect");
    orgSelect.setAttribute("userID", userid);
    orgSelect.innerHTML = "";
    orgModalBody.innerHTML = "";
    for (let i in userOrgs) {
        let orgBadge = document.createElement("button");
        orgBadge.setAttribute("class", "btn btn-success p-2 m-2 orgBadge");
        orgBadge.setAttribute("value", "false");
        orgBadge.addEventListener("click", function () {
            selectDeletableOrg(this, userid)
        })
        orgBadge.innerHTML = userOrgs[i].name;
        orgModalBody.appendChild(orgBadge);
    }
    for (let k in allOrgs) {
        let option = document.createElement("option");
        option.setAttribute("value", allOrgs[k].name);
        option.innerHTML = allOrgs[k].name;
        orgSelect.appendChild(option);
    }
}

function selectDeletableOrg(element, userid) {
    document.querySelector("#deleteSelectedOrgs").disabled = false;
    document.querySelector("#deleteSelectedOrgs").userid = userid;
    if (element.getAttribute("value") === "false") {
        element.setAttribute("value", "true");
        element.style = "background-color : red;"
    } else {
        element.setAttribute("value", "false");
        element.style = "background-color : green;"
    }
}

function deleteSelectedOrgs() {
    let selectedOrgs = document.querySelectorAll(".orgBadge");
    let userid = document.querySelector("#deleteSelectedOrgs").userid;
    let orglist = [];
    for (let i in selectedOrgs) {
        if (selectedOrgs[i].value === "true") {
            orglist.push(selectedOrgs[i].innerHTML);
        }
    }
    let confirm = window.confirm("Biztos, hogy törölni szeretné ezeket a szervezeteket?");
    if (confirm) {
        deleteOrgForUser(orglist, userid)
    }
}

function deleteOrgForUser(orglist, userid) {
    fetch("/deleteOrgForUser/" + userid, {
        method: "POST",
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        },
        body: JSON.stringify({
            orgs: orglist
        }),
    }).then(function (response) {
        return response.json();
    })
        .then(function (jsonData) {
            refreshOrgModal(userid);
        })
        .catch(error => refreshOrgModal(userid));
    return false;

}

function addOrg() {
    var values = $('#orgSelect').val();
    var userid = document.querySelector("#orgModal").getAttribute("userid");
    let orgs = {
        "org": values,
    }

    fetch("/addOrgs/" + userid, {
        method: "POST",
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        },
        body: JSON.stringify(orgs)
    }).then(function (response) {
        return response.json();
    })
        .then(function (jsonData) {
            refreshOrgModal(userid);

        })
        .catch(error => refreshOrgModal(userid));
    return false;
}

function refreshOrgModal(userid) {
    fetch("/getUser/" + userid)
        .then(function (response) {
            return response.json();
        })
        .then(function (user) {
            refreshUserOrgList(user.orgs, userid);
        });
}

function refreshUserOrgList(userOrgs, userid) {
    let orgModalBody = document.querySelector("#orgModalBody");
    orgModalBody.innerHTML = "";
    for (let i in userOrgs) {
        let orgBadge = document.createElement("button");
        orgBadge.setAttribute("class", "btn btn-success p-2 m-2 orgBadge");
        orgBadge.setAttribute("value", "false");
        orgBadge.addEventListener("click", function () {
            selectDeletableOrg(this, userid)
        })
        orgBadge.innerHTML = userOrgs[i].name;
        orgModalBody.appendChild(orgBadge);
    }
}

function initErrorCss() {
    let style = "border: 1px solid red;";
    document.querySelector('#nameError').innerHTML === "" ? document.querySelector('#nameInput').style = "" : document.querySelector('#nameInput').style = style;
    document.querySelector("#emailError").innerHTML === "" ? document.querySelector("#emailInput").style = "" : document.querySelector("#emailInput").style = style;
}

function hideSuccessMessage() {
    let successMessage = document.querySelector("#successMessage");
    setTimeout(function () {
            successMessage.style = "display:none;";
            fetch("/resetActionMessage")
                .catch(error => console.log(error));
        },
        4000);
}

function resetTable() {
    location.reload();
}

function initButtons(){
    document.querySelector("#closeModalButton").addEventListener("click", function () {
        location.reload()
    });
    document.querySelector("#deleteSelectedOrgs").addEventListener("click", function () {
        deleteSelectedOrgs()
    });
    document.querySelectorAll(".searchButton").forEach(element => element.addEventListener("click", function () {
        searchField(this.value, this.parentElement.parentElement.firstChild)
    }));
    document.querySelector("#adduserbutton").addEventListener("click", initAddUserPanel);
}

function generateAjaxDataTableByCriteria(values){

    $('#userTable').DataTable().destroy();

    $('#userTable').DataTable({
        "processing": true,
        "serverSide": true,
        "stateSave": true,
        "ajax": {
            'type': 'GET',
            "contentType": "application/json; charset=utf-8",
            'url': '/getUsersForPageByCriteria/',
            "data" : values,
            dataSrc: function (response){return generateDataSrcForDataTable(response)}
        },
        "recordsTotal": "recordsTotal",
        "recordsFiltered": "recordsFiltered",
        "rowId": "userid",
        "pagingType": "numbers",
        columns: [
            {data: "userid"},
            {data: "name"},
            {data: "email"},
            {data: "address"},
            {data: "phone"},
            {data: "role"},
            {data: function (data) {return data.orgs.map(org => org.name).join("<br>");}},
            {"defaultContent": "<button class='btn btn-danger' onclick='deleteUser(this.parentElement.parentElement.id)'>delete</button>"},
            {"defaultContent": "<button class='btn btn-warning' onclick='getUser(this.parentElement.parentElement.id)'>update</button>"}
        ]
    });
    removeInputFeedback();
}

function removeInputFeedback(){
    document.querySelectorAll(".searchInput").forEach(element => element.classList.remove("feedback"))
}

function initEnterButton(){
    $(document).on('keydown', document, function(e) {
        if (e.keyCode === 13) {
            searchField();
        }
    });
}