window.onload = function () {
    getUser();
}

function getUser() {
    fetch("api/user")
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            if (jsonData.role == "ROLE_ADMIN") {
                showForAdmin();
            }

        });
}

function showForAdmin() {
    let admindiv = document.querySelector("#visibleforadmin");
    admindiv.setAttribute("style", "display : block;");
}