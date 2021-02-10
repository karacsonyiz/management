function switchthemeformanage(e){
    let theme = sessionStorage.getItem("theme");
    if(theme === "light"){
        document.querySelector("body").classList.add("darktheme");
        document.querySelector("nav").classList.add("darktheme");
        document.querySelector(".navbar-brand").classList.add("darktheme");
        document.querySelector(".table").classList.add("darktheme");
        document.querySelectorAll("#dataTableTbody td").forEach(element => element.classList.add("darktheme"));
        document.querySelectorAll(".nav-link").forEach(element => element.classList.add("darkthemefornavlink"));
        sessionStorage.setItem("theme","dark");
        e.innerHTML = "light"
        setThemeForUser("dark")
    } else {
        document.querySelector("body").classList.remove("darktheme");
        document.querySelector("nav").classList.remove("darktheme");
        document.querySelector(".navbar-brand").classList.remove("darktheme");
        document.querySelector(".table").classList.remove("darktheme");
        document.querySelectorAll("#dataTableTbody td").forEach(element => element.classList.remove("darktheme"));
        document.querySelectorAll(".nav-link").forEach(element => element.classList.remove("darkthemefornavlink"));
        sessionStorage.setItem("theme","light");
        e.innerHTML = "dark"
        setThemeForUser("light")
    }
}

function switchthemeforhello(e){
    let theme = sessionStorage.getItem("theme");
    if(theme === "light"){
        document.querySelector("body").classList.add("darktheme");
        document.querySelector("nav").classList.add("darktheme");
        document.querySelector(".navbar-brand").classList.add("darktheme");
        document.querySelectorAll(".nav-link").forEach(element => element.classList.add("darkthemefornavlink"));
        sessionStorage.setItem("theme","dark");
        e.innerHTML = "light"
        setThemeForUser("dark")
    } else {
        document.querySelector("body").classList.remove("darktheme");
        document.querySelector("nav").classList.remove("darktheme");
        document.querySelector(".navbar-brand").classList.remove("darktheme");
        document.querySelectorAll(".nav-link").forEach(element => element.classList.remove("darkthemefornavlink"));
        sessionStorage.setItem("theme","light");
        e.innerHTML = "dark"
        setThemeForUser("light")
    }
}

function setThemeForUser(theme){
    fetch("/setUserTheme/"+theme)
        .then(function (response) {
            return response;
        })
        .then(function (responsetext) {
            console.log(responsetext)
        });
}