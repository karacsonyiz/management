<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:useBean id="sessionBean" class="com.example.jsp.Model.Session" scope="session"/>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.datatables.net/1.10.23/css/jquery.dataTables.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/responsive/2.2.7/css/responsive.bootstrap.css" rel="stylesheet">
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
        </ul>
    </div>
    <ul class="navbar-nav ml-auto">
        <li class="nav-item">
            <a class="nav-link" href="manageorgs?lang=hu" hreflang="hu" id="huLocale">Hu</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="manageorgs?lang=en" hreflang="en" id="enLocale">Eng</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="login">Login</a>
        </li>
    </ul>
</nav>
<h1><spring:message code="manageorgs"/></h1>
<table id="orgTable" class="table table-striped" style="width: 100%;">
    <thead>
    <tr>
        <th><spring:message code="name"/></th>
        <th><spring:message code="users"/></th>
        <th>delete</th>
        <th>update</th>
    </tr>
    </thead>
</table>
<script src="/js/manageorgs.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.datatables.net/1.10.23/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.datatables.net/1.10.23/js/dataTables.bootstrap4.min.js"></script>
<script src="https://cdn.datatables.net/responsive/2.2.7/js/dataTables.responsive.js"></script>
</body>
</html>