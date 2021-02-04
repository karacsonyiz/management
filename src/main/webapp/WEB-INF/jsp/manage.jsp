<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/manage.css">
    <link href="https://cdn.datatables.net/1.10.23/css/jquery.dataTables.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
</head>
<body class="container-fluid">
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="hello">Hello ${sessionBean.getLogin().getUsername()}!</a>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" id="sajt" href="manage"><spring:message code="manageusers"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="manageorgs"><spring:message code="manageorgs"/></a>
            </li>
        </ul>
    </div>
    <ul class="navbar-nav ml-auto">
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
    <h5 style="${sessionBean.getActionMessage()}" id="successMessage"><spring:message code='${sessionBean.getActionResponse()}' text=""/></h5>
</div>
<div style="text-align: center;">
    <h5 style="display:none;" id="deleteMessage"><spring:message code="deletesuccess"/></h5>
</div>
<div>
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
            <th><spring:message code="delete"/></th>
            <th><spring:message code="update"/></th>
        </tr>
        </thead>
        <tbody id="dataTableTbody">
        </tbody>
        <tfoot>
        <tr>
            <th class="px-0">
                <div style="display:inline-flex"><input type="number" placeholder="Search id" class="searchInput" title="userid">
                    <div>
                        <button class="mx-1 searchButton" value="userid">Go</button>
                    </div>
                </div>
            </th>
            <th class="px-0">
                <div style="display:inline-flex"><input placeholder="Search name" class="searchInput" title="name">
                    <div>
                        <button class="mx-1 searchButton" value="name">Go</button>
                    </div>
                </div>
            </th>
            <th class="px-0">
                <div style="display:inline-flex"><input placeholder="Search email" class="searchInput" title="email">
                    <div>
                        <button class="mx-1 searchButton" value="email">Go</button>
                    </div>
                </div>
            </th>
            <th class="px-0">
                <div style="display:inline-flex"><input placeholder="Search address" class="searchInput" title="address">
                    <div>
                        <button class="mx-1 searchButton" value="address">Go</button>
                    </div>
                </div>
            </th>
            <th class="px-0">
                <div style="display:inline-flex"><input placeholder="Search phone" class="searchInput" title="phone">
                    <div>
                        <button class="mx-1 searchButton" value="phone" >Go</button>
                    </div>
                </div>
            </th>
            <th class="px-0">
                <div style="display:inline-flex"><input placeholder="Search role" class="searchInput" title="role">
                    <div>
                        <button class="mx-1 searchButton" value="role" >Go</button>
                    </div>
                </div>
            </th>
            <th class="px-0">
                <div style="display:inline-flex"><input placeholder="Search orgs" class="searchInput" id="searchInputForKeyUp" title="orgs">
                    <div>
                        <button class="mx-1 searchButton" id="SearchButton" value="orgs">Go</button>
                    </div>
                </div>
            </th>
            <th>
                <button class="btn btn-danger" onclick="resetTable()">reset</button>
            </th>
            <th>
                <input type="checkbox" id="conditionToggle" data-toggle="toggle" data-on="And" data-off="Or" data-onstyle="success" data-offstyle="primary">
            </th>
        </tr>
        </tfoot>
    </table>
    <button class="btn btn-success mb-2" id="adduserbutton" onclick="adduser()"><spring:message code="adduser"/></button>
</div>
<div id="adduserdiv" style="${userTableStyle}">
    <jsp:include page="adduser.jsp"></jsp:include>
</div>
<script src="/js/manage.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.datatables.net/1.10.23/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.datatables.net/1.10.23/js/dataTables.bootstrap4.min.js"></script>
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
</body>
</html>