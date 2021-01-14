<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="user" class="com.example.jsp.Model.User" scope="session"/>
<jsp:useBean id="sessionBean" class="com.example.jsp.Model.Session" scope="session"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
</head>
<body>

<form:form method="POST" id="adduserForm" modelAttribute="user" action="save">
<div class="row mt-4">
    <div class="col-6">
      <table class="table table-striped" id="usercontroltable">
        <tbody>
        <h1>Save User</h1>
            <tr id="idInputTr">
                <td>id :</td>
                <td><form:input path="userid" id="idInput" readOnly="true" style="color:grey;"/></td>
                <td style="color:red;"><form:errors path="userid"/></td>
            </tr>
              <tr>
                  <td>name :</td>
                  <td><form:input path="name" id="nameInput"/> *</td>
                  <td style="color:red;" id="nameError"><form:errors path="name"/></td>
              </tr>
              <tr>
                  <td>password :</td>
                  <td><form:input path="password" id="passwordInput"/> *</td>
                  <td style="color:red;"><form:errors path="password"/></td>
              </tr>
              <tr>
                  <td>email :</td>
                  <td><form:input path="email" id="emailInput"/> *</td>
                  <td style="color:red;" id="emailError"><form:errors path="email"/></td>
              </tr>
              <tr>
                  <td>phone :</td>
                  <td><form:input path="phone" id="phoneInput"/></td>
                  <td style="color:red;" id="phoneError"><form:errors path="phone"/></td>
              </tr>
              <tr>
                  <td>address :</td>
                  <td><form:input path="address" id="addressInput"/></td>
                  <td style="color:red;" id="addressError"><form:errors path="address"/></td>
              </tr>
              <tr>
                  <td>role :</td>
                  <td><form:select path="role" id="roleInput">
                      <form:option value="ROLE_ADMIN"></form:option>
                      <form:option value="ROLE_USER"></form:option>
                      </form:select> *
                  </td>
                  <td style="color:red;"><form:errors path="role"/></td>
              </tr>
              <tr>
                  <td colspan="3">
                      <input type="submit" value="Save user" id="savebutton" class="btn btn-success" />
                      <a style="margin-left : 107px;"> * = Kötelező</a>
                  </td>
              </tr>
          </tbody>
      </table>
  </div>
</div>
  </form:form>
  <script src="/js/manage.js"></script>
</body>
</html>