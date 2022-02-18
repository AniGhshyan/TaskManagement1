<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page import="model.Task" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manager page</title>
</head>
<body>
<% List<User> allUsers = (List<User>) request.getAttribute("users");%>
<% List<Task> allTasks = (List<Task>) request.getAttribute("tasks");%>
<a href="/logout" >logout</a>
<div style="width: 800px">
    <div style="width: 50%;float: left">
        Add User:<br>
        <form action="/userRegister" method="post">
            <input type="text" name="name" placeholder="name"><br>
            <input type="text" name="surname" placeholder="surname"><br>
            <input type="email" name="email" placeholder="email"><br>
            <input type="password" name="password" placeholder="password"><br>
            <select name="type">
                <option value="USER">USER</option>
                <option value="MANAGER">MANAGER</option>
            </select><br>
            <input type="submit" value="Register">
        </form>
    </div>
    <div style="width: 50%;float: left">
        Add Task:<br>
        <form action="/addTask" method="post">
            <input type="text" name="name" placeholder="name"><br>
            <textarea name="description" placeholder="description"></textarea><br>
            <input type="date" name="deadline"><br>

            <select name="status">
                <option value="NEW">NEW</option>
                <option value="TO_PROGRESS">TO_PROGRESS</option>
                <option value="FINISHED">FINISHED</option>
            </select><br>
            <select name="user_id">
                <%
                    for (User user : allUsers) {%>
                <option value="<%=user.getId()%>"><%=user.getName()%> <%=user.getSurname()%>
                </option>
                <%
                    }
                %>
            </select><br>
            <input type="submit" value="Add Task">
        </form>
    </div>
</div>
<div>
    All Users:<br>
    <table border="1">
        <tr>
            <th>name</th>
            <th>surname</th>
            <th>email</th>
            <th>type</th>
        </tr>
        <%
            for (User user : allUsers) { %>
        <tr >
            <td><%=user.getName()%></td>
            <td><%=user.getSurname()%></td>
            <td><%=user.getEmail()%></td>
            <td><%=user.getType().name()%></td>
        </tr>
        <%
            }
        %>
    </table>
</div>
<div>
    All Tasks:<br>
    <table border="1">
        <tr>
            <th>name</th>
            <th>description</th>
            <th>deadline</th>
            <th>status</th>
            <th>user</th>
        </tr>
        <%
            for (Task task : allTasks) { %>
        <tr >
            <td><%=task.getName()%></td>
            <td><%=task.getDescription()%></td>
            <td><%=task.getDeadline()%></td>
            <td><%=task.getStatus().name()%></td>
            <td><%=task.getUser().getName() + " " + task.getUser().getSurname()%></td>
        </tr>
        <%
            }
        %>
    </table>
</div>

</body>
</html>
