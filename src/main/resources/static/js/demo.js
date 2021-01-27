window.onload = function () {
    getUser();
}

function getUser() {
    fetch("api/users")
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            fillUserDiv(jsonData);
        });
}

function fillUserDiv(jsonData) {

    let usertbody = document.querySelector("#user-tbody")

    for (i in jsonData) {
        let tr = document.createElement("tr");
        let idTd = document.createElement("td");
        idTd.innerHTML = jsonData[i].id;
        tr.appendChild(idTd);
        let nameTd = document.createElement("td");
        nameTd.innerHTML = jsonData[i].name;
        tr.appendChild(nameTd);
        usertbody.appendChild(tr);
    }


    console.log(jsonData)
}