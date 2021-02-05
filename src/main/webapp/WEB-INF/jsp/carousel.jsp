<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/carousel.css">
    <link href="https://cdn.datatables.net/1.10.23/css/jquery.dataTables.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-1 backbutton">
            <i class="fa fa-chevron-left fa-3x btn-prev"></i>
        </div>
        <div class="col"style="display: flex;align-items: center;justify-content: center;max-width: 750px;"  >
            <div>
                <div class="row" >
                    <div class="card-body" >
                        <div class="img-body">
                            <div>
                                <h2 style="text-align: center;">Az első kép</h2>
                            </div>
                            <div>
                                <img src="/getImage/1" class="image">
                            </div>
                        </div>
                        <div class="img-body">
                            <div>
                                <h2 style="text-align: center;">A második kép</h2>
                            </div>
                            <div>
                                <img src="/getImage/2" class="image">
                            </div>
                        </div>
                        <div class="img-body">
                            <div>
                                <h2 style="text-align: center;">A harmadik kép</h2>
                            </div>
                            <div>
                                <img src="/getImage/3" class="image">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-1 fwdbutton">
            <i class="fa fa-chevron-right fa-3x btn-next"></i>
        </div>
    </div>
</div>
<script src="/js/carousel.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" ></script>
</body>
</html>