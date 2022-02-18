<%@ page import="model.Task" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User page</title>
</head>
<body>
<% List<Task> allTasks = (List<Task>) request.getAttribute("tasks");%>
<a href="/logout" >logout</a>
<div>
    All Tasks:<br>
    <table border="1">
        <tr>
            <th>name</th>
            <th>description</th>
            <th>deadline</th>
            <th>status</th>
            <th>Update Status</th>
        </tr>
        <%
            for (Task task : allTasks) { %>
        <tr >
            <td><%=task.getName()%></td>
            <td><%=task.getDescription()%></td>
            <td><%=task.getDeadline()%></td>
            <td><%=task.getStatus().name()%></td>
            <td>
                <form action="/changeTaskStatus" method="post">
                    <input type="hidden" name="taskId" value="<%=task.getId()%>">
                    <select name="status">
                        <option value="NEW">NEW</option>
                        <option value="TO_PROGRESS">TO_PROGRESS</option>
                        <option value="FINISHED">FINISHED</option>
                    </select><input type="submit" value="OK">
                </form>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>
