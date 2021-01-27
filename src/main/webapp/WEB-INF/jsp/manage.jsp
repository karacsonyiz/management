<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/manage.css">
    <link href="https://cdn.datatables.net/1.10.23/css/jquery.dataTables.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
</head>
<body class="container-fluid">
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="hello">Hello ${sessionBean.getLogin().getUsername()}!</a>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="manage"><spring:message code="manageusers" text="default"/></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="manageorgs"><spring:message code="manageorgs" text="default"/></a>
            </li>
        </ul>
    </div>
    <ul class="navbar-nav ml-auto">
        <li class="nav-item">
            <a class="nav-link" href="manage?lang=hu">Hu</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="manage?lang=eng">Eng</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="login"><spring:message code="loginpage" text="default"/></a>
        </li>
    </ul>
</nav>
<h1><spring:message code="manageusers" text="default"/></h1>
<div style="text-align: center;">
    <h5 style="${sessionBean.getActionMessage()}" id="successMessage"><spring:message code="savesuccess" text="default"/></h5>
</div>
<div style="text-align: center;">
    <h5 style="display:none;" id="deleteMessage"><spring:message code="deletesuccess" text="default"/></h5>
</div>
<div>
<table id="userTable" class="table table-striped">
            <thead>
                <tr>
                    <th>id</th>
                    <th><spring:message code="name" text="default"/></th>
                    <th>email</th>
                    <th><spring:message code="address" text="default"/></th>
                    <th><spring:message code="phone" text="default"/></th>
                    <th><spring:message code="role" text="default"/></th>
                    <th><spring:message code="orgs" text="default"/></th>
                    <th><spring:message code="delete" text="default"/></th>
                    <th><spring:message code="update" text="default"/></th>
                </tr>
            </thead>
            <tbody id="dataTableTbody">
            </tbody>
            <tfoot>
                <tr>
                    <th>id</th>
                    <th>name</th>
                    <th>email</th>
                    <th>address</th>
                    <th>phone</th>
                    <th>role</th>
                    <th>orgs</th>
                    <th style="display:none;">delete</th>
                    <th style="display:none;">update</th>
                </tr>
            </tfoot>
</table>
<button class="btn btn-success" id="adduserbutton" onclick="adduser()"><spring:message code="adduser" text="default"/></button>
<div id="adduserdiv" style="${userTableStyle}">
<jsp:include page="adduser.jsp"></jsp:include>
</div>
<div>
    <div class="row mt-4">
        <div class="col-6">
          <table class="table table-striped" >
              <tbody id="updatetable">
              </tbody>
          </table>
      </div>
    </div>
</div>
<script src="/js/manage.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.datatables.net/1.10.23/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<script src="https://cdn.datatables.net/1.10.23/js/dataTables.bootstrap4.min.js"></script>
</body>
</html>