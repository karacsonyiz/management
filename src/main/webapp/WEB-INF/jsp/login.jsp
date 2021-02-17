<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>
<form:form method="POST" id="loginForm" modelAttribute="login" action="hello">
    <div class="login">
        <div class="login-screen">
            <div class="login-title">
                <h1>Login</h1>
            </div>
            <div class="login-form">
                <table>
                    <tbody>
                    <tr>
                        <td><form:input path="username" placeholder="Username"/></td>
                    </tr>
                    <tr>
                        <td style="color:red;text-align:center;"><form:errors path="username"/></td>
                    </tr>
                    <tr>
                        <td><form:input type="password" path="password" placeholder="Password"/></td>
                        <td><form:errors path="password"/></td>
                    </tr>
                    <tr>
                        <td>
                            <input type="submit" value="Log In" class="btn btn-success"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</form:form>
</body>
</html>