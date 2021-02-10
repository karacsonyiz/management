window.onload = function () {
    getOrgs();
    getUserTheme();
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
        "initComplete": function(){renderThemeForTable()},
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

function renderThemeForTable(){
    let theme = sessionStorage.getItem("theme");
    if(theme === "dark"){
        document.querySelectorAll("#dataTableTbody td").forEach(element => element.classList.add("darktheme"));
    }
}

function getUserTheme(){
    fetch("/getUserTheme")
        .then(function (response) {
            return response.text();
        })
        .then(function (responsetext) {
            sessionStorage.setItem("theme",responsetext);
            setTheme(responsetext);
        });
}

function setTheme(theme){
    if(theme === "dark"){
        switchtheme(document.getElementById("themeSwitcher"))
    }
}

function switchtheme(e){
    document.querySelector("body").classList.add("darktheme");
    document.querySelector("nav").classList.add("darktheme");
    document.querySelector(".navbar-brand").classList.add("darktheme");
    document.querySelector(".table").classList.add("darktheme");
    document.querySelectorAll("#dataTableTbody td").forEach(element => element.classList.add("darktheme"));
    document.querySelectorAll(".nav-link").forEach(element => element.classList.add("darkthemefornavlink"));
    sessionStorage.setItem("theme","dark");
    e.innerHTML = "light"
}