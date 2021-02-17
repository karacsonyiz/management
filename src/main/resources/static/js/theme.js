function switchthemeformanage(e) {
    let theme = sessionStorage.getItem("theme");
    if (theme === "light") {
        setDarkThemeForManage();
        e.innerHTML = "light"
        saveThemeForUser("dark");
    } else {
        let elementsToRemoveTheme = ["body", "nav", ".navbar-brand", ".table", "#usercontroltable", ".modal-content",];
        elementsToRemoveTheme.forEach(element => document.querySelector(element).classList.remove("darktheme"));
        document.querySelector("#usercontroltable ").classList.add("table-striped");
        document.querySelector(".table").classList.add("table-striped");
        document.querySelectorAll("#dataTableTbody td").forEach(element => element.classList.remove("darktheme"));
        document.querySelectorAll(".nav-link").forEach(element => element.classList.remove("darkthemewithoutbackground"));
        document.querySelectorAll(".toggle-vis").forEach(element => element.classList.remove("darthemewithwhitetextcolor"));
        sessionStorage.setItem("theme", "light");
        e.innerHTML = "dark"
        saveThemeForUser("light");
    }
}

function switchthemeforhello(e) {
    let theme = sessionStorage.getItem("theme");
    if (theme === "light") {
        setDarkThemeForHello();
        e.innerHTML = "light";
        saveThemeForUser("dark");
    } else {
        let elementsToRemoveTheme = ["body", "nav", ".navbar-brand"];
        elementsToRemoveTheme.forEach(element => document.querySelector(element).classList.remove("darktheme"));
        document.querySelectorAll(".nav-link").forEach(element => element.classList.remove("darkthemewithoutbackground"));
        document.querySelector("#imageInput").classList.remove("darkthemewithoutbackground");
        sessionStorage.setItem("theme", "light");
        e.innerHTML = "dark";
        saveThemeForUser("light");
    }
}

function saveThemeForUser(theme) {
    fetch("/setUserTheme/" + theme)
        .then(function (response) {
            return response;
        }).catch(error => console.log(error));
}

function switchthemeformanageorgs(e) {
    let theme = sessionStorage.getItem("theme");
    if (theme === "light") {
        setDarkThemeForManageOrgs();
        e.innerHTML = "light"
        saveThemeForUser("dark");
    } else {
        let elementsToRemoveTheme = ["body", "nav", ".navbar-brand", ".table"];
        elementsToRemoveTheme.forEach(element => document.querySelector(element).classList.remove("darktheme"));
        document.querySelector(".table").classList.add("table-striped");
        document.querySelectorAll("#dataTableTbody td").forEach(element => element.classList.remove("darktheme"));
        document.querySelectorAll(".nav-link").forEach(element => element.classList.remove("darkthemewithoutbackground"));
        sessionStorage.setItem("theme", "light");
        e.innerHTML = "dark"
        saveThemeForUser("light");
    }
}

function setDarkThemeForManage() {
    let elementsToAddTheme = ["body", "nav", ".navbar-brand", ".table", "#usercontroltable", ".modal-content",];
    elementsToAddTheme.forEach(element => document.querySelector(element).classList.add("darktheme"));
    document.querySelector("#usercontroltable").classList.remove("table-striped");
    document.querySelector(".table").classList.remove("table-striped");
    document.querySelectorAll(".nav-link").forEach(element => element.classList.add("darkthemewithoutbackground"));
    document.querySelectorAll("#dataTableTbody td").forEach(element => element.classList.add("darktheme"));
    document.querySelectorAll(".toggle-vis").forEach(element => element.classList.add("darthemewithwhitetextcolor"));
    sessionStorage.setItem("theme", "dark");
}

function setDarkThemeForHello() {
    let elementsToAddTheme = ["body", "nav", ".navbar-brand"];
    elementsToAddTheme.forEach(element => document.querySelector(element).classList.add("darktheme"));
    document.querySelectorAll(".nav-link").forEach(element => element.classList.add("darkthemewithoutbackground"));
    document.querySelector("#imageInput").classList.add("darkthemewithoutbackground");
    sessionStorage.setItem("theme", "dark");
}

function setDarkThemeForManageOrgs() {
    let elementsToAddTheme = ["body", "nav", ".navbar-brand", ".table"];
    elementsToAddTheme.forEach(element => document.querySelector(element).classList.add("darktheme"));
    document.querySelector(".table").classList.remove("table-striped");
    document.querySelectorAll(".nav-link").forEach(element => element.classList.add("darkthemewithoutbackground"));
    document.querySelectorAll("#dataTableTbody td").forEach(element => element.classList.add("darktheme"));
    sessionStorage.setItem("theme", "dark");
}