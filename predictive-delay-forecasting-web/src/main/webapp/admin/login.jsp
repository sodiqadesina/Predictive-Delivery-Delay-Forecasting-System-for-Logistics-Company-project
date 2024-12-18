<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Login</title>
</head>
<body>
    <h2>Admin Login</h2>
<form action="<%= request.getContextPath() %>/AdminLoginServlet" method="post">
    <label for="name">Admin Username:</label>
    <input type="text" name="username" id="username" required><br><br> <!-- Note the name="username" -->
    <label for="password">Password:</label>
    <input type="password" name="password" id="password" required><br><br>
    <input type="submit" value="Login">
</form>

</body>
</html>
