window.onload = function () {
    document.querySelector("#uploadMessage").style = "display:none";
    document.querySelector("#enLocale").addEventListener("click",function(){sessionStorage.setItem("lang","en")})
    document.querySelector("#huLocale").addEventListener("click",function(){sessionStorage.setItem("lang","hu")})
    getLanguageMap();
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

function test() {
    fetch("/test")
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
        });
}

function getUser() {
    fetch("api/user")
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            console.log(jsonData.role)
        });
}

function uploadImage(){
    let formData = new FormData();
    let fileInput = document.getElementById('imageInput');
    formData.append('file', fileInput.files[0]);
    fetch('/uploadImage', {
        method: "POST",
        body: formData,
    }).then(function (response) {
        return response.json();
    })
        .then(function (jsonData) {
            setMessage(jsonData);
        })
        .catch(error => setMessage("fail"));
    return false;
}

function setMessage(jsonData){
    let uploadMessage = document.querySelector("#uploadMessage");
    if(jsonData !== "fail"){
        uploadMessage.style = "display:block;color:green;";
        uploadMessage.innerHTML = "Upload successful!"
    } else {
        uploadMessage.style = "display:block;color:red;";
        uploadMessage.innerHTML = "Upload was not successful!"
    }
}





