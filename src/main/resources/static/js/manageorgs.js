window.onload = function () {
    getOrgs();
    getUserTheme();
    getCurrentUser();
}

function logOut() {
    fetch("/logout");
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

function getOrgs() {
    fetch("/getOrgs")
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            generateAjaxDataTable(jsonData);
        });
}

function generateAjaxDataTable(dataTable) {

    $('#orgTable').DataTable({
        "recordsTotal": dataTable.recordsTotal,
        "recordsFiltered": dataTable.recordsFiltered,
        "filter": false,
        "searching": false,
        "pagingType": "numbers",
        "bPaginate": false,
        "bLengthChange": false,
        "bInfo": false,
        "responsive": true,
        "initComplete": function () {
            renderThemeForTable()
        },
        data: dataTable.orgEntities,
        columns: [
            {data: "name"},
            {
                data: function (data) {
                    return data.users.map(user => user.name).join("<br>");
                }
            },
            {"defaultContent": "<button class='btn btn-danger' onclick='deleteUser(this.parentElement.parentElement.id)'>delete</button>"},
            {"defaultContent": "<button class='btn btn-warning' onclick='getUser(this.parentElement.parentElement.id)'>update</button>"}
        ]
    });
}

function renderThemeForTable() {
    let theme = sessionStorage.getItem("theme");
    if (theme === "dark") {
        document.querySelectorAll("#dataTableTbody td").forEach(element => element.classList.add("darktheme"));
    } else {
        document.querySelectorAll("#dataTableTbody td").forEach(element => element.classList.remove("darktheme"));
    }
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
        setDarkThemeForManageOrgs();
        document.getElementById("themeSwitcher").innerHTML = "light"
    }
}
