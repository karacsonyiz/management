<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:useBean id="sessionBean" class="com.example.jsp.Model.Session" scope="session"/>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello ${sessionBean.getLogin().getUsername()}!</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
</head>
<body class="container-fluid">
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="hello">Hello ${sessionBean.getLogin().getUsername()}!</a>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="manage"><spring:message code="manageusers"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="manageorgs"><spring:message code="manageorgs"/></a>
            </li>
            <li>
                <a class="nav-link"><spring:message code="greeting"/></a>
            </li>
        </ul>
    </div>
    <ul class="navbar-nav ml-auto">
        <li class="nav-item">
            <a class="nav-link" href="hello?lang=hu">Hu</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="hello?lang=en">Eng</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="login">Login</a>
        </li>
    </ul>
</nav>
<h2 class="hello-title mt-2"></h2>
<button onclick="location.href='manage';" class="btn btn-success mt-3">Manage Users</button>
<br>
<button onclick="location.href='manageorgs';" class="btn btn-info mt-3">Manage Organizations</button>
<br>
<button onclick="generate()" class="btn btn-primary mt-3">GenerateUsers</button>
<br>
<button onclick="test()" class="btn btn-warning mt-3" disabled>Test</button>
<br>
<button onclick="complexCriteriaSelect()" id="complexCritera" class="btn btn-success mt-3">Hány .hu-s email címre
    végződő user van a K-val kezdődő szervezetek között? Válasz :
</button>
<br>
<button onclick="evictCache()" class="btn btn-danger mt-3">EvictCache</button>
<br>
<script src="/js/hello.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>