window.onload = function () {
    generateAjaxDataTable();
    initErrorCss();
    hideSuccessMessage();
    initButtons();
    initLocaleSetter();
    getUserTheme();
    initColSearch();
    initInputFeedback();
    getCurrentUser();
}

function getCurrentUser() {
    fetch("/getCurrentUser")
        .then(function (response) {
            return response.text();
        })
        .then(function (jsonData) {
            if (jsonData === "Guest" || jsonData === "") {
                document.querySelector("#logout").innerHTML = "Login";
            }
        });
}

function initLocaleSetter() {
    document.querySelector("#enLocale").addEventListener("click", function () {
        sessionStorage.setItem("lang", "en")
    })
    document.querySelector("#huLocale").addEventListener("click", function () {
        sessionStorage.setItem("lang", "hu")
    })
}

function initInputFeedback() {
    $(".dataTableTfoot :input").on('keyup', function () {
        if (this.value.length > 0) {
            this.classList.add("feedback");
        }
    });
}

function deleteUser(element) {
    let id;
    if (element.id === "") {
        id = $(element.parentElement.parentElement.parentElement.previousSibling).closest('tr')[0].id;
    } else {
        id = element.id;
    }
    let springMessage = ""
    let lang = sessionStorage.getItem("lang");
    if (lang === "hu") {
        springMessage = sessionStorage.getItem("deletePrompt,hu");
    } else {
        springMessage = sessionStorage.getItem("deletePrompt,en");
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

function initColSearch() {
    let search = "Search";
    if (sessionStorage.getItem("lang") === "hu") {
        search = sessionStorage.getItem("search,hu");
    }
    $('#userTable tfoot th').each(function () {
        let title = $(this).text();
        if (this.classList.contains("ignorecolvis") === false) {
            if (this.classList.contains("number")) {
                $(this).html('<input type="number" placeholder="' + search + " " + title + '" />');
            } else {
                $(this).html('<input type="text" placeholder="' + search + " " + title + '" />');
            }
        }
    });
}


function showDeleteSuccessAndReload() {
    document.querySelector("#deleteMessage").setAttribute("style", "display:block;color:green;");
    $('#userTable').DataTable().ajax.reload();
    setTimeout(function () {
        document.querySelector("#deleteMessage").setAttribute("style", "display:none;");
    }, 4000);
}

function generateDataSrcForDataTable(response) {
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

function generateAjaxDataTable(values) {

    let table = $('#userTable').DataTable({
        "processing": true,
        "serverSide": true,
        "drawCallback": function (settings) {
            removeInputFeedback();
        },
        "ajax": {
            'type': 'POST',
            'url': '/getUsersForPage/',
            "data": values,
            dataSrc: function (response) {
                return generateDataSrcForDataTable(response)
            },
        },
        "search": true,
        "initComplete": function () {
            this.api().columns().every(function () {
                var that = this;
                $('input', this.footer()).on('focusout', function () {
                    that.search(this.value)
                });
                $('label', this.footer()).on('click', function () {
                    if (this.innerHTML === "Or") {
                        that.search("And")
                    } else {
                        that.search("Or")
                    }
                });
                $('input', this.footer()).on('keypress', function (e) {
                    if (e.which === 13) {
                        if (that.search() !== this.value) {
                            that.search(this.value).draw();
                        }
                    }
                });
            });

        },
        "recordsTotal": "recordsTotal",
        "recordsFiltered": "recordsFiltered",
        "rowId": "userid",
        "pagingType": "numbers",
        "responsive": true,
        "info": false,
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
            {"defaultContent": ""},
            {"defaultContent": "<button class='btn btn-danger' onclick='deleteUser(this.parentElement.parentElement)'>delete</button>"},
            {"defaultContent": "<button class='btn btn-warning' onclick='getUser(this.parentElement.parentElement)'>update</button>"}
        ]
    });
    InitHideColBarAndButtons(table);
    table.on('draw', function () {
        setErrorMsgAfterSearch();
        removeInputFeedback();
    });
}

function setErrorMsgAfterSearch(){
    $("#errorMsg").load(" #errorMsg");
    setTimeout(function () {
            fetch("/resetActionMessage")
            .catch(error => console.log(error));
            document.querySelector("#errorMsg").innerHTML = "";
        },
        4000);
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

function getUser(element) {
    let id;
    if (element.id === "") {
        id = $(element.parentElement.parentElement.parentElement.previousSibling).closest('tr')[0].id;
    } else {
        id = element.id;
    }
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
            populateOrgModal(userOrgs, orgs.orgEntities, userid);
        });
}

function populateOrgModal(userOrgs, allOrgs, userid) {
    let orgSelect = document.querySelector("#orgSelect");
    orgSelect.setAttribute("userID", userid);
    orgSelect.innerHTML = "";
    fillUserOrgList(userOrgs, userid);
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
        element.style = "background-color : red;";
    } else {
        element.setAttribute("value", "false");
        element.style = "background-color : green;";
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
        deleteOrgForUser(orglist, userid);
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
            fillUserOrgList(user.orgs, userid);
        });
}

function fillUserOrgList(userOrgs, userid) {
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

function refreshTable() {
    $('#userTable').DataTable().ajax.reload();
}

function resetTable(){
    location.reload();
}

function initButtons() {
    document.querySelector("#closeModalButton").addEventListener("click", function () {
        refreshTable()
    });
    document.querySelector("#deleteSelectedOrgs").addEventListener("click", function () {
        deleteSelectedOrgs();
    });
    document.querySelector("#adduserbutton").addEventListener("click", initAddUserPanel);
}

function renderThemeForTable() {
    let theme = sessionStorage.getItem("theme");
    if (theme === "dark") {
        document.querySelectorAll("#dataTableTbody td").forEach(element => element.classList.add("darktheme"));
    } else {
        document.querySelectorAll("#dataTableTbody td").forEach(element => element.classList.remove("darktheme"));
    }
}

function removeInputFeedback() {
    document.querySelectorAll('input').forEach(element => {
        if (element.classList.contains("feedback")) {
            element.classList.remove("feedback")
        }
    });
}

function getUserTheme() {
    fetch("/getUserTheme")
        .then(function (response) {
            return response.text();
        })
        .then(function (responsetext) {
            sessionStorage.setItem("theme", responsetext);
            setTheme(responsetext);
        });
}

function setTheme(theme) {
    if (theme === "dark") {
        setDarkThemeForManage();
        document.getElementById("themeSwitcher").innerHTML = "light";
    }
}

function InitHideColBarAndButtons(table) {
    let lang = sessionStorage.getItem("lang");
    new $.fn.dataTable.Buttons(table, {
        "buttons": [
            {
                extend: 'excel',
                text: sessionStorage.getItem("saveexcel," + lang),
                columns: ':not(.notexport)'
            }, {
                extend: 'colvis',
                columns: ':not(.ignorecolvis)',
                text: sessionStorage.getItem("columnvisibility," + lang)
            }
        ],
    });
    table.buttons().containers().appendTo('.dataTableTfoot');
    document.querySelector(".buttons-excel").parentNode.insertBefore(document.createElement("br"), document.querySelector(".buttons-excel").nextSibling);
}
