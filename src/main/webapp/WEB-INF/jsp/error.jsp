<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="/css/login.css">
</head>
<body style="text-align:center;">
<h1>Oops something went wrong!</h1>
<br>
<h3>Error : ${error}</h3>
<br>
<h3>StatusCode : ${status}</h3>
<br>
<h3>${message}</h3>
<br>
<a href="hello">Back to main page</a>
</body>
</html>