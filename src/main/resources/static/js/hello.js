window.onload = function(){
   // getUser();
}

function generate(){
     fetch("/generate")
             .then(function (response) {
                 return response.json();
             })
             .then(function(jsonData) {
             });
    }

function test(){
     fetch("/test")
             .then(function (response) {
                 return response.json();
             })
             .then(function(jsonData) {
             });
    }


function getUser() {
     fetch("api/user")
             .then(function (response) {
                 return response.json();
             })
             .then(function(jsonData) {
                 console.log(jsonData.role)
             });
}
