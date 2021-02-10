window.onload = function () {
    setDefaultLang();
    getLanguageMap();
    getImages(3);
    autoPage();
    initButtons();
    document.querySelector("#uploadMessage").style = "display:none";
    getUserTheme()
}

function getLanguageMap() {
    fetch("/getLanguageMap")
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            jsonData.forEach(element => sessionStorage.setItem(element.key + "," + element.locale, element.content));
        });
}

function evictCache() {
    fetch("/evictCache")
        .catch(error => console.log(error));
}

function complexCriteriaSelect() {
    fetch("/complexCriteriaSelect")
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            document.querySelector("#complexCritera").innerHTML = document.querySelector("#complexCritera").innerHTML + jsonData
        });
}

function generate() {
    fetch("/generate")
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
        });
}

function checkIfFileValid(file) {
    if (file === undefined) {
        let lang = sessionStorage.getItem("lang");
        let uploadMessage = document.querySelector("#uploadMessage");
        uploadMessage.style = "display:block;color:red;";
        uploadMessage.innerHTML = lang === "en" ? "No file selected!" : "Nincs fájl kiválasztva!";
        throw "No file selected!"
    }
}

function uploadImage() {
    let formData = new FormData();
    let fileInput = document.getElementById('imageInput');
    checkIfFileValid(fileInput.files[0]);
    formData.append("file", fileInput.files[0]);
    fetch('/uploadImage', {
        method: "POST",
        body: formData,
    }).then(function (response) {
        return response;
    })
        .then(function (jsonData) {
            setMessage(jsonData);
        })
        .catch(error => setMessage(error));
    return false;
}

function setMessage(jsonData) {
    let uploadMessage = document.querySelector("#uploadMessage");
    let lang = sessionStorage.getItem("lang");
    if (jsonData.status === 200) {
        uploadMessage.style = "display:block;color:green;";
        uploadMessage.innerHTML = sessionStorage.getItem("imguploadsuccess," + lang);
    } else if (jsonData.status === 413) {
        uploadMessage.style = "display:block;color:red;";
        uploadMessage.innerHTML = sessionStorage.getItem("imguploadlimiterror," + lang);
    } else {
        uploadMessage.style = "display:block;color:red;";
        uploadMessage.innerHTML = sessionStorage.getItem("imguploadgeneralerror," + lang);
    }
}

function getNext() {
    let current = $('.img-body:visible');
    let next = (current.next().length) ? current.next() : $('.img-body').first();
    current.css('display', 'none');
    next.css('display', 'block');
}

function getPrevious() {
    let current = $('.img-body:visible');
    let next = (current.prev().length) ? current.prev() : $('.img-body').last();
    current.css('display', 'none');
    next.css('display', 'block');
}

function autoPage() {
    setInterval(function () {
        getNext()
    }, 5000);
}

function getImages(numberOfImages) {
    fetch("/getImageIdsByLimit/" + numberOfImages)
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            createImagesForCarousel(data)
        });
}

function createImagesForCarousel(imgIds) {
    let carouselBody = document.querySelector(".carousel-body");
    let lang = sessionStorage.getItem("lang");
    for (let i in imgIds) {
        let imgbody = document.createElement("div");
        imgbody.classList.add("img-body");
        let img = document.createElement("img");
        img.src = "/getImage/" + imgIds[i];
        img.alt = "image" + imgIds[i];
        img.classList.add("image");
        imgbody.appendChild(img);
        let label = document.createElement("h2");
        label.innerHTML = sessionStorage.getItem("labelforimg" + imgIds[i] + "," + lang);
        label.setAttribute("style", "text-align : center;");
        imgbody.appendChild(label);
        carouselBody.appendChild(imgbody);
    }
}

function initButtons() {
    document.querySelector("#enLocale").addEventListener("click", function () {
        sessionStorage.setItem("lang", "en")
    })
    document.querySelector("#huLocale").addEventListener("click", function () {
        sessionStorage.setItem("lang", "hu")
    })
    document.querySelector(".btn-next").addEventListener("click", getNext);
    document.querySelector(".btn-prev").addEventListener("click", getPrevious);
}

function setDefaultLang() {
    if (sessionStorage.getItem("lang") === null) {
        sessionStorage.setItem("lang", "en");
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
    document.querySelectorAll(".nav-link").forEach(element => element.classList.add("darkthemefornavlink"));
    sessionStorage.setItem("theme","dark");
    e.innerHTML = "light"
}