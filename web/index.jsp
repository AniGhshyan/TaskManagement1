<%@ page import="model.User" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2/14/2022
  Time: 3:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Task Management</title>
</head>
<body>
<form action="/login" method="post">
    <input type="email" name="email" placeholder="Input your email"><br>
    <input type="password" name="password" placeholder="Input your password"><br>
    <input type="submit" value="ok">
</form>
</body>
</html>
