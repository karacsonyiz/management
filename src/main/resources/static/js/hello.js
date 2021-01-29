window.onload = function () {
    // getUser();
}

function evictCache(){
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
            document.querySelector("#complexCriteriaSelectAnswer").innerHTML = jsonData;
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
