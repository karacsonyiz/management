<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/manage.css">
    <link rel="stylesheet" href="/css/theme.css">
    <link href="https://cdn.datatables.net/1.10.23/css/jquery.dataTables.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/responsive/2.2.7/css/responsive.bootstrap.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/buttons/1.6.5/css/buttons.dataTables.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <nav class="navbar navbar-expand-lg navbar-light">
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
                <a onclick="switchthemeformanage(this)" class="nav-link" id="themeSwitcher" style="cursor: pointer">dark</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="manage?lang=hu" hreflang="hu" id="huLocale">Hu</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="manage?lang=en" hreflang="en" id="enLocale">Eng</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="login">Login</a>
            </li>
        </ul>
    </nav>
    <h1><spring:message code="manageusers"/></h1>
    <div style="text-align: center;">
        <h5 style="${sessionBean.getActionMessage()}" id="successMessage"><spring:message
                code='${sessionBean.getActionResponse()}' text=""/></h5>
    </div>
    <div style="text-align: center;">
        <h5 style="display:none;" id="deleteMessage"><spring:message code="deletesuccess"/></h5>
    </div>
    <div class="userTableDiv" style="width: 100%;">
        <table id="userTable" class="table table-striped">
            <thead>
            <tr>
                <th>id</th>
                <th><spring:message code="name"/></th>
                <th>email</th>
                <th><spring:message code="address"/></th>
                <th><spring:message code="phone"/></th>
                <th><spring:message code="role"/></th>
                <th><spring:message code="orgs"/></th>
                <th class="ignorecolvis notexport"></th>
                <th class="ignorecolvis notexport"><spring:message code="delete"/></th>
                <th class="ignorecolvis notexport"><spring:message code="update"/></th>
            </tr>
            </thead>
            <tbody id="dataTableTbody">
            </tbody>
            <tfoot class="dataTableTfoot">
            <tr>
                <th>id</th>
                <th><spring:message code="name"/></th>
                <th>email</th>
                <th><spring:message code="address"/></th>
                <th><spring:message code="phone"/></th>
                <th><spring:message code="role"/></th>
                <th><spring:message code="orgs"/></th>
                <th class="ignorecolvis notexport" style="display:none;"></th>
                <th class="ignorecolvis notexport" style="display:none;"></th>
                <th style="display:block !important;" class="ignorecolvis">
                    <input type="checkbox" id="conditionToggle" data-toggle="toggle" title="" data-on="And" data-off="Or"
                           data-onstyle="success" data-offstyle="primary" style="width:15px;">
                </th>
            </tr>
            </tfoot>
        </table>
        <button class="btn btn-success mb-2" id="adduserbutton" onclick="adduser()"><spring:message
                code="adduser"/></button>
    </div>
    <div id="adduserdiv" style="${userTableStyle}">
        <jsp:include page="adduser.jsp"></jsp:include>
    </div>
    <script src="/js/manage.js"></script>
    <script src="/js/theme.js"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <script src="https://cdn.datatables.net/1.10.23/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.23/js/dataTables.bootstrap4.min.js"></script>
    <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
    <script src="https://cdn.datatables.net/responsive/2.2.7/js/dataTables.responsive.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.6.5/js/dataTables.buttons.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.6.5/js/buttons.html5.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.6.4/js/buttons.colVis.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.6.4/js/buttons.colVis.min.js"></script>
    <script src="https://cdn.datatables.net/colreorder/1.5.3/js/dataTables.colReorder.js"></script>
</div>
</body>
</html>