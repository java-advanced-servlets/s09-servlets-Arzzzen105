<%@ page import="com.softserve.itacademy.model.Task" %>
<%@ page import="com.softserve.itacademy.model.Priority" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
<head>
    <title>Edit existing Task</title>
    <style>
        <%@include file="../styles/main.css"%>
    </style>
</head>
<body>
    <%@include file="header.html" %>

    <div class="form-container">
        <h2>Edit the Task</h2>

        <% if (request.getAttribute("error") != null) {%>
            <div class="alert-danger">
                <%= request.getAttribute("error") %>
            </div>>
        <% } %>
        <%
            Task task = (Task) request.getAttribute("task");
        %>
        <form action="${pageContext.request.contextPath}/edit-task?id=<%= task.getId() %>" method="post">
            <%  String prevTitle = (String) request.getAttribute("prevTitle");
                Priority prevPriority = (Priority) request.getAttribute("prevPriority");
                if (task != null) { %>
                <input id="id" name="name" value="<%=task.getId()%>" hidden>
                <div class="form-group">
                    <label for="title">Title:</label>
                    <input  type="text" id="title" name="title" class="form-control" placeholder="Enter new task title"
                            value="<%=prevTitle != null ? prevTitle : task.getTitle()%>" required>
                </div>
                <div class="form-group">
                    <label for="priority">Priority:</label>
                    <select id="priority" name="priority" class="form-select" required>
                        <%
                            Priority selected = prevPriority == null ? task.getPriority() : prevPriority;
                            for (Priority priority : Priority.values()) {
                        %>
                            <option value="<%=priority.name()%>" <%=selected.equals(priority) ? "selected" : ""%>><%=priority.name()%></option>
                        <% } %>
                    </select>
                </div>

                <button type="submit" class="btn btn-success">Edit Task</button>
                <a href="${pageContext.request.contextPath}/tasks-list" class="btn">Cancel</a>
            <% } else { %>
                <p class="alert alert-danger">Task information is missing.</p>
            <% } %>
        </form>
    </div>

</body>
</html>
