window.onload = function () {
    getLanguageMap();
    getImages(4);
    autoPage();
    initButtons();
    document.querySelector("#uploadMessage").style = "display:none";
}

function getLanguageMap(){
    fetch("/getLanguageMap")
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            jsonData.forEach(element => sessionStorage.setItem(element.key+","+element.locale,element.content));
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
            console.log(jsonData)
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

function uploadImage(){
    let formData = new FormData();
    let fileInput = document.getElementById('imageInput');
    formData.append("file", fileInput.files[0]);
    fetch('/uploadImage', {
        method: "POST",
        body: formData,
    }).then(function (response) {
        return response.json();
    })
        .then(function (jsonData) {
            setMessage(jsonData);
        })
        .catch(error => setMessage());
    return false;
}

function setMessage(jsonData){
    let uploadMessage = document.querySelector("#uploadMessage");
    if(jsonData.status !== 400){
        uploadMessage.style = "display:block;color:green;";
        uploadMessage.innerHTML = "Upload successful!"
    } else {
        uploadMessage.style = "display:block;color:red;";
        uploadMessage.innerHTML = "Upload was not successful!"
    }
}

function getNext() {
    let current = $('.img-body:visible');
    let next = (current.next().length) ? current.next() : $('.img-body').first();
    current.css('display', 'none');
    next.css('display', 'block');
}

function getPrevious(){
    let current = $('.img-body:visible');
    let next = (current.prev().length) ? current.prev() : $('.img-body').last();
    current.css('display', 'none');
    next.css('display', 'block');
}

function autoPage(){
    setInterval(function(){getNext()} ,5000);
}

function getImages(numberOfImages){
    fetch("/getImageIdsByLimit/" + numberOfImages)
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            createImagesForCarousel(data)
        });
}

function createImagesForCarousel(imgIds){
    let carouselBody = document.querySelector(".carousel-body");
    let lang = sessionStorage.getItem("lang");

    for(let i in imgIds){
        let imgbody = document.createElement("div");
        imgbody.classList.add("img-body");
        let img = document.createElement("img");
        img.src = "/getImage/" + imgIds[i];
        img.alt = "image" + imgIds[i];
        img.classList.add("image");
        imgbody.appendChild(img);
        let label = document.createElement("h2");
        label.innerHTML = sessionStorage.getItem("labelforimg"+imgIds[i]+","+lang);
        label.setAttribute("style","text-align : center;");
        imgbody.appendChild(label);
        carouselBody.appendChild(imgbody);
    }
}


function initButtons(){
    document.querySelector("#enLocale").addEventListener("click",function(){sessionStorage.setItem("lang","en")})
    document.querySelector("#huLocale").addEventListener("click",function(){sessionStorage.setItem("lang","hu")})
    document.querySelector(".btn-next").addEventListener("click",getNext);
    document.querySelector(".btn-prev").addEventListener("click",getPrevious);
}




