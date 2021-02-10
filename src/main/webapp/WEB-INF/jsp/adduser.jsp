<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="user" class="com.example.jsp.Model.User" scope="session"/>
<jsp:useBean id="sessionBean" class="com.example.jsp.Model.Session" scope="session"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/manage.css">
    <link href="https://cdn.datatables.net/1.10.23/css/jquery.dataTables.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<form:form method="POST" id="adduserForm" modelAttribute="user" action="save">
    <div class="row mt-4">
        <div class="col-6">
            <table class="table table-striped" id="usercontroltable">
                <tbody>
                <h1><spring:message code="saveuser"/></h1>
                <tr id="idInputTr">
                    <td>id :</td>
                    <td><form:input path="userid" id="idInput" readOnly="true" style="color:grey;"/></td>
                    <td style="color:red;"><form:errors path="userid"/></td>
                </tr>
                <tr>
                    <td><spring:message code="name"/> : *</td>
                    <td><form:input path="name" id="nameInput"/></td>
                    <td style="color:red;" id="nameError"><form:errors path="name"/></td>
                </tr>
                <tr>
                    <td><spring:message code="password"/>: *</td>
                    <td><form:input path="password" type="password" id="passwordInput"/></td>
                    <td style="color:red;"><form:errors path="password"/></td>
                </tr>
                <tr>
                    <td>email : *</td>
                    <td><form:input path="email" type="email" id="emailInput"/></td>
                    <td style="color:red;" id="emailError"><form:errors path="email"/></td>
                </tr>
                <tr>
                    <td><spring:message code="phone"/> :</td>
                    <td><form:input path="phone" id="phoneInput"/></td>
                    <td style="color:red;" id="phoneError"><form:errors path="phone"/></td>
                </tr>
                <tr>
                    <td><spring:message code="address"/> :</td>
                    <td><form:input path="address" id="addressInput"/></td>
                    <td style="color:red;" id="addressError"><form:errors path="address"/></td>
                </tr>
                <tr>
                    <td><spring:message code="role"/> : *</td>
                    <td><form:select path="role" id="roleInput">
                        <form:option value="ROLE_ADMIN"></form:option>
                        <form:option value="ROLE_USER"></form:option>
                    </form:select>
                    </td>
                    <td style="color:red;"><form:errors path="role"/></td>
                </tr>
                <tr>
                    <td><spring:message code="organizations"/> :</td>
                    <td>
                        <button style="margin-left : 30px;" type="button" class="btn btn-warning" id="orgModal"
                                data-bs-toggle="modal" data-bs-target="#exampleModal"><spring:message
                                code="manageorgs"/></button>
                    </td>
                </tr>
                <tr>
                    <td><input type="submit" value="<spring:message code="saveuser"/>" id="savebutton"
                               class="btn btn-success"/></td>
                    <td><p style="color:red;margin-bottom: 0;padding:0;">${errorMsg}</p></td>
                    <td><p style="margin-bottom: 0;padding:0;"> * = <spring:message code="required"/></p>
                    </td>
                </tr>
                <tr style="display:none;">
                    <td style="display:none;"><form:input path="version" id="versionInput"/></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</form:form>
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel"><spring:message code="addremoveorg"/></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="orgModalBody">
            </div>
            <div class="modal-footer">
                <button id="deleteSelectedOrgs" class="btn btn-danger" disabled><spring:message
                        code="deleteorg"/></button>
                <p style="margin-right:70%;"><spring:message code="addorganization"/> :</p>
                <select class="form-select" id="orgSelect" multiple></select>
                <button type="button" class="btn btn-success" onclick="addOrg()"><spring:message code="add"/></button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" id="closeModalButton">
                    <spring:message code="close"/></button>
            </div>
        </div>
    </div>
</div>
<script src="/js/manage.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.datatables.net/1.10.23/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"/>
<script src="https://cdn.datatables.net/1.10.23/js/dataTables.bootstrap4.min.js"></script>
</body>
</html>