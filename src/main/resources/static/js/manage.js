window.onload = function(){
    initColSearch();
    getUsers();
    initAddUserButton();
    initErrorCss();
    hideSuccessMessage();
}

function initColSearch(){
    $('#userTable tfoot th').each( function () {
        var title = $(this).text();
        $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
    } );
}

function deleteUser(id){
        fetch("/deleteUser/"+id)
            .then(function (response) {
                return response.json();
            })
            .then(function (response) {
                location.href = "manage";
        })
        .catch(error => location.href = "manage");
         return false;
    }

function getUsers(){
        fetch("/getUsers/")
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                console.log(data)
                generateAjaxDataTable(data);
        });
}

function generateAjaxDataTable(dataTable){

        $('#userTable').DataTable({
            initComplete: function () {
                this.api().columns().every( function () {
                    var that = this;
                    $( 'input', this.footer() ).on( 'keyup change clear', function () {
                        if ( that.search() !== this.value ) {
                            that
                                .search( this.value )
                                .draw();
                        }
                    } );
                } );
            },
            "recordsTotal": dataTable.recordsTotal,
            "recordsFiltered": dataTable.recordsFiltered,
            "rowId" : "userid",
            "filter":false,
            "searching":true,
            "pagingType": "numbers",
            data : dataTable.userEntities,
                columns: [
                    {data : "userid"},
                    {data : "name"},
                    {data : "email"},
                    {data : "address"},
                    {data : "phone"},
                    {data : "role"},
                    {"defaultContent" : "<button class='btn btn-danger' onclick='deleteUser(this.parentElement.parentElement.id)'>delete</button>"},
                    {"defaultContent" : "<button class='btn btn-warning' onclick='getUser(this.parentElement.parentElement.id)'>update</button>"}
                    ]
            });
}

function adduser(){
    adduserdiv = document.querySelector("#adduserdiv");
    adduserdiv.setAttribute("style","display:block;")
}

function initAddUserPanel(){
        //document.querySelector("#userH1").innerHTML = "Add User"
        document.querySelector("#idInput").value = "";
        document.querySelector("#nameInput").value = "";
        document.querySelector("#emailInput").value = "";
        document.querySelector("#phoneInput").value = "";
        document.querySelector("#emailInput").value = "";
        document.querySelector("#addressInput").value = "";
        document.querySelector("#roleInput").value = "";
}

function getUser(id){
        fetch("/getUser/" + id)
            .then(function (response) {
                return response.json();
            })
            .then(function (user) {
                filluserDiv(user,id);
            });
}

function filluserDiv(user,id){
    //document.querySelector("#userH1").innerHTML = "Update User"
    let adduserdiv = document.querySelector("#adduserdiv");
    adduserdiv.setAttribute("style","display:block");
    document.querySelector("#idInput").value = user.userid;
    document.querySelector("#nameInput").value = user.name;
    document.querySelector("#emailInput").value = user.email;
    document.querySelector("#phoneInput").value = user.phone;
    document.querySelector("#emailInput").value = user.email;
    document.querySelector("#addressInput").value = user.address;
    document.querySelector("#roleInput").value = user.role;
    }

function generateSaveButton(id){
    let savebutton = document.querySelector("#savebutton");
    savebutton.style="display:block;"
    savebutton.addEventListener("click",saveUser);
    savebutton.userId = id;
}

function countusers(){
     fetch("/countusers")
             .then(function (response) {
                 return response.json();
             })
             .then(function(jsonData) {
                console.log(jsonData);
             });
}

function initAddUserButton(){
let adduserbutton = document.querySelector("#adduserbutton")
adduserbutton.addEventListener("click",initAddUserPanel);
}

function initErrorCss(){
let style = "border: 1px solid red;";
document.querySelector('#nameError').innerHTML == "" ? document.querySelector('#nameInput').style = "" : document.querySelector('#nameInput').style = style;
document.querySelector("#emailError").innerHTML == "" ? document.querySelector("#emailInput").style = "" : document.querySelector("#emailInput").style = style;
}

function hideSuccessMessage(){
    let successMessage = document.querySelector("#successMessage");
    setTimeout(function(){
        successMessage.style="display:none;";
    },
    6000);
}




