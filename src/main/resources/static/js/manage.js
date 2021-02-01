window.onload = function () {
    //initColSearch();
    getUsers();
    initAddUserButton();
    initErrorCss();
    hideSuccessMessage();
    initSearchButtons();
    document.querySelector("#closeModalButton").addEventListener("click", function () {
        location.reload()
    })
    document.querySelector("#deleteSelectedOrgs").addEventListener("click", function () {
        deleteSelectedOrgs()
    })
}

function initSearchButtons() {
    document.querySelectorAll(".searchButton").forEach(element => element.addEventListener("click", function () {
        searchField(this.value, this.parentElement.parentElement.firstChild)
    }));
}

function searchField(field, input) {
    values = {
        "field": field,
        "input": input.value
    }

    fetch("/searchOnField/", {
        method: "POST",
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        },
        body: JSON.stringify(values),
    }).then(function (response) {
        return response.json();
    })
        .then(function (jsonData) {
            handleSearchResult(jsonData);
        })
        .catch(error => console.log(error));
    return false;
}

function handleSearchResult(jsonData) {
    if (jsonData.userEntities.length !== 0) {
        $('#userTable').DataTable().destroy();
        generateAjaxDataTable(jsonData);
    } else {
        setSearchMessage();
    }
}

function setSearchMessage() {
    document.querySelector("#searchResult").setAttribute("style", "display:block;color:red;text-align: center;");
    setTimeout(function () {
        document.querySelector("#searchResult").setAttribute("style", "display:none;");
    }, 4000);
}

function initColSearch() {
    $('#userTable tfoot th').each(function () {
        var title = $(this).text();
        $(this).html('<input type="text" placeholder="Search ' + title + '" />');
    });
}

function deleteUser(id) {
    fetch("/deleteUser/" + id)
        .then(function (response) {
            showDeleteSuccessAndReload();
        })
        .catch(error => console.log(error));
    return false;
}

function showDeleteSuccessAndReload() {
    document.querySelector("#deleteMessage").setAttribute("style", "display:block;color:green;");
    $('#userTable').DataTable().destroy();
    getUsers();
    setTimeout(function () {
        document.querySelector("#deleteMessage").setAttribute("style", "display:none;");
    }, 4000);
}

function getUsers() {
    fetch("/getUsers/")
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            console.log(data)
            generateAjaxDataTable(data);
        });
}

function generateAjaxDataTable(dataTable) {

    $('#userTable').DataTable({
        "ajax": {
            'type': 'GET',
            'url': '/getUsers/',
            dataSrc : "userEntities"
        },
        "recordsTotal": "recordsTotal",
        "recordsFiltered": "recordsFiltered",
        "rowId": "userid",
        "filter": false,
        "searching": false,
        "bPaginate": false,
        "pagingType": "numbers",
        "bLengthChange": false,
        "bInfo": false,
        columns: [
            {data: "userid"},
            {data: "name"},
            {data: "email"},
            {data: "address"},
            {data: "phone"},
            {data: "role"},
            {
                data: function (data) {
                    return data.orgs.map(org => org.name).join("<br>");
                }
            },
            {"defaultContent": "<button class='btn btn-danger' onclick='deleteUser(this.parentElement.parentElement.id)'>delete</button>"},
            {"defaultContent": "<button class='btn btn-warning' onclick='getUser(this.parentElement.parentElement.id)'>update</button>"}
        ]
    });
}

function adduser() {
    adduserdiv = document.querySelector("#adduserdiv");
    adduserdiv.setAttribute("style", "display:block;")
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
    console.log(user);
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
    for (i in userOrgs) {
        let orgBadge = document.createElement("button");
        orgBadge.setAttribute("class", "btn btn-success p-2 m-2 orgBadge");
        orgBadge.setAttribute("value", "false");
        orgBadge.addEventListener("click", function () {
            selectDeletableOrg(this, userid)
        })
        orgBadge.innerHTML = userOrgs[i].name;
        orgModalBody.appendChild(orgBadge);
    }
    for (k in allOrgs) {
        let option = document.createElement("option");
        option.setAttribute("value", allOrgs[k].name);
        option.innerHTML = allOrgs[k].name;
        orgSelect.appendChild(option);
    }
}

function selectDeletableOrg(element, userid) {
    document.querySelector("#deleteSelectedOrgs").disabled = false;
    document.querySelector("#deleteSelectedOrgs").userid = userid;
    console.log(element);
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
            console.log(jsonData);
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
    for (i in userOrgs) {
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

function initAddUserButton() {
    let adduserbutton = document.querySelector("#adduserbutton");
    adduserbutton.addEventListener("click", initAddUserPanel);
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
    $('#userTable').DataTable().destroy();
    getUsers();
}




