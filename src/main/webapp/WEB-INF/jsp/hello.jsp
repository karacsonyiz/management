<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:useBean id="sessionBean" class="com.example.jsp.Model.Session" scope="session"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello ${name}!</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

</head>
<body class="container-fluid">
    <h2 class="hello-title mt-2">Hello ${sessionBean.getLogin().getUsername()}!</h2>
    <button onclick="location.href='manage';" class="btn btn-success mt-3">Manage Users</button>
    <br>
    <button onclick="location.href='manageorgs';" class="btn btn-info mt-3">Manage Organizations</button>
    <br>
    <button onclick="generate()" class="btn btn-primary mt-3" disabled>GenerateUsers</button>
    <br>
    <button onclick="test()" class="btn btn-warning mt-3">Test</button>
    <script src="/js/hello.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body>
</html>