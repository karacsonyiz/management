window.onload = function () {
    getOrgs();
}

function getOrgs() {
    fetch("/getOrgs")
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            console.log(jsonData);
            generateAjaxDataTable(jsonData);
        });
}

function generateAjaxDataTable(dataTable) {

    $('#orgTable').DataTable({
        initComplete: function () {
            this.api().columns().every(function () {
                var that = this;
                $('input', this.footer()).on('keyup change clear', function () {
                    if (that.search() !== this.value) {
                        that
                            .search(this.value)
                            .draw();
                    }
                });
            });
        },
        "recordsTotal": dataTable.recordsTotal,
        "recordsFiltered": dataTable.recordsFiltered,
        "filter": false,
        "searching": true,
        "pagingType": "numbers",

        data: dataTable.orgEntities,
        columns: [
            {data: "name"},
            {
                data: function (data) {
                    return data.users.map(user => user.name).join("<br>");
                }
            },
            {"defaultContent": "<button class='btn btn-danger' onclick='deleteUser(this.parentElement.parentElement.id)'>delete</button>"},
            {"defaultContent": "<button class='btn btn-warning' onclick='getUser(this.parentElement.parentElement.id)'>update</button>"}
        ]
    });
}