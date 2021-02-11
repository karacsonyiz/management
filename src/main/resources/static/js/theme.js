function switchthemeformanage(e){
    let theme = sessionStorage.getItem("theme");
    if(theme === "light"){
        document.querySelector("body").classList.add("darktheme");
        document.querySelector("nav").classList.add("darktheme");
        document.querySelector(".navbar-brand").classList.add("darktheme");
        document.querySelector(".table").classList.add("darktheme");
        document.querySelector(".table").classList.remove("table-striped");
        document.querySelector("#usercontroltable ").classList.add("darktheme");
        document.querySelector("#usercontroltable ").classList.remove("table-striped");
        document.querySelector(".modal-content").classList.add("darktheme");
        document.querySelectorAll(".nav-link").forEach(element => element.classList.add("darkthemewithoutbackground"));
        document.querySelectorAll("#dataTableTbody td").forEach(element => element.classList.add("darktheme"));
        sessionStorage.setItem("theme","dark");
        e.innerHTML = "light"
        saveThemeForUser("dark");
    } else {
        document.querySelector("body").classList.remove("darktheme");
        document.querySelector("nav").classList.remove("darktheme");
        document.querySelector(".navbar-brand").classList.remove("darktheme");
        document.querySelector(".table").classList.remove("darktheme");
        document.querySelector(".table").classList.add("table-striped");
        document.querySelector("#usercontroltable ").classList.remove("darktheme");
        document.querySelector("#usercontroltable ").classList.add("table-striped");
        document.querySelector(".modal-content").classList.remove("darktheme");
        document.querySelectorAll("#dataTableTbody td").forEach(element => element.classList.remove("darktheme"));
        document.querySelectorAll(".nav-link").forEach(element => element.classList.remove("darkthemewithoutbackground"));
        sessionStorage.setItem("theme","light");
        e.innerHTML = "dark"
        saveThemeForUser("light");
    }
}

function switchthemeforhello(e){
    let theme = sessionStorage.getItem("theme");
    if(theme === "light"){
        document.querySelector("body").classList.add("darktheme");
        document.querySelector("nav").classList.add("darktheme");
        document.querySelector(".navbar-brand").classList.add("darktheme");
        document.querySelectorAll(".nav-link").forEach(element => element.classList.add("darkthemewithoutbackground"));
        document.querySelector("#imageInput").classList.add("darkthemewithoutbackground");
        sessionStorage.setItem("theme","dark");
        e.innerHTML = "light";
        saveThemeForUser("dark");
    } else {
        document.querySelector("body").classList.remove("darktheme");
        document.querySelector("nav").classList.remove("darktheme");
        document.querySelector(".navbar-brand").classList.remove("darktheme");
        document.querySelector("#imageInput").classList.remove("darkthemewithoutbackground");
        document.querySelectorAll(".nav-link").forEach(element => element.classList.remove("darkthemewithoutbackground"));
        sessionStorage.setItem("theme","light");
        e.innerHTML = "dark";
        saveThemeForUser("light");
    }
}

function saveThemeForUser(theme){
    fetch("/setUserTheme/"+theme)
        .then(function (response) {
            return response;
        }).catch(error => console.log(error));
}

function switchthemeformanageorgs(e){
    let theme = sessionStorage.getItem("theme");
    if(theme === "light"){
        document.querySelector("body").classList.add("darktheme");
        document.querySelector("nav").classList.add("darktheme");
        document.querySelector(".navbar-brand").classList.add("darktheme");
        document.querySelector(".table").classList.add("darktheme");
        document.querySelector(".table").classList.remove("table-striped");
        document.querySelectorAll(".nav-link").forEach(element => element.classList.add("darkthemewithoutbackground"));
        document.querySelectorAll("#dataTableTbody td").forEach(element => element.classList.add("darktheme"));
        sessionStorage.setItem("theme","dark");
        e.innerHTML = "light"
        saveThemeForUser("dark");
    } else {
        document.querySelector("body").classList.remove("darktheme");
        document.querySelector("nav").classList.remove("darktheme");
        document.querySelector(".navbar-brand").classList.remove("darktheme");
        document.querySelector(".table").classList.remove("darktheme");
        document.querySelector(".table").classList.add("table-striped");
        document.querySelectorAll("#dataTableTbody td").forEach(element => element.classList.remove("darktheme"));
        document.querySelectorAll(".nav-link").forEach(element => element.classList.remove("darkthemewithoutbackground"));
        sessionStorage.setItem("theme","light");
        e.innerHTML = "dark"
        saveThemeForUser("light");
    }
}